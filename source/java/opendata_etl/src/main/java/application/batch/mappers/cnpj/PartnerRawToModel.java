package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Partner;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class PartnerRawToModel implements MapFunction<Row, Partner> {
    @Override
    public Partner call(Row value) {
        try {
            Partner record = new Partner();

            record.setBasicCnpj(value.getAs(0));
            record.setPartnerType(Short.decode(value.getAs(1)));
            record.setPartnerName(value.getAs(2));
            record.setPartnerDocument(value.getAs(3));
            record.setPartnerQualification(Short.decode(value.getAs(4)));
            record.setPartnerStartDate(CnpjUtils.getLocalDateAsString(value, 5));
            record.setCountry(value.getAs(6));
            record.setLegalRepresentative(value.getAs(7));
            record.setRepresentativeName(value.getAs(8));
            record.setRepresentativeQualification(Short.decode(value.getAs(9)));
            record.setAgeRange(Short.decode(value.getAs(10)));

            return record;
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            return null;
        }
    }
}
