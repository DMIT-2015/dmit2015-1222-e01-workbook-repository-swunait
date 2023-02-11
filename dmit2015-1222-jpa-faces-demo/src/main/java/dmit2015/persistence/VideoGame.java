package dmit2015.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * This entity class maps to a database table.
 *
 * @author Sam Wu
 * @version 2022.05.12
 */
@Entity
@Table(name = "VideoGames")
@NoArgsConstructor
@Getter @Setter
@ToString
public class VideoGame implements Serializable {

    @Id
    private Long webCode;

    @NotBlank
    @Size(min = 3, max = 64)
    @Column(length = 64)
    private String title;

    @Enumerated(EnumType.STRING)
    private GamingPlatform platform;

    private double price;

    public VideoGame(String title, GamingPlatform platform, double price, Long webCode) {
        this.webCode = webCode;
        this.title = title;
        this.platform = platform;
        this.price = price;
    }
}