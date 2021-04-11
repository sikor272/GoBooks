DROP TABLE IF EXISTS api_login_entries;

CREATE TABLE api_login_entries
(
    id         BIGSERIAL PRIMARY KEY,
    token      VARCHAR(255) NOT NULL,
    expired_at TIMESTAMP WITH TIME ZONE,
    user_id    BIGINT       NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    CONSTRAINT api_login_entries_user_fk
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);