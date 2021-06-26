# Open data from Brasil

Leia em outro idioma (Read this in other language):

[![en](https://img.shields.io/badge/lang-en-red.svg)](README-en-US.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.md)


## Resumo

Este projeto foi feito para explorar os dados abertos do Brasil sobre empresas.

Este projeto seguiu as seguintes etapas:

1. Definir o escopo do projeto e coletar dados
2. Explorar e avaliar os dados
3. Definir o modelo de dados
4. Executar ETL para modelar os dados
5. Descrever e documentar o Projeto


## 1. Definição do escopo do projeto e coleta de dados

### __Escopo do projeto__

Este projeto tem como objetivo final permitir que as pessoas possam fazer análises relacionadas as empresas brasileiras e dar base para tomada de decisões, com base na concorrência e ou outros fatores observados, como por exemplo: 

- Em que cidade é um bom local para abrir uma empresa?
- Quais são os clientes (empresas ou pessoas que são sócias de alguma empresa), cidades ou regiões em que é possível atuar ou oferecer serviços e produtos?
- Qual o tamanho do mercado disponível?
- Quem são meus clientes, quais empresas eles possuem, existe alguma informação de contato disponível?
- Faço alguma abordagem diferenciada para alguns clientes em função de existir conexão (sociedade) entre clientes?

## 2. Explorar e avaliar os dados

### __Descrição dos conjuntos de dados__

Os conjutos de dados utilizados inicialmente são oriundos do governo brasileiro e disponibilizados de forma aberta.

__Dados Abertos do CNPJ__


**Característica**|**Descrição**|**Status**
-----|:-----:|:-----:
Periodicidade atualização:|mensal
Formato dados:|csv
Origem:|[clique aqui](https://www.gov.br/receitafederal/pt-br/assuntos/orientacao-tributaria/cadastros/consultas/dados-publicos-cnpj)|![Website](https://img.shields.io/website?url=http%3A%2F%2F200.152.38.155%2FCNPJ%2F)
Layout:|[clique aqui](https://www.gov.br/receitafederal/pt-br/assuntos/orientacao-tributaria/cadastros/consultas/arquivos/NOVOLAYOUTDOSDADOSABERTOSDOCNPJ.pdf)

</br>

Arquivos e exemplos:

__Estabelecimentos__

![csv_estabelecimentos.jpg](./assets/images/cnpj/csv_estabelecimentos.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#estabelecimentos)

</br>

__Empresas__

![csv_empresas.jpg](./assets/images/cnpj/csv_empresas.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#empresas)

</br>

__Sócios__

![csv_socios.jpg](./assets/images/cnpj/csv_socios.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#sócios)

</br>

__CNAE - Classificação Nacional de Atividades Econômicas__

![csv_cnaes.jpg](./assets/images/cnpj/csv_cnaes.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#cnae---classificação-nacional-de-atividades-econômicas)

_Órgão responsável por esta classificação:_ [concla](https://concla.ibge.gov.br)

</br>

__Natureza Jurídica__

![csv_naturezajuridica.jpg](./assets/images/cnpj/csv_naturezajuridica.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#natureza-jurídica)

_Órgão responsável por esta classificação:_ [concla](https://concla.ibge.gov.br)

</br>

__Qualificação do Sócio__

![csv_qualificacao.jpg](./assets/images/cnpj/csv_qualificacao.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#qualificação-do-sócio)

</br>

__Código do Município__

![csv_municipio.jpg](./assets/images/cnpj/csv_municipio.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#código-do-município)

</br>

__Código do País__

![csv_pais.jpg](./assets/images/cnpj/csv_pais.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#código-do-país)

</br>

__Dados do Simples Nacional__

![csv_simples.jpg](./assets/images/cnpj/csv_simples.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#dados-do-simples-nacional)

</br>


__Motivo Situação Cadastral__

https://receita.economia.gov.br/orientacao/tributaria/cadastros/cadastro-nacional-de-pessoas-juridicas-cnpj/DominiosMotivoSituaoCadastral.csv/view

__Notas__

1. O campo (CNPJ/CPF DO SÓCIO) e (CNPJ/CPF DO REPRESENTANTE) do layout de sócios devem ser descaracterizados conforme a regra abaixo:

- Ocultação de informações pessoais sigilosas como no caso do CPF, o qual deve ser descaracterizado por meio da ocultação dos três primeiros dígitos e dos dois dígitos verificadores, conforme orientação disposta no art. 129 § 2o da Lei no 13.473/2017 (LDO
2018).

2. Campo Ente Federativo Responsável – EFR, no Layout Principal (Dados Cadastrais): 

Deve ser preenchido para os casos de Órgãos e Entidades do grupo de Natureza Jurídica 1XX. Para as demais naturezas, esse atributo fica em branco.

Exemplos de texto que deverão aparecer no arquivo final: 

- UNIÃO; 
- DISTRITO FEDERAL; 
- BAHIA; 

para municípios, exibir também a sigla da UF: 

- SÃO PAULO – SP;
- BELO HORIZONTE – MG;

3. Campo Faixa Etária, no Layout Sócios

Baseada na data de nascimento do CPF de cada sócio, deverá ser criado o valor para o campo Faixa Etária conforme a regra abaixo:

- 1 para os intervalos entre 0 a 12 anos;
- 2 para os intervalos entre 13 a 20 anos;
- 3 para os intervalos entre 21 a 30 anos;
- 4 para os intervalos entre 31 a 40 anos;
- 5 para os intervalos entre 41 a 50 anos;
- 6 para os intervalos entre 51 a 60 anos;
- 7 para os intervalos entre 61 a 70 anos;
- 8 para os intervalos entre 71 a 80 anos; 
- 9 para maiores de 80 anos;
- 0 para não se aplica;

4. O Campo CNAE FISCAL SECUNDÁRIA, no Layout Estabelecimentos:

Deve ser preenchido com cada ocorrência sendo separada por vírgula, para os casos de várias ocorrências.

</br>

### __Problemas conhecidos nos dados e como tratar__
<br/>

__Arquivo:__ K3241.K03200Y0.D10410.ESTABELE

__Campo:__ Data situação cadastral

__Valor:__ 0

__Problema:__ Formato inválido

```txt
"30005475";"0001";"31";"1";"";"2";"0";"0";"";"";"20180322";"6204000";"6209100,7490104";"AVENIDA";"PAULISTA";"2202";"CONJ  54-B";"BELA VISTA";"01310300";"SP";"7107";"11";"59085410";"";"";"";"";"CEFISCO@UOL.COM.BR";"";""
```

__Arquivo:__ [pendente de identificar arquivo origem]

__Campo:__ Data situação cadastral

__Valor:__ 4100813

__Problema:__ Formato inválido

```txt
"18825426";"0001";"40";"1";"ALAMBIQUE SANTO ANTONIO";"8";"20150209";"73";"";"";"4100813";"5611204";"";"RUA";"DEOLINDO PERIM";"79";"";"ITAPUA";"29101811";"ES";"5703";"27";"98921990";"27";"";"";"";"JFJUNCAL@GMAIL.COM";"";""
```

__Arquivo:__ K3241.K03200Y0.D10410.ESTABELE

__Campo:__ Data situação cadastral

__Valor:__ 4100813

__Problema:__ Complemento "EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805" tem ponto e vírgula que é o separador do arquivo e dependendo do parser de csv que estiver sendo usado ele se perde e bagunça as colunas.

```txt
"36452531";"0001";"62";"1";"AMPPLA CREATIVE STUDIO";"2";"20200221";"0";"";"";"20200221";"1821100";"5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100";"ESTRADA";"DO MANDU";"560";"EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805";"SAO MARCOS";"41250400";"BA";"3849";"71";"99479533";"";"";"";"";"JONATASMA@GMAIL.COM";"";""
```

<br/>

### __Cuidados necessários__
<br/>

- Foi encontrado registros com caracteres que quebram o parser padrão do apache spark utilizando DataFrameReader como "\\" que é o caracter de scape default. Foi preciso implementar uma leitura dos csv's customizada para evitar que as colunas dos arquivos ficassem quebradas (com mais ou menos colunas). 

- Cuidar com campos que podem ser nulos e avaliar se o campo contém valores como: null ou embranco ("")

```Java
public static String fixStringValues(String value) {
        if (value == null || value.equals("null"))
            return null;
        return value.replaceAll("^\"|\"$", "");
    }
```

- Foi preciso tratar valores numéricos (inteiros) que continham zeros a esquerda, exemplo: 0001. Nestes casos foi removido os zeros das esquerda antes de converter para inteiro. Além disso foi preciso verificar por valores nulos, vazios antes de tentar converter.

- Para converter valores monetários foi preciso utilizar formatação local do brasil

```Java
//exemplo
public static String fixStringValues(String value) {
    if (value == null || value.equals("null"))
        return null;
    return value.replaceAll("^\"|\"$", "");
}
NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
String numberString = fixStringValues("000000010000,00");
return nf.parse(numberString).toString();
```

- Datas inválidas foram tratadas como nulas


## 3. Definir o modelo de dados

### __Modelo__

![csv_estabelecimentos.jpg](./assets/images/cnpj/opendata.png)



# Requisitos funcionais da aplicação:

- Transformar do formato de origem para o de destino   
- Permitir criar implementações de transformações de forma customizadas no futuro
    - Por padrão é utilizado um pipeline usando dataframe
- Permitir definir as configurações do spark via:
    - linha de comando (spark-submit)
    - parâmetros de aplicação
- Permitir definir as configurações por formato de leitura/escrita (origem/destino) via:
    - parâmetros de aplicação
- Permitir selecionar o pipeline de transformação de acordo com os parâmetros de origem e destino

# Melhorias futuras

## Inclusão de novos conjuntos de dados

__Dados populacionais__

Coletar dados relacionados a população brasileira para subsidiar análises relacionadas a abertura de empresas ou detecção de oportunidades.

__Informações relacionadas a valores de terrenos e imóveis__

Coletar dados relacionados a preço de compra e venda de imóveis e terrenos para que seja possível subsidiar análises relacionadas a abertura de empresas, detecção de oportunidades e concorrência.

__Reclamações relacionadas as empresas__

Coletar dados relacionados a reclamações abertas ou relatadas por clientes em sites como reclameaqui, procon para pode analisar as empresas por esta perspectiva. Pode medir o nível de satisfação dos clientes.

__Informações climáticas__

Coletar informações relacionadas a temperatura e clima para subisidiar análises de abertura de empresa, detecção de oportunidades e riscos.

__Dados relacionados a força de trabalho disponível__

Dados sobre nível de escolaridade e educacional.

__CNAE - Estrutura detalhada e notas explicativas__

Descreve de forma mais detalhada quais atividades estão enquadradas ou não em cada CNAE. Este dataset pode ser usado para localizar de forma mais precisa as empresas pelas atividades que ela pode ou não exercer. 

Observa-se no PDF um padrão na forma de descrever o quais atividades estão ou não incluídas em cada código o que permite extração de forma programática.	

https://concla.ibge.gov.br/images/concla/downloads/revisao2007/PropCNAE20/CNAE20_NotasExplicativas.pdf



# Referências técnicas

- https://github.com/jonatasemidio/multilanguage-readme-pattern/blob/master/README.md
- https://github.com/tiimgreen/github-cheat-sheet/blob/master/README.md
- https://udacity.github.io/git-styleguide/
- https://shields.io/
- https://tabletomarkdown.com/convert-spreadsheet-to-markdown/
- https://github.com/georgevbsantiago/qsacnpj/
- https://www.kaggle.com/hugomathien/soccer/home
- https://www.w3schools.com/python/python_intro.asp
- https://github.com/databricks/spark-csv/blob/master/src/main/scala/com/databricks/spark/csv/util/TextFile.scala
- https://github.com/databricks/spark-csv/blob/master/src/test/scala/com/databricks/spark/csv/util/TextFileSuite.scala
- https://github.com/wuga214/TEACHING_OSU_DocAnalysis_Fall2015_Assign4/blob/master/code/a4example/BadRecordCount.java