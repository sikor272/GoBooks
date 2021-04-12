DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    score       SMALLINT     NOT NULL,
    reviewer_id BIGINT       NOT NULL,
    book_id     BIGINT       NOT NULL,
    deleted     BOOLEAN DEFAULT (FALSE),
    created_on  TIMESTAMP WITH TIME ZONE,
    updated_on  TIMESTAMP WITH TIME ZONE,

    CONSTRAINT reviewer_fk
        FOREIGN KEY (reviewer_id)
            REFERENCES users (id),
    CONSTRAINT reviews_book_fk
        FOREIGN KEY (book_id)
            REFERENCES books (id)
);