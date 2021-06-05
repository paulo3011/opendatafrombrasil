package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Establishment;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class EstablishmentsRawToModel implements MapFunction<Row, Establishment> {
    @Override
    public Establishment call(Row value) {
        Establishment record = new Establishment();
        try {
            record.setBasicCnpj(value.getAs("basic_cnpj"));
            record.setCnpjOrder(value.getAs("cnpj_order"));
            record.setCnpjCheckingDigit(value.getAs("cnpj_checking_digit"));
            record.setMatrixBranch(Short.decode(value.getAs("matrix_branch")));
            record.setFantasyName(value.getAs("fantasy_name"));
            record.setRegistrationStatus(Short.decode(value.getAs("registration_situation")));
            record.setDateRegistrationStatus(CnpjUtils.getLocalDateAsString(value, "date_registration_situation"));
            record.setReasonRegistrationStatus(Integer.decode(value.getAs("reason_registration_situation")));
            record.setNameCityAbroad(value.getAs("name_city_abroad"));
            record.setCountryCode(value.getAs("country_code"));
            record.setActivityStartDate(CnpjUtils.getLocalDateAsString(value, "activity_start_date"));
            record.setMainCnaeFiscal(value.getAs("main_fiscal_cnae"));
            record.setSecondaryCnaeFiscal(value.getAs("secondary_fiscal_cnae"));
            record.setAddressType(value.getAs("type_of_address"));
            record.setAddress(value.getAs("address"));
            record.setAddressNumber(value.getAs("address_number"));
            record.setAddressComplement(value.getAs("address_complement"));
            record.setAddressDistrict(value.getAs("address_district"));
            record.setZipCode(value.getAs("zip_code"));
            record.setState(value.getAs("federation_unit"));
            record.setCityJurisdictionCode(value.getAs("city_jurisdiction_code"));
            record.setTelephone1AreaCode(value.getAs("telephone1_area_code"));
            record.setTelephone1(value.getAs("telephone1"));
            record.setTelephone2AreaCode(value.getAs("telephone2_area_code"));
            record.setTelephone2(value.getAs("telephone2"));
            record.setFaxAreaCode(value.getAs("fax_area_code"));
            record.setFaxNumber(value.getAs("fax_number"));
            record.setTaxpayerEmail(value.getAs("contributors_email"));
            record.setSpecialSituation(value.getAs("special_situation"));
            record.setSpecialSituationDate(CnpjUtils.getLocalDateAsString(value, "special_situation_date"));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), value.toString());
            record.setRawData(value.toString());
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
