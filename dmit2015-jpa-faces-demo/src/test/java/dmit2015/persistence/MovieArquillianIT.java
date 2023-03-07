package dmit2015.persistence;

import common.config.ApplicationConfig;
import common.listener.MovieStartupListener;
import dmit2015.entity.Movie;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
public class MovieArquillianIT { // The class must be declared as public

    static String mavenArtifactIdId;

    @Deployment
    public static WebArchive createDeployment() throws IOException, XmlPullParserException {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        mavenArtifactIdId = model.getArtifactId();
        final String archiveName = model.getArtifactId() + ".war";
        return ShrinkWrap.create(WebArchive.class, archiveName)
                .addAsLibraries(pomFile.resolve("org.codehaus.plexus:plexus-utils:3.4.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.214").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:11.2.3.jre17").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.8.0.0").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hibernate.orm:hibernate-spatial:6.1.7.Final").withTransitivity().asFile())
//                .addPackage("dmit2015.entity")
                .addClass(ApplicationConfig.class)
                .addClasses(MovieStartupListener.class)
                .addClasses(Movie.class, MovieRepository.class)
                // TODO: Add any additional classes or resource files required
                .addAsResource("data/csv/movies.csv")
                .addAsResource(new File("src/test/resources/data/csv/FederalIncomeTax_TestData.csv"),
                        "data/csv/FederalIncomeTax_TestData.csv")
                .addAsResource(new File("src/test/resources/data/csv/ProvincialIncomeTax_TestData.csv"),
                        "data/csv/ProvincialIncomeTax_TestData.csv")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/beans.xml");
    }

    @Inject
    private MovieRepository _movieRepository;

    @Resource
    private UserTransaction _beanManagedTransaction;

    @BeforeAll
    static void beforeAllTestMethod() {
        // code to execute before test methods are executed
    }

    @BeforeEach
    void beforeEachTestMethod() {
        // Code to execute before each method such as creating the test data
    }

    @AfterEach
    void afterEachTestMethod() {
        // code to execute after each test method such as deleteing the test data
    }


    @Order(1)
    @ParameterizedTest
    @CsvSource({
            "4,When Harry Met Sally,Rio Bravo"
    })
    void findAll(int expectedSize, String firstExpectedTitle, String lastExpectedTitle) {
        assertNotNull(_movieRepository);
        // Arrange
        List<Movie> movieList = _movieRepository.findAll();
        // Act
        // Assert
        assertEquals(expectedSize, movieList.size());

        // Get the first entity and compare with expected results
        Movie firstMovie = movieList.get(0);
        assertEquals(firstExpectedTitle, firstMovie.getTitle());

        // Get the last entity and compare with expected results
        Movie lastMovie = movieList.get( movieList.size() - 1);
        assertEquals(lastExpectedTitle, lastMovie.getTitle());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({
            "2,Ghostbusters,1984-03-13",
            "3,Ghostbusters 2,1986-02-23"
    })
    void shouldFineOne(Long movieId, String expectedTitle, LocalDate expectedReleaseDate) {
        // Arrange and Act
        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.orElseThrow();

        // Assert
        assertNotNull(existingMovie);
        assertEquals(expectedTitle, existingMovie.getTitle());
        assertEquals(expectedReleaseDate, existingMovie.getReleaseDate());
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource({
            "Star Wars 99,2099-09-09,Action,99.99,G",
            "Star Trek 98,2088-08-08,Advendure,88.88,PG",
    })
    void shouldCreate(String title, LocalDate releaseDate, String genre, BigDecimal price, String rating) throws SystemException, NotSupportedException {
        _beanManagedTransaction.begin();

        // Arrange
        Movie newMovie = new Movie();
        newMovie.setTitle(title);
        newMovie.setReleaseDate(releaseDate);
        newMovie.setGenre(genre);
        newMovie.setPrice(price);
        newMovie.setRating(rating);

        // Act
        _movieRepository.add(newMovie);

        // Assert
        assertTrue( newMovie.getId() > 0 );

        _beanManagedTransaction.rollback();
    }

//    @Order(4)
//    @ParameterizedTest
//    @CsvSource({
//            "PrimaryKey, Property1Value, Property2Value, Property3Value",
//            "PrimaryKey, Property1Value, Property2Value, Property3Value",
//    })
//    void update_existingEntity_shouldPass(Long movieId, String property1, String property2, String property3) throws SystemException, NotSupportedException {
//        _beanManagedTransaction.begin();
//
//        // Arrange
//        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
//        assertTrue(optionalMovie.isPresent());
//        Movie existingMovie = optionalMovie.orElseThrow();
//        assertNotNull(existingMovie);
//
//        // Act
//        // existingMovie.setPropertyName("Property Value");
//
//        //Movie updatedMovie = _movieRepository.update(movieId, existingMovie);
//        // Assert
//        // assertEquals(existingMovie.getPropertyName(), updatedMovie.getPropertyName());
//
//        _beanManagedTransaction.rollback();
//    }
//
//    @Order(5)
//    @ParameterizedTest
//    @CsvSource({
//            "primaryKey1",
//            "primaryKey2",
//    })
//    void deleteById_existingId_shouldPass(Long movieId) throws SystemException, NotSupportedException {
//        _beanManagedTransaction.begin();
//
//        // Arrange and Act
//        _movieRepository.deleteById(movieId);
//
//        // Assert
//        assertTrue(_movieRepository.findById(movieId).isEmpty());
//
//        _beanManagedTransaction.rollback();
//    }
//
//    @Order(6)
//    @ParameterizedTest
//    @CsvSource({
//            "primaryKey",
//            "primaryKey"
//    })
//    void findById_nonExistingId_shouldFail(Long movieId) {
//        // Arrange and Act
//        Optional<Movie> optionalMovie = _movieRepository.findById(movieId);
//
//        // Assert
//        assertFalse(optionalMovie.isPresent());
//
//    }
//
//
//    @Order(7)
//    @ParameterizedTest
//    @CsvSource({
//            "Invalid Property1Value, Property2Value, Property3Value, ExpectedExceptionMessage",
//            "Property1Value, Invalid Property2Value, Property3Value, ExpectedExceptionMessage",
//    })
//    void create_beanValidation_shouldFail(String property1, String property2, String property3, String expectedExceptionMessage) {
//        // Arrange
//        Movie newMovie = new Movie();
//        // newMovie.setProperty1(property1);
//        // newMovie.setProperty2(property2);
//        // newMovie.setProperty3(property3);
//
//        try {
//            // Act
//            _movieRepository.add(newMovie);
//            fail("An bean validation constraint should have been thrown");
//        } catch (Exception ex) {
//            // Assert
////            assertEquals(expectedExceptionMessage, ex.getMessage());
//            assertThat(ex.getMessage(), containsString(expectedExceptionMessage));
//        }
//    }

}