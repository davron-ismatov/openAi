DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'openai_plugin') THEN
            PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE openai_plugin');
        END IF;
    END $$;