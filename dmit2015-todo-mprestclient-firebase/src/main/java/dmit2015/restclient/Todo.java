package dmit2015.restclient;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Todo {

    private String key;
    private String task;
    private Boolean important = false;
    private Boolean completed = false;
    // private LocalDate dueDate;
    // private LocalDateTime remindDateTime;

}