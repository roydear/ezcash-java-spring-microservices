CREATE TABLE IF NOT EXISTS wallets (
   id UUID PRIMARY KEY,
   user_id UUID NOT NULL UNIQUE,
   balance NUMERIC(19, 4) NOT NULL,
   currency VARCHAR(3) NOT NULL,
   status VARCHAR(20) NOT NULL,
   last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TO RUN IN THE CONSOLE OF POSTGRES
INSERT INTO wallets (id, user_id, balance, currency, status, last_updated)
   SELECT '1d1b5bc8-f9e8-11ee-bb8b-0242ac120002',
          '2d2b5bc8-f9e8-11ee-bb8b-0242ac120002',
          12345.67,
          'PHP',
          'ACTIVE',
          NOW()
       WHERE NOT EXISTS (SELECT 1 FROM wallets WHERE id  = '1d1b5bc8-f9e8-11ee-bb8b-0242ac120002');

INSERT INTO wallets (id, user_id, balance, currency, status, last_updated)
    SELECT '3d3b5bc8-f9e8-11ee-bb8b-0242ac120002',
           '4d4b5bc8-f9e8-11ee-bb8b-0242ac120002',
           452.12,
           'PHP',
           'ACTIVE',
           NOW()
WHERE NOT EXISTS (SELECT 1 FROM wallets WHERE id  = '3d3b5bc8-f9e8-11ee-bb8b-0242ac120002');


