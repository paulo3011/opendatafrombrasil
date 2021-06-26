from airflow.providers.postgres.hooks.postgres import PostgresHook
from airflow.models.baseoperator import BaseOperator
from airflow.utils.decorators import apply_defaults

class RedshiftRunOperator(BaseOperator):

    ui_color = '#F98866'
    template_fields = ["sql"]

    @apply_defaults
    def __init__(self,
                 sql,
                 db_api_hook=PostgresHook("redshift"),
                 *args, **kwargs):

        super(RedshiftRunOperator, self).__init__(*args, **kwargs)
        # Map params here
        self.sql = sql
        self.db_api_hook = db_api_hook

    def execute(self, context):
        self.log.info('Starting RedshiftRunOperator')
        self.db_api_hook.run(self.sql)
