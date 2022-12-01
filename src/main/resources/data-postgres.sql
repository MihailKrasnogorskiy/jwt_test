INSERT INTO users (name, password)
VALUES ('test', 'test')
ON CONFLICT (name) DO NOTHING;