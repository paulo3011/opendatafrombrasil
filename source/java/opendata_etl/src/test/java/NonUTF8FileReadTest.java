import application.batch.mappers.cnpj.PartnerQualificationCodeStringRawToModel;
import application.batch.models.cnpj.genericcodes.PartnerQualificationCode;
import application.batch.utils.SparkTextUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class NonUTF8FileReadTest implements Serializable {

    private final String charset8859 = "ISO-8859-1";
    private final String charsetUtf8 = "UTF-8";
    private String partnerFile8859 = "src/test/resources/D10410.QUALSCSV";

    public SparkSession getOrCreateSparkSession(){
        SparkConf conf = new SparkConf().setAppName("SparkSample").setMaster("local[*]");
        SparkSession sparkSession = SparkSession
                .builder()
                .config(conf)
                .getOrCreate();

        return sparkSession;
    }

    @Test
    public void getPartnerQualificationWithCharset() throws UnsupportedEncodingException, FileNotFoundException {
        SparkSession sparkSession = getOrCreateSparkSession();
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());

        JavaRDD<String> csvLines = SparkTextUtils.withCharset(sparkContext, partnerFile8859, StandardCharsets.ISO_8859_1.toString());
        //JavaRDD<String> csvLines = sparkContext.textFile(path);
        JavaRDD<PartnerQualificationCode> est = csvLines.mapPartitions(new PartnerQualificationCodeStringRawToModel());
        Dataset<Row> df = sparkSession.createDataFrame(est, PartnerQualificationCode.class);
        df.show(50);
    }
}
