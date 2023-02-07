package dmit2015.model;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CanadianPersonalIncomeTaxRateManager {

    @Getter
    private List<CanadianPersonalIncomeTaxRate> incomeTaxRates;

    public int[] availableTaxYears() {
        return incomeTaxRates.stream()
                .map(e -> e.getTaxYear())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .mapToInt(e -> e)
                .toArray();
    }

    public List<Integer> availableTaxYearList() {
        return incomeTaxRates.stream()
                .map(e -> e.getTaxYear())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();
    }


    public CanadianPersonalIncomeTaxRateManager() {
        incomeTaxRates = new ArrayList<>();
        try {
            try (var reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/data/csv/CanadianPersonalIncomeTaxRates.csv")))) {
                // Skip the first line as it contains headers
                reader.readLine();
                String line;
                while ( (line = reader.readLine()) != null) {
                    var optionalCanadianPersonalIncomeTaxRate = CanadianPersonalIncomeTaxRate.parseCsv(line);
                    if (optionalCanadianPersonalIncomeTaxRate.isPresent()) {
                        CanadianPersonalIncomeTaxRate currentCanadianPersonalIncomeTaxRate
                                = optionalCanadianPersonalIncomeTaxRate.orElseThrow();
                        incomeTaxRates.add(currentCanadianPersonalIncomeTaxRate);
                    }
                }

            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
