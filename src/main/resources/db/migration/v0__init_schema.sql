create sequence user_id_seq
    START WITH 1
    increment BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table users (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seg'),
    name VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL CHECK(char_length(password) >= 6),
    role VARCHAR(50) NOT NULL
);