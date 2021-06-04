package application.batch.models.cnpj;


import lombok.Getter;
import lombok.Setter;

public class Establishment {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NÚMERO BASE DE INSCRIÇÃO NO CNPJ (OITO PRIMEIROS DÍGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    /**
     * NUMBER OF THE ESTABLISHMENT OF REGISTRATION WITH THE CNPJ (FROM THE NINTH TO THE TWELFTH DIGIT OF THE CNPJ).
     *
     * NÚMERO DO ESTABELECIMENTO DE INSCRIÇÃO NO CNPJ (DO NONO ATÉ O DÉCIMO SEGUNDO DÍGITO DO CNPJ).
     */
    @Getter @Setter
    private String cnpjOrder;
    /**
     * VERIFYING DIGIT OF THE CNPJ REGISTRATION NUMBER (LAST TWO DIGITS OF THE CNPJ).
     *
     * DÍGITO VERIFICADOR DO NÚMERO DE INSCRIÇÃO NO CNPJ (DOIS ÚLTIMOS DÍGITOS DO CNPJ).
     */
    @Getter @Setter
    private String cnpjCheckingDigit;
    /**
     * HEADQUARTERS/BRANCH IDENTIFIER CODE:
     * 1 - MATRIX
     * 2 – BRANCH
     *
     * CÓDIGO DO IDENTIFICADOR MATRIZ/FILIAL:
     * 1 – MATRIZ
     * 2 – FILIAL
     */
    @Getter @Setter
    private short matrixBranch;
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
     * CÓDIGO DA SITUAÇÃO CADASTRAL:
     * 01 – NULA
     * 2 – ATIVA
     * 3 – SUSPENSA
     * 4 – INAPTA
     * 08 – BAIXADA
     */
    @Getter @Setter
    private short registrationStatus;
    /**
     * DATE OF REGISTRATION STATUS EVENT
     *
     * DATA DO EVENTO DA SITUAÇÃO CADASTRAL
     */
    @Getter @Setter
    private String dateRegistrationStatus;
    /**
     * CODE OF REASON FOR REGISTRATION STATUS
     *
     * CÓDIGO DO MOTIVO DA SITUAÇÃO CADASTRAL
     */
    @Getter @Setter
    private int reasonRegistrationStatus;
    /**
     * NAME OF THE CITY ABROAD
     *
     * NOME DA CIDADE NO EXTERIOR
     */
    @Getter @Setter
    private String nameCityAbroad;
    /**
     * COUNTRY CODE
     *
     * CÓDIGO DO PAIS
     */
    @Getter @Setter
    private String countryCode;
    /**
     * START DATE OF ACTIVITY
     *
     * DATA DE INÍCIO DA ATIVIDADE
     */
    @Getter @Setter
    private String activityStartDate;
    /**
     * CODE OF THE MAIN ECONOMIC ACTIVITY OF THE ESTABLISHMENT
     *
     * CÓDIGO DA ATIVIDADE ECONÔMICA PRINCIPAL DO ESTABELECIMENTO
     */
    @Getter @Setter
    private String mainCnaeFiscal;
    /**
     * CODE OF THE SECONDARY ECONOMIC ACTIVITY(S) OF THE ESTABLISHMENT
     *
     * CÓDIGO DA(S) ATIVIDADE(S) ECONÔMICA(S) SECUNDÁRIA(S) DO ESTABELECIMENTO
     */
    @Getter @Setter
    private String secondaryCnaeFiscal;
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
     * NEIGHBORHOOD WHERE THE ESTABLISHMENT IS LOCATED.
     *
     * BAIRRO ONDE SE LOCALIZA O ESTABELECIMENTO.
     */
    @Getter @Setter
    private String addressDistrict;
    /**
     * ZIP CODE
     *
     * CÓDIGO DE ENDEREÇAMENTO POSTAL
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
     * CÓDIGO DO MUNICÍPIO DE JURISDIÇÃO ONDE SE ENCONTRA O ESTABELECIMENTO (NÃO É O CÓDIGO DO IBGE)
     */
    @Getter @Setter
    private String cityJurisdictionCode;
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
    private String specialSituationDate;
}
