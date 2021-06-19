package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.CityCode;

@SuppressWarnings("unused")
public class CityCodeStringRawToModel extends GenericCodeStringRawToModel<CityCode> {
    public CityCodeStringRawToModel() {
        super(CityCode.class);
    }
}
