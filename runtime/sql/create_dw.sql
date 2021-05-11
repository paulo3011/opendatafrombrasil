-- Create a new schema on redshift called dw
CREATE SCHEMA IF NOT EXISTS open_data;
-- select dw schema as default
SET search_path TO open_data;


DROP TABLE IF EXISTS stage_establishment;
DROP TABLE IF EXISTS stage_company;
DROP TABLE IF EXISTS stage_partner;
DROP TABLE IF EXISTS stage_simple_national;
DROP TABLE IF EXISTS stage_city_code;
DROP TABLE IF EXISTS stage_cnae;
DROP TABLE IF EXISTS stage_country_code;
DROP TABLE IF EXISTS stage_legal_nature;
DROP TABLE IF EXISTS stage_partner_qualification;

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

CREATE TABLE IF NOT EXISTS stage_company
(
	basic_cnpj VARCHAR(10)
	,legal_name VARCHAR(300)
	,legal_nature VARCHAR(300)
	,responsible_qualification VARCHAR(300)
	,company_capital VARCHAR(300)
	,company_size VARCHAR(300)
	,federative_entity_responsible VARCHAR(300)	
);

CREATE TABLE IF NOT EXISTS stage_partner
(
	basic_cnpj VARCHAR(300)
	,partner_type VARCHAR(300)
	,partner_name VARCHAR(300)
	,partner_document VARCHAR(300)
	,partner_qualification VARCHAR(300)
	,partner_start_date VARCHAR(300)
	,country VARCHAR(300)
	,legal_representative VARCHAR(300)
	,representative_name VARCHAR(300)
	,representative_qualification VARCHAR(300)
	,age_range VARCHAR(300)	
);

CREATE TABLE IF NOT EXISTS stage_simple_national
(
	basic_cnpj VARCHAR(300)
	,is_simple VARCHAR(300)
	,simple_option_date DATE
	,simple_exclusion_date DATE
	,is_mei VARCHAR(300)
	,mei_option_date DATE
	,mei_exclusion_date DATE	
);

CREATE TABLE IF NOT EXISTS stage_city_code
(
	code varchar(60)
	,description varchar(300)
);

CREATE TABLE IF NOT EXISTS stage_cnae
(
	code varchar(60)
	,description varchar(300)
);

CREATE TABLE IF NOT EXISTS stage_country_code
(
	code varchar(60)
	,description varchar(300)
);

CREATE TABLE IF NOT EXISTS stage_legal_nature
(
	code varchar(60)
	,description varchar(300)
);

CREATE TABLE IF NOT EXISTS stage_partner_qualification
(
	code varchar(60)
	,description varchar(300)
);