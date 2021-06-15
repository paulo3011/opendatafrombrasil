package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Establishment;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @see <a href="https://spark.apache.org/docs/latest/rdd-programming-guide.html">Java RDD</a>
 */
@SuppressWarnings("unused")
public class EstablishmentsStringRawToModel implements FlatMapFunction<Iterator<String>, Establishment>, Serializable {
    @Override
    public Iterator<Establishment> call(Iterator<String> stringIterator) {
        ArrayList<Establishment> records = new ArrayList<>();
        while(stringIterator.hasNext()){
            String textLine = stringIterator.next();
            records.add(tryParseRecord(textLine));
        }
        return records.iterator();
    }

    public Establishment tryParseRecord(String textLine){
        Establishment record = new Establishment();
        try {
            String[] values = CnpjUtils.splitTextLine(textLine);
            record.setBasicCnpj(CnpjUtils.fixStringValues(values[0]));
            record.setCnpjOrder(CnpjUtils.fixStringValues(values[1]));
            record.setCnpjCheckingDigit(CnpjUtils.fixStringValues(values[2]));
            record.setMatrixBranch(CnpjUtils.getShort(values[3]));
            record.setFantasyName(CnpjUtils.fixStringValues(values[4]));
            record.setRegistrationStatus(CnpjUtils.getShort(values[5]));
            record.setRegistrationStatusDate(CnpjUtils.getSqlDate(values[6]));
            record.setRegistrationStatusReason(Integer.decode(CnpjUtils.fixStringValues(values[7])));
            record.setCityAbroadName(CnpjUtils.fixStringValues(values[8]));
            record.setCountryCode(CnpjUtils.getInteger(values[9]));
            record.setActivityStartDate(CnpjUtils.getSqlDate(values[10]));
            record.setMainCnae(CnpjUtils.getInteger(values[11]));
            record.setSecondaryCnae(CnpjUtils.fixStringValues(values[12]));
            record.setAddressType(CnpjUtils.fixStringValues(values[13]));
            record.setAddress(CnpjUtils.fixStringValues(values[14]));
            record.setAddressNumber(CnpjUtils.fixStringValues(values[15]));
            record.setAddressComplement(CnpjUtils.fixStringValues(values[16]));
            record.setAddressDistrict(CnpjUtils.fixStringValues(values[17]));
            record.setZipCode(CnpjUtils.fixStringValues(values[18]));
            record.setState(CnpjUtils.fixStringValues(values[19]));
            record.setCityCode(CnpjUtils.getInteger(values[20]));
            record.setTelephone1AreaCode(CnpjUtils.fixStringValues(values[21]));
            record.setTelephone1(CnpjUtils.fixStringValues(values[22]));
            record.setTelephone2AreaCode(CnpjUtils.fixStringValues(values[23]));
            record.setTelephone2(CnpjUtils.fixStringValues(values[24]));
            record.setFaxAreaCode(CnpjUtils.fixStringValues(values[25]));
            record.setFaxNumber(CnpjUtils.fixStringValues(values[26]));
            record.setTaxpayerEmail(CnpjUtils.fixStringValues(values[27]));
            record.setSpecialSituation(CnpjUtils.fixStringValues(values[28]));
            record.setSpecialSituationDate(CnpjUtils.getSqlDate(values[29]));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), textLine);
            record.setRawData(textLine);
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
