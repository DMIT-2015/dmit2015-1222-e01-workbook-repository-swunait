package dmit2015.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class models a Rectangle shape.
 *
 * @author Sam Wu
 * @version 2023.01.20
 */
@Getter @Setter
public class Rectangle {

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
