
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ciManagerDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ciManagerDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ciManagerDB` DEFAULT CHARACTER SET utf8 ;
USE `ciManagerDB` ;

-- -----------------------------------------------------
-- Table `ciManagerDB`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ciManagerDB`.`Project` (
  `id` BIGINT(8) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `git_url` VARCHAR(100) NOT NULL,
  `git_login` VARCHAR(45) NULL,
  `git_pasword` VARCHAR(45) NULL,
  `description` VARCHAR(150) NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ciManagerDB`.`Org`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ciManagerDB`.`Org` (
  `id` BIGINT(8) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isProduction` TINYINT(1) NULL,
  `project_id` BIGINT(8) NOT NULL,
  `branch_name` VARCHAR(45) NOT NULL,
  `branch_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `project_FK_idx` (`project_id` ASC),
  CONSTRAINT `Org_project_FK`
  FOREIGN KEY (`project_id`)
  REFERENCES `ciManagerDB`.`Project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ciManagerDB`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ciManagerDB`.`Role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ciManagerDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ciManagerDB`.`User` (
  `id` BIGINT(8) NOT NULL,
  `name` VARCHAR(45) NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NULL,
  `registrated` TIMESTAMP NULL DEFAULT now(),
  `enabled` TINYINT(1) NULL,
  `email` VARCHAR(45) NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_role_fk_idx` (`role_id` ASC),
  CONSTRAINT `user_role_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `ciManagerDB`.`Role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ciManagerDB`.`User_has_Org`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ciManagerDB`.`User_has_Org` (
  `User_id` BIGINT(8) NOT NULL,
  `Org_id` BIGINT(8) NOT NULL,
  PRIMARY KEY (`User_id`, `Org_id`),
  INDEX `fk_User_has_Org_Org1_idx` (`Org_id` ASC),
  INDEX `fk_User_has_Org_User1_idx` (`User_id` ASC),
  CONSTRAINT `fk_User_has_Org_User1`
  FOREIGN KEY (`User_id`)
  REFERENCES `ciManagerDB`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Org_Org1`
  FOREIGN KEY (`Org_id`)
  REFERENCES `ciManagerDB`.`Org` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
