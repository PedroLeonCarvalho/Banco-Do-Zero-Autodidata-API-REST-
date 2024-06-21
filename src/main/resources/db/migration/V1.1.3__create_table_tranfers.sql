
CREATE TABLE transfers (
    id bigint not null auto_increment,
    sender_id bigint,
    receiver_id bigint,
    transfer_value DECIMAL (19,2) not null,
    transfer_timestamp TIMESTAMP,
    primary key (id)
);



