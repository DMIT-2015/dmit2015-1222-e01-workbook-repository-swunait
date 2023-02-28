package dmit2015.faces;

import dmit2015.entity.Movie;
import dmit2015.persistence.MovieRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("currentMovieListView")
@ViewScoped
public class MovieListView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private MovieRepository _movieRepository;

    @Getter
    private List<Movie> movieList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            movieList = _movieRepository.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}