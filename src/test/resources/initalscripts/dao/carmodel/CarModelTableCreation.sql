CREATE TABLE car_models
(
    id                  SERIAL PRIMARY KEY,
    body_id             INT,
    brand_id            INT,
    fuel_id             INT,
    model_name          VARCHAR(30) NOT NULL,
    engine_displacement DECIMAL(2, 1),
    gearbox_type        VARCHAR(9) CHECK ( gearbox_type = 'automatic' OR gearbox_type = 'manual'),
    production_year     INT         NOT NULL
);


