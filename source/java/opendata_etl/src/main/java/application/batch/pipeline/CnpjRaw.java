package application.batch.pipeline;

import application.batch.contracts.IPipeline;
import application.batch.enums.FileFormat;
import application.batch.models.args.Parameters;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CnpjRaw implements IPipeline {

    /**
     * Glob pattern to filter input files of type Simple National.
     */
    private static final String SIMPLE_NATIONAL_RAW_GLOB = "*.SIMPLES.*";
    /**
     * Default folder where this program will saves output files of type Simple National.
     */
    private static final String SIMPLE_NATIONAL_FOLDER = "simple_national";
    /**
     * Glob pattern to filter input files of type establishment.
     */
    private static final String ESTABLISHMENT_RAW_GLOB = "*.ESTABELE";
    /**
     * Default folder where this program will saves output files of type Establishment
     */
    private static final String ESTABLISHMENT_FOLDER = "establishment";

    @Override
    public void Start(SparkSession sparkSession, Parameters parameters) {
        parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\");

        Dataset<Row> establishment_df  = this.getDataFrame(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER);
        Dataset<Row> simple_national_df  = this.getDataFrame(sparkSession, parameters, SIMPLE_NATIONAL_RAW_GLOB, SIMPLE_NATIONAL_FOLDER);

    }

    private void OrganizeData(Parameters parameters) throws IOException {
        //FileUtils.iterateFiles()
        File dirFile = new File(parameters.getInputPath());
        if (!dirFile.exists() || dirFile.isFile())
            return;

        //* @see org.apache.commons.io.filefilter.FileFilterUtils
        //* @see org.apache.commons.io.filefilter.NameFileFilter

        // simple national
        //FileFilterUtils.prefixFileFilter(simpleNationalFilenamePattern, IOCase.INSENSITIVE);
        File simple_national_dir = new File(parameters.getInputPath() + SIMPLE_NATIONAL_FOLDER);
        FileFilter fileFilter = new WildcardFileFilter(SIMPLE_NATIONAL_RAW_GLOB, IOCase.INSENSITIVE);
        File[] files = dirFile.listFiles(fileFilter);

        for (File file : files) {
            File destinationFile = new File(simple_national_dir + file.getName());
            FileUtils.moveFile(file, destinationFile);
        }
    }


    /**
     * Create and returns one dataframe for reading the data.
     * (Retorna um dataframe de leitura dos dados)
     * @param spark Spark Session
     * @param parameters App parameters
     * @param pathGlobFilter Path Global Filter (glob pattern)
     * @param defautFolder Default folder where this program saves output files for this type of archive
     * @return Returns one dataframe for reading the data.
     *
     * @see <a href="https://spark.apache.org/docs/latest/sql-data-sources-generic-options.html#path-global-filter">path-global-filter</a>
     * @see <a href="https://mincong.io/2019/04/16/glob-expression-understanding/">glob-expression-understanding</a>
     */
    public Dataset<Row> getDataFrame(SparkSession spark, Parameters parameters, String pathGlobFilter, String defautFolder)
    {
        /*
        Rules:
        1. If input file format == csv (original raw format) then all files needs to be in the same folder (original organization)
        2. If input file format != csv then all files needs to ben in the default output folder (default name)
        */
        FileFormat inputFormat = parameters.getInputFileFormat();

        Path inputPath = Paths.get(parameters.getInputPath());
        if (inputFormat != FileFormat.csv){
            inputPath = Paths.get(parameters.getInputPath(), defautFolder);
            pathGlobFilter = null;
        }

        DataFrameReader reader = spark.read()
                .format(inputFormat.toString().toLowerCase())
                .option("inferSchema", "false")
                .option("header", "false")
                .option("sep",";")
                .option("encoding","ISO-8859-1")
                ;

        if(pathGlobFilter != null)
            reader = reader.option("pathGlobFilter", pathGlobFilter);

        Dataset<Row> df = reader.load(inputPath.toString());

        System.out.printf("*** %s ingested in a dataframe \n", pathGlobFilter);
        df.show(5);
        df.printSchema();
        long total = df.count(); //27600101
        System.out.println("*** total records: " + total);

        return df;
    }
}
