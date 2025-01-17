CREATE TABLE group_buys
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    product_id      BIGINT NULL,
    start_date_time datetime NULL,
    end_date_time   datetime NULL,
    status          VARCHAR(30) NOT NULL,
    CONSTRAINT pk_group_buys PRIMARY KEY (id)
);
