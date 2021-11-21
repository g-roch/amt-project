-- AMT - amt_pianorgue
-- 08.11.2021
-- Version: 1.0
-- Roch, Gianinetti, Canton, Zaccaria, Hungerbühler

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`article` (
  `id` INTEGER UNSIGNED UNIQUE AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  `price` DECIMAL(10,2) NULL DEFAULT NULL,
  `description` TEXT NOT NULL,
  `image` VARCHAR(255) NULL DEFAULT NULL,
  `stock` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;
  

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`category` (
  `id` INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`article_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`article_category` (
  `articleId` INT UNSIGNED NOT NULL,
  `categoryId` INT UNSIGNED NOT NULL,
  CONSTRAINT `article_category_articleId`
    FOREIGN KEY (`articleId`)
    REFERENCES `amt_pianorgue`.`article` (`id`),
  CONSTRAINT `article_category_categoryId`
    FOREIGN KEY (`categoryId`)
    REFERENCES `amt_pianorgue`.`category` (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`users` (
  `id` INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`cart` (
  `userId` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `articleId` INT UNSIGNED NOT NULL,
  CONSTRAINT `cart_articleId`
    FOREIGN KEY (`articleId`)
    REFERENCES `amt_pianorgue`.`article` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `cart_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `amt_pianorgue`.`users` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `amt_pianorgue`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `amt_pianorgue`.`hibernate_sequence` (
  `next_val` BIGINT NULL DEFAULT NULL)
ENGINE=INNODB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;
