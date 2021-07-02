CREATE TABLE discount_cards
(
    id             SERIAL PRIMARY KEY,
    card_number    TEXT UNIQUE,
    discount_value INT CHECK (discount_value > 0 AND discount_value <= 15)
);



