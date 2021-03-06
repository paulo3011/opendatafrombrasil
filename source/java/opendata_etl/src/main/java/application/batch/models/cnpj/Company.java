package application.batch.models.cnpj;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.spark.sql.Column;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Company extends FromTextFileModel {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NUMERO BASE DE INSCRICAO NO CNPJ (OITO PRIMEIROS DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    /**
     * CORPORATE NAME IS YOUR COMPANY'S REGISTERED NAME.
     *
     * NOME EMPRESARIAL DA PESSOA JURIDICA
     */
    @Getter @Setter
    private String legalName;
    /**
     * CODE OF LEGAL NATURE
     *
     * CÓDIGO DA NATUREZA JURIDICA
     */
    @Getter @Setter
    private Integer legalNature;
    /**
     * QUALIFICATION OF THE INDIVIDUAL RESPONSIBLE FOR THE COMPANY
     *
     * QUALIFICACAO DA PESSOA FISICA RESPONSAVEL PELA EMPRESA
     */
    @Getter @Setter
    private Short responsibleQualification;
    /**
     * COMPANY SHARE CAPITAL
     *
     * CAPITAL SOCIAL DA EMPRESA
     */
    @Getter @Setter
    private BigDecimal companyCapital;
    /**
     * COMPANY PORT CODE:
     * 1  - NOT INFORMED
     * 2  - MICRO COMPANY
     * 03 - SMALL COMPANY
     * 05 - OTHERS
     *
     * CÓDIGO DO PORTE DA EMPRESA:
     * 1  – NAO INFORMADO
     * 2  - MICRO EMPRESA
     * 03 - EMPRESA DE PEQUENO PORTE
     * 05 - DEMAIS
     */
    @Getter @Setter
    private Short companySize;
    /**
     * THE RESPONSIBLE FEDERATIVE ENTITY IS COMPLETED FOR THE CASES OF ORGANS AND ENTITIES OF THE GROUP OF LEGAL NATURE 1XXX. FOR OTHER NATURES, THIS ATTRIBUTE IS BLANK.
     *
     * O ENTE FEDERATIVO RESPONSAVEL E PREENCHIDO PARA OS CASOS DE ORGAOS E ENTIDADES DO GRUPO DE NATUREZA JURIDICA 1XXX. PARA AS DEMAIS NATUREZAS, ESTE ATRIBUTO FICA EM BRANCO.
     */
    @Getter @Setter
    private String federatedEntityResponsible;

    public static List<String> getColumnList(){
        return Arrays.asList("basicCnpj","legalName","legalNature","responsibleQualification","companyCapital","companySize","federatedEntityResponsible");
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
