CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    timestamp,
    created_by    VARCHAR(50),
    updated_at    timestamp NULL,
    updated_by    VARCHAR(50),
    name          VARCHAR(150) NULL,
    price         BIGINT NULL,
    image_url     VARCHAR(255) NULL,
    type          VARCHAR(40) NULL,
    description VARCHAR(255) NULL,
    start_date    timestamp NULL,
    end_date      timestamp NULL,
    status        VARCHAR(30) NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);
