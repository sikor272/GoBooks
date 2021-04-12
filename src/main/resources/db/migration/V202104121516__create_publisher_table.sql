DROP TABLE IF EXISTS publishing_houses;

CREATE TABLE publishing_houses
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    deleted    BOOLEAN DEFAULT (FALSE),
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE
);