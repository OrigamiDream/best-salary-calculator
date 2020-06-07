package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;
import studio.avis.salary.SalaryUtils;

import java.util.List;

public class LongTermCareInsurance extends SalaryTax {

    public static final double LONG_TERM_CASE_INSURANCE_RATIO = 0.1025;

    private final HealthInsurance healthInsurance;
    private int tax;

    public LongTermCareInsurance() {
        super("장기요양보험");

        healthInsurance = new HealthInsurance();
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        tax = (int) Math.round(healthInsurance.calculate(value, salaries) * LONG_TERM_CASE_INSURANCE_RATIO);
        return tax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        return "건강보험료 x " + (LONG_TERM_CASE_INSURANCE_RATIO * 100) + "% = " + SalaryUtils.FORMAT.format(salaryResult.getTotalTax(HealthInsurance.class)) + "원 x " + (LONG_TERM_CASE_INSURANCE_RATIO * 100) + "% = " + SalaryUtils.FORMAT.format(tax) + "원";
    }
}
