package application.batch.schemes.cnpj;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

public class EstablishmentSchema {
    public static StructType getSchema(){
        return new StructType()
                .add("basic_cnpj", DataTypes.StringType, false)
                .add("cnpj_order", DataTypes.StringType, true)
                .add("cnpj_checking_digit", DataTypes.StringType, true)
                // 1 – MATRIX 2 - BRANCH
                .add("matrix_branch", DataTypes.StringType, true)
                .add("fantasy_name", DataTypes.StringType, true)
                .add("registration_situation", DataTypes.StringType, true)
                .add("date_registration_situation", DataTypes.StringType, true)
                // registration situation represent an event like: extinction of the company and others
                .add("reason_registration_situation", DataTypes.StringType, true)
                .add("name_city_abroad", DataTypes.StringType, true)
                .add("country_code", DataTypes.StringType, true)
                .add("activity_start_date", DataTypes.StringType, true)
                .add("main_fiscal_cnae", DataTypes.StringType, true)
                // splited by ,
                .add("secondary_fiscal_cnae", DataTypes.StringType, true)
                .add("type_of_address", DataTypes.StringType, true)
                .add("address", DataTypes.StringType, true)
                .add("address_number", DataTypes.StringType, true)
                .add("address_complement", DataTypes.StringType, true)
                .add("address_district", DataTypes.StringType, true)
                .add("zip_code", DataTypes.StringType, true)
                 // SIGLA OF THE FEDERATION UNIT IN WHICH THE ESTABLISHMENT IS
                .add("federation_unit", DataTypes.StringType, true)
                .add("city_jurisdiction_code", DataTypes.StringType, true)
                .add("telephone1_area_code", DataTypes.StringType, true)
                .add("telephone1", DataTypes.StringType, true)
                .add("telephone2_area_code", DataTypes.StringType, true)
                .add("telephone2", DataTypes.StringType, true)
                .add("fax_area_code", DataTypes.StringType, true)
                .add("fax_number", DataTypes.StringType, true)
                .add("contributors_email", DataTypes.StringType, true)
                .add("special_situation", DataTypes.StringType, true)
                // YYYYMMDD
                .add("special_situation_date", DataTypes.StringType, true);
    }
}
