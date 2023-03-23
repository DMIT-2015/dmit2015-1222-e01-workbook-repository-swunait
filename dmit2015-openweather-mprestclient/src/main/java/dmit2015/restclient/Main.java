
package dmit2015.restclient;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

@JsonbPropertyOrder({
    "temp",
    "feels_like",
    "temp_min",
    "temp_max",
    "pressure",
    "humidity",
    "sea_level",
    "grnd_level"
})
@Generated("jsonschema2pojo")
public class Main {

    @JsonbProperty("temp")
    private Double temp;
    @JsonbProperty("feels_like")
    private Double feelsLike;
    @JsonbProperty("temp_min")
    private Double tempMin;
    @JsonbProperty("temp_max")
    private Double tempMax;
    @JsonbProperty("pressure")
    private Integer pressure;
    @JsonbProperty("humidity")
    private Integer humidity;
    @JsonbProperty("sea_level")
    private Integer seaLevel;
    @JsonbProperty("grnd_level")
    private Integer grndLevel;
    @JsonbTransient
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonbProperty("temp")
    public Double getTemp() {
        return temp;
    }

    @JsonbProperty("temp")
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @JsonbProperty("feels_like")
    public Double getFeelsLike() {
        return feelsLike;
    }

    @JsonbProperty("feels_like")
    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    @JsonbProperty("temp_min")
    public Double getTempMin() {
        return tempMin;
    }

    @JsonbProperty("temp_min")
    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    @JsonbProperty("temp_max")
    public Double getTempMax() {
        return tempMax;
    }

    @JsonbProperty("temp_max")
    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    @JsonbProperty("pressure")
    public Integer getPressure() {
        return pressure;
    }

    @JsonbProperty("pressure")
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    @JsonbProperty("humidity")
    public Integer getHumidity() {
        return humidity;
    }

    @JsonbProperty("humidity")
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @JsonbProperty("sea_level")
    public Integer getSeaLevel() {
        return seaLevel;
    }

    @JsonbProperty("sea_level")
    public void setSeaLevel(Integer seaLevel) {
        this.seaLevel = seaLevel;
    }

    @JsonbProperty("grnd_level")
    public Integer getGrndLevel() {
        return grndLevel;
    }

    @JsonbProperty("grnd_level")
    public void setGrndLevel(Integer grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
