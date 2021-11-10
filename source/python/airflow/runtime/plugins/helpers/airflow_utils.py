# Airflow utils

from airflow import DAG
from airflow.executors.debug_executor import DebugExecutor

def create_airflow_fernet_env_key():
    """
    Create one string to be used with bash export or docker-compose.yml containing a fernet key.
    E.G.: create_airflow_fernet_env_key() => AIRFLOW__CORE__FERNET_KEY=G_F-iZ8J4zLE0nJ58DVplD0sma6m44ioTL8wWc3BU_k=
    """
    # seealso: https://pypi.org/project/cryptography/
    # seealso: https://stackoverflow.com/questions/53897333/read-fernet-key-causes-valueerror-fernet-key-must-be-32-url-safe-base64-encoded
    from cryptography.fernet import Fernet

    key = Fernet.generate_key()
    result = "AIRFLOW__CORE__FERNET_KEY=" + key.decode("utf-8")
    print(result)
    return result

def debug_dag(dag: DAG, use_default_executor=False):
    """Debug a DAG in VSCode with DebugExecutor not needing to change airflow.cfg."""
    # Caution: remove missing tasks manually in website
    dag.clear()
    if use_default_executor:
        dag.run()
    else:
        dag.run(executor=DebugExecutor())


def check_s3_access_key(bucket="your_s3_bucket_name", prefix="your_s3_folder"):
    import boto3
    s3 = boto3.client("s3")
    response = s3.list_objects_v2(Bucket=bucket, MaxKeys=1, Prefix=prefix)
    print("s3 ls response: ", response)
    if "ResponseMetadata" in response and "HTTPStatusCode" in response["ResponseMetadata"] and response["ResponseMetadata"]["HTTPStatusCode"] == 200:
        return True
    else:
        return False
    
def upload_file_to_s3(file_path, bucket, destination_key):
    import boto3
    s3 = boto3.resource('s3')
    s3.meta.client.upload_file(Filename=file_path, Bucket=bucket, Key=destination_key)


