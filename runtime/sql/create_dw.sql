-- Create a new schema on redshift called dw
CREATE SCHEMA IF NOT EXISTS open_data;
-- select dw schema as default
SET search_path TO open_data;

-- Establishment
DROP TABLE IF EXISTS stage_establishment;

CREATE TABLE IF NOT EXISTS stage_establishment
(
	basic_cnpj VARCHAR(10)
	,cnpj_order VARCHAR(10)
	,cnpj_checking_digit VARCHAR(10)
	,matrix_branch SMALLINT
	,fantasy_name VARCHAR(300)
	,registration_situation SMALLINT
	,date_registration_situation DATE 
	,reason_registration_situation SMALLINT
	,name_city_abroad VARCHAR(300) 
	,country_code SMALLINT
	,activity_start_date DATE
	,main_fiscal_cnae INTEGER
	,secondary_fiscal_cnae VARCHAR(max)
	,type_of_address VARCHAR(300)
	,address VARCHAR(300)
	,address_number VARCHAR(50)
	,address_complement VARCHAR(300)
	,address_district VARCHAR(300)
	,zip_code VARCHAR(50)
	,federation_unit VARCHAR(300)
	,city_jurisdiction_code VARCHAR(300)
	,telephone1_area_code VARCHAR(50)
	,telephone1 VARCHAR(50)
	,telephone2_area_code VARCHAR(50)
	,telephone2 VARCHAR(50)
	,fax_area_code VARCHAR(50)
	,fax_number VARCHAR(50)
	,contributors_email VARCHAR(300)
	,special_situation VARCHAR(300)
	,special_situation_date DATE
);