package studio.avis.salary;

import java.util.List;

public abstract class SalaryTax {

    protected final String name;
    protected boolean abbreviate10Thousands = true;

    public SalaryTax(String name) {
        this.name = name;
    }

    public void setAbbreviate10Thousands(boolean abbreviate10Thousands) {
        this.abbreviate10Thousands = abbreviate10Thousands;
    }

    public String getName() {
        return name;
    }

    public abstract int calculate(int value, List<Salary> salaries);

    protected String abbr10ThousandsOr(int value) {
        if(abbreviate10Thousands) {
            return SalaryUtils.abbreviate10Thousands(value);
        } else {
            return SalaryUtils.FORMAT.format(value);
        }
    }

    public abstract String toString(SalaryResult salaryResult);
}
