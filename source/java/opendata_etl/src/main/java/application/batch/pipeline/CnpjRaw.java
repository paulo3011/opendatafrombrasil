package application.batch.pipeline;

import application.batch.contracts.IPipeline;
import application.batch.enums.FileFormat;
import application.batch.enums.FileType;
import application.batch.mappers.cnpj.SimpleNationalRawToModel;
import application.batch.models.args.Parameters;
import application.batch.models.cnpj.SimpleNational;
import org.apache.spark.sql.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        parameters.setOutputFileFormat(FileFormat.orc);
        //parameters.setOutputFileFormat(FileFormat.parquet);
        runTransformation(sparkSession,parameters,true);
    }

    @SuppressWarnings("unused")
    private <T> void debugDataSet(Dataset<T> ds) {
        List<T> list = ds.collectAsList();
        ds.show(5);
        ds.printSchema();
        long total = ds.count();
        System.out.println("*** total records: " + total);
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
    public Dataset<Row> getDataFrame(SparkSession spark, Parameters parameters, String pathGlobFilter, String defautFolder, boolean cache)
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

        if(cache)
            df.cache();

        /*
        System.out.printf("*** %s ingested in a dataframe \n", pathGlobFilter);
        df.show(5);
        df.printSchema();
        long total = df.count(); //27600101
        System.out.println("*** total records: " + total);
        */

        return df;
    }

    /**
     * Create and returns one generic dataframe writer to output data to output path.
     * (Retorna um escritor genérico de dataframe para salvar os dados)
     * @param dataset The dataset for write to output
     * @param parameters The app parameters
     * @param <T> The type of rows of the dataset
     * @return DataFrameWriter<T>
     *
     * @see <a href="https://spark.apache.org/docs/latest/api/java/org/apache/spark/sql/DataFrameWriter.html">DataFrameWriter Mode - Java</a>
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.mode.html#pyspark.sql.DataFrameWriter.mode">DataFrameWriter Mode - Python</a>
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.option.html#pyspark.sql.DataFrameWriter.option">About timezone option</a>
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.csv.html#pyspark.sql.DataFrameWriter.csv">CSV options</a>
     */
    public <T> DataFrameWriter<T> getDataFrameWriter(Dataset<T> dataset, Parameters parameters)
    {
        FileFormat outputFormat = parameters.getOutputFileFormat();

        DataFrameWriter<T> dfWriter = dataset.write().format(outputFormat.toString().toLowerCase());
        dfWriter.mode(SaveMode.Overwrite);

        //to improve can be implemented custom options for the target output format in the same way as Parameters.sparkConf
        //e.g. http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameWriter.csv.html#pyspark.sql.DataFrameWriter.csv options can be set using write.option()
        //dfWriter.option("","");

        if(outputFormat == FileFormat.orc){
            dfWriter.option("compression","snappy");
        }
        if(outputFormat == FileFormat.csv){
            //the same format as original
            dfWriter.option("header","false");
        }

        return dfWriter;
    }

    /**
     * Execute the Transformations jobs.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        runSimpleNationalTransformation(sparkSession,parameters,cache);
    }

    /**
     * Execute the Simple National Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runSimpleNationalTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> simple_national_df  = this.getDataFrame(sparkSession, parameters, SIMPLE_NATIONAL_RAW_GLOB, SIMPLE_NATIONAL_FOLDER, cache);
        //debugDataSet(simple_national_df);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> simpleNationalWriter = getDataFrameWriter(simple_national_df, parameters);
            simpleNationalWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //debugDataSet(simpleNationalDataset);
            //transform to lake model
            Dataset<SimpleNational> simpleNationalDataset = simple_national_df.map(new SimpleNationalRawToModel(), Encoders.bean(SimpleNational.class));
            DataFrameWriter<SimpleNational> simpleNationalWriter = getDataFrameWriter(simpleNationalDataset, parameters);
            simpleNationalWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }
    }
}
