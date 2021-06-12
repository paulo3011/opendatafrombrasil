package application.batch.utils;

import org.apache.spark.sql.Row;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * CNPJ dataset utils class.
 */
@SuppressWarnings("unused")
public class CnpjUtils {
    /**
     * Returns the local date for the column value using CNPJ dataset date format.
     * @param dateAsString The Row with values
     * @param fieldName Field name
     * @return Return null if no date were found or the LocalDate found
     */
    public static LocalDate getLocalDate(String dateAsString, String fieldName) {
        /*
        Known issues:

        file: K3241.K03200Y0.D10410.ESTABELE
        line: "30005475";"0001";"31";"1";"";"2";"0";"0";"";"";"20180322";"6204000";"6209100,7490104";"AVENIDA";"PAULISTA";"2202";"CONJ  54-B";"BELA VISTA";"01310300";"SP";"7107";"11";"59085410";"";"";"";"";"CEFISCO@UOL.COM.BR";"";""
        wrong date value: 0

        line: "36451356","0001","99","1","BROCADUS DELIVERY","2","20200221","0","","","20200221","5611203","","RUA","RIO JAPURA","169","LETRA A","PERPETUO SOCORRO","68905540","AP","605","96","84110818","","","","","DENISEBALIEIRO@HOTMAIL.COM","",""
        empty date value: "" (SpecialSituationDate - last column)

        line: "18825426";"0001";"40";"1";"ALAMBIQUE SANTO ANTONIO";"8";"20150209";"73";"";"";"4100813";"5611204";"";"RUA";"DEOLINDO PERIM";"79";"";"ITAPUA";"29101811";"ES";"5703";"27";"98921990";"27";"";"";"";"JFJUNCAL@GMAIL.COM";"";""
        wrong date value: 4100813
         */
        LocalDate date = null;

        try {
            //"yyyyMMdd"
            dateAsString = fixStringValues(dateAsString);
            if (dateAsString != null && !dateAsString.isEmpty() && !dateAsString.equals("00000000") && !dateAsString.equals("0")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                date = LocalDate.parse(dateAsString, formatter);
            }
        }
        catch (Exception ex){
            System.out.printf("date parser error. dateAsString: %s fieldName: %s \n", dateAsString, fieldName);
            //Don't throw exception because there is wrong values like 4100813
        }

        return date;
    }

    public static LocalDate getLocalDate(String dateAsString) {
        return getLocalDate(dateAsString,null);
    }

    /**
     * Returns the local date for the row and index using CNPJ dataset date format.
     * @param value The Row with values
     * @param filedName The Column fieldName to get the value
     * @return Return null if no date were found or the LocalDate found
     */
    public static LocalDate getLocalDate(Row value, String filedName) {
        return getLocalDate(getString(value, filedName), filedName);
    }

    /**
     * Returns the local date for the row and index using CNPJ dataset date format.
     * @param value The Row with values
     * @param fieldName The Column fieldName to get the value
     * @return Return null if no date were found or the LocalDate found
     */
    public static String getLocalDateAsString(Row value, String fieldName) {

        LocalDate date = getLocalDate(value, fieldName);
        if (date != null)
            return date.toString();

        return null;
    }

    public static String getLocalDateAsString(String value) {

        LocalDate date = getLocalDate(value);
        if (date != null)
            return date.toString();

        return null;
    }

    public static BigDecimal getBigDecimal(Row value, String fieldName)  {
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
            String numberString = getString(value, fieldName);
            return new BigDecimal(nf.parse(numberString).toString());
        }
        catch (Exception ex){
            return BigDecimal.ZERO;
        }
    }

    public static String getBigDecimalAsString(Row value, String fieldName)  {
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
            String numberString = getString(value, fieldName);
            return nf.parse(numberString).toString();
        }
        catch (Exception ex){
            return "0";
        }
    }

    public static String getBigDecimalAsString(String value)  {
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
            String numberString = fixStringValues(value);
            return nf.parse(numberString).toString();
        }
        catch (Exception ex){
            return "0";
        }
    }

    public static Short getShort(Row value, String fieldName) {
        try {
            String numberString = getString(value, fieldName);
            if (numberString == null || numberString.equals("null"))
                return null;
            return Short.decode(numberString);
        }
        catch (Exception ex){
            return null;
        }
    }

    public static Short getShort(String numberString) {
        try {
            numberString = fixStringValues(numberString);

            if (numberString == null || numberString.equals("null") || numberString.equals(""))
                return null;
            return Short.decode(numberString);
        }
        catch (Exception ex){
            return null;
        }
    }

    public static Integer getInteger(String numberString){
        String regexStr = "^0+(?!$)";
        numberString = fixStringValues(numberString);

        if(numberString == null)
            return null;

        return Integer.parseInt(numberString.replaceFirst(regexStr,""));
    }

    public static String getString(Row value, String fieldName) {
        String text = value.getAs(fieldName);
        if (text == null || text.equals("null"))
            return null;
        return text.replaceAll("^\"|\"$", "");
    }

    public static String fixStringValues(String value) {
        if (value == null || value.equals("null"))
            return null;
        return value.replaceAll("^\"|\"$", "");
    }

    public static String[] splitTextLine(String textLine){
        if(textLine.startsWith("\""))
            textLine = textLine.substring(1);
        if(textLine.endsWith("\""))
            textLine = textLine.substring(0, textLine.length()-1);

        //System.out.printf("parts.length: %s \n", parts.length);
        return textLine.split("\";\"",-1);
    }
}
