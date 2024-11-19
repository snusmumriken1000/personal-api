PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE habit_records (
        id integer,
        completed boolean not null,
        record_date date not null,
        habit_id bigint not null,
        primary key (id)
    );
CREATE TABLE habits (
        id integer,
        created_at timestamp,
        description varchar(255),
        title varchar(255) not null,
        updated_at timestamp,
        user_id bigint,
        primary key (id)
    );
CREATE TABLE users (
        id integer,
        age integer,
        email varchar(255),
        name varchar(255),
        primary key (id)
    );
COMMIT;
