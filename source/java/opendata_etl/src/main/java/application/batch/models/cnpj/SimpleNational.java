package application.batch.models.cnpj;

/*
simple_company_raw_schema.add("basic_cnpj", StringType(), False)
simple_company_raw_schema.add("is_simple", StringType(), True)
simple_company_raw_schema.add("simple_option_date", DateType(), True)
simple_company_raw_schema.add("simple_exclusion_date", DateType(), True)
simple_company_raw_schema.add("is_mei", StringType(), True)
simple_company_raw_schema.add("mei_option_date", DateType(), True)
simple_company_raw_schema.add("mei_exclusion_date", DateType(), True)
 */

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class SimpleNational {
    @Getter @Setter
    private String basicCnpj;
    @Getter @Setter
    private Boolean isSimple;
    @Getter @Setter
    private LocalDate simpleOptionDate;
    @Getter @Setter
    private LocalDate simpleExclusionDate;
    @Getter @Setter
    private Boolean isMei;
    @Getter @Setter
    private LocalDate meiOptionDate;
    @Getter @Setter
    private LocalDate meiExclusionDate;
}
