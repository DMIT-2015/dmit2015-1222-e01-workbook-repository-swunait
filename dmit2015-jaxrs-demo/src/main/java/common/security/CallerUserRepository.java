package common.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
//@Interceptors({CallerUserSecurityInterceptor.class})
public class CallerUserRepository {

    @PersistenceContext
    // The unitUnit is only needed when there are multiple persistence units defined in persistence.xml
    private EntityManager _entityManager;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public void add(CallerUser newCallerUser, String plainTextPassword, String[] groupNames) {
        if (groupNames == null || groupNames.length == 0) {
            throw new RuntimeException("A caller user must be assigned a caller group.");
        }
        if (findById(newCallerUser.getUsername()).isPresent()) {
            throw new RuntimeException("Username " + newCallerUser.getUsername() + " is already in use by another user.");
        }
        // Convert the plain text password into a hashed value.
        String hashedPassword = passwordHash.generate(plainTextPassword.toCharArray());
        newCallerUser.setPassword(hashedPassword);
        for (String singleGroupName : groupNames) {
            newCallerUser.getGroups().add(singleGroupName);
        }
        _entityManager.persist(newCallerUser);
    }

    public void update(CallerUser updatedCallerUser, String[] groupNames) {
        if (groupNames == null || groupNames.length == 0) {
            throw new RuntimeException("A caller user must be assigned a caller group.");
        }
        Optional<CallerUser> optionalCallerUser = findById(updatedCallerUser.getUsername());
        if (optionalCallerUser.isPresent()) {
            CallerUser existingCallerUser = optionalCallerUser.get();
            existingCallerUser.getGroups().clear();
            for (String singleGroupName : groupNames) {
                existingCallerUser.getGroups().add(singleGroupName);
            }
            _entityManager.merge(existingCallerUser);
            _entityManager.flush();
        }
    }

    public void remove(String id) {
        Optional<CallerUser> optionalCallerUser = findById(id);
        if (optionalCallerUser.isPresent()) {
            CallerUser existingCallerUser = optionalCallerUser.get();
            _entityManager.remove(existingCallerUser);
            _entityManager.flush();
        }
    }

    public Optional<CallerUser> findById(String id) {
        Optional<CallerUser> optionalCallerUser = Optional.empty();
        try {
            CallerUser querySingleResult = _entityManager.find(CallerUser.class, id);
            if (querySingleResult != null) {
                optionalCallerUser = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalCallerUser;
    }

    public List<CallerUser> findAll() {
        return _entityManager.createQuery("SELECT u FROM CallerUser u ORDER BY u.username", CallerUser.class).getResultList();
    }

    public long count() {
        return _entityManager.createQuery("SELECT COUNT(u) FROM CallerUser u", Long.class).getSingleResult();
    }

    public void changePassword(String username, String currentPlainTextPassword, String newPlainTextPassword) throws Exception {
        char[] currentPassword = currentPlainTextPassword.toCharArray();
        Optional<CallerUser> optionalCallerUser = findById(username);
        if (optionalCallerUser.isPresent()) {
            CallerUser existingCallerUser = optionalCallerUser.get();
            // verify currentPlainTextPassword is valid
            if (passwordHash.verify(currentPassword, existingCallerUser.getPassword())) {
                String newHashedPassword = passwordHash.generate(newPlainTextPassword.toCharArray());
                existingCallerUser.setPassword(newHashedPassword);
                _entityManager.merge(existingCallerUser);
            } else {
                throw new Exception("Current password is incorrect.");
            }
        }
    }
}