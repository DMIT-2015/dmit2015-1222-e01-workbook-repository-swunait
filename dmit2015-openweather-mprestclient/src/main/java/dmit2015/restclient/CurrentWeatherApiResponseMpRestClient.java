package dmit2015.restclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RequestScoped
@RegisterRestClient(baseUri = "https://api.openweathermap.org/data/2.5")
public interface CurrentWeatherApiResponseMpRestClient {

    @GET
    @Path("/weather")
    OpenWeather getCurrentWeatherByCity(
            @QueryParam("q") String city,
            @QueryParam("appid") String apiKey,
            @DefaultValue("metric") @QueryParam("units") String units   // The units of measurement: standard, metric, imperial
    );

    @GET
    @Path("/weather")
    OpenWeather getCurrentWeatherByGeographicalCoordinates(
            @QueryParam("lat") String latitude,
            @QueryParam("lon") String longitude,
            @QueryParam("appid") String apiKey,
            @QueryParam("units") String units
    );
}
