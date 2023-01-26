DO LANGUAGE plpgsql
$body$
DECLARE
   old_schema NAME = 'abrastat_template';
   new_schema NAME = 'dst_schema';
   tbl TEXT;
   sql TEXT;
BEGIN
  EXECUTE format('CREATE SCHEMA IF NOT EXISTS %I', new_schema);

  FOR tbl IN
    SELECT table_name
    FROM information_schema.tables
    WHERE table_schema=old_schema
  LOOP
    sql := format(
            'CREATE TABLE IF NOT EXISTS %I.%I '
            '(LIKE %I.%I INCLUDING INDEXES INCLUDING CONSTRAINTS)'
            , new_schema, tbl, old_schema, tbl);

    EXECUTE sql;

    sql := format(
            'INSERT INTO %I.%I '
            'SELECT * FROM %I.%I'
            , new_schema, tbl, old_schema, tbl);

    EXECUTE sql;
  END LOOP;
END
$body$;