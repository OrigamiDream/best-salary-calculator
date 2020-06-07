package studio.avis.salary;

import java.util.*;

public class SalaryResult {

    private final List<Map.Entry<SalaryTax, Integer>> results = new ArrayList<>();
    private boolean abbreviate10Thousands = true;

    public void setAbbreviate10Thousands(boolean abbreviate10Thousands) {
        this.abbreviate10Thousands = abbreviate10Thousands;
    }

    void addResult(SalaryTax salaryTax, int tax) {
        results.add(new AbstractMap.SimpleEntry<>(salaryTax, tax));
    }

    public <T extends SalaryTax> String toString(Class<T> taxType) {
        for(Map.Entry<SalaryTax, Integer> entry : results) {
            if(taxType.isInstance(entry.getKey())) {
                entry.getKey().setAbbreviate10Thousands(abbreviate10Thousands);
                return entry.getKey().getName() + ": " + entry.getKey().toString(this);
            }
        }
        return null;
    }

    public <T extends SalaryTax> int getTotalTax(Class<T> taxType) {
        for(Map.Entry<SalaryTax, Integer> entry: results) {
            if(taxType.isInstance(entry.getKey())) {
                return entry.getValue();
            }
        }
        return -1;
    }

}
