package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.GenericCode;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class GenericCodeStringRawToModel<T extends GenericCode> implements FlatMapFunction<Iterator<String>, T> {
    private final Class<T> tClass;
    public GenericCodeStringRawToModel(final Class<T> tClass){
        this.tClass = tClass;
    }

    @Override
    public Iterator<T> call(Iterator<String> stringIterator) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArrayList<T> records = new ArrayList<>();
        while(stringIterator.hasNext()){
            String textLine = stringIterator.next();
            records.add(tryParseRecord(textLine));
        }
        return records.iterator();
    }

    @SuppressWarnings("ConstantConditions")
    public T tryParseRecord(String textLine) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T record = tClass.getDeclaredConstructor().newInstance();
        try {
            String[] values = CnpjUtils.splitTextLine(textLine);
            record.setCode(CnpjUtils.getInteger(values[0]));
            record.setDescription(CnpjUtils.fixStringValues(values[1]));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), textLine);
            record.setRawData(textLine);
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
