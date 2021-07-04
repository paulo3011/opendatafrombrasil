# Open data from Brasil

Leia em outro idioma (Read this in other language):

[![en](https://img.shields.io/badge/lang-en-red.svg)](README-en-US.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.md)

## Summary

This project was made to explore the Brazil open data about companies.

This project followed the following steps:

1. Define project scope and collect data
2. Explore and evaluate the data
3. Define the data model
4. Run ETL to model the data
5. Describe and document the Project


## 1. Definition of project scope and data collection

### __Project scope__

This project's ultimate goal is to allow people to make analyzes related to Brazilian companies and provide a basis for decision-making, based on competition and other observed factors, such as:

- In which city is a good place to start a business?
- Who are the customers (companies or people who are partners of any company), cities or regions where it is possible to operate or offer services and products?
- How big is the market available?
- Who are my customers, what companies do they own, is there any contact information available?
- Do I have a different approach for some clients due to the existence of a connection (society) between clients?

## 2. Explore and evaluate the data

### __Description of data sets__

The datasets used initially come from the Brazilian government and are made available openly.

__CNPJ Open Data__


**Feature**|**Description**|**Status**
-----|:-----:|:-----:
Update frequency:|monthly
data format:|csv
Origin:|[Click here](https://www.gov.br/receitafederal/pt-br/assuntos/orientacao-tributaria/cadastros/consultas/dados-publicos-cnpj)|![Website](https://img.shields.io/website?url=http%3A%2F%2F200.152.38.155%2FCNPJ%2F)
Layout:|[Click here](https://www.gov.br/receitafederal/pt-br/assuntos/orientacao-tributaria/cadastros/consultas/arquivos/NOVOLAYOUTDOSDADOSABERTOSDOCNPJ.pdf)

</br>

Files and examples:

__Establishments__

![csv_estabelecimentos.jpg](./assets/images/cnpj/csv_estabelecimentos.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#estabelecimentos)

</br>

__Companies__

![csv_empresas.jpg](./assets/images/cnpj/csv_empresas.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#empresas)

</br>

__Partners__

![csv_socios.jpg](./assets/images/cnpj/csv_socios.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#sócios)

</br>

__CNAE - National Classification of Economic Activities__

![csv_cnaes.jpg](./assets/images/cnpj/csv_cnaes.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#cnae---classificação-nacional-de-atividades-econômicas)

_Agency responsible for this classification:_ [concla](https://concla.ibge.gov.br)

</br>

__Legal Nature__

![csv_naturezajuridica.jpg](./assets/images/cnpj/csv_naturezajuridica.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#natureza-jurídica)

_Agency responsible for this classification:_ [concla](https://concla.ibge.gov.br)

</br>

__Member Qualification__

![csv_qualificacao.jpg](./assets/images/cnpj/csv_qualificacao.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#qualificação-do-sócio)

</br>

__City Code__

![csv_municipio.jpg](./assets/images/cnpj/csv_municipio.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#código-do-município)

</br>

__Country code__

![csv_pais.jpg](./assets/images/cnpj/csv_pais.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#código-do-país)

</br>

__Data from the National Simple__

![csv_simples.jpg](./assets/images/cnpj/csv_simples.jpg)
[Field Dictionary](./assets/docs/database/dictionary-en-US.md#dados-do-simples-nacional)

</br>


__Reason for Registration Status__

https://receita.economia.gov.br/orientacao/tributaria/cadastros/cadastro-nacional-de-pessoas-juridicas-cnpj/DominiosMotivoSituaoCadastral.csv/view

__Comments__

1. The field (CNPJ/CPF OF THE PARTNER) and (CNPJ/CPF OF THE REPRESENTATIVE) of the layout of partners must be uncharacterized according to the rule below:

- Concealment of confidential personal information as in the case of the CPF, which must be uncharacterized by hiding the first three digits and the two verification digits, as provided for in art. 129 § 2 of Law No. 13.473/2017 (LDO
2018).

2. Responsible Federative Entity Field - EFR, in the Main Layout (Registration Data):

It must be completed for the cases of Bodies and Entities of the 1XX Legal Nature group. For other natures, this attribute is blank.

Examples of text that should appear in the final file:

- UNION;
- FEDERAL DISTRICT;
- BAHIA;

for municipalities, also display the acronym of the UF:

- SAO PAULO-SP;
- BELO HORIZONTE – MG;

3. Age Range field, in the Members Layout

Based on the date of birth of the CPF of each partner, the value for the Age group field must be created according to the rule below:

- 1 for the intervals between 0 and 12 years;
- 2 for the intervals between 13 and 20 years;
- 3 for the intervals between 21 and 30 years;
- 4 for the intervals between 31 and 40 years;
- 5 for the intervals between 41 and 50 years;
- 6 for the intervals between 51 and 60 years;
- 7 for the intervals between 61 and 70 years;
- 8 for the intervals between 71 and 80 years;
- 9 for people over 80 years old;
- 0 for not applicable;

4. The CNAE FISCAL SECONDARY Field, in the Establishments Layout:

It must be filled in with each occurrence being separated by a comma, for cases of multiple occurrences.

</br>

### __Known Data Issues and How to Address__
<br/>

__Archive:__ K3241.K03200Y0.D10410.ESTABELE

__Field:__ Date registration status

__Value:__ 0

__Problema:__ Invalid format

```txt
"30005475";"0001";"31";"1";"";"2";"0";"0";"";"";"20180322";"6204000";"6209100,7490104";"AVENIDA";"PAULISTA";"2202";"CONJ  54-B";"BELA VISTA";"01310300";"SP";"7107";"11";"59085410";"";"";"";"";"CEFISCO@UOL.COM.BR";"";""
```

__Archive:__ 

__Field:__ Date registration status

__Value:__ 4100813

__Problema:__ Invalid format

```txt
"18825426";"0001";"40";"1";"ALAMBIQUE SANTO ANTONIO";"8";"20150209";"73";"";"";"4100813";"5611204";"";"RUA";"DEOLINDO PERIM";"79";"";"ITAPUA";"29101811";"ES";"5703";"27";"98921990";"27";"";"";"";"JFJUNCAL@GMAIL.COM";"";""
```

__Archive:__ K3241.K03200Y0.D10410.ESTABELE

__Field:__ Date registration status

__Value:__ 4100813

__Problema:__ Complement "EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805" it has a semicolon which is the file separator and depending on the csv parser being used it gets lost and messes up the columns.

```txt
"36452531";"0001";"62";"1";"AMPPLA CREATIVE STUDIO";"2";"20200221";"0";"";"";"20200221";"1821100";"5819100,5811500,5812302,1813001,5912099,5812301,7319002,5813100";"ESTRADA";"DO MANDU";"560";"EDIF HORTO SAO RAFAEL;BLOCO 2;ANDAR 805";"SAO MARCOS";"41250400";"BA";"3849";"71";"99479533";"";"";"";"";"JONATASMA@GMAIL.COM";"";""
```

<br/>

### __Necessary care__
<br/>

- Found records with characters that break the apache spark default parser using DataFrameReader as "\\" which is the default scape character. It was necessary to implement a custom csv's reading to prevent the columns of the files from being broken (with more or less columns).

- Take care of fields that may be null and evaluate if the field contains values ​​like: null or empty ("")

```Java
public static String fixStringValues(String value) {
        if (value == null || value.equals("null"))
            return null;
        return value.replaceAll("^\"|\"$", "");
    }
```

- It was necessary to treat numeric values ​​(integers) that contained leading zeros, example: 0001. In these cases, the leading zeros were removed before converting to integer. Also it was necessary to check for null, empty values ​​before trying to convert.

- To convert monetary values ​​it was necessary to use local format from Brazil

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

- Invalid dates were treated as null


## 3. Define the data model

### __Model__

![csv_estabelecimentos.jpg](./assets/images/cnpj/opendata.png)


## 4. Run ETL to model the data

1. Run Spark job to process CSV files and create ORC files

* You need to download the csv files from: http://200.152.38.155/CNPJ/ 
* Unzip the files to some folder

```shell
# Running on spark cluster or local mode
spark-submit --class application.batch.App opendata_etl-1.0.jar --spark-conf spark.app.name=brasil-open-etl --input-path E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\ --input-type cnpj_raw --input-format csv --output-type cnpj_lake --output-format orc
# or locally
java -jar opendata_etl-1.0.jar --spark-conf spark.master=local[*],spark.app.name=brasil-open-etl --input-path E:\\hdfs\\cnpj\\2021-04-14\\allfilesdev\\ --input-type cnpj_raw --input-format csv --output-type cnpj_lake --output-format orc
```
Sample result:

![spark_sample_2.jpg](./assets/images/cnpj/spark_sample_2.jpg)

After save spark output on s3, manualy run airflow dag to load the ORC files into Redshift.

![airflow.jpg](./assets/images/cnpj/airflow_data_dag_2.jpg)

## 5. Describe and document the Project

### Defending Decisions

__Distribution strategy__:

Node size: 1 x dc2.large (160 GB storage) com 2 vCPU (2 slice), 15 GiB RAM

(https://docs.aws.amazon.com/pt_br/redshift/latest/mgmt/working-with-clusters.html)

```sql
select
	(select count(0) from open_data.dim_company) as dim_company
	,(select count(0) from open_data.fact_establishment) as fact_establishment
	,(select count(0) from open_data.dim_simple_national) as dim_simple_national
	,(select count(0) from open_data.dim_partner) as dim_partner
	
	,(select count(0) from open_data.dim_city_code) as dim_city_code
	,(select count(0) from open_data.dim_cnae) as dim_cnae
	,(select count(0) from open_data.dim_country_code) as dim_country_code
	,(select count(0) from open_data.dim_legal_nature) as dim_legal_nature	
	,(select count(0) from open_data.dim_partner_qualification) as dim_partner_qualification
;

/*
Name                     |Value   |
-------------------------|--------|
dim_company              |45485995|
fact_establishment       |48085895|
dim_simple_national      |27600101|
dim_partner              |40666844|
dim_city_code            |5571    |
dim_cnae                 |1358    |
dim_country_code         |255     |
dim_legal_nature         |88      |
dim_partner_qualification|68      |
*/
```

As we know the frequent access pattern, we defined the following strategy:

- Was not used "Even" distribution because all tables can be joined, and the cost is high to do this operation- Was used "All" distribution for small tables to speed up joins (dim_city_code, dim_cnae, dim_country_code, dim_legal_nature, dim_partner_qualification)
- Was used KEY distribution for larger tables to put similar values in the same slice and speed up queries
- Was distributed dim_company, dim_simple_national, dim_partner and fact_establishment on the joining key (basiccnpj) to eliminates shuffling. This column is good because all information about one company is distributed between these tables and this information is joined by this key.

Sorting strategy:

It was used sorting key to minimizes the query time.

"Sorting enables efficient handling of range-restricted predicates. Amazon Redshift stores columnar data in 1 MB disk blocks. The min and max values for each block are stored as part of the metadata. If query uses a range-restricted predicate, the query processor can use the min and max values to rapidly skip over large numbers of blocks during table scans." (https://docs.aws.amazon.com/redshift/latest/dg/t_Sorting_data.html)

Frequent queries:

- establishment, dim_company and dim_simple_national by: city, cnae, country, legal nature, matrix, companysize etc;
- dim_partner by: partnertype, partnerqualification, country, agerange;

SORTKEY considerations:

- Was sorted by DISTKEY to speed up the join with related tables (dim and facts tables)
- Was sorted by the frequent query filters (city, cnae, country, legal nature, matrix, companysize and partner qualification, etc) to speed up query time
- I choose COMPOUND SORTKEY because the data will be sorted in the same order that the sortkey columns and the table filter will be probably done by sortkey columns and the type INTERLEAVED isn't a good choose for columns like datetime and autoincrements id's

__Redshift__

Redshift was used to be the data warehouse for the following reasons:

- Amazon Redshift is a fully managed (We don't need to do maintenance), petabyte-scale, cloud-based data warehouse service who manages the work needed to set up, operate, and scale a data warehouse. For example, provisioning the infrastructure capacity, automating ongoing administrative tasks such as backups, and patching, and monitoring nodes and drives to recover from failures. Redshift also has automatic tuning capabilities, and surfaces recommendations for managing your warehouse in Redshift Advisor
- On-premises data warehouses require significant time and resource to administer, especially for large datasets. In addition, the financial costs associated with building, maintaining, and growing self-managed, on-premises data warehouses are very high
- Amazon Redshift is optimized for high-performance analysis and reporting of very large datasets
- Amazon Redshift integrates with various data loading, ETL, business intelligence reporting and analytics tools
- Can easily support thousands of concurrent users and concurrent queries, with consistently fast query performance
- Can load data in columnar formats and execute queries in a parallel and optimized way (Massively Parallel Processing - MPP)
- Supports optimized file formats like ORC and PARQUET
- Redshift lets you easily save the results of your queries back to your S3 data lake using open formats, like Apache Parquet
- Allows scale up and scale down


Seealso: 

- https://aws.amazon.com/redshift/features/concurrency-scaling/?nc=sn&loc=2&dn=3
- https://docs.aws.amazon.com/redshift/latest/dg/c_high_level_system_architecture.html
- https://aws.amazon.com/redshift/faqs/?nc1=h_ls

__Airflow__

Airflow was used to orchestrate the jobs for several reasons:

- Allow schedule, monitoring and view the whole history of executions
- Allow visualize the workflow executions and logs
- Stable and widely used tool
- Allows customization through the creation of custom plugins
- Allows scale up

__Apache Spark__

Apache Spark was used for several reasons:

- The dataset is not small and needs some tool that can be able to handle it
- Apache Spark is a unified analytics engine for large-scale data processing, stable and widely used tool
- Spark offers over 80 high-level operators that make it easy to build parallel apps
- Apache Spark achieves high performance for both batch and streaming data, using a state-of-the-art DAG scheduler, a query optimizer, and a physical execution engine;
- Spark runs on Hadoop, Apache Mesos, Kubernetes, standalone, or in the cloud. It can access diverse data sources;

Seealso: https://spark.apache.org/


#### Addressing Other Scenarios

A logical approach to this project under the following scenarios:

- If the data was increased by 100x.
- If the pipelines would be run on a daily basis by 7 am every day.
- If the database needed to be accessed by 100+ people.

All the tools used in this project are ready to deal with the possible scenarios mentioned above because they are tools able to process data on a large scale and allow us increasing or decrease resource consumption as needed.

__Data increased by 100 x :__

Current storage consumption by the Redshift:

```sql
-- https://docs.aws.amazon.com/pt_br/redshift/latest/dg/r_SVV_TABLE_INFO.html
SELECT 
	"schema", 
	"table", 
	tbl_rows,
	"size", -- the size in blocks of 1 MB
	diststyle, 
	sortkey1 
from SVV_TABLE_INFO;

/*
schema   |table                    |tbl_rows|size|diststyle     |sortkey1     |
---------|-------------------------|--------|----|--------------|-------------|
open_data|dim_partner              |40666844|1236|KEY(basiccnpj)|basiccnpj    |
open_data|dim_cnae                 |    1358|   5|ALL           |AUTO(SORTKEY)|
open_data|fact_establishment       |48085895|4525|KEY(basiccnpj)|basiccnpj    |
open_data|dim_company              |45485995|1850|KEY(basiccnpj)|basiccnpj    |
open_data|dim_simple_national      |27600101| 456|KEY(basiccnpj)|basiccnpj    |
open_data|dim_country_code         |     255|   5|ALL           |AUTO(SORTKEY)|
open_data|dim_city_code            |    5571|   5|ALL           |AUTO(SORTKEY)|
open_data|dim_legal_nature         |      88|   5|ALL           |AUTO(SORTKEY)|
open_data|dim_partner_qualification|      68|   5|ALL           |AUTO(SORTKEY)|
*/

-- total size: 1236 + 5 + 4525 + 1850 + 456 + (4 * 5) = 8092 MB (8 GB)
```

If the data increased by 100 x the numbers of the data would be:

- CSV source files: 17 x 100 = 1700 GB (1,7 TB)
- Lake files (ORC format - output of spark processing): 5 GB x 100 = 500 GB (0,5 TB)
- Redshift storage consumption: 8 GB x 100 = 800 GB

Redshift recomendation for this scenario:

- For datasets under 1 TB is to use DC2 nodes and choose the number of nodes based on data size and performance requirements
- For 800 GB we can continue to use dc2.large and increase the node number to 10. We will have a total storage of 10x160 = 1600 GB (1,600TB) - Double the size is being considered due to redshift mirroring the data on another node's disks to reduce the risk of data loss
- Increasing the number of nodes, we also increase the query performance because Amazon Redshift distributes and executes queries in parallel across all of a cluster’s compute nodes. 
- The impact on cost at this size would be: 10 x $0.25 ($2,50) per hour. Considering 720 hours at the of the month we will have total cost of 720 x 2,50 ($1.800)
- We can reserve nodes for steady-state production workloads, and receive significant discounts over on-demand nodes (at least 20 percent discount over on-demand rates.)
- We can pause the cluster during unused time and leave it on only during working hours and reduce costs this way
- If we consider 8 hours per day of work and at least 20 percent discount over on-demand rates we will have 240 x 2,50 ($600), subtracting the discounts the total cost at the end of month would be $480 (600-120).
- We can also try to reduce to 7 the number of nodes to reduce cost (Testing before running in production of course)
- We can also try to use Redshift Spectrum to query directly on s3 and reduce the number of nodes (test before running in production, of course)
- Another possibility would be to assess the costs and response time for using AWS Athena as a way to query the data stored in s3
- It will not be necessary to replicate the cluster because the objective would be for internal use and for analysis that can take a few hours to re-establish the cluster in case of any problem that makes the cluster unavailable. In addition to the form of use, another point that imposes a certain limit on replicating the cluster is the cost of the service.

* DC2 nodes store data locally for high performance, and as the data size grows, we can add more compute nodes to increase the storage capacity of the cluster
* AWS recommends DC2 node types for the best performance at the lowest price
* When you run a cluster with at least two compute nodes, __data on each node is mirrored on disks of another node to reduce the risk of incurring data loss__.

Seealso:

- https://docs.aws.amazon.com/redshift/latest/mgmt/working-with-clusters.html
- https://aws.amazon.com/premiumsupport/knowledge-center/redshift-cluster-storage-space/
- https://aws.amazon.com/redshift/pricing/


__S3 and Spark Job impact (Data increased by 100 x):__

![airflow.jpg](./assets/images/cnpj/sizing.jpg)

The actual data distribution of the major table (fact_establishment):

```sql
select 
	e.state
	,count(0) as total_by_state 
	,(count(0)*100.0)/48085895.0 as percentage_of_total
from open_data.fact_establishment e group by e.state
order by count(0) desc;

/*
state|total_by_state|percentage_of_total|
-----|--------------|-------------------|
SP   |      13771427|      28.6392236226|
MG   |       5277500|      10.9751518610|
RJ   |       4141697|       8.6131224135|
RS   |       3496187|       7.2707121287|
PR   |       3221563|       6.6996007872|
BA   |       2458328|       5.1123681902|
SC   |       2140080|       4.4505358588|
GO   |       1648965|       3.4292072550|
PE   |       1471800|       3.0607728108|
CE   |       1370095|       2.8492658813|
ES   |        995624|       2.0705115294|
PA   |        974539|       2.0266629122|
MT   |        875544|       1.8207917311|
DF   |        805872|       1.6759010100|
MA   |        750845|       1.5614662054|
MS   |        649279|       1.3502483420|
PB   |        610967|       1.2705742505|
RN   |        562458|       1.1696943563|
AM   |        503559|       1.0472072943|
AL   |        463822|       0.9645697558|
PI   |        431652|       0.8976686406|
RO   |        345790|       0.7191090027|
TO   |        326762|       0.6795381473|
SE   |        319961|       0.6653947067|
EX   |        148921|       0.3096978854|
AC   |        117169|       0.2436660480|
AP   |        112170|       0.2332700680|
RR   |         93318|       0.1940652243|
BR   |             1|       0.0000020796|
 */

select 
	e.maincnae
	,count(0) as total_by_maincnae
	,(count(0)*100.0)/48085895.0 as percentage_of_total
from open_data.fact_establishment e group by e.maincnae 
order by count(0) desc limit 10;

/*
maincnae|total_by_maincnae|percentage_of_total|
--------|-----------------|-------------------|
 4781400|          2822601|       5.8699146600|
 9492800|          2798437|       5.8196629177|
 8888888|          1808469|       3.7609136733|
 5611203|          1590811|       3.3082695039|
 4712100|          1415944|       2.9446140079|
 9602501|          1276983|       2.6556290571|
 5611201|           855265|       1.7786192811|
 4399103|           779004|       1.6200259972|
 7319002|           692591|       1.4403204931|
 9430800|           607662|       1.2637011331|
 */ 

-- Brazilian National Classification of Economic Activities
select * from open_data.dim_cnae limit 3;

/*
code  |description                                              |
------|---------------------------------------------------------|
111302|Cultivo de milho                                         | (corn cultivation)
111399|Cultivo de outros cereais não especificados anteriormente| (Cultivation of other cereals)
112102|Cultivo de juta                                          | (Jute cultivation)
 */ 

select count(0) from open_data.dim_cnae;
-- 1358
```

To define which partitioning strategy to use was taking into account the following best practices:

- If the cardinality of a column will be very high, do not use that column for partitioning. For example, if you partition by a column userId and if there can be 1M distinct user IDs, then that is a bad partitioning strategy.
- Amount of data in each partition: You can partition by a column if you expect data in that partition to be at least 1 GB.

(https://docs.databricks.com/delta/best-practices.html)

Considering the increase in data size by 100x and the daily execution, partitioning could be done:

- As the most frequent analysis in the database will be considering economic activity (one or a restricted group of activities) and taking into account the distribution of data, we can have the main economic activity code as the main partition.
- It would be a maximum of 1358 partitions.
- Partitioning by economic activity proved better than partitioning by geographic distribution because the geographic distribution would make the data unevenly distributed. São Paulo, for example, would have 28% of the data.
- Partitioning by economic activity can be used to distribute data from dim_company, dim_partner, dim_simple_national and fact_establishment tables because the data fetching logic is the same.
- Other tables do not need partitioning because they are small.


__The pipelines would be run on a daily basis by 7 am every day__

* The CNPJ data source there's no need to run on a daily basis (The CNPJ data source is released on a monthly basis), but we will suppose this scenario for the purpose of this project.

Will be necessary to adjust the airflow job:

- Will need to build a new task on the current dag (cnpj_dag.py) to automate the spark work that processes CNPJ CSV files and saves to s3
- The new Spark task will call the EMR API to run work on a Spark cluster because the airflow is not meant to do this kind of processing
- Configure the dag schedule (cnpj_dag.py) to run on daily basis at 7 am every day. (schedule_interval="0 7 * * *")
- There will be an increase in cost due to the need for a daily EMR run. The cluster does not need to be running for 24 hours and will always be turned off after finishing the processing routine. (Will need to simulate the increase in data size and run the spark job to assess the total execution time in this scenario)
- There is no need to increase the airflow infrastructure because all the heavy lifting is done in Spark Cluster or Redshift Cluster
- We can do studies and tests to run airflow and Spark on the same cluster of kubernetes to assess cost savings
- The impact of running the routine daily would be to provide updated information with a maximum daley of 1 day. This allows to generate more accurate analysis and insights

Seealso:

- https://airflow.apache.org/docs/apache-airflow/1.10.1/scheduler.html
- https://en.wikipedia.org/wiki/Cron#CRON_expression
- https://www.upsolver.com/blog/partitioning-data-s3-improve-performance-athena-presto


__The database needed to be accessed by 100+ people:__

For this project in particular replication does not make sense because


# Future improvements

## Adding new datasets

__Population data__

Collect data related to the Brazilian population to support analyzes related to starting a business or detecting opportunities.

__Information related to land and real estate values__

Collect data related to the purchase and sale price of properties and land so that it is possible to subsidize analyzes related to starting a business, detecting opportunities and competition.

__Complaints related to companies__

Collect data related to complaints opened or reported by customers on sites like Complain Here, procon para can analyze companies from this perspective. It can measure the level of customer satisfaction.

__Weather information__

Collect information related to temperature and weather to support analysis of starting a business, detecting opportunities and risks.

__Data related to available workforce__

Data on education and educational level.

__CNAE - Detailed structure and explanatory notes__

Describes in more detail which activities are included or not in each CNAE. This dataset can be used to more accurately locate companies by activities they may or may not perform.

The PDF shows a pattern in the way of describing which activities are or are not included in each code, which allows for programmatic extraction.	

https://concla.ibge.gov.br/images/concla/downloads/revisao2007/PropCNAE20/CNAE20_NotasExplicativas.pdf



# Technical references

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