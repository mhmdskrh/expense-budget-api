create table _user(
    id serial primary key,
    name varchar not null,
    email varchar not null unique,
    password varchar not null
);