package application.batch.models.cnpj;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;

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
    private String simpleOptionDate;
    @Getter @Setter
    private String simpleExclusionDate;
    @Getter @Setter
    private Boolean isMei;
    @Getter @Setter
    private String meiOptionDate;
    @Getter @Setter
    private String meiExclusionDate;
}
