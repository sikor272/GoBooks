ALTER TABLE users
    ADD COLUMN deleted BOOLEAN DEFAULT (FALSE);

UPDATE users
SET deleted = FALSE
WHERE deleted IS NULL;

ALTER TABLE api_login_entries
    ADD COLUMN deleted BOOLEAN DEFAULT (FALSE);

UPDATE api_login_entries
SET deleted = FALSE
WHERE deleted IS NULL;