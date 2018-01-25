
-- -----------------------------------------------------
-- Table `biz_airline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airline` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '航线id',
  `name` VARCHAR(255) NOT NULL COMMENT '航线名称',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '航班号',
  `carrierCompanyId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '航空公司',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `type` VARCHAR(64) NOT NULL COMMENT '航线类型：来自字典表',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '航线表';

