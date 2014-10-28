CREATE TABLE users (
  id varchar(10) NOT NULL,
  name  varchar(20) NOT NULL,
  password varchar(10) NOT NULL,
  level tinyint NOT NULL,
  login int NOT NULL,
  recommend int NOT NULL,

  PRIMARY KEY (id)
) 