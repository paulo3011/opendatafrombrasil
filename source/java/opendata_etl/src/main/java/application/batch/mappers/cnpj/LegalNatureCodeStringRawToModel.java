package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.LegalNatureCode;

@SuppressWarnings("unused")
public class LegalNatureCodeStringRawToModel extends GenericCodeStringRawToModel<LegalNatureCode> {
    public LegalNatureCodeStringRawToModel() {
        super(LegalNatureCode.class);
    }
}
