CREATE TABLE IF NOT EXISTS users
(
    uuid      VARCHAR  NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS stats
(
    user_uuid     VARCHAR   REFERENCES users(uuid)  NOT NULL,
    kills         INT                               NOT NULL,
    deaths        INT                               NOT NULL,
    broken_beds   INT                               NOT NULL
);