package com.service.apiservice.utils;

import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StringUtil {
    public static String formatRequest(String input) {
        if (input != null && !StringUtils.isEmpty(input)) {
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append("<").append(input.replace(",", "><")).append(">").toString();
        }
        return null;
    }

    public static String formatBigDecimal(BigDecimal input) {
        if (input.compareTo(BigDecimal.ZERO) < 0) {
            input = input.abs();
        }
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        return new DecimalFormat("#,##0", decimalFormatSymbols).format(input);
    }

    public static BigDecimal formatBigDecimal(String input) {
        if (StringUtils.isEmpty(input)) {
            return BigDecimal.ZERO;
        }
        input = input.replace(",", "");
        return NumberUtils.parseNumber(input, BigDecimal.class);
    }

    public static String nvl(Object objInput, String strNullValue) {
        if (objInput == null) {
            return strNullValue;
        }
        return objInput.toString();
    }
}
