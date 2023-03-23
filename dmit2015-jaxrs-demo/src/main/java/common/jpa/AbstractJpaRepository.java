package common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * This abstract Jakarta Persistence repository contains common code to manage an Jakarta Persistence entity.
 * To use this abstract class, create a new Java class that extends (inherits) from this Java class
 * then you can use the super keyword to access methods in this class.
 *
 * <p>
 * The following example creates Jakarta Persistence a repository for the Region entity in the Oracle HR schema:
 *
 * @version 2022.02.06
 * @jakarta.enterprise.context.ApplicationScoped
 * @jakarta.transaction.Transactional public class RegionRepository extends AbstractJpaRepository<Region,Long> {
 * public RegionRepository() {
 * super(Region.class);
 * }
 * }
 * The following example creates Jakarta Persistence a repository for the Country entity in the Oracle HR schema:
 * @jakarta.enterprise.context.ApplicationScoped
 * @jakarta.transaction.Transactional public class CountryRepository extends AbstractJpaRepository<Country,String> {
 * public CountryRepository() {
 * super(Country.class);
 * }
 * }
 */

public abstract class AbstractJpaRepository<E extends Serializable, ID> implements Serializable {

    private final Class<E> entityType;

    public AbstractJpaRepository(Class<E> entityType) {
        this.entityType = entityType;
    }

    /**
     * The persistence unit that is defined in persistence.xml to use.
     * You can omit the unitName if persistence.xml contains only one persistence unit.
     */
    @PersistenceContext//(unitName = "h2database-jpa-pu")
    private EntityManager _entityManager;

    public int totalPages(int pageSize) {
        int total = 0;

        if (pageSize > 0) {
            total = (int) Math.ceil(1.0 * count() / pageSize);
        }

        return total;
    }

    public int[] pageNumbers(int pageSize) {
        int[] numbersArray = {};

        if (pageSize > 0) {
            numbersArray = IntStream.range(1, totalPages(pageSize) + 1).toArray();
        }

        return numbersArray;
    }

    /**
     * Make an instance managed and persistent.
     *
     * @param entity entity instance
     * @param <E>    entity class type
     */
    public <E> void add(E entity) {
        _entityManager.persist(entity);
    }

    /**
     * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context, it is returned from there.
     *
     * @param id   primary key
     * @param <ID> primary key class type
     * @return
     */
    public <ID> E findById(ID id) {
        return _entityManager.find(entityType, id);
    }

    /**
     * Get an instance, whose state may be lazily fetched
     *
     * @param id   primary key
     * @param <ID> primary key class type
     * @return the found entity instance
     */
    public <ID> E getReference(ID id) {
        return _entityManager.getReference(entityType, id);
    }

    /**
     * Find entity by primary key and return an Optional
     *
     * @param id   primary key value
     * @param <ID> primary key class type
     * @return an Optional with the entity object
     */
    public <ID> Optional<E> findOptionalById(ID id) {
        Optional<E> optionalResult = Optional.empty();
        try {
            E singleResult = findById(id);
            if (singleResult != null) {
                optionalResult = Optional.of(singleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalResult;
    }

    /**
     * Get the data from the database instead of the second-level cache.
     *
     * @param id   primary key value
     * @param <ID> primary key class type
     * @return
     */
    public <ID> E findFresh(ID id) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.cache.retrieveMode", "BYPASS");
        return _entityManager.find(entityType, id, hints);
    }

    /**
     * Return a list of entity instances for the given pageNumber and pageSize
     *
     * @param pageNumber the starting position of the first result
     * @param pageSize   the maximum of results to return
     * @return a list of entity instance
     */
    public List<E> findAll(int pageNumber, int pageSize) {
        final CriteriaQuery<E> criteriaQuery = _entityManager.getCriteriaBuilder().createQuery(entityType);
        Root<E> queryRoot = criteriaQuery.from(entityType);
        criteriaQuery.select(queryRoot);
        return _entityManager
                .createQuery(criteriaQuery)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * Return a list with a maximum of 1000 entity instance for the given entity class
     *
     * @return a list entity instance
     */
    public List<E> findAll() {
        final CriteriaQuery<E> criteriaQuery = _entityManager.getCriteriaBuilder().createQuery(entityType);
        Root<E> queryRoot = criteriaQuery.from(entityType);
        criteriaQuery.select(queryRoot);
        return _entityManager
                .createQuery(criteriaQuery)
                .setMaxResults(1000)
                .getResultList();
    }

    /**
     * Return a list of entity instance for the given range
     *
     * @param from the index of the first entity instance
     * @param to   the index of the last entity instance
     * @return a list of entity instance
     */
    public List<E> findRange(int from, int to) {
        CriteriaQuery<E> criteriaQuery = _entityManager.getCriteriaBuilder().createQuery(entityType);
        Root<E> queryRoot = criteriaQuery.from(entityType);
        criteriaQuery.select(queryRoot);
        Query query = _entityManager.createQuery(criteriaQuery);
        query.setMaxResults(to - from + 1);
        query.setFirstResult(from);
        return query.getResultList();
    }

    /**
     * Return a list of entity instance for the given range
     *
     * @param range an array where the first element is the from index and the second element is the to index
     * @return list of entity instance
     */
    public List<E> findRange(int[] range) {
        return findRange(range[0], range[1]);
    }

    /**
     * Merge the state of the given entity into the current persistence context.
     *
     * @param entity entity instance
     * @param <E>    entity class type
     * @return the managed instance that the state was merged to
     */
    public <E> E update(E entity) {
        E merge = _entityManager.merge(entity);
        return merge;
    }

    /**
     * Remove the entity instance.
     *
     * @param entity entity instance
     */
    public <E> void delete(E entity) {
        if (isAttached(entity)) {
            _entityManager.remove(entity);
        } else {
            _entityManager.remove(_entityManager.merge(entity));
        }
    }

    /**
     * Remove the entity instance using the primary key value
     *
     * @param id primary key value
     */
    public <ID> void deleteById(ID id) {
        Optional<E> optionalEntity = findOptionalById(id);
        if (optionalEntity.isPresent()) {
            E entity = optionalEntity.get();
            delete(entity);
        }
    }

    /**
     * Delete all entities for the given entity class
     *
     * @return the number of entities deleted
     */
    public int deleteAll() {
        CriteriaBuilder criteriaBuilder = _entityManager.getCriteriaBuilder();
        CriteriaDelete<E> delete = criteriaBuilder.createCriteriaDelete(entityType);
        Root<E> root = delete.from(entityType);
        return _entityManager.createQuery(delete).executeUpdate();
    }

    /**
     * Count the number entity instance for the given entity class.
     *
     * @return number of entities
     */
    public int count() {
        CriteriaBuilder criteriaBuilder = _entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> queryRoot = criteriaQuery.from(this.entityType);
        criteriaQuery.select(criteriaBuilder.count(queryRoot));
        return ((Long) _entityManager.createQuery(criteriaQuery).getSingleResult()).intValue();
    }

    /**
     * Check if the instance is a managed entity instance belonging to the current persistence context.
     *
     * @param entity entity instance
     * @return boolean indicating if entity is in persistence context
     */
    public boolean isAttached(Object entity) {
        return _entityManager.contains(entity);
    }

    /**
     * Synchronize the persistence context to the underlying database.
     * Clear the cache.
     */
    public void clearCache() {
        _entityManager.flush();
        _entityManager.getEntityManagerFactory().getCache().evictAll();
    }

    /**
     * Return the entity manager interface used to interact with the persistence context.
     *
     * @return entity manager
     */
    protected EntityManager getEntityManager() {
        return _entityManager;
    }
}