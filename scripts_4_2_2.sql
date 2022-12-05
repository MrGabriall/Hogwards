create table if not exists cars
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(16)                      not null,
    price VARCHAR(32)                      not null,
    price INTEGER CHECK ( cars.price > 0 ) not null
);

create table if not exists owners
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(32)                      not null,
    age                 INTEGER CHECK ( owners.age > 0 ) not null,
    has_driving_licence BOOLEAN DEFAULT FALSE,
    car_id              BIGSERIAL REFERENCES cars (id)
)
