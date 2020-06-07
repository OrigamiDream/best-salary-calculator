package studio.avis.salary.taxes;

import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegularTax extends SalaryTax {

    static class SalaryInfo {
        public int prev;
        public int curr;
        public double rate;
    }

    private final List<Integer> taxes = new ArrayList<>();
    private final List<SalaryInfo> salaryInfos = new ArrayList<>();

    private int totalTax;

    public RegularTax() {
        super("세금");
    }

    @Override
    public int calculate(int value, List<Salary> salaries) {
        int tax = 0;
        int prevSalary = 0;
        for(Salary salary : salaries) {
            int prev = prevSalary;
            int curr;
            int salaryValue;
            if(salary.getSalaryRange().contains(value)) {
                salaryValue = value - prevSalary;

                curr = value;
            } else {
                int max = salary.getSalaryRange().getMax();
                salaryValue = max - prevSalary;
                prevSalary = max;

                curr = max;
            }

            SalaryInfo salaryInfo = new SalaryInfo();
            salaryInfo.prev = prev;
            salaryInfo.curr = curr;
            salaryInfo.rate = salary.getComprehensiveTaxRate();
            salaryInfos.add(salaryInfo);

            int result = (int) (salaryValue * salary.getComprehensiveTaxRate());
            tax += result;

            taxes.add(result);
        }
        totalTax = tax;
        return tax;
    }

    @Override
    public String toString(SalaryResult salaryResult) {
        StringBuilder output = new StringBuilder();
        List<StringBuilder> builders = new ArrayList<>();
        salaryInfos.forEach(salaryInfo -> {
            boolean bracket = salaryInfo.prev != 0;
            StringBuilder builder = new StringBuilder();
            if(bracket) {
                builder.append("(");
            }

            builder.append(abbr10ThousandsOr(salaryInfo.curr)).append("원");

            if(bracket) {
                builder.append(" - ").append(abbr10ThousandsOr(salaryInfo.prev)).append("원").append(")");
            }

            builder.append(" x ").append((int) (salaryInfo.rate * 100)).append("%");
            builders.add(builder);
        });
        output.append(builders.stream().map(StringBuilder::toString).collect(Collectors.joining(" + ")));
        output.append(" = ");
        output.append(taxes.stream().map(i -> abbr10ThousandsOr(i) + "원").collect(Collectors.joining(" + ")));
        output.append(" = ");
        output.append(abbr10ThousandsOr(totalTax)).append("원");
        return output.toString();
    }
}
