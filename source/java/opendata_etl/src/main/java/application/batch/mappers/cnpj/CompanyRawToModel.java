package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Company;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

@SuppressWarnings("unused")
public class CompanyRawToModel implements MapFunction<Row, Company> {
    @Override
    public Company call(Row value) {
        Company record = new Company();

        record.setBasicCnpj(value.getAs(0));
        record.setLegalName(value.getAs(1));
        record.setLegalNature(value.getAs(2));
        record.setResponsibleQualification(value.getAs(3));
        record.setCompanyCapital(value.getAs(4));
        record.setCompanySize(Short.decode(value.getAs(5)));
        record.setFederatedEntityResponsible(value.getAs(6));

        return record;
    }
}
