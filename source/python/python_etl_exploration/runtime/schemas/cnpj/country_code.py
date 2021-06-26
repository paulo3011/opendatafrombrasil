from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)

# country code source schema
country_code_raw_schema = StructType()
country_code_raw_schema.add("code", StringType(), False)
country_code_raw_schema.add("description", StringType(), True)
country_code_raw_schema.add("broken", StringType(), True)
