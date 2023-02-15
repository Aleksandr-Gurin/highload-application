create table countries
(
    id           UUID PRIMARY KEY,
    country_name varchar(256) NOT NULL
);

create table cities
(
    id         UUID PRIMARY KEY,
    city_name  varchar(256) NOT NULL,
    country_id UUID         NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries (id)
);

create table concerts
(
    id           UUID PRIMARY KEY,
    title        varchar(256) NOT NULL,
    performer    varchar(256) NOT NULL,
    concert_date date         NOT NULL,
    city_id      UUID         NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities (id)
);

create table authors
(
    id          UUID PRIMARY KEY,
    name        varchar(256) NOT NULL,
    description varchar      NOT NULL
);

create table audios
(
    id        UUID PRIMARY KEY,
    title     varchar(256) NOT NULL,
--     Потом поставить NOT NULL
    audio     bytea default NULL,
    author_id UUID         NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id)
);
