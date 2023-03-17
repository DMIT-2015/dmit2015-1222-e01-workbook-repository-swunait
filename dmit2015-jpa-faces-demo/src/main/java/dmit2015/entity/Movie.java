package dmit2015.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The persistent class for the movies database table.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity                     // This class is map to database table with the same name as the class name
@Table(name = "movies")     // This entity class maps to a database table named movies
public class Movie implements Serializable {

    @Column(length=32, nullable = false)
    private String username;

    @Id                 // This is the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // This primary key field is generated by the database
    private Long id;

    @Column(length = 60, nullable = false)
    @NotBlank(message = "The Title field is required.")
    @Size(min = 3, max = 60, message = "The field Title must be a string with a minimum length of {min} and a maximum length of {max}.")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "The Release Date field is required")
    private LocalDate releaseDate;

    @DecimalMin(value = "1.00",message = "The price must be a number between 0.00 and 100.00.")
    @DecimalMax(value = "100.00",message = "The price must be a number between 0.00 and 100.00.")
    private BigDecimal price;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "The field Genre is required.")
    @Pattern(regexp = "^[A-Z]+[a-zA-Z\\s]*$",  // Must only use letters.
                                            // The first letter is required to be uppercase. White space, numbers, and special characters are not allowed.
            message = "The field Genre must match the regular expression '^[A-Z]+[a-zA-Z\\s]*$'.")
    private String genre;

    @Column(nullable = false, length = 5)
    @NotBlank(message = "The field Rating is required.")
    @Pattern(regexp = "^[A-Z]+[a-zA-Z0-9\"\"'\\s-]*$", // The first character can be an uppercase letter
                                                        // Allows special characters and numbers in subsequent spaces. PG-13 is valid but fails for a Genre
            message = "The field Rating must match the regular expression '^[A-Z]+[a-zA-Z0-9\"\"'\\s-]*$'.")
    private String rating;      // G, PG, PG-13, R, NC-17


    @Version
    private Integer version;

    @Column(nullable = false)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    private void beforePersist() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate() {
        updateTime = LocalDateTime.now();
    }

    public static Optional<Movie> parseCsv(String line) {
        Optional<Movie> optionalMovie = Optional.empty();
        final var DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
        /**
         * The order of the columns are:
         * 0 - title
         * 1 - releaseDate
         * 2 - genre
         * 3 - price
         * 4 - rating
         */
        if (tokens.length == 5) {
            Movie parsedMovie = new Movie();

            try {
                String title = tokens[0].replaceAll("\"","");
                LocalDate releaseDate = LocalDate.parse(tokens[1]);
                String genre = tokens[2].replaceAll("\"","");
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(tokens[3]));
                String rating = tokens[4].replaceAll("\"","");

                parsedMovie.setTitle(title);
                parsedMovie.setReleaseDate(releaseDate);
                parsedMovie.setGenre(genre);
                parsedMovie.setPrice(price);
                parsedMovie.setRating(rating);

                optionalMovie = Optional.of(parsedMovie);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return optionalMovie;
    }
}