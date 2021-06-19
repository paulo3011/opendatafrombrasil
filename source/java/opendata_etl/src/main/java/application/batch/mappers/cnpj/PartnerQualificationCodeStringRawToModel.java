package application.batch.mappers.cnpj;

import application.batch.models.cnpj.genericcodes.PartnerQualificationCode;

@SuppressWarnings("unused")
public class PartnerQualificationCodeStringRawToModel extends GenericCodeStringRawToModel<PartnerQualificationCode> {
    public PartnerQualificationCodeStringRawToModel() {
        super(PartnerQualificationCode.class);
    }
}
