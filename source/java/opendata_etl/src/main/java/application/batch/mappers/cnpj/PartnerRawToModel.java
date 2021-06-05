package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Partner;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class PartnerRawToModel implements MapFunction<Row, Partner> {
    @Override
    public Partner call(Row value) {
        Partner record = new Partner();
        try {
            record.setBasicCnpj(value.getAs("basic_cnpj"));
            record.setPartnerType(Short.decode(value.getAs("partner_type")));
            record.setPartnerName(value.getAs("partner_name"));
            record.setPartnerDocument(value.getAs("partner_document"));
            record.setPartnerQualification(Short.decode(value.getAs("partner_qualification")));
            record.setPartnerStartDate(CnpjUtils.getLocalDateAsString(value, "partner_start_date"));
            record.setCountry(value.getAs("country"));
            record.setLegalRepresentative(value.getAs("legal_representative"));
            record.setRepresentativeName(value.getAs("representative_name"));
            record.setRepresentativeQualification(Short.decode(value.getAs("representative_qualification")));
            record.setAgeRange(Short.decode(value.getAs("age_range")));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
