CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
