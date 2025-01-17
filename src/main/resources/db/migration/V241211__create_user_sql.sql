CREATE TABLE user_entity
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    name         VARCHAR(30) NULL,
    email        VARCHAR(30) NULL,
    phone_number VARCHAR(30) NULL,
    CONSTRAINT pk_userentity PRIMARY KEY (id)
);
