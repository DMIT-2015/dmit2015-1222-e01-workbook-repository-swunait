package dmit2015.faces;

import dmit2015.entity.Movie;
import dmit2015.persistence.MovieRepository;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

@Named("currentMovieDeleteView")
@ViewScoped
public class MovieDeleteView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private MovieRepository _movieRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private Movie existingMovie;

    @PostConstruct
    public void init() {
        Optional<Movie> optionalMovie = _movieRepository.findById(editId);
        if (optionalMovie.isPresent()) {
            existingMovie = optionalMovie.get();
        } else {
            Faces.redirect(Faces.getRequestURI().substring(0, Faces.getRequestURI().lastIndexOf("/")) + "/index.xhtml");
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _movieRepository.delete(existingMovie);
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}