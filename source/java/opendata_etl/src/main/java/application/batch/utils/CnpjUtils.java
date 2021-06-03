package application.batch.utils;

import org.apache.spark.sql.Row;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        LocalDate date = null;

        //"yyyyMMdd"
        String dateAsString = value.getString(index);
        if (dateAsString != null && !dateAsString.equals("00000000")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            date = LocalDate.parse(dateAsString, formatter);
        }

        return date;
    }
}
