package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Establishment;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

@SuppressWarnings("unused")
public class EstablishmentsRawToModel implements MapFunction<Row, Establishment> {
    @Override
    public Establishment call(Row value) {
        Establishment record = new Establishment();
        record.setBasicCnpj(value.getAs(0));
        record.setCnpjOrder(value.getAs(1));
        record.setCnpjCheckingDigit(value.getAs(2));
        record.setMatrixBranch(Short.decode(value.getAs(3)));
        record.setFantasyName(value.getAs(4));
        record.setRegistrationSituation(Short.decode(value.getAs(5)));
        record.setDateRegistrationSituation(CnpjUtils.getLocalDateAsString(value,6));
        record.setReasonRegistrationSituation(value.getAs(7));
        record.setNameCityAbroad(value.getAs(8));
        record.setCountryCode(value.getAs(9));
        record.setActivityStartDate(CnpjUtils.getLocalDateAsString(value,10));
        record.setMainCnaeFiscal(value.getAs(11));
        record.setSecondaryCnaeFiscal(value.getAs(12));
        record.setAddressType(value.getAs(13));
        record.setAddress(value.getAs(14));
        record.setAddressNumber(value.getAs(15));
        record.setAddressComplement(value.getAs(16));
        record.setAddressDistrict(value.getAs(17));
        record.setZipCode(value.getAs(18));
        record.setFederationUnit(value.getAs(19));
        record.setCityJurisdictionCode(value.getAs(20));
        record.setTelephone1AreaCode(value.getAs(21));
        record.setTelephone1(value.getAs(22));
        record.setTelephone2AreaCode(value.getAs(23));
        record.setTelephone2(value.getAs(24));
        record.setFaxAreaCode(value.getAs(25));
        record.setFaxNumber(value.getAs(26));
        record.setContributorsEmail(value.getAs(27));
        record.setSpecialSituation(value.getAs(28));
        record.setSpecialSituationDate(CnpjUtils.getLocalDateAsString(value,29));

        //record.setIsSimple(value.getAs(1).equals("S"));
        //record.setSimpleOptionDate(CnpjUtils.getLocalDate(value,2));

        return record;
    }
}
