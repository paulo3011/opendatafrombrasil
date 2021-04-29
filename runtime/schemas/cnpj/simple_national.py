from pyspark.sql.types import (
    StructType,
    StringType,
    ShortType,
    IntegerType,
    LongType,
    DoubleType,
    DecimalType,
    DateType)


# company of the simple national source schema
simple_company_raw_schema = StructType()
simple_company_raw_schema.add("basic_cnpj", StringType(), False)
simple_company_raw_schema.add("is_simple", StringType(), True)
simple_company_raw_schema.add("simple_option_date", StringType(), True)
simple_company_raw_schema.add("simple_exclusion_date", StringType(), True)
simple_company_raw_schema.add("is_mei", StringType(), True)
simple_company_raw_schema.add("mei_option_date", StringType(), True)
simple_company_raw_schema.add("mei_exclusion_date", StringType(), True)
