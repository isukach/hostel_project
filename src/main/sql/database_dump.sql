-- MySQL dump 10.13  Distrib 5.1.39, for Win32 (ia32)
--
-- Host: localhost    Database: hostel_project
-- ------------------------------------------------------
-- Server version	5.1.39-community

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
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `account_enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (-3,'\0','\0','','Minsk','????????','47760','MP','\0','to_starosta@tut.by','','Starosta','Starosta','62832393ae85b23d37566095fba817f7c4bcd908','O_O','','starosta',1,'http://starosto.com'),(-2,'\0','\0','','Minsk','????????','80210','CO','\0','to_admin@tut.by','','Admin','Admin','d033e22ae348aeb5660fc2140aec35850c4da997','=)','','admin',1,'http://adminko.com'),(-1,'\0','\0','','Minsk','????????','47760','CO','\0','to_echO@tut.by','','Andrei','Mukhin','8ca2fcb549a98cd2706904c7d8af77d8c23dd10f','(:','','Cheshire',1,'http://cheshire.apache.org'),(3,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_admiknko@tut.by','','adminko','adminko','d033e22ae348aeb5660fc2140aec35850c4da997','=)','','adminko',0,''),(4,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminreko@tut.by','','adminko','adminko','1','=)','','1',0,''),(5,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminkoasd@tut.by','','adminko','kefa','62832393ae85b23d37566095fba817f7c4bcd908','=)','','kefa',0,''),(6,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminkasoasd@tut.by','','aa','aa','62832393ae85b23d37566095fba817f7c4bcd908','=)','','aa',0,'');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `day_duty`
--

DROP TABLE IF EXISTS `day_duty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `day_duty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `floor` int(11) DEFAULT NULL,
  `first_user` bigint(20) DEFAULT NULL,
  `first_user_location` bigint(20) DEFAULT NULL,
  `second_user` bigint(20) DEFAULT NULL,
  `second_user_location` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK73165199D9BEBFC` (`second_user`),
  KEY `FK73165199F433E340` (`first_user`),
  KEY `FK7316519986508E3E` (`second_user_location`),
  KEY `FK73165199D940837A` (`first_user_location`),
  CONSTRAINT `FK7316519986508E3E` FOREIGN KEY (`second_user_location`) REFERENCES `user_location` (`id`),
  CONSTRAINT `FK73165199D940837A` FOREIGN KEY (`first_user_location`) REFERENCES `user_location` (`id`),
  CONSTRAINT `FK73165199D9BEBFC` FOREIGN KEY (`second_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK73165199F433E340` FOREIGN KEY (`first_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `day_duty`
--

LOCK TABLES `day_duty` WRITE;
/*!40000 ALTER TABLE `day_duty` DISABLE KEYS */;
INSERT INTO `day_duty` VALUES (1,'2010-03-01 00:00:00',8,-2,2,NULL,NULL),(2,'2010-03-03 00:00:00',8,-2,2,NULL,NULL),(3,'2010-09-01 00:00:00',8,-2,2,-2,2),(4,'2010-10-01 00:00:00',8,-2,2,-2,2),(5,'2010-10-02 00:00:00',8,-2,2,-2,2),(6,'2010-09-02 00:00:00',8,-2,2,-2,2),(7,'2010-10-03 00:00:00',8,-2,2,-2,2),(8,'2010-09-04 00:00:00',8,-2,2,-2,2),(9,'2010-09-06 00:00:00',8,-2,2,-2,2),(10,'2010-09-05 00:00:00',8,-2,2,-2,2),(11,'2010-09-07 00:00:00',8,-2,2,-2,2),(12,'2010-09-03 00:00:00',8,-2,2,-2,2),(13,'2010-10-21 00:00:00',8,-2,2,NULL,NULL),(14,'2010-10-08 00:00:00',8,-2,2,-2,2),(15,'2010-10-09 00:00:00',8,-2,2,-2,2),(16,'2010-09-13 00:00:00',8,-2,2,NULL,NULL),(17,'2010-10-15 00:00:00',8,-2,2,NULL,NULL),(18,'2010-09-09 00:00:00',8,-2,2,NULL,NULL),(19,'2010-10-04 00:00:00',8,-2,2,-2,2),(20,'2010-10-05 00:00:00',8,-2,2,-2,2),(21,'2010-10-06 00:00:00',8,-2,2,-2,2),(22,'2010-10-07 00:00:00',8,-2,2,-2,2),(23,'2010-11-14 00:00:00',8,-2,2,NULL,NULL),(24,'2010-11-05 00:00:00',8,NULL,NULL,-2,2);
/*!40000 ALTER TABLE `day_duty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duty_month`
--

DROP TABLE IF EXISTS `duty_month`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `duty_month` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duty_month`
--

LOCK TABLES `duty_month` WRITE;
/*!40000 ALTER TABLE `duty_month` DISABLE KEYS */;
INSERT INTO `duty_month` VALUES (1,'',8,8,2010),(2,'',8,9,2010);
/*!40000 ALTER TABLE `duty_month` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(64) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (-3,'Administrator role (can edit Users and Duties)','ROLE_ADMIN'),(-2,'Starosta role (can edit Duties)','ROLE_STAROSTA'),(-1,'Default role for all Users','ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_location`
--

DROP TABLE IF EXISTS `user_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `floor` int(11) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `univeristy_group` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7452F9A9F503D155` (`user_id`),
  CONSTRAINT `FK7452F9A9F503D155` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_location`
--

LOCK TABLES `user_location` WRITE;
/*!40000 ALTER TABLE `user_location` DISABLE KEYS */;
INSERT INTO `user_location` VALUES (1,8,'803','722402',-3),(2,8,'806','721702',-2);
/*!40000 ALTER TABLE `user_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK143BF46A4FD90D75` (`role_id`),
  KEY `FK143BF46AF503D155` (`user_id`),
  CONSTRAINT `FK143BF46A4FD90D75` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46AF503D155` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (-1,-3),(-3,-2),(-2,-1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-01-12 21:47:05
