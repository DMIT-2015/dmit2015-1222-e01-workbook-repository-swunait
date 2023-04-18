package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;
import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Properties;

/**
 * An ItemProcessor is executed after an ItemReader has finished.
 */
@Named
@Dependent
public class EnforcementZoneCentreItemProcessor implements ItemProcessor {

    @Inject
    private JobContext _jobContext;
    /**
     * Change the return type of this method to the type of object (JsonObject, String, etc) you are processing
     * Process one item returned from an ItemReader
     */
    @Override
    public String processItem(Object item) throws Exception {
        Properties jobParameters = _jobContext.getProperties();
        String tableName = jobParameters.getProperty("table_name");

        String line = (String) item;
        final String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String[] values = line.split(delimiter);

        EnforcementZoneCentre model = new EnforcementZoneCentre();
        model.setSiteId(Short.parseShort(values[0]));
        model.setLocationDescription(values[1]);
        model.setSpeedLimit(Short.parseShort(values[2]));
        model.setReasonCodes(values[3].replaceAll("[\"()]", ""));
        model.setLatitude(Double.valueOf(values[4]));
        model.setLongitude(Double.valueOf(values[5]));

        String sqlInsertStatement = String.format("INSERT INTO %s(site_id, location_description, speed_limit, reason_code,latitude,longitude) "
                        + " VALUES(%d, '%s',  %d, '%s', %f, %f);",
                tableName.toUpperCase(),
                model.getSiteId(),
                model.getLocationDescription(),
                model.getSpeedLimit(),
                model.getReasonCodes(),
                model.getLatitude(),
                model.getLongitude());

        return sqlInsertStatement;
    }

}