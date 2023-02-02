package dmit2015.model;

/**
 * This class models a Circle shape.
 *
 * @author Sam Wu
 * @version 2023-01-20
 */

public class Circle {

    /**
     * The radius of this circle.
     */
    private double radius;

    /**
     * Returns the radius of this circle
     * @return the radius of this circle
     */
    public double getRadius() {
        return radius;
    }

    // CHECKED  exception example
//    public void setRadius(double radius) throws Exception {
//        // Validate the new value assigned to the Radius
//        if (radius <= 0)
//        {
//            throw new Exception("Radius must be a positive non-zero number.");
//        }
//        this.radius = radius;
//    }

    // RuntimeException example

    /**
     * Change the radius of this circle
     * @param radius the new radius of this circle
     */
    public void setRadius(double radius) {
        // Validate the new value assigned to the Radius
        if (radius <= 0)
        {
            throw new RuntimeException("Radius must be a positive non-zero number.");
        }
        this.radius = radius;
    }

    /**
     * Creates a circle with radius of 1
     */
    public Circle() {
        this.radius = 1;
    }

    /**
     * Creates a circle with a specific radius
     * @param radius the new radius of this circle
     */
    public Circle(double radius) {
        this.radius = radius;
    }

    /**
     * Compute and return the area of this circle
     * @return the area of this circle
     */
    public double area()
    {
        return Math.PI * radius * radius;
    }

    /**
     * Compute and return the diameter of this circle.
     *
     * @return the diameter of this circle
     */
    public double diameter()
    {
        return 2 * radius;
    }

    /**
     * Compute and return the circumference of this circle
     * @return the circumference of this circle
     */
    public double circumference() { return 2 * Math.PI * radius; }

    /**
     * Console application to demonstrate usage of Circle object.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Circle circle1 = new Circle();
// The radius of circle1 should be 1,
// area should be 3.14159
// perimeter should be 6.28
        System.out.println("The radius of circle1 is " + circle1.getRadius());
        System.out.printf("The radius of circle1 is %s\n", circle1.getRadius());
        System.out.printf("The area of circle1 is %.5f\n", circle1.area());
        System.out.printf("The diameter of circle1 is %.2f\n", circle1.diameter());

        try
        {
            // Change the radius of circle1 to 5
            circle1.setRadius(5);
// The radius of circle1 should be 5,
// area should be 78.53982
// perimeter should be 31.41593
            System.out.printf("The radius of circle1 is %s\n", circle1.getRadius());
            System.out.printf("The area of circle1 is %.5f\n", circle1.area());
            System.out.printf("The perimeter of circle1 is %.5f\n", circle1.diameter());

            // It should throw an exception
            // Change the radius of cirlce1 to -25
            circle1.setRadius(-25);
            System.out.println("A exception should have been thrown");
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
