package com.service.apiservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DataUtil {
    private DataUtil() {
    }

    public static String parseToString(Object value, String defaultVal) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static String parseToString(Object value) {
        if (isObjectNull(value)) {
            return null;
        }
        return parseToString(value, "");
    }

    public static boolean isObjectNull(Object obj) {
        return obj == null;
    }

    public static Integer parseToInt(String value, Integer defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Integer parseToInt(String value) {
        return parseToInt(value, null);
    }

    public static Date convertStringToDateNoTime(String value) {
        Date dateTime = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateTime = formatter.parse(value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return dateTime;
    }

    public static Long parseToLong(String value, Long defaultVal) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Long parseToLong(String value) {
        return parseToLong(value, null);
    }

    public static Double parseToDoble(String value, Double defaultVal) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return defaultVal;
        }
    }

    public static Double parseToDoble(String value) {
        return parseToDoble(value, null);
    }

    public static Integer safeToInt(Object obj1, Integer defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static int safeToInt(Object obj1) {
        return safeToInt(obj1, 0);
    }

    public static String safeToString(Object obj1, String defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }

        return obj1.toString().trim();
    }

    public static String safeToString(Object obj1) {
        return safeToString(obj1, "");
    }

    public static Boolean safeToBoolean(String data) {
        if (data == null) return false;
        return data.equals("true") || data.equals("1");
    }

    public static Date safeToDate(Object obj1) {
        if (obj1 == null) {
            return null;
        }
        return (Date) obj1;
    }

    public static Boolean safeToBoolean(Object data) {
        String toString = safeToString(data);
        if ("".equals(toString)) return false;
        return toString.equals("true") || toString.equals("1");
    }

    public static <T> void showLogSetter(Class<T> classTarget, String prefix) {
        try {
            Constructor<?> cons = classTarget.getConstructor();
            Object target = cons.newInstance();

            Field[] targetFields = target.getClass().getDeclaredFields();

            for (int i = 0; i < targetFields.length; i++) {
                Field targetFieldItem = targetFields[i];

                String dataType = targetFieldItem.getType().getName();
                String fieldName = targetFieldItem.getName();

                StringBuilder log = new StringBuilder(prefix + ".set");
                log.append(fieldName.substring(0, 1).toUpperCase());
                log.append(fieldName.substring(1));

                if (dataType.equalsIgnoreCase(String.class.getName())) {
                    log.append("(safeToString(");
                } else if (dataType.equalsIgnoreCase(Integer.class.getName())) {
                    log.append("(safeToInt(");
                } else if (dataType.equalsIgnoreCase(Date.class.getName())) {
                    log.append("(safeToDate((Timestamp) ");
                } else if (dataType.equalsIgnoreCase(Double.class.getName())) {
                    log.append("(safeToDouble(");
                } else if (dataType.equalsIgnoreCase(Long.class.getName())) {
                    log.append("(safeToLong(");
                }

                log.append("object[").append(i).append("]));");

                System.out.println(log.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> void showLogFieldElasticSearch(Class<T> classTarget) {
        try {
            Constructor<?> cons = classTarget.getConstructor();
            Object target = cons.newInstance();

            Field[] targetFields = target.getClass().getDeclaredFields();

            for (Field targetFieldItem : targetFields) {
                String dataType = targetFieldItem.getType().getName();
                String fieldName = targetFieldItem.getName();

                System.out.println("@Field(name = \"" + fieldName.toLowerCase() + "\")");
                System.out.println("private " + dataType.substring(dataType.lastIndexOf(".") + 1) + " " + fieldName + ";\n");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> void showLogSetter2(Class<T> classTarget, String prefix) {
        try {
            Constructor<?> cons = classTarget.getConstructor();
            Object target = cons.newInstance();

            Field[] targetFields = target.getClass().getDeclaredFields();

            for (int i = 0; i < targetFields.length; i++) {
                Field targetFieldItem = targetFields[i];

                String dataType = targetFieldItem.getType().getName();
                String fieldName = targetFieldItem.getName();

                StringBuilder log = new StringBuilder(prefix + ".set");
                log.append(fieldName.substring(0, 1).toUpperCase());
                log.append(fieldName.substring(1));

                if (dataType.equalsIgnoreCase(String.class.getName())) {
                    //log.append("(safeToString(");
                    log.append(" ((String) object[").append(i).append("]);");
                } else if (dataType.equalsIgnoreCase(Integer.class.getName()) || dataType.equalsIgnoreCase(int.class.getName())) {
                    //log.append("(safeToInt(");
                    log.append("(((BigDecimal) object[").append(i).append("]).intValue());");
                } else if (dataType.equalsIgnoreCase(Long.class.getName())) {
                    log.append("(((BigDecimal) object[").append(i).append("]).longValue());");
                }

                System.out.println(log.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> void showSetterBuilder(Class<T> classTarget) {
        try {
            Constructor<?> cons = classTarget.getConstructor();
            Object target = cons.newInstance();

            Field[] targetFields = target.getClass().getDeclaredFields();
            int totalFiled = 0;
            for (int i = 0; i < targetFields.length; i++) {
                Field targetFieldItem = targetFields[i];

                String dataType = targetFieldItem.getType().getName();
                String fieldName = targetFieldItem.getName();

                StringBuilder log = new StringBuilder();
                log.append("." + fieldName);

                if (dataType.equalsIgnoreCase(String.class.getName())) {
                    log.append("(safeToString(").append("item[").append(i).append("]))");
                } else if (dataType.equalsIgnoreCase(Integer.class.getName())) {
                    log.append("(safeToInt(").append("item[").append(i).append("], null))");
                } else if (dataType.equalsIgnoreCase(Date.class.getName())) {
                    log.append("(((Date) ");
                } else if (dataType.equalsIgnoreCase(Double.class.getName())) {
                    log.append("(safeToDouble(").append("item[").append(i).append("], null))");
                } else if (dataType.equalsIgnoreCase(Long.class.getName())) {
                    log.append("(safeToLong(").append("item[").append(i).append("], null))");
                } else if (dataType.equalsIgnoreCase(BigDecimal.class.getName())) {
                    log.append("(safeToBigDecimal(").append("item[").append(i).append("], null))");
                } else log.append("------------------ " + fieldName + " other data type------------------");

                System.out.println(log);
                totalFiled = i + 1;
            }
            System.out.println("** Total field: " + totalFiled + " **");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static boolean isNullOrEmpty(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Object[] collection) {
        return collection == null || collection.length == 0;
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static Long safeToLong(Object obj1, Long defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        if (obj1 instanceof BigDecimal) {
            return ((BigDecimal) obj1).longValue();
        }
        if (obj1 instanceof BigInteger) {
            return ((BigInteger) obj1).longValue();
        }
        if (obj1 instanceof Double) {
            return ((Double) obj1).longValue();
        }

        try {
            return Long.parseLong(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static BigDecimal safeToBigDecimal(Object obj, BigDecimal defaultValue) {
        if (obj == null) {
            return defaultValue;
        } else if (obj instanceof Number) {
            return new BigDecimal(((Number) obj).doubleValue());
        }
        return defaultValue;
    }

    public static BigDecimal safeToBigDecimal(Object obj) {
        return safeToBigDecimal(obj, new BigDecimal(0d));
    }

    /**
     * @param obj1 Object
     * @return Long
     */
    public static Long safeToLong(Object obj1) {
        return safeToLong(obj1, 0L);
    }

    public static Boolean listNullOrEmpty(List list) {
        if (list == null || list.size() == 0) return true;
        return false;
    }

    public static Double safeToDouble(Object obj1, Double defaultValue) {
        if (obj1 == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(obj1.toString());
        } catch (final NumberFormatException nfe) {
            log.error(nfe.getMessage(), nfe);
            return defaultValue;
        }
    }

    public static Double safeToDouble(Object obj1) {
        return safeToDouble(obj1, 0.0);
    }

    public static boolean isStringNullOrEmpty(Object obj1) {
        return obj1 == null || "".equals(obj1.toString().trim());
    }

    /*
     * @author: AnhTD
     * @since: 17/08/2021 - 15:07
     * @description: Convert xml string to class properties
     * */
    /*public static void convertXmlToClassProperties(String xml) {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(new StringReader(xml));
            List<AbstractDocument.Content> listContent = ((Element) (doc.getContent()).get(0)).getContent().stream()
                    .filter(item -> !(item instanceof Text))
                    .collect(Collectors.toList());

            listContent.forEach(content -> {
                List<Content> listContentItem = ((Element) content).getContent().stream()
                        .filter(item -> !(item instanceof Text))
                        .collect(Collectors.toList());

                String propertyName = "";
                String dataType = "";
                for (Content item : listContentItem) {
                    if (((Element) item).getAttributes().get(0).getValue().equals("name")) {
                        propertyName = item.getValue();
                    } else if (((Element) item).getAttributes().get(0).getValue().equals("dataType")) {
                        dataType = item.getValue();
                    }
                }

                StringBuilder result = new StringBuilder("private ");

                if (dataType.equalsIgnoreCase("string")) result.append("String ");
                else if (dataType.equalsIgnoreCase("date-time")) result.append("Date ");
                else if (dataType.equalsIgnoreCase("decimal")) result.append("Long ");
                else if (dataType.equalsIgnoreCase("Boolean")) result.append("Boolean ");

                result.append(propertyName.toLowerCase());
                System.out.println(result);
            });
        } catch (JDOMException e) {
            // handle JDOMException
        } catch (IOException e) {
            // handle IOException
        }
    }*/

    /*
     * @author: AnhTD
     * @since: 19/08/2021 - 09:41
     * @description: Convert to int
     * */
    public static Integer convertToInt(Boolean value) {
        if (value) return 1;
        return 0;
    }

    public static String dateToStringWithPattern(Date date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
//            logger.error(e.getMessage(), e);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        }
    }

    public static enum TYPE_OPTION_NOTIFY {
        NOW(1),
        TIME(2),
        ADMIN(3);
        private final int value;

        TYPE_OPTION_NOTIFY(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum TYPE_NOTIFY {
        REGISTER(1),
        PULL(2);

        private final int value;

        TYPE_NOTIFY(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum TARGET_NOTIFY {
        ALL(1),
        ADMIN(2),
        SALE(3),
        USER(4);

        private final int value;

        TARGET_NOTIFY(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum ORDER_STATUS {
        MOI(1),
        DANG_XAC_NHAN(2),
        DA_XAC_NHAN(3),
        THANH_CONG(4),
        THAT_BAI(5),
        TIEP_NHAN(6),
        DANG_THUC_HIEN(7),
        DA_HOAN_THANH(8),
        DA_THU_TIEN(9),
        TU_CHOI(10),
        TO_ADMINISTRATOR(20),
        // SALE
        TO_SALE(30),
        SALE_XAC_NHAN(31),
        SALE_UP_FILE_CHECK_IN(32),
        SALE_YEU_CAU_THANH_TOAN(33),
        SALE_DA_THU_TIEN(34),
        //STAFF
        TO_STAFF(40),
        STAFF_XAC_NHAN(41),
        STAFF_START(42),
        STAFF_END(43),
        STAFF_UP_FILE_CHECK_OUT(44);
        private final int value;

        ORDER_STATUS(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
