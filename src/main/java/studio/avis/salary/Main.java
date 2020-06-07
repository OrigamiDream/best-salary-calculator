package studio.avis.salary;

import studio.avis.juikit.Juikit;
import studio.avis.salary.Salary;
import studio.avis.salary.SalaryResult;
import studio.avis.salary.SalaryTable;
import studio.avis.salary.SalaryType;
import studio.avis.salary.taxes.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static studio.avis.salary.SalaryTable.TEN_THOUSANDS;

public class Main {

    static class Info {

        int index;
        int monthlySalary;
        int actualSalary;

    }

    private static final Map<Integer, Info> INFO_MAP = new HashMap<>();
    private static final AtomicReference<Info> INFO_REF = new AtomicReference<>();
    private static final AtomicInteger MAX_SALARY = new AtomicInteger();

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    public static void main(String[] args) {
        List<Salary> salaries = new ArrayList<>();
        salaries.add(new Salary(Range.of(0, 1200 * TEN_THOUSANDS), 0.06));
        salaries.add(new Salary(Range.of(1200 * TEN_THOUSANDS, 4600 * TEN_THOUSANDS), 0.15));
        salaries.add(new Salary(Range.of(4600 * TEN_THOUSANDS, 8800 * TEN_THOUSANDS), 0.24));
        salaries.add(new Salary(Range.of(8800 * TEN_THOUSANDS, 15000 * TEN_THOUSANDS), 0.35));
        salaries.add(new Salary(Range.of(15000 * TEN_THOUSANDS, 30000 * TEN_THOUSANDS), 0.38));
        salaries.add(new Salary(Range.of(30000 * TEN_THOUSANDS, 50000 * TEN_THOUSANDS), 0.40));
        salaries.add(new Salary(Range.of(50000 * TEN_THOUSANDS, Integer.MAX_VALUE), 0.42));
        SalaryTable salaryTable = new SalaryTable(salaries);

        {
            SalaryResult result;
            for(int i = 1; i < WIDTH; i++) {
                int monthlySalary = (int) ((double) i / 2 * 10000);
                result = salaryTable.calculateSalaryTax(SalaryType.MONTHLY, monthlySalary,
                        new RegularTax(),
                        new LocalIncomeTax(),
                        new CivilianPensionTax(),
                        new HealthInsurance(),
                        new LongTermCareInsurance(),
                        new EmploymentInsurance());

                int tax = 0;
                tax += result.getTotalTax(RegularTax.class) / 12;
                tax += result.getTotalTax(LocalIncomeTax.class) / 12;
                tax += result.getTotalTax(CivilianPensionTax.class);
                tax += result.getTotalTax(HealthInsurance.class);
                tax += result.getTotalTax(LongTermCareInsurance.class);
                tax += result.getTotalTax(EmploymentInsurance.class);

                int actualSalary = monthlySalary - tax;
                Info info = new Info();
                info.index = i - 1;
                info.monthlySalary = monthlySalary;
                info.actualSalary = actualSalary;
                INFO_MAP.put(i - 1, info);

                if(MAX_SALARY.get() < monthlySalary) {
                    MAX_SALARY.set(monthlySalary);
                }
            }
        }

        Juikit.createFrame()
                .size(WIDTH, HEIGHT + 20)
                .centerAlign()
                .title("연봉별 실수령금액 그래프")
                .closeOperation(WindowConstants.EXIT_ON_CLOSE)
                .antialiasing(true)
                .repaintInterval(10)
                .background(Color.BLACK)
                .painter((juikitView, graphics) -> {
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(0, 0, juikitView.width(), juikitView.height());

                    Info info = INFO_REF.get();

                    for(Map.Entry<Integer, Info> entry : INFO_MAP.entrySet()) {
                        int monthlySalaryHeight = HEIGHT - (int) (((double) entry.getValue().monthlySalary / MAX_SALARY.get()) * HEIGHT);
                        int actualSalaryHeight = HEIGHT - (int) (((double) entry.getValue().actualSalary / MAX_SALARY.get()) * HEIGHT);
                        graphics.setColor(Color.YELLOW);
                        graphics.drawLine(entry.getKey(), monthlySalaryHeight, entry.getKey(), monthlySalaryHeight);

                        if(info != null && entry.getKey() == info.index) {
                            graphics.setColor(Color.GREEN);
                        } else {
                            graphics.setColor(Color.WHITE);
                        }
                        graphics.drawLine(entry.getKey(), HEIGHT, entry.getKey(), actualSalaryHeight);
                    }

                    if(info != null) {
                        graphics.drawString("월 급여: " + SalaryUtils.FORMAT.format(info.monthlySalary) + "원", 20, 20);
                        graphics.drawString("실수령금액: " + SalaryUtils.FORMAT.format(info.actualSalary) + "원", 20, 40);
                    }
                })
                .mouseMoved((view, mouseEvent) -> {
                    int mouseX = mouseEvent.getX();

                    Info info = INFO_MAP.get(mouseX);
                    if(info == null) {
                        return;
                    }
                    INFO_REF.set(info);
                })
                .visibility(true);
    }

}
