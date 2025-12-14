CREATE DATABASE IF NOT EXISTS Auto;
USE Auto;

CREATE TABLE IF NOT EXISTS AutoData (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mpg DOUBLE,
    cylinders INT,
    displacement DOUBLE,
    horsepower DOUBLE,
    weight INT,
    acceleration DOUBLE,
    model_year INT,
    origin INT,
    car_name VARCHAR(100)
);
