package dmit2015.faces;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.util.random.RandomGenerator;

@Named("currentDiceRollViewScopedView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
// class must implement Serializable
public class DiceRollViewScopedView implements Serializable {

    // Declare read/write properties (field + getter + setter) for each form field
    @Getter @Setter
    private Integer diceFaceValue;

    // Declare read only properties (field + getter) for data sources
    @Getter
    private String currentFaceValueImage;

    // Declare private fields for internal usage only objects
    private String[] _diceFaceValueImages = {
            "resources/img/dice/dice-shield.png",
            "resources/img/dice/dice-six-faces-one.png",
            "resources/img/dice/dice-six-faces-two.png",
            "resources/img/dice/dice-six-faces-three.png",
            "resources/img/dice/dice-six-faces-four.png",
            "resources/img/dice/dice-six-faces-five.png",
            "resources/img/dice/dice-six-faces-six.png",
    };

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onRollDice() {
        // Generate a random number between 1 and 6
        RandomGenerator rand  = RandomGenerator.getDefault();
        diceFaceValue = rand.nextInt(1, 7);
        // Use the faceValue of the dice to determine the image to display
        currentFaceValueImage = _diceFaceValueImages[diceFaceValue];
    }
    public void onClear() {
        // Set all fields to default values
        diceFaceValue = null;
        currentFaceValueImage = null;
    }
}