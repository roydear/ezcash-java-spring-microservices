CREATE TABLE IF NOT EXISTS wallets (
     id UUID PRIMARY KEY,
     user_id UUID NOT NULL UNIQUE,
     balance NUMERIC(19, 4) NOT NULL,
     currency VARCHAR(3) NOT NULL,
     status VARCHAR(20) NOT NULL,
     last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO wallets (id, user_id, balance, currency, status, last_updated) VALUES
('1d1b5bc8-f9e8-11ee-bb8b-0242ac120002', '2d2b5bc8-f9e8-11ee-bb8b-0242ac120002', 12345.67, 'PHP', 'ACTIVE', NOW()),
('3d3b5bc8-f9e8-11ee-bb8b-0242ac120002', '4d4b5bc8-f9e8-11ee-bb8b-0242ac120002', 452.12, 'PHP', 'ACTIVE', NOW()),
('5d5b5bc8-f9e8-11ee-bb8b-0242ac120002', '6d6b5bc8-f9e8-11ee-bb8b-0242ac120002', 33100.00, 'PHP', 'FROZEN', NOW()),
('7d7b5bc8-f9e8-11ee-bb8b-0242ac120002', '8d8b5bc8-f9e8-11ee-bb8b-0242ac120002', 0.00, 'PHP', 'CLOSED', NOW()),
('9d9b5bc8-f9e8-11ee-bb8b-0242ac120002', '10ab5bc8-f9e8-11ee-bb8b-0242ac120002', 19872.50, 'PHP', 'ACTIVE', NOW());

