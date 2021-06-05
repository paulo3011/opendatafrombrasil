package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Company;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

@SuppressWarnings("unused")
public class CompanyRawToModel implements MapFunction<Row, Company> {
    /**
     *
     * @param value row values
     * @return Company
     * @see <a href="http://spark.apache.org/docs/3.1.1/api/python/reference/api/pyspark.sql.DataFrameReader.csv.html#pyspark.sql.DataFrameReader.csv">About columnNameOfCorruptRecord and PERMISSIVE mode</a>
     */
    @Override
    public Company call(Row value) {
        Company record = new Company();
        try {
            record.setBasicCnpj(value.getAs("basic_cnpj"));
            record.setLegalName(value.getAs("legal_name"));
            record.setLegalNature(value.getAs("legal_nature"));
            record.setResponsibleQualification(value.getAs("responsible_qualification"));
            record.setCompanyCapital(CnpjUtils.getBigDecimalAsString(value,"company_capital"));
            record.setCompanySize(Short.decode(value.getAs("company_size")));
            record.setFederatedEntityResponsible(value.getAs("federative_entity_responsible"));
        }
        catch (Exception ex){
            System.out.printf("New - date parser error: %s: , row data: %s", ex.toString(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.toString());
        }
        return record;
    }
}
