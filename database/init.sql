-- Create the database if it doesn't exist (running this might require connecting to postgres db first)
-- DROP DATABASE IF EXISTS mon_futur_stage;
-- CREATE DATABASE mon_futur_stage;

-- Connect to the database
-- \c mon_futur_stage

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (login)
);

-- Insert default data
INSERT INTO users (login, password, role_name) VALUES 
('etudiant', 'password', 'STUDENT'),
('admin', 'admin', 'ADMIN')
ON CONFLICT (login) DO NOTHING;
