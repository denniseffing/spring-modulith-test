CREATE TABLE IF NOT EXISTS habits
(
    id          VARCHAR(36),
    name        VARCHAR(255),
    frequency   VARCHAR(50),
    repetitions INTEGER
);

CREATE TABLE IF NOT EXISTS tracked_habits
(
    id      VARCHAR(36),
    version INTEGER
);
