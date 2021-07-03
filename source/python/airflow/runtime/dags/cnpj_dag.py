from datetime import datetime, timedelta
import os
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from helpers.redshift_helper import create_intersect_total_test 
from helpers.sql_data_quality_queries import SqlDataQualityQueries
from helpers.cnpj_queries import CnpjSqlQueries
from operators.redshift_load import RedshiftLoadOperator
from operators.redshift_run import RedshiftRunOperator
from operators.data_quality import DataQualityOperator

# from helpers.sql_data_quality_queries import SqlDataQualityQueries

default_args = {
    "owner": "paulo_moreira",
    "start_date": datetime(2018, 11, 1),
    # "end_date": datetime(2018, 11, 30),
    "depends_on_past": False,
    "retries": 3,
    "retry_delay": timedelta(minutes=5),
    "email_on_retry": False
}

dag = DAG("cnpj_etl",
          default_args=default_args,
          description="Copy cnpj data from s3 ORC files to redshift.",
          # schedule_interval="0 * * * *",
          schedule_interval=None,
          catchup=False
        )

start_operator = DummyOperator(task_id="Begin_execution",  dag=dag)
end_operator = DummyOperator(task_id="Stop_execution",  dag=dag)


create_stage_tables = RedshiftRunOperator(task_id="create_stage_tables", sql=CnpjSqlQueries.drop_create_stage_tables, dag=dag)
drop_stage_tables = RedshiftRunOperator(task_id="drop_stage_tables", sql=CnpjSqlQueries.drop_stage_tables, dag=dag)

# copy from s3 to stage table and then to target table
# stage_table_name, source, target_table, load_command, iam_role="{{var.value.redshift_iam_role}}", db_api_hook=PostgresHook("redshift")
load_partner_table = RedshiftLoadOperator(
  task_id="load_dim_partner", 
  stage_table_name = "open_data.stage_dim_partner",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/partner/part-", 
  target_table = "open_data.dim_partner",
  dag=dag)

load_simple_national_table = RedshiftLoadOperator(
  task_id="load_dim_simple_national", 
  stage_table_name = "open_data.stage_dim_simple_national",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/simple_national/part-", 
  target_table = "open_data.dim_simple_national",
  dag=dag)  

load_company_table = RedshiftLoadOperator(
  task_id="load_dim_company", 
  stage_table_name = "open_data.stage_dim_company",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/company/part-", 
  target_table = "open_data.dim_company",
  dag=dag)   

load_fact_establishment_table = RedshiftLoadOperator(
  task_id="load_fact_establishment", 
  stage_table_name = "open_data.stage_fact_establishment",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/establishment/part-", 
  target_table = "open_data.fact_establishment",
  dag=dag)   

load_dim_city_code_table = RedshiftLoadOperator(
  task_id="load_dim_city_code", 
  stage_table_name = "open_data.stage_dim_city_code",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/city_code/part-", 
  target_table = "open_data.dim_city_code",
  dag=dag)   

load_dim_cnae_table = RedshiftLoadOperator(
  task_id="load_dim_cnae", 
  stage_table_name = "open_data.stage_dim_cnae",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/cnae_code/part-", 
  target_table = "open_data.dim_cnae",
  dag=dag)   

load_dim_country_code = RedshiftLoadOperator(
  task_id="load_dim_country_code", 
  stage_table_name = "open_data.stage_dim_country_code",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/country_code/part-", 
  target_table = "open_data.dim_country_code",
  dag=dag)    

load_dim_legal_nature = RedshiftLoadOperator(
  task_id="load_dim_legal_nature", 
  stage_table_name = "open_data.stage_dim_legal_nature",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/legal_nature_code/part-", 
  target_table = "open_data.dim_legal_nature",
  dag=dag)    

load_dim_partner_qualification = RedshiftLoadOperator(
  task_id="load_dim_partner_qualification", 
  stage_table_name = "open_data.stage_dim_partner_qualification",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/partner_qualification_code/part-", 
  target_table = "open_data.dim_partner_qualification",
  dag=dag)     


tests = [    
    create_intersect_total_test("open_data.stage_dim_partner","open_data.dim_partner"),
    create_intersect_total_test("open_data.stage_dim_simple_national","open_data.dim_simple_national"),
    create_intersect_total_test("open_data.stage_dim_company","open_data.dim_company"),
    create_intersect_total_test("open_data.stage_fact_establishment","open_data.fact_establishment"),
    create_intersect_total_test("open_data.stage_dim_city_code","open_data.dim_city_code"),
    create_intersect_total_test("open_data.stage_dim_cnae","open_data.dim_cnae"),
    create_intersect_total_test("open_data.stage_dim_country_code","open_data.dim_country_code"),
    create_intersect_total_test("open_data.stage_dim_legal_nature","open_data.dim_legal_nature"),
    create_intersect_total_test("open_data.stage_dim_partner_qualification","open_data.dim_partner_qualification"),
    # the cnpj database is not healthy
    SqlDataQualityQueries.establisment_company_relation_check
    ]

run_quality_checks = DataQualityOperator(
    task_id="run_data_quality_checks",
    dag=dag,
    tests=tests
)  

# dim_tasks = [load_dim_partner_qualification, load_dim_legal_nature, load_dim_country_code, load_dim_city_code_table, load_dim_cnae_table]
# facts_tasks = [load_partner_table, load_simple_national_table, load_company_table, load_fact_establishment_table]

start_operator >> create_stage_tables >> [load_dim_partner_qualification, load_dim_legal_nature, load_dim_country_code, load_dim_city_code_table, load_dim_cnae_table, load_partner_table, load_simple_national_table, load_company_table, load_fact_establishment_table] >> run_quality_checks >> drop_stage_tables >> end_operator
