create table users
(
    id               SERIAL PRIMARY KEY,
    email            TEXT UNIQUE NOT NULL,
    password         TEXT        NOT NULL,
    discount_card_id INT UNIQUE,
    type             TEXT CHECK ( type = 'customer' OR type = 'manager' )
);

