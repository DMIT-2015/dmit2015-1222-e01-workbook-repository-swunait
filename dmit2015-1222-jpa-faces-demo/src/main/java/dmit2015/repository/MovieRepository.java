package dmit2015.repository;

import dmit2015.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MovieRepository {
    @PersistenceContext (unitName = "h2database-jpa-pu")
    private EntityManager em;

    @Transactional
    public void add(Movie newMovie) {
        em.persist(newMovie);
    }

    @Transactional
    public void update(Movie updatedMovie) {
        Optional<Movie> optionalMovie = findOptionalById(updatedMovie.getId());
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setPrice(updatedMovie.getPrice());
            existingMovie.setRating(updatedMovie.getRating());
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            em.merge(existingMovie);
        }
    }

    @Transactional
    public void delete(Movie existingMovie) {
        if (!em.contains(existingMovie)) {
            existingMovie = em.merge(existingMovie);
        }
        em.remove(existingMovie);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Movie> optionalMovie = findOptionalById(id);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            em.remove(existingMovie);
        }
    }

    public Movie findById(Long id) {
        return em.find(Movie.class, id);
    }

    public Optional<Movie> findOptionalById(Long id) {
        Optional<Movie> optionalMovie = Optional.empty();
        try {
            Movie querySingleResult = findById(id);
            if (querySingleResult != null) {
                optionalMovie = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalMovie;
    }

    public List<Movie> findAll() {
        return em.createQuery("SELECT m FROM Movie m ", Movie.class)
                .getResultList();
    }

    public List<Movie> findAllOrderByTitle() {
        return em.createQuery("SELECT m FROM Movie m ORDER BY m.title", Movie.class)
                .getResultList();
    }

    public long count() {
        return em.createQuery("SELECT COUNT(m) FROM Movie m", Long.class).getSingleResult().longValue();
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Movie").executeUpdate();
    }

}