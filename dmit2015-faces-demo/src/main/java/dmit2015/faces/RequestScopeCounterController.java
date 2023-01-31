package dmit2015.faces;

import org.omnifaces.util.Messages;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named  // allows you to access an object of this class with the name requestScopeCounterController using EL
@RequestScoped  // create this object for one HTTP request and destroy after the HTTP response has been sent
public class RequestScopeCounterController {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        counter += 1;
        Messages.addInfo(null,"RequestScoped Counter = {0}", counter);
        Messages.addInfo("counterResult", "RequestScoped Count Result = {0}", counter);
        return "";
    }
}