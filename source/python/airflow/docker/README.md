# Building the airflow custom image

1. Clone the github official repository

```shell
$ # enter to your preferred path
$ cd ~/projects/external
$ # clone the repo
$ git clone https://github.com/apache/airflow.git
```

2. Checkout to the stable version that you want

```shell
$ git checkout v2-0-stable
```

3. Enter on airflow folder

```shell
$ cd ~/projects/external/airflow
```

4. Check if is up to date

```shell
$ git status
# The result needs to be:
# On branch v2-0-stable
# Your branch is up to date with 'origin/v2-0-stable'.
```

5. Build your build command with your customizations like:

seealso: https://airflow.apache.org/docs/docker-stack/build-arg-ref.html

```shell
docker build . \
  --build-arg PYTHON_BASE_IMAGE="python:3.8-slim-buster" \
  --build-arg AIRFLOW_PIP_VERSION=21.3.1 \
  --build-arg PYTHON_MAJOR_MINOR_VERSION=3.8 \
  --build-arg AIRFLOW_INSTALLATION_METHOD="apache-airflow" \
  --build-arg AIRFLOW_VERSION="2.0.1" \
  --build-arg AIRFLOW_INSTALL_VERSION="==2.0.1" \
  --build-arg AIRFLOW_SOURCES_FROM="empty" \
  --build-arg AIRFLOW_SOURCES_TO="/empty" \
  --build-arg INSTALL_MYSQL_CLIENT="false" \
  --build-arg AIRFLOW_EXTRAS="amazon,http,postgres,sqlite,microsoft.mssql,odbc" \
  --build-arg ADDITIONAL_PYTHON_DEPS=" \
        petl \
        fastavro " \
  --tag moreira-opendata-airlfow-v2
```

Notes about configurations used:

- AIRFLOW_VERSION:

    This is airflow version that is put in the label of the image build

- AIRFLOW_INSTALL_VERSION?

    By default latest released version of airflow is installed (when empty) but this value can be overridden and we can install specific version of airflow this way.

    sample: --build-arg AIRFLOW_INSTALL_VERSION="==2.0.1" \

- AIRFLOW_EXTRAS:

    This is where we define additional softwares that needs to be install with airflow.
    Seealso: https://airflow.apache.org/docs/apache-airflow/stable/extra-packages-ref.html#

- AIRFLOW_CONSTRAINTS_REFERENCE

    This configuration is used to set the url:
    https://raw.githubusercontent.com/apache/airflow/constraints-2-0/constraints-3.7.txt

- AIRFLOW_INSTALLATION_METHOD:

    Determines the way airflow is installed. By default we install airflow from PyPI `apache-airflow` package But it also can be `.` from local installation or GitHub URL pointing to specific branch or tag Of Airflow.

    Note That __for local source installation__ you need to have local sources of Airflow checked out together with the Dockerfile and AIRFLOW_SOURCES_FROM and AIRFLOW_SOURCES_TO set to "." and "/opt/airflow" respectively.

- INSTALL_FROM_DOCKER_CONTEXT_FILES:

    We can seet this value to true in case we want to install .whl .tar.gz packages placed in the docker-context-files folder. This can be done for both - additional packages you want to install and for airflow as well (you have to set INSTALL_FROM_PYPI to false in this case)

# Starting docker compose airflow dev env

To start airflow do:

```shell
$ # enter to your project path
$ cd ~/projects/opendatafrombrasil/source/python/airflow/docker
$ # to run in background
$ docker-compose up -d
$ # or to run in current console and see logs on the screen (Useful when the container don't start)
$ docker-compose up
```

To start airflow scheduler do:

```shell
$ # see the airflow container id
$ docker ps
$ # login on airflow instance
$ docker exec -it container_id bash
$ # start airflow scheduler
$ airflow scheduler &
$ # Access on browser http://localhost:8081/
```

# Airflow cli commands

```shell
usage: airflow [-h] GROUP_OR_COMMAND ...

positional arguments:
  GROUP_OR_COMMAND

    Groups:
      celery         Celery components
      config         View configuration
      connections    Manage connections
      dags           Manage DAGs
      db             Database operations
      kubernetes     Tools to help run the KubernetesExecutor
      pools          Manage pools
      providers      Display providers
      roles          Manage roles
      tasks          Manage tasks
      users          Manage users
      variables      Manage variables

    Commands:
      cheat-sheet    Display cheat sheet
      info           Show information about current Airflow and environment
      kerberos       Start a kerberos ticket renewer
      plugins        Dump information about loaded plugins
      rotate-fernet-key
                     Rotate encrypted connection credentials and variables
      scheduler      Start a scheduler instance
      sync-perm      Update permissions for existing roles and DAGs
      version        Show the version
      webserver      Start a Airflow webserver instance

optional arguments:
  -h, --help         show this help message and exit
```

To test load and run the dag file you can use:

```shell
$ # airflow dags test [dag_id] 2021-04-13
$ # sample:
$ airflow dags test dag_id 2021-04-13
```

To check if some dag file is without error you can do:

```shell
$ docker exec -it container_id bash
$ python dags/dag_file.py
```

# Configure redshift connection

1. Create the redshift user:

```sql
-- https://docs.aws.amazon.com/pt_br/redshift/latest/dg/r_CREATE_USER.html
CREATE USER opendata_airflow WITH password 'yourpass';
CREATE GROUP etl_group WITH USER opendata_airflow;
GRANT USAGE ON SCHEMA open_data TO GROUP etl_group;
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON ALL TABLES IN SCHEMA open_data TO GROUP etl_group;
```

2. On create connection page, enter the following values:

- Conn Id: Enter redshift.
- Conn Type: Enter Postgres.
- Host: Enter the endpoint of your Redshift cluster, excluding the port at the end. You can find this by selecting your cluster in the Clusters page of the Amazon Redshift console. __IMPORTANT:__ Make sure to NOT include the port at the end of the Redshift endpoint string.
- Schema: This is the Redshift database you want to connect to.
- Login: Enter the userlogin.
- Password: Enter the password you created when launching your Redshift cluster.
- Port: Enter 5439.
- Extras: {"keepalives_idle": "30", "redshift": "True"}

Sampla values:

```text
- Conn Id: redshift
- Conn Type: Postgres
- Host: redshift-cluster.[yourpath].[region].redshift.amazonaws.com
- Schema: dev
- Login: opendatauser_airflow
- Password: yourpass
- Port: 5439
```

# References

## CI image build arguments

- https://github.com/apache/airflow/blob/v2-0-stable/IMAGES.rst
- https://github.com/apache/airflow/blob/master/IMAGES.rst

## Others

- https://www.youtube.com/watch?v=wDr3Y7q2XoI
- https://airflow.apache.org/docs/apache-airflow/stable/production-deployment.html#production-container-images
- https://airflow.apache.org/docs/apache-airflow/stable/extra-packages-ref.html
- https://github.com/astronomer/airflow-guides/blob/main/guides_in_progress/apache_airflow/best-practices-guide.md
- https://gtoonstra.github.io/etl-with-airflow/
- https://stackoverflow.com/questions/44424473/airflow-structure-organization-of-dags-and-tasks
