package application.batch.utils;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class SparkTextUtils {
    public static JavaRDD<String> withCharset(JavaSparkContext context, String location, String charset)
    {
        if (Charset.forName(charset) == StandardCharsets.UTF_8) {
            return context.textFile(location,1);
        } else {
            //val file = sparkContext.hadoopFile[LongWritable, Text, TextInputFormat](path)
            // can't pass a Charset object here cause its not serializable
            JavaPairRDD<LongWritable, Text> rdd = context.hadoopFile(location, TextInputFormat.class, LongWritable.class, Text.class);

            JavaRDD<Text> values = rdd.values();

            return values.map(text -> {
                //System.out.printf("textRDD map: %s | orignal string: %s \n",s, text.toString());
                //textRDD map: "08";"Conselheiro de Administração" | orignal string: "08";"Conselheiro de Administra??o"
                return new String(text.getBytes(), 0, text.getLength(), charset);
            });
        }
    }
}
