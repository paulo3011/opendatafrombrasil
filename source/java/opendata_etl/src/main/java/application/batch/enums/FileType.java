package application.batch.enums;

public enum FileType {
    /**
     * All CNPJ files without transformations (in raw format, but with output format variable by FileFormat)
     * (Todos os arquivos do CNPJ sem transformação (ex: unir arquivo de empresa com estabelecimentos)
     */
    cnpj_raw,
    /**
     * All CNPJ files transformed into a format optimized for analysis
     * (Todos os arquivos do CNPJ transformados para um formato otimizado para análises)
     */
    cnpj_lake
}
