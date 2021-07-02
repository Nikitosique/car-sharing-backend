CREATE TABLE rent_sessions
(
    id                 SERIAL PRIMARY KEY,
    customer_id        INT DEFAULT (0),
    car_id             INT DEFAULT (0),
    rent_time_interval INTERVAL,
    rent_session_cost  INT
);

