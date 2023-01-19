package dmit2015.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleParameterizedTest {

    @ParameterizedTest(name = "radius = {0}, expected area = {1} ")
    @CsvSource({"1.0, 3.14",
            "25.0, 1963.50",
            "100.0, 31415.93",
            "125.0, 49087.39",

    })
    void area_DifferentRadius_ReturnsCorrectResults(double radius, double expectedArea) {
// Arrange
        Circle circle1 = new Circle();
// Act
        circle1.setRadius(radius);
// Assert
        assertEquals(expectedArea, circle1.area(), 0.005);
    }

    @ParameterizedTest(name = "radius = {0}, expected diameter = {1} ")
    @CsvSource({
            "1.0, 2.0",
            "25.0, 50.0",
            "100.0, 200.0",
            "125.0, 250.0",
    })
    void diameter_DifferentRadius_ReturnsCorrectResults(double radius, double expectedDiameter) {
// Arrange
        Circle circle1 = new Circle();
// Act
        circle1.setRadius(radius);
// Assert
        assertEquals(expectedDiameter, circle1.diameter(), 0.05);
    }

    @ParameterizedTest(name = "radius = {0}, expected circumference = {1} ")
    @CsvSource({
            "1.0, 6.28",
            "25.0, 157.08",
            "100.0, 628.32",
            "125.0, 785.40",
    })
    void circumference_DifferentRadius_ReturnsCorrectResults(double radius, double
            expectedCircumference) {
// Arrange
        Circle circle1 = new Circle();
// Act
        circle1.setRadius(radius);
// Assert
        assertEquals(expectedCircumference, circle1.circumference(), 0.005);
    }
}