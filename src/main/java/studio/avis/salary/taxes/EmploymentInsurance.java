package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;
import studio.avis.salary.SalaryUtils;

import java.util.List;

public class EmploymentInsurance extends SalaryTax {

    public static final double EMPLOYMENT_INSURANCE_RATIO = 0.016;

    private int salaryValue;
    private int tax;

    public EmploymentInsurance() {
        super("고용보험(실업급여)");
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        int monthlySalary = value / 12;
        salaryValue = value;
        tax = (int) Math.round((monthlySalary * EMPLOYMENT_INSURANCE_RATIO) / 2);
        return tax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        return "보수월액 x " + (EMPLOYMENT_INSURANCE_RATIO * 100) + "% / 2 = (" + abbr10ThousandsOr(salaryValue / 12) + "원 x " + (EMPLOYMENT_INSURANCE_RATIO * 100) + "%) / 2 = " + SalaryUtils.FORMAT.format(tax) + "원";
    }
}
