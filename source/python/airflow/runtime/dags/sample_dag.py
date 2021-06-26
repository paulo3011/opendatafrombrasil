from datetime import datetime, timedelta
import os
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from sample_operators.hello_operator import HelloOperator
# from operators.stage_redshift import StageToRedshiftOperator
# from operators.data_quality import DataQualityOperator
# from helpers.sql_queries import SqlQueries
# from helpers.sql_data_quality_queries import SqlDataQualityQueries


# AWS_KEY = os.environ.get("AWS_KEY")
# AWS_SECRET = os.environ.get("AWS_SECRET")

# [ok] https://classroom.udacity.com/nanodegrees/nd027/parts/45d1c3b1-d87b-4578-a6d0-7e86bb5fea6c/modules/57c3b9d1-4d8b-4afe-bfb4-92cfac622c7f/lessons/4d1d5892-2cab-4456-8b1a-fb2b5fa1488d/concepts/3b78f18c-3a53-40ab-8300-b1fe5208de97

default_args = {
    "owner": "paulo_moreira",
    "start_date": datetime(2018, 11, 1),
    # "end_date": datetime(2018, 11, 30),
    "depends_on_past": False,
    "retries": 3,
    "retry_delay": timedelta(minutes=5),
    "email_on_retry": False
}

dag = DAG("sample_dag",
          default_args=default_args,
          description="Sample dag.",
          # schedule_interval="0 * * * *",
          schedule_interval=None,
          catchup=False
        )

start_operator = DummyOperator(task_id="Begin_execution",  dag=dag)
hello_operator = HelloOperator(task_id="hello_operator", name="my custom sample operator name", dag=dag)
end_operator = DummyOperator(task_id="Stop_execution",  dag=dag)

start_operator >> hello_operator >> end_operator
