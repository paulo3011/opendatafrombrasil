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
partner_raw_schema = StructType()
partner_raw_schema.add("basic_cnpj", StringType(), False)
partner_raw_schema.add("partner_type", StringType(), True)
partner_raw_schema.add("partner_name", StringType(), True)
partner_raw_schema.add("partner_document", StringType(), True)
partner_raw_schema.add("partner_qualification", StringType(), True)
partner_raw_schema.add("partner_start_date", StringType(), True)
partner_raw_schema.add("country", StringType(), True)
partner_raw_schema.add("legal_representative", StringType(), True)
partner_raw_schema.add("representative_name", StringType(), True)
partner_raw_schema.add("representative_qualification", StringType(), True)
partner_raw_schema.add("age_range", StringType(), True)
partner_raw_schema.add("broken", StringType(), True)
