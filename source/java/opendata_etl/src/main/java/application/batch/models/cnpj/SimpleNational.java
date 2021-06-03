package application.batch.models.cnpj;

import lombok.Getter;
import lombok.Setter;

public class SimpleNational {
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
