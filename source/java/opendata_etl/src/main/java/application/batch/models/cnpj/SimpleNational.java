package application.batch.models.cnpj;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.Column;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleNational extends FromTextFileModel {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NÚMERO BASE DE INSCRICAO NO CNPJ (OITO PRIMEIROS DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    @Getter @Setter
    private Boolean isSimple;
    @Getter @Setter
    private Date simpleOptionDate;
    @Getter @Setter
    private Date simpleExclusionDate;
    @Getter @Setter
    private Boolean isMei;
    @Getter @Setter
    private Date meiOptionDate;
    @Getter @Setter
    private Date meiExclusionDate;

    public static List<String> getColumnList(){
        return Arrays.asList("basicCnpj","isSimple","simpleOptionDate","simpleExclusionDate","isMei","meiOptionDate","meiExclusionDate");
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
