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
public class CnpjUtils {
    /**
     * Returns the local date for the row and index using CNPJ dataset date format.
     * @param value The Row with values
     * @param index The Row index to get the value
     * @return Return null if no date were found or the LocalDate found
     */
    public static LocalDate getLocalDate(Row value, int index) {
        /*
        Known issues:
        file: K3241.K03200Y0.D10410.ESTABELE
        line: "30005475";"0001";"31";"1";"";"2";"0";"0";"";"";"20180322";"6204000";"6209100,7490104";"AVENIDA";"PAULISTA";"2202";"CONJ  54-B";"BELA VISTA";"01310300";"SP";"7107";"11";"59085410";"";"";"";"";"CEFISCO@UOL.COM.BR";"";""
        wrong datetime value: 0
         */
        LocalDate date = null;

        try {
            //"yyyyMMdd"
            String dateAsString = value.getString(index);

            if (dateAsString != null && !dateAsString.equals("00000000") && !dateAsString.equals("0") && Long.parseLong(dateAsString) != 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                date = LocalDate.parse(dateAsString, formatter);
            }
        }
        catch (Exception ex){
            System.out.printf("date parser error. dateAsString: %s, row data: %s", value.getString(index), value.toString());
            return null;
        }

        return date;
    }

    /**
     * Returns the local date for the row and index using CNPJ dataset date format.
     * @param value The Row with values
     * @param index The Row index to get the value
     * @return Return null if no date were found or the LocalDate found
     */
    public static String getLocalDateAsString(Row value, int index) {

        LocalDate date = getLocalDate(value, index);
        if (date != null)
            return date.toString();

        return null;
    }

    public static BigDecimal getBigDecimal(Row value, int index)  {
        try {
            NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
            String numberString = value.getAs(index);
            return new BigDecimal(nf.parse(numberString).toString());
        }
        catch (Exception ex){
            return BigDecimal.ZERO;
        }
    }
}
