package dmit2015.restclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.LinkedHashMap;

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
@RegisterRestClient(baseUri = "https://dmit2015-1222-swu-demos-default-rtdb.firebaseio.com")
public interface TodoMpRestClient {

    final String DOCUMENT_URL = "/Todo";

    @POST
    @Path(DOCUMENT_URL + ".json")
    JsonObject create(Todo newTodo);

    @GET
    @Path(DOCUMENT_URL + ".json")
    LinkedHashMap<String, Todo> findAll();

    @GET
    @Path(DOCUMENT_URL + "/{key}.json")
    Todo findByKey(@PathParam("key") String key);

    @PUT
    @Path(DOCUMENT_URL + "/{key}.json")
    Todo update(@PathParam("key") String key, Todo updatedTodo);

    @DELETE
    @Path(DOCUMENT_URL + "/{key}.json")
    void delete(@PathParam("key") String key);

}