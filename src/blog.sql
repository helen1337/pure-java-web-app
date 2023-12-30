CREATE TABLE `user` (
                        `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
                        `firstName` VARCHAR(20) NOT NULL COMMENT 'firstName',
                        `lastName` VARCHAR(20) NOT NULL COMMENT 'firstName',
                        `email` VARCHAR(20) NOT NULL COMMENT 'email',
                        `login` VARCHAR(20) NOT NULL COMMENT 'login',
                        `password` VARCHAR(20) NOT NULL COMMENT 'password',
                        `city` VARCHAR(20) NOT NULL COMMENT 'city',
                        PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `article` (
                           `id` INT(11) NOT NULL AUTO_INCREMENT,
                           `title` VARCHAR(80) NOT NULL,
                           `author` VARCHAR(30) NOT NULL,
                           `theme` VARCHAR(30) NOT NULL,
                           `time` date NOT NULL,
                           `content` TEXT,
                           PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
