package application.batch.models.cnpj;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

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
}
