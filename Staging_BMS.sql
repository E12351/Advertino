-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.13-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table bmsbackend.permission: 1 rows
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`id`, `alert_config_create`, `alert_config_read`, `alert_config_update`, `dash_board_create`, `dash_board_read`, `dash_board_update`, `reports_create`, `reports_read`, `reports_update`, `entity_id`, `role_id`) VALUES
	(1, 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 10, 3);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- Dumping data for table bmsbackend.role: 8 rows
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `description`, `loc_config_create`, `loc_config_read`, `loc_config_update`, `name`) VALUES
	(0, 'user', b'0', b'0', b'0', 'genaral'),
	(3, 'control entities', b'1', b'1', b'1', 'admin'),
	(4, 'Dialog Manager', b'1', b'1', b'1', 'Dialog Manager'),
	(5, 'Main Building Manager', b'1', b'1', b'1', 'Main Building Manager'),
	(6, 'Kit Building Manager', b'1', b'1', b'1', 'Kit Building Manager'),
	(7, 'Switch Building Manager', b'1', b'1', b'1', 'Switch Building Manager'),
	(8, 'Switch Floor 1 Manager', b'1', b'1', b'1', 'Switch Floor 1 Manager'),
	(9, 'Switch Floor 2 Manager', b'1', b'1', b'1', 'Switch Floor 2 Manager');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping data for table bmsbackend.user: 2 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `created_date`, `email`, `mobile`, `name`, `password`, `remembertoken`, `status`, `type`, `added_admin_id`, `role_id`) VALUES
	(1, '2018-08-23', 'Amila@dialog.lk', 'Amila', 'Indrajith', '164161648', NULL, NULL, NULL, 1, 3),
	(2, '2018-08-23', 'Indrajith@dialog.lk', '0775455047', 'Amila', '0775455047', NULL, NULL, NULL, NULL, 3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
