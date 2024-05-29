CREATE TABLE accounts (
    id bigint not null auto_increment,
    account_number varchar(255) not null,
    active bit not null,
    balance decimal(38,2),
    creation_date date,
    earnings_amount decimal(38,2),
    earnings_date date,
    last_deposit_date datetime(6),
    type enum ('CORRENTE','POUPANCA','PREMIUM'),
    user_id bigint,
    primary key (id)
);

