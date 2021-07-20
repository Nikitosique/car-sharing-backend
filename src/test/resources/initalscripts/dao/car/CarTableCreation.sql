CREATE TABLE cars
(
    id                SERIAL PRIMARY KEY,
    model_id          INT DEFAULT (0),
    reg_plate         TEXT UNIQUE NOT NULL,
    rent_cost_per_min INT CHECK (rent_cost_per_min >= 3),
    color             TEXT,
    photo             TEXT
);
