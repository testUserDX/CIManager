-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cimanagerdb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `org`
--

DROP TABLE IF EXISTS `org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isProduction` tinyint(1) DEFAULT NULL,
  `project_id` bigint(8) NOT NULL,
  `branch_name` varchar(45) NOT NULL,
  `new_column` int(11) DEFAULT NULL,
  `orgLink` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `project_FK_idx` (`project_id`),
  CONSTRAINT `Org_project_FK` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `org`
--

LOCK TABLES `org` WRITE;
/*!40000 ALTER TABLE `org` DISABLE KEYS */;
INSERT INTO `org` VALUES (133,'login1','password1',NULL,141,'master',NULL,'link'),(134,'login2','password2',NULL,142,'branch2',NULL,'link'),(135,'ded','eded',0,144,'wde',NULL,'ded');
/*!40000 ALTER TABLE `org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `git_url` varchar(100) NOT NULL,
  `git_login` varchar(45) DEFAULT NULL,
  `git_pasword` varchar(45) DEFAULT NULL,
  `description` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (141,'testProject-1','https://github.com/testandreytsykh/ciamanager.git','testandreytsykh','ciamanager1',NULL),(142,'testProject-2','git-2-url',NULL,NULL,NULL),(144,'qw','dwd','dede','wd','ded');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=352 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (351,'test-role');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `registrated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` tinyint(1) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_fk_idx` (`role_id`),
  CONSTRAINT `user_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=374 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (306,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(307,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(308,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(309,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(310,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(311,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(312,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(313,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(314,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(315,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(316,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(317,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(318,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(319,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(320,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(321,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(322,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(323,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(324,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(325,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(326,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(327,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(328,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(329,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(330,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(331,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(332,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(333,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(334,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(335,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(336,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(337,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(338,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(339,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(340,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(341,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(342,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(343,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(344,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(345,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(346,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(347,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(348,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(349,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(350,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(351,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(352,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(353,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(354,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(355,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(356,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(357,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(358,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(359,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(360,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(361,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(362,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(363,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(364,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(365,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(366,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(367,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(368,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(369,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(370,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(371,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(372,'Test name','test login','111',NULL,NULL,'111@111.com',NULL),(373,'Test name','test login','111',NULL,NULL,'111@111.com',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_org`
--

DROP TABLE IF EXISTS `user_has_org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_org` (
  `User_id` bigint(8) NOT NULL,
  `Org_id` bigint(8) NOT NULL,
  PRIMARY KEY (`User_id`,`Org_id`),
  KEY `fk_User_has_Org_Org1_idx` (`Org_id`),
  KEY `fk_User_has_Org_User1_idx` (`User_id`),
  CONSTRAINT `fk_User_has_Org_Org1` FOREIGN KEY (`Org_id`) REFERENCES `org` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Org_User1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_org`
--

LOCK TABLES `user_has_org` WRITE;
/*!40000 ALTER TABLE `user_has_org` DISABLE KEYS */;
INSERT INTO `user_has_org` VALUES (373,133),(373,134),(306,135);
/*!40000 ALTER TABLE `user_has_org` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-14 10:08:11
