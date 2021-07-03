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

-- open_data.dim_city_code definition

-- Drop table

-- DROP TABLE open_data.dim_city_code;

--DROP TABLE open_data.dim_city_code;
CREATE TABLE IF NOT EXISTS open_data.dim_city_code
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_city_code owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254543 ON open_data.dim_city_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254543" AFTER
DELETE
    ON
    dim_city_code
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('fact_establishment_citycode_fkey', 'fact_establishment', 'dim_city_code', 'UNSPECIFIED', 'citycode', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254544 ON open_data.dim_city_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254544" AFTER
UPDATE
    ON
    dim_city_code
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('fact_establishment_citycode_fkey', 'fact_establishment', 'dim_city_code', 'UNSPECIFIED', 'citycode', 'code');

-- Permissions;


-- open_data.dim_cnae definition

-- Drop table

-- DROP TABLE open_data.dim_cnae;

--DROP TABLE open_data.dim_cnae;
CREATE TABLE IF NOT EXISTS open_data.dim_cnae
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_cnae owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254539 ON open_data.dim_cnae;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254539" AFTER
DELETE
    ON
    dim_cnae
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('fact_establishment_maincnae_fkey', 'fact_establishment', 'dim_cnae', 'UNSPECIFIED', 'maincnae', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254540 ON open_data.dim_cnae;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254540" AFTER
UPDATE
    ON
    dim_cnae
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('fact_establishment_maincnae_fkey', 'fact_establishment', 'dim_cnae', 'UNSPECIFIED', 'maincnae', 'code');

-- Permissions;


-- open_data.dim_country_code definition

-- Drop table

-- DROP TABLE open_data.dim_country_code;

--DROP TABLE open_data.dim_country_code;
CREATE TABLE IF NOT EXISTS open_data.dim_country_code
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_country_code owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254535 ON open_data.dim_country_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254535" AFTER
DELETE
    ON
    dim_country_code
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('fact_establishment_countrycode_fkey', 'fact_establishment', 'dim_country_code', 'UNSPECIFIED', 'countrycode', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254536 ON open_data.dim_country_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254536" AFTER
UPDATE
    ON
    dim_country_code
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('fact_establishment_countrycode_fkey', 'fact_establishment', 'dim_country_code', 'UNSPECIFIED', 'countrycode', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254567 ON open_data.dim_country_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254567" AFTER
DELETE
    ON
    dim_country_code
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_partner_country_fkey', 'dim_partner', 'dim_country_code', 'UNSPECIFIED', 'country', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254568 ON open_data.dim_country_code;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254568" AFTER
UPDATE
    ON
    dim_country_code
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_partner_country_fkey', 'dim_partner', 'dim_country_code', 'UNSPECIFIED', 'country', 'code');

-- Permissions;


-- open_data.dim_legal_nature definition

-- Drop table

-- DROP TABLE open_data.dim_legal_nature;

--DROP TABLE open_data.dim_legal_nature;
CREATE TABLE IF NOT EXISTS open_data.dim_legal_nature
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_legal_nature owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254519 ON open_data.dim_legal_nature;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254519" AFTER
DELETE
    ON
    dim_legal_nature
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_company_legalnature_fkey', 'dim_company', 'dim_legal_nature', 'UNSPECIFIED', 'legalnature', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254520 ON open_data.dim_legal_nature;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254520" AFTER
UPDATE
    ON
    dim_legal_nature
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_company_legalnature_fkey', 'dim_company', 'dim_legal_nature', 'UNSPECIFIED', 'legalnature', 'code');

-- Permissions;


-- open_data.dim_partner_qualification definition

-- Drop table

-- DROP TABLE open_data.dim_partner_qualification;

--DROP TABLE open_data.dim_partner_qualification;
CREATE TABLE IF NOT EXISTS open_data.dim_partner_qualification
(
	code INTEGER NOT NULL  ENCODE az64
	,description VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (code)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_partner_qualification owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254523 ON open_data.dim_partner_qualification;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254523" AFTER
DELETE
    ON
    dim_partner_qualification
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_company_responsiblequalification_fkey', 'dim_company', 'dim_partner_qualification', 'UNSPECIFIED', 'responsiblequalification', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254524 ON open_data.dim_partner_qualification;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254524" AFTER
UPDATE
    ON
    dim_partner_qualification
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_company_responsiblequalification_fkey', 'dim_company', 'dim_partner_qualification', 'UNSPECIFIED', 'responsiblequalification', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254563 ON open_data.dim_partner_qualification;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254563" AFTER
DELETE
    ON
    dim_partner_qualification
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_partner_partnerqualification_fkey', 'dim_partner', 'dim_partner_qualification', 'UNSPECIFIED', 'partnerqualification', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254564 ON open_data.dim_partner_qualification;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254564" AFTER
UPDATE
    ON
    dim_partner_qualification
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_partner_partnerqualification_fkey', 'dim_partner', 'dim_partner_qualification', 'UNSPECIFIED', 'partnerqualification', 'code');

-- Permissions;


-- open_data.dim_company definition

-- Drop table

-- DROP TABLE open_data.dim_company;

--DROP TABLE open_data.dim_company;
CREATE TABLE IF NOT EXISTS open_data.dim_company
(
	basiccnpj VARCHAR(9) NOT NULL  ENCODE lzo
	,legalname VARCHAR(300)   ENCODE lzo
	,legalnature INTEGER   ENCODE az64
	,responsiblequalification SMALLINT   ENCODE az64
	,companycapital NUMERIC(14,2)   ENCODE az64
	,companysize SMALLINT   ENCODE az64
	,federatedentityresponsible VARCHAR(300)   ENCODE lzo
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_company owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254518 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254518" AFTER
INSERT
    OR
UPDATE
    ON
    dim_company
FROM
    dim_legal_nature NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_company_legalnature_fkey', 'dim_company', 'dim_legal_nature', 'UNSPECIFIED', 'legalnature', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254522 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254522" AFTER
INSERT
    OR
UPDATE
    ON
    dim_company
FROM
    dim_partner_qualification NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_company_responsiblequalification_fkey', 'dim_company', 'dim_partner_qualification', 'UNSPECIFIED', 'responsiblequalification', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254531 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254531" AFTER
DELETE
    ON
    dim_company
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('fact_establishment_basiccnpj_fkey', 'fact_establishment', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254532 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254532" AFTER
UPDATE
    ON
    dim_company
FROM
    fact_establishment NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('fact_establishment_basiccnpj_fkey', 'fact_establishment', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254551 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254551" AFTER
DELETE
    ON
    dim_company
FROM
    dim_simple_national NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_simple_national_basiccnpj_fkey', 'dim_simple_national', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254552 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254552" AFTER
UPDATE
    ON
    dim_company
FROM
    dim_simple_national NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_simple_national_basiccnpj_fkey', 'dim_simple_national', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254559 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254559" AFTER
DELETE
    ON
    dim_company
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_del"('dim_partner_basiccnpj_fkey', 'dim_partner', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254560 ON open_data.dim_company;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254560" AFTER
UPDATE
    ON
    dim_company
FROM
    dim_partner NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_noaction_upd"('dim_partner_basiccnpj_fkey', 'dim_partner', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');

-- Permissions;


-- open_data.dim_partner definition

-- Drop table

-- DROP TABLE open_data.dim_partner;

--DROP TABLE open_data.dim_partner;
CREATE TABLE IF NOT EXISTS open_data.dim_partner
(
	basiccnpj VARCHAR(9) NOT NULL  ENCODE lzo
	,partnertype SMALLINT   ENCODE az64
	,partnername VARCHAR(300)   ENCODE lzo
	,partnerdocument VARCHAR(20) NOT NULL  ENCODE lzo
	,partnerqualification INTEGER   ENCODE az64
	,partnerstartdate DATE   ENCODE az64
	,country INTEGER   ENCODE az64
	,legalrepresentative VARCHAR(300)   ENCODE lzo
	,representativename VARCHAR(300)   ENCODE lzo
	,representativequalification INTEGER   ENCODE az64
	,agerange SMALLINT   ENCODE az64
	,PRIMARY KEY (basiccnpj, partnerdocument)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_partner owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254558 ON open_data.dim_partner;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254558" AFTER
INSERT
    OR
UPDATE
    ON
    dim_partner
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_partner_basiccnpj_fkey', 'dim_partner', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254562 ON open_data.dim_partner;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254562" AFTER
INSERT
    OR
UPDATE
    ON
    dim_partner
FROM
    dim_partner_qualification NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_partner_partnerqualification_fkey', 'dim_partner', 'dim_partner_qualification', 'UNSPECIFIED', 'partnerqualification', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254566 ON open_data.dim_partner;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254566" AFTER
INSERT
    OR
UPDATE
    ON
    dim_partner
FROM
    dim_country_code NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_partner_country_fkey', 'dim_partner', 'dim_country_code', 'UNSPECIFIED', 'country', 'code');

-- Permissions;


-- open_data.dim_simple_national definition

-- Drop table

-- DROP TABLE open_data.dim_simple_national;

--DROP TABLE open_data.dim_simple_national;
CREATE TABLE IF NOT EXISTS open_data.dim_simple_national
(
	basiccnpj VARCHAR(9) NOT NULL  ENCODE lzo
	,issimple BOOLEAN   ENCODE RAW
	,simpleoptiondate DATE   ENCODE az64
	,simpleexclusiondate DATE   ENCODE az64
	,ismei BOOLEAN   ENCODE RAW
	,meioptiondate DATE   ENCODE az64
	,meiexclusiondate DATE   ENCODE az64
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.dim_simple_national owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254550 ON open_data.dim_simple_national;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254550" AFTER
INSERT
    OR
UPDATE
    ON
    dim_simple_national
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('dim_simple_national_basiccnpj_fkey', 'dim_simple_national', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');

-- Permissions;


-- open_data.fact_establishment definition

-- Drop table

-- DROP TABLE open_data.fact_establishment;

--DROP TABLE open_data.fact_establishment;
CREATE TABLE IF NOT EXISTS open_data.fact_establishment
(
	basiccnpj VARCHAR(9) NOT NULL  ENCODE lzo
	,cnpjorder VARCHAR(5)   ENCODE lzo
	,cnpjcheckingdigit VARCHAR(3)   ENCODE lzo
	,matrixbranch SMALLINT   ENCODE az64
	,fantasyname VARCHAR(300)   ENCODE lzo
	,registrationstatus SMALLINT   ENCODE az64
	,registrationstatusdate DATE   ENCODE az64
	,registrationstatusreason INTEGER   ENCODE az64
	,cityabroadname VARCHAR(300)   ENCODE lzo
	,countrycode INTEGER   ENCODE az64
	,activitystartdate DATE   ENCODE az64
	,maincnae INTEGER NOT NULL  ENCODE az64
	,secondarycnae VARCHAR(65535)   ENCODE lzo
	,addresstype VARCHAR(300)   ENCODE lzo
	,address VARCHAR(300)   ENCODE lzo
	,addressnumber VARCHAR(50)   ENCODE lzo
	,addresscomplement VARCHAR(300)   ENCODE lzo
	,addressdistrict VARCHAR(300)   ENCODE lzo
	,zipcode VARCHAR(50)   ENCODE lzo
	,state VARCHAR(3)   ENCODE lzo
	,citycode INTEGER   ENCODE az64
	,telephone1areacode VARCHAR(5)   ENCODE lzo
	,telephone1 VARCHAR(20)   ENCODE lzo
	,telephone2areacode VARCHAR(5)   ENCODE lzo
	,telephone2 VARCHAR(20)   ENCODE lzo
	,faxareacode VARCHAR(5)   ENCODE lzo
	,faxnumber VARCHAR(20)   ENCODE lzo
	,taxpayeremail VARCHAR(300)   ENCODE lzo
	,specialsituation VARCHAR(300)   ENCODE lzo
	,specialsituationdate DATE   ENCODE az64
	,PRIMARY KEY (basiccnpj)
)
DISTSTYLE AUTO
;
ALTER TABLE open_data.fact_establishment owner to opendata_airflow;

-- Table Triggers

-- DROP TRIGGER RI_ConstraintTrigger_254530 ON open_data.fact_establishment;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254530" AFTER
INSERT
    OR
UPDATE
    ON
    fact_establishment
FROM
    dim_company NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('fact_establishment_basiccnpj_fkey', 'fact_establishment', 'dim_company', 'UNSPECIFIED', 'basiccnpj', 'basiccnpj');
-- DROP TRIGGER RI_ConstraintTrigger_254534 ON open_data.fact_establishment;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254534" AFTER
INSERT
    OR
UPDATE
    ON
    fact_establishment
FROM
    dim_country_code NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('fact_establishment_countrycode_fkey', 'fact_establishment', 'dim_country_code', 'UNSPECIFIED', 'countrycode', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254538 ON open_data.fact_establishment;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254538" AFTER
INSERT
    OR
UPDATE
    ON
    fact_establishment
FROM
    dim_cnae NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('fact_establishment_maincnae_fkey', 'fact_establishment', 'dim_cnae', 'UNSPECIFIED', 'maincnae', 'code');
-- DROP TRIGGER RI_ConstraintTrigger_254542 ON open_data.fact_establishment;

CREATE CONSTRAINT TRIGGER "RI_ConstraintTrigger_254542" AFTER
INSERT
    OR
UPDATE
    ON
    fact_establishment
FROM
    dim_city_code NOT DEFERRABLE INITIALLY IMMEDIATE FOR EACH ROW EXECUTE PROCEDURE "RI_FKey_check_ins"('fact_establishment_citycode_fkey', 'fact_establishment', 'dim_city_code', 'UNSPECIFIED', 'citycode', 'code');

-- Permissions;