# Notes about definitions done

1. Error on trying to parse cnpj wrong date:

```shell
Caused by: org.apache.spark.SparkUpgradeException: You may get a different result due to the upgrading of Spark 3.0: Fail to parse 
'4771701' in the new parser. You can set spark.sql.legacy.timeParserPolicy to LEGACY to restore the behavior before Spark 3.0, or set to CORRECTED and treat it as an invalid datetime string.
```

Problem origin:
- Wrong date value (4771701) on the csv K3241.K03200Y0.D10410.ESTABELE

What was done:
- set spark.sql.legacy.timeParserPolicy to CORRECTED

Results:
- This solves error on save/convert csv to orc
- Is necessary to execute some data quality check on date columns that got null and check if the problem is with the raw source file

Seealso: 
- https://spark.apache.org/docs/latest/sql-migration-guide.html
- https://spark.apache.org/docs/3.1.1/sql-ref-datetime-pattern.html
- http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv
- https://spark.apache.org/docs/latest/sql-ref-datatypes.html