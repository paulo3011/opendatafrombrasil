import configparser
# from datetime import datetime
import os
from pyspark.conf import SparkConf
from pyspark.sql import SparkSession
from pyspark.sql.functions import broadcast
from pyspark.sql.types import (IntegerType)
# /home/paulo/projects/paulo3011/opendatafrombrasil/runtime/schemas/cnpj/establishments.py
from schemas.cnpj.establishments import establishments_raw_schema

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

def _process_establishments_to_parquet(df, output_path="/home/paulo/tmp/establishments/all/parquet/", compression = "snappy"):
    df.write.parquet(
        path=output_path,
        # partitionBy=["year", "artist_id"],
        compression=compression,
        mode="overwrite")      

def _process_establishments_to_orc(df, output_path="/home/paulo/tmp/establishments/all/orc/", compression = "snappy"):
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
        mode="overwrite")     

def _process_establishments_to_avro(df, output_path="/home/paulo/tmp/establishments/all/avro/", compression = "snappy"):
    """
    seealso: 
    - https://spark.apache.org/docs/latest/sql-data-sources-avro.html
    - http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.format.html#pyspark.sql.DataFrameWriter.format
    - http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.save.html#pyspark.sql.DataFrameWriter.save
    - https://docs.databricks.com/data/data-sources/read-avro.html#language-python
    """
    df.write.mode("overwrite").format("avro").save(output_path)                

def process_establishments(spark, output_path="/home/paulo/tmp/establishments/", compression = "snappy"):
    print("starting process_establishments")
    # file:/home/paulo/projects/paulo3011/opendatafrombrasil/~/tmp/K3241.K03200Y0.D10410.ESTABELE
    path = "/home/paulo/tmp/2021-04-14/estabelecimento/"
    # http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv
    establishments_raw_df = spark.read.csv(
        path=path, 
        schema=establishments_raw_schema, 
        sep=";", 
        encoding="ISO-8859-1", 
        enforceSchema=False)

    print(establishments_raw_df.show(1))

    _process_establishments_to_parquet(establishments_raw_df) 
    _process_establishments_to_orc(establishments_raw_df) 
    _process_establishments_to_avro(establishments_raw_df) 
    

def main():
    spark = create_spark_session()
    process_establishments(spark)


if __name__ == "__main__":
    main()

print("ok")