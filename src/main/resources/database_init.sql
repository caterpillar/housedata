CREATE DATABASE house_data
    CHARACTER SET utf8
    DEFAULT CHARACTER SET utf8
    COLLATE utf8_general_ci
    DEFAULT COLLATE utf8_general_ci;

# 城市配置表
CREATE TABLE `city_config` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(30) NOT NULL,
  `city_code` varchar(20) DEFAULT NULL,
  `request_url` varchar(200) DEFAULT NULL,
  `order_code` int(2) DEFAULT NULL,
  `default_page` int(5) DEFAULT NULL,
  `running_status` int(5) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE `house_data` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `city_id` varchar(20) NOT NULL,
  `city_name` varchar(30) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `section` VARCHAR(20) DEFAULT NULL,
  `address` VARCHAR(100) DEFAULT NULL,
  `sale_status` VARCHAR(10) DEFAULT NULL,
  `origin_url` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




