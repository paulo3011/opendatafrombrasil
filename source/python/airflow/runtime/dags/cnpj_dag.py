from datetime import datetime, timedelta
import os
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from helpers.redshift_helper import get_orc_copy_command
from helpers.cnpj_queries import CnpjSqlQueries
from operators.redshift_load import RedshiftLoadOperator

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


create_stage_tables = RedshiftLoadOperator(task_id="create_stage_tables", sql=CnpjSqlQueries.drop_create_stage_tables, dag=dag)
drop_stage_tables = RedshiftLoadOperator(task_id="drop_stage_tables", sql=CnpjSqlQueries.drop_stage_tables, dag=dag)

# get_orc_copy_command(stage_table_name, source, iam_role="{{var.value.redshift_iam_role}}", target_table=None, truncate_table=True):

partner_copy_cmd = get_orc_copy_command(
  stage_table_name = "open_data.stage_dim_partner",
  source = "s3://moreira-ud/stage/cnpj/teste/2021-06-19/partner/part-", 
  iam_role = "arn:aws:iam::852046719065:role/myRedshiftRole", 
  target_table = "open_data.dim_partner"
  )

copy_from_s3_to_redshit_stage = RedshiftLoadOperator(task_id="s3_partner_to_redshift_stage", sql=partner_copy_cmd, dag=dag)

load_partner_table = RedshiftLoadOperator(task_id="load_partner_table", sql=CnpjSqlQueries.load_partner, dag=dag)

start_operator >> create_stage_tables >> copy_from_s3_to_redshit_stage >> load_partner_table >> drop_stage_tables >> end_operator
