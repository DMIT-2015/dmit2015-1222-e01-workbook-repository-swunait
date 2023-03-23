
package dmit2015.restclient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

@JsonbPropertyOrder({
    "coord",
    "weather",
    "base",
    "main",
    "visibility",
    "wind",
    "clouds",
    "dt",
    "sys",
    "timezone",
    "id",
    "name",
    "cod"
})
@Generated("jsonschema2pojo")
public class OpenWeather {

    @JsonbProperty("coord")
    private Coord coord;
    @JsonbProperty("weather")
    private List<Weather> weather;
    @JsonbProperty("base")
    private String base;
    @JsonbProperty("main")
    private Main main;
    @JsonbProperty("visibility")
    private Integer visibility;
    @JsonbProperty("wind")
    private Wind wind;
    @JsonbProperty("clouds")
    private Clouds clouds;
    @JsonbProperty("dt")
    private Integer dt;
    @JsonbProperty("sys")
    private Sys sys;
    @JsonbProperty("timezone")
    private Integer timezone;
    @JsonbProperty("id")
    private Integer id;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("cod")
    private Integer cod;
    @JsonbTransient
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonbProperty("coord")
    public Coord getCoord() {
        return coord;
    }

    @JsonbProperty("coord")
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @JsonbProperty("weather")
    public List<Weather> getWeather() {
        return weather;
    }

    @JsonbProperty("weather")
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @JsonbProperty("base")
    public String getBase() {
        return base;
    }

    @JsonbProperty("base")
    public void setBase(String base) {
        this.base = base;
    }

    @JsonbProperty("main")
    public Main getMain() {
        return main;
    }

    @JsonbProperty("main")
    public void setMain(Main main) {
        this.main = main;
    }

    @JsonbProperty("visibility")
    public Integer getVisibility() {
        return visibility;
    }

    @JsonbProperty("visibility")
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @JsonbProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonbProperty("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @JsonbProperty("clouds")
    public Clouds getClouds() {
        return clouds;
    }

    @JsonbProperty("clouds")
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    @JsonbProperty("dt")
    public Integer getDt() {
        return dt;
    }

    @JsonbProperty("dt")
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    @JsonbProperty("sys")
    public Sys getSys() {
        return sys;
    }

    @JsonbProperty("sys")
    public void setSys(Sys sys) {
        this.sys = sys;
    }

    @JsonbProperty("timezone")
    public Integer getTimezone() {
        return timezone;
    }

    @JsonbProperty("timezone")
    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    @JsonbProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonbProperty("cod")
    public Integer getCod() {
        return cod;
    }

    @JsonbProperty("cod")
    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
