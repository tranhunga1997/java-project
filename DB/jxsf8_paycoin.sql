-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 103.200.20.95    Database: jxaccount
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Table structure for table `jxsf8_paycoin`
--

DROP TABLE IF EXISTS `jxsf8_paycoin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jxsf8_paycoin` (
  `accid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `jbcoin` int(11) unsigned NOT NULL DEFAULT '0',
  `CoinCardKey` varchar(50) DEFAULT NULL,
  `mathe` varchar(20) DEFAULT NULL,
  `loaithe` varchar(10) DEFAULT NULL,
  `menhgia` int(11) DEFAULT NULL,
  PRIMARY KEY (`accid`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jxsf8_paycoin`
--

LOCK TABLES `jxsf8_paycoin` WRITE;
/*!40000 ALTER TABLE `jxsf8_paycoin` DISABLE KEYS */;
/*!40000 ALTER TABLE `jxsf8_paycoin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-03 17:00:04
