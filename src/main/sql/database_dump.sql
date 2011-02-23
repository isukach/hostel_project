-- MySQL dump 10.11
--
-- Host: localhost    Database: hostel_project
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

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

DROP TABLE IF EXISTS `app_user`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `address` varchar(150) default NULL,
  `city` varchar(50) default NULL,
  `country` varchar(100) default NULL,
  `postal_code` varchar(15) default NULL,
  `province` varchar(100) default NULL,
  `credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `account_enabled` bit(1) default NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(255) default NULL,
  `phone_number` varchar(255) default NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) default NULL,
  `website` varchar(255) default NULL,
  `hostel_floor` int(11) default NULL,
  `hostel_room` int(11) default NULL,
  `university_group` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (-3,'\0','\0','','Minsk','BE','47760','MP','\0','to_starosta@tut.by','','Starosta','Starosta','62832393ae85b23d37566095fba817f7c4bcd908','O_O','','starosta',2,'http://starosto.com',8,808,NULL),(-2,'\0','\0','','Minsk','????????','80210','CO','\0','to_admin@tut.by','','Admin','Admin','d033e22ae348aeb5660fc2140aec35850c4da997','=)','','admin',1,'http://adminko.com',8,808,NULL),(-1,'\0','\0','','Minsk','????????','47760','CO','\0','to_echO@tut.by','','Andrei','Mukhin','8ca2fcb549a98cd2706904c7d8af77d8c23dd10f','(:','','Cheshire',1,'http://cheshire.apache.org',8,808,NULL),(3,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_admiknko@tut.by','','adminko','adminko','d033e22ae348aeb5660fc2140aec35850c4da997','=)','','adminko',0,'',8,808,NULL),(4,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminreko@tut.by','','adminko','adminko','1','=)','','1',0,'',8,808,NULL),(5,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminkoasd@tut.by','','adminko','kefa','62832393ae85b23d37566095fba817f7c4bcd908','=)','','kefa',0,'',8,808,NULL),(6,'\0','\0','Y.Kolasa 28','Minsk','BY','777777','Minsk','\0','to_adminkasoasd@tut.by','','aa','aa','62832393ae85b23d37566095fba817f7c4bcd908','=)','','aa',0,'',8,808,NULL),(7,'\0','\0','hello','1111','VN','111111','1111','\0','111@111.eu','','111','111','6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2','111','','111',0,'',11,1111,'111111');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `day_duty`
--

DROP TABLE IF EXISTS `day_duty`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `day_duty` (
  `id` bigint(20) NOT NULL auto_increment,
  `date` timestamp NOT NULL,
  `floor` int(11) default NULL,
  `first_user` bigint(20) default NULL,
  `first_user_location` bigint(20) default NULL,
  `second_user` bigint(20) default NULL,
  `second_user_location` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK73165199D9BEBFC` (`second_user`),
  KEY `FK73165199F433E340` (`first_user`),
  KEY `FK7316519986508E3E` (`second_user_location`),
  KEY `FK73165199D940837A` (`first_user_location`),
  KEY `FK731651992BBD901` (`second_user`),
  KEY `FK73165199E953D045` (`first_user`),
  CONSTRAINT `FK73165199E953D045` FOREIGN KEY (`first_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK731651992BBD901` FOREIGN KEY (`second_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK7316519986508E3E` FOREIGN KEY (`second_user_location`) REFERENCES `user_location` (`id`),
  CONSTRAINT `FK73165199D940837A` FOREIGN KEY (`first_user_location`) REFERENCES `user_location` (`id`),
  CONSTRAINT `FK73165199D9BEBFC` FOREIGN KEY (`second_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK73165199F433E340` FOREIGN KEY (`first_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `day_duty`
--

LOCK TABLES `day_duty` WRITE;
/*!40000 ALTER TABLE `day_duty` DISABLE KEYS */;
--INSERT INTO `day_duty` VALUES (1,'2010-03-01 00:00:00',8,-2,2,NULL,NULL),(2,'2010-03-03 00:00:00',8,-2,2,NULL,NULL),(3,'2010-09-01 00:00:00',8,-2,2,-2,2),(4,'2010-10-01 00:00:00',8,-2,2,-2,2),(5,'2010-10-02 00:00:00',8,-2,2,-2,2),(6,'2010-09-02 00:00:00',8,-2,2,-2,2),(7,'2010-10-03 00:00:00',8,-2,2,-2,2),(8,'2010-09-04 00:00:00',8,-2,2,-2,2),(9,'2010-09-06 00:00:00',8,-2,2,-2,2),(10,'2010-09-05 00:00:00',8,-2,2,-2,2),(11,'2010-09-07 00:00:00',8,-2,2,-2,2),(12,'2010-09-03 00:00:00',8,-2,2,-2,2),(13,'2010-10-21 00:00:00',8,-2,2,NULL,NULL),(14,'2010-10-08 00:00:00',8,-2,2,-2,2),(15,'2010-10-09 00:00:00',8,-2,2,-2,2),(16,'2010-09-13 00:00:00',8,-2,2,NULL,NULL),(17,'2010-10-15 00:00:00',8,-2,2,NULL,NULL),(18,'2010-09-09 00:00:00',8,-2,2,NULL,NULL),(19,'2010-10-04 00:00:00',8,-2,2,-2,2),(20,'2010-10-05 00:00:00',8,-2,2,-2,2),(21,'2010-10-06 00:00:00',8,-2,2,-2,2),(22,'2010-10-07 00:00:00',8,-2,2,-2,2),(23,'2010-11-14 00:00:00',8,-2,2,NULL,NULL),(24,'2010-11-05 00:00:00',8,NULL,NULL,-2,2),(25,'2011-02-01 00:00:00',8,-2,2,4,NULL),(26,'2011-02-02 00:00:00',8,-2,2,-2,2),(27,'2011-02-05 00:00:00',8,-2,2,NULL,NULL);
/*!40000 ALTER TABLE `day_duty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duty_month`
--

DROP TABLE IF EXISTS `duty_month`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `duty_month` (
  `id` bigint(20) NOT NULL auto_increment,
  `available` bit(1) default NULL,
  `floor` int(11) default NULL,
  `month` int(11) default NULL,
  `year` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `duty_month`
--

LOCK TABLES `duty_month` WRITE;
/*!40000 ALTER TABLE `duty_month` DISABLE KEYS */;
INSERT INTO `duty_month` VALUES (1,'',8,8,2010),(2,'',8,9,2010),(3,'',8,1,2011);
/*!40000 ALTER TABLE `duty_month` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL auto_increment,
  `description` varchar(64) default NULL,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

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
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_location` (
  `id` bigint(20) NOT NULL auto_increment,
  `floor` int(11) default NULL,
  `room` varchar(255) default NULL,
  `univeristy_group` varchar(255) default NULL,
  `user_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK7452F9A9F503D155` (`user_id`),
  KEY `FK7452F9A9EA23BE5A` (`user_id`),
  CONSTRAINT `FK7452F9A9EA23BE5A` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK7452F9A9F503D155` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `user_location`
--

LOCK TABLES `user_location` WRITE;
/*!40000 ALTER TABLE `user_location` DISABLE KEYS */;
INSERT INTO `user_location` VALUES (1,8,'803','722401',-3),(2,8,'806','721702',-2);
/*!40000 ALTER TABLE `user_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FK143BF46A4FD90D75` (`role_id`),
  KEY `FK143BF46AF503D155` (`user_id`),
  KEY `FK143BF46A44F8FA7A` (`role_id`),
  KEY `FK143BF46AEA23BE5A` (`user_id`),
  CONSTRAINT `FK143BF46AEA23BE5A` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK143BF46A44F8FA7A` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46A4FD90D75` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46AF503D155` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (-1,-3),(-3,-2),(-2,-1),(7,-1);
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

-- Dump completed on 2011-02-20 21:55:00
