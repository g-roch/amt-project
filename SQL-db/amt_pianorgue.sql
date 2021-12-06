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
  `articleId` INT UNSIGNED NOT NULL,
  `categoryId` INT UNSIGNED NOT NULL,
  CONSTRAINT `article_category_articleId`
    FOREIGN KEY (`articleId`)
    REFERENCES `article` (`id`),
  CONSTRAINT `article_category_categoryId`
    FOREIGN KEY (`categoryId`)
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
  `userId` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `articleId` INT UNSIGNED NOT NULL,
  CONSTRAINT `cart_articleId`
    FOREIGN KEY (`articleId`)
    REFERENCES `article` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `cart_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
