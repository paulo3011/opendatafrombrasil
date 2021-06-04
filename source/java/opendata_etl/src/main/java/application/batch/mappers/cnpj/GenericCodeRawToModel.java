package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.GenericCode;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class GenericCodeRawToModel<T extends GenericCode> implements MapFunction<Row, T> {
    private Class<T> tClass;
    public GenericCodeRawToModel(final Class<T> tClass){
        this.tClass = tClass;
    }

    @Override
    public T call(Row value) throws Exception {
        T record = tClass.getDeclaredConstructor().newInstance();

        //note: In Java, numbers prefixed with a "0" are treated as octal.
        //java.lang.NumberFormatException: For input string: "008" under radix 8
        record.setCode(Integer.parseInt(value.getAs(0)));
        record.setDescription(value.getAs(1));

        return record;
    }
}
