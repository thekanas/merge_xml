create table history
(
    doc_ref                 VARCHAR(50) NOT NULL UNIQUE,
    document_date           DATE,
    date_time_upload        TIMESTAMP,
    primary key (doc_ref)
);
