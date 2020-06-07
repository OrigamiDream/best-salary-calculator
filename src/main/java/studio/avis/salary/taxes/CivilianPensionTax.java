package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;
import studio.avis.salary.SalaryUtils;

import java.util.List;

public class CivilianPensionTax extends SalaryTax {

    public static final double CIVILIAN_PENSION_RATIO = 0.09;

    private int salaryValue;
    private int tax;

    public CivilianPensionTax() {
        super("국민연금");
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        int monthlySalary = value / 12;
        salaryValue = value;
        tax = (int) Math.round((monthlySalary * CIVILIAN_PENSION_RATIO) / 2);
        return tax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        return "기준소득월액 x " + (CIVILIAN_PENSION_RATIO * 100) + "% / 2 = (" + abbr10ThousandsOr(salaryValue / 12) + "원 x " + (CIVILIAN_PENSION_RATIO * 100) + "%) / 2 = " + SalaryUtils.FORMAT.format(tax) + "원";
    }
}
