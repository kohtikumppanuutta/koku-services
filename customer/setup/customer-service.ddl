
CREATE TABLE customer (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  status VARCHAR(30) NOT NULL,
  status_date DATE NOT NULL,
  pic VARCHAR(11) NOT NULL UNIQUE,
  birth_date DATE NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  first_names VARCHAR(50) NOT NULL,
  nationality VARCHAR(3) NOT NULL,
  municipality VARCHAR(10) NOT NULL,
  turvakielto BOOLEAN NOT NULL,
  version INT NOT NULL
);

