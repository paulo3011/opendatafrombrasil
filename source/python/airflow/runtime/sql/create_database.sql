-- Create a new schema on redshift called open_data
CREATE SCHEMA IF NOT EXISTS open_data;
-- select open_data as default schema 
SET search_path TO open_data;

-- drop tables to recreate if exists
DROP TABLE IF EXISTS fact_establishment;
DROP TABLE IF EXISTS dim_simple_national;
DROP TABLE IF EXISTS dim_partner;

DROP TABLE IF EXISTS dim_company;

DROP TABLE IF EXISTS dim_city_code;
DROP TABLE IF EXISTS dim_country_code;
DROP TABLE IF EXISTS dim_partner_qualification;
DROP TABLE IF EXISTS dim_cnae;
DROP TABLE IF EXISTS dim_legal_nature;

--DROP TABLE open_data.dim_city_code;
CREATE TABLE IF NOT EXISTS open_data.dim_city_code
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(100)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE ALL
;
ALTER TABLE open_data.dim_city_code owner to opendata_airflow;

--DROP TABLE open_data.dim_cnae;
CREATE TABLE IF NOT EXISTS open_data.dim_cnae
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE ALL
;
ALTER TABLE open_data.dim_cnae owner to opendata_airflow;

--DROP TABLE open_data.dim_country_code;
CREATE TABLE IF NOT EXISTS open_data.dim_country_code
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(100)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE ALL
;
ALTER TABLE open_data.dim_country_code owner to opendata_airflow;

--DROP TABLE open_data.dim_legal_nature;
CREATE TABLE IF NOT EXISTS open_data.dim_legal_nature
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(150)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE ALL
;
ALTER TABLE open_data.dim_legal_nature owner to opendata_airflow;

--DROP TABLE open_data.dim_partner_qualification;
CREATE TABLE IF NOT EXISTS open_data.dim_partner_qualification
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(150)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE ALL
;
ALTER TABLE open_data.dim_partner_qualification owner to opendata_airflow;

--DROP TABLE open_data.dim_company;
CREATE TABLE IF NOT EXISTS open_data.dim_company
(
	basiccnpj VARCHAR(8) NOT NULL  ENCODE lzo
	,legalname VARCHAR(300)   ENCODE lzo
	,legalnature INTEGER NULL REFERENCES dim_legal_nature (code) ENCODE az64
	,responsiblequalification SMALLINT   ENCODE az64
	,companycapital NUMERIC(14,2)   ENCODE az64
	,companysize SMALLINT   ENCODE az64
	,federatedentityresponsible VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE KEY
DISTKEY (basiccnpj)
COMPOUND SORTKEY (basiccnpj, companysize, legalnature)
;
ALTER TABLE open_data.dim_company owner to opendata_airflow;

--DROP TABLE open_data.dim_partner;
CREATE TABLE IF NOT EXISTS open_data.dim_partner
(
	basiccnpj VARCHAR(8) NOT NULL REFERENCES dim_company (basicCnpj) ENCODE lzo
	,partnertype SMALLINT   ENCODE az64
	,partnername VARCHAR(300)   ENCODE lzo
	,partnerdocument VARCHAR(20) NOT NULL  ENCODE lzo
	,partnerqualification INTEGER NULL REFERENCES dim_partner_qualification (code) ENCODE az64
	,partnerstartdate DATE   ENCODE az64
	,country INTEGER NULL REFERENCES dim_country_code (code) ENCODE az64
	,legalrepresentative VARCHAR(300)   ENCODE lzo
	,representativename VARCHAR(300)   ENCODE lzo
	,representativequalification INTEGER   ENCODE az64
	,agerange SMALLINT   ENCODE az64
	,PRIMARY KEY (basiccnpj, partnerdocument)
)
DISTSTYLE KEY
DISTKEY (basiccnpj)
COMPOUND SORTKEY (basiccnpj, partnertype, partnerqualification, country, agerange)
;
ALTER TABLE open_data.dim_partner owner to opendata_airflow;

--DROP TABLE open_data.dim_simple_national;
CREATE TABLE IF NOT EXISTS open_data.dim_simple_national
(
	basiccnpj VARCHAR(8) NOT NULL REFERENCES dim_company (basicCnpj) ENCODE lzo
	,issimple BOOLEAN   ENCODE RAW
	,simpleoptiondate DATE   ENCODE az64
	,simpleexclusiondate DATE   ENCODE az64
	,ismei BOOLEAN   ENCODE RAW
	,meioptiondate DATE   ENCODE az64
	,meiexclusiondate DATE   ENCODE az64
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE KEY
DISTKEY (basiccnpj)
COMPOUND SORTKEY (basiccnpj, issimple, ismei)
;
ALTER TABLE open_data.dim_simple_national owner to opendata_airflow;

--DROP TABLE open_data.fact_establishment;
CREATE TABLE IF NOT EXISTS open_data.fact_establishment
(
	basiccnpj VARCHAR(8) NOT NULL REFERENCES dim_company (basicCnpj) ENCODE lzo
	,cnpjorder VARCHAR(4)   ENCODE lzo
	,cnpjcheckingdigit VARCHAR(2)   ENCODE lzo
	,matrixbranch SMALLINT   ENCODE az64
	,fantasyname VARCHAR(300)   ENCODE lzo
	,registrationstatus SMALLINT   ENCODE az64
	,registrationstatusdate DATE   ENCODE az64
	,registrationstatusreason INTEGER   ENCODE az64
	,cityabroadname VARCHAR(100)   ENCODE lzo
	,countrycode INTEGER NULL REFERENCES dim_country_code (code) ENCODE az64
	,activitystartdate DATE   ENCODE az64
	,maincnae INTEGER NOT NULL REFERENCES dim_cnae (code) ENCODE az64
	,secondarycnae VARCHAR(2000)   ENCODE lzo
	,addresstype VARCHAR(40)   ENCODE lzo
	,address VARCHAR(300)   ENCODE lzo
	,addressnumber VARCHAR(30)   ENCODE lzo
	,addresscomplement VARCHAR(300)   ENCODE lzo
	,addressdistrict VARCHAR(100)   ENCODE lzo
	,zipcode VARCHAR(10)   ENCODE lzo
	,state VARCHAR(2)   ENCODE lzo
	,citycode INTEGER NULL REFERENCES dim_city_code (code) ENCODE az64
	,telephone1areacode VARCHAR(4)   ENCODE lzo
	,telephone1 VARCHAR(10)   ENCODE lzo
	,telephone2areacode VARCHAR(4)   ENCODE lzo
	,telephone2 VARCHAR(10)   ENCODE lzo
	,faxareacode VARCHAR(4)   ENCODE lzo
	,faxnumber VARCHAR(10)   ENCODE lzo
	,taxpayeremail VARCHAR(300)   ENCODE lzo
	,specialsituation VARCHAR(300)   ENCODE lzo
	,specialsituationdate DATE   ENCODE az64
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE KEY
DISTKEY (basiccnpj)
COMPOUND SORTKEY (basiccnpj, registrationstatus, matrixbranch, maincnae, state, citycode, addressdistrict, addresstype, specialsituation)
;
ALTER TABLE open_data.fact_establishment owner to opendata_airflow;

