package application.batch.contracts;

import application.batch.models.args.Parameters;
import org.apache.spark.sql.SparkSession;

public interface IPipeline {
    void Start(SparkSession sparkSession, Parameters parameters);
}
