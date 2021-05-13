-- stage_establishment maxlength

SELECT 
	max(len(basic_cnpj)) as basic_cnpj
	,max(len(cnpj_order )) as cnpj_order
	,max(len(cnpj_checking_digit )) as cnpj_checking_digit
	,max(len(matrix_branch )) as matrix_branch
	,max(len(fantasy_name )) as fantasy_name
	,max(len(registration_situation )) as registration_situation
	,max(len(date_registration_situation )) as date_registration_situation
	,max(len(reason_registration_situation )) as reason_registration_situation
	,max(len(name_city_abroad )) as name_city_abroad
	,max(len(country_code )) as country_code
	,max(len(activity_start_date )) as activity_start_date
	,max(len(main_fiscal_cnae )) as main_fiscal_cnae
	,max(len(secondary_fiscal_cnae )) as secondary_fiscal_cnae
	,max(len(type_of_address )) as type_of_address
	,max(len(address )) as address
	,max(len(address_number )) as address_number
	,max(len(address_complement )) as address_complement
	,max(len(address_district )) as address_district
	,max(len(zip_code )) as zip_code
	,max(len(federation_unit )) as federation_unit
	,max(len(city_jurisdiction_code )) as city_jurisdiction_code
	,max(len(telephone1_area_code )) as telephone1_area_code
	,max(len(telephone1 )) as telephone1
	,max(len(telephone2_area_code )) as telephone2_area_code
	,max(len(telephone2 )) as telephone2
	,max(len(fax_area_code )) as fax_area_code
	,max(len(fax_number )) as fax_number
	,max(len(contributors_email )) as contributors_email
	,max(len(special_situation )) as special_situation
FROM stage_establishment;

/*
Name                         |Value|
-----------------------------|-----|
basic_cnpj                   |8    |
cnpj_order                   |4    |
cnpj_checking_digit          |2    |
matrix_branch                |1    |
fantasy_name                 |61   |
registration_situation       |1    |
date_registration_situation  |10   |
reason_registration_situation|2    |
name_city_abroad             |52   |
country_code                 |3    |
activity_start_date          |10   |
main_fiscal_cnae             |8    |
secondary_fiscal_cnae        |791  | * (max)
type_of_address              |119  |
address                      |63   |
address_number               |39   |
address_complement           |156  |
address_district             |50   |
zip_code                     |29   |
federation_unit              |28   |
city_jurisdiction_code       |8    |
telephone1_area_code         |8    | **
telephone1                   |8    |
telephone2_area_code         |8    | **
telephone2                   |8    | 
fax_area_code                |8    | **
fax_number                   |33   | **
contributors_email           |115  |
special_situation            |35   |


-- ** wrong processing for: (RICARDOVINICIUSCARNEIRO@GMAIL.COM)
-- basic_cnpj                   |33539021 
-- cnpj_order                   |0001     
-- cnpj_checking_digit          |39       
-- matrix_branch                |1        
*/
select * from stage_establishment where len(fax_number)=33;
*/