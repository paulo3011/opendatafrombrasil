package application.batch.models.cnpj;

import lombok.Getter;
import lombok.Setter;

public class Company {
    /**
     * BASE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).
     *
     * NÚMERO BASE DE INSCRIÇÃO NO CNPJ (OITO PRIMEIROS DÍGITOS DO CNPJ).
     */
    @Getter @Setter
    private String basicCnpj;
    /**
     * CORPORATE NAME IS YOUR COMPANY'S REGISTERED NAME.
     *
     * NOME EMPRESARIAL DA PESSOA JURÍDICA
     */
    @Getter @Setter
    private String legalName;
    /**
     * CODE OF LEGAL NATURE
     *
     * CÓDIGO DA NATUREZA JURÍDICA
     */
    @Getter @Setter
    private String legalNature;
    /**
     * QUALIFICATION OF THE INDIVIDUAL RESPONSIBLE FOR THE COMPANY
     *
     * QUALIFICAÇÃO DA PESSOA FÍSICA RESPONSÁVEL PELA EMPRESA
     */
    @Getter @Setter
    private String responsibleQualification;
    /**
     * COMPANY SHARE CAPITAL
     *
     * CAPITAL SOCIAL DA EMPRESA
     */
    @Getter @Setter
    private String companyCapital;
    /**
     * COMPANY PORT CODE:
     * 1  - NOT INFORMED
     * 2  - MICRO COMPANY
     * 03 - SMALL COMPANY
     * 05 - OTHERS
     *
     * CÓDIGO DO PORTE DA EMPRESA:
     * 1  – NÃO INFORMADO
     * 2  - MICRO EMPRESA
     * 03 - EMPRESA DE PEQUENO PORTE
     * 05 - DEMAIS
     */
    @Getter @Setter
    private short companySize;
    /**
     * THE RESPONSIBLE FEDERATIVE ENTITY IS COMPLETED FOR THE CASES OF ORGANS AND ENTITIES OF THE GROUP OF LEGAL NATURE 1XXX. FOR OTHER NATURES, THIS ATTRIBUTE IS BLANK.
     *
     * O ENTE FEDERATIVO RESPONSÁVEL É PREENCHIDO PARA OS CASOS DE ÓRGÃOS E ENTIDADES DO GRUPO DE NATUREZA JURÍDICA 1XXX. PARA AS DEMAIS NATUREZAS, ESTE ATRIBUTO FICA EM BRANCO.
     */
    @Getter @Setter
    private String federatedEntityResponsible;
}
