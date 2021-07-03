class CnpjSqlQueries:
    sample_table_insert = ("""
    DELETE
    FROM
        fact_songplays
    USING dim_time
    WHERE
        dim_time.start_time = fact_songplays.start_time
        AND dim_time.month = {{execution_date.strftime("%m")}}
        AND dim_time.year = {{execution_date.strftime("%Y")}}
    """, """
    INSERT
        INTO
        fact_songplays ( start_time, user_id, level, song_id, artist_id, session_id, location, user_agent, start_date)
    SELECT
        events.ts as start_time,
        events.userid,
        events.level,
        songs.song_id,
        songs.artist_id,
        events.sessionid,
        events.location,
        events.useragent,
        events.start_date
    FROM
        (
        SELECT
            TIMESTAMP 'epoch' + ts / 1000 * interval '1 second' AS start_date, *
        FROM
            stage_events
        WHERE
            page = 'NextSong') events
    LEFT JOIN stage_songs songs ON
        events.song = songs.title
        AND events.artist = songs.artist_name
        AND events.length = songs.duration;
    """)

    drop_stage_tables = ("""
    SET search_path TO open_data;
    DROP TABLE IF EXISTS stage_dim_city_code;
    DROP TABLE IF EXISTS stage_dim_cnae;
    DROP TABLE IF EXISTS stage_dim_company;
    DROP TABLE IF EXISTS stage_dim_country_code;
    DROP TABLE IF EXISTS stage_dim_legal_nature;
    DROP TABLE IF EXISTS stage_dim_partner;
    DROP TABLE IF EXISTS stage_dim_partner_qualification;
    DROP TABLE IF EXISTS stage_dim_simple_national;
    DROP TABLE IF EXISTS stage_fact_establishment;
    """)

    create_stage_tables = ("""
    SET search_path TO open_data;
    CREATE TABLE stage_dim_city_code AS SELECT * FROM dim_city_code WHERE 1 = 0;
    CREATE TABLE stage_dim_cnae AS SELECT * FROM dim_cnae WHERE 1 = 0;
    CREATE TABLE stage_dim_company AS SELECT * FROM dim_company WHERE 1 = 0;
    CREATE TABLE stage_dim_country_code AS SELECT * FROM dim_country_code WHERE 1 = 0;
    CREATE TABLE stage_dim_legal_nature AS SELECT * FROM dim_legal_nature WHERE 1 = 0;
    CREATE TABLE stage_dim_partner AS SELECT * FROM dim_partner WHERE 1 = 0;
    CREATE TABLE stage_dim_partner_qualification AS SELECT * FROM dim_partner_qualification WHERE 1 = 0;
    CREATE TABLE stage_dim_simple_national AS SELECT * FROM dim_simple_national WHERE 1 = 0;
    CREATE TABLE stage_fact_establishment AS SELECT * FROM fact_establishment WHERE 1 = 0;    
    """)

    drop_create_stage_tables = drop_stage_tables + create_stage_tables
   
