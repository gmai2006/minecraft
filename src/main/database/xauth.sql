#ACCOUNT("accounts"),  LOCATION("locations"),  LOCKOUT("lockouts"),  PLAYERDATA("playerdata"),  SESSION("sessions"),  PLAYERSTORAGE("playerstorage");
GRANT ALL PRIVILEGES ON xauth.* TO 'xauth'@'%' IDENTIFIED BY 'xauth123$$' WITH GRANT OPTION;

CREATE TABLE `accounts` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`playername` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`pwtype` TINYINT(2) UNSIGNED NOT NULL DEFAULT 0,
	`email` VARCHAR(100) NULL,
	`registerdate` DATETIME NULL,
	`registerip` CHAR(45) NULL,
	`lastlogindate` DATETIME NULL,
	`lastloginip` CHAR(45) NULL,
	`active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`)
);


ALTER TABLE `accounts` ADD COLUMN `resetpw` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `active`;
ALTER TABLE `accounts` ADD COLUMN `premium` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `resetpw`;

CREATE TABLE IF NOT EXISTS `playerdata` (
	`playername` VARCHAR(255) NOT NULL,
	`items` TEXT NOT NULL,
	`armor` TEXT NOT NULL,
	`location` TEXT NULL,
	PRIMARY KEY (`playername`)
);

ALTER TABLE `playerdata` ADD COLUMN `potioneffects` TEXT NULL;
ALTER TABLE `playerdata` MODIFY `items` TEXT NULL;
ALTER TABLE `playerdata` MODIFY `armor` TEXT NULL;
ALTER TABLE `playerdata` ADD COLUMN `fireticks` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `playerdata` ADD COLUMN `remainingair` SMALLINT NOT NULL DEFAULT 300;
ALTER TABLE `playerdata` ADD COLUMN `gamemode` VARCHAR(10) NOT NULL DEFAULT 'ADVENTURE';

CREATE TABLE IF NOT EXISTS `sessions` (
	`accountid` INT UNSIGNED NOT NULL,
	`ipaddress` CHAR(45) NOT NULL,
	`logintime` DATETIME NOT NULL,
	PRIMARY KEY(`accountid`),
	FOREIGN KEY (`accountid`) REFERENCES `accounts`(`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `locations` (
	`uid` VARCHAR(36) NOT NULL,
	`x` DOUBLE NOT NULL,
	`y` DOUBLE NOT NULL,
	`z` DOUBLE NOT NULL,
	`yaw` FLOAT NOT NULL,
	`pitch` FLOAT NOT NULL,
	`global` TINYINT(1) NOT NULL DEFAULT 0,
	PRIMARY KEY(`uid`)
);

CREATE TABLE IF NOT EXISTS `lockouts` (
	`ipaddress` VARCHAR(45) NOT NULL,
	`playername` VARCHAR(255) NOT NULL,
	`time` DATETIME NOT NULL,
	PRIMARY KEY (`ipaddress`)
);

ALTER TABLE `lockouts` ADD COLUMN `id` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST, DROP PRIMARY KEY, ADD PRIMARY KEY (`id`);