from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)


# Establishments source schema
establishments_raw_schema = StructType()
establishments_raw_schema.add("basic_cnpj", StringType(), False)
establishments_raw_schema.add("cnpj_order", StringType(), False)
establishments_raw_schema.add("cnpj_checking_digit", StringType(), False)
establishments_raw_schema.add("matrix_branch", ShortType(), False)
establishments_raw_schema.add("fantasy_name", StringType(), True)

# YYYYMMDD
# https://spark.apache.org/docs/latest/sql-ref-datatypes.html
# registration situation represent an event like: extinction of the company and others
establishments_raw_schema.add("date_registration_situation", DateType(), False)
# todo: check if exists varchar values
establishments_raw_schema.add("reason_registration_situation", ShortType(), False)
establishments_raw_schema.add("name_city_abroad", StringType(), False)
establishments_raw_schema.add("country_code", ShortType(), False)
# YYYYMMDD
establishments_raw_schema.add("activity_start_date", DateType(), False)
establishments_raw_schema.add("main_fiscal_cnae", IntegerType(), False)
# splited by ,
establishments_raw_schema.add("secondary_fiscal_cnae", StringType(), False)
establishments_raw_schema.add("type_of_address", StringType(), False)
establishments_raw_schema.add("address", StringType(), False)
establishments_raw_schema.add("address_number", StringType(), False)
establishments_raw_schema.add("address_complement", StringType(), False)
establishments_raw_schema.add("address_district", StringType(), False)
establishments_raw_schema.add("zip_code", StringType(), False)
# SIGLA OF THE FEDERATION UNIT IN WHICH THE ESTABLISHMENT IS
establishments_raw_schema.add("federation_unit", StringType(), False)
establishments_raw_schema.add("city_jurisdiction_code", StringType(), False)
establishments_raw_schema.add("telephone1_area_code", StringType(), False)
establishments_raw_schema.add("telephone1", StringType(), False)
establishments_raw_schema.add("telephone2_area_code", StringType(), False)
establishments_raw_schema.add("telephone2", StringType(), False)
establishments_raw_schema.add("fax_area_code", StringType(), False)
establishments_raw_schema.add("fax_number", StringType(), False)
establishments_raw_schema.add("contributors_email", StringType(), False)
establishments_raw_schema.add("special_situation", StringType(), False)
# YYYYMMDD
establishments_raw_schema.add("special_situation_date", DateType(), False)

# company
# establishments_raw_schema.add("legal_name", StringType(), True)