CREATE TABLE IF NOT EXISTS `t_transaction` (
  `id` BIGINT NOT NULL PRIMARY KEY,
  `transaction_id` VARCHAR(64) NOT NULL,
  `account` VARCHAR(64) NOT NULL,
  `amount` DECIMAL(15, 2) NOT NULL COMMENT 'Positive values represent income and negative values represent expenditure',
  `balance` DECIMAL(15, 2) NOT NULL COMMENT 'Post-transaction account balance',
  `type` VARCHAR(20) NOT NULL COMMENT 'TRANSFER, DEPOSIT, WITHDRAW, PAYMENT',
  `status` VARCHAR(20) NOT NULL COMMENT 'SUCCESS, FAILED, PROCESSING',
  `counterparty` VARCHAR(64) NOT NULL COMMENT 'Counterparty information',
  `description` VARCHAR(255),
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL
);

-- Here are the indexes that must be create
-- Create an index for specifying the user
CREATE INDEX IF NOT EXISTS idx_account_time_id ON t_transaction (account, create_time DESC, id DESC);

-- Create an index for global queries
CREATE INDEX IF NOT EXISTS idx_time_id ON t_transaction (create_time DESC, id DESC);

-- Create an index for specifying the user and type
CREATE INDEX IF NOT EXISTS idx_account_type_time_id ON t_transaction (account, type, create_time DESC, id DESC);

-- Because creating too many indexes will lead to a decrease in write efficiency,
-- the following indexes need to be created as needed based on actual usage:
-- account + status + create_time + id
-- account + type + status + create_time + id
