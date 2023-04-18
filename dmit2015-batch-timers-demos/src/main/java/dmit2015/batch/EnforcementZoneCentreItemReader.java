package dmit2015.batch;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.BufferedReader;
import java.nio.file.Paths;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;

/**
 * The sequence for a batch chunk step are: ItemReader --> ItemProcessor --> ItemWriter
 */
@Named
@Dependent
public class EnforcementZoneCentreItemReader extends AbstractItemReader {

    private BufferedReader _reader;

    @Inject
    @BatchProperty(name = "input_file")
    private String inputFile;

    @Inject
    @BatchProperty(name = "max_results")
    private int _maxResults;

    private int _resultCount;

    /**
     * The open method is used to open a data source for reading.
     */
    @Override
    public void open(Serializable checkpoint) throws Exception {
        super.open(checkpoint);

        _reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile()));
        // Skip the first line as it contains field name headers
        _reader.readLine();

        _resultCount = 0;
    }

    /**
     * Read from the data source one item at a time.
     * Return null to trigger the end of the file.
     */
    @Override
    public Object readItem() throws Exception {
        try {
            String line = _reader.readLine();
            _resultCount += 1;
            if ((_maxResults == 0) || (_resultCount <= _maxResults)) {
                return line;
            } else {
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}