alter table group_buys
add column created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
add column created_by    VARCHAR(50) DEFAULT 'system',
add column updated_at    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
add column updated_by    VARCHAR(50) DEFAULT 'system';
