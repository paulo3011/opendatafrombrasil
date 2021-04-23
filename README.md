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
[Dicionário de campos](./assets/docs/database/dictionary.md#Estabelecimentos)

</br>

__Empresas__
![csv_estabelecimentos.jpg](./assets/images/cnpj/csv_empresas.jpg)
[Dicionário de campos](./assets/docs/database/dictionary.md#Empresas)

</br>
</br>



# Referências técnicas

- https://github.com/jonatasemidio/multilanguage-readme-pattern/blob/master/README.md
- https://github.com/tiimgreen/github-cheat-sheet/blob/master/README.md
- https://udacity.github.io/git-styleguide/
- https://shields.io/