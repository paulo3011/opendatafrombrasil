-- select open_data as default schema 
SET search_path TO open_data;

-- create airflow open data user
CREATE USER opendata_airflow WITH password 'YourPassWordHere';
CREATE GROUP etl_group WITH USER opendata_airflow;
GRANT USAGE ON SCHEMA open_data TO GROUP etl_group;
GRANT CREATE ON SCHEMA open_data TO GROUP etl_group;
GRANT SELECT, INSERT, UPDATE, DELETE,  REFERENCES ON ALL TABLES IN SCHEMA open_data TO GROUP etl_group;