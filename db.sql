-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: mysql
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `dname` varchar(30) NOT NULL,
  `dtel` varchar(30) NOT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Разработка','8-017-1234567'),(2,'Тестирование','8-017-7654321'),(3,'Сопровождение','8-017-4321567'),(4,'Менеджмент','8-017-1112233'),(5,'Аналитика','8-017-3322111');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `department_id` int NOT NULL,
  `position_id` int NOT NULL,
  `comp_id` int NOT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `department_id` (`department_id`),
  KEY `position_id` (`position_id`),
  KEY `comp_id` (`comp_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `position` (`position_id`),
  CONSTRAINT `employee_ibfk_3` FOREIGN KEY (`comp_id`) REFERENCES `equipment` (`comp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'root','Pupkin','Vasiliy','toor',4,13,1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `comp_id` int NOT NULL AUTO_INCREMENT,
  `os` varchar(30) NOT NULL,
  `service_life` varchar(30) NOT NULL,
  `employee_id` int DEFAULT NULL,
  PRIMARY KEY (`comp_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `equipment_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,'Windows','10 лет',1),(2,'MacOS','10 лет',NULL),(3,'Linux','10 лет',NULL),(4,'Linux','10 лет',NULL),(5,'Linux','10 лет',NULL),(6,'Linux','10 лет',NULL),(7,'Linux','10 лет',NULL),(8,'MacOS','10 лет',NULL),(9,'Windows','10 лет',NULL),(10,'Linux','10 лет',NULL);
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `position_id` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(30) NOT NULL,
  `qualification` varchar(30) NOT NULL,
  `work_experience` varchar(30) NOT NULL,
  `basics_of_algorithms` tinyint(1) DEFAULT NULL,
  `backend_language` tinyint(1) DEFAULT NULL,
  `frontend_language` tinyint(1) DEFAULT NULL,
  `testing_basics` tinyint(1) DEFAULT NULL,
  `framework` tinyint(1) DEFAULT NULL,
  `deep_tech_knowledge` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,'Front-end разработчик','junior','отсутствует',1,0,1,0,0,0),(2,'Front-end разработчик','middle','2+ года',1,0,1,1,1,0),(3,'Front-end разработчик','senior','4+ года',1,0,1,1,1,1),(4,'Back-end разработчик','junior','отсутствует',1,1,0,0,0,0),(5,'Back-end разработчик','middle','2+ года',1,1,0,1,1,0),(6,'Back-end разработчик','senior','4+ года',1,1,0,1,1,1),(7,'Full-stack разработчик','junior','отсутствует',1,1,1,0,0,0),(8,'Full-stack разработчик','middle','2+ года',1,1,1,1,1,0),(9,'Full-stack разработчик','senior','4+ года',1,1,1,1,1,1),(10,'Тестировщик','junior','отсутствует',1,0,0,1,0,0),(11,'Тестировщик','middle','2+ года',1,0,0,1,0,0),(12,'Тестировщик','senior','4+ года',1,0,0,1,1,1),(13,'Менеджер','высшее образование','3+ года',NULL,NULL,NULL,NULL,NULL,NULL),(14,'Аналитик','высшее образование','2+ года',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statistics`
--

DROP TABLE IF EXISTS `statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statistics` (
  `stat_id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(30) NOT NULL,
  `app_time` varchar(30) NOT NULL,
  `comp_id` int NOT NULL,
  `app_date` varchar(30) NOT NULL,
  PRIMARY KEY (`stat_id`),
  KEY `comp_id` (`comp_id`),
  CONSTRAINT `statistics_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `equipment` (`comp_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statistics`
--

LOCK TABLES `statistics` WRITE;
/*!40000 ALTER TABLE `statistics` DISABLE KEYS */;
INSERT INTO `statistics` VALUES (32,'ApplicationFrameHost','6901072',1,'04122022'),(33,'idea64              ','6901072',1,'04122022'),(38,'ApplicationFrameHost','48517',1,'04122022'),(39,'idea64       ','189120',1,'06122022'),(40,'java         ','189124',1,'06122022'),(41,'ApplicationFrameHost','11163',1,'11122022'),(43,'ApplicationFrameHost','257527',1,'11122022'),(44,'ApplicationFrameHost','1892',1,'11122022'),(51,'ApplicationFrameHost','264817',1,'12122022'),(52,'idea64              ','264821',1,'12122022');
/*!40000 ALTER TABLE `statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `general_log` (
  `event_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_host` mediumtext NOT NULL,
  `thread_id` bigint unsigned NOT NULL,
  `server_id` int unsigned NOT NULL,
  `command_type` varchar(64) NOT NULL,
  `argument` mediumblob NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8mb3 COMMENT='General log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slow_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `slow_log` (
  `start_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_host` mediumtext NOT NULL,
  `query_time` time(6) NOT NULL,
  `lock_time` time(6) NOT NULL,
  `rows_sent` int NOT NULL,
  `rows_examined` int NOT NULL,
  `db` varchar(512) NOT NULL,
  `last_insert_id` int NOT NULL,
  `insert_id` int NOT NULL,
  `server_id` int unsigned NOT NULL,
  `sql_text` mediumblob NOT NULL,
  `thread_id` bigint unsigned NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8mb3 COMMENT='Slow log';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-12  0:30:45
