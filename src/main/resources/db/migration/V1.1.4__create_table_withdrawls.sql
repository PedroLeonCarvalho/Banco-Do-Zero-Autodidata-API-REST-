
CREATE TABLE withdrawls (
    id bigint not null auto_increment,
    user_id bigint,
    withdrawl_value DECIMAL (19,2) not null,
    withdrawl_timestamp TIMESTAMP,
    account_id bigint,
    primary key (id)
);



