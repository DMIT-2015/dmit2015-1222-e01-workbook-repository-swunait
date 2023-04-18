package dmit2015.batch;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;

import jakarta.batch.api.chunk.AbstractItemWriter;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * An ItemWriter is executed after an ItemProcessor has executed.
 */
@Named
@Dependent
public class EnforcementZoneCentreItemWriter extends AbstractItemWriter {

	@Inject
	private JobContext _jobContext;


    /**
     * Write a list of items returned from the ItemProcessor to a destination data source..
     */
    @Override
    public void writeItems(List<Object> items) throws Exception {
        Properties jobParameters = _jobContext.getProperties();
        String outputFile = jobParameters.getProperty("output_file");

        Path outputFilePath = Paths.get(outputFile);

        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            for (Object singleItem : items) {
                String sqlStatement = (String) singleItem;
                writer.write(sqlStatement);
                writer.newLine();
            }
        }

    }

}