package application.batch;

import application.batch.contracts.IPipeline;
import application.batch.enums.FileType;
import application.batch.models.args.Parameters;
import application.batch.pipeline.CnpjRaw;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        System.out.println("Starting app program..");
        Parameters parameters = new Parameters(args);

        System.out.println("Starting SparkSession..");
        SparkConf sparkConf = new SparkConf();

        //SET DEFAULTS
        sparkConf.setAppName("OPEN_DATA_FROM_BRAZIL_ETL")
                .set("fs.s3a.multipart.size", "104M")
                .set("spark.jars.packages", "org.apache.hadoop:hadoop-aws:3.1.1")
                .set("spark.jars.packages", "org.apache.hadoop:hadoop-common:3.1.1")
                .set("spark.jars.packages", "org.apache.hadoop:hadoop-hdf:3.1.1")
                .set("spark.sql.adaptive.enabled", "true")
                .set("spark.sql.adaptive.coalescePartitions.enabled", "true")
                .set("spark.sql.adaptive.skewJoin.enabled", "true")
                .set("spark.sql.legacy.timeParserPolicy", "CORRECTED");

        parameters.getSparkConf().forEach((o, o2) -> {
            System.out.println("setting sparkConf" + o.toString() + "=" + o2.toString());
            sparkConf.set(o.toString(),o2.toString());
        });

        SparkSession sparkSession = SparkSession
                .builder()
                .config(sparkConf)
                .getOrCreate();

        IPipeline pipeline = null;
        if(parameters.getInputFileType() == FileType.cnpj_raw)
            pipeline = new CnpjRaw();

        if(pipeline != null)
            pipeline.Start(sparkSession, parameters);

        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>> >>>>>>>>>");
        System.out.println("Ended SparkSession..");
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>> >>>>>>>>>");
    }
}
