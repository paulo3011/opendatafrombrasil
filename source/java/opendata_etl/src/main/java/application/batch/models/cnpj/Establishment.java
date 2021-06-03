package application.batch.models.cnpj;


import lombok.Getter;
import lombok.Setter;

public class Establishment {
    @Getter @Setter
    private String basicCnpj;
    @Getter @Setter
    private String cnpjOrder;
    @Getter @Setter
    private String cnpjCheckingDigit;
    @Getter @Setter
    private short matrixBranch;
    @Getter @Setter
    private String fantasyName;
    @Getter @Setter
    private short registrationSituation;
    @Getter @Setter
    private String dateRegistrationSituation;
    @Getter @Setter
    private String reasonRegistrationSituation;
    @Getter @Setter
    private String nameCityAbroad;
    @Getter @Setter
    private String countryCode;
    @Getter @Setter
    private String activityStartDate;
    @Getter @Setter
    private String mainCnaeFiscal;
    @Getter @Setter
    private String secondaryCnaeFiscal;
    @Getter @Setter
    private String addressType;
    @Getter @Setter
    private String address;
    /**
     * Address number (can be same like 'SN' -> without number)
     */
    @Getter @Setter
    private String addressNumber;
    @Getter @Setter
    private String addressComplement;
    @Getter @Setter
    private String addressDistrict;
    @Getter @Setter
    private String zipCode;
    @Getter @Setter
    private String federationUnit;
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
    @Getter @Setter
    private String contributorsEmail;
    @Getter @Setter
    private String specialSituation;
    @Getter @Setter
    private String specialSituationDate;
}
