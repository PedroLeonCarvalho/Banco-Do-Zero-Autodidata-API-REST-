
CREATE TABLE deposit (
    id bigint not null auto_increment,
    account_id bigint,
    deposit_value DECIMAL (19,2) not null,
    time_stamp TIMESTAMP,

    primary key (id)
);



