
SET storage_engine = innodb;

--
-- lok service
--
CREATE TABLE log (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  data_item_id VARCHAR(10) NOT NULL,
  timestamp DATETIME NOT NULL,
  user_pic VARCHAR(11) NOT NULL,
  customer_pic VARCHAR(11),
  data_item_type VARCHAR(15) NOT NULL,
  operation VARCHAR(10) NOT NULL,
  client_system_id VARCHAR(10) NOT NULL,
  message VARCHAR(100)
);

CREATE TABLE log_admin (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  log_id BIGINT UNSIGNED NOT NULL,
  timestamp DATETIME NOT NULL,
  user_pic VARCHAR(11) NOT NULL,
  customer_pic VARCHAR(11) NOT NULL,
  operation VARCHAR(10) NOT NULL,
  message VARCHAR(100)
);

CREATE TABLE log_archive (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  data_item_id VARCHAR(10) NOT NULL,
  timestamp DATETIME NOT NULL,
  user_pic VARCHAR(11) NOT NULL,
  customer_pic VARCHAR(11) NOT NULL,
  data_item_type VARCHAR(15) NOT NULL,
  operation VARCHAR(10) NOT NULL,
  client_system_id VARCHAR(10) NOT NULL,
  message VARCHAR(100)
);
