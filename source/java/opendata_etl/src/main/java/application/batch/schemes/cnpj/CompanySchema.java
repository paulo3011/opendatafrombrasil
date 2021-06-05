package application.batch.schemes.cnpj;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class CompanySchema {
    /**
     * Create and return the csv Company schema
     * @return The csv Company schema
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv">About columnNameOfCorruptRecord and PERMISSIVE mode</a>
     */
    public static StructType getSchema(){

        //.add("broken", DataTypes.StringType, true)

        /*
        //this works too:
        StructType schema = new StructType()
                .add("basic_cnpj", "string")
                .add("legal_name", "string")
                .add("legal_nature", "string")
                .add("responsible_qualification", "string")
                .add("company_capital", "string")
                .add("company_size", "string")
                .add("federative_entity_responsible", "string")
                //.add("broken", "string")
                ;
         */

        return new StructType()
            .add("basic_cnpj", DataTypes.StringType, true)
            .add("legal_name", DataTypes.StringType, true)
            .add("legal_nature", DataTypes.StringType, true)
            .add("responsible_qualification", DataTypes.StringType, true)
            .add("company_capital", DataTypes.StringType, true)
            .add("company_size", DataTypes.StringType, true)
            .add("federative_entity_responsible", DataTypes.StringType, true);
    }
}
