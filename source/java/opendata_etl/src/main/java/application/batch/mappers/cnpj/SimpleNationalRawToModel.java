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
        record.setBasicCnpj(value.getAs(0));
        record.setIsSimple(value.getAs(1).equals("S"));
        record.setSimpleOptionDate(CnpjUtils.getLocalDate(value,2));
        record.setSimpleExclusionDate(CnpjUtils.getLocalDate(value,3));
        record.setIsMei(value.getAs(4).equals("S"));
        record.setMeiOptionDate(CnpjUtils.getLocalDate(value,5));
        record.setMeiExclusionDate(CnpjUtils.getLocalDate(value,6));
        return record;
    }
}
