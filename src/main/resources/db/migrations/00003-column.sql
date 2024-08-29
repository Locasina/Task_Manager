--liquibase formatted sql

--changeset Nicko:3
--comment: init column

CREATE TABLE IF NOT EXISTS pillar
(
    id                  SERIAL      PRIMARY KEY,
    task_board_id       INT         REFERENCES task_board (id) ON UPDATE CASCADE ON DELETE CASCADE,
    position_number     INT         NOT NULL UNIQUE,
    title               TEXT        NOT NULL,
    is_archived         BOOLEAN     NOT NULL,
    archivation_date    DATE,
    creation_date       DATE        NOT NULL
);

COMMENT ON TABLE pillar IS 'Таблица для хранения данных колонок';
COMMENT ON COLUMN pillar.id IS 'Идентификатор записи';
COMMENT ON COLUMN pillar.task_board_id IS 'Идентификатор рабочего пространства';
COMMENT ON COLUMN pillar.position_number IS 'Номер позиции в колонке';
COMMENT ON COLUMN pillar.title IS 'Название колонки';
COMMENT ON COLUMN pillar.is_archived IS 'Статус нахождения в архиве';
COMMENT ON COLUMN pillar.archivation_date IS 'Дата и время архивирования';
COMMENT ON COLUMN pillar.creation_date IS 'Дата и время создания';

 --changeset Nicko:4
 --comment: renaming

ALTER TABLE task_board RENAME COLUMN desription TO description;

