CREATE SCHEMA IF NOT EXISTS contact_schema;

CREATE TABLE IF NOT EXISTS contact_schema.contacts (
  id integer PRIMARY KEY,
  firstName VARCHAR(50),
  lastName VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20)
);