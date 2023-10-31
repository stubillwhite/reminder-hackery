DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
  id VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  deadline TIMESTAMP WITH TIME ZONE NOT NULL,
  PRIMARY KEY (id)
);