COPY stage_establishment
FROM 's3://moreira-ud/stage/cnpj/orc/part-'
iam_role 'arn:aws:iam::accounid:role/rolename'
FORMAT AS ORC
;

-- 5304980 (1 file)
-- 48.085.895 (all files)
select count(0) from stage_establishment;

select * from stage_establishment where fantasy_name like '%BROCADUS DELIVERY%';
-- todo fix, check columns order and values
select count(0) as total from stage_establishment where activity_start_date is null;
/*
total|
-----|
   14| needs to be checked
*/
select * from stage_establishment where activity_start_date is null;