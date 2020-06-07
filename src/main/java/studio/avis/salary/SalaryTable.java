package studio.avis.salary;

import studio.avis.salary.taxes.LocalIncomeTax;
import studio.avis.salary.taxes.RegularTax;

import java.util.ArrayList;
import java.util.List;

public class SalaryTable {

    public static final int TEN_THOUSANDS = 10000;

    private final List<Salary> salaries;

    public SalaryTable(List<Salary> salaries) {
        this.salaries = salaries;
    }

    public SalaryResult calculateSalaryTax(int value, SalaryTax... salaryTaxes) {
        return calculateSalaryTax(SalaryType.ANNUALLY, value, salaryTaxes);
    }

    public SalaryResult calculateSalaryTax(SalaryType type, int value, SalaryTax... salaryTaxes) {
        if(salaryTaxes.length == 0) {
            salaryTaxes = new SalaryTax[] { new RegularTax(), new LocalIncomeTax() };
        }
        if(type == SalaryType.MONTHLY) {
            value *= 12;
        }
        List<Salary> salaries = new ArrayList<>();
        for(Salary salary : this.salaries) {
            salaries.add(salary);
            if(salary.getSalaryRange().contains(value)) {
                break;
            }
        }
        if(salaries.isEmpty()) {
            throw new IllegalArgumentException("No match salaries for " + value + ".");
        }

        SalaryResult result = new SalaryResult();
        for(SalaryTax salaryTax : salaryTaxes) {
            int totalTax = salaryTax.calculate(value, salaries);
            result.addResult(salaryTax, totalTax);
        }
        return result;
    }

}
