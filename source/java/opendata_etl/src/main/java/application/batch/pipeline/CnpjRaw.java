package application.batch.pipeline;

import application.batch.contracts.IPipeline;
import application.batch.models.args.Parameters;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class CnpjRaw implements IPipeline {
    @Override
    public void Start(SparkSession sparkSession, Parameters parameters) {
        getDataFrame(sparkSession, parameters);
    }

    public Dataset<Row> getDataFrame(SparkSession spark, Parameters parameters)
    {
        String filename = "E:\\hdfs\\cnpj\\2021-04-14\\simple_national";
        Dataset<Row> df = spark.read().format("csv")
                .option("inferSchema", "false")
                .option("header", "false")
                .option("sep",";")
                .option("encoding","ISO-8859-1")
                .load(filename);

        System.out.println("*** Books ingested in a dataframe");
        df.show(5);
        df.printSchema();
        return df;
    }
}
