-- Create table `user`
CREATE TABLE "user"
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DOUBLE NOT NULL,
    password VARCHAR(255),
    status   VARCHAR(255),
    username VARCHAR(255),
    email    VARCHAR(255)
);


-- Create table `operation`
CREATE TABLE Operation
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    cost DECIMAL     NOT NULL
);

-- Create table `record`
CREATE TABLE record
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_id       BIGINT       NOT NULL,
    user_id            VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    first_value DOUBLE,
    second_value DOUBLE,
    user_balance DOUBLE NOT NULL,
    operation_response VARCHAR(255),
    date               TIMESTAMP    NOT NULL,
    CONSTRAINT fk_operation FOREIGN KEY (operation_id) REFERENCES operation (id)
);


-- Create table `customer`
CREATE TABLE customer
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL
);

-- Create table `place`
CREATE TABLE place
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

-- Create table `person`
CREATE TABLE person
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (id) REFERENCES customer (id)
);

-- Create table `guest`
CREATE TABLE guest
(
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_guest FOREIGN KEY (id) REFERENCES person (id)
);

-- Create table `employee`
CREATE TABLE employee
(
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_employee FOREIGN KEY (id) REFERENCES person (id)
);

-- Create table `booking`
CREATE TABLE booking
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    status     VARCHAR(255) NOT NULL,
    guest_id   BIGINT       NOT NULL,
    place_id   BIGINT       NOT NULL,
    CONSTRAINT fk_booking_guest FOREIGN KEY (guest_id) REFERENCES person (id),
    CONSTRAINT fk_booking_place FOREIGN KEY (place_id) REFERENCES place (id)
);


