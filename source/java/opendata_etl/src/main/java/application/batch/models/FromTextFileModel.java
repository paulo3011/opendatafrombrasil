package application.batch.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.io.Serializable;

public class FromTextFileModel implements Serializable {
    /**
     * Raw data with error during parse.
     */
    @Getter
    public String rawData;
    /**
     * Parser error
     */
    @Getter @Setter
    public String parseErrorMessage;

    /**
     * Set the raw data where there was parse error.
     * @param value the row value
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv">About columnNameOfCorruptRecord and PERMISSIVE mode</a>
     */
    public void setRawData(String value){
        this.rawData = value;
    }

    /**
     * Drop debug columns from the dataset
     * @param dataset the dataset to drop columns
     * @param <T> dataset type
     */
    @SuppressWarnings("unused")
    public static <T> Dataset<Row> dropDebugColumns(Dataset<T> dataset){
        return dataset.drop("rawData","parseErrorMessage");
    }
}
