package dmit2015.faces;

import dmit2015.restclient.Todo;
import dmit2015.restclient.TodoMpRestClient;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Named("currentTodoListView")
@ViewScoped
public class TodoListView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    @RestClient
    private TodoMpRestClient _todoMpRestClient;

    @Getter
    private List<Todo> todoList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            todoList = _todoMpRestClient.findAll();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}