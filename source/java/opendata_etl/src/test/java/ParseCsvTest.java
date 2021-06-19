import application.batch.mappers.cnpj.CompanyStringRawToModel;
import application.batch.models.cnpj.Company;
import application.batch.utils.CnpjUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.junit.Assert;
import org.junit.Test;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ParseCsvTest {
    @Test
    public void SplitLine_1(){
        String textLine = "\"36452531\";\"0001\";\"62\";\"1\";\"AMPPLA CREATIVE STUDIO\";\"2\";\"20200221\";\"0\";\"\";\"\";\"20200221\";\"1821100\";\"5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100\";\"ESTRADA\";\"DO MANDU\";\"560\";\"EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805\";\"SAO MARCOS\";\"41250400\";\"BA\";\"3849\";\"71\";\"99479533\";\"\";\"\";\"\";\"\";\"JONATASMA@GMAIL.COM\";\"\";\"\"";

        String[] expectedResult = {
                "36452531",
                "0001",
                "62",
                "1",
                "AMPPLA CREATIVE STUDIO",
                "2",
                "20200221",
                "0",
                "",
                "",
                "20200221",
                "1821100",
                "5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100",
                "ESTRADA",
                "DO MANDU",
                "560",
                "EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805",
                "SAO MARCOS",
                "41250400",
                "BA",
                "3849",
                "71",
                "99479533",
                "",
                "",
                "",
                "",
                "JONATASMA@GMAIL.COM",
                "",
                ""
                };

        String[] parts = splitTextLine(textLine);
        Assert.assertEquals(30, parts.length,0);

        checkExpectedResult(parts, expectedResult);
    }

    public String[] splitTextLine(String textLine){
        if(textLine.startsWith("\""))
            textLine = textLine.substring(1);
        if(textLine.endsWith("\""))
            textLine = textLine.substring(0, textLine.length()-1);

        String[] parts = textLine.split("\";\"",-1);
        System.out.printf("parts.length: %s \n", parts.length);
        return parts;
    }

    public void checkExpectedResult(String[] parts, String[] expectedResult){
        for (int i = 0; i < parts.length; i++) {
            System.out.printf("%s == %s \n", parts[i], expectedResult[i]);
            Assert.assertEquals(parts[i], expectedResult[i]);
        }
    }

    @Test
    public void split_2(){
        String textLine = "\"cnpj_basico;separador,terceiro\";\"ordem,1,2\";\"cnpj dv\\\";\"identificador matriz filial\";\"nome fantasia\";\"situacao cadastral\";\"data situacao cadastral\";\"motivo situacao cadastral\";\"cidade exterior\";\"pais\";\"data inicio atividade\";\"cnae principal\";\"cnae secundario\";\"tipo logradouro\";\"logradouro\";\"numero\";\"complemento\";\"bairro\";\"cep\";\"uf\";\"municipio\";\"ddd 1\";\"telefone 1\";\"ddd 2\";\"telefone 2\";\"ddd fax\";\"fax\";\"email\";\"situacao especial\";\"data situacao especial\"";

        String[] expectedResult = {
                "cnpj_basico;separador,terceiro",
                "ordem,1,2",
                "cnpj dv\\",
                "identificador matriz filial",
                "nome fantasia",
                "situacao cadastral",
                "data situacao cadastral",
                "motivo situacao cadastral",
                "cidade exterior",
                "pais",
                "data inicio atividade",
                "cnae principal",
                "cnae secundario",
                "tipo logradouro",
                "logradouro",
                "numero",
                "complemento",
                "bairro",
                "cep",
                "uf",
                "municipio",
                "ddd 1",
                "telefone 1",
                "ddd 2",
                "telefone 2",
                "ddd fax",
                "fax",
                "email",
                "situacao especial",
                "data situacao especial"};

        String[] parts = splitTextLine(textLine);
        Assert.assertEquals(30, parts.length,0);
        checkExpectedResult(parts, expectedResult);

    }

    @Test
    public void removeLeadingZeroes(){
        String regexStr = "^0+(?!$)";
        Assert.assertEquals("1", "001".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "01".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "0001".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "1".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "0008".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "08".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "8".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "0010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "00010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "10".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "0011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "00011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "11".replaceFirst(regexStr,""));
    }

    @Test
    public void dateParseTest(){
        //yyyymmdd
        String dateAsString = "20210614";
        java.sql.Date dt = CnpjUtils.getSqlDate(dateAsString);
        assert dt != null;
        Assert.assertEquals("2021-06-14", dt.toString());
    }

    //
    @Test
    public void listToSeq(){
        //yyyymmdd
        List<String> dateAsString = Arrays.asList("field1", "filed2");
        Seq<String> cols = JavaConverters.asScalaBuffer(dateAsString).toSeq();
        //JavaConverters.asScalaIteratorConverter(columns.iterator()).asScala().toSeq()
    }

    @Test
    public void testBigDecimalScale() throws ParseException {
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        String numberString = "000000010501,03";
        Number number = nf.parse(numberString);

        BigDecimal value = new BigDecimal(number.toString());
        BigDecimal newValue = value.setScale(2);
        String strValue = newValue.toString();

        String maxNumberString = "900000099991,56";
        Number maxNumber = nf.parse(maxNumberString);
        BigDecimal maxValue = new BigDecimal(maxNumber.toString());
        BigDecimal newMaxValue = maxValue.setScale(2);
        String strMaxValue = newMaxValue.toString();
    }

    @Test
    public void testOrcSchemaWithBigdecimal(){
        SparkConf conf = new SparkConf().setAppName("SparkSample").setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqc = new SQLContext(jsc);
        // sample data
        List<String> data = new ArrayList<String>();
        data.add("dev, engg, 10000");
        data.add("karthik, engg, 20000");
        // DataFrame
        Dataset<Row> df = sqc.createDataset(data, Encoders.STRING()).toDF();
        df.printSchema();
        df.show();
        // Convert
        Dataset<Row> df1 = df.selectExpr("split(value, ',')[0] as name", "split(value, ',')[1] as degree","split(value, ',')[2] as salary");
        df1.printSchema();
        df1.show();
    }

    @Test
    public void testOrcSchemaWithBigdecimal2(){
        SparkConf conf = new SparkConf().setAppName("SparkSample").setMaster("local[*]");
        SparkSession sparkSession = SparkSession
                .builder()
                .config(conf)
                .getOrCreate();
        /*
        "41273603";"GRAFLINE ACESSORIOS  GRAFICOS LTDA";"2062";"49";"000000010501,03";"01";""
        "41273604";"RUMO - ESTUDIO DE DANCA LTDA";"2062";"49";"900000099991,56";"01";""
         */

        String[] lines = {
           "\"41273603\";\"GRAFLINE ACESSORIOS  GRAFICOS LTDA\";\"2062\";\"49\";\"000000010501,03\";\"01\";\"\"",
            "\"41273604\";\"RUMO - ESTUDIO DE DANCA LTDA\";\"2062\";\"49\";\"900000099991,56\";\"01\";\"\""
        };

        // Seq<String> seqs = JavaConverters.asScalaBuffer(Arrays.asList(lines)).toSeq();
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());

        JavaRDD<String> csvLines = sparkContext.parallelize(Arrays.asList(lines));
        JavaRDD<Company> est = csvLines.mapPartitions(new CompanyStringRawToModel());
        Dataset<Row> df = sparkSession.createDataFrame(est, Company.class);
        Column newCol = df.col("companyCapital").cast("decimal(14,2)");
        df = df.withColumn("companyCapital2", newCol)
                .drop("companyCapital")
                .withColumnRenamed("companyCapital2","companyCapital")
                .select(Company.getColumns());

        df.printSchema();
        df.show(3);
        df.collect();

    }

    private <T> void debugDataSet(Dataset<T> ds) {
        ds.show(5);
        ds.printSchema();
    }

    public SparkSession getOrCreateSparkSession(){
        SparkConf conf = new SparkConf().setAppName("SparkSample").setMaster("local[*]");
        SparkSession sparkSession = SparkSession
                .builder()
                .config(conf)
                .getOrCreate();
        return sparkSession;
    }

    @Test
    public void debugEstablishmentSchema(){
        SparkSession sparkSession = getOrCreateSparkSession();
        DataFrameReader reader = sparkSession.read().format("orc");

        String path = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\2021-06-19\\establishment\\part-00035-0f735af4-a1a0-4ba7-946e-6c7b7edbee74-c000.snappy.orc";
        Dataset<Row> ds = reader.load(path);
        ds.printSchema();
    }

    @Test
    public void debugPartnerSchema(){
        SparkSession sparkSession = getOrCreateSparkSession();
        DataFrameReader reader = sparkSession.read().format("orc");

        String path = "E:\\hdfs\\cnpj\\2021-04-14\\allfiles\\2021-06-19\\partner\\part-00000-2b965d19-bd1e-4ded-adff-b0a25b3a3419-c000.snappy.orc";
        Dataset<Row> ds = reader.load(path);
        ds.printSchema();

        /*
        root
         |-- basicCnpj: string (nullable = true)
         |-- partnerType: short (nullable = true)
         |-- partnerName: string (nullable = true)
         |-- partnerDocument: string (nullable = true)
         |-- partnerQualification: integer (nullable = true)
         |-- partnerStartDate: date (nullable = true)
         |-- country: integer (nullable = true)
         |-- legalRepresentative: string (nullable = true)
         |-- representativeName: string (nullable = true)
         |-- representativeQualification: integer (nullable = true)
         |-- ageRange: short (nullable = true)
         */
    }
}
