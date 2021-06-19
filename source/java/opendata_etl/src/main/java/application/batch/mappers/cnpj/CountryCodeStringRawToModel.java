package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.CountryCode;

@SuppressWarnings("unused")
public class CountryCodeStringRawToModel extends GenericCodeStringRawToModel<CountryCode> {
    public CountryCodeStringRawToModel() {
        super(CountryCode.class);
    }
}
