from datetime import datetime, timedelta
import os
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from sample_operators.hello_operator import HelloOperator

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
