CREATE TABLE group_buy_participants
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    group_buy_id BIGINT       NOT NULL,
    user_id      BIGINT       NOT NULL,
    joined_at    datetime     NOT NULL,
    status       VARCHAR(50) NOT NULL,
    CONSTRAINT pk_group_buy_participants PRIMARY KEY (id)
);

ALTER TABLE group_buy_participants
    ADD CONSTRAINT uc_0efefbc1fbef3d6e2a82e8ff8 UNIQUE (group_buy_id, user_id);
