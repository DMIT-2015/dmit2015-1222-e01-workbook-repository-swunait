
package dmit2015.restclient;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

@JsonbPropertyOrder({
    "lon",
    "lat"
})
@Generated("jsonschema2pojo")
public class Coord {

    @JsonbProperty("lon")
    private Double lon;
    @JsonbProperty("lat")
    private Double lat;
    @JsonbTransient
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonbProperty("lon")
    public Double getLon() {
        return lon;
    }

    @JsonbProperty("lon")
    public void setLon(Double lon) {
        this.lon = lon;
    }

    @JsonbProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonbProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
