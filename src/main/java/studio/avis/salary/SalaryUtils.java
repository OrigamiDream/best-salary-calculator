package studio.avis.salary;

import studio.avis.salary.SalaryTable;

import java.text.DecimalFormat;

public final class SalaryUtils {

    public static final DecimalFormat FORMAT = new DecimalFormat("#,###.##");

    private SalaryUtils() {
    }

    public static String abbreviate10Thousands(int value) {
        return FORMAT.format(((double) value / (double) SalaryTable.TEN_THOUSANDS)) + "만";
    }

}
