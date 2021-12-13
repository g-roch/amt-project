-- AMT
-- 08.11.2021
-- Version: 1.0
-- Roch, Gianinetti, Canton, Zaccaria, Hungerbühler

-- -----------------------------------------------------
-- Table `article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `article` (
  `id` INTEGER UNSIGNED UNIQUE AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  `price` DECIMAL(10,2) NULL DEFAULT NULL,
  `description` TEXT NOT NULL,
  `image` VARCHAR(255) NULL DEFAULT NULL,
  `stock` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
  

-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `article_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `article_category` (
  `articleid` INT UNSIGNED NOT NULL,
  `categoryid` INT UNSIGNED NOT NULL,
  CONSTRAINT `article_category_articleid`
    FOREIGN KEY (`articleid`)
    REFERENCES `article` (`id`),
  CONSTRAINT `article_category_categoryid`
    FOREIGN KEY (`categoryid`)
    REFERENCES `category` (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cart` (
  `userid` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `articleid` INT UNSIGNED NOT NULL,
  CONSTRAINT `cart_articleid`
    FOREIGN KEY (`articleid`)
    REFERENCES `article` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `cart_userid`
    FOREIGN KEY (`userid`)
    REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=INNODB;