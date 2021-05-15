package application.batch;

import application.batch.models.Parameters;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting app program..");
        Parameters parameters = new Parameters(args);

        System.out.println("Starting SparkSession..");
        SparkConf sparkConf = new SparkConf()
                .setMaster("local[*]");

        SparkSession sparkSession = SparkSession
                .builder()
                .config(sparkConf)
                .getOrCreate();

        System.out.println("Ended SparkSession..");
    }
}
