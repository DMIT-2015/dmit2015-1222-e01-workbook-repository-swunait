package common.listener;

import dmit2015.entity.Movie;
import dmit2015.persistence.MovieRepository;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

@WebListener
public class MovieStartupListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Inject
    private MovieRepository _movieRepository;

    public MovieStartupListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        if (_movieRepository.count() == 0) {
            try {
                try (var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/csv/movies.csv")))) {
                    String line;
                    final var delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
                    // Skip the first line as it is containing column headings
                    reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        Optional<Movie> optionalMovie = Movie.parseCsv(line);
                        if (optionalMovie.isPresent()) {
                            Movie csvMovie = optionalMovie.orElseThrow();
                            _movieRepository.add(csvMovie);
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
