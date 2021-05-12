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


2. How to solve the error message "ava.time.format.DateTimeParseException: Text '00000000' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12)"

```shell
ERROR Executor: Exception in task 10.0 in stage 42.0 (TID 62)
org.apache.spark.SparkUpgradeException: You may get a different result due to the upgrading of Spark 3.0: Fail to parse '00000000' in the new parser. You can set spark.sql.legacy.timeParserPolicy to LEGACY to restore the behavior before Spark 3.0, or set to CORRECTED and treat it as an invalid datetime string.
Caused by: java.time.format.DateTimeParseException: Text '00000000' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 0
        at java.base/java.time.format.DateTimeFormatter.createError(DateTimeFormatter.java:2017)
        at java.base/java.time.format.DateTimeFormatter.parse(DateTimeFormatter.java:1878)
        at org.apache.spark.sql.catalyst.util.Iso8601DateFormatter.$anonfun$parse$1(DateFormatter.scala:59)
```

3. How to process company capital value (e.g. 000000018000,00)

```shell
Row(basic_cnpj='13846071', legal_name='CESAR APARECIDO NICOLETI', legal_nature='2135', responsible_qualification='50', company_capital='000000018000,00', company_size='01', federative_entity_responsible=None)
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
|basic_cnpj|          legal_name|legal_nature|responsible_qualification|company_capital|company_size|federative_entity_responsible|
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
|  13846071|CESAR APARECIDO N...|        2135|                       50|000000018000,00|          01|                         null|
+----------+--------------------+------------+-------------------------+---------------+------------+-----------------------------+
```

4. Since Spark 2.3, the queries from raw JSON/CSV files are disallowed when the referenced columns only include the internal corrupt record column (named _corrupt_record by default).

Since Spark 2.3, the queries from raw JSON/CSV files are disallowed when the referenced columns only include the internal corrupt record column (named _corrupt_record by default). For example: spark.read.schema(schema).csv(file).filter($"_corrupt_record".isNotNull).count() and spark.read.schema(schema).csv(file).select("_corrupt_record").show().
Instead, you can cache or save the parsed results and then send the same query.
For example, val df = spark.read.schema(schema).csv(file).cache() and then df.select("_corrupt_record").show()

Sample:

```python
df = spark.read.csv(
    path=source_path, 
    schema=schema, 
    enforceSchema=True,
    columnNameOfCorruptRecord='broken')  
df = df.cache()
broken_rows = df.where("broken is not null").select("broken")
print(broken_rows.show(1))    
total_broken = broken_rows.count()
```

```shell
Row(basic_cnpj='00000006', is_simple='N', simple_option_date=datetime.date(2018, 1, 1), simple_exclusion_date=datetime.date(2019, 12, 31), is_mei='N', mei_option_date=None, mei_exclusion_date=None, broken='"00000006";"N";"20180101";"20191231";"N";"00000000";"00000000"')
+----------+---------+------------------+---------------------+------+---------------+------------------+--------------------+
|basic_cnpj|is_simple|simple_option_date|simple_exclusion_date|is_mei|mei_option_date|mei_exclusion_date|              broken|
+----------+---------+------------------+---------------------+------+---------------+------------------+--------------------+
|  00000006|        N|        2018-01-01|           2019-12-31|     N|           null|              null|"00000006";"N";"2...|
+----------+---------+------------------+---------------------+------+---------------+------------------+--------------------+
```