/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 5.7.34 : Database - itsm_site_audit
*********************************************************************
*/
CREATE
DATABASE /*!32312 IF NOT EXISTS */`makerspace` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE
`makerspace`;


/*Table structure for table `user_type` */

DROP TABLE IF EXISTS `user_type`;

CREATE TABLE `user_type`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) DEFAULT NULL,

    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `user_type`(`name`)
VALUES ('Admin'),
       ('Student'),
       ('Trainer');


/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name`   varchar(100) DEFAULT NULL,
    `last_name`    varchar(100) DEFAULT NULL,
    `username`     varchar(100) DEFAULT NULL,
    `email`        varchar(100) DEFAULT NULL,
    `password`     varchar(100) DEFAULT NULL,
    `profile_url`  text         DEFAULT NULL,
    `is_active`    tinyint(1) DEFAULT NULL,
    `user_type_id` bigint(20) DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY            `fk_user_user_type` (`user_type_id`),
    CONSTRAINT `fk_user_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Table structure for table `labs` */
DROP TABLE IF EXISTS `labs`;
CREATE TABLE `labs`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(100) DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    `start_time`   datetime     DEFAULT NULL,
    `end_time`     datetime     DEFAULT NULL,
    `is_active`    tinyint(1) DEFAULT NULL,
    `image`        text         DEFAULT NULL,
    `time_period`  varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `courses` */
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(100) DEFAULT NULL,
    `description`  text         DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    `start_time`   datetime     DEFAULT NULL,
    `end_time`     datetime     DEFAULT NULL,
    `is_active`    tinyint(1) DEFAULT NULL,
    `time_period`  varchar(100) DEFAULT NULL,
    `course_venue` varchar(255) DEFAULT NULL,
    `status`       varchar(255) DEFAULT NULL,
    `image`        text         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `membership` */
DROP TABLE IF EXISTS `membership`;
CREATE TABLE `membership`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(100) DEFAULT NULL,
    `description`  text         DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    `start_time`   datetime     DEFAULT NULL,
    `end_time`     datetime     DEFAULT NULL,
    `is_active`    tinyint(1) DEFAULT NULL,
    `time_period`  varchar(100) DEFAULT NULL,
    `image`        text         DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `projects` */
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `name`         varchar(100) DEFAULT NULL,
    `description`  text         DEFAULT NULL,
    `url`          text         DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    `is_active`    tinyint(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `course_enrollment` */
DROP TABLE IF EXISTS `course_enrollment`;
CREATE TABLE `course_enrollment`
(
    `id`                 bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`            bigint(20) DEFAULT NULL,
    `course_id`          bigint(20) DEFAULT NULL,
    `start_time`         datetime     DEFAULT NULL,
    `end_time`           datetime     DEFAULT NULL,
    `time_period`        varchar(100) DEFAULT NULL,
    `status`             varchar(100) DEFAULT NULL,
    `earned_certificate` varchar(255) DEFAULT NULL,
    `course_venue`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                  `fk_course_enrollment_user_id` (`user_id`),
    KEY                  `fk_course_enrollment_course_id` (`course_id`),
    CONSTRAINT `fk_course_enrollment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_course_enrollment_course_id` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Table structure for table `labs_booking` */
DROP TABLE IF EXISTS `labs_booking`;
CREATE TABLE `labs_booking`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20) DEFAULT NULL,
    `lab_id`      bigint(20) DEFAULT NULL,
    `start_time`  datetime     DEFAULT NULL,
    `end_time`    datetime     DEFAULT NULL,
    `time_period` varchar(100) DEFAULT NULL,
    `status`      varchar(100) DEFAULT NULL,
    `venue`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `fk_labs_booking_user_id` (`user_id`),
    KEY           `fk_labs_booking_lab_id` (`lab_id`),
    CONSTRAINT `fk_labs_booking_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_labs_booking_lab_id` FOREIGN KEY (`lab_id`) REFERENCES `labs` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `user_membership` */
DROP TABLE IF EXISTS `user_membership`;
CREATE TABLE `user_membership`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`       bigint(20) DEFAULT NULL,
    `membership_id` bigint(20) DEFAULT NULL,
    `start_time`    datetime     DEFAULT NULL,
    `end_time`      datetime     DEFAULT NULL,
    `is_active`     tinyint(1) DEFAULT NULL,
    `time_period`   varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY             `fk_user_membership_user_id` (`user_id`),
    KEY             `fk_user_membership_membership_id` (`membership_id`),
    CONSTRAINT `fk_user_membership_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_membership_membership_id` FOREIGN KEY (`membership_id`) REFERENCES `membership` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `user_projects` */
DROP TABLE IF EXISTS `user_projects`;
CREATE TABLE `user_projects`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`      bigint(20) DEFAULT NULL,
    `project_id`   bigint(20) DEFAULT NULL,
    `created_time` datetime     DEFAULT NULL,
    `start_time`   datetime     DEFAULT NULL,
    `end_time`     datetime     DEFAULT NULL,
    `time_period`  varchar(100) DEFAULT NULL,
    `status`       varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY            `fk_user_projects_user_id` (`user_id`),
    KEY            `fk_user_projects_project_id` (`project_id`),
    CONSTRAINT `fk_user_projects_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_projects_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


/*Admin User Creation*/
INSERT INTO `makerspace`.`user` (`first_name`, `last_name`, `username`, `email`, `password`, `is_active`,
                                 `user_type_id`, `created_date`)
VALUES ('Admin', 'User', 'admin', 'admin', 'B3Q6ei8Vj+1LZ3pEiT1s4w==', '1', '1', '2024-09-11 10:58:16');

