--liquibase formatted sql

--changeset roman:1
--comment: initial migration

CREATE TABLE IF NOT EXISTS account
(
    id                          SERIAL PRIMARY KEY,
    person_name                 TEXT,
    person_surname              TEXT,
    login                       TEXT      NOT NULL UNIQUE,
    password                    TEXT      NOT NULL,
    email                       TEXT UNIQUE,
    enabled                     BOOLEAN   NOT NULL DEFAULT FALSE,
    last_time_active            TIMESTAMP NOT NULL,
    creation_time               TIMESTAMP NOT NULL,
    update_time                 TIMESTAMP NOT NULL
);

COMMENT ON TABLE account IS 'Таблица для хранения данных пользователей';
COMMENT ON COLUMN account.id IS 'Идентификатор записи';
COMMENT ON COLUMN account.person_name IS 'Имя пользователя';
COMMENT ON COLUMN account.person_surname IS 'Фамилия пользователя';
COMMENT ON COLUMN account.login IS 'Логин пользователя';
COMMENT ON COLUMN account.password IS 'Пароль пользователя';
COMMENT ON COLUMN account.email IS 'Адрес эл. почты пользователя';
COMMENT ON COLUMN account.enabled IS 'Активен ли аккаунт пользователя';
COMMENT ON COLUMN account.last_time_active IS 'Дата и время последней активности пользователя';
COMMENT ON COLUMN account.creation_time IS 'Дата и время создания аккаунта пользователя';
COMMENT ON COLUMN account.update_time IS 'Дата и время обновления данных пользователя';

CREATE TABLE IF NOT EXISTS token_blacklist
(
    id              SERIAL PRIMARY KEY,
    token           VARCHAR(500) NOT NULL,
    expiration_time TIMESTAMP    NOT NULL,
    blacklist_time  TIMESTAMP    NOT NULL
);

COMMENT ON TABLE token_blacklist IS 'Таблица для хранения данных токенов, попавших в чёрный список';
COMMENT ON COLUMN token_blacklist.id IS 'Идентификатор записи';
COMMENT ON COLUMN token_blacklist.token IS 'Дата и время истечения срока действия токена';
COMMENT ON COLUMN token_blacklist.blacklist_time IS 'Дата и время попадания токена в чёрный список';
