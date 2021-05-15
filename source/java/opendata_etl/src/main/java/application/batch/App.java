package application.batch;

import application.batch.models.Parameters;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting app program..");
        Parameters parameters = new Parameters(args);

        System.out.println("Starting SparkSession..");
        SparkConf sparkConf = new SparkConf();

        parameters.getSparkConf().forEach((o, o2) -> {
            System.out.println("setting sparkConf" + o.toString() + "=" + o2.toString());
            sparkConf.set(o.toString(),o2.toString());
        });

        SparkSession sparkSession = SparkSession
                .builder()
                .config(sparkConf)
                .getOrCreate();

        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>> >>>>>>>>>");
        System.out.println("Ended SparkSession..");
        System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>> >>>>>>>>>");
    }
}
