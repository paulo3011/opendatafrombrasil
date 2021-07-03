# How to run

## On spark-submit

```shell script
# default params
spark-submit --class application.batch.App opendata_etl-1.0.jar
# custom params
spark-submit --class application.batch.App opendata_etl-1.0.jar --spark-conf spark.app.name=paulo,spark.driver.cores=1
# default params 1
spark-submit --class application.batch.App opendata_etl-1.0.jar --spark-conf spark.app.name=brasil-open-etl --input-path E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\ --input-type cnpj_raw --input-format csv --output-type cnpj_lake --output-format orc
# default params 2
spark-submit --class application.batch.App opendata_etl-1.0.jar --spark-conf spark.app.name=brasil-open-etl --input-path E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\ --input-type cnpj_raw --input-format csv --output-type cnpj_lake --output-format parquet
```
## On cmd

```shell script
java -jar opendata_etl-1.0.jar
java -jar opendata_etl-1.0.jar --spark-conf spark.master=local[*],spark.app.name=brasil-open-etl --input-path E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\ --input-type cnpj_raw --input-format csv --output-type cnpj_lake --output-format parquet
```

# Erros conhecidos

## NativeIO$Windows.access0(Ljava/lang/String;I)Z

Em ambiente windows pode acontecer o erro abaixo:

```shell script
java.lang.UnsatisfiedLinkError: org.apache.hadoop.io.nativeio.NativeIO$Windows.access0(Ljava/lang/String;I)Z
```
Verifique se foi feito download dos arquivos do Hadoop na versão correta e se as variáveis de ambiente necessárias foram criadas.

1. Baixar winutils
```shell script
git clone https://github.com/steveloughran/winutils.git
cd winutils
cp hadoop-3.0.0 C:/hadoop 
```
2. Configurar variáveis de ambiente:

```shell script
set HADOOP_HOME=c:/hadoop
set PATH=%PATH%;%HADOOP_HOME%/bin;
``` 

# References

- https://www.atlassian.com/git/tutorials/saving-changes/gitignore
- https://jcommander.org/#_custom_types_converters_and_splitters
- https://spark.apache.org/docs/latest/configuration.html#spark-properties
- https://spark.apache.org/docs/latest/submitting-applications.html#master-urls