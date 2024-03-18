CREATE TABLE sw_homeworld (
  id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sw_character (
  id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) DEFAULT NULL,
  picture_url varchar(255) DEFAULT NULL,
  sw_homeworld_id INTEGER UNSIGNED,
  FOREIGN KEY (sw_homeworld_id) REFERENCES sw_homeworld(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;