from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from airflow.operators.python_operator import PythonOperator
from airflow.operators.mssql_operator import MsSqlOperator
from airflow.providers.microsoft.mssql.hooks.mssql import MsSqlHook
# from operators.petl_plugin import PetlOperator
from airflow.models.connection import Connection

default_args = {
    "owner": "paulo_moreira",
    "start_date": datetime(2018, 11, 1),
    # "end_date": datetime(2018, 11, 30),
    "depends_on_past": False,
    "retries": 0,
    "retry_delay": timedelta(minutes=1),
    "email_on_retry": False
}

dag = DAG("mssql_sample",
          default_args=default_args,
          description="Sample demonstration for mssql operator.",
          schedule_interval=None,
          catchup=False
        )

sample_sql = "select 1 as id, 'paulo' as name union select 2 as id, 'jose' as name"        

start_operator = DummyOperator(task_id="Begin_execution",  dag=dag)

conn_test = MsSqlOperator(task_id="conn_test", sql="select 1 as con_ok", mssql_conn_id="mssql_conn", dag=dag)

def mssql_etl_func(**kwargs):
    hook = MsSqlHook(mssql_conn_id="mssql_conn")
    df = hook.get_pandas_df(sql=sample_sql)
    
    #do whatever you need on the df

    #e.g. save on tmp disk
    destination = "/tmp/my_records.csv"
    df.to_csv(destination)
    # df.to_parquet(destination)
    print("The file was saved to disk (local tmp dir)")
    
    # checking saved file
    file = open(destination, mode='r')
    all_lines = file.read()
    file.close()
    print("File lines: ", all_lines)

    # or save on s3:
    # https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.to_csv.html?highlight=to_csv
    # https://stackoverflow.com/questions/38154040/save-dataframe-to-csv-directly-to-s3-python
    # df.to_csv("s3://buckect/key/my_records.csv")

run_etl_with_pandas = PythonOperator(
    task_id='run_etl_with_pandas',
    python_callable=mssql_etl_func,
    dag=dag
)

"""
run_etl_with_petl = PetlOperator(
    task_id="run_etl_with_petl",
    source_conn="mssql_conn",
    source="select 1 as id",
    #dest_conn="s3_default",
    dest_conn=Connection(host='/tmp/', conn_type='fs'),
    destination="output.avro",
    dag=dag,
    debug=True
)
"""

# start_operator >> conn_test >> [run_etl_with_pandas, run_etl_with_petl]

start_operator >> conn_test >> run_etl_with_pandas