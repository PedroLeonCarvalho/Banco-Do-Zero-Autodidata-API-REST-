
ALTER TABLE accounts
ADD CONSTRAINT FK_user_id_accounts FOREIGN KEY (user_id)
REFERENCES users (id);

ALTER TABLE deposits
ADD CONSTRAINT FK_account_id_deposits FOREIGN KEY (account_id)
REFERENCES accounts (id);

ALTER TABLE withdrawls
ADD CONSTRAINT FK_account_id_withdrawls FOREIGN KEY (account_id)
REFERENCES accounts (id);

