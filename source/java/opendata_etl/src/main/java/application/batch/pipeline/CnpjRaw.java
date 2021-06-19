package application.batch.pipeline;

import application.batch.contracts.IPipeline;
import application.batch.enums.FileFormat;
import application.batch.enums.FileType;
import application.batch.mappers.cnpj.*;
import application.batch.models.FromTextFileModel;
import application.batch.models.args.Parameters;
import application.batch.models.cnpj.Company;
import application.batch.models.cnpj.Establishment;
import application.batch.models.cnpj.Partner;
import application.batch.models.cnpj.SimpleNational;
import application.batch.models.cnpj.genericcodes.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class CnpjRaw implements IPipeline {

    //#region constants
    /**
     * Glob pattern to filter input files of type Simple National from CNPJ dataset.
     */
    private static final String SIMPLE_NATIONAL_RAW_GLOB = "*.SIMPLES.*";
    /**
     * Default folder where this program will saves output files of type Simple National from CNPJ dataset.
     */
    private static final String SIMPLE_NATIONAL_FOLDER = "simple_national";
    /**
     * Glob pattern to filter input files of type Establishment from CNPJ dataset.
     */
    private static final String ESTABLISHMENT_RAW_GLOB = "*.ESTABELE";
    /**
     * Default folder where this program will saves output files of type Establishment from CNPJ dataset.
     */
    private static final String ESTABLISHMENT_FOLDER = "establishment";

    /**
     * Glob pattern to filter input files of type Company from CNPJ dataset.
     */
    private static final String COMPANY_RAW_GLOB = "*.EMPRECSV";
    /**
     * Default folder where this program will saves output files of type Company from CNPJ dataset.
     */
    private static final String COMPANY_FOLDER = "company";
    /**
     * Default folder where this program will saves output files resulting of join between Company and Establishment datasets from CNPJ dataset.
     */
    private static final String FULL_COMPANY_FOLDER = "full_company";
    /**
     * Glob pattern to filter input files of type Partner from CNPJ dataset.
     */
    private static final String PARTNER_RAW_GLOB = "*.SOCIOCSV";
    /**
     * Default folder where this program will saves output files of type Partner from CNPJ dataset.
     */
    private static final String PARTNER_FOLDER = "partner";
    /**
     * Glob pattern to filter input files of type City Code from CNPJ dataset.
     */
    private static final String CITY_CODE_RAW_GLOB = "*.MUNICCSV";
    /**
     * Default folder where this program will saves output files of type City Code from CNPJ dataset.
     */
    private static final String CITY_CODE_FOLDER = "city_code";
    /**
     * Glob pattern to filter input files of type CNAE Code from CNPJ dataset.
     */
    private static final String CNAE_CODE_RAW_GLOB = "*.CNAECSV";
    /**
     * Default folder where this program will saves output files of type CNAE Code from CNPJ dataset.
     */
    private static final String CNAE_CODE_FOLDER = "cnae_code";
    /**
     * Glob pattern to filter input files of type Country Code from CNPJ dataset.
     */
    private static final String COUNTRY_CODE_RAW_GLOB = "*.PAISCSV";
    /**
     * Default folder where this program will saves output files of type Country Code from CNPJ dataset.
     */
    private static final String COUNTRY_CODE_FOLDER = "country_code";
    /**
     * Glob pattern to filter input files of type Legal Nature Code from CNPJ dataset.
     */
    private static final String LEGAL_NATURE_CODE_RAW_GLOB = "*.NATJUCSV";
    /**
     * Default folder where this program will saves output files of type Legal Nature Code from CNPJ dataset.
     */
    private static final String LEGAL_NATURE_CODE_FOLDER = "legal_nature_code";
    /**
     * Glob pattern to filter input files of type Partner Qualification Code from CNPJ dataset.
     */
    private static final String PARTNER_QUALIFICATION_CODE_RAW_GLOB = "*.QUALSCSV";
    /**
     * Default folder where this program will saves output files of type Partner Qualification Code from CNPJ dataset.
     */
    private static final String PARTNER_QUALIFICATION_CODE_FOLDER = "partner_qualification_code";
    /**
     * Default folder for files resulting from raw data analysis.
     */
    private static final String OUTPUT_FOLDER_FOR_RAW_ANALYSE = "analyze";
    //#endregion

    @Override
    public void Start(SparkSession sparkSession, Parameters parameters)
            throws Exception {
        //todo decide about sqllite output - https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            //not implemented yet - needs to read without change de datatypes or add columns, just read the original format and write it to output format
            throw new Exception("Output format FileType.cnpj_raw not implemented yet!");
        }

        //parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\");
        parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\");
        //parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfiles-PRD\\");
        //parameters.setInputPath("E:\\hdfs\\cnpj\\2021-05\\");//
        parameters.setOutputFileFormat(FileFormat.orc);

        debugSchemas(sparkSession);

        //this.loadTest(sparkSession);

        //this.analyzeRawData(sparkSession, parameters);

        //runTransformation(sparkSession,parameters,true);


    }

    @SuppressWarnings("unused")
    private void analyzeRawData(SparkSession sparkSession, Parameters parameters)
            throws ReflectiveOperationException {
        //this.analyzeRawData(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER, EstablishmentsRawToModel.class, Establishment.class, EstablishmentSchema.getSchema());
        this.analyzeRawData2(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER, EstablishmentsStringRawToModel.class, Establishment.class, true);
        this.analyzeRawData2(sparkSession, parameters, COMPANY_RAW_GLOB, COMPANY_FOLDER, CompanyStringRawToModel.class, Company.class, true);
        this.analyzeRawData2(sparkSession, parameters, SIMPLE_NATIONAL_RAW_GLOB, SIMPLE_NATIONAL_FOLDER, SimpleNationalStringRawToModel.class, SimpleNational.class, true);
        this.analyzeRawData2(sparkSession, parameters, PARTNER_RAW_GLOB, PARTNER_FOLDER, PartnerStringRawToModel.class, Partner.class, true);
    }

    /**
     * Analyze the raw data from the file and saves an output file with errors found.
     * @param sparkSession Spark session
     * @param parameters App params
     * @param pathGlobFilter glob filter
     * @param defautFolder defaut folder to save files
     * @param mapperClass mapper to transform raw data to model
     * @param modelClass target model class for the mapper
     * @param <T> Model type
     * @param <U> Mapper type
     * @throws NoSuchMethodException NoSuchMethodException
     * @throws IllegalAccessException IllegalAccessException
     * @throws InvocationTargetException InvocationTargetException
     * @throws InstantiationException InstantiationException
     */
    private <T, U extends MapFunction<Row, T>> void analyzeRawData(
            SparkSession sparkSession,
            Parameters parameters,
            String pathGlobFilter,
            String defautFolder,
            final Class<U> mapperClass,
            final Class<T> modelClass,
            StructType schema)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, pathGlobFilter, defautFolder, true, schema);
        Dataset<T> dataset = sourceDf.map(mapperClass.getDeclaredConstructor().newInstance(), Encoders.bean(modelClass)).cache();
        Dataset<T> datasetOfErrors = dataset.filter(dataset.col("rawData").isNotNull()).cache();

        debugDataSet(datasetOfErrors);

        DataFrameWriter<T> dfWriter = getDataFrameWriter(datasetOfErrors, parameters);
        String outputPath = Paths.get(parameters.getInputPath(), OUTPUT_FOLDER_FOR_RAW_ANALYSE, defautFolder).toString();
        dfWriter.save(outputPath);
    }

    /**
     * Analyze the raw data from the file and saves an output file with errors found.
     * @param sparkSession Spark session
     * @param parameters App params
     * @param pathGlobFilter glob filter
     * @param defautFolder defaut folder to save files
     * @param mapperClass mapper to transform raw data to model
     * @param modelClass target model class for the mapper
     * @param <T> Model type
     * @param <U> Mapper type
     * @throws NoSuchMethodException NoSuchMethodException
     * @throws IllegalAccessException IllegalAccessException
     * @throws InvocationTargetException InvocationTargetException
     * @throws InstantiationException InstantiationException
     */
    @SuppressWarnings("SameParameterValue")
    private <T, U extends FlatMapFunction<Iterator<String>, T>> void analyzeRawData2(
            SparkSession sparkSession,
            Parameters parameters,
            String pathGlobFilter,
            String defautFolder,
            final Class<U> mapperClass,
            final Class<T> modelClass,
            boolean cache)
            throws ReflectiveOperationException {

        Dataset<Row> dataset  = this.getDataFrame(sparkSession, parameters, pathGlobFilter, defautFolder, mapperClass, modelClass, cache);
        Dataset<Row> datasetOfErrors = dataset.filter(dataset.col("rawData").isNotNull()).cache();

        debugDataSet(datasetOfErrors);

        DataFrameWriter<Row> dfWriter = getDataFrameWriter(datasetOfErrors, parameters);
        String outputPath = Paths.get(parameters.getInputPath(), OUTPUT_FOLDER_FOR_RAW_ANALYSE, defautFolder).toString();
        dfWriter.save(outputPath);
    }

    /**
     * Read input files and return a dataframe
     * @param sparkSession Spark session
     * @param parameters App params
     * @param pathGlobFilter glob filter to filter files to read
     * @param defautFolder defaut folder to read files
     * @param mapperClass mapper to transform raw data to model
     * @param modelClass target model class for the mapper
     * @param cache If true cache the dataframe
     * @param <T> Model type
     * @param <U> Mapper type
     * @throws ReflectiveOperationException ReflectiveOperationException
     * @see <a href="https://spark.apache.org/docs/latest/rdd-programming-guide.html">textfile options</a>
     */
    private <T, U extends FlatMapFunction<Iterator<String>, T>> Dataset<Row> getDataFrame(
            SparkSession sparkSession,
            Parameters parameters,
            String pathGlobFilter,
            String defautFolder,
            final Class<U> mapperClass,
            final Class<T> modelClass,
            boolean cache)
            throws ReflectiveOperationException
    {
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
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

        String path = inputPath.toString();

        if(pathGlobFilter != null){
            path = inputPath.toString() + File.separator + pathGlobFilter;
        }

        /*
        If using a path on the local filesystem, the file must also be accessible at the same path on worker nodes.
        Either copy the file to all workers or use a network-mounted shared file system.

        All of Spark’s file-based input methods, including textFile, support running on directories, compressed files,
        and wildcards as well. For example, you can use textFile("/my/directory"), textFile("/my/directory/*.txt"), and textFile("/my/directory/*.gz").

        The textFile method also takes an optional second argument for controlling the number of partitions of the file.
        By default, Spark creates one partition for each block of the file (blocks being 128MB by default in HDFS),
        but you can also ask for a higher number of partitions by passing a larger value. Note that you cannot have fewer partitions than blocks.
         */
        JavaRDD<String> csvLines = sparkContext.textFile(path);
        JavaRDD<T> est = csvLines.mapPartitions(mapperClass.getDeclaredConstructor().newInstance());
        //List<T> list = est.collect();

        Dataset<Row> df = sparkSession.createDataFrame(est, modelClass);

        if(cache)
            df = df.cache();

        return df;
    }

    @SuppressWarnings("unused")
    private <T> void debugDataSet(Dataset<T> ds) {
        //List<T> list = ds.collectAsList();
        ds.show(5);
        ds.printSchema();
        long total = ds.count();
        System.out.println("*** total records: " + total);
    }

    public void debugSchemas(SparkSession spark){
        DataFrameReader reader = spark.read()
                .format("orc")
                ;
        //if(schema != null)
            //reader = reader.schema(schema);

        Dataset<Row> df = reader.load("E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\2021-06-19\\company\\part-00000-1f74b08f-c39b-4fc5-b879-5369cc38e22f-c000.snappy.orc");
        debugDataSet(df);

        //String establisment = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles-PRD\\orc\\establishment\\part-00319-35da67fe-1851-4628-93f6-9cd3d1e2b799-c000.snappy.orc";
        //Dataset<Row> df2 = reader.load(establisment);
        //debugDataSet(df2);

        //String country_code = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles-PRD\\orc\\country_code\\part-00000-d5ae1e49-7ac4-415d-bcef-bc43e0837742-c000.snappy.orc";
        //Dataset<Row> df3 = reader.load(country_code);
        //debugDataSet(df3);

        //String partner = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles-PRD\\orc\\partner\\part-00066-0da314b6-d491-4aa1-8cce-5b053e7e1dd8-c000.snappy.orc";
        //Dataset<Row> df4 = reader.load(partner);
        //debugDataSet(df4);

        //String simple = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles-PRD\\orc\\simple_national\\part-00048-66866090-95bf-4e9f-8563-1287f9848d9a-c000.snappy.orc";
        //Dataset<Row> df5 = reader.load(simple);
        //debugDataSet(df5);
    }

    @SuppressWarnings("unused")
    public void loadTest(SparkSession sparkSession){
        String path = "E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\K3241.K03200Y9.D10410.ESTABELE";

        //JavaRDD do not extends scala RDD
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
        JavaRDD<String> csvLines = sparkContext.textFile(path);

        JavaRDD<Establishment> est = csvLines.mapPartitions(new EstablishmentsStringRawToModel());
        List<Establishment> list = est.collect();
        Dataset<Row> df = sparkSession.createDataFrame(est,Establishment.class);
        debugDataSet(df);
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
     * @see <a href="http://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/sql/Dataset.html">Dataset</a>
     */
    public Dataset<Row> getDataFrame(SparkSession spark, final Parameters parameters, String pathGlobFilter, final String defautFolder, final boolean cache) {
        return this.getDataFrame(spark, parameters, pathGlobFilter, defautFolder, cache, null);
    }

    /**
     * Create and returns one dataframe for reading the data.
     * (Retorna um dataframe de leitura dos dados)
     * @param spark Spark Session
     * @param parameters App parameters
     * @param pathGlobFilter Path Global Filter (glob pattern)
     * @param defautFolder Default folder where this program saves output files for this type of archive
     * @param schema Schema for reading the data
     * @return Returns one dataframe for reading the data.
     *
     * @see <a href="https://spark.apache.org/docs/latest/sql-data-sources-generic-options.html#path-global-filter">path-global-filter</a>
     * @see <a href="https://mincong.io/2019/04/16/glob-expression-understanding/">glob-expression-understanding</a>
     * @see <a href="http://spark.apache.org/docs/latest/api/java/index.html?org/apache/spark/sql/Dataset.html">Dataset</a>
     */
    public Dataset<Row> getDataFrame(SparkSession spark, final Parameters parameters, String pathGlobFilter, final String defautFolder, final boolean cache, final StructType schema)
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

        //spark.sparkContext().textFile()

        //seealso: http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv
        //seealso: http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html?highlight=textfile
        DataFrameReader reader = spark.read()
                .format(inputFormat.toString().toLowerCase())
                .option("inferSchema", "false")
                .option("header", "false")
                .option("sep",";")
                .option("encoding","ISO-8859-1")
                .option("mode","PERMISSIVE")
                .option("columnNameOfCorruptRecord","rawData")
                //.option("unescapedQuoteHandling","STOP_AT_DELIMITER")
                //.option("quote", "")// turn off quotations to handle manually
                ;

        if(pathGlobFilter != null)
            reader = reader.option("pathGlobFilter", pathGlobFilter);

        if(schema != null)
            reader = reader.schema(schema);

        Dataset<Row> df = reader.load(inputPath.toString());

        if(cache)
            df.cache();

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
            dfWriter.option("header","true");
        }

        return dfWriter;
    }

    /**
     * Execute the Transformations jobs.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runTransformation(SparkSession sparkSession, Parameters parameters, boolean cache) throws Exception {
        runGenericCodeTransformation(sparkSession,parameters,cache);
        runPartnerTransformation(sparkSession,parameters,cache);
        runEstablishmentCompanyTransformation(sparkSession,parameters,cache);
        runSimpleNationalTransformation(sparkSession,parameters,cache);
    }

    /**
     * Execute the Simple National Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runSimpleNationalTransformation(SparkSession sparkSession, Parameters parameters, boolean cache) throws ReflectiveOperationException {
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, SIMPLE_NATIONAL_RAW_GLOB, SIMPLE_NATIONAL_FOLDER, SimpleNationalStringRawToModel.class, SimpleNational.class, cache);
        sourceDf = trackErrors(sourceDf, parameters, SIMPLE_NATIONAL_FOLDER, SimpleNational.getColumns());

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }
    }

    public Dataset<Row> trackErrors(Dataset<Row> sourceDs, Parameters parameters, String datasetName, Seq<Column> columnsToReturn){
        boolean containDebugColumns = false;
        for (String c : sourceDs.columns()) {
            if (c.equals("rawData") || c.equals("parseErrorMessage")) {
                containDebugColumns = true;
                break;
            }
        }

        if(!containDebugColumns) {
            //theres no debug columns, so return the dataset
            if(columnsToReturn != null)
                return sourceDs.select(columnsToReturn);
            return sourceDs;
        }

        //create errors dataset
        List<Column> columns = Arrays.asList(new Column("rawData"),new Column("parseErrorMessage"));
        Dataset<Row> errors = sourceDs.select(JavaConverters.asScalaIteratorConverter(columns.iterator()).asScala().toSeq());
        errors = errors.filter(errors.col("rawData").isNotNull());

        //remove debug columns from the dataset
        Dataset<Row> resultDataset = FromTextFileModel.dropDebugColumns(sourceDs);
        //select the requested columns if requested
        if(columnsToReturn != null)
            resultDataset = resultDataset.select(columnsToReturn);

        //save errors on disc if exists
        if(errors.rdd().isEmpty())
            return resultDataset;

        DataFrameWriter<Row> dfWriter = errors.write().format(FileFormat.parquet.toString().toLowerCase());
        dfWriter.mode(SaveMode.Overwrite);
        dfWriter.save(Paths.get(parameters.getInputPath(), "errors", datasetName).toString());

        return resultDataset;
    }

    /**
     * Execute the Establishment and Company Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runEstablishmentCompanyTransformation(SparkSession sparkSession, Parameters parameters, boolean cache) throws ReflectiveOperationException {
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER, EstablishmentsStringRawToModel.class, Establishment.class, cache);
        sourceDf = trackErrors(sourceDf, parameters, ESTABLISHMENT_FOLDER, Establishment.getColumns());
        Dataset<Row> companySourceDf  = this.getDataFrame(sparkSession, parameters, COMPANY_RAW_GLOB, COMPANY_FOLDER, CompanyStringRawToModel.class, Company.class, cache);
        companySourceDf = trackErrors(companySourceDf, parameters, COMPANY_FOLDER, Company.getColumns());

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), ESTABLISHMENT_FOLDER).toString());

            DataFrameWriter<Row> companyDfWriter = getDataFrameWriter(companySourceDf, parameters);
            companyDfWriter.save(Paths.get(parameters.getInputPath(), COMPANY_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), ESTABLISHMENT_FOLDER).toString());

            //transform to lake model
            DataFrameWriter<Row> companyDfWriter = getDataFrameWriter(companySourceDf, parameters);
            companyDfWriter.save(Paths.get(parameters.getInputPath(), COMPANY_FOLDER).toString());

            /*
            //Unify establishment and company dataframes
            ArrayList<String> columns = new ArrayList<>();
            columns.add("basicCnpj");
            Dataset<Row> newEstablismentDf = FromTextFileModel.dropDebugColumns(sourceDf);
            Dataset<Row> joinedDs = newEstablismentDf.join(FromTextFileModel.dropDebugColumns(companySourceDf), convertListToSeq(columns), "left");

            DataFrameWriter<Row> joinedDsDfWriter = getDataFrameWriter(joinedDs, parameters);
            joinedDsDfWriter.save(Paths.get(parameters.getInputPath(), FULL_COMPANY_FOLDER).toString());
            */
        }
    }

    /**
     * Execute the Partner Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runPartnerTransformation(SparkSession sparkSession, Parameters parameters, boolean cache) throws ReflectiveOperationException {
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, PARTNER_RAW_GLOB, PARTNER_FOLDER, PartnerStringRawToModel.class, Partner.class, cache);
        sourceDf = trackErrors(sourceDf, parameters, PARTNER_FOLDER, Partner.getColumns());

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_FOLDER).toString());
        }
    }

    /**
     * Execute the Generic Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runGenericCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache) throws Exception {
        runGenericCodeTransformation(sparkSession, parameters, CITY_CODE_RAW_GLOB, CITY_CODE_FOLDER, CityCodeStringRawToModel.class, CityCode.class);
        runGenericCodeTransformation(sparkSession, parameters, CNAE_CODE_RAW_GLOB, CNAE_CODE_FOLDER, CnaeCodeStringRawToModel.class, CnaeCode.class);
        runGenericCodeTransformation(sparkSession, parameters, COUNTRY_CODE_RAW_GLOB, COUNTRY_CODE_FOLDER, CountryCodeStringRawToModel.class, CountryCode.class);
        runGenericCodeTransformation(sparkSession, parameters, LEGAL_NATURE_CODE_RAW_GLOB, LEGAL_NATURE_CODE_FOLDER, LegalNatureCodeStringRawToModel.class, LegalNatureCode.class);
        runGenericCodeTransformation(sparkSession, parameters, PARTNER_QUALIFICATION_CODE_RAW_GLOB, PARTNER_QUALIFICATION_CODE_FOLDER, PartnerQualificationCodeStringRawToModel.class, PartnerQualificationCode.class);
    }

    /**
     * Run a generic code transformation.
     * @param <T> Model type
     * @param <U> Mapper type
     * @param sparkSession Spark session
     * @param parameters App params
     * @param pathGlobFilter glob filter to filter files to read
     * @param defautFolder defaut folder to read files
     * @param mapperClass mapper to transform raw data to model
     * @param modelClass target model class for the mapper
     * @throws ReflectiveOperationException ReflectiveOperationException
     * @see <a href="https://spark.apache.org/docs/latest/rdd-programming-guide.html">textfile options</a>
     */
    private <T extends GenericCode, U extends FlatMapFunction<Iterator<String>, T>> void runGenericCodeTransformation(SparkSession sparkSession, Parameters parameters, String pathGlobFilter, String defautFolder, final Class<U> mapperClass, final Class<T> modelClass)
            throws Exception
    {
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, pathGlobFilter, defautFolder, mapperClass, modelClass, true);
        sourceDf = trackErrors(sourceDf, parameters, defautFolder, GenericCode.getColumns());

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), defautFolder).toString());
        }
    }

    public Seq<String> convertListToSeq(List<String> inputList) {
        return JavaConverters.asScalaIteratorConverter(inputList.iterator()).asScala().toSeq();
    }
}
