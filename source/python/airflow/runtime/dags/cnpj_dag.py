from datetime import datetime, timedelta
import os
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from helpers.redshift_helper import get_orc_copy_command
from helpers.cnpj_queries import CnpjSqlQueries
from operators.redshift_load import RedshiftLoadOperator
from operators.redshift_run import RedshiftRunOperator

# from operators.stage_redshift import StageToRedshiftOperator
# from operators.data_quality import DataQualityOperator

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
          description="Copy cnpj data from ORC files in s3 to redshift.",
          # schedule_interval="0 * * * *",
          schedule_interval=None,
          catchup=False
        )

start_operator = DummyOperator(task_id="Begin_execution",  dag=dag)
end_operator = DummyOperator(task_id="Stop_execution",  dag=dag)


create_stage_tables = RedshiftRunOperator(task_id="create_stage_tables", sql=CnpjSqlQueries.drop_create_stage_tables, dag=dag)
drop_stage_tables = RedshiftRunOperator(task_id="drop_stage_tables", sql=CnpjSqlQueries.drop_stage_tables, dag=dag)

# copy form s3 to stage table and then to target table
# stage_table_name, source, target_table, load_command, iam_role="{{var.value.redshift_iam_role}}", db_api_hook=PostgresHook("redshift")
load_partner_table = RedshiftLoadOperator(
  task_id="load_dim_partner", 
  stage_table_name = "open_data.stage_dim_partner",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/partner/part-", 
  target_table = "open_data.dim_partner",
  dag=dag)

load_simple_national_table = RedshiftLoadOperator(
  task_id="load_dim_simple_national", 
  stage_table_name = "open_data.dim_simple_national",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/simple_national/part-", 
  target_table = "open_data.dim_simple_national",
  dag=dag)  

load_company_table = RedshiftLoadOperator(
  task_id="load_dim_company", 
  stage_table_name = "open_data.dim_company",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/company/part-", 
  target_table = "open_data.dim_company",
  dag=dag)   

load_fact_establishment_table = RedshiftLoadOperator(
  task_id="load_fact_establishment", 
  stage_table_name = "open_data.fact_establishment",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/establishment/part-", 
  target_table = "open_data.fact_establishment",
  dag=dag)   

start_operator >> create_stage_tables >> [load_partner_table, load_simple_national_table, load_company_table, load_fact_establishment_table] >> drop_stage_tables >> end_operator
