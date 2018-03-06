DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) DEFAULT NULL,
  `MONEY` double DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8;

/*Table structure for table `http_log` */

DROP TABLE IF EXISTS `http_log`;

CREATE TABLE `http_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_url` varchar(200) NOT NULL,
  `http_method` varchar(20) NOT NULL,
  `client_ip` varchar(20) NOT NULL,
  `client_address` varchar(200) NOT NULL,
  `client_proxy` varchar(500) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

/*Table structure for table `love_image` */

DROP TABLE IF EXISTS `love_image`;

CREATE TABLE `love_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_url` varchar(200) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
