use amt_pianorgue;

-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: amt_pianorgue
-- ------------------------------------------------------
-- Server version	8.0.17

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idUser_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Lucas','lg@heig-vd.ch'),(2,'Nico','nh@heig-vd.ch'),(3,'Dylan','dc@heig-vd.ch'),(4,'Gab','gr@heig-vd.ch'),(5,'Chris','cz@heig-vd.ch');

