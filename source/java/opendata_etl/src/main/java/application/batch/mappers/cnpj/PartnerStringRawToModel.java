package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Partner;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class PartnerStringRawToModel implements FlatMapFunction<Iterator<String>, Partner> {
    @Override
    public Iterator<Partner> call(Iterator<String> stringIterator) {
        ArrayList<Partner> records = new ArrayList<>();
        while(stringIterator.hasNext()){
            String textLine = stringIterator.next();
            records.add(tryParseRecord(textLine));
        }
        return records.iterator();
    }

    public Partner tryParseRecord(String textLine){
        Partner record = new Partner();
        try {
            String[] values = CnpjUtils.splitTextLine(textLine);
            record.setBasicCnpj(CnpjUtils.fixStringValues(values[0]));
            record.setPartnerType(CnpjUtils.getShort(values[1]));
            record.setPartnerName(CnpjUtils.fixStringValues(values[2]));
            record.setPartnerDocument(CnpjUtils.fixStringValues(values[3]));
            record.setPartnerQualification(CnpjUtils.getInteger(values[4]));
            record.setPartnerStartDate(CnpjUtils.getSqlDate(values[5]));
            record.setCountry(CnpjUtils.getInteger(values[6]));
            record.setLegalRepresentative(CnpjUtils.fixStringValues(values[7]));
            record.setRepresentativeName(CnpjUtils.fixStringValues(values[8]));
            record.setRepresentativeQualification(CnpjUtils.getInteger(values[9]));
            record.setAgeRange(CnpjUtils.getShort(values[10]));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), textLine);
            record.setRawData(textLine);
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
