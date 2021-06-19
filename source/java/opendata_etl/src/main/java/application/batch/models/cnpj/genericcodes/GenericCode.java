package application.batch.models.cnpj.genericcodes;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.Column;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericCode extends FromTextFileModel {
    @Getter @Setter
    int code = 0;
    @Getter @Setter
    String description;

    public static List<String> getColumnList(){
        return Arrays.asList("code","description");
    }

    public static Seq<Column> getColumns(){
        List<Column> columns = new ArrayList<>();
        getColumnList().forEach(x -> {
            columns.add(new Column(x));
        });
        return JavaConverters.asScalaBuffer(columns).toSeq();
    }

    public static String getSelectStatement(){
        return String.join(",", getColumnList());
    }
}
