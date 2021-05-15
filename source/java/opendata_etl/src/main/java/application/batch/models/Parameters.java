package application.batch.models;

import com.beust.jcommander.JCommander;
import lombok.Getter;
import lombok.Setter;
import com.beust.jcommander.Parameter;

import java.util.Properties;

/**
 * Batch Application parameters
 */
public class Parameters {
    @Getter @Setter
    @Parameter
    private String outputPath;

    @Getter @Setter
    @Parameter(names = "--spark-conf", converter = SparkConfConverter.class)
    private Properties sparkConf = SparkConfConverter.parseFromString("spark.master=local[*],spark.app.name=BrasilOpenDataETL");

    public Parameters(){}
    public Parameters(String[] argv){
        System.out.println("Parsing parameters..");
        this.parse(argv);
    }

    /**
     * Parse batch parameters
     */
    public void parse(String[] argv){
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(argv);
    }
}
