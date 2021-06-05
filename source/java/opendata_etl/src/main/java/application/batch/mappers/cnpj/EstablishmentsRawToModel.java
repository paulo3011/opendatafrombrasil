package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Establishment;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class EstablishmentsRawToModel implements MapFunction<Row, Establishment> {
    @Override
    public Establishment call(Row value) {
        Establishment record = new Establishment();
        //todo testar cnpj 36452531, tem vários cnaes e parece estar se perdendo nas colunas
        try {
            record.setBasicCnpj(CnpjUtils.getString(value,"basic_cnpj"));
            record.setCnpjOrder(CnpjUtils.getString(value,"cnpj_order"));
            record.setCnpjCheckingDigit(CnpjUtils.getString(value,"cnpj_checking_digit"));
            record.setMatrixBranch(CnpjUtils.getShort(value,"matrix_branch"));
            record.setFantasyName(CnpjUtils.getString(value,"fantasy_name"));
            record.setRegistrationStatus(CnpjUtils.getShort(value,"registration_situation"));
            record.setDateRegistrationStatus(CnpjUtils.getLocalDateAsString(value, "date_registration_situation"));
            record.setReasonRegistrationStatus(Integer.decode(CnpjUtils.getString(value,"reason_registration_situation")));
            record.setNameCityAbroad(CnpjUtils.getString(value,"name_city_abroad"));
            record.setCountryCode(CnpjUtils.getString(value,"country_code"));
            record.setActivityStartDate(CnpjUtils.getLocalDateAsString(value, "activity_start_date"));
            record.setMainCnaeFiscal(CnpjUtils.getString(value,"main_fiscal_cnae"));
            record.setSecondaryCnaeFiscal(CnpjUtils.getString(value,"secondary_fiscal_cnae"));
            record.setAddressType(CnpjUtils.getString(value,"type_of_address"));
            record.setAddress(CnpjUtils.getString(value,"address"));
            record.setAddressNumber(CnpjUtils.getString(value,"address_number"));
            record.setAddressComplement(CnpjUtils.getString(value,"address_complement"));
            record.setAddressDistrict(CnpjUtils.getString(value,"address_district"));
            record.setZipCode(CnpjUtils.getString(value,"zip_code"));
            record.setState(CnpjUtils.getString(value,"federation_unit"));
            record.setCityJurisdictionCode(CnpjUtils.getString(value,"city_jurisdiction_code"));
            record.setTelephone1AreaCode(CnpjUtils.getString(value,"telephone1_area_code"));
            record.setTelephone1(CnpjUtils.getString(value,"telephone1"));
            record.setTelephone2AreaCode(CnpjUtils.getString(value,"telephone2_area_code"));
            record.setTelephone2(CnpjUtils.getString(value,"telephone2"));
            record.setFaxAreaCode(CnpjUtils.getString(value,"fax_area_code"));
            record.setFaxNumber(CnpjUtils.getString(value,"fax_number"));
            record.setTaxpayerEmail(CnpjUtils.getString(value,"contributors_email"));
            record.setSpecialSituation(CnpjUtils.getString(value,"special_situation"));
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
