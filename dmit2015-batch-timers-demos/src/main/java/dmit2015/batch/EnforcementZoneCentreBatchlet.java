package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;
import dmit2015.repository.EnforcementZoneCentreRepository;
import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.context.JobContext;
import jakarta.batch.runtime.BatchStatus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Batchlets are task oriented step that is called once.
 * It either succeeds or fails. If it fails, it CAN be restarted and it runs again.
 */
@Named
@Dependent
public class EnforcementZoneCentreBatchlet extends AbstractBatchlet {

    @Inject
    private JobContext _jobContext;

    @Inject
    private Logger _logger;

    @Inject
    private EnforcementZoneCentreRepository _repository;

    /**
     * Perform a task and return "COMPLETED" if the job has successfully completed
     * otherwise return "FAILED" to indicate the job failed to complete.
     */
    @Transactional
    @Override
    public String process() throws Exception {
        String batchStatus = BatchStatus.COMPLETED.toString();

        Properties jobParameters = _jobContext.getProperties();
        String inputFile = jobParameters.getProperty("input_file");

        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile()))) {
            String lineText;
            final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            // We can skip the first line as it contains column headings
            reader.readLine();
            while ((lineText = reader.readLine()) != null) {
                String[] tokens = lineText.split(delimiter, -1);

                EnforcementZoneCentre currentEnforcementZoneCentre = new EnforcementZoneCentre();
                // TODO: Write the code to copy from the values array to properties of the entity object
                currentEnforcementZoneCentre.setSiteId(Short.parseShort(tokens[0]));
                currentEnforcementZoneCentre.setLocationDescription(tokens[1]);
                currentEnforcementZoneCentre.setSpeedLimit(Short.parseShort(tokens[2]));
                currentEnforcementZoneCentre.setReasonCodes(tokens[3].replaceAll("[\"()]", ""));
                currentEnforcementZoneCentre.setLatitude(Double.valueOf(tokens[4]));
                currentEnforcementZoneCentre.setLongitude(Double.valueOf(tokens[5]));

                String wktText = "POINT" + tokens[6].replaceAll("[\",]","");
                Point geoLocation = (org.locationtech.jts.geom.Point) new WKTReader().read(wktText);
                currentEnforcementZoneCentre.setGeoLocation(geoLocation);

//                Point geoLocation = new GeometryFactory().createPoint(
//                        new Coordinate(
//                                currentEnforcementZoneCentre.getLongitude(), currentEnforcementZoneCentre.getLatitude()
//                        )
//                );
//                currentEnforcementZoneCentre.setGeoLocation(geoLocation)

                _repository.add(currentEnforcementZoneCentre);
            }
        } catch (Exception ex) {
            batchStatus = BatchStatus.FAILED.toString();
            ex.printStackTrace();
            _logger.fine("Batchlet failed with exception: " + ex.getMessage());
        }

        return batchStatus;
    }
}