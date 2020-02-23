-- --------------------------------------------------------
-- Värd:                         127.0.0.1
-- Serverversion:                5.7.29-log - MySQL Community Server (GPL)
-- Server-OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumpar databasstruktur för holidaymaker
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci */;
USE `holidaymaker`;

-- Dumpar struktur för tabell holidaymaker.cities
CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(255) NOT NULL,
  `position` point DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

-- Dumpar struktur för tabell holidaymaker.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) DEFAULT NULL,
  `room` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customers_rooms` (`room`),
  CONSTRAINT `FK_customers_rooms` FOREIGN KEY (`room`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

-- Dumpar struktur för tabell holidaymaker.group_of_one_travelers
CREATE TABLE IF NOT EXISTS `group_of_one_travelers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

-- Dumpar struktur för tabell holidaymaker.positions
CREATE TABLE IF NOT EXISTS `positions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pos_lat` double NOT NULL,
  `pos_long` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

-- Dumpar struktur för tabell holidaymaker.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city` int(11) unsigned DEFAULT NULL,
  `room_size` int(11) unsigned NOT NULL,
  `booking_start` datetime DEFAULT NULL,
  `booking_end` datetime DEFAULT NULL,
  `max_amount_of_customer` int(10) unsigned NOT NULL,
  `facilities_restaurant` tinyint(1) unsigned NOT NULL,
  `facilities_pool` tinyint(1) unsigned NOT NULL,
  `facilities_evening_entertainment` tinyint(1) unsigned NOT NULL,
  `facilities_children_club` tinyint(1) unsigned NOT NULL,
  `additional_services_board_half` tinyint(1) unsigned NOT NULL,
  `additional_services_board_full` tinyint(1) unsigned NOT NULL,
  `additional_services_extra_bed` tinyint(1) unsigned NOT NULL,
  `price_per_night` double(5,2) unsigned NOT NULL,
  `rating` double(5,1) unsigned NOT NULL,
  `distance_to_city` double(5,1) unsigned NOT NULL,
  `distance_to_beach` double(5,1) unsigned NOT NULL,
  `availability` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

-- Dumpar struktur för tabell holidaymaker.travelers
CREATE TABLE IF NOT EXISTS `travelers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `person01` int(11) unsigned NOT NULL DEFAULT '0',
  `person02` int(11) unsigned NOT NULL DEFAULT '0',
  `person03` int(11) unsigned NOT NULL DEFAULT '0',
  `person04` int(11) unsigned NOT NULL,
  `person05` int(11) unsigned NOT NULL,
  `person06` int(11) unsigned DEFAULT NULL,
  `person07` int(11) unsigned DEFAULT NULL,
  `person08` int(11) unsigned DEFAULT NULL,
  `person09` int(11) unsigned DEFAULT NULL,
  `person10` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customer_groups_customers_7` (`person08`),
  KEY `FK_customer_groups_customers_8` (`person09`),
  KEY `FK_customer_groups_customers_9` (`person03`),
  KEY `FK_customer_groups_customers` (`person01`),
  KEY `FK_customer_groups_customers_10` (`person10`),
  KEY `FK_customer_groups_customers_2` (`person02`),
  KEY `FK_customer_groups_customers_3` (`person04`),
  KEY `FK_customer_groups_customers_4` (`person05`),
  KEY `FK_customer_groups_customers_5` (`person06`),
  KEY `FK_customer_groups_customers_6` (`person07`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Dataexport var bortvalt.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
