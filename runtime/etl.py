import configparser
# from datetime import datetime
import os
from pyspark.conf import SparkConf
from pyspark.sql import SparkSession
from pyspark.sql.functions import broadcast
from pyspark.sql.types import (IntegerType)
# /home/paulo/projects/paulo3011/opendatafrombrasil/runtime/schemas/cnpj/establishments.py
from schemas.cnpj.establishments import establishments_raw_schema
from schemas.cnpj.company import company_raw_schema
from schemas.cnpj.partner import partner_raw_schema
from schemas.cnpj.simple_national import simple_company_raw_schema
from schemas.cnpj.cnae import cnae_raw_schema
from schemas.cnpj.city_code import city_code_raw_schema
from schemas.cnpj.country_code import country_code_raw_schema
from schemas.cnpj.legal_nature import legal_nature_raw_schema
from schemas.cnpj.partner_qualification import partner_qualification_raw_schema



config = configparser.ConfigParser()
# config.read('dl.cfg')

# Setup the Spark Process

conf = SparkConf() \
       .setAppName("OPEN_DATA_FROM_BRAZIL_ETL") \
       .set("fs.s3a.multipart.size", "104M") \
       .set("spark.jars.packages", "org.apache.hadoop:hadoop-aws:3.1.1") \
       .set("spark.jars.packages", "org.apache.spark:spark-avro_2.12:3.1.1") \
       .set("spark.sql.adaptive.enabled", "true") \
       .set("spark.sql.adaptive.coalescePartitions.enabled", "true") \
       .set("spark.sql.adaptive.skewJoin.enabled", "true") \
       .set("spark.sql.legacy.timeParserPolicy", "CORRECTED") \
       .setMaster("local[*]")

def create_spark_session():
    """
    Create the entry point to programming Spark with the Dataset and DataFrame API.
    The entry point into all functionality in Spark is the SparkSession class.
    Instead of having a spark context, hive context, SQL context, now all of it is encapsulated in a Spark session.
    Seealso: http://spark.apache.org/docs/3.1.1/api/python/reference/pyspark.sql.html#spark-session-apis
    """
    spark = SparkSession \
        .builder \
        .config(conf=conf) \
        .getOrCreate()

    return spark

# tasks

def _write_to_orc(df, output_path, compression= "snappy", mode="overwrite"):
    """
    Notes:
    - Redshift COPY inserts values into the target table's columns in the same order as the columns occur in the columnar data files. The number of columns in the target table and the number of columns in the data file must match.
    seealso: 
    - http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.orc.html#pyspark.sql.DataFrameWriter.orc
    - https://docs.amazonaws.cn/en_us/redshift/latest/dg/copy-usage_notes-copy-from-columnar.html
    """
    df.write.orc(
        path=output_path,
        compression=compression,
        mode=mode)       


def convert_cnpj_file_to_orc(spark, source_path, schema, base_output_path, destination_key):
    """
    Convert the raw csv file of CNPJ into orc.
    """
    df = spark.read.csv(
        path=source_path, 
        schema=schema, 
        sep=";", 
        encoding="ISO-8859-1", 
        dateFormat="yyyyMMdd",
        enforceSchema=False)        

    print(df.head())
    print(df.show(1))

    output_path = base_output_path + destination_key
    _write_to_orc(df, output_path)  


def convert_cnpj_registration_files_to_orc(spark, base_source_path, base_output_path):
    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "partner_qualification/",
        schema=partner_qualification_raw_schema,
        base_output_path=base_output_path,
        destination_key="partner_qualification")

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "legal_nature/",
        schema=legal_nature_raw_schema,
        base_output_path=base_output_path,
        destination_key="legal_nature")       

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "country_code/",
        schema=country_code_raw_schema,
        base_output_path=base_output_path,
        destination_key="country_code")    

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "cnae/",
        schema=cnae_raw_schema,
        base_output_path=base_output_path,
        destination_key="cnae")

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "city_code/",
        schema=city_code_raw_schema,
        base_output_path=base_output_path,
        destination_key="city_code")  

def convert_cnpj_files_to_orc(spark, base_source_path, base_output_path):
    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "simple_national/",
        schema=simple_company_raw_schema,
        base_output_path=base_output_path,
        destination_key="simple_national")

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "partner/",
        schema=partner_raw_schema,
        base_output_path=base_output_path,
        destination_key="partner")

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "company/",
        schema=company_raw_schema,
        base_output_path=base_output_path,
        destination_key="company")

    convert_cnpj_file_to_orc(
        spark=spark,
        source_path=base_source_path + "establishment/",
        schema=establishments_raw_schema,
        base_output_path=base_output_path,
        destination_key="establishment")        

def main():
    spark = create_spark_session()
    base_source_path = "/home/paulo/tmp/2021-04-14/"
    base_output_path = "/home/paulo/tmp/output/"
    convert_cnpj_registration_files_to_orc(spark, base_source_path, base_output_path)
    convert_cnpj_files_to_orc(spark, base_source_path, base_output_path)


if __name__ == "__main__":
    main()

