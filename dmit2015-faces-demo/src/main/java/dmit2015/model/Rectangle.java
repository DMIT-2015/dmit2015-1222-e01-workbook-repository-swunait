package dmit2015.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

/**
 * This class models a Rectangle shape.
 *
 * @author Sam Wu
 * @version 2023.01.20
 */
@Getter @Setter
@Named("currentRectangleRequestScopedView")
@RequestScoped
public class Rectangle {
    // Declare read/write properties (field + getter + setter) for each form field
    @Getter @Setter
    private Rectangle currentRectangle;

    // Declare read only properties (field + getter) for data sources

    // Declare private fields for internal usage only objects

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject
        currentRectangle = new Rectangle();
    }

    public void showArea() {
        Messages.addGlobalInfo("The area of this rectangle is {0} for length {1} and width {2}",
                currentRectangle.area(), currentRectangle.getLength(), currentRectangle.getWidth());
    }

    public void showDiagonal() {
        Messages.addGlobalInfo("The diagonal of this rectangle is {0}", currentRectangle.diagonal());
    }

    public void showPerimeter() {
        Messages.addGlobalInfo("The perimeter of this rectangle is {0}", currentRectangle.perimeter());
    }

    public void onClear() {
        // Set all fields to default values
        currentRectangle = new Rectangle();
    }
    /**
     * The length of this Rectangle
     */
    private double length;

    /**
     * The width of this Rectangle.
     */
    private double width;

//    public double getLength() {
//        return length;
//    }
//
//    public void setLength(double length) {
//        this.length = length;
//    }
//
//    public double getWidth() {
//        return width;
//    }
//
//    public void setWidth(double width) {
//        this.width = width;
//    }

    public Rectangle() {
        setLength(1);
        setWidth(1);
    }

    public Rectangle(double length, double width) {
//        this.length = length;
//        this.width = width;
        setLength(length);
        setWidth(width);
    }

    public double area() {
        return width * length;
    }

    public double perimeter() {
        return 2 * (length + width);
    }

    public double diagonal() {
        return Math.sqrt( (width * width) + (length * length) );
    }

}
