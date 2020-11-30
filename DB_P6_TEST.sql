-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema P6_DB_TEST
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `P6_DB_TEST` ;

-- -----------------------------------------------------
-- Schema P6_DB_TEST
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `P6_DB_TEST` DEFAULT CHARACTER SET utf8 ;
USE `P6_DB_TEST` ;

-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`User` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(30) NULL,
  `first_name` VARCHAR(30) NULL,
  `birthday` DATE NULL,
  `adress` VARCHAR(50) NULL,
  `zip_code` INT NULL,
  `city` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`Connexion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`Connexion` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`Connexion` (
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`email`),
  INDEX `fk_Connexion_Utilisateur_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_Connexion_Utilisateur`
    FOREIGN KEY (`id_user`)
    REFERENCES `P6_DB_TEST`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`Account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`Account` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`Account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_created` DATE NULL,
  `status` VARCHAR(20) NULL,
  `balance` DECIMAL(15,2) NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Compte_Utilisateur1_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_Compte_Utilisateur1`
    FOREIGN KEY (`id_user`)
    REFERENCES `P6_DB_TEST`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`External_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`External_account` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`External_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `IBAN` VARCHAR(15) NULL,
  `BIC` VARCHAR(15) NULL,
  `bank_name` VARCHAR(45) NULL,
  `date_added` DATE NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Compte exterieur_Utilisateur1_idx` (`id_user` ASC) VISIBLE,
  CONSTRAINT `fk_Compte exterieur_Utilisateur1`
    FOREIGN KEY (`id_user`)
    REFERENCES `P6_DB_TEST`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`External_movements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`External_movements` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`External_movements` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movement_type` VARCHAR(15) NULL,
  `issuer` INT NULL,
  `beneficiary` INT NULL,
  `date_movement` DATE NULL,
  `amount` DECIMAL(15,2) NULL,
  `description` VARCHAR(100) NULL,
  `id_account` INT NULL,
  `id_external_account` INT NULL,
  INDEX `fk_Mouvements exterieurs_Compte1_idx` (`id_account` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_Mouvements exterieurs_Compte exterieur1_idx` (`id_external_account` ASC) VISIBLE,
  CONSTRAINT `fk_Mouvements exterieurs_Compte1`
    FOREIGN KEY (`id_account`)
    REFERENCES `P6_DB_TEST`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mouvements exterieurs_Compte exterieur1`
    FOREIGN KEY (`id_external_account`)
    REFERENCES `P6_DB_TEST`.`External_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`Movements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`Movements` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`Movements` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `movement_type` VARCHAR(15) NULL,
  `issuer` INT NOT NULL,
  `beneficiary` INT NOT NULL,
  `date_movement` DATETIME NULL,
  `amount` DECIMAL(12,2) NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Movements_Account1_idx` (`issuer` ASC) VISIBLE,
  INDEX `fk_Movements_Account2_idx` (`beneficiary` ASC) VISIBLE,
  CONSTRAINT `fk_Movements_Account1`
    FOREIGN KEY (`issuer`)
    REFERENCES `P6_DB_TEST`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movements_Account2`
    FOREIGN KEY (`beneficiary`)
    REFERENCES `P6_DB_TEST`.`Account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`Fees`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`Fees` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`Fees` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `id_movement` INT NULL,
  `id_external_movements` INT NULL,
  `fees_type` VARCHAR(20) NULL,
  `rate` DECIMAL(6,4) NULL,
  `fees_amount` DECIMAL(12,2) NULL,
  INDEX `fk_Frais_Mouvements1_idx` (`id_movement` ASC) VISIBLE,
  PRIMARY KEY (`Id`),
  INDEX `fk_Fees_External_movements1_idx` (`id_external_movements` ASC) VISIBLE,
  CONSTRAINT `fk_Frais_Mouvements1`
    FOREIGN KEY (`id_movement`)
    REFERENCES `P6_DB_TEST`.`Movements` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Fees_External_movements1`
    FOREIGN KEY (`id_external_movements`)
    REFERENCES `P6_DB_TEST`.`External_movements` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `P6_DB_TEST`.`Relation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `P6_DB_TEST`.`Relation` ;

CREATE TABLE IF NOT EXISTS `P6_DB_TEST`.`Relation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_user` INT NOT NULL,
  `id_user_buddy` INT NOT NULL,
  `date_beginning` DATE NULL,
  `date_end` DATE NULL,
  INDEX `fk_Relation_Utilisateur1_idx` (`id_user` ASC) VISIBLE,
  INDEX `fk_Relation_Utilisateur2_idx` (`id_user_buddy` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Relation_Utilisateur1`
    FOREIGN KEY (`id_user`)
    REFERENCES `P6_DB_TEST`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Relation_Utilisateur2`
    FOREIGN KEY (`id_user_buddy`)
    REFERENCES `P6_DB_TEST`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
