package application.batch.mappers.cnpj;

import application.batch.models.cnpj.Company;
import application.batch.utils.CnpjUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class CompanyStringRawToModel implements FlatMapFunction<Iterator<String>, Company>, Serializable {
    @Override
    public Iterator<Company> call(Iterator<String> stringIterator) {
        ArrayList<Company> records = new ArrayList<>();
        while(stringIterator.hasNext()){
            String textLine = stringIterator.next();
            records.add(tryParseRecord(textLine));
        }
        return records.iterator();
    }

    public Company tryParseRecord(String textLine){
        Company record = new Company();
        try {
            String[] values = CnpjUtils.splitTextLine(textLine);
            record.setBasicCnpj(CnpjUtils.fixStringValues(values[0]));
            record.setLegalName(CnpjUtils.fixStringValues(values[1]));
            record.setLegalNature(CnpjUtils.getInteger(values[2]));
            record.setResponsibleQualification(CnpjUtils.getShort(values[3]));
            record.setCompanyCapital(CnpjUtils.getBigDecimal(values[4]));
            record.setCompanySize(CnpjUtils.getShort(values[5]));
            record.setFederatedEntityResponsible(CnpjUtils.fixStringValues(values[6]));
        }
        catch (Exception ex){
            System.out.printf("date parser error: %s: , row data: %s", ex.getMessage(), textLine);
            record.setRawData(textLine);
            record.setParseErrorMessage(ex.getMessage());
        }
        return record;
    }
}
