CREATE TABLE USER
(
    ID bigint AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID varchar(100),
    NAME varchar(50),
    TOKEN varchar(36),
    GMT_CREATE bigint,
    GMT_MODIFIED bigint,
    sex INT DEFAULT 0 NULL,
    pwd VARCHAR(100) NULL
);
