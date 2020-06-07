package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;
import studio.avis.salary.SalaryUtils;

import java.util.List;

public class HealthInsurance extends SalaryTax {

    public static final double HEALTH_INSURANCE_RATIO = 0.0667;

    private int salaryValue;
    private int tax;

    public HealthInsurance() {
        super("건강보험");
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        int monthlySalary = value / 12;
        salaryValue = value;
        tax = (int) Math.round((monthlySalary * HEALTH_INSURANCE_RATIO) / 2);
        return tax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        return "보수월액 x " + (HEALTH_INSURANCE_RATIO * 100) + "% / 2 = (" + abbr10ThousandsOr(salaryValue / 12) + "원 x " + (HEALTH_INSURANCE_RATIO * 100) + "%) / 2 = " + SalaryUtils.FORMAT.format(tax) + "원";
    }
}
