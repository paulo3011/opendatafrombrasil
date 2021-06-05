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
import application.batch.schemes.cnpj.CompanySchema;
import application.batch.schemes.cnpj.EstablishmentSchema;
import application.batch.schemes.cnpj.PartnerSchema;
import application.batch.schemes.cnpj.SimpleNationalSchema;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CnpjRaw implements IPipeline {

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

    @Override
    public void Start(SparkSession sparkSession, Parameters parameters) {
        //todo decide about sqllite output - https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html
        parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\");
        //parameters.setInputPath("E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\");
        //parameters.setOutputFileFormat(FileFormat.orc);

        //this.analyzeRawData(sparkSession, parameters);

        runTransformation(sparkSession,parameters,true);
    }

    @SuppressWarnings("unused")
    private void analyzeRawData(SparkSession sparkSession, Parameters parameters)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        this.analyzeRawData(sparkSession, parameters, COMPANY_RAW_GLOB, COMPANY_FOLDER, CompanyRawToModel.class, Company.class, CompanySchema.getSchema());
        this.analyzeRawData(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER, EstablishmentsRawToModel.class, Establishment.class, EstablishmentSchema.getSchema());
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
        debugDataSet(sourceDf);
        Dataset<T> dataset = sourceDf.map(mapperClass.getDeclaredConstructor().newInstance(), Encoders.bean(modelClass)).cache();

        debugDataSet(dataset);

        Dataset<T> datasetOfErrors = dataset.filter(dataset.col("rawData").isNotNull()).cache();

        debugDataSet(datasetOfErrors);

        DataFrameWriter<T> dfWriter = getDataFrameWriter(datasetOfErrors, parameters);
        String outputPath = Paths.get(parameters.getInputPath(), OUTPUT_FOLDER_FOR_RAW_ANALYSE, defautFolder).toString();
        dfWriter.save(outputPath);
    }

    @SuppressWarnings("unused")
    private <T> void debugDataSet(Dataset<T> ds) {
        //List<T> list = ds.collectAsList();
        ds.show(5);
        ds.printSchema();
        long total = ds.count();
        System.out.println("*** total records: " + total);
    }

    @SuppressWarnings("unused")
    public void loadTest(SparkSession sparkSession){
        DataFrameReader reader = sparkSession.read()
                .schema(CompanySchema.getSchema())
                .format("csv")
                .option("inferSchema", "false")
                .option("header", "false")
                .option("sep",";")
                .option("encoding","ISO-8859-1")
                ;

        Dataset<Row> df = reader.load("E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\K3241.K03200Y0.D10410.EMPRECSV").cache();
        this.debugDataSet(df);
        Dataset<Company> dfc = df.map(new CompanyRawToModel(), Encoders.bean(Company.class));
        Object r = dfc.collect();
        this.debugDataSet(dfc);
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
        DataFrameReader reader = spark.read()
                .format(inputFormat.toString().toLowerCase())
                .option("inferSchema", "false")
                .option("header", "false")
                .option("sep",";")
                .option("encoding","ISO-8859-1")
                .option("mode","PERMISSIVE")
                .option("columnNameOfCorruptRecord","rawData")
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
    public void runSimpleNationalTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, SIMPLE_NATIONAL_RAW_GLOB, SIMPLE_NATIONAL_FOLDER, cache, SimpleNationalSchema.getSchema());
        //debugDataSet(sourceDf);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //debugDataSet(simpleNationalDataset);
            //transform to lake model
            Dataset<SimpleNational> dataset = sourceDf.map(new SimpleNationalRawToModel(), Encoders.bean(SimpleNational.class));
            DataFrameWriter<SimpleNational> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), SIMPLE_NATIONAL_FOLDER).toString());
        }
    }

    /**
     * Execute the Establishment and Company Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runEstablishmentCompanyTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, ESTABLISHMENT_RAW_GLOB, ESTABLISHMENT_FOLDER, cache, EstablishmentSchema.getSchema());
        Dataset<Row> companySourceDf  = this.getDataFrame(sparkSession, parameters, COMPANY_RAW_GLOB, COMPANY_FOLDER, cache, CompanySchema.getSchema());

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
            Dataset<Establishment> establishmentDataset = sourceDf.map(new EstablishmentsRawToModel(), Encoders.bean(Establishment.class));
            debugDataSet(establishmentDataset);

            DataFrameWriter<Establishment> dfWriter = getDataFrameWriter(establishmentDataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), ESTABLISHMENT_FOLDER).toString());

            //transform to lake model
            Dataset<Company> companyDataset = companySourceDf.map(new CompanyRawToModel(), Encoders.bean(Company.class));

            debugDataSet(companyDataset);

            DataFrameWriter<Company> companyDfWriter = getDataFrameWriter(companyDataset, parameters);
            companyDfWriter.save(Paths.get(parameters.getInputPath(), COMPANY_FOLDER).toString());

            //Unify establishment and company dataframes
            ArrayList<String> columns = new ArrayList<>();
            columns.add("basicCnpj");
            Dataset<Row> newEstablismentDf = FromTextFileModel.dropDebugColumns(establishmentDataset);
            Dataset<Row> joinedDs = newEstablismentDf.join(FromTextFileModel.dropDebugColumns(companyDataset), convertListToSeq(columns), "left");

            debugDataSet(joinedDs);

            DataFrameWriter<Row> joinedDsDfWriter = getDataFrameWriter(joinedDs, parameters);
            joinedDsDfWriter.save(Paths.get(parameters.getInputPath(), FULL_COMPANY_FOLDER).toString());
        }
    }

    /**
     * Execute the Partner Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runPartnerTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, PARTNER_RAW_GLOB, PARTNER_FOLDER, cache, PartnerSchema.getSchema());

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<Partner> dataset = sourceDf.map(new PartnerRawToModel(), Encoders.bean(Partner.class));
            DataFrameWriter<Partner> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_FOLDER).toString());
        }
    }

    /**
     * Execute the Generic Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runGenericCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        runCityCodeTransformation(sparkSession, parameters, cache);
        runCnaeCodeTransformation(sparkSession, parameters, cache);
        runCountryCodeTransformation(sparkSession, parameters, cache);
        runLegalNatureCodeTransformation(sparkSession, parameters, cache);
        runPartnerQualificationCodeTransformation(sparkSession, parameters, cache);
    }

    /**
     * Execute the City Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runCityCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, CITY_CODE_RAW_GLOB, CITY_CODE_FOLDER, cache);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), CITY_CODE_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<CityCode> dataset = sourceDf.map(new GenericCodeRawToModel<>(CityCode.class), Encoders.bean(CityCode.class));
            DataFrameWriter<CityCode> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), CITY_CODE_FOLDER).toString());
        }
    }

    /**
     * Execute the CNAE Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runCnaeCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, CNAE_CODE_RAW_GLOB, CNAE_CODE_FOLDER, cache);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), CNAE_CODE_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<CnaeCode> dataset = sourceDf.map(new GenericCodeRawToModel<>(CnaeCode.class), Encoders.bean(CnaeCode.class));
            DataFrameWriter<CnaeCode> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), CNAE_CODE_FOLDER).toString());
        }
    }

    /**
     * Execute the Country Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runCountryCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, COUNTRY_CODE_RAW_GLOB, COUNTRY_CODE_FOLDER, cache);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), COUNTRY_CODE_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<CountryCode> dataset = sourceDf.map(new GenericCodeRawToModel<>(CountryCode.class), Encoders.bean(CountryCode.class));
            DataFrameWriter<CountryCode> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), COUNTRY_CODE_FOLDER).toString());
        }
    }

    /**
     * Execute the Legal Nature Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runLegalNatureCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, LEGAL_NATURE_CODE_RAW_GLOB, LEGAL_NATURE_CODE_FOLDER, cache);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), LEGAL_NATURE_CODE_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<LegalNatureCode> dataset = sourceDf.map(new GenericCodeRawToModel<>(LegalNatureCode.class), Encoders.bean(LegalNatureCode.class));
            DataFrameWriter<LegalNatureCode> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), LEGAL_NATURE_CODE_FOLDER).toString());
        }
    }

    /**
     * Execute the Partner Qualification Codes Transformation.
     * @param sparkSession Spark Session
     * @param parameters App parameters
     * @param cache Save dataframe on cache if true
     */
    public void runPartnerQualificationCodeTransformation(SparkSession sparkSession, Parameters parameters, boolean cache){
        Dataset<Row> sourceDf  = this.getDataFrame(sparkSession, parameters, PARTNER_QUALIFICATION_CODE_RAW_GLOB, PARTNER_QUALIFICATION_CODE_FOLDER, cache);

        if(parameters.getOutputFileType() == FileType.cnpj_raw)
        {
            //no transformations, can be used to backup in a better format
            DataFrameWriter<Row> dfWriter = getDataFrameWriter(sourceDf, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_QUALIFICATION_CODE_FOLDER).toString());
        }

        if(parameters.getOutputFileType() == FileType.cnpj_lake)
        {
            //transform to lake model
            Dataset<PartnerQualificationCode> dataset = sourceDf.map(new GenericCodeRawToModel<>(PartnerQualificationCode.class), Encoders.bean(PartnerQualificationCode.class));
            DataFrameWriter<PartnerQualificationCode> dfWriter = getDataFrameWriter(dataset, parameters);
            dfWriter.save(Paths.get(parameters.getInputPath(), PARTNER_QUALIFICATION_CODE_FOLDER).toString());
        }
    }

    public Seq<String> convertListToSeq(List<String> inputList) {
        return JavaConverters.asScalaIteratorConverter(inputList.iterator()).asScala().toSeq();
    }
}
