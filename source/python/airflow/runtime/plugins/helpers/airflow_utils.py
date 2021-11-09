# Airflow utils

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

