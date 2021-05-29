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
        setIsSimple(value,1, record);
        record.setSimpleOptionDate(getLocalDate(value,2));
        record.setSimpleExclusionDate(getLocalDate(value,3));
        setIsMei(value,4, record);
        record.setMeiOptionDate(getLocalDate(value,5));
        record.setMeiExclusionDate(getLocalDate(value,6));
        return record;
    }

    private void setIsSimple(Row value, int index, SimpleNational record ){
        record.setIsSimple(value.getAs(index) == "N");
    }

    private void setIsMei(Row value, int index, SimpleNational record ){
        record.setIsMei(value.getAs(index) == "N");
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
