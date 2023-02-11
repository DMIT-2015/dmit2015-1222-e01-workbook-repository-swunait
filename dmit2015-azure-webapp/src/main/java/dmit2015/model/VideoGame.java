package dmit2015.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * This data class contains properties of a VideoGame.
 *
 * To use Project Lombok annotations,  add the following dependency to the `pom.xml` file of a maven project.
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
    <scope>provided</scope>
</dependency>
 *
 * @author Sam Wu
 * @version 2022.05.12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoGame {

    private String title;
    private GamingPlatform platform;
    private double price;
    private Long webCode;


    public static final String CSV_HEADER = "Title, Platform, Price, WebCode\n";
    public String toCsv() {
        return String.format("%s,%s,%.2f,%d",title, platform, price, webCode);
    }

    public static Optional<VideoGame> parseCsv(String line) {
        Optional<VideoGame> optionalVideoGame = Optional.empty();

        final String DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] tokens = line.split(DELIMITER, -1);  // The -1 limit allows for any number of fields and not discard trailing empty fields
        if (tokens.length == 4) {
            VideoGame parsedVideoGame = new VideoGame();
            try {
                parsedVideoGame.setTitle(tokens[0]);
                parsedVideoGame.setPlatform(GamingPlatform.valueOf(tokens[1]));
                parsedVideoGame.setPrice(tokens[2].isBlank() ? 0 : Double.parseDouble(tokens[2]));
                parsedVideoGame.setWebCode(tokens[3].isBlank() ? null : Long.parseLong(tokens[3]));
                optionalVideoGame = Optional.of(parsedVideoGame);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return optionalVideoGame;
    }

}