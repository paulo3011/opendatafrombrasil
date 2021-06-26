from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from operators.data_quality import DataQualityOperator

default_args = {
    "owner": "paulo_moreira",
    "start_date": datetime(2018, 11, 1),
    # "end_date": datetime(2018, 11, 30),
    "depends_on_past": False,
    "retries": 3,
    "retry_delay": timedelta(minutes=1),
    "email_on_retry": False
}

dag = DAG("check_connections",
          default_args=default_args,
          description="Dag to test externals connections.",
          # schedule_interval="0 * * * *",
          schedule_interval=None,
          catchup=False
        )

start_operator = DummyOperator(task_id="Begin_execution",  dag=dag)

# use the data quality operator to check if connection will work
# if execute the sql query then the connection was made successfully
tests = [("SET search_path TO open_data; select count(0) from dim_company;",">= 0","")]

run_quality_checks = DataQualityOperator(
    task_id="run_connections_test",
    dag=dag,
    tests=tests
)

end_operator = DummyOperator(task_id="Stop_execution",  dag=dag)

start_operator >> run_quality_checks >> end_operator
