package application.batch.contracts;

import application.batch.models.args.Parameters;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public interface IPipeline {
    void Start(SparkSession sparkSession, Parameters parameters) throws IOException;
}
