package application.batch.mappers.cnpj;

import application.batch.models.cnpj.SimpleNational;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class SimpleNationalRawToModel implements MapFunction<Row, SimpleNational> {
    /**
     * Class serialization version
     * @see <a href="">https://pt.stackoverflow.com/questions/180407/para-que-serve-o-serialversionuid</a>
     */
    private static final long serialVersionUID = 1L;

    @Override
    public SimpleNational call(Row value) {
        SimpleNational record = new SimpleNational();
        try {
            record.setBasicCnpj(CnpjUtils.getString(value,"basic_cnpj"));
            record.setIsSimple(CnpjUtils.getString(value,"is_simple").equals("S"));
            record.setSimpleOptionDate(CnpjUtils.getLocalDateAsString(value, "simple_option_date"));
            record.setSimpleExclusionDate(CnpjUtils.getLocalDateAsString(value, "simple_exclusion_date"));
            record.setIsMei(CnpjUtils.getString(value,"is_mei").equals("S"));
            record.setMeiOptionDate(CnpjUtils.getLocalDateAsString(value, "mei_option_date"));
            record.setMeiExclusionDate(CnpjUtils.getLocalDateAsString(value, "mei_exclusion_date"));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
