package application.batch.mappers.cnpj;

import application.batch.models.cnpj.SimpleNational;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimpleNationalRawToModel implements MapFunction<Row, SimpleNational> {
    /**
     * Class serialization version
     * @see <a href="">https://pt.stackoverflow.com/questions/180407/para-que-serve-o-serialversionuid</a>
     */
    private static final long serialVersionUID = 1L;

    @Override
    public SimpleNational call(Row value) {
        SimpleNational record = new SimpleNational();
        record.setBasicCnpj(value.getAs(0));
        record.setIsSimple(value.getAs(1).equals("S"));
        record.setSimpleOptionDate(getLocalDate(value,2));
        record.setSimpleExclusionDate(getLocalDate(value,3));
        record.setIsMei(value.getAs(4).equals("S"));
        record.setMeiOptionDate(getLocalDate(value,5));
        record.setMeiExclusionDate(getLocalDate(value,6));
        return record;
    }

    private LocalDate getLocalDate(Row value, int index) {
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
