from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)


# company source schema
company_raw_schema = StructType()
company_raw_schema.add("legal_name", StringType(), True)