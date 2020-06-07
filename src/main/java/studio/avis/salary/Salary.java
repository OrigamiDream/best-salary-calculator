package studio.avis.salary;

public class Salary {

    private final Range<Integer> salaryRange;
    // 0-1 rate
    private final double comprehensiveTaxRate;

    public Salary(Range<Integer> salaryRange, double comprehensiveTaxRate) {
        this.salaryRange = salaryRange;
        this.comprehensiveTaxRate = comprehensiveTaxRate;
    }

    public Range<Integer> getSalaryRange() {
        return salaryRange;
    }

    public double getComprehensiveTaxRate() {
        return comprehensiveTaxRate;
    }
}
