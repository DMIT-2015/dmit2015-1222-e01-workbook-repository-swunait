package dmit2015.restclient;

import lombok.Data;

@Data
public class Todo {

    private Long id;
    private String name;
    private Boolean complete = false;

}

