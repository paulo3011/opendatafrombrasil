from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)

# city code source schema
city_code_raw_schema = StructType()
city_code_raw_schema.add("code", StringType(), False)
city_code_raw_schema.add("description", StringType(), True)
