
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
  data_item_type VARCHAR(100) NOT NULL,
  operation VARCHAR(20) NOT NULL,
  client_system_id VARCHAR(20) NOT NULL,
  message TEXT
);

CREATE TABLE log_admin (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  timestamp DATETIME NOT NULL,
  user_pic VARCHAR(11) NOT NULL,
  customer_pic VARCHAR(11),
  operation VARCHAR(20) NOT NULL,
  message VARCHAR(200)
);

CREATE TABLE log_archive (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  data_item_id VARCHAR(10) NOT NULL,
  timestamp DATETIME NOT NULL,
  user_pic VARCHAR(11) NOT NULL,
  customer_pic VARCHAR(11),
  data_item_type VARCHAR(100) NOT NULL,
  operation VARCHAR(20) NOT NULL,
  client_system_id VARCHAR(20) NOT NULL,
  message TEXT
);
