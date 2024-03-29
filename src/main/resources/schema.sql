CREATE TABLE IF NOT EXISTS users
(
    uuid          VARCHAR                           NOT NULL PRIMARY KEY,
    kills         INT                               NOT NULL,
    deaths        INT                               NOT NULL,
    broken_beds   INT
);