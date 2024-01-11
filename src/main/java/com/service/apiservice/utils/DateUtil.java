package com.service.apiservice.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_NOW_YYYY_MM_HH_MM_SS = "yyyyMMddHHmmss";
    public static Date parseDateYYYYMMDDHHMISS(String inputDate) {
        return parseDate(inputDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseDateYYYYMMDD(String inputDate) {
        return parseDate(inputDate, "yyyy-MM-dd");
    }

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String date2StringByPattern(Date date, String pattern) {
        if (date == null || DataUtil.isNullOrEmpty(pattern)) {
            return null;
        }

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    public static String toStringFromDate(Date date, String format) {
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }
    public static java.sql.Timestamp parseTimestamp(String timestamp) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date parseDate(String inputDate, String pattern) {
        try {
            if (inputDate == null || inputDate.isBlank()) {
                return null;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            return null;
        }

    }

    public static String formatDateYYYYMMDD_HHMISS(Date inputDate) {
        return formatDate(inputDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateYYYYMMDD_HHMISSMI(Date inputDate) {
        return formatDate(inputDate, "yyyy-MM-dd HH:mm:ss:sss");
    }

    public static String formatDateYYYYMMDD(Date inputDate) {
        return formatDate(inputDate, "yyyy-MM-dd");
    }

    public static String formatDate(Date inputDate, String pattern) {
        if (inputDate == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(inputDate);
    }

    public static Date formatSpecialDate(String inputDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(inputDate);
            return output.parse(output.format(date));
        } catch(ParseException e) {
            return null;
        }
    }
    /**
     * @param value Date
     * @return String
     */
    public static String date2ddMMyyyyString(Date value) {
        if (value != null) {
            SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
            return ddMMyyyy.format(value);
        }
        return "";
    }
    /*
     * @author: AnhTD
     * @since: 20/07/2021 - 00:36
     * @description: Compare date by pattern
     * */
    public static int compare(String first, String second, String pattern) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat(pattern);
        Date firstDate = parser.parse(first);
        Date secondDate = parser.parse(second);

        if (firstDate.after(secondDate)) {
            return 1;
        } else if (firstDate.equals(secondDate)) {
            return 0;
        } else {
            return -1;
        }
    }
    /**
     * @param value Date
     * @return Timestamp
     */
    public static Timestamp date2Timestamp(Date value) {
        if (value != null) {
            return new Timestamp(value.getTime());
        }
        return null;
    }
    public static Date string2Date(String value) {
        return DateUtil.string2DateByPattern(value, "dd/MM/yyyy");
    }

    public static Date string2DateByPattern(String value, String pattern) {
        if (!DataUtil.isNullOrEmpty(value)) {
            SimpleDateFormat dateTime = new SimpleDateFormat(pattern);
            dateTime.setLenient(false);
            try {
                return dateTime.parse(value);
            } catch (ParseException ex) {
                return null;
            }
        }
        return null;
    }



    /*
     * @author: AnhTD
     * @since: 20/07/2021 - 00:50
     * @description: Check date by pattern
     * */
    public static boolean isDate(String str, String datePattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        try {
            sdf.setLenient(false);
            Date date = sdf.parse(str);
            if (date == null) {
                return false;
            }
        } catch (Exception ex) {

            return false;
        }
        return true;
    }

    public static Date safeToDate(Timestamp timestamp) {
        if (timestamp == null) return null;
        return new Date(timestamp.getTime());
    }
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static String nowUp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW_YYYY_MM_HH_MM_SS);
        return sdf.format(cal.getTime());
    }
    public static java.sql.Date toSqlDate(Date date) {
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }
}
