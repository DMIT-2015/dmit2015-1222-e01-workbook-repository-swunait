package dmit2015.restclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * The baseUri for the web MpRestClient be set in either microprofile-config.properties (recommended)
 * or in this file using @RegisterRestClient(baseUri = "http://server/path").
 * <p>
 * To set the baseUri in microprofile-config.properties:
 * 1) Open src/main/resources/META-INF/microprofile-config.properties
 * 2) Add a key/value pair in the following format:
 * package-name.ClassName/mp-rest/url=baseUri
 * For example:
 * package-name:    dmit2015.restclient
 * ClassName:       TodoMpRestClient
 * baseUri:         http://localhost:8080/contextName
 * The key/value pair you need to add is:
 * <code>
 * dmit2015.restclient.TodoMpRestClient/mp-rest/url=http://localhost:8080/contextName
 * </code>
 * <p>
 * To use the client interface from an environment does support CDI, add @Inject and @RestClient before the field declaration such as:
 * <code>
 *
 * @Inject
 * @RestClient private TodoMpRestClient _todoMpRestClient;
 * </code>
 * <p>
 * To use the client interface from an environment that does not support CDI, you can use the RestClientBuilder class to programmatically build an instance as follows:
 * <code>
 * URI apiURI = new URI("http://sever/contextName");
 * TodoMpRestClient _todoMpRestClient = RestClientBuilder.newBuilder().baseUri(apiURi).build(TodoMpRestClient.class);
 * </code>
 */
@RequestScoped
@RegisterRestClient(baseUri = "http://localhost:8080/dmit2015-jaxrs-backend/restapi/TodoItems")
public interface TodoMpRestClient {

    @POST
    void create(Todo newTodo);

    @GET
    List<Todo> findAll();

    @GET
    @Path( "/{key}.json")
    Todo findById(@PathParam("key") Long key);

    @PUT
    @Path( "/{key}.json")
    Todo update(@PathParam("key") Long key, Todo updatedTodo);

    @DELETE
    @Path("/{key}.json")
    void delete(@PathParam("key") Long key);

}