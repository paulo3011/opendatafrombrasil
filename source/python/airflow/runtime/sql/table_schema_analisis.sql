-- 32
select max(len(description)) from open_data.dim_city_code;
-- 150
select max(len(description)) from open_data.dim_cnae;
-- 40
select max(len(description)) from open_data.dim_country_code;
-- 72
select max(len(description)) from open_data.dim_legal_nature;
-- 69
select max(len(description)) from open_data.dim_partner_qualification;

select 
	max(len(partnername)) as max_partnername 
	,max(len(partnerdocument)) as max_partnerdocument
	,max(len(legalrepresentative)) as max_legalrepresentative
	,max(len(representativename)) as max_representativename
from open_data.dim_partner;
/*
Name                   |Value|
-----------------------|-----|
max_partnername        |133  |
max_partnerdocument    |14   |
max_legalrepresentative|11   |
max_representativename |60   |
*/

select 
	max(len(basiccnpj)) as max_basiccnpj
	,max(len(legalname)) as max_legalname 
from open_data.dim_company;
/*
Name         |Value|
-------------|-----|
max_basiccnpj|8    |
max_legalname|150  |
*/

select * from open_data.fact_establishment limit 4;
select 
	max(len(basiccnpj)) as max_basiccnpj
	,max(len(cnpjorder)) as max_cnpjorder
	,max(len(cnpjcheckingdigit)) as max_cnpjcheckingdigit
	,max(len(fantasyname)) as max_fantasyname 
	,max(len(cityabroadname)) as max_cityabroadname
	,max(len(secondarycnae)) as max_secondarycnae
	,max(len(addresstype)) as max_addresstype
	,max(len(address)) as max_address
	,max(len(addressnumber)) as max_addressnumber
	,max(len(addresscomplement)) as max_addresscomplement
	,max(len(addressdistrict)) as max_addressdistrict
	,max(len(zipcode)) as max_zipcode
	,max(len(state)) as max_state
	,max(len(telephone1areacode)) as max_telephone1areacode
	,max(len(telephone1)) as max_telephone1
	,max(len(taxpayeremail)) as max_taxpayeremail
from open_data.fact_establishment;

/*
Name                  |Value|
----------------------|-----|
max_basiccnpj         |8    |
max_cnpjorder         |4    |
max_cnpjcheckingdigit |2    |
max_fantasyname       |57   |
max_cityabroadname    |30   |
max_secondarycnae     |791  |
max_addresstype       |20   |
max_address           |60   |
max_addressnumber     |7    |
max_addresscomplement |156  |
max_addressdistrict   |50   |
max_zipcode           |8    |
max_state             |2    |
max_telephone1areacode|4    |
max_telephone1        |8    |
max_taxpayeremail     |83   |
 */

select taxpayeremail from open_data.fact_establishment where len(taxpayeremail)=83;
/*
Name         |Value                                                                              |
-------------|-----------------------------------------------------------------------------------|
taxpayeremail|kybass.imoveis@gmail.com                                        kybass@terra.com.br|
 */

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
dim_company              |5032255 |
fact_establishment       |5304980 |
dim_simple_national      |27600101|
dim_partner              |2161072 |
dim_city_code            |5571    |
dim_cnae                 |1358    |
dim_country_code         |255     |
dim_legal_nature         |88      |
dim_partner_qualification|68      |
*/
