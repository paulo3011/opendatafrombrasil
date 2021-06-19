package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.CnaeCode;

@SuppressWarnings("unused")
public class CnaeCodeStringRawToModel extends GenericCodeStringRawToModel<CnaeCode> {
    public CnaeCodeStringRawToModel() {
        super(CnaeCode.class);
    }
}
