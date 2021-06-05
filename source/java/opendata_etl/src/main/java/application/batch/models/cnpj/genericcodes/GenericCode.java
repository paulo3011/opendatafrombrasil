package application.batch.models.cnpj.genericcodes;

import application.batch.models.FromTextFileModel;
import lombok.Getter;
import lombok.Setter;

public class GenericCode extends FromTextFileModel {
    @Getter @Setter
    int code = 0;
    @Getter @Setter
    String description;
}
