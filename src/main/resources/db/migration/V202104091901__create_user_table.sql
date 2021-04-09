DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id          BIGSERIAL       PRIMARY KEY,
    username    VARCHAR(32)     NOT NULL UNIQUE,
    password    VARCHAR(255)    NOT NULL,
    email       VARCHAR(255)    NOT NULL UNIQUE,
    avatar      VARCHAR(1000),
    created_on  TIMESTAMP WITH TIME ZONE,
    updated_on  TIMESTAMP WITH TIME ZONE
);