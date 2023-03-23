
package dmit2015.restclient;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

@JsonbPropertyOrder({
    "country",
    "sunrise",
    "sunset"
})
@Generated("jsonschema2pojo")
public class Sys {

    @JsonbProperty("country")
    private String country;
    @JsonbProperty("sunrise")
    private Integer sunrise;
    @JsonbProperty("sunset")
    private Integer sunset;
    @JsonbTransient
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonbProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonbProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonbProperty("sunrise")
    public Integer getSunrise() {
        return sunrise;
    }

    @JsonbProperty("sunrise")
    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    @JsonbProperty("sunset")
    public Integer getSunset() {
        return sunset;
    }

    @JsonbProperty("sunset")
    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
