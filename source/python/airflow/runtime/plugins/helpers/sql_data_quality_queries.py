class SqlDataQualityQueries:
    sample_check = ("""
    -- Make sure all song is unique on dim_song table
    -- Result expected: none song is duplicate on dim_song table
    SELECT song_id, COUNT(0) AS total_duplicated FROM dim_song group by song_id HAVING COUNT(0) > 1 LIMIT 1
    ;
    """, "== 0", "")