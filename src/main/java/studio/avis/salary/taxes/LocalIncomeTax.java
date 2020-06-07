package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;

import java.util.List;

public class LocalIncomeTax extends SalaryTax {

    private int localIncomeTax;
    private int salaryValue;
    private double rate;

    public LocalIncomeTax() {
        super("지방소득세");
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        for(Salary salary : salaries) {
            if(salary.getSalaryRange().contains(value)) {
                localIncomeTax = (int) Math.round(value * salary.getComprehensiveTaxRate() * 0.1);
                salaryValue = value;
                rate = salary.getComprehensiveTaxRate();
                break;
            }
        }
        return localIncomeTax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        return abbr10ThousandsOr(salaryValue) + "원 x " + (int) (rate * 100) + "% x 10% = " + abbr10ThousandsOr(localIncomeTax) + "원";
    }
}
