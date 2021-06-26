from airflow.providers.postgres.hooks.postgres import PostgresHook
from airflow.models.baseoperator import BaseOperator
from airflow.utils.decorators import apply_defaults
from helpers.redshift_helper import get_orc_copy_command

class RedshiftLoadOperator(BaseOperator):

    ui_color = '#F98866'
    template_fields = ["iam_role"]

    @apply_defaults
    def __init__(self,
                 stage_table_name,
                 source,                 
                 target_table,
                 load_command,
                 iam_role="{{var.value.redshift_iam_role}}",
                 db_api_hook=PostgresHook("redshift"),
                 *args, **kwargs):

        super(RedshiftLoadOperator, self).__init__(*args, **kwargs)
        # Map params here
        self.stage_table_name = stage_table_name
        self.source = source
        self.iam_role = iam_role
        self.target_table = target_table
        self.load_command = load_command
        self.db_api_hook = db_api_hook

    def execute(self, context):
        self.log.info("Starting RedshiftLoadOperator")        

        copy_cmd = get_orc_copy_command(
            stage_table_name = self.stage_table_name,
            source = self.source, 
            iam_role = self.iam_role, 
            target_table = self.target_table
        )

        self.log.info("command %s", copy_cmd)

        self.db_api_hook.run(copy_cmd)

        self.log.info("load command %s", self.load_command)

        self.db_api_hook.run(self.load_command)               
