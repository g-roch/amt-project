drop database if exists amt_local_database;
create database amt_local_database;
use amt_local_database;

DROP TABLE IF EXISTS `article`;




CREATE TABLE `article` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `name` varchar(45) NOT NULL,
                           `stock` int NOT NULL,
                           `price` int NOT NULL,
                           `description` varchar(45) NOT NULL,
                           `image` varchar(100) NOT NULL DEFAULT 'f1.png',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4;

INSERT INTO `article` VALUES (1,'Violon',1, 100, 'sexy Violon', default), (2,'Piano',1, 200, 'what a piano', default);

