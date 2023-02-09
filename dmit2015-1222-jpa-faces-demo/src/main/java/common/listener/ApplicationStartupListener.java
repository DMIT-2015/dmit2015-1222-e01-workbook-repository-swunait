package common.listener;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.Logger;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

    private Logger _logger = Logger.getLogger(ApplicationStartupListener.class.getName());

    @Inject
    MovieRepository _movieRepository;

    public ApplicationStartupListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */

        // _movieRepository.deleteAll();

 //        try {
//            Movie movie1 = new Movie();
//            movie1.setTitle("When Harry Met Sally");
//            movie1.setReleaseDate(LocalDate.parse("1989-02-12"));
//            movie1.setGenre("Romantic Comedy");
//            movie1.setPrice(BigDecimal.valueOf(7.99));
//            movie1.setRating("G");
//            _movieRepository.add(movie1);
//
//            Movie movie2 = new Movie();
//            movie2.setTitle("Ghostbusters");
//            movie2.setReleaseDate(LocalDate.parse("1984-03-13"));
//            movie2.setGenre("Comedy");
//            movie2.setPrice(BigDecimal.valueOf(8.99));
//            movie2.setRating("PG");
//            _movieRepository.add(movie2);
//
//            Movie movie3 = new Movie();
//            movie3.setTitle("Ghostbusters 2");
//            movie3.setReleaseDate(LocalDate.parse("1986-02-23"));
//            movie3.setGenre("Comedy");
//            movie3.setPrice(BigDecimal.valueOf(9.99));
//            movie3.setRating("PG");
//            _movieRepository.add(movie3);
//
//            Movie movie4 = new Movie();
//            movie4.setTitle("Rio Bravo");
//            movie4.setReleaseDate(LocalDate.parse("1959-04-15"));
//            movie4.setGenre("Western");
//            movie4.setPrice(BigDecimal.valueOf(7.99));
//            movie4.setRating("PG-13");
//            _movieRepository.add(movie4);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }


       if (_movieRepository.count() == 0) {
            try {
                try (var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/csv/movies.csv"))) ) {
                    String line;
                    final var delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
                    // Skip the first line as it is containing column headings
                    reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        Optional<Movie> optionalMovie = Movie.parseCsv(line);
                        if (optionalMovie.isPresent()) {
                            Movie csvMovie = optionalMovie.get();
                            _movieRepository.add(csvMovie);
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}