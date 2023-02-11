package dmit2015.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.*;
import java.util.stream.Collectors;

public class VideoGameJavaSeRepository {

    private EntityManagerFactory _emf;

    public VideoGameJavaSeRepository() {
        // https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#obtaining-an-entity-manager-factory-in-a-java-se-environment
        _emf = Persistence.createEntityManagerFactory("local-mssql-dmit2015-jpa-pu");
    }

    public VideoGame create(VideoGame newVideoGame) {
        EntityManager em = _emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(newVideoGame);

        em.getTransaction().commit();
        em.close();
        return newVideoGame;
    }

    public VideoGame update(VideoGame existingVideoGame) {
        EntityManager em = _emf.createEntityManager();
        em.getTransaction().begin();

        em.merge(existingVideoGame);

        em.getTransaction().commit();
        em.close();
        return existingVideoGame;
    }

    public Optional<VideoGame> findById(Long webCode) {
        Optional<VideoGame> optionalVideoGame;
        EntityManager em = _emf.createEntityManager();

        try {
            VideoGame existingVideo = em.createQuery("""
                            SELECT g
                            FROM VideoGame g
                            WHERE g.webCode = :webCodeValue
                            """, VideoGame.class)
                    .setParameter("webCodeValue", webCode)
                    .getSingleResult();
            optionalVideoGame = Optional.of(existingVideo);
        } catch (NoResultException e) {
//            e.printStackTrace();
            optionalVideoGame = Optional.empty();
        }
        em.close();

        return optionalVideoGame;
    }

    public List<VideoGame> findAll() {
        List<VideoGame> games;
        EntityManager em = _emf.createEntityManager();

        games = em.createQuery("""
                        SELECT g
                        FROM VideoGame g
                        ORDER BY g.title
                        """, VideoGame.class)
                .getResultList();

        em.close();

        return games;
    }

    public void delete(VideoGame existingVideoGame) {
        EntityManager em = _emf.createEntityManager();
        em.getTransaction().begin();

        if (!em.contains(existingVideoGame)) {
            existingVideoGame = em.merge(existingVideoGame);
        }
        em.remove(existingVideoGame);

        em.getTransaction().commit();
        em.close();
    }

    public void deleteAll() {
        EntityManager em = _emf.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM VideoGame").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public List<VideoGame> findByGamingPlatform(GamingPlatform platform) {
        EntityManager em = _emf.createEntityManager();
        List<VideoGame> resultList = em.createQuery("SELECT g FROM VideoGame g WHERE g.platform = :platformValue ORDER BY g.title", VideoGame.class)
                .setParameter("platformValue", platform)
                .getResultList();
        em.close();
        return resultList;
    }

    public List<String> mapToTitle() {
        EntityManager em = _emf.createEntityManager();
        List<String> resultList = em.createQuery("SELECT g.title FROM VideoGame g ORDER BY g.title", String.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public List<Double> mapToDistinctPrices() {
        EntityManager em = _emf.createEntityManager();
        List<Double> resultList = em.createQuery("SELECT g.price FROM VideoGame g GROUP BY g.price ORDER BY g.price DESC", Double.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public Set<Double> mapToPriceSet() {
        EntityManager em = _emf.createEntityManager();
        Set<Double> resultSet = em.createQuery("SELECT g.price FROM VideoGame g GROUP BY g.price ORDER BY g.price DESC", Double.class)
                .getResultStream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        em.close();
        return resultSet;
    }

    public List<VideoGame> sortByPlatformThenTitleThenPriceDescending() {
        EntityManager em = _emf.createEntityManager();
        List<VideoGame> resultList = em.createQuery("SELECT g FROM VideoGame g ORDER BY g.platform, g.title, price DESC ", VideoGame.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public Optional<VideoGame> findFirstByTitleAndPlatform(String title, GamingPlatform platform) {
        EntityManager em = _emf.createEntityManager();
        List<VideoGame> resultList = em.createQuery("SELECT g FROM VideoGame g WHERE g.title = :titleParam AND g.platform = :platformParam", VideoGame.class)
                .setParameter("titleParam",title)
                .setParameter("platformParam",platform)
                .getResultList();
        Optional<VideoGame> optionalVideoGame = Optional.empty();
        if (resultList.size() > 0) {
            optionalVideoGame = Optional.of(resultList.get(0));
        }
        em.close();
        return optionalVideoGame;
    }

     public long count() {
        EntityManager em = _emf.createEntityManager();
        long countValue = em.createQuery("SELECT COUNT(g) FROM VideoGame g", Long.class).getSingleResult();
        em.close();
        return countValue;
    }

    public double sumAllPrices() {
        EntityManager em = _emf.createEntityManager();
        double sum = em.createQuery("SELECT SUM(g.price) FROM VideoGame g", Double.class).getSingleResult();
        em.close();
        return sum;
    }

    public double highestPriceByPlatform(GamingPlatform platform) {
        EntityManager em = _emf.createEntityManager();
        double highestPrice = em.createQuery("SELECT MAX(g.price) FROM VideoGame g WHERE g.platform = :platformParam", Double.class)
                .setParameter("platformParam", platform)
                .getSingleResult();
        em.close();
        return highestPrice;
    }

    public double lowestPriceByPlatform(GamingPlatform platform) {
        EntityManager em = _emf.createEntityManager();
        double lowestPrice = em.createQuery("SELECT MIN(g.price) FROM VideoGame g WHERE g.platform = :platformParam", Double.class)
                .setParameter("platformParam", platform)
                .getSingleResult();
        em.close();
        return lowestPrice;
    }

    public double averagePriceByPlatform(GamingPlatform platform) {
        EntityManager em = _emf.createEntityManager();
        double averagePrice = em.createQuery("SELECT AVG(g.price) FROM VideoGame g WHERE g.platform = :platformParam", Double.class)
                .setParameter("platformParam", platform)
                .getSingleResult();
        em.close();
        return averagePrice;
    }

    public Optional<VideoGame> highestPriceGame() {
        EntityManager em = _emf.createEntityManager();
        List<VideoGame> resultList = em.createQuery("SELECT g FROM VideoGame g WHERE g.price >= (SELECT MAX(g.price) FROM VideoGame g)", VideoGame.class)
                .getResultList();
        Optional<VideoGame> optionalVideoGame = Optional.empty();
        if (resultList.size() > 0) {
            optionalVideoGame = Optional.of(resultList.get(0));
        }
        em.close();
        return optionalVideoGame;
    }

    public Optional<VideoGame> lowestPriceGame() {
        EntityManager em = _emf.createEntityManager();
        List<VideoGame> resultList = em.createQuery("SELECT g FROM VideoGame g WHERE g.price <= (SELECT MIN(g.price) FROM VideoGame g)", VideoGame.class)
                .getResultList();
        Optional<VideoGame> optionalVideoGame = Optional.empty();
        if (resultList.size() > 0) {
            optionalVideoGame = Optional.of(resultList.get(0));
        }
        em.close();
        return optionalVideoGame;
    }

    public Map<GamingPlatform, List<VideoGame>> groupByPlatform() {
        EntityManager em = _emf.createEntityManager();
        Map<GamingPlatform, List<VideoGame>> resultMap = em.createQuery(
                "SELECT g FROM VideoGame g ORDER BY g.platform", VideoGame.class)
                .getResultStream()
                .collect(Collectors.groupingBy(VideoGame::getPlatform));

        em.close();
        return resultMap;
    }

}