CREATE TABLE users (
  id varchar(50) NOT NULL,
  name  varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  level tinyint NOT NULL,
  login int NOT NULL,
  recommend int NOT NULL,

  PRIMARY KEY (id)
) 