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
company_raw_schema.add("basic_cnpj", StringType(), False)
company_raw_schema.add("legal_name", StringType(), True)
company_raw_schema.add("legal_nature", StringType(), True)
company_raw_schema.add("responsible_qualification", StringType(), True)
company_raw_schema.add("company_capital", StringType(), True)
company_raw_schema.add("company_size", StringType(), True)
# FEDERATIVE ENTITY RESPONSIBLE
company_raw_schema.add("federative_entity_responsible", StringType(), True)
