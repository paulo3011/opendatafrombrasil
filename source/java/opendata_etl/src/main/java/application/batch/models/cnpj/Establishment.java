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

public class Establishment extends FromTextFileModel {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NUMERO BASE DE INSCRICAO NO CNPJ (OITO PRIMEIROS DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    /**
     * NUMBER OF THE ESTABLISHMENT OF REGISTRATION WITH THE CNPJ (FROM THE NINTH TO THE TWELFTH DIGIT OF THE CNPJ).
     *
     * NUMERO DO ESTABELECIMENTO DE INSCRICAO NO CNPJ (DO NONO ATÉ O DÉCIMO SEGUNDO DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String cnpjOrder;
    /**
     * VERIFYING DIGIT OF THE CNPJ REGISTRATION NUMBER (LAST TWO DIGITS OF THE CNPJ).
     *
     * DIGITOS VERIFICADOR DO NUMERO DE INSCRICAO NO CNPJ (DOIS ÚLTIMOS DIGITOS DO CNPJ).
     */
    @Getter @Setter
    private String cnpjCheckingDigit;
    /**
     * HEADQUARTERS/BRANCH IDENTIFIER CODE:
     * 1 - MATRIX
     * 2 – BRANCH
     *
     * CODIGO DO IDENTIFICADOR MATRIZ/FILIAL:
     * 1 – MATRIZ
     * 2 – FILIAL
     */
    @Getter @Setter
    private Short matrixBranch;
    /**
     * FANTASY NAME (Facade Name or Corporate Brand is the popular name of a company, and may or may not be the same as its corporate name.)
     *
     * NOME FANTASIA
     */
    @Getter @Setter
    private String fantasyName;
    /**
     * CODE OF REGISTRATION STATUS:
     * 01 - NULL
     * 2 – ACTIVE
     * 3 – SUSPENDED
     * 4 – UNABLE
     * 08 – CLOSED
     *
     * CODIGO DA SITUAÇÃO CADASTRAL:
     * 01 – NULA
     * 2 – ATIVA
     * 3 – SUSPENSA
     * 4 – INAPTA
     * 08 – BAIXADA
     */
    @Getter @Setter
    private Short registrationStatus;
    /**
     * DATE OF REGISTRATION STATUS EVENT
     *
     * DATA DO EVENTO DA SITUAÇÃO CADASTRAL
     */
    @Getter @Setter
    private Date registrationStatusDate;
    /**
     * CODE OF REASON FOR REGISTRATION STATUS
     *
     * CODIGO DO MOTIVO DA SITUAÇÃO CADASTRAL
     */
    @Getter @Setter
    private Integer registrationStatusReason;
    /**
     * NAME OF THE CITY ABROAD
     *
     * NOME DA CIDADE NO EXTERIOR
     */
    @Getter @Setter
    private String cityAbroadName;
    /**
     * COUNTRY CODE
     *
     * CODIGO DO PAIS
     */
    @Getter @Setter
    private Integer countryCode;
    /**
     * START DATE OF ACTIVITY
     *
     * DATA DE INICIO DA ATIVIDADE
     */
    @Getter @Setter
    private Date activityStartDate;
    /**
     * CODE OF THE MAIN ECONOMIC ACTIVITY OF THE ESTABLISHMENT
     *
     * CODIGO DA ATIVIDADE ECONOMICA PRINCIPAL DO ESTABELECIMENTO
     */
    @Getter @Setter
    private Integer mainCnae;
    /**
     * CODE OF THE SECONDARY ECONOMIC ACTIVITY(S) OF THE ESTABLISHMENT
     *
     * CODIGO DA(S) ATIVIDADE(S) ECONOMICA(S) SECUNDARIA(S) DO ESTABELECIMENTO
     */
    @Getter @Setter
    private String secondaryCnae;
    /**
     * THE ADDRESS TYPE
     *
     * DESCRIÇÃO DO TIPO DE LOGRADOURO
     */
    @Getter @Setter
    private String addressType;
    /**
     * THE ADDRESS WHERE THE ESTABLISHMENT IS LOCATED.
     *
     * NOME DO LOGRADOURO ONDE SE LOCALIZA O ESTABELECIMENTO.
     */
    @Getter @Setter
    private String address;
    /**
     * Address number (can be same like 'SN' -> without number)
     */
    @Getter @Setter
    private String addressNumber;
    /**
     * ADDRESS ADDITIONAL INFORMATION (COMPLEMENT)
     *
     * COMPLEMENTO PARA O ENDEREÇO DE LOCALIZAÇÃO DO ESTABELECIMENTO
     */
    @Getter @Setter
    private String addressComplement;
    /**
     * AREA WHERE THE ESTABLISHMENT IS LOCATED.
     *
     * BAIRRO ONDE SE LOCALIZA O ESTABELECIMENTO.
     */
    @Getter @Setter
    private String addressDistrict;
    /**
     * ZIP CODE
     *
     * CODIGO DE ENDEREÇAMENTO POSTAL
     */
    @Getter @Setter
    private String zipCode;
    /**
     * ACRONYM OF THE STATE IN WHICH THE ESTABLISHMENT EXISTS
     * SIGLA DO ESTADO EM QUE SE ENCONTRA O ESTABELECIMENTO
     */
    @Getter @Setter
    private String state;
    /**
     * CITY CODE
     * CODIGO DO MUNICIPIO DE JURISDIÇÃO ONDE SE ENCONTRA O ESTABELECIMENTO (NAO É O CODIGO DO IBGE)
     */
    @Getter @Setter
    private Integer cityCode;
    @Getter @Setter
    private String telephone1AreaCode;
    @Getter @Setter
    private String telephone1;
    @Getter @Setter
    private String telephone2AreaCode;
    @Getter @Setter
    private String telephone2;
    @Getter @Setter
    private String faxAreaCode;
    @Getter @Setter
    private String faxNumber;
    /**
     * TAXPAYER'S E-MAIL
     *
     * E-MAIL DO CONTRIBUINTE
     */
    @Getter @Setter
    private String taxpayerEmail;
    @Getter @Setter
    private String specialSituation;
    @Getter @Setter
    private Date specialSituationDate;

    public static List<String> getColumnList(){
        return Arrays.asList("basicCnpj","cnpjOrder","cnpjCheckingDigit","matrixBranch","fantasyName","registrationStatus","registrationStatusDate","registrationStatusReason","cityAbroadName","countryCode","activityStartDate","mainCnae","secondaryCnae","addressType","address","addressNumber","addressComplement","addressDistrict","zipCode","state","cityCode","telephone1AreaCode","telephone1","telephone2AreaCode","telephone2","faxAreaCode","faxNumber","taxpayerEmail","specialSituation","specialSituationDate");
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
