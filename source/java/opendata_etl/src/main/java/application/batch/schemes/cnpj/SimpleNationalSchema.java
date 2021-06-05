package application.batch.schemes.cnpj;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class SimpleNationalSchema {
    public final static StructType getSchema(){
        StructType schema = new StructType()
                .add("basic_cnpj", DataTypes.StringType, false)
                .add("is_simple", DataTypes.StringType, true)
                .add("simple_option_date", DataTypes.StringType, true)
                .add("simple_exclusion_date", DataTypes.StringType, true)
                .add("is_mei", DataTypes.StringType, true)
                .add("mei_option_date", DataTypes.StringType, true)
                .add("mei_exclusion_date", DataTypes.StringType, true);
        return schema;
    }
}
