create table group_buy_notifications
(
    id           bigint auto_increment not null,
    group_buy_id bigint       not null,
    user_id      bigint       not null,
    status       enum('PENDING', 'SENT', 'FAILED') not null,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    created_by   varchar(50) DEFAULT 'system',
    updated_at   timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by   varchar(50) DEFAULT 'system',
    CONSTRAINT pk_group_buy_notifications PRIMARY KEY (id)
);
