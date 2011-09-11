
SET storage_engine = innodb;

--
-- customer service
--
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

--
-- community service
--
CREATE TABLE community (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(30) NOT NULL,
  name VARCHAR(30),
  version INT NOT NULL
);

CREATE TABLE community_member (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  community_id BIGINT UNSIGNED NOT NULL,
  member_id BIGINT UNSIGNED NOT NULL,
  role VARCHAR(30) NOT NULL,
  CONSTRAINT UNIQUE (community_id, member_id),
  FOREIGN KEY (community_id) REFERENCES community(id)
);
