
CREATE TABLE user (
    id bigint not null auto_increment,
    active bit not null,
    age integer not null,
    birth_date date,
    city varchar(255),
    cpf varchar(255),
    email varchar(255),
    name varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);



