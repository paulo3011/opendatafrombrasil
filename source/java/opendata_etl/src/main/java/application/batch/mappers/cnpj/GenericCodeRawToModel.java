package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.GenericCode;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

import java.lang.reflect.InvocationTargetException;

public class GenericCodeRawToModel<T extends GenericCode> implements MapFunction<Row, T> {
    private final Class<T> tClass;
    public GenericCodeRawToModel(final Class<T> tClass){
        this.tClass = tClass;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public T call(Row value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T record = tClass.getDeclaredConstructor().newInstance();
        try {

            //note: In Java, numbers prefixed with a "0" are treated as octal.
            //java.lang.NumberFormatException: For input string: "008" under radix 8
            record.setCode(CnpjUtils.getInteger(value.getAs(0)));
            record.setDescription(value.getAs(1));

            return record;
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
