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

/**
 * Partner
 *
 * Sócio
 */
public class Partner extends FromTextFileModel {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NÚMERO BASE DE INSCRICAO NO CNPJ (OITO PRIMEIROS DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    /**
     * MEMBER IDENTIFIER CODE
     * 1 - LEGAL ENTITY
     * 2 - INDIVIDUAL
     * 3 – FOREIGN
     *
     * CODIGO DO IDENTIFICADOR DE SÓCIO
     * 1 – PESSOA JURIDICA
     * 2 – PESSOA FISICA
     * 3 – ESTRANGEIRO
     */
    @Getter @Setter
    private Short partnerType;
    /**
     *
     * NAME OF PARTNER INDIVIDUAL OR CORPORATE NAME AND/OR NAME
     * COMPANY OF THE LEGAL ENTITY AND/OR NAME OF THE
     * PARTNER/CORPORATE NAME OF THE FOREIGN PARTNER
     *
     * NOME DO SÓCIO PESSOA FISICA OU A RAZÃO SOCIAL E/OU NOME
     * EMPRESARIAL DA PESSOA JURIDICA E/OU NOME DO
     * SÓCIO/RAZÃO SOCIAL DO SÓCIO ESTRANGEIRO
     */
    @Getter @Setter
    private String partnerName;
    /**
     * CPF OU CNPJ DO SÓCIO (SÓCIO ESTRANGEIRO NÃO TEM ESTA INFORMAÇÃO).
     *
     * CPF OR CNPJ OF THE PARTNER (FOREIGN PARTNER DOES NOT HAVE THIS INFORMATION).
     */
    @Getter @Setter
    private String partnerDocument;
    /**
     * MEMBER QUALIFICATION CODE
     *
     * CODIGO DA QUALIFICAÇÃO DO SÓCIO
     */
    @Getter @Setter
    private Integer partnerQualification;
    /**
     * DATE OF ENTRY INTO THE COMPANY
     *
     * DATA DE ENTRADA NA SOCIEDADE
     */
    @Getter @Setter
    private Date partnerStartDate;
    /**
     * COUNTRY CODE OF FOREIGN PARTNER
     *
     * CODIGO PAIS DO SÓCIO ESTRANGEIRO
     */
    @Getter @Setter
    private Integer country;
    /**
     * CPF NUMBER OF THE LEGAL REPRESENTATIVE
     *
     * NÚMERO DO CPF DO REPRESENTANTE LEGAL
     */
    @Getter @Setter
    private String legalRepresentative;
    /**
     * NAME OF LEGAL REPRESENTATIVE
     *
     * NOME DO REPRESENTANTE LEGAL
     */
    @Getter @Setter
    private String representativeName;
    /**
     * LEGAL REPRESENTATIVE QUALIFICATION CODE
     *
     * CODIGO DA QUALIFICAÇÃO DO REPRESENTANTE LEGAL
     */
    @Getter @Setter
    private Integer representativeQualification;
    /**
     * CODE CORRESPONDING TO THE AGE RANGE OF THE MEMBER
     *
     * CODIGO CORRESPONDENTE A FAIXA ETARIA DO SÓCIO
     */
    @Getter @Setter
    private Short ageRange;

    public static List<String> getColumnList(){
        return Arrays.asList("basicCnpj","partnerType","partnerName","partnerDocument","partnerQualification","partnerStartDate","country","legalRepresentative","representativeName","representativeQualification","ageRange");
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
