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
            record.setBasicCnpj(CnpjUtils.getString(value,"basic_cnpj"));
            record.setPartnerType(Short.decode(CnpjUtils.getString(value,"partner_type")));
            record.setPartnerName(CnpjUtils.getString(value,"partner_name"));
            record.setPartnerDocument(CnpjUtils.getString(value,"partner_document"));
            record.setPartnerQualification(Short.decode(CnpjUtils.getString(value,"partner_qualification")));
            record.setPartnerStartDate(CnpjUtils.getLocalDateAsString(value, "partner_start_date"));
            record.setCountry(CnpjUtils.getString(value,"country"));
            record.setLegalRepresentative(CnpjUtils.getString(value,"legal_representative"));
            record.setRepresentativeName(CnpjUtils.getString(value,"representative_name"));
            record.setRepresentativeQualification(Short.decode(CnpjUtils.getString(value,"representative_qualification")));
            record.setAgeRange(Short.decode(CnpjUtils.getString(value,"age_range")));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
