-- Create authors table
CREATE TABLE IF NOT EXISTS authors (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

-- Create papers table
CREATE TABLE IF NOT EXISTS papers (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    author_id INT REFERENCES authors(id)
);
-- Create log table
CREATE TABLE IF NOT EXISTS paper_log (
    log_id SERIAL PRIMARY KEY,
    paper_id INT,
    title TEXT,
    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger function
CREATE OR REPLACE FUNCTION log_paper_insert_fn()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO paper_log (paper_id, title)
    VALUES (NEW.id, NEW.title);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_trigger WHERE tgname = 'log_paper_insert'
    ) THEN
        CREATE TRIGGER log_paper_insert
        AFTER INSERT ON papers
        FOR EACH ROW
        EXECUTE FUNCTION log_paper_insert_fn();
    END IF;
END
$$;

-- Create view
DROP VIEW IF EXISTS paper_author_view;

CREATE VIEW paper_author_view AS
SELECT
    p.id AS paper_id,
    p.title,
    a.name AS author_name
FROM
    papers p
JOIN
    authors a ON p.author_id = a.id;
    -- Cursor Example: Loop through all papers and raise notice for each
-- Cursor Example: Loop through all papers and raise notice for each
DO $$
DECLARE
    paper_rec RECORD;
BEGIN
    FOR paper_rec IN SELECT * FROM papers LOOP
        RAISE NOTICE 'Paper ID: %, Title: %, Author ID: %',
            paper_rec.id, paper_rec.title, paper_rec.author_id;
    END LOOP;
END
$$;