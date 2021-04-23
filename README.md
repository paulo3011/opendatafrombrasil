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

Este projeto tem como objetivo final permitir que as pessoas possam fazer análises relacionadas as empresas brasileiras e dar base para tomadas de decisões, com base na concorrência e ou outros fatores observados, como por exemplo: 

- Em que cidade é um bom local para abrir uma empresa?
- Quais são os clientes, cidades ou regiões que é possível explorar?
- Qual o tamanho do mercado disponível?
- Quem são meus clientes, quais empresas eles possuem, existe alguma informação de contato disponível?
- Faço alguma abordagem diferenciada para alguns clientes em função de existir clientes se relacionando entre si?

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

# Referências técnicas

- https://github.com/jonatasemidio/multilanguage-readme-pattern/blob/master/README.md
- https://github.com/tiimgreen/github-cheat-sheet/blob/master/README.md
- https://udacity.github.io/git-styleguide/
- https://shields.io/
- https://tabletomarkdown.com/convert-spreadsheet-to-markdown/