import org.junit.Assert;
import org.junit.Test;

public class ParseCsvTest {
    @Test
    public void SplitLine_1(){
        String textLine = "\"36452531\";\"0001\";\"62\";\"1\";\"AMPPLA CREATIVE STUDIO\";\"2\";\"20200221\";\"0\";\"\";\"\";\"20200221\";\"1821100\";\"5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100\";\"ESTRADA\";\"DO MANDU\";\"560\";\"EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805\";\"SAO MARCOS\";\"41250400\";\"BA\";\"3849\";\"71\";\"99479533\";\"\";\"\";\"\";\"\";\"JONATASMA@GMAIL.COM\";\"\";\"\"";

        String[] expectedResult = {
                "36452531",
                "0001",
                "62",
                "1",
                "AMPPLA CREATIVE STUDIO",
                "2",
                "20200221",
                "0",
                "",
                "",
                "20200221",
                "1821100",
                "5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100",
                "ESTRADA",
                "DO MANDU",
                "560",
                "EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805",
                "SAO MARCOS",
                "41250400",
                "BA",
                "3849",
                "71",
                "99479533",
                "",
                "",
                "",
                "",
                "JONATASMA@GMAIL.COM",
                "",
                ""
                };

        String[] parts = splitTextLine(textLine);
        Assert.assertEquals(30, parts.length,0);

        checkExpectedResult(parts, expectedResult);
    }

    public String[] splitTextLine(String textLine){
        if(textLine.startsWith("\""))
            textLine = textLine.substring(1);
        if(textLine.endsWith("\""))
            textLine = textLine.substring(0, textLine.length()-1);

        String[] parts = textLine.split("\";\"",-1);
        System.out.printf("parts.length: %s \n", parts.length);
        return parts;
    }

    public void checkExpectedResult(String[] parts, String[] expectedResult){
        for (int i = 0; i < parts.length; i++) {
            System.out.printf("%s == %s \n", parts[i], expectedResult[i]);
            Assert.assertEquals(parts[i], expectedResult[i]);
        }
    }

    @Test
    public void split_2(){
        String textLine = "\"cnpj_basico;separador,terceiro\";\"ordem,1,2\";\"cnpj dv\\\";\"identificador matriz filial\";\"nome fantasia\";\"situacao cadastral\";\"data situacao cadastral\";\"motivo situacao cadastral\";\"cidade exterior\";\"pais\";\"data inicio atividade\";\"cnae principal\";\"cnae secundario\";\"tipo logradouro\";\"logradouro\";\"numero\";\"complemento\";\"bairro\";\"cep\";\"uf\";\"municipio\";\"ddd 1\";\"telefone 1\";\"ddd 2\";\"telefone 2\";\"ddd fax\";\"fax\";\"email\";\"situacao especial\";\"data situacao especial\"";

        String[] expectedResult = {
                "cnpj_basico;separador,terceiro",
                "ordem,1,2",
                "cnpj dv\\",
                "identificador matriz filial",
                "nome fantasia",
                "situacao cadastral",
                "data situacao cadastral",
                "motivo situacao cadastral",
                "cidade exterior",
                "pais",
                "data inicio atividade",
                "cnae principal",
                "cnae secundario",
                "tipo logradouro",
                "logradouro",
                "numero",
                "complemento",
                "bairro",
                "cep",
                "uf",
                "municipio",
                "ddd 1",
                "telefone 1",
                "ddd 2",
                "telefone 2",
                "ddd fax",
                "fax",
                "email",
                "situacao especial",
                "data situacao especial"};

        String[] parts = splitTextLine(textLine);
        Assert.assertEquals(30, parts.length,0);
        checkExpectedResult(parts, expectedResult);

    }

    @Test
    public void removeLeadingZeroes(){
        String regexStr = "^0+(?!$)";
        Assert.assertEquals("1", "001".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "01".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "0001".replaceFirst(regexStr,""));
        Assert.assertEquals("1", "1".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "0008".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "08".replaceFirst(regexStr,""));
        Assert.assertEquals("8", "8".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "0010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "00010".replaceFirst(regexStr,""));
        Assert.assertEquals("10", "10".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "0011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "00011".replaceFirst(regexStr,""));
        Assert.assertEquals("11", "11".replaceFirst(regexStr,""));
    }
}
