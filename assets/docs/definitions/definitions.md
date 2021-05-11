# Notes about definitions done

1. Error on trying to parse cnpj wrong date:

```shell
Caused by: org.apache.spark.SparkUpgradeException: You may get a different result due to the upgrading of Spark 3.0: Fail to parse 
'4771701' in the new parser. You can set spark.sql.legacy.timeParserPolicy to LEGACY to restore the behavior before Spark 3.0, or set to CORRECTED and treat it as an invalid datetime string.
```

Problem origin:
- Wrong date value (4771701) on the csv K3241.K03200Y0.D10410.ESTABELE

The line on the file that cause the problem:
```csv
"02354676";"0001";"09";"1";"POLI BRILHO\";"3";"20100518";"21";"";"";"19980203";"4771701";"";"RUA";"DA ESCOLA";"129 A";"CASA";"MAPELE";"43700000";"BA";"3913";"";"";"";"";"";"";"";"";""
```

The text character "\" in the "POLI BRILHO\" field made spark join "POLI BRILHO\";"3" in one field.

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


2. How to process company capital value (e.g. 000000018000,00)

```shell
Row(basic_cnpj='13846071', legal_name='CESAR APARECIDO NICOLETI', legal_nature='2135', responsible_qualification='50', company_capital='000000018000,00', company_size='01', federative_entity_responsible=None)
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
|basic_cnpj|          legal_name|legal_nature|responsible_qualification|company_capital|company_size|federative_entity_responsible|
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
|  13846071|CESAR APARECIDO N...|        2135|                       50|000000018000,00|          01|                         null|
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
```