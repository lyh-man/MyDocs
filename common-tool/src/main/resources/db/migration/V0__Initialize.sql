CREATE FUNCTION current_timestamp_utc() RETURNS timestamp
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN CURRENT_TIMESTAMP AT TIME ZONE 'UTC';
END;
$$;


CREATE TABLE name
(
    id                 bigserial    NOT NULL,
    old_name           varchar(64)  NOT NULL,
    new_name           varchar(64)  NOT NULL,
    last_modified_date timestamp    NOT NULL,
    registered_date    timestamp    NOT NULL,
    expire_date        timestamp    NOT NULL,
    lang_id            smallint     NOT NULL,
    title              varchar(64)  NOT NULL,
    description        varchar(512) NOT NULL,
    CONSTRAINT pk_name PRIMARY KEY (id)
);

CREATE TABLE age
(
    id                 bigserial    NOT NULL,
    age                integer      NOT NULL,
    last_modified_date timestamp    NOT NULL,
    registered_date    timestamp    NOT NULL,
    expire_date        timestamp    NOT NULL,
    lang_id            smallint     NOT NULL,
    title              varchar(64)  NOT NULL,
    description        varchar(512) NOT NULL,
    CONSTRAINT pk_age PRIMARY KEY (id)
);