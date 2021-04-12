DROP TABLE IF EXISTS authors;

CREATE TABLE authors
(
    id          BIGSERIAL PRIMARY KEY,
    uuid        VARCHAR(36)  NOT NULL UNIQUE,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    nationality VARCHAR(255),
    birth_date  DATE,
    biography   TEXT,
    deleted     BOOLEAN DEFAULT (FALSE),
    created_on  TIMESTAMP WITH TIME ZONE,
    updated_on  TIMESTAMP WITH TIME ZONE
);