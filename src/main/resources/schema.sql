CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

INSERT INTO users (name, email, password) VALUES ('Admin', 'admin@example.com', 'admin');
