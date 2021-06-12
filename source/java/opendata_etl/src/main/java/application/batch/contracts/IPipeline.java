package application.batch.contracts;

import application.batch.models.args.Parameters;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IPipeline {
    void Start(SparkSession sparkSession, Parameters parameters) throws IOException, ReflectiveOperationException;
}
