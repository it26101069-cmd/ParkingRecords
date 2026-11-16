-- ParkWise: runs automatically on startup (spring.sql.init.mode=always).
-- Every statement is IF NOT EXISTS, so existing data is never dropped.

CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_number VARCHAR(20)  NOT NULL PRIMARY KEY,
    vehicle_type   VARCHAR(20)  NOT NULL,
    brand          VARCHAR(50)  NOT NULL,
    model          VARCHAR(50)  NOT NULL,
    color          VARCHAR(30)  NOT NULL,
    owner_name     VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id      BIGINT       NOT NULL AUTO_INCREMENT,
    full_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NOT NULL,
    address      VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS parking_zones (
    zone_code    VARCHAR(10) NOT NULL PRIMARY KEY,
    zone_name    VARCHAR(50) NOT NULL,
    floor_number INT         NOT NULL,
    description  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS parking_slots (
    slot_number VARCHAR(20)   NOT NULL PRIMARY KEY,
    zone_code   VARCHAR(10)   NOT NULL,
    slot_type   VARCHAR(20)   NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'AVAILABLE',
    hourly_rate DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_slot_zone FOREIGN KEY (zone_code) REFERENCES parking_zones (zone_code)
);

CREATE TABLE IF NOT EXISTS part_payments (
    payment_id     VARCHAR(20)   PRIMARY KEY,
    vehicle_number VARCHAR(20)   NOT NULL,
    customer_name  VARCHAR(100)  NOT NULL,
    total_amount   DECIMAL(10,2) NOT NULL,
    paid_amount    DECIMAL(10,2) NOT NULL,
    balance_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(30)   NOT NULL,
    payment_status VARCHAR(20)   NOT NULL,
    payment_date   DATETIME      NOT NULL,
    notes          VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS staff (
    staff_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100),
    phone    VARCHAR(20),
    `role`   VARCHAR(50)  NOT NULL,
    shift    VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS parking_records (
    record_id      VARCHAR(20)  NOT NULL PRIMARY KEY,
    customer_name  VARCHAR(100) NOT NULL,
    vehicle_number VARCHAR(20)  NOT NULL,
    slot_number    VARCHAR(20)  NOT NULL,
    booking_time   DATETIME     NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE'
);
