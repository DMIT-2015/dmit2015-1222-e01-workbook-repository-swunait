package dmit2015.faces;

import dmit2015.model.Circle;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("currentRectangleRequestScopedController")
@RequestScoped  // create this object for one HTTP request and destroy after the HTTP response has been sent
public class RectangleRequestScopedController {



}