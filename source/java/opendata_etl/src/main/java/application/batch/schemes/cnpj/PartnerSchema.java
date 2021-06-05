package application.batch.schemes.cnpj;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class PartnerSchema {
    public static StructType getSchema(){
        return new StructType()
                .add("basic_cnpj", DataTypes.StringType, false)
                .add("partner_type", DataTypes.StringType, true)
                .add("partner_name", DataTypes.StringType, true)
                .add("partner_document", DataTypes.StringType, true)
                .add("partner_qualification", DataTypes.StringType, true)
                .add("partner_start_date", DataTypes.StringType, true)
                .add("country", DataTypes.StringType, true)
                .add("legal_representative", DataTypes.StringType, true)
                .add("representative_name", DataTypes.StringType, true)
                .add("representative_qualification", DataTypes.StringType, true)
                .add("age_range", DataTypes.StringType, true);
    }
}
