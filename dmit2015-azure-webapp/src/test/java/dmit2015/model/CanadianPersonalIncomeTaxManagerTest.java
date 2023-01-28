package dmit2015.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CanadianPersonalIncomeTaxManagerTest {

    @Test
    void readAllLinesFromCsvFile() {
        // Arrange an object to test
        var incomeTaxManager = new CanadianPersonalIncomeTaxManager();

        // Act on the object
        final String csvRelativePath = "data/CanadianPersonalIncomeTaxRates.csv";
        List<String> allLines = incomeTaxManager.readAllLinesFromCsvFile(csvRelativePath);

        // Assert - Compare expected with actual results
        // There should be 439 elements in the list
        assertEquals(439,allLines.size());
        // Check the first and last element of the file
        final String firstElement = "CAN,Canada,2023,1,0,53359,0.1500,0.00,0";
        final int firstElementIndex = 0;
        final String lastElement = "NU,Nunavut,2015,4,138586,,0.1150,9489.22,9489";
        final int lastElementIndex = allLines.size() - 1;
        assertEquals(firstElement, allLines.get(firstElementIndex));
        assertEquals(lastElement, allLines.get(lastElementIndex));


    }
}