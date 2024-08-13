--liquibase formatted sql

--changeset Nicko:1
--comment: initial task_board

CREATE TABLE IF NOT EXISTS task_board
(
  id            SERIAL PRIMARY KEY,
  title         TEXT NOT NULL,
  desription    TEXT,
  is_archived   BOOLEAN NOT NULL,
  archive_date  DATE,
  create_date   DATE NOT NULL
);

COMMENT ON TABLE task_board IS 'Таблица для хранения данных рабочих пространств';
COMMENT ON COLUMN task_board.id IS 'Идентификатор рабочего пространства';
COMMENT ON COLUMN task_board.title IS 'Имя рабочего пространства';
COMMENT ON COLUMN task_board.desription IS 'Описвание рабочего пространства';
COMMENT ON COLUMN task_board.is_archived IS 'Статус нахождения в архиве';
COMMENT ON COLUMN task_board.archive_date IS 'Дата архивирования';
COMMENT ON COLUMN task_board.create_date IS 'Дата создания';

--changeset Nicko:2
--comment: create table account_to_task_board

CREATE TABLE IF NOT EXISTS account_to_task_board
(
    id             SERIAL PRIMARY KEY,
    account_id     INT REFERENCES account (id) ON UPDATE CASCADE ON DELETE CASCADE,
    task_board_id  INT REFERENCES task_board (id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE account_to_task_board IS 'Таблица связи аккаунтов и рабочих пространств';
COMMENT ON COLUMN account_to_task_board.id IS 'Идентификатор записи';
COMMENT ON COLUMN account_to_task_board.account_id IS 'Идентификатор аккаунта';
COMMENT ON COLUMN account_to_task_board.task_board_id IS 'Идентификатор рабчоего пространства';