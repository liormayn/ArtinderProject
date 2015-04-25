-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.24-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table artinder.articleclassification
DROP TABLE IF EXISTS `articleclassification`;
CREATE TABLE IF NOT EXISTS `articleclassification` (
  `UserId` int(11) NOT NULL,
  `ArticleId` int(11) NOT NULL,
  `Category` int(11) NOT NULL,
  `Classification` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserId`,`ArticleId`),
  KEY `ArticleId` (`ArticleId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `ArticleId` FOREIGN KEY (`ArticleId`) REFERENCES `articles` (`ArticleId`),
  CONSTRAINT `UserId` FOREIGN KEY (`UserId`) REFERENCES `users` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table artinder.articleclassification: ~0 rows (approximately)
/*!40000 ALTER TABLE `articleclassification` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleclassification` ENABLE KEYS */;


-- Dumping structure for table artinder.articles
DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
  `ArticleId` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(50) NOT NULL DEFAULT '0',
  `Source` varchar(300) NOT NULL DEFAULT '0',
  `Content` text NOT NULL,
  PRIMARY KEY (`ArticleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table artinder.articles: ~0 rows (approximately)
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;


-- Dumping structure for table artinder.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL DEFAULT '0',
  `Password` char(128) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table artinder.users: ~0 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
