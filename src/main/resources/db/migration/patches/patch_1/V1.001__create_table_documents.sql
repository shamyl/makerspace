/*Table structure for table `attached_documents` */
DROP TABLE IF EXISTS `attached_documents`;

CREATE TABLE `attached_documents`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(200),
    `url`         varchar(255) DEFAULT NULL,
    `module_id`   BIGINT(20) DEFAULT NULL,
    `module_type` varchar(50) NOT NULL,
    KEY           `index_attached_documents_module_type` (`module_type`),
    KEY           `index_attached_documents_module_id` (`module_id`),
    PRIMARY KEY (`id`)
);


