package application.batch.enums;

/**
 * Supported format types for input and output of ETL
 *
 * Tipos de formatos de arquivos suportados para origem (input do ETL) ou destino (output do ETL)
 */
public enum FileFormat {
    csv,
    json,
    orc,
    parquet
}
