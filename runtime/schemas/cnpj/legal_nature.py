from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)

# legal nature source schema
legal_nature_raw_schema = StructType()
legal_nature_raw_schema.add("code", StringType(), False)
legal_nature_raw_schema.add("description", StringType(), True)
legal_nature_raw_schema.add("broken", StringType(), True)
