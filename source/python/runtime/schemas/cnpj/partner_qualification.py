from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)

# partner source schema
partner_qualification_raw_schema = StructType()
partner_qualification_raw_schema.add("code", StringType(), False)
partner_qualification_raw_schema.add("description", StringType(), True)
partner_qualification_raw_schema.add("broken", StringType(), True)
