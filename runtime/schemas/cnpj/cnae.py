from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)

# cnae source schema
cnae_raw_schema = StructType()
cnae_raw_schema.add("code", StringType(), False)
cnae_raw_schema.add("description", StringType(), True)
