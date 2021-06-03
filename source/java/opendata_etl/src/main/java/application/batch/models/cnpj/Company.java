package application.batch.models.cnpj;

import lombok.Getter;
import lombok.Setter;

public class Company {
    @Getter @Setter
    private String basicCnpj;
    @Getter @Setter
    private String legalName;
    @Getter @Setter
    private String legalNature;
    @Getter @Setter
    private String responsibleQualification;
    @Getter @Setter
    private String companyCapital;
    @Getter @Setter
    private short companySize;
    @Getter @Setter
    private String federatedEntityResponsible;
}
