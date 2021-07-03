
def _get_json_copy_command(target_table, source, iam_role, jsonpaths=None, region="us-east-1", truncate_table=True):
        """
        Notes:
        Loss of numeric precision: You might lose precision when loading numbers
        from data files in JSON format to a column that is defined as a numeric
        data type. Some floating point values aren't represented exactly in
        computer systems. As a result, data you copy from a JSON file might
        not be rounded as you expect. To avoid a loss of precision seealso:
        https://docs.aws.amazon.com/redshift/latest/dg/copy-usage_notes-copy-from-json.html
        """
        _jsonpaths = "auto ignorecase"
        _region = ""
        _truncate_command = ""
        if jsonpaths is not None:
            _jsonpaths = jsonpaths
        if region is not None:
            _region = f"region '{region}'"
        if truncate_table:
            _truncate_command = "TRUNCATE TABLE {}; ".format(target_table)

        copy_command = ("""
        COPY {} FROM '{}'
        IAM_ROLE '{}'
        JSON '{}'
        {};
        """).format(
            target_table,
            source,
            iam_role,
            _jsonpaths,
            _region
            )

        if truncate_table:
            return (_truncate_command, copy_command)

        return copy_command

def get_orc_copy_command(stage_table_name, source, iam_role="{{var.value.redshift_iam_role}}", target_table=None):
        """
        Create an ORC COPY command for the parameters.
        Notes:
        - The Amazon S3 bucket must be in the same AWS Region as the Amazon Redshift cluster.
        seealso: https://docs.aws.amazon.com/redshift/latest/dg/copy-usage_notes-copy-from-columnar.html
        """

        #drop_command = "DROP TABLE IF EXISTS {}; ".format(stage_table_name)
        #commands = ("SET search_path TO open_data;", drop_command,)
        commands = ()

        # if target_table is not None:
        #    commands = commands + ( _create_stage_table_command(table_name=stage_table_name, target_table=target_table) , )

        """
        --sample
        COPY dim_company
        FROM 's3://s3path/stage/cnpj/2021-06/company/part-'
        iam_role 'arn:aws:iam::[id]:role/myRedshiftRole'
        FORMAT AS ORC
        ;
        """

        copy_command = ("""COPY {} FROM '{}' IAM_ROLE '{}' FORMAT AS ORC;""").format(
            stage_table_name,
            source,
            iam_role
            )

        commands = commands + (copy_command,)

        return commands

def _create_default_load_command(stage_table, target_table)            :
    return ("TRUNCATE TABLE {}".format(target_table), "INSERT INTO {} SELECT * FROM {}".format(target_table, stage_table))

def _create_stage_table_command(table_name, target_table, selected_fields="*"):
        """
        Create an redshift create temporary table command.
        Notes:
        - CREATE TABLE AS command does not inherits "NOT NULL"
        - CREATE TABLE LIKE statement also inherits sort key, distribution key. But the main point to to note here that, 
        CREATE TABLE LIKE command additionally inherits "NOT NULL" settings from the source table that CREATE TABLE AS does not
        seealso: https://docs.aws.amazon.com/pt_br/redshift/latest/dg/r_CREATE_TABLE_AS.html
        seealso: https://docs.aws.amazon.com/pt_br/redshift/latest/dg/r_CREATE_TABLE_AS.html
        """    
        copy_command = ("""CREATE TEMPORARY TABLE {} AS SELECT {} FROM {} WHERE 1 = 0;""").format(
            table_name,
            selected_fields,
            target_table
            )    

        return copy_command

def create_intersect_total_test(first_table, second_table):
    """
    Create a data quality test for redshift who checks if the total of register in
    the first table is the same as the second table.
    The result must be zero (if both tables are empty) or greater than zero (if not empty).
    If it return None, something is wrong.

    :param first_table: the first table name.
    :type first_table: str    

    :param second_table: the second table name.
    :type second_table: str      
    """
    return ("""SELECT * FROM
    (
	    SELECT count(0) as total_records FROM {}
	    INTERSECT	
	    SELECT count(0) as total_records FROM {}
    );""".format(first_table, second_table), "== 1", "> 0") 
