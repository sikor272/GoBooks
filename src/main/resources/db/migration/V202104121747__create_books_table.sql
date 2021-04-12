DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    isbn             VARCHAR(20)  NOT NULL UNIQUE,
    title            VARCHAR(255) NOT NULL,
    category         VARCHAR(100) NOT NULL,
    publication_date DATE         NOT NULL,
    author_id        BIGINT       NOT NULL,
    publisher_id     BIGINT       NOT NULL,
    deleted          BOOLEAN DEFAULT (FALSE),
    created_on       TIMESTAMP WITH TIME ZONE,
    updated_on       TIMESTAMP WITH TIME ZONE,

    CONSTRAINT books_author_fk
        FOREIGN KEY (author_id)
            REFERENCES authors (id),
    CONSTRAINT books_publisher_fk
        FOREIGN KEY (publisher_id)
            REFERENCES publishing_houses (id)
);