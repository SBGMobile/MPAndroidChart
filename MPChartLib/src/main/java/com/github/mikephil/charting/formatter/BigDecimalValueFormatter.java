
package com.github.mikephil.charting.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BigDecimalValueFormatter {

    public String getFormattedValue(BigDecimal value, String currency) {
        return currency + " " + getDecimalFormatter().format(value);
    }

    private static DecimalFormat getDecimalFormatter() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormatter = new DecimalFormat("###,##0.00", decimalFormatSymbols);
        decimalFormatter.setMaximumFractionDigits(2);
        decimalFormatter.setGroupingSize(3);
        return decimalFormatter;
    }
}
