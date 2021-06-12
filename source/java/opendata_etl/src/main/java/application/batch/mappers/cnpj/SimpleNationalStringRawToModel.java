package application.batch.mappers.cnpj;

import application.batch.models.cnpj.SimpleNational;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class SimpleNationalStringRawToModel implements FlatMapFunction<Iterator<String>,  SimpleNational> {
    /**
     * Class serialization version
     * @see <a href="">https://pt.stackoverflow.com/questions/180407/para-que-serve-o-serialversionuid</a>
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Iterator<SimpleNational> call(Iterator<String> stringIterator) {
        ArrayList<SimpleNational> records = new ArrayList<>();
        while(stringIterator.hasNext()){
            String textLine = stringIterator.next();
            records.add(tryParseRecord(textLine));
        }
        return records.iterator();
    }

    public SimpleNational tryParseRecord(String textLine){
        SimpleNational record = new SimpleNational();
        try {
            String[] values = CnpjUtils.splitTextLine(textLine);
            record.setBasicCnpj(CnpjUtils.fixStringValues(values[0]));
            record.setIsSimple(CnpjUtils.fixStringValues(values[1]).equals("S"));
            record.setSimpleOptionDate(CnpjUtils.getLocalDateAsString(values[2]));
            record.setSimpleExclusionDate(CnpjUtils.getLocalDateAsString(values[3]));
            record.setIsMei(CnpjUtils.fixStringValues(values[4]).equals("S"));
            record.setMeiOptionDate(CnpjUtils.getLocalDateAsString(values[5]));
            record.setMeiExclusionDate(CnpjUtils.getLocalDateAsString(values[6]));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), textLine);
            record.setRawData(textLine);
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
