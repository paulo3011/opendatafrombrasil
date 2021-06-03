package application.batch.models.args;

import application.batch.enums.FileFormat;
import application.batch.enums.FileType;
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
    @Parameter(
            names = "--spark-conf",
            converter = SparkConfConverter.class)
    private Properties sparkConf = SparkConfConverter.convertFromString("spark.master=local[*],spark.app.name=BrasilOpenDataETL");

    @Getter @Setter
    @Parameter(
            names = "--input-path",
            description = "The path where the files will be read. It can be a file path, s3, hdfs or one supported by the reader that will be used.")
    private String inputPath = "/tmp/input/";

    @Getter @Setter
    @Parameter(
            names = "--input-type",
            description = "The file type to be read. cnpj_raw or cnpj_lake",
            converter = FileTypeConverter.class
    )
    private FileType inputFileType = FileType.cnpj_raw;

    @Getter @Setter
    @Parameter(
            names = "--input-format",
            description = "The format of the input files. csv or orc",
            converter = FileFormatConverter.class
    )
    private FileFormat inputFileFormat = FileFormat.csv;

    @Getter @Setter
    @Parameter(
            names = "--output-path",
            description = "The path where the files will be saved.")
    private String outputPath = "/tmp/output/";

    @Getter @Setter
    @Parameter(
            names = "--output-type",
            description = "The file type to be saved. cnpj_raw or cnpj_lake",
            converter = FileTypeConverter.class)
    private FileType outputFileType = FileType.cnpj_raw;

    @Getter @Setter
    @Parameter(
            names = "--output-format",
            description = "The format of the input files. csv or orc",
            converter = FileFormatConverter.class
    )
    private FileFormat outputFileFormat = FileFormat.csv;

    @SuppressWarnings("unused")
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
