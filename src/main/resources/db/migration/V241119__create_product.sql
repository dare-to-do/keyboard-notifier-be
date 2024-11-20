CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(50) DEFAULT 'system',
    updated_at    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by    VARCHAR(50) DEFAULT 'system',
    name          VARCHAR(150) NULL,
    price         BIGINT NULL,
    price_unit    VARCHAR(50) NULL,
    image_url     VARCHAR(2500) NULL,
    product_url   VARCHAR(255) NULL,
    type          VARCHAR(40) NULL,
    description   VARCHAR(255) NULL,
    start_date    timestamp NULL,
    end_date      timestamp NULL,
    status        VARCHAR(30) NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);
