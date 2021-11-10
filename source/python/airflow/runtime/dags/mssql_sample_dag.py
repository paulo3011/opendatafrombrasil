from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.dummy import DummyOperator
from airflow.operators.python_operator import PythonOperator
from airflow.operators.mssql_operator import MsSqlOperator
from airflow.providers.microsoft.mssql.hooks.mssql import MsSqlHook
from helpers.airflow_utils import check_s3_access_key, upload_file_to_s3
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

    upload_file_to_s3(file_path=destination, bucket="your-bucket-name", destination_key="tmp/my_records.csv")

    # or save on s3:
    # https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.to_csv.html?highlight=to_csv
    # https://stackoverflow.com/questions/38154040/save-dataframe-to-csv-directly-to-s3-python
    # df.to_csv("s3://your-bucket/tmp/my_records.csv")
    # print("The file was saved to s3")

run_etl_with_pandas = PythonOperator(
    task_id='run_etl_with_pandas',
    python_callable=mssql_etl_func,
    dag=dag
)

def check_s3_connection_func():
    #check_s3_access_key(bucket="put_your_bucket_name_here", prefix="put_your_folder_name_here")


check_s3_connection = PythonOperator(
    task_id="check_s3_connection",
    python_callable=check_s3_connection_func,
    dag=dag
)

"""
run_etl_with_petl = PetlOperator(
    task_id="run_etl_with_petl",
    source_conn="mssql_conn",
    source="select 1 as id",
    dest_conn="s3_dev",
    #dest_conn=Connection(host='/tmp/', conn_type='fs'),    
    destination="tmp/output.avro",
    dag=dag,
    debug=True
)
"""

start_operator >> conn_test >> check_s3_connection >> run_etl_with_pandas

if __name__ == "__main__":
    from helpers.airflow_utils import debug_dag

    debug_dag(dag)