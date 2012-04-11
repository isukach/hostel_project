-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.41-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema hostel_project
--

CREATE DATABASE IF NOT EXISTS hostel_project;
USE hostel_project;

--
-- Definition of table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `hostel_floor` int(11) NOT NULL,
  `hostel_room` int(11) NOT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `account_enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `university_group` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `study_pay` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_user`
--

/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` (`id`,`account_expired`,`account_locked`,`hostel_floor`,`hostel_room`,`credentials_expired`,`account_enabled`,`first_name`,`last_name`,`middle_name`,`password`,`university_group`,`username`,`version`,`date_of_birth`,`email`,`phone_number`,`department`,`study_pay`) VALUES 
 (1,'\0','\0',8,806,'\0','','Starosta','Starosta','Starosta','62832393ae85b23d37566095fba817f7c4bcd908','721702','starosta',1,NULL,NULL,NULL,NULL,''),
 (2,'\0','\0',8,806,'\0','\0','Andrei','Muhin','Aleksandrovich','62832393ae85b23d37566095fba817f7c4bcd908','721702','muh',1,NULL,NULL,NULL,NULL,''),
 (3,'\0','\0',8,1231,'\0','','Asdas','ASdasd','asd','62832393ae85b23d37566095fba817f7c4bcd908','123131','1231asdasdaa',0,'1980-08-18 23:29:51','asda@ryr.by','',NULL,'');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;


--
-- Definition of table `day_duty`
--

DROP TABLE IF EXISTS `day_duty`;
CREATE TABLE `day_duty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `floor` int(11) DEFAULT NULL,
  `first_user` bigint(20) DEFAULT NULL,
  `second_user` bigint(20) DEFAULT NULL,
  `first_user_remarks` varchar(255) DEFAULT NULL,
  `second_user_remarks` varchar(255) DEFAULT NULL,
  `first_user_punishment` varchar(255) DEFAULT NULL,
  `second_user_punishment` varchar(255) DEFAULT NULL,
  `isOwnFirstDuty` bit(1) DEFAULT NULL,
  `isOwnSecondDuty` bit(1) DEFAULT NULL,
  `own_second_duty` char(1) DEFAULT NULL,
  `own_first_duty` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK731651992BBD901` (`second_user`),
  KEY `FK73165199E953D045` (`first_user`),
  CONSTRAINT `FK731651992BBD901` FOREIGN KEY (`second_user`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK73165199E953D045` FOREIGN KEY (`first_user`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `day_duty`
--

/*!40000 ALTER TABLE `day_duty` DISABLE KEYS */;
INSERT INTO `day_duty` (`id`,`date`,`floor`,`first_user`,`second_user`,`first_user_remarks`,`second_user_remarks`,`first_user_punishment`,`second_user_punishment`,`isOwnFirstDuty`,`isOwnSecondDuty`,`own_second_duty`,`own_first_duty`) VALUES 
 (9,'2011-09-01 00:00:00',8,3,3,'AAAAAAAAAAAAAAAAAAAAAAAAAAA','HUI',NULL,NULL,NULL,NULL,NULL,NULL),
 (10,'2011-09-03 00:00:00',8,3,3,NULL,'sdfdsf; GGGG; Pidaras blyad',NULL,NULL,NULL,NULL,NULL,NULL),
 (11,'2011-09-02 00:00:00',8,2,2,'Pidor Ebuchiy; TY HUI~!~~~!!!111',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (12,'2011-09-04 00:00:00',8,2,2,'sdfasdfsdfsdf; as!!!; BLYA',NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (13,'2011-09-05 00:00:00',8,2,2,'reterter','Huilo!',NULL,NULL,NULL,NULL,NULL,NULL),
 (14,'2011-09-06 00:00:00',8,3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (15,'2011-09-07 00:00:00',8,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (16,'2016-08-13 00:00:00',8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
 (17,'2016-08-07 00:00:00',8,2,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,NULL),
 (18,'2016-08-01 00:00:00',8,3,2,NULL,NULL,NULL,NULL,'\0','\0',NULL,NULL);
INSERT INTO `day_duty` (`id`,`date`,`floor`,`first_user`,`second_user`,`first_user_remarks`,`second_user_remarks`,`first_user_punishment`,`second_user_punishment`,`isOwnFirstDuty`,`isOwnSecondDuty`,`own_second_duty`,`own_first_duty`) VALUES 
 (19,'2016-08-02 00:00:00',8,2,2,NULL,NULL,NULL,NULL,'\0','\0',NULL,NULL),
 (20,'2016-08-03 00:00:00',8,2,3,NULL,NULL,NULL,NULL,'\0','\0',NULL,NULL),
 (21,'2016-08-04 00:00:00',8,2,3,NULL,NULL,NULL,NULL,'\0','\0',NULL,NULL),
 (22,'2016-08-05 00:00:00',8,3,3,NULL,NULL,NULL,NULL,'\0','\0',NULL,NULL),
 (23,'2012-03-01 00:00:00',8,3,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,NULL),
 (24,'2012-07-01 00:00:00',8,2,NULL,NULL,NULL,NULL,NULL,'\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `day_duty` ENABLE KEYS */;


--
-- Definition of table `duty_month`
--

DROP TABLE IF EXISTS `duty_month`;
CREATE TABLE `duty_month` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `duty_month`
--

/*!40000 ALTER TABLE `duty_month` DISABLE KEYS */;
INSERT INTO `duty_month` (`id`,`available`,`floor`,`month`,`year`) VALUES 
 (1,'',8,0,2011),
 (2,'',8,11,2011);
/*!40000 ALTER TABLE `duty_month` ENABLE KEYS */;


--
-- Definition of table `duty_remark`
--

DROP TABLE IF EXISTS `duty_remark`;
CREATE TABLE `duty_remark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shift` int(11) DEFAULT NULL,
  `day_duty` bigint(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8111C90974E321AB` (`day_duty`),
  CONSTRAINT `FK8111C90974E321AB` FOREIGN KEY (`day_duty`) REFERENCES `day_duty` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duty_remark`
--

/*!40000 ALTER TABLE `duty_remark` DISABLE KEYS */;
INSERT INTO `duty_remark` (`id`,`shift`,`day_duty`,`remark`) VALUES 
 (1,2,10,'?? ??? ???. ????? - ?????? ????!!'),
 (2,2,10,'Ti pidor ebuchii'),
 (3,2,13,'3454353dfgdfgdfgd'),
 (4,2,13,'345324523452'),
 (5,2,12,'AAAAAAAAAAAAAAA'),
 (6,2,11,'2423423'),
 (7,1,12,'prosto hui'),
 (8,1,12,'prosto hui'),
 (9,1,10,'4563456'),
 (10,1,9,'56'),
 (11,1,10,'23423423'),
 (12,2,10,'234234234234'),
 (13,2,10,'wfsdfsdfsdfsdf'),
 (14,1,14,'sadfasdfa'),
 (15,1,15,'kefa'),
 (16,1,11,'asdasdas'),
 (17,1,13,'pidor ebuchi'),
 (18,1,10,'erterddd'),
 (19,1,10,'URA!'),
 (20,1,11,'sadasdas'),
 (21,1,12,'pizdec'),
 (22,1,10,'?? ??? ??????!!!!'),
 (23,2,9,'eteter'),
 (24,2,12,'ertreterte');
/*!40000 ALTER TABLE `duty_remark` ENABLE KEYS */;


--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(64) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`,`description`,`name`) VALUES 
 (-4,'Super admin role','ROLE_SUPER_ADMIN'),
 (-3,'Administrator role (can edit Users and Duties)','ROLE_ADMIN'),
 (-2,'Starosta role (can edit Duties)','ROLE_STAROSTA'),
 (-1,'Default role for all Users','ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK143BF46A44F8FA7A` (`role_id`),
  KEY `FK143BF46AEA23BE5A` (`user_id`),
  CONSTRAINT `FK143BF46A44F8FA7A` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46AEA23BE5A` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (1,-4),
 (2,-1),
 (3,-1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;


--
-- Definition of table `work_unit`
--

DROP TABLE IF EXISTS `work_unit`;
CREATE TABLE `work_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `hours_amount` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `employee` bigint(20) DEFAULT NULL,
  `employer` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41008DF23A0BB6F9` (`employee`),
  KEY `FK41008DF23A0BB706` (`employer`),
  CONSTRAINT `FK41008DF23A0BB6F9` FOREIGN KEY (`employee`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK41008DF23A0BB706` FOREIGN KEY (`employer`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `work_unit`
--



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
