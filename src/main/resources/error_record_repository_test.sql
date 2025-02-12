DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'openai_plugin') THEN
            PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE openai_plugin');
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'openai') THEN
            EXECUTE 'CREATE USER openai WITH PASSWORD ''openai''';
        END IF;

        EXECUTE 'GRANT ALL PRIVILEGES ON DATABASE openai_plugin TO openai';
    END
$$;