package dmit2015.faces;

import org.omnifaces.util.Messages;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named      // allows you to access an object of this class with the name viewScopeCounterController using EL
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
            // class must implement Serializable
public class ViewScopeCounterController implements Serializable {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        counter += 1;
        Messages.addInfo(null,"Counter = {0}", counter);
        Messages.addInfo("counterResult", "Count Result = {0}", counter);
        return "";
    }
}