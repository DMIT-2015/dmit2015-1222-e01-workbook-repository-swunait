package dmit2015.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CanadianPersonalIncomeTaxManager {

    public List<String> readAllLinesFromCsvFile(String filePathString) {
        List<String> allLines = new ArrayList<>();

        try {
            Path csvPath = Path.of(Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource(filePathString)
                    .toURI());
            allLines = Files.readAllLines(csvPath);
            // Skip the first line using Streams
            allLines = allLines.stream().skip(1).toList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        return allLines;
    }
    public static void main(String[] args) {
        try {
            Path csvPath = Path.of(Thread.currentThread().getContextClassLoader().getResource("data/CanadianPersonalIncomeTaxRates.csv").toURI());
            // Use the `Files.readAllLines()` method to read all lines from a file as a List.
            System.out.println("I am reading all the lines from the csv file into a list of String.");
            List allLines = Files.readAllLines(csvPath);
            System.out.println("I am printing each line read from the list.");
            allLines.forEach(System.out::println);
            System.out.println("\n\n");


        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);

        }
    }
}
