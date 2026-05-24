CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE rides (
    ride_id SERIAL PRIMARY KEY,
    driver_id INT REFERENCES users(user_id),
    source VARCHAR(100),
    destination VARCHAR(100),
    seats INT
);

CREATE TABLE bookings (
    booking_id SERIAL PRIMARY KEY,
    ride_id INT REFERENCES rides(ride_id),
    student_id INT REFERENCES users(user_id),
    status VARCHAR(20),
    confirmation_id VARCHAR(50) UNIQUE
);