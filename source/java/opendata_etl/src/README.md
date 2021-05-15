# How to run

## On spark-submit

```shell script
# default params
spark-submit --class application.batch.App opendata_etl-1.0.jar
# custom params
spark-submit --class application.batch.App opendata_etl-1.0.jar --spark-conf spark.app.name=paulo,spark.driver.cores=1
```
## On cmd

```shell script
java -jar opendata_etl-1.0.jar
```

# References

- https://www.atlassian.com/git/tutorials/saving-changes/gitignore
- https://jcommander.org/#_custom_types_converters_and_splitters
- https://spark.apache.org/docs/latest/configuration.html#spark-properties
- https://spark.apache.org/docs/latest/submitting-applications.html#master-urls