create table subscriptions
(
    id          UUID PRIMARY KEY,
    name        varchar(256) NOT NULL,
    price       int          NOT NULL,
    description text         NOT NULL
);

create table tickets
(
    id         UUID PRIMARY KEY,
    price      int  NOT NULL,
    concert_id UUID NOT NULL,
    user_id    UUID NOT NULL
);

create table user_playlists
(
    id          UUID PRIMARY KEY,
    title       varchar(256) NOT NULL,
    description varchar(256) NOT NULL,
    user_id     UUID         NOT NULL
);

create table user_subscriptions
(
    id              UUID PRIMARY KEY,
    is_valid        boolean NOT NULL,
    host_user_id    UUID,
    subscription_id UUID    NOT NULL,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions (id)
);
