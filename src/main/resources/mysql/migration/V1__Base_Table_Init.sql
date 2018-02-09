CREATE DATABASE IF NOT EXISTS flywayDB DEFAULT CHARSET utf8 COLLATE utf8_unicode_ci

CREATE TABLE IF NOT EXISTS `table_1` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '号',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '表';


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


-- -----------------------------------------------------
-- Table `biz_airline_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airline_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '空运航线明细id',
  `airlineId` INT(11) UNSIGNED NOT NULL COMMENT '空运航线Id',
  `airportId` INT(11) UNSIGNED NOT NULL COMMENT '机场id',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '空运航线明细表';


-- -----------------------------------------------------
-- Table `biz_airplane_model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airplane_model` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `iata` VARCHAR(64) NOT NULL COMMENT 'IATA代码（三字码）',
  `icao` VARCHAR(64) NULL DEFAULT NULL COMMENT 'ICAO代码（四字码）',
  `modelType` INT(11) NULL DEFAULT '1' COMMENT '1-货运飞机; 2-客运飞机；3-其它',
  `payload` DOUBLE(16,3) NULL DEFAULT '0.000' COMMENT '最大负载能力，单位吨',
  `manufacturer` VARCHAR(64) NULL DEFAULT NULL COMMENT '制造商',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '序号',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `picture` VARCHAR(256) NULL DEFAULT NULL COMMENT '图片',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '机型表';


-- -----------------------------------------------------
-- Table `biz_airplane_model_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airplane_model_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '机型表索引ID',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地化名称',
  PRIMARY KEY (`id`),
  INDEX `refId_idx` (`refId` ASC),
  CONSTRAINT `refkey`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_airplane_model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '机型国际化表';


-- -----------------------------------------------------
-- Table `biz_airport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airport` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '机场id',
  `name` VARCHAR(256) NOT NULL COMMENT '机场名称',
  `countryId` VARCHAR(40) NOT NULL COMMENT '国家ID',
  `cityId` VARCHAR(40) NOT NULL COMMENT '城市ID',
  `triadCode` VARCHAR(32) NOT NULL COMMENT '三字码',
  `tetradCode` VARCHAR(32) NOT NULL COMMENT '四字码',
  `level` INT(11) NULL DEFAULT '0' COMMENT '等级',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '机场排序',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `jianpin` VARCHAR(40) NULL DEFAULT NULL COMMENT '拼音缩写',
  `pinyin` VARCHAR(40) NULL DEFAULT NULL COMMENT '完整拼音',
  `remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `address` VARCHAR(512) NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  INDEX `biz_airport_dict` (`countryId` ASC),
  INDEX `iz_airport_dict` (`cityId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '机场表';


-- -----------------------------------------------------
-- Table `biz_airport_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_airport_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '机场id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '国际化名称',
  PRIMARY KEY (`id`),
  INDEX `refId_idx` (`refId` ASC),
  CONSTRAINT `refId_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_airport` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '机场国际化表';


-- -----------------------------------------------------
-- Table `biz_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_area` (
  `id` VARCHAR(40) NOT NULL COMMENT '自增型主键',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `parentId` VARCHAR(40) NOT NULL COMMENT '父id',
  `startPostCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '邮编开始',
  `endPostCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '邮编结束',
  `hubCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '分站代码',
  `terminalCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '终端网点代码',
  `isDeleted` BIT(1) NULL DEFAULT NULL COMMENT '软删除标记',
  PRIMARY KEY (`id`),
  INDEX `parentId` (`parentId` ASC),
  INDEX `name` (`name` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '地区表';


-- -----------------------------------------------------
-- Table `biz_area_cn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_area_cn` (
  `id` VARCHAR(40) NOT NULL COMMENT '地区id',
  `districtCode` VARCHAR(40) NOT NULL COMMENT '行政区域编码',
  `name` VARCHAR(40) NOT NULL COMMENT '地区名称',
  `parentId` VARCHAR(11) NOT NULL COMMENT '上级地区',
  `startPostCode` VARCHAR(40) NULL DEFAULT NULL COMMENT '邮政编码',
  `endPostCode` VARCHAR(40) NULL DEFAULT NULL COMMENT '国内地区无此项',
  `areaCode` VARCHAR(40) NULL DEFAULT NULL COMMENT '地区编码',
  `figureCode` VARCHAR(40) NULL DEFAULT NULL COMMENT '二字码',
  `triadCode` VARCHAR(40) NOT NULL COMMENT '三字码',
  `level` INT(11) NULL DEFAULT '0' COMMENT '级别',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '排序',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `lng` DECIMAL(13,6) NULL DEFAULT NULL COMMENT '经度',
  `lat` DECIMAL(13,6) NULL DEFAULT NULL COMMENT '维度',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '国内地区表';


-- -----------------------------------------------------
-- Table `biz_area_cn_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_area_cn_i18n` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '地区国际化id',
  `refId` VARCHAR(40) NOT NULL COMMENT '地区id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对应的国家的地区名字',
  PRIMARY KEY (`id`),
  INDEX `areaId` (`refId` ASC),
  CONSTRAINT `areaId`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_area_cn` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '国内地区国际化表';


-- -----------------------------------------------------
-- Table `biz_area_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_area_i18n` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '地区国际化id',
  `refId` VARCHAR(40) NOT NULL COMMENT '地区id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NOT NULL COMMENT '对应的国家的地区名字',
  PRIMARY KEY (`id`),
  INDEX `areaId` (`refId` ASC),
  CONSTRAINT `area`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_area` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '地区国际化表';


-- -----------------------------------------------------
-- Table `biz_billing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_billing` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` VARCHAR(32) NOT NULL DEFAULT '2' COMMENT '账单类型：2为销售订单，customerId为客户，1为采购订单，customerId为供应商',
  `billNo` VARCHAR(64) NOT NULL COMMENT '账单号',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户',
  `releaseTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `currencyType` VARCHAR(255) NULL DEFAULT NULL COMMENT '币种',
  `total` DECIMAL(20,5) NULL DEFAULT NULL COMMENT '总计',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `status` VARCHAR(10) NULL DEFAULT NULL COMMENT '账单状态',
  `orderQuantity` INT(11) NULL DEFAULT NULL COMMENT '订单总数量',
  `completeOrderQuantity` INT(11) NULL DEFAULT NULL COMMENT '人物列表：已完成订单数量',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `isActive` BIT(1) NULL DEFAULT b'1' COMMENT '活动状态标记',
  `isRejected` BIT(1) NULL DEFAULT b'0' COMMENT '拒绝状态标记',
  `startEffectTime` TIMESTAMP NULL DEFAULT NULL COMMENT '账单起始时间',
  `endEffectTime` TIMESTAMP NULL DEFAULT NULL COMMENT '账单结束时间',
  `actualTotal` DECIMAL(20,5) NULL DEFAULT NULL COMMENT '实际金额',
  `adjustmentTypeCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整类型： 上调、下调',
  `adjustmentValue` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整基数',
  `adjustmentUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整单位： 百分比或者其他',
  `adjustmentRemark` VARCHAR(512) NULL DEFAULT NULL COMMENT '调整说明',
  `adjustmentTime` TIMESTAMP NULL DEFAULT NULL COMMENT '调整的时间',
  `adjustmentByUser` INT(11) NULL DEFAULT NULL COMMENT '调整人',
  `taskCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '任务编码',
  `billTime` TIMESTAMP NULL DEFAULT NULL COMMENT '出帐时间',
  `url` VARCHAR(255) NULL DEFAULT NULL COMMENT '附加信息链接',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账单表';


-- -----------------------------------------------------
-- Table `biz_billing_order_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_billing_order_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` VARCHAR(32) NOT NULL DEFAULT '2' COMMENT '账单类型：2为销售订单，customerId为客户，1为采购订单，customerId为供应商',
  `taskCode` VARCHAR(64) NOT NULL COMMENT '任务编号',
  `billNo` VARCHAR(64) NOT NULL COMMENT '账单号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `status` INT(11) NULL DEFAULT NULL COMMENT '当前状态',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单-账单参照表';


-- -----------------------------------------------------
-- Table `biz_billing_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_billing_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` VARCHAR(32) NOT NULL COMMENT '账单类型：1为销售订单，customerId为客户，2为采购订单，customerId为供应商',
  `taskCode` VARCHAR(64) NOT NULL COMMENT '任务代码',
  `startEffectTime` TIMESTAMP NULL DEFAULT NULL COMMENT '账单开始时间',
  `endEffectTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '账单截止时间',
  `orderQuantity` INT(11) NULL DEFAULT NULL COMMENT '订单数量',
  `completeOrderQuantity` INT(11) NULL DEFAULT NULL COMMENT '已完成订单数量',
  `taskStartTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '任务开始时间',
  `taskEndTime` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '任务结束时间',
  `taskStatus` VARCHAR(10) NULL DEFAULT NULL COMMENT '任务状态',
  `isRegenerate` BIT(1) NULL DEFAULT b'0' COMMENT '再生成标记',
  `regenerateTimes` INT(11) NULL DEFAULT NULL COMMENT '再生成次数',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `customerQuantity` INT(11) NULL DEFAULT NULL COMMENT '客户数量',
  `completeCustomerQuantity` INT(11) NULL DEFAULT NULL COMMENT '已完成客户数量',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `customerId` LONGTEXT NULL DEFAULT NULL COMMENT '客户ID',
  `skippedOrderQuantity` INT(11) NULL DEFAULT NULL COMMENT '已忽略订单数量',
  `skippedCustomerQuantity` INT(11) NULL DEFAULT NULL COMMENT '已忽略客户数量',
  `skippedCustomerList` LONGTEXT NULL DEFAULT NULL COMMENT '跳过已经生成过账单客户列表，逗号分隔',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账单任务表';


-- -----------------------------------------------------
-- Table `biz_carrier_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_carrier_company` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '承运公司id',
  `countryId` VARCHAR(40) NOT NULL COMMENT '所属国家',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `figureCode` VARCHAR(32) NOT NULL COMMENT '二字码',
  `triadCode` VARCHAR(32) NOT NULL COMMENT '三字码',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `level` INT(11) NULL DEFAULT '0' COMMENT '级别',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '排序字段',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `jianpin` VARCHAR(40) NULL DEFAULT NULL COMMENT '拼音缩写',
  `pinyin` VARCHAR(40) NULL DEFAULT NULL COMMENT '完整拼音',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`),
  INDEX `countryId` (`countryId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '承运公司表';


-- -----------------------------------------------------
-- Table `biz_carrier_company_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_carrier_company_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '承运公司索引ID',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地化名称',
  PRIMARY KEY (`id`),
  INDEX `ref_idx` (`refId` ASC),
  CONSTRAINT `ref`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_carrier_company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '承运公司国际化表';


-- -----------------------------------------------------
-- Table `biz_country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_country` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国家id',
  `figureCode` VARCHAR(40) NOT NULL COMMENT '二字码',
  `name` VARCHAR(255) NOT NULL COMMENT '地区名称',
  `englishName` VARCHAR(255) NULL DEFAULT NULL COMMENT '英文名称',
  `areaCode` VARCHAR(40) NOT NULL COMMENT '地区编码',
  `triadCode` VARCHAR(40) NOT NULL COMMENT '三字码',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `ordinal` INT(11) NULL DEFAULT NULL COMMENT '排序字段',
  `lng` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '经度',
  `lat` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '维度',
  PRIMARY KEY (`id`),
  INDEX `figureCode` (`figureCode` ASC),
  INDEX `figureCode_2` (`figureCode` ASC, `id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '国家表';


-- -----------------------------------------------------
-- Table `biz_country_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_country_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '地区国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '地区id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对应的国家的地区名字',
  PRIMARY KEY (`id`),
  INDEX `areaId` (`refId` ASC),
  CONSTRAINT `refId`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '国家国际化表';


-- -----------------------------------------------------
-- Table `biz_currency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_currency` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `code` VARCHAR(45) NOT NULL DEFAULT 'CNY' COMMENT '货币代码，比如CNY',
  `name` VARCHAR(45) NOT NULL DEFAULT '人民币' COMMENT '货币名称，比如人民币',
  `csymbol` VARCHAR(45) NULL DEFAULT '' COMMENT '货币符号，比如美元的$',
  `organization` VARCHAR(45) NOT NULL DEFAULT '0' COMMENT '货币使用范围，可以是多个国家，此处为国家表中引用的国家二字码，如果是多个国家，此处用英文逗号间隔',
  `isDelete` BIT(1) NOT NULL DEFAULT b'0' COMMENT '记录删除标志，默认为false，false代表未删除，true代表已删除',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '币种表';


-- -----------------------------------------------------
-- Table `biz_currency_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_currency_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refId` VARCHAR(45) NOT NULL COMMENT '币种索引ID',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地化名称',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '币种国际化表';


-- -----------------------------------------------------
-- Table `biz_exchange_rate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_exchange_rate` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `sourceCurrency` VARCHAR(45) NOT NULL DEFAULT 'CNY' COMMENT '原始货币',
  `destCurrency` VARCHAR(45) NOT NULL DEFAULT 'CNY' COMMENT '目标货币',
  `rate` DECIMAL(15,5) NOT NULL DEFAULT '0.00000' COMMENT '汇率',
  `beginTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '汇率生效时间',
  `endTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '汇率失效时间',
  `isDelete` BIT(1) NOT NULL DEFAULT b'0' COMMENT '删除标志',
  `lastUpdateTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `exchangeid_UNIQUE` (`id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '汇率表';


-- -----------------------------------------------------
-- Table `biz_fee_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_fee_type` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '费用类型id',
  `code` VARCHAR(255) NOT NULL COMMENT '费用类型编码',
  `name` VARCHAR(255) NOT NULL COMMENT '费用类型名字',
  `type` INT(11) NOT NULL COMMENT '费用的类型 1、空运费  2 海运费  3、燃油附加费',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被删除 0，未被删除，1已经被删除',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '详细描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '费用类型';


-- -----------------------------------------------------
-- Table `biz_fee_type_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_fee_type_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '费用类开国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '费用类型id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言种类',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '费用类型国际化名称',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `biz_fee_type_i18n_UN` (`refId` ASC, `language` ASC),
  CONSTRAINT `biz_fee_type_i18n_biz_fee_type_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_fee_type` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '费用类型国际化表';


-- -----------------------------------------------------
-- Table `biz_flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_flight` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '空运班次id',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '航班号',
  `airlineId` INT(11) NOT NULL COMMENT '航线id',
  `airplaneModelId` INT(11) NULL DEFAULT NULL COMMENT '机型id',
  `carrierCompanyId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '航空公司',
  `type` INT(11) NULL DEFAULT NULL COMMENT '航班类型:1自营航班，2卡车航班，3SPA航班',
  `isStopOver` BIT(1) NOT NULL COMMENT '是否经停',
  `periodType` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '周期类型 1按周 2按月',
  `periods` VARCHAR(255) NULL DEFAULT NULL COMMENT '周期W代表按周的周几D代表按月的某天',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `isSame` BIT(1) NOT NULL COMMENT '每天是否相同',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '空运班次表';


-- -----------------------------------------------------
-- Table `biz_flight_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_flight_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '空运班次明细id',
  `flightId` VARCHAR(255) NULL DEFAULT NULL COMMENT '航班Id',
  `airportId` INT(11) UNSIGNED NOT NULL COMMENT '机场id',
  `arrivePlusDay` INT(11) UNSIGNED NULL DEFAULT '0' COMMENT '经停港抵达+的天数',
  `arriveTime` VARCHAR(64) NULL DEFAULT NULL COMMENT '经停港抵达时间 时:分',
  `startPlusDay` INT(11) UNSIGNED NULL DEFAULT '0' COMMENT '经停港出发+的天数',
  `startTime` VARCHAR(64) NULL DEFAULT NULL COMMENT '经停港出发时间 时:分',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '空运班次明细表';


-- -----------------------------------------------------
-- Table `biz_message_template`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_message_template` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name` VARCHAR(64) NOT NULL COMMENT '模板名称',
  `templateType` VARCHAR(64) NOT NULL COMMENT 'email、sms，引用表sys_dict表',
  `refParentId` INT(11) NOT NULL COMMENT ' 叶子父节点',
  `templateUrl` VARCHAR(64) NOT NULL COMMENT '模板地址 如果是短信的话 保存的短信的code 如果是邮件的话保存邮件名称',
  `templateKey` VARCHAR(64) NOT NULL COMMENT '模板内容里面的key值，用逗号隔开',
  `createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '消息模板表，包括email、sms两种模板';


-- -----------------------------------------------------
-- Table `biz_operation_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_operation_log` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `accountId` INT(11) NOT NULL DEFAULT '0' COMMENT '操作人账号Id',
  `operSource` INT(11) NOT NULL DEFAULT '1' COMMENT '来源；即domain，1=运营系统；2=供应商系统；3=客户系统；4=数据交换平台；',
  `module` VARCHAR(255) NOT NULL COMMENT '模块,ord=物流订单;',
  `subModule` VARCHAR(255) NOT NULL COMMENT '子模块,ord_order=订单',
  `operType` VARCHAR(64) NULL DEFAULT NULL COMMENT '操作类型: create/update/delete',
  `eventType` VARCHAR(32) NULL DEFAULT NULL COMMENT '事件类型，1=保存草稿;2=提交订单;3=订单受理;4=物流信息变更;5=状态变更事件;6=订单信息修改;7=附件修改',
  `refNum` VARCHAR(32) NOT NULL COMMENT '外部唯一标识，订单这里存储订单的订单号',
  `data` MEDIUMTEXT NULL DEFAULT NULL COMMENT '数据备份区，物流订单=变化的字段json',
  `extra` VARCHAR(255) NULL DEFAULT NULL COMMENT '扩展描述,物流订单这里存储操作时刻，订单的状态',
  `operTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '物流订单操作记录表';


-- -----------------------------------------------------
-- Table `biz_port`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_port` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '港口id',
  `name` VARCHAR(256) NOT NULL COMMENT '名称',
  `englishName` VARCHAR(256) NULL DEFAULT NULL,
  `code` VARCHAR(32) NOT NULL COMMENT '编码',
  `countryId` VARCHAR(40) NOT NULL COMMENT '国家',
  `cityId` VARCHAR(40) NULL DEFAULT NULL COMMENT '城市',
  `type` VARCHAR(32) NULL DEFAULT NULL COMMENT '类型',
  `address` VARCHAR(512) NULL DEFAULT NULL COMMENT '地址',
  `airRoute` VARCHAR(255) NULL DEFAULT NULL COMMENT '航线',
  `remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
  `level` INT(11) NULL DEFAULT '0' COMMENT '级别',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '排序字段',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `jianpin` VARCHAR(40) NULL DEFAULT NULL COMMENT '拼音缩写',
  `pinyin` VARCHAR(40) NULL DEFAULT NULL COMMENT '完整拼音',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '港口表';


-- -----------------------------------------------------
-- Table `biz_port_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_port_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '港口国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '港口索引ID',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地化名称',
  PRIMARY KEY (`id`),
  INDEX `refId_port` (`refId` ASC),
  CONSTRAINT `refId_port`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_port` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '港口国际化表';


-- -----------------------------------------------------
-- Table `biz_price_combo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_combo` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `uid` VARCHAR(64) NOT NULL COMMENT '供价格之类的表使用的uuid值',
  `name` VARCHAR(255) NOT NULL COMMENT '套餐名称',
  `code` VARCHAR(255) NOT NULL COMMENT '套餐编码',
  `isActive` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否被激活　0：不激活　1：激活　',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0：未删除 1删除',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失效时间',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '价格套餐表';


-- -----------------------------------------------------
-- Table `biz_price_combo_quotation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_combo_quotation` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `comboId` INT(11) NOT NULL COMMENT '价格套餐（biz_price_combo）id',
  `productUid` VARCHAR(64) NOT NULL COMMENT '产品（biz_product）uid',
  `priceQuotationGrade` INT(11) NOT NULL COMMENT '价格套餐的等级，（biz_price_quotation）grade',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '价格套餐与报价表等级的中间表';


-- -----------------------------------------------------
-- Table `biz_price_freight_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_freight_item` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `quotationId` INT(11) NOT NULL COMMENT '价格方案的Id',
  `quotationSetId` INT(11) NOT NULL COMMENT 'set的Id',
  `toRegionUid` VARCHAR(64) NOT NULL COMMENT '分区表biz_region的Uid',
  `price` DECIMAL(18,5) NOT NULL DEFAULT '0.00000' COMMENT '价格值',
  `calcType` VARCHAR(64) NOT NULL COMMENT '计价方式 来自字典表',
  `baseValue` DECIMAL(18,5) NOT NULL COMMENT '每多少计价单位去乘单价',
  `baseUnitCode` VARCHAR(64) NOT NULL COMMENT '计价单位（千克、克、吨）',
  `idx` INT(11) NOT NULL COMMENT '序号，每个重量段一组',
  `ordinal` INT(11) NOT NULL COMMENT '序号每个价格一组',
  PRIMARY KEY (`id`),
  INDEX `idx_biz_price_freight_item_quotationId` (`quotationId` ASC),
  INDEX `idx_biz_price_freight_item_quotationSetId` (`quotationSetId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '价格的项';


-- -----------------------------------------------------
-- Table `biz_price_freight_regionsetting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_freight_regionsetting` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `quotationId` INT(11) NOT NULL COMMENT '报价单ID',
  `quotationSetId` INT(11) NOT NULL COMMENT '报价单SetID',
  `toRegionUid` VARCHAR(64) NOT NULL COMMENT '目的分区,二级分区的uid',
  `dayOutWeek` VARCHAR(255) NULL DEFAULT NULL COMMENT '1-7',
  `dayOutMonth` VARCHAR(255) NULL DEFAULT NULL COMMENT '1-31',
  `validTimeFrom` DATETIME NULL DEFAULT NULL COMMENT '生效的日期段开始',
  `validTimeTo` DATETIME NULL DEFAULT NULL COMMENT '生效的日期段终止',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '运费分区的设置';


-- -----------------------------------------------------
-- Table `biz_price_freight_weightsetting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_freight_weightsetting` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `quotationId` INT(11) NOT NULL COMMENT '报价单ID',
  `quotationSetId` INT(11) NOT NULL COMMENT '报价单SetID',
  `calcType` VARCHAR(64) NULL DEFAULT NULL COMMENT '计价方式来自字典表',
  `baseValue` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '每多少计量单位去乘单价',
  `baseUnitCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '计量单位',
  `ordinal` INT(11) NULL DEFAULT NULL COMMENT '排序字段，要用这个顺序去匹配重量段方案下的各个重量段',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '价格选择分区的方案\r\n这里保存的是有顺序的设置基础表';


-- -----------------------------------------------------
-- Table `biz_price_quotation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_quotation` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(255) NOT NULL COMMENT '报价单名称',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '报价单编码',
  `type` INT(1) NOT NULL COMMENT '报价单类型：1成本价2销售价',
  `regionSchemaUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '分区方案,运费类型必选',
  `weightSchemaUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '重量段方案,运费类型必选',
  `settlementTypeId` INT(11) NULL DEFAULT NULL COMMENT '结算方式',
  `gradeType` INT(11) NOT NULL DEFAULT '1' COMMENT '等级类型：1等级价2协议价',
  `grade` INT(11) NOT NULL DEFAULT '1' COMMENT '价格等级：成本价永远为1，销售价自己定义',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '协议价的时候有客户的id',
  `cargoTypeCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '货物类型',
  `currencyCode` VARCHAR(64) NOT NULL COMMENT '货币code',
  `startEffectTime` DATETIME NULL DEFAULT NULL COMMENT '生效时间',
  `endEffectTime` DATETIME NULL DEFAULT NULL COMMENT '失效时间',
  `status` INT(11) NOT NULL COMMENT '状态1草稿2提交审核3审核',
  `isRejected` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否打回',
  `isOnline` BIT(1) NULL DEFAULT NULL COMMENT '是否在线',
  `bizUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务/产品的uid',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `uid` VARCHAR(64) NOT NULL COMMENT '供其他参照表使用的UID',
  `isActive` BIT(1) NOT NULL COMMENT '活动状态标识',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `isDeleted` BIT(1) NOT NULL COMMENT '是否被删除',
  `inactiveTime` DATETIME NOT NULL COMMENT '失效时间',
  `createUserId` INT(11) NOT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最近一次修改者',
  `createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_biz_price_quotation_bizuid` (`bizUid` ASC),
  INDEX `idx_biz_price_quotation_uid` (`uid` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '报价单';


-- -----------------------------------------------------
-- Table `biz_price_quotation_set`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_quotation_set` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `quotationId` INT(10) UNSIGNED NOT NULL COMMENT '报价id',
  `feeTypeId` INT(11) NULL DEFAULT NULL COMMENT '费用类型id，引用费用类型表',
  `supplierId` INT(11) NULL DEFAULT NULL COMMENT '供应商id，当为成本价时有数据',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务uid，当为销售价时有数据',
  `weightSchemaUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '重量段的uid',
  `regionSchemaUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '分区uid',
  PRIMARY KEY (`id`),
  INDEX `idx_biz_price_quotation_set_quotationId` (`quotationId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '报价单集合表';


-- -----------------------------------------------------
-- Table `biz_price_quotation_start`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_price_quotation_start` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `quotationId` INT(11) NOT NULL COMMENT '报价单的Id',
  `type` INT(11) NOT NULL COMMENT '分区详细类型，1、国家 2、中国地区 3、外国地区 4、邮编',
  `country` VARCHAR(64) NOT NULL COMMENT '国家',
  `areaId` VARCHAR(64) NULL DEFAULT NULL COMMENT '地区的id，当type为国家或邮编时为NULL',
  `startPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编开始',
  `endPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编结束',
  `polygon` VARCHAR(512) NULL DEFAULT NULL COMMENT '预留字段，gis路径',
  PRIMARY KEY (`id`),
  INDEX `idx_biz_price_quotation_start_quotationId` (`quotationId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '报价单表头表';


-- -----------------------------------------------------
-- Table `biz_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `uid` VARCHAR(64) NOT NULL COMMENT '供其他参照表使用的UID',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `code` VARCHAR(128) NOT NULL COMMENT '编码',
  `isOnline` BIT(1) NULL DEFAULT NULL COMMENT '是否在线',
  `status` INT(11) NOT NULL DEFAULT '1' COMMENT '状态,3、审核通过',
  `isActive` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否是有效版本',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失活时间',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `createUserId` INT(10) UNSIGNED NOT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(10) UNSIGNED NOT NULL COMMENT '最近一次修改者',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `volumeFactor` DECIMAL(20,3) NULL DEFAULT NULL COMMENT '体积系数',
  `introduction` VARCHAR(255) NULL DEFAULT NULL COMMENT '产品介绍',
  `estimatedTime` DOUBLE NULL DEFAULT NULL COMMENT '预估时效',
  `estimatedUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '预估时效单位',
  `productGroupLeafId` INT(10) NULL DEFAULT NULL COMMENT '产品组，存的叶子节点',
  `remarks` LONGTEXT NULL DEFAULT NULL COMMENT '备注',
  `isVolume` BIT(1) NULL DEFAULT b'0' COMMENT '是否计体积',
  `weightValueTye` VARCHAR(45) NULL DEFAULT NULL COMMENT '取值方式',
  `weightLimitMin` DECIMAL(20,3) NULL DEFAULT NULL COMMENT '产品重量限制下限',
  `weightLimitMax` DECIMAL(20,3) NULL DEFAULT NULL COMMENT '产品重量限制上限',
  `weightLimitUnitCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '重量上限单位',
  `isRejected` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否打回',
  `image` VARCHAR(255) NULL DEFAULT NULL COMMENT '产品图片',
  `acceptType` INT(11) NULL DEFAULT '1' COMMENT '受理方式：0: 自动； 1：人工',
  `isPublic` BIT(1) NULL DEFAULT b'1' COMMENT '是否公开产品',
  PRIMARY KEY (`id`),
  INDEX `idx_product_uid` (`uid` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品表';


-- -----------------------------------------------------
-- Table `biz_product_cargotype_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_cargotype_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `productId` INT(11) UNSIGNED NOT NULL COMMENT '产品的Id',
  `cargoTypeCode` VARCHAR(64) NOT NULL COMMENT '货物类型code',
  PRIMARY KEY (`id`),
  INDEX `idx_product_cargo_pid` (`productId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品-货物类型参照表';


-- -----------------------------------------------------
-- Table `biz_product_customer_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_customer_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `productGroupId` INT(11) NULL DEFAULT NULL COMMENT '产品的Id',
  `productUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品uid',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `type` INT(11) NULL DEFAULT NULL COMMENT '限制类型： 0： 产品组; 1: 产品',
  `wbFlag` INT(11) NULL DEFAULT NULL COMMENT '白黑名单标志 0:可以看到公共产品和自己设置的白名单产品 1: 白名单(只能看白名单里的产品)； 2： 黑名单(不能看的产品)',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  PRIMARY KEY (`id`),
  INDEX `idx_product_cargo_pid` (`productGroupId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品客户参照表';


-- -----------------------------------------------------
-- Table `biz_product_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_group` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '产品组id',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '名称',
  `parentId` INT(11) NOT NULL COMMENT '产品组父id',
  `isDeleted` BIT(1) NULL DEFAULT NULL COMMENT '软删除标记',
  `ordinal` INT(11) NULL DEFAULT NULL COMMENT '序号',
  `image` VARCHAR(255) NULL DEFAULT NULL COMMENT '产品组图片',
  `isPublic` BIT(1) NULL DEFAULT b'1' COMMENT '是否公开产品组',
  `currencyCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '货币类型',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品组表';


-- -----------------------------------------------------
-- Table `biz_product_region_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_region_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `productId` INT(11) NOT NULL COMMENT '产品Id',
  `schemaUid` VARCHAR(64) NOT NULL COMMENT 'biz_region_schema中对应条目的uid',
  `type` VARCHAR(1) NOT NULL COMMENT '范围类型',
  `code` VARCHAR(512) NOT NULL COMMENT '范围编码',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` LONGTEXT NULL DEFAULT NULL COMMENT '备注',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品-区域参照表';


-- -----------------------------------------------------
-- Table `biz_product_service_cargotype_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_service_cargotype_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `psId` INT(11) NULL DEFAULT NULL COMMENT '产品服务关联id',
  `cargoTypeCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '货物类型编码',
  `cargoTypeName` VARCHAR(255) NULL DEFAULT NULL COMMENT '货物类型名称',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `biz_product_service_region_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_service_region_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `psId` INT(11) NULL DEFAULT NULL COMMENT '产品服务关联id',
  `region` INT(11) NULL DEFAULT NULL COMMENT '产品服务范围(根据产品不同获取不同的flight id, ship id, region schema item id)',
  `productId` INT(11) NULL DEFAULT NULL COMMENT '产品id',
  `productUid` VARCHAR(45) NULL DEFAULT NULL COMMENT '产品uid',
  `serviceUid` VARCHAR(45) NULL DEFAULT NULL COMMENT '服务uid',
  `regionName` VARCHAR(255) NULL DEFAULT NULL COMMENT '航班，海线，地区名称',
  `type` VARCHAR(45) NULL DEFAULT NULL COMMENT '产品服务范围类型： s-起始； e-终点',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `biz_product_service_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_service_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `productId` INT(10) UNSIGNED NOT NULL COMMENT '产品ID',
  `serviceUid` VARCHAR(64) NOT NULL COMMENT '服务UID',
  `serviceProperty` VARCHAR(10) NULL DEFAULT NULL COMMENT '是否是可选的服务',
  `ordinal` INT(10) NULL DEFAULT NULL COMMENT '生成服务单时可按照此顺序执行',
  `serviceTypeCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '服务类型编码',
  `serviceTypeName` VARCHAR(255) NULL DEFAULT NULL COMMENT '服务类型名称',
  `serviceName` VARCHAR(255) NULL DEFAULT NULL COMMENT '服务名称',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
  `priceWeighting` INT(10) NULL DEFAULT NULL COMMENT '价格权重',
  `agingWeighting` INT(10) NULL DEFAULT NULL COMMENT '时效权重',
  `customerLevel` INT(10) NULL DEFAULT NULL COMMENT '客户等级',
  `customerLevelExt` VARCHAR(45) NULL DEFAULT NULL COMMENT '客户等级： 及以上、及以下',
  `weightLimitMin` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '重量限制： 上限',
  `weightLimitMax` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '量限制： 下限',
  `declaredValueMin` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '申报价值： 下限',
  `declaredValueMax` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '申报价值： 上限',
  `orderQuantity` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '单量',
  `orderQuantityExt` VARCHAR(10) NULL DEFAULT NULL COMMENT '单量： more, less 大于,小于',
  `itemDetail` LONGTEXT NULL DEFAULT NULL COMMENT '冗余一个所有分区明细的字段，方便查询',
  `customerLevelName` VARCHAR(45) NULL DEFAULT NULL,
  `customerLevelExtName` VARCHAR(45) NULL DEFAULT NULL,
  `orderQuantityUnit` VARCHAR(45) NULL DEFAULT NULL,
  `orderQuantityExtName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_product_service_pid` (`productId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品服务关系表';


-- -----------------------------------------------------
-- Table `biz_product_servicetype_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_product_servicetype_rel` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `productId` INT(11) UNSIGNED NOT NULL COMMENT '产品的Id',
  `serviceTypeCode` VARCHAR(64) NOT NULL COMMENT '服务类型的code',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品-服务类型参照表';


-- -----------------------------------------------------
-- Table `biz_region_schema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_region_schema` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `uid` VARCHAR(64) NOT NULL COMMENT '供分区表（biz_region）使用的uuid值',
  `code` VARCHAR(45) NULL DEFAULT NULL COMMENT '分区方案的编码',
  `name` VARCHAR(64) NOT NULL COMMENT '分区方案名称',
  `addressType` INT(11) NOT NULL DEFAULT '11' COMMENT '分区方案地址的类型：1、全球海运 6、全球空运 11、全球速递',
  `type` INT(11) NOT NULL COMMENT '分区方案的类类型，1、计费分区 2、派送分区 3、产品范围分区4、服务范围分区',
  `isActive` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否被激活　0：不激活　1：激活　',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0：未删除 1删除',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失效时间',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  `itemDetail` LONGTEXT NULL DEFAULT NULL COMMENT '冗余一个所有分区明细的字段，方便查询',
  PRIMARY KEY (`id`),
  INDEX `biz_region_schema_uid_index` (`uid` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '分区';


-- -----------------------------------------------------
-- Table `biz_region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_region` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `uid` VARCHAR(64) NOT NULL COMMENT '供价格之类的表使用的uuid值',
  `schemaId` VARCHAR(64) NOT NULL COMMENT '外键id，关联biz_region_schema的uid',
  `code` VARCHAR(64) NOT NULL COMMENT '分区编码',
  `name` VARCHAR(64) NOT NULL COMMENT '分区名称',
  `isActive` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否被激活　0：不激活　1：激活　',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0：未删除 1删除',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失效时间',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  INDEX `biz_region_schemaId_FK_idx` (`schemaId` ASC),
  CONSTRAINT `biz_region_schemaId_FK`
  FOREIGN KEY (`schemaId`)
  REFERENCES `biz_region_schema` (`uid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '区域表';


-- -----------------------------------------------------
-- Table `biz_region_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_region_item` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `regionId` INT(11) UNSIGNED NOT NULL COMMENT '关联biz_region表的主键',
  `type` INT(11) NOT NULL COMMENT '分区详细类型，1、国家 2、中国地区 3、外国地区 4、邮编 5、海运地址 6、空运地址',
  `country` VARCHAR(64) NOT NULL COMMENT '国家',
  `areaId` VARCHAR(64) NULL DEFAULT NULL COMMENT '地区的id，当type为国家或邮编时为NULL',
  `transportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id，其中存的是空运的站点id还是海运的站点id，根据type来决定，5 代表海运地址 6代表空运地址',
  `startPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编开始',
  `endPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编结束',
  `polygon` VARCHAR(512) NULL DEFAULT NULL COMMENT '预留字段，gis路径',
  `itemDetail` LONGTEXT NULL DEFAULT NULL COMMENT '冗余全称',
  PRIMARY KEY (`id`),
  INDEX `biz_region_item_regionId_FK_idx` (`regionId` ASC),
  CONSTRAINT `biz_region_item_regionId_FK`
  FOREIGN KEY (`regionId`)
  REFERENCES `biz_region` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '区域条目表';


-- -----------------------------------------------------
-- Table `biz_serialno_management`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_serialno_management` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serialNumber` VARCHAR(128) NULL DEFAULT NULL COMMENT '渠道单号',
  `batchNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '批次号',
  `orderNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '订单号',
  `ruleId` INT(11) NULL DEFAULT NULL COMMENT '规则id',
  `refUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务Uid',
  `refId` INT(11) NULL DEFAULT NULL COMMENT '供应商ID',
  `isUsed` BIT(1) NULL DEFAULT b'0' COMMENT '是否已被使用',
  `createTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `generateMethod` INT(2) NULL DEFAULT NULL COMMENT '生成方式',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者ID',
  `status` TINYINT(1) NULL DEFAULT '0' COMMENT '状态',
  `usedTime` DATETIME NULL DEFAULT NULL,
  `type` INT(1) NOT NULL DEFAULT '1' COMMENT '1：渠道单号 2：运单号',
  `refCustomer` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '单号管理表';


-- -----------------------------------------------------
-- Table `biz_serialno_rule_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_serialno_rule_details` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `ruleId` INT(11) NULL DEFAULT NULL COMMENT '规则id',
  `createMethod` INT(2) NULL DEFAULT NULL COMMENT '生成方式',
  `startNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '起始号码',
  `endNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '结尾号码',
  `total` BIGINT(20) NULL DEFAULT NULL COMMENT '总量',
  `batchNumber` INT(11) NULL DEFAULT NULL COMMENT '每批数量',
  `generateThreshold` INT(11) NULL DEFAULT NULL COMMENT '触发自动生成的阈值',
  `warnThreshold` INT(11) NULL DEFAULT NULL COMMENT '触发提醒的阈值',
  `noticeType` VARCHAR(32) NULL DEFAULT NULL COMMENT '提醒方式',
  `receiver` VARCHAR(128) NULL DEFAULT NULL COMMENT '提醒的接收人',
  `type` INT(1) NOT NULL DEFAULT '1' COMMENT '1：渠道单号;2 : 运单号',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '单号规则详情表';


-- -----------------------------------------------------
-- Table `biz_serialno_rules`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_serialno_rules` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务Uid',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '规则代号',
  `formatRule` VARCHAR(64) NULL DEFAULT NULL COMMENT '格式规则',
  `prefix` VARCHAR(64) NULL DEFAULT NULL COMMENT '前缀',
  `serialNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '序列号',
  `postfix` VARCHAR(64) NULL DEFAULT NULL COMMENT '后缀',
  `verifyRule` VARCHAR(64) NULL DEFAULT NULL COMMENT '校验规则',
  `numberSection` VARCHAR(64) NULL DEFAULT NULL COMMENT '号段',
  `sectionStart` VARCHAR(64) NULL DEFAULT NULL COMMENT '号段起点',
  `sectionEnd` VARCHAR(64) NULL DEFAULT NULL COMMENT '号段终点',
  `createTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` INT(2) NULL DEFAULT NULL COMMENT '状态',
  `type` INT(1) NULL DEFAULT '1' COMMENT '1:渠道单号；2：运单号',
  `refId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '单号规则表';


-- -----------------------------------------------------
-- Table `biz_serialno_warn_notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_serialno_warn_notice` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `ruleId` INT(11) NULL DEFAULT NULL COMMENT '规则id',
  `batchNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '触发提醒的批次号',
  `isSent` BIT(1) NULL DEFAULT b'0' COMMENT '是否已经发送提醒',
  `sendTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `noticeType` VARCHAR(32) NULL DEFAULT NULL COMMENT '提醒方式',
  `receiver` VARCHAR(128) NULL DEFAULT NULL COMMENT '提醒的接收人',
  `template` VARCHAR(128) NULL DEFAULT NULL COMMENT '模板文件名',
  `refMessageId` INT(11) NULL DEFAULT NULL,
  `type` INT(1) NOT NULL DEFAULT '1' COMMENT '1：渠道单号；2：运单号',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '单号号段提醒记录表';


-- -----------------------------------------------------
-- Table `biz_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(256) NOT NULL COMMENT '服务名称',
  `code` VARCHAR(256) NULL DEFAULT NULL COMMENT '服务编码',
  `uid` VARCHAR(64) NOT NULL COMMENT '供其他参照表使用的UID',
  `serviceContent` VARCHAR(512) NULL DEFAULT NULL COMMENT '服务内容',
  `supplierId` INT(10) NULL DEFAULT NULL COMMENT '供应商id',
  `goodsTypeId` INT(11) NULL DEFAULT NULL COMMENT '商品类型',
  `isActive` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否激活',
  `isOnline` BIT(1) NULL DEFAULT NULL COMMENT '是否在线',
  `isDeleted` BIT(1) NOT NULL COMMENT '软删除标记',
  `activeTime` DATETIME NOT NULL COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失效时间',
  `status` INT(10) UNSIGNED NOT NULL DEFAULT '1' COMMENT '当前状态',
  `isRejected` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否打回',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createUserId` INT(10) UNSIGNED NOT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(10) UNSIGNED NOT NULL COMMENT '最近一次修改者',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `estimatedTime` DOUBLE NULL DEFAULT NULL COMMENT '预估时效',
  `estimatedUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '预估时效单位',
  `weightLimitMin` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '服务重量限制下限',
  `weightLimitMax` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '服务重量限制上限',
  `weightLimitUnitCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '重量上限单位',
  PRIMARY KEY (`id`),
  INDEX `biz_service_uid` USING BTREE (`uid` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务表';


-- -----------------------------------------------------
-- Table `biz_service_area_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_area_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serviceId` INT(11) NOT NULL COMMENT '服务的主键',
  `type` INT(11) NOT NULL COMMENT '分区详细类型，1、国家 2、中国地区 3、外国地区 4、邮编',
  `country` VARCHAR(64) NOT NULL COMMENT '国家',
  `areaId` VARCHAR(64) NULL DEFAULT NULL COMMENT '地区的id，当type为国家或邮编时为NULL',
  `startPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编开始',
  `endPostcode` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮编结束',
  `polygon` VARCHAR(512) NULL DEFAULT NULL COMMENT '预留字段，gis路径',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务的服务范围';


-- -----------------------------------------------------
-- Table `biz_service_cargotype_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_cargotype_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serviceId` INT(10) UNSIGNED NOT NULL COMMENT '服务ID',
  `cargoTypeCode` VARCHAR(64) NOT NULL COMMENT '货物类型代码',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务-货物类型参照表';


-- -----------------------------------------------------
-- Table `biz_service_class_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_class_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增的主键',
  `serviceId` INT(10) UNSIGNED NOT NULL COMMENT '服务的id',
  `classId` INT(10) UNSIGNED NOT NULL COMMENT '班次的Id,来源',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '干线服务班次的关联表';


-- -----------------------------------------------------
-- Table `biz_service_effectiveness`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_effectiveness` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `supplierId` INT(11) NOT NULL COMMENT '供应商id',
  `serviceUid` VARCHAR(64) NOT NULL COMMENT '服务uid',
  `timeCost` INT(11) NOT NULL COMMENT '时间成本，小时为单位',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商服务效率表';


-- -----------------------------------------------------
-- Table `biz_service_region_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_region_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serviceId` INT(11) NOT NULL COMMENT '服务Id',
  `schemaUid` VARCHAR(64) NOT NULL COMMENT 'biz.region.schema中的对应条目的ID',
  `type` VARCHAR(1) NOT NULL COMMENT '范围类型',
  `code` VARCHAR(512) NOT NULL COMMENT '范围编码',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` LONGTEXT NULL DEFAULT NULL COMMENT '备注',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务-区域参照表';


-- -----------------------------------------------------
-- Table `biz_service_servicetype_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_service_servicetype_rel` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serviceId` INT(10) UNSIGNED NOT NULL COMMENT '服务ID',
  `serviceTypeCode` VARCHAR(64) NOT NULL COMMENT '来自字典表',
  `subServiceTypeCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '二级服务类型，目前干线服务有二级服务类型，包括：海运、空运、其它',
  `lineId` INT(10) NULL DEFAULT NULL COMMENT '航线Id(包括海运、空运)',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务-服务类型参照表';


-- -----------------------------------------------------
-- Table `biz_settlement_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_settlement_type` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` VARCHAR(255) NOT NULL COMMENT '结算编号',
  `name` VARCHAR(255) NOT NULL COMMENT '结算类型',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0：未删除。1：已删除',
  `isSystem` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否系统预设',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '结算类型表';


-- -----------------------------------------------------
-- Table `biz_settlement_type_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_settlement_type_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '结算编码id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对应结算类型的国际化名字',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `biz_settlement_type_i18n_UN` (`refId` ASC, `language` ASC),
  CONSTRAINT `biz_settlement_type_i18n_biz_settlement_type_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_settlement_type` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '结算类型国际化表';


-- -----------------------------------------------------
-- Table `biz_ship`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_ship` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '船舶id',
  `name` VARCHAR(255) NOT NULL COMMENT '船舶名称',
  `nameEn` VARCHAR(255) NOT NULL COMMENT '英文船舶名称',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '船舶编码',
  `mmsi` VARCHAR(32) NOT NULL COMMENT '船舶mmsi',
  `imo` VARCHAR(32) NULL DEFAULT NULL COMMENT 'imo',
  `callSign` VARCHAR(32) NULL DEFAULT NULL COMMENT '呼号 4~5位',
  `companyId` INT(11) UNSIGNED NOT NULL COMMENT '所属公司',
  `countryId` VARCHAR(10) NOT NULL COMMENT '国籍',
  `length` DECIMAL(8,3) NULL DEFAULT NULL COMMENT '船长',
  `width` DECIMAL(8,3) NULL DEFAULT NULL COMMENT '船宽',
  `draught` DECIMAL(8,3) NULL DEFAULT NULL COMMENT '吃水深度',
  `lightWeight` DECIMAL(18,3) NULL DEFAULT NULL COMMENT '空船重量',
  `deadWeight` DECIMAL(18,3) NULL DEFAULT NULL COMMENT '载重量',
  `shippingArea` VARCHAR(255) NULL DEFAULT NULL COMMENT '航区：远洋 沿海',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `picture` VARCHAR(255) NULL DEFAULT NULL COMMENT '船图片',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '船型表';


-- -----------------------------------------------------
-- Table `biz_ship_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_ship_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '船舶id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '船舶名字i18n',
  PRIMARY KEY (`id`),
  INDEX `ship_i18n_shipId` (`refId` ASC),
  CONSTRAINT `ship_i18n_refId`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_ship` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '船型国际化表';


-- -----------------------------------------------------
-- Table `biz_shipping_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shipping_company` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '航运公司id',
  `countryId` VARCHAR(40) NOT NULL COMMENT '所属国家',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `nameEn` VARCHAR(32) NULL DEFAULT NULL COMMENT '英文名称',
  `code` VARCHAR(32) NOT NULL COMMENT '代码',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`),
  INDEX `countryId` (`countryId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '航运公司表';


-- -----------------------------------------------------
-- Table `biz_shipping_company_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shipping_company_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '航运公司索引ID',
  `language` VARCHAR(16) NOT NULL COMMENT '语言',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '本地化名称',
  PRIMARY KEY (`id`),
  INDEX `ref_idx` (`refId` ASC),
  CONSTRAINT `ref_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `biz_shipping_company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '航运公司国际化表';


-- -----------------------------------------------------
-- Table `biz_shippingline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shippingline` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '航线id',
  `name` VARCHAR(255) NOT NULL COMMENT '航线名称',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '航线编码',
  `carrierId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '承运的船运公司',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `TYPE` VARCHAR(64) NOT NULL COMMENT '航线类型：来自字典表',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '船运航线表';


-- -----------------------------------------------------
-- Table `biz_shippingline_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shippingline_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '海运航线明细id',
  `shippingLineId` INT(11) UNSIGNED NOT NULL COMMENT '海运航线Id',
  `portId` INT(11) UNSIGNED NOT NULL COMMENT '港口id',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '海运航线明细表';


-- -----------------------------------------------------
-- Table `biz_shippingline_setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shippingline_setting` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '海运班次id',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '海运班号',
  `shipId` INT(11) UNSIGNED NOT NULL COMMENT '船id',
  `shippingLineId` INT(11) NOT NULL COMMENT '航线id',
  `shippingCompanyId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '海运公司',
  `periodType` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '周期类型 1按周 2按月 3不定期',
  `periods` VARCHAR(255) NULL DEFAULT NULL COMMENT '周期W代表按周的周几D代表按月的某天',
  `days` INT(11) NULL DEFAULT NULL COMMENT '全程时长 天单位',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '海运班次表';


-- -----------------------------------------------------
-- Table `biz_shippingline_setting_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_shippingline_setting_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '轮班明细id',
  `shippingLineSettingId` INT(11) UNSIGNED NOT NULL COMMENT '轮班Id',
  `portId` INT(11) UNSIGNED NOT NULL COMMENT '港口id',
  `eta` TIMESTAMP NULL DEFAULT NULL COMMENT '到港时间',
  `etd` TIMESTAMP NULL DEFAULT NULL COMMENT '离港时间',
  `cargoDeadTime` TIMESTAMP NULL DEFAULT NULL COMMENT '截货时间',
  `portDeadTime` TIMESTAMP NULL DEFAULT NULL COMMENT '截关时间',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '顺序',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '轮班明细表';


-- -----------------------------------------------------
-- Table `biz_verify_rules`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_verify_rules` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(64) NULL DEFAULT NULL COMMENT '格式规则名',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '格式规则编码',
  `format` VARCHAR(64) NULL DEFAULT NULL COMMENT '格式规则',
  `regex` VARCHAR(128) NULL DEFAULT NULL COMMENT '规则正则表达式',
  `createTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` INT(1) NOT NULL DEFAULT '1' COMMENT '1：渠道单号规则；2：运单号',
  `example` VARCHAR(64) NULL DEFAULT NULL COMMENT '格式规则示例',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '校验规则表';


-- -----------------------------------------------------
-- Table `biz_weight_schema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_weight_schema` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `uid` VARCHAR(64) NOT NULL COMMENT '供其他参照表使用的UID',
  `name` VARCHAR(64) NOT NULL COMMENT '模式名称',
  `code` VARCHAR(45) NOT NULL COMMENT '模式代码',
  `unit` VARCHAR(12) NULL DEFAULT NULL COMMENT '计量单位',
  `creatorId` INT(11) NOT NULL COMMENT '创建者ID',
  `creator` VARCHAR(64) NOT NULL COMMENT '创建者',
  `createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  `isActive` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否激活',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '软删除标记',
  `activeTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '激活时间',
  `inactiveTime` DATETIME NOT NULL DEFAULT '2100-01-01 00:00:00' COMMENT '失效时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '称重模式表';


-- -----------------------------------------------------
-- Table `biz_weight_section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_weight_section` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refId` INT(10) NOT NULL COMMENT '称重模式索引ID',
  `startPoint` DECIMAL(13,3) NOT NULL COMMENT '起算点',
  `endPoint` DECIMAL(13,3) NOT NULL COMMENT '结算点',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '重量区间表';


-- -----------------------------------------------------
-- Table `biz_wf_approval`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `biz_wf_approval` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `ownerType` VARCHAR(64) NOT NULL COMMENT '所有者类型',
  `ownerId` VARCHAR(64) NOT NULL COMMENT '所有者ID',
  `fromStatus` INT(10) NOT NULL COMMENT '起始状态',
  `toStatus` INT(10) NOT NULL COMMENT '结束状态',
  `approvedBy` INT(11) NOT NULL COMMENT '审核人',
  `approvalResult` BIT(1) NOT NULL DEFAULT b'1' COMMENT '审核结果',
  `approvalOpinion` VARCHAR(512) NULL DEFAULT NULL COMMENT '审核意见',
  `approvalTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '工作流程审核表';


-- -----------------------------------------------------
-- Table `cont_contract`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cont_contract` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同标题',
  `serviceType` INT(11) NULL DEFAULT NULL COMMENT '业务类型;1=物流；2=贸易',
  `type` INT(11) NOT NULL COMMENT '合同类型;1=模板合同；2=自定义合同',
  `templateId` INT(11) NULL DEFAULT NULL COMMENT '合同模板的id',
  `templateName` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同模板名称',
  `fileId` VARCHAR(64) NULL DEFAULT NULL COMMENT '文件Id',
  `status` INT(11) NULL DEFAULT NULL COMMENT '状态;1=草稿;2=已生成',
  `signStatus` INT(11) NULL DEFAULT NULL COMMENT '签署状态;1=甲方签署;2=乙方签署;3=双方签署',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被删除 0，未被删除，1已经被删除',
  `genFileTime` TIMESTAMP NULL DEFAULT NULL COMMENT '生成pdf时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建人',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '修改人',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '合同表';


-- -----------------------------------------------------
-- Table `cont_contractor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cont_contractor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` INT(11) NOT NULL COMMENT '类型；1=合同模板；2=合同',
  `relId` INT(11) NOT NULL COMMENT '合同模板id或者合同id',
  `contractorType` INT(11) NOT NULL COMMENT '合同方的类型，1=甲方，2=乙方',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同方名称',
  `signName` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同方签章',
  `address` VARCHAR(1024) NULL DEFAULT NULL COMMENT '合同方地址',
  `contact` VARCHAR(255) NULL DEFAULT NULL COMMENT '联系方式',
  `bankAccount` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同方银行账号',
  `bankName` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同方开户行名称',
  `authDelegate` VARCHAR(255) NULL DEFAULT NULL COMMENT '合同方授权代表',
  `signTime` TIMESTAMP NULL DEFAULT NULL COMMENT '合同方签字时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '合同缔约方表';


-- -----------------------------------------------------
-- Table `cont_field`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cont_field` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` INT(11) NOT NULL COMMENT '类型；1=合同模板；2=合同',
  `relId` INT(11) NOT NULL COMMENT '合同模板id或者合同id',
  `orderNum` INT(11) NOT NULL COMMENT '顺序号',
  `fieldKey` VARCHAR(255) NULL DEFAULT NULL COMMENT '标题，引言，正文等field的key',
  `fieldValue` LONGTEXT NULL DEFAULT NULL COMMENT '输入值',
  `fieldType` INT(11) NOT NULL DEFAULT '1' COMMENT '类型;1=直接值类型；2=表达式',
  `expression` VARCHAR(255) NULL DEFAULT NULL COMMENT '表达式',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '合同区块内容表';


-- -----------------------------------------------------
-- Table `cont_template`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cont_template` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serviceType` INT(11) NOT NULL COMMENT '业务类型;1=物流;2=贸易',
  `type` INT(11) NOT NULL DEFAULT '1' COMMENT '类型；1=数据类型；2=文件类型',
  `name` VARCHAR(512) NOT NULL COMMENT '模板名称',
  `projectCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '项目编码',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被删除 0，未删除，1已删除',
  `createUserId` INT(11) NOT NULL COMMENT '创建人',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '修改人',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '合同模板表';


-- -----------------------------------------------------
-- Table `csr_ask_board_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `csr_ask_board_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `refAskBoardId` INT(11) NULL DEFAULT NULL COMMENT '关联的主题ID，引用自csr_ask_board_schema中ID字段',
  `refReplyerId` INT(11) NULL DEFAULT NULL COMMENT '回复者ID 引用自运营系统的用户表',
  `messageBody` VARCHAR(512) NULL DEFAULT NULL COMMENT '回复的消息内容',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建者ID 引用自',
  `isModerator` BIT(1) NULL DEFAULT NULL COMMENT '是否是版主 0：不是 1：是',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '问答详情表';


-- -----------------------------------------------------
-- Table `csr_ask_board_schema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `csr_ask_board_schema` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '客户id，引用自客户账户表',
  `topic` VARCHAR(128) NULL DEFAULT NULL COMMENT '主题',
  `content` VARCHAR(512) NULL DEFAULT NULL COMMENT '内容',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除标志 1：删除  0：未删除',
  `status` BIT(1) NULL DEFAULT b'0' COMMENT '状态 1：解决 0：未解决',
  `updateTime` TIMESTAMP NULL DEFAULT NULL COMMENT '修改时间',
  `lastReplyId` INT(11) NULL DEFAULT NULL COMMENT '最近一条留言者ID',
  `isModeratorForReplyer` BIT(1) NULL DEFAULT b'1' COMMENT '留言者是否是版主 1：是 0：不是',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '编码',
  `serialNumber` INT(4) NULL DEFAULT NULL COMMENT '流水号',
  `type` INT(2) NULL DEFAULT '0' COMMENT '留言类型 0:客户留言 1：系统留言',
  `anonymity` VARCHAR(64) NULL DEFAULT NULL COMMENT '匿名用户名称',
  `anonyTel` VARCHAR(64) NULL DEFAULT NULL COMMENT '匿名用户电话',
  `isRead` BIT(1) NULL DEFAULT b'0' COMMENT '标记此留言主题中是否存在未读的消息 0:存在 1:不存在',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '问答模式表';


-- -----------------------------------------------------
-- Table `csr_workorder_schema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `csr_workorder_schema` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` INT(2) NULL DEFAULT '0' COMMENT '工单类型  业务咨询：0 问题反馈：1 修改订单：2 其他：3',
  `orderNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '订单号',
  `refAskBoardId` INT(11) NULL DEFAULT NULL COMMENT '问答版块的参照ID',
  `title` VARCHAR(128) NULL DEFAULT NULL COMMENT '标题',
  `content` LONGTEXT NULL DEFAULT NULL COMMENT '问题描述',
  `fileGroupId` VARCHAR(256) NULL DEFAULT NULL COMMENT '文件组ID',
  `priority` INT(2) NULL DEFAULT '0' COMMENT '优先级 0：一般 1：紧急 2：非常紧急',
  `creator` INT(11) NULL DEFAULT NULL COMMENT '工单创建者ID',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '工单创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '工单更新时间',
  `status` INT(2) NULL DEFAULT NULL COMMENT '工单状态：0:已创建 1：处理中 2：已处理 3：已关闭',
  `transactor` INT(11) NULL DEFAULT NULL COMMENT '当前处理人',
  `anonymity` VARCHAR(64) NULL DEFAULT NULL COMMENT '匿名者名称',
  `anonyTel` VARCHAR(64) NULL DEFAULT NULL COMMENT '匿名者联系方式',
  `workOrderCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '工单编号',
  `customerUserId` INT(11) NULL DEFAULT '0' COMMENT '客户账号ID',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除标志 0：未删除 1：已经删除',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '工单模式表';


-- -----------------------------------------------------
-- Table `csr_workorder_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `csr_workorder_transaction` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refWorkorderId` INT(11) NULL DEFAULT NULL COMMENT '关联的工单ID',
  `suggest` LONGTEXT NULL DEFAULT NULL COMMENT '建议',
  `transactor` INT(11) NULL DEFAULT NULL COMMENT '处理人ID',
  `transactTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '处理时间',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `action` INT(11) NULL DEFAULT NULL COMMENT '动作 0：转交 1：提交建议 2：接受处理 3：完结 4：关闭 5：创建并转交',
  `sendTo` INT(11) NULL DEFAULT NULL COMMENT '下一个处理的人',
  `fileGroupId` VARCHAR(256) NULL DEFAULT NULL COMMENT '关联文件组ID',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '工单事务表';


-- -----------------------------------------------------
-- Table `cus_business_contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_business_contacts` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '递增id',
  `refBussContactId` INT(11) NULL DEFAULT NULL COMMENT '业务联系人ID',
  `description` VARCHAR(168) NULL DEFAULT NULL COMMENT '备注',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除标志 0未删 1被删',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '归属客户ID',
  `refBussGroupCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '业务组代码',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '业务联系人表';


-- -----------------------------------------------------
-- Table `cus_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_company` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '客户归属公司ID',
  `fullName` VARCHAR(255) NULL DEFAULT NULL COMMENT '公司全称',
  `legalPerson` VARCHAR(255) NULL DEFAULT NULL COMMENT '法人',
  `taxCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '公司的税号',
  `license` VARCHAR(256) NULL DEFAULT NULL COMMENT '执照',
  `mainBusiness` VARCHAR(200) NULL DEFAULT NULL COMMENT '主营业务',
  `webUrl` VARCHAR(256) NULL DEFAULT NULL COMMENT '网址链接',
  `depositBank` VARCHAR(128) NULL DEFAULT NULL COMMENT '开户行',
  `bankAccountNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '银行账户',
  `telNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '联系电话',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '客户的参照ID',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除标志',
  `residence` VARCHAR(256) NULL DEFAULT NULL COMMENT '住所',
  `locationCountryCode` VARCHAR(12) NULL DEFAULT NULL COMMENT '所在地国家二字码',
  `locationCityCode` VARCHAR(12) NULL DEFAULT NULL COMMENT '所在地城市二字码',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creatorId` INT(11) NULL DEFAULT NULL COMMENT '创建者ID',
  `creator` VARCHAR(128) NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '公司表';


-- -----------------------------------------------------
-- Table `cus_customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userName` VARCHAR(64) NULL DEFAULT NULL COMMENT '账户名称',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '编码',
  `businessType` VARCHAR(128) NULL DEFAULT NULL COMMENT '业务类型编码',
  `refSalesId` INT(11) NULL DEFAULT NULL COMMENT '销售人员',
  `refSettlementId` INT(11) NULL DEFAULT NULL COMMENT '结算方式',
  `tradingCurrency` VARCHAR(24) NULL DEFAULT NULL COMMENT '交易币种',
  `refCombos` VARCHAR(64) NULL DEFAULT NULL COMMENT '价格套餐',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  `creatorId` INT(11) NULL DEFAULT NULL COMMENT '创建者ID',
  `creator` VARCHAR(128) NULL DEFAULT NULL COMMENT '创建者',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除标志 0：未删 1：已删',
  `status` INT(2) NULL DEFAULT NULL COMMENT '客户状态，予以备用',
  `isLocked` BIT(1) NULL DEFAULT b'0' COMMENT '是否已被锁定',
  `email` VARCHAR(128) NULL DEFAULT NULL COMMENT 'email',
  `evaluateLeval` INT(2) NULL DEFAULT '1' COMMENT '评价等级',
  `authStatus` INT(2) NULL DEFAULT '0' COMMENT '认证状态 0：未认证 1：已填写认证 2：认证通过 ',
  `userType` INT(11) NULL DEFAULT '1' COMMENT '1是物流， 2是贸易， 3两者都是,128 贸易供应商',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户表';


-- -----------------------------------------------------
-- Table `cus_customer_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_address` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `customerId` INT(11) NOT NULL COMMENT '客户的用户id ',
  `name` VARCHAR(45) NULL DEFAULT NULL COMMENT '名字',
  `type` INT(11) NOT NULL COMMENT '类型 1、发货人 2、收货人',
  `transportType` INT(11) NOT NULL DEFAULT '1' COMMENT '1是海运，6是空运，11,14,15,16是陆运',
  `transportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `phone` VARCHAR(45) NULL DEFAULT NULL COMMENT '联系电话',
  `country` VARCHAR(45) NULL DEFAULT NULL COMMENT '国家',
  `province` VARCHAR(45) NULL DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(45) NULL DEFAULT NULL COMMENT '城市',
  `district` VARCHAR(45) NULL DEFAULT NULL COMMENT '地区',
  `street` VARCHAR(45) NULL DEFAULT NULL COMMENT '街道',
  `address` VARCHAR(512) NULL DEFAULT NULL COMMENT '详细地址',
  `postcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '邮政编码',
  `email` VARCHAR(255) NULL DEFAULT NULL COMMENT '电子邮件',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  `fullAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '完整地址',
  `isDefault` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否为默认地址',
  `telPhone` VARCHAR(64) NULL DEFAULT NULL COMMENT '固定电话',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户地址表';

-- -----------------------------------------------------
-- Table `cus_customer_business_feature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_business_feature` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '客户参照ID',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `shipBooking` BIT(1) NOT NULL DEFAULT b'0' COMMENT '订舱功能',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户业务功能模块表';

-- -----------------------------------------------------
-- Table `cus_customer_contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_contacts` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(64) NULL DEFAULT NULL COMMENT '联系人名称',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '联系人代码',
  `department` VARCHAR(128) NULL DEFAULT NULL COMMENT '部门',
  `function` VARCHAR(128) NULL DEFAULT NULL COMMENT '部门职能',
  `mobilePhone` VARCHAR(64) NULL DEFAULT NULL COMMENT '移动电话',
  `phone` VARCHAR(64) NULL DEFAULT NULL COMMENT '电话',
  `duty` VARCHAR(128) NULL DEFAULT NULL COMMENT '职责',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '客户参照ID',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户联系人表';


-- -----------------------------------------------------
-- Table `cus_customer_message_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_message_rel` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `customerId` INT(11) NOT NULL COMMENT '客户的账户id',
  `refParentId` INT(11) NOT NULL COMMENT ' 叶子节点的父节点',
  `refId` INT(11) NOT NULL COMMENT '引用表cus_customer_message_type 的主键，叶子节点',
  `sendType` VARCHAR(64) NOT NULL COMMENT 'email、sms，引用表sys_dict表',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账户消息配置表';


-- -----------------------------------------------------
-- Table `cus_customer_message_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_message_type` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `name` VARCHAR(64) NOT NULL COMMENT '名字',
  `code` VARCHAR(64) NOT NULL COMMENT '编码',
  `parentId` INT(11) NOT NULL DEFAULT '0' COMMENT '父节点',
  `isLeaf` BIT(1) NULL DEFAULT NULL COMMENT '是否叶子节点 0 非叶子节点 1叶子节点',
  `ordinal` INT(11) NOT NULL DEFAULT '0' COMMENT '叶子节点排序',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户消息类型';


-- -----------------------------------------------------
-- Table `cus_customer_message_type_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_message_type_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '字典表id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言种类',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '国际化名称',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cus_customer_message_type_i18n_UN` (`refId` ASC, `language` ASC),
  CONSTRAINT `cus_customer_message_type_i18n_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `cus_customer_message_type` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '字典表国际化表';


-- -----------------------------------------------------
-- Table `cus_customer_order_messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_customer_order_messages` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customerId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT '客户',
  `cusUserId` INT(11) UNSIGNED NULL DEFAULT NULL COMMENT 'cusUserId',
  `businessType` VARCHAR(45) NULL DEFAULT '1' COMMENT '业务类型：1 物流 2 贸易',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '产品的订单编号',
  `orderStatus` VARCHAR(45) NOT NULL COMMENT '订单的状态,例如：1、等待付款 2、正在配货 3、准备发货 等.....',
  `messageType` VARCHAR(45) NULL DEFAULT 'sms' COMMENT 'sms, email',
  `refMessageId` INT(11) NULL DEFAULT NULL,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sendTimes` INT(11) NOT NULL DEFAULT '1' COMMENT '表示该请求是第几次发送',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户订单消息表';


-- -----------------------------------------------------
-- Table `cus_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userName` VARCHAR(64) NOT NULL COMMENT '客户名称',
  `fullName` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户全名',
  `password` VARCHAR(64) NULL DEFAULT NULL COMMENT '密码',
  `credentialSalt` VARCHAR(48) NULL DEFAULT NULL COMMENT '加密盐',
  `email` VARCHAR(255) NULL DEFAULT NULL COMMENT '电子邮箱',
  `mobilePhone` VARCHAR(64) NULL DEFAULT NULL COMMENT '联系电话',
  `isSystem` BIT(1) NULL DEFAULT b'0' COMMENT '是否系统用户 0：不是 1：是',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除 1：删除 0：未删除',
  `isLocked` BIT(1) NULL DEFAULT b'0' COMMENT '用户锁定状态 1：被锁 0：未锁',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最新一次修改时间',
  `refCustomerId` INT(11) NULL DEFAULT NULL COMMENT '客户主账号ID',
  `userAvatar` VARCHAR(64) NULL DEFAULT NULL COMMENT '用户头像',
  `needResetPwd` BIT(1) NULL DEFAULT b'0' COMMENT '是否需要重新设置密码 0：不是 1：是',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户表';


-- -----------------------------------------------------
-- Table `cus_user_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_user_config` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(11) UNSIGNED NOT NULL COMMENT '用户ID',
  `paramName` VARCHAR(64) NOT NULL COMMENT '参数名',
  `paramValue` VARCHAR(256) NOT NULL COMMENT '参数值',
  PRIMARY KEY (`id`),
  INDEX `cus_user_config_userId_FK` (`userId` ASC),
  CONSTRAINT `cus_user_config_userId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `cus_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户配置表';


-- -----------------------------------------------------
-- Table `cus_user_login_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_user_login_info` (
  `userName` VARCHAR(64) NOT NULL COMMENT '用户名',
  `userCode` VARCHAR(64) NOT NULL COMMENT '用户编码',
  `loginTime` DATETIME NOT NULL COMMENT '登录时间',
  `loginStatus` BIT(1) NOT NULL COMMENT '登录状态',
  `loginIp` VARCHAR(64) NOT NULL COMMENT '登录IP',
  `errCode` INT(11) NOT NULL COMMENT '错误代码',
  PRIMARY KEY (`userName`),
  INDEX `userName_FK_idx` (`userName` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户登录信息表';


-- -----------------------------------------------------
-- Table `sys_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '代码',
  `name` VARCHAR(64) NOT NULL DEFAULT '1' COMMENT '名称',
  `domainId` INT(10) NOT NULL DEFAULT '1' COMMENT '域ID',
  `parentId` INT(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `isSystem` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否系统预设 0否 1是',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '软删除标记',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `description` VARCHAR(200) NULL DEFAULT NULL COMMENT '描述',
  `ordinal` INT(11) NOT NULL DEFAULT '0' COMMENT '序号',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `roleId_UNIQUE` (`id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '系统角色表';


-- -----------------------------------------------------
-- Table `cus_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cus_user_role` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `roleId` INT(10) UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `cusUserId_FK` (`userId` ASC),
  INDEX `cusRoleId_FK` (`roleId` ASC),
  CONSTRAINT `cusRoleId_FK`
  FOREIGN KEY (`roleId`)
  REFERENCES `sys_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cusUserId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `cus_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '客户关系表';


-- -----------------------------------------------------
-- Table `edi_data_conversion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edi_data_conversion` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `functionCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '编码',
  `toClassName` VARCHAR(1024) NULL DEFAULT NULL COMMENT '类的全路径',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '系统数据(xml到object等)转换表';


-- -----------------------------------------------------
-- Table `edi_syn_classcargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edi_syn_classcargo` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` VARCHAR(64) NOT NULL COMMENT '班次类型 AIR、SHIP、TRUCK',
  `classNo` VARCHAR(64) NOT NULL COMMENT '班次号 HU8899这样的数据',
  `startTime` DATETIME NOT NULL COMMENT '出发时间，同一个航班每天都有，匹配时只取当天的 年月日格式不含时分秒',
  `cargo` DECIMAL(13,3) NOT NULL COMMENT '剩余仓位',
  `cargoUnit` VARCHAR(64) NOT NULL COMMENT '仓位的单位',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '仓位的余量表';


-- -----------------------------------------------------
-- Table `edi_syn_companyaccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edi_syn_companyaccount` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `bizId` INT(11) NOT NULL COMMENT '客户或者供应商的id,来自业务表',
  `bizAccountId` INT(11) NOT NULL COMMENT '账号的id',
  `type` INT(11) NOT NULL DEFAULT '1' COMMENT '1、供应商。2、客户',
  `clientId` VARCHAR(64) NULL DEFAULT NULL COMMENT 'clientId',
  `apiKey` VARCHAR(128) NULL DEFAULT NULL COMMENT 'apiKey 创建客户和供应商时创建/等同于用户名',
  `securityKey` VARCHAR(128) NULL DEFAULT NULL COMMENT 'securityKey 创建客户和供应商时创建/等同于密码',
  `token` VARCHAR(128) NULL DEFAULT NULL COMMENT 'token',
  `expire` INT(11) NULL DEFAULT NULL COMMENT 'token的有效时间，秒',
  `expireTime` TIMESTAMP NULL DEFAULT NULL COMMENT 'token的失效时间，时间',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `ftpUrl` VARCHAR(256) NULL DEFAULT NULL COMMENT '推消息给对方的ftp，如果setting表中配了ftp,则忽略此。否则使用此设置',
  `ftpUsername` VARCHAR(64) NULL DEFAULT NULL COMMENT '对方的ftp用户名',
  `ftpPassword` VARCHAR(64) NULL DEFAULT NULL COMMENT '对方的ftp密码',
  `synUsername` VARCHAR(128) NULL DEFAULT NULL COMMENT 'http方式的用户名',
  `synPassword` VARCHAR(128) NULL DEFAULT NULL COMMENT 'http方式的密码',
  `synToken` VARCHAR(128) NULL DEFAULT NULL COMMENT 'http方式的token',
  `isEncrypt` INT(11) NULL DEFAULT '0' COMMENT '是否启用传输内容加密',
  `keySign` VARCHAR(64) NULL DEFAULT NULL COMMENT '密钥，供密文形式通信时解密用',
  `permission` VARCHAR(1024) NULL DEFAULT NULL COMMENT '字符串的权限集合',
  `permissionedIP` VARCHAR(256) NULL DEFAULT NULL COMMENT '对方白名单ip',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uni_syn_commpany_type_bizid` (`bizId` ASC, `type` ASC, `bizAccountId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '企业用户访问信息同步表';


-- -----------------------------------------------------
-- Table `edi_syn_companyaccountsetting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `edi_syn_companyaccountsetting` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `clientId` VARCHAR(64) NOT NULL COMMENT 'clientId客户端标识',
  `synType` INT(11) NULL DEFAULT NULL COMMENT '1.ftp,2.rest,3.webservice',
  `synCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '推功能的code，例如推查询的订单：PUSH_ORDERS',
  `synUrl` VARCHAR(45) NULL DEFAULT NULL COMMENT '同步数据的url',
  `synMap` VARCHAR(2048) NULL DEFAULT NULL COMMENT '对象映射的map,程序将使用此映射将EDI的对象转换成对方的对象',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '企业用户设置同步表';


-- -----------------------------------------------------
-- Table `ord_calc_job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_calc_job` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号',
  `type` INT(11) NOT NULL COMMENT '1采购价2销售价',
  `calcType` INT(11) NOT NULL COMMENT '1定时任务2手工计费',
  `isSucceeded` BIT(1) NOT NULL COMMENT '是否计算成功',
  `feeTime` DATETIME NOT NULL COMMENT '运单揽收的时间，即获取报价的时间',
  `feeCalcTime` DATETIME NOT NULL COMMENT '程序运行的时间',
  `workerId` INT(11) NOT NULL COMMENT '定时任务为0，手工点击计费为当前登录系统的人',
  `calcMsg` VARCHAR(4096) NULL DEFAULT NULL COMMENT '计算过程',
  `fromCountry` VARCHAR(45) NOT NULL COMMENT '发件人的国家',
  `fromProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的省份',
  `fromCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的城市',
  `fromDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的地区',
  `fromStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的街道',
  `fromTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `fromAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '发件人的详细地址',
  `fetchCountry` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货人所在的国家',
  `fetchProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的省份',
  `fetchCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的城市',
  `fetchDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的地区',
  `fetchStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的街道',
  `fetchTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `fetchAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '提货的详细地址',
  `toCountry` VARCHAR(45) NOT NULL COMMENT '收件人的国家',
  `toProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的省份',
  `toCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的城市',
  `toDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的地区',
  `toStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的街道',
  `toTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `toAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '收件人的详细地址',
  `cargoType` VARCHAR(45) NOT NULL COMMENT '货物类型',
  PRIMARY KEY (`id`),
  INDEX `idx_ord_calc_job_waybillno` (`orderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '运单的计算信息记录表';


-- -----------------------------------------------------
-- Table `ord_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号编号',
  `waybillNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品的运单编号，草稿状态下为空',
  `channelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '渠道单号',
  `type` INT(11) NULL DEFAULT NULL COMMENT '1、客户下单。2、后台代下单',
  `customerId` INT(11) NOT NULL COMMENT '账户id',
  `cusUserId` INT(11) NOT NULL COMMENT '账户下面的子账号的id',
  `cargoType` VARCHAR(45) NOT NULL COMMENT '货物类型，从sys_dict表中找catalog为biz.cargo.type字段。\n例如：文件、普通小包、普通大包、生鲜、电子、电池',
  `productUid` VARCHAR(64) NOT NULL COMMENT '产品的uid',
  `orderStatus` VARCHAR(45) NOT NULL COMMENT '订单的状态：101、草稿；102、已提交；103、已受理；104、已收货；105、运输中；106、配送中；107、已签收',
  `payStatus` VARCHAR(5) NULL DEFAULT '201' COMMENT '订单 支付状态(待支付:201/已支付:202/申请退款:203/已退款:204)',
  `settlementTypeId` INT(11) NULL DEFAULT NULL COMMENT '结算方式编号，来源表：biz_settlement_type，例如1-SYS-MONTH-月结；2-SYS-HALFMONTH-半月结；3-SYS-YEAR-年结；4-SYS-HALFYEAR-半年结；5-SYS-QUARTER-季度结；6-SYS-INSTANT-现结',
  `cusWeight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '客户称重-货物的重量',
  `cusWeightUnit` VARCHAR(45) NULL DEFAULT 'kg' COMMENT '客户称重-货物的重量单位,默认为kg',
  `supWeight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '供应商称重-货物的重量',
  `supWeightUnit` VARCHAR(45) NULL DEFAULT 'kg' COMMENT '供应商称重-货物的重量单位,默认为kg',
  `feeCalcWeight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '计费重',
  `feeCalcWeightUnit` VARCHAR(64) NULL DEFAULT NULL COMMENT '计费重单位',
  `volumeWeight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '体积重',
  `volumeWeightUnit` VARCHAR(64) NULL DEFAULT NULL COMMENT '体积重单位',
  `transportStatus` INT(11) NOT NULL DEFAULT '0' COMMENT '订单是否发过运输中的物流， 0：未发送过。1：发送过。 ',
  `packageNum` INT(11) NOT NULL COMMENT '包裹的个数（子订单个数）',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被删除 0，未被删除，1已经被删除',
  `validFlag` BIT(1) NOT NULL DEFAULT b'1' COMMENT '订单是否有效，true：有效 false 无效  默认为true',
  `externalNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户的外部关联号，客户自定义',
  `customerNote` VARCHAR(512) NULL DEFAULT NULL COMMENT '客户的留言',
  `deliveryCargoTime` DATETIME NULL DEFAULT NULL COMMENT '取货时间，海运或空运的产品支持 指定时间去提货。',
  `receiveCargoTime` DATETIME NULL DEFAULT NULL COMMENT '收货的时间',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '定单的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '定单的最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `un_ord_order_waybillno` (`orderNo` ASC),
  UNIQUE INDEX `waybillNo_UNIQUE` (`waybillNo` ASC),
  INDEX `idx_ord_order_waybillno` (`orderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品的订单表';


-- -----------------------------------------------------
-- Table `ord_order_additional`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_additional` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `serviceTypeCode` VARCHAR(255) NOT NULL COMMENT '服务类型编码',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务uid',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '定单创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '定单更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_ord_order_additional_waybillno` (`orderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品的订单的附加服务表';


-- -----------------------------------------------------
-- Table `ord_order_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_address` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `fromName` VARCHAR(45) NULL DEFAULT NULL COMMENT '发货人的名字',
  `fromTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '发货人联系电话',
  `fromPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '发货人固定联系电话',
  `fromTransportType` INT(11) NOT NULL DEFAULT '1' COMMENT '11是陆运，1是海运 6是空运，',
  `fromCountry` VARCHAR(45) NOT NULL COMMENT '发件人的国家',
  `fromProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的省份',
  `fromCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的城市',
  `fromDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的地区',
  `fromStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的街道',
  `fromTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `fromAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '发件人的详细地址',
  `fromPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人邮政编码',
  `fromEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `fromCusUserAddressId` INT(11) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `fromDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '联系人地址|固定电话|联系人电话|国家|省份|城市|区域|街道|运输类型|港口或者机场名字|详细地址|邮编',
  `fetchName` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货时要联系的人',
  `fetchTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货人固定联系电话',
  `fetchPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货时要联系的人电话',
  `fetchTransportType` INT(11) NULL DEFAULT NULL COMMENT '11是陆运，1是海运 6是空运，',
  `fetchCountry` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货人所在的国家',
  `fetchProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的省份',
  `fetchCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的城市',
  `fetchDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的地区',
  `fetchStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的街道',
  `fetchTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `fetchAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '提货的详细地址',
  `fetchPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货邮政编码',
  `fetchEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `fetchCusUserAddressId` INT(11) NULL DEFAULT NULL COMMENT '提货地址',
  `fetchDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '联系人地址|固定电话|联系人电话|国家|省份|城市|区域|街道|运输类型|港口或者机场名字|详细地址|邮编',
  `toName` VARCHAR(45) NOT NULL COMMENT '收货人的名字',
  `toTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人固定联系电话',
  `toPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人联系电话',
  `toTransportType` INT(11) NULL DEFAULT '1' COMMENT '11是陆运，1是海运 6是空运',
  `toCountry` VARCHAR(45) NOT NULL COMMENT '收件人的国家',
  `toProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的省份',
  `toCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的城市',
  `toDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的地区',
  `toStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的街道',
  `toTransportId` INT(11) NULL DEFAULT NULL COMMENT '运输工具的站点id',
  `toAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '收件人的详细地址',
  `toPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人邮政编码',
  `toEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `toCusUserAddressId` INT(11) NULL DEFAULT NULL COMMENT '客户收件人地址',
  `toDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '联系人地址|固定电话|联系人电话|国家|省份|城市|区域|街道|运输类型|港口或者机场名字|详细地址|邮编',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '定单的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '定单的最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_ord_fromtoinfo_waybillno` (`orderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单地址表'
  ROW_FORMAT = DYNAMIC;


-- -----------------------------------------------------
-- Table `ord_order_batch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_batch` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `type` INT(11) NOT NULL COMMENT '1、客户批量下单。2、后台批量代下单',
  `externalNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户的外部关联号，客户自定义',
  `batchNumber` VARCHAR(64) NOT NULL COMMENT '批次号',
  `customerId` INT(11) NOT NULL COMMENT '账户id',
  `cusUserId` INT(11) NOT NULL COMMENT '账户下面的子账号的id',
  `productNameOrCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品的名称或者编码',
  `redundanceData` VARCHAR(2048) NULL DEFAULT NULL COMMENT '转换的数据，例如：productId:123|productUid:23437478374234|cargoType:GT001',
  `fetchFlag` BIT(1) NULL DEFAULT b'0' COMMENT '可选服务类型中是否有揽收服务',
  `additionalTypeCode` VARCHAR(128) NULL DEFAULT NULL COMMENT '可选服务类型编码,用竖线|隔开',
  `cargoType` VARCHAR(64) NULL DEFAULT NULL COMMENT '货物类型，从sys_dict表中找catalog为biz.cargo.type字段。文件、普通小包、普通大包、生鲜、电子、电池',
  `packageNum` VARCHAR(64) NULL DEFAULT NULL COMMENT '货物件数包裹的个数（子订单个数）',
  `cusWeight` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户称重-货物的重量',
  `cusWeightUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '客户称重-货物的重量单位',
  `fromName` VARCHAR(45) NULL DEFAULT NULL COMMENT '发货人的名字',
  `fromCountry` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的国家',
  `fromProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的省份',
  `fromCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的城市',
  `fromDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的地区，区县/机场/港口',
  `fromStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人的街道',
  `fromAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '发件人的详细地址',
  `fromTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人联系电话',
  `fromPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人固定联系电话',
  `fromPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '发件人邮政编码',
  `fromEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '发件人电子邮箱',
  `fromDetailCode` VARCHAR(2048) NULL DEFAULT NULL COMMENT '发件人地址冗余:地址类型|国家code|省份id|城市id|城区id|街道id|机场id，或者港口id',
  `fromDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '发件人地址冗余:0联系人名称|1固定电话|2联系人电话|3国家|4省份|5城市|6区域|7街道或者港口名字或者机场名字|8运输类型|9详细地址|10邮编|11邮箱',
  `toName` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人的名字',
  `toCountry` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的国家',
  `toProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的省份',
  `toCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的城市',
  `toDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的地区,区县/机场/港口',
  `toStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人的街道',
  `toAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '收件人的详细地址',
  `toTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人固定联系电话',
  `toPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人联系电话',
  `toPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '收件人邮政编码',
  `toEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `toDetailCode` VARCHAR(2048) NULL DEFAULT NULL COMMENT '收件人地址冗余:地址类型|国家code|省份id|城市id|城区id|街道id|机场id，或者港口id',
  `toDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '发件人地址冗余:0联系人名称|1固定电话|2联系人电话|3国家|4省份|5城市|6区域|7街道或者港口名字或者机场名字|8运输类型|9详细地址|10邮编|11邮箱',
  `fetchName` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货时要联系的人',
  `fetchCountry` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货人所在的国家',
  `fetchProvince` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的省份',
  `fetchCity` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的城市',
  `fetchDistrict` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的地区',
  `fetchStreet` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货的街道',
  `fetchAddress` VARCHAR(512) NULL DEFAULT NULL COMMENT '提货的详细地址',
  `fetchTelephone` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货人固定联系电话',
  `fetchPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货时要联系的人电话',
  `fetchPostcode` VARCHAR(45) NULL DEFAULT NULL COMMENT '提货邮政编码',
  `fetchEmail` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户发件人地址',
  `fetchDetailCode` VARCHAR(2048) NULL DEFAULT NULL COMMENT '揽收人地址编码冗余：地址类型|国家code|省份id|城市id|城区id|街道id',
  `fetchDetail` VARCHAR(2048) NULL DEFAULT NULL COMMENT '发件人地址冗余:0联系人名称|1固定电话|2联系人电话|3国家|4省份|5城市|6区域|7街道或者港口名字或者机场名字|8运输类型|9详细地址|10邮编|11邮箱',
  `goodsDetail` LONGTEXT NULL DEFAULT NULL COMMENT '物品明细的集合的json',
  `attaches` LONGTEXT NULL DEFAULT NULL COMMENT '附件集合的json',
  `customerNote` VARCHAR(512) NULL DEFAULT NULL COMMENT '客户的留言',
  `verifiedStatus` BIT(1) NULL DEFAULT b'0' COMMENT '是否校验通过',
  `createUserId` INT(11) NOT NULL COMMENT '创建者id',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新者id',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_ord_order_batch_batchNumber` (`batchNumber` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '批量下单数据表';


-- -----------------------------------------------------
-- Table `ord_order_cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_cargo` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `goodsNameCn` VARCHAR(45) NULL DEFAULT NULL COMMENT '商品的中文名称',
  `goodsNameEn` VARCHAR(45) NULL DEFAULT NULL COMMENT '商品的英文名称',
  `goodsNumber` INT(11) NOT NULL COMMENT '商品的数量',
  `goodsUnit` VARCHAR(45) NOT NULL COMMENT '商品的单位,从sys_dict表中找catalog为biz.unit.base，例如：件、条、个、辆,',
  `goodsPrice` DECIMAL(18,5) NOT NULL COMMENT '商品的价格，单个商品的价值 ',
  `tradingCurrency` VARCHAR(45) NULL DEFAULT NULL COMMENT '交易的货币,使用的货币类型',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `hsCode` VARCHAR(64) NOT NULL COMMENT '海关编码',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品明细的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '商品明细的最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品的订单的商品明细表';


-- -----------------------------------------------------
-- Table `ord_order_fee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_fee` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号',
  `estimateFee` DECIMAL(13,5) NULL DEFAULT NULL COMMENT '预估费用',
  `estimateCurrencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '预估费用的货币',
  `actualFee` DECIMAL(13,5) NULL DEFAULT NULL COMMENT '实际费用',
  `actualCurrencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '实际费用的货币',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单费用的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '定单费用的最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `ord_order_fee_orderNo` (`orderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品订单的费用表';


-- -----------------------------------------------------
-- Table `ord_order_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_file` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键 编号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号',
  `code` VARCHAR(32) NOT NULL COMMENT '附件类型的code,引用sys_dict,ord.attachment.type',
  `orderStatus` VARCHAR(32) NOT NULL COMMENT '附件添加时订单的状态',
  `fileId` VARCHAR(64) NOT NULL COMMENT '文件id',
  `fileName` VARCHAR(64) NULL DEFAULT NULL COMMENT '文件名称',
  `desciption` VARCHAR(128) NULL DEFAULT NULL COMMENT '文件描述',
  `createUserType` INT(11) NOT NULL COMMENT '0=运营系统;1=物流系统;',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `createUserId` INT(11) NOT NULL COMMENT '创建人id',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后更新人id',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单文件表';


-- -----------------------------------------------------
-- Table `ord_order_service_lwh`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_service_lwh` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `subOrderNo` VARCHAR(64) NOT NULL COMMENT '子订单编号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `cargoNumber` INT(11) NOT NULL COMMENT '货物的件数据',
  `serviceId` VARCHAR(64) NOT NULL COMMENT '服务id或供应商id',
  `length` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '客户或服务商称重-货物的长',
  `width` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '客户或服务商称重-货物的宽',
  `height` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '客户或服务商称重-货物的高',
  `lwhUnit` VARCHAR(45) NULL DEFAULT 'cm' COMMENT '客户或服务商称重-长宽高的单位，例如:厘米，毫米,从sys_dict表中引用，默认为cm',
  `weight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '客户或服务商称重-货物的重量',
  `weightUnit` VARCHAR(45) NULL DEFAULT 'kg' COMMENT '客户或服务商称重-货物的重量单位,默认为kg',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '子定单的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '子定单的最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品的定单的客户或服务商称重表';


-- -----------------------------------------------------
-- Table `ord_order_status_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_status_code` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `rank` INT(11) NOT NULL COMMENT '物流信息等级',
  `code` VARCHAR(255) NOT NULL COMMENT '物流信息状态码',
  `type` INT(11) NULL DEFAULT NULL COMMENT '物流编码是正常or异常。1=正常，2=异常',
  `operType` INT(11) NULL DEFAULT NULL COMMENT '操作类型：操作2  揽收 3 干线4 海关5   配送6，参考字典表sys_dict中的。ord_status_code_opertype',
  `isReviseStatus` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否更新订单状态，true，需要更新，false不需要更新，默认为false',
  `orderStatus` VARCHAR(45) NOT NULL COMMENT '对应的订单应该所处的状态,如果isReviseStatus为true，表示需要修改订单的状态为此状态，反之不需要做修改',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `messageTemplate` VARCHAR(100) NULL DEFAULT NULL COMMENT '消息模板',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '物流状态等级表';


-- -----------------------------------------------------
-- Table `ord_order_status_code_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_status_code_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '物流状态等级表id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言种类',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '国际化名称',
  PRIMARY KEY (`id`),
  INDEX `ord_order_status_code_i18n_ord_order_status_code_idx` (`refId` ASC),
  CONSTRAINT `ord_order_status_code_i18n_ord_order_status_code`
  FOREIGN KEY (`refId`)
  REFERENCES `ord_order_status_code` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '物流状态等级国际化表';


-- -----------------------------------------------------
-- Table `ord_order_tracking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_tracking` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `originalId` INT(11) UNSIGNED NULL DEFAULT '0' COMMENT '原始物流消息的id',
  `code` VARCHAR(255) NOT NULL COMMENT '物流返回的消息编码',
  `type` INT(11) NOT NULL COMMENT '消息的类型（消息进来还是出来）1=系统发出。2=系统接收 3=系统自动生成的提交和已受理tracking 信息,4=页面手动发消息',
  `status` INT(11) NOT NULL COMMENT '消息的状态1、消息未完成。2、消息完成（如果是系统发服务的消息，则就是2）3、消息已经发出去',
  `batchNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '批次号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '产品的订单编号',
  `waybillNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品的运单编号，草稿状态下为空',
  `subOrderNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '子订单编号',
  `serviceOrderNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务的运单编号,即与服务商之间的对应关系',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务uid',
  `supplierId` INT(10) NULL DEFAULT NULL COMMENT '供应商id',
  `trackMessage` VARCHAR(4096) NULL DEFAULT NULL COMMENT '物流的跟踪的信息',
  `messageTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '物流的跟踪信息的时间',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '子定单创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '子定单最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_ord_order_tracking_waybillNo` (`waybillNo` ASC),
  INDEX `idx_ord_order_tracking_subwaybillNo` (`subOrderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品定单的运单跟踪表';


-- -----------------------------------------------------
-- Table `ord_order_tracking_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_order_tracking_message` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `eventCode` VARCHAR(255) NOT NULL COMMENT '消息编码',
  `type` INT(11) NOT NULL COMMENT '消息的类型,从哪个接口保存进来的 1=系统发出 3=系统自动生成的提交和已受理tracking 信息,4=页面手动发消息',
  `status` INT(11) NOT NULL COMMENT '消息的状态1、消息未同步给供应商或客户。2、消息同步给供应商或客户 3、多次尝试未成功，停止同步',
  `batchNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '批次号',
  `waybillNo` VARCHAR(64) NOT NULL COMMENT '运单号',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务UID',
  `supplierId` INT(10) NULL DEFAULT NULL COMMENT '供应商id',
  `trackingReport` TEXT NULL DEFAULT NULL COMMENT '物流的相关报文',
  `retry` INT(11) NULL DEFAULT '0' COMMENT '重发次数',
  `description` VARCHAR(2048) NULL DEFAULT NULL COMMENT '描述',
  `messageTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '物流的跟踪信息的时间',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '子定单创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '子定单最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品定单的运单跟踪表';


-- -----------------------------------------------------
-- Table `ord_service_assign`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_service_assign` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号，一单只有一行，异常的数据需要运营手动重新操作。操作之后更新本行数据',
  `isAssignOk` BIT(1) NOT NULL COMMENT '是否分派成功',
  `stTypes` VARCHAR(255) NULL DEFAULT NULL COMMENT '统计订单总共用到的服务类型,逗号分割',
  `st001Id` INT(11) NULL DEFAULT NULL COMMENT '揽收服务的id',
  `st001Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '揽收服务的uid',
  `st001ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '揽收服务渠道编号',
  `st002Id` INT(11) NULL DEFAULT NULL COMMENT '仓储服务的id',
  `st002Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '仓储服务的uid',
  `st002ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '仓储服务渠道编号',
  `st003Id` INT(11) NULL DEFAULT NULL COMMENT '清关服务的id',
  `st003Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '清关服务的uid',
  `st003ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '清关服务渠道编号',
  `st004Id` INT(11) NULL DEFAULT NULL COMMENT '干线服务的id',
  `st004Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '干线服务的uid',
  `st004ClassId` INT(11) NULL DEFAULT NULL COMMENT '干线服务的班次',
  `st004ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '干线服务渠道编号',
  `st005Id` INT(11) NULL DEFAULT NULL COMMENT '配送服务的id',
  `st005Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '配送服务的uid',
  `st005ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '配送服务渠道编号',
  `st006Id` INT(11) NULL DEFAULT NULL COMMENT '综合服务的id',
  `st006Uid` VARCHAR(64) NULL DEFAULT NULL COMMENT '综合服务的uid',
  `st006ChannelNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '综合服务渠道编号',
  `calcTime` DATETIME NULL DEFAULT NULL COMMENT '计算时间',
  `calcMsg` LONGTEXT NULL DEFAULT NULL COMMENT '计算过程',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  INDEX `ord_service_assign_waybillno` (`orderNo` ASC),
  INDEX `ord_service_assign_uid1channelno1` (`st001Uid` ASC, `st001ChannelNo` ASC),
  INDEX `ord_service_assign_uid2channelno2` (`st002Uid` ASC, `st002ChannelNo` ASC),
  INDEX `ord_service_assign_uid3channelno3` (`st003Uid` ASC, `st003ChannelNo` ASC),
  INDEX `ord_service_assign_uid4channelno4` (`st004Uid` ASC, `st004ChannelNo` ASC),
  INDEX `ord_service_assign_uid5channelno5` (`st005Uid` ASC, `st005ChannelNo` ASC),
  INDEX `ord_service_assign_uid6channelno6` (`st006Uid` ASC, `st006ChannelNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单中同服务类型多服务的分派表';


-- -----------------------------------------------------
-- Table `ord_service_bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_service_bill` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `serviceOrderNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务的订单编号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `type` INT(11) NOT NULL COMMENT '类型 1.采购价 2销售价',
  `amountDue` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '服务的应收款',
  `amountReceived` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '服务的实收款',
  `couponId` INT(11) NULL DEFAULT NULL COMMENT '服务的优惠劵信息,应引用优惠券表的id',
  `billTime` TIMESTAMP NULL DEFAULT NULL COMMENT '服务的计费的时间',
  `productId` INT(11) NULL DEFAULT NULL COMMENT '产品的id',
  `productUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品uid',
  `quotationId` INT(11) NULL DEFAULT NULL COMMENT '报价id',
  `quotationUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '报价uid',
  `serviceId` INT(11) NULL DEFAULT NULL COMMENT '服务的ID',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务的uid',
  `weightSchemaId` INT(11) NULL DEFAULT NULL COMMENT '重量段方案的id',
  `regionId` INT(11) NULL DEFAULT NULL COMMENT '分区id,具体到哪个分区上',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `isBilled` BIT(1) NULL DEFAULT NULL COMMENT '是否已经生成账单',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `supplierId` INT(11) NULL DEFAULT NULL COMMENT '供应商id',
  `feeTypeId` INT(11) NULL DEFAULT NULL COMMENT '费用类型id',
  `currencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '价格货币code',
  `custCurrencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户货币code',
  `custCurrencyAmount` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '客户货币金额',
  `exchangeRate` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '当时汇率',
  `feeWeight` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '计费重',
  `feeWeightUnit` VARCHAR(64) NULL DEFAULT NULL COMMENT '计费重单位',
  `jobId` INT(11) NULL DEFAULT NULL COMMENT '任务的Id',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uni_ord_service_bill_waybillno_type_svcuid` (`orderNo` ASC, `type` ASC, `serviceUid` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务的计费表';


-- -----------------------------------------------------
-- Table `ord_service_bill_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_service_bill_history` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `serviceOrderNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务的订单编号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单编号',
  `type` INT(11) NOT NULL COMMENT '类型 1.采购价 2销售价',
  `amountDue` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '服务的应收款',
  `amountReceived` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '服务的实收款',
  `couponId` INT(11) NULL DEFAULT NULL COMMENT '服务的优惠劵信息,应引用优惠券表的id',
  `billTime` TIMESTAMP NULL DEFAULT NULL COMMENT '服务的计费的时间',
  `productId` INT(11) NULL DEFAULT NULL COMMENT '产品的id',
  `productUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '产品uid',
  `quotationId` INT(11) NULL DEFAULT NULL COMMENT '报价id',
  `quotationUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '报价uid',
  `serviceId` INT(11) NULL DEFAULT NULL COMMENT '服务的ID',
  `serviceUid` VARCHAR(64) NULL DEFAULT NULL COMMENT '服务的uid',
  `weightSchemaId` INT(11) NULL DEFAULT NULL COMMENT '重量段方案的id',
  `regionId` INT(11) NULL DEFAULT NULL COMMENT '分区id,具体到哪个分区上',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `isBilled` BIT(1) NULL DEFAULT NULL COMMENT '是否已经生成账单',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `supplierId` INT(11) NULL DEFAULT NULL COMMENT '供应商id',
  `feeTypeId` INT(11) NULL DEFAULT NULL COMMENT '费用类型id',
  `currencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '价格货币code',
  `custCurrencyCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户货币code',
  `custCurrencyAmount` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '客户货币金额',
  `exchangeRate` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '当时汇率',
  `feeWeight` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '计费重',
  `feeWeightUnit` VARCHAR(64) NULL DEFAULT NULL COMMENT '计费重单位',
  `jobId` INT(11) NULL DEFAULT NULL COMMENT '任务的Id',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务的计费历史表';


-- -----------------------------------------------------
-- Table `ord_service_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_service_order` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '产品的运单编号',
  `serviceUid` VARCHAR(64) NOT NULL COMMENT '服务uid',
  `supplierId` INT(10) NULL DEFAULT NULL COMMENT '供应商id',
  `serviceStatus` INT(11) NOT NULL COMMENT '服务的状态,例如：1、开始处理 2、完成处理',
  `settlementTypeId` INT(11) NULL DEFAULT NULL COMMENT '结算方式编号，例如：现结、月结',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被删除 0，未被删除，1已经被删除',
  `toSupplerNote` VARCHAR(512) NULL DEFAULT NULL COMMENT '给供应商的留言',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务定单的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '服务定单的最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '服务的订单表';


-- -----------------------------------------------------
-- Table `ord_sub_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_sub_order` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `subOrderNo` VARCHAR(64) NOT NULL COMMENT '子订单编号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '产品的订单编号',
  `cargoNumber` INT(11) NOT NULL DEFAULT '0' COMMENT '货物的件数',
  `length` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '货物的长',
  `width` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '货物的宽',
  `height` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '货物的高',
  `lwhUnit` VARCHAR(45) NULL DEFAULT 'cm' COMMENT '长宽高的单位，例如:厘米，毫米,从sys_dict表中引用，默认为cm',
  `weight` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '货物的重量',
  `weightUnit` VARCHAR(45) NULL DEFAULT 'kg' COMMENT '货物的重量单位,默认为kg',
  `orderStatus` VARCHAR(45) NULL DEFAULT NULL COMMENT '子订单的状态，104=已收货，105=运输中，106=配送中，107=已签收',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '子定单的创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '子定单的最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_ord_sub_order_waybillno` (`orderNo` ASC),
  INDEX `idx_ord_sub_order_subWaybillNo` (`subOrderNo` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品的定单的子订单表';


-- -----------------------------------------------------
-- Table `ord_tracking_original`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_tracking_original` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `eventCode` VARCHAR(128) NOT NULL COMMENT '事件的编码',
  `eventSource` VARCHAR(128) NULL DEFAULT NULL COMMENT '事件源',
  `eventTarget` VARCHAR(128) NULL DEFAULT NULL COMMENT '事件的目标',
  `token` VARCHAR(255) NOT NULL COMMENT '交易的token',
  `eventTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '交易的时间',
  `convertStatus` VARCHAR(32) NOT NULL COMMENT '原始物流消息转换为物流消息的状态，待转换，转换成功,不转换(重复消息等错误消息),转换失败,走枚举类',
  `trackingReport` VARCHAR(4096) NULL DEFAULT NULL COMMENT '发过来的消息体',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '产品定单的运单跟踪表';


-- -----------------------------------------------------
-- Table `ord_waybillno_management`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_waybillno_management` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serialNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '订单编号',
  `serviceType` TINYINT(4) NULL DEFAULT NULL COMMENT '业务类型',
  `createUserId` VARCHAR(64) NULL DEFAULT NULL COMMENT '创建者',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `isUsed` BIT(1) NULL DEFAULT b'0' COMMENT '是否已使用,0代表否，1代表是',
  `isPrearranged` BIT(1) NULL DEFAULT b'0' COMMENT '是否为预分配号段',
  `verificationCode` TINYINT(1) NULL DEFAULT NULL COMMENT '校验位',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单管理表';


-- -----------------------------------------------------
-- Table `ord_waybillno_prearrange`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ord_waybillno_prearrange` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `serviceType` TINYINT(1) NULL DEFAULT NULL COMMENT '业务类型',
  `startNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '起始编号',
  `endNumber` VARCHAR(64) NULL DEFAULT NULL COMMENT '终止编号',
  `refId` INT(11) NULL DEFAULT NULL COMMENT '此号段获得者在客户表中的参照ID',
  `currentIndex` VARCHAR(64) NULL DEFAULT NULL COMMENT '当前已被使用的最大号码',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '预分配号码段记录表';


-- -----------------------------------------------------
-- Table `pay_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_account` (
  `id` VARCHAR(32) NOT NULL COMMENT '交易网会员代码',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `password` VARCHAR(120) NULL DEFAULT NULL COMMENT '会员支付密码',
  `salt` VARCHAR(10) NULL DEFAULT NULL COMMENT '加密盐',
  `accountType` INT(11) NOT NULL DEFAULT '2' COMMENT '会员类型 0 汇总 1 物流 2 贸易 3 两者都是 4平台 128 贸易供应商',
  `custProperty` VARCHAR(2) NOT NULL DEFAULT '00' COMMENT '00：默认普通会员',
  `custName` VARCHAR(120) NULL DEFAULT NULL COMMENT '会员名称',
  `idType` VARCHAR(2) NULL DEFAULT NULL COMMENT '会员证件类型',
  `idCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '会员证件号码',
  `nickName` VARCHAR(120) NULL DEFAULT NULL COMMENT '用户昵称',
  `mobilePhone` VARCHAR(64) NULL DEFAULT NULL COMMENT '手机号码',
  `email` VARCHAR(120) NULL DEFAULT NULL COMMENT '邮箱',
  `reserve` VARCHAR(255) NULL DEFAULT NULL COMMENT '保留域',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createUserId` INT(11) NULL DEFAULT NULL,
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '会员帐户信息表';


-- -----------------------------------------------------
-- Table `pay_bankcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_bankcard` (
  `id` VARCHAR(32) NOT NULL COMMENT '会员银行卡id',
  `thirdPayAccountId` VARCHAR(120) NOT NULL COMMENT '第三方支付用户编码',
  `bank` VARCHAR(255) NULL DEFAULT NULL COMMENT '银行名称',
  `subbranch` VARCHAR(255) NULL DEFAULT NULL COMMENT '开户支行',
  `swiftCode` VARCHAR(11) NULL DEFAULT NULL COMMENT 'swift编码',
  `cardNo` VARCHAR(32) NULL DEFAULT NULL COMMENT '卡号',
  `holder` VARCHAR(120) NULL DEFAULT NULL COMMENT '持卡人',
  `mobile` VARCHAR(32) NULL DEFAULT NULL COMMENT '手机号',
  `currency` VARCHAR(20) NULL DEFAULT NULL COMMENT '货币类型',
  `isDefault` BIT(1) NULL DEFAULT b'0' COMMENT '是否默认银行',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `status` INT(11) NULL DEFAULT NULL COMMENT '卡绑定的状态',
  `idType` VARCHAR(12) NULL DEFAULT NULL COMMENT '见文档附录的“接口证件类型说明”，一般都是身份证。例如身份证，送1',
  `idCode` VARCHAR(32) NULL DEFAULT NULL COMMENT '卡主件号码',
  `bankType` INT(11) NULL DEFAULT NULL COMMENT '平安是本行1：本行 2：他行',
  `bankCode` VARCHAR(20) NULL DEFAULT NULL COMMENT '大小额行号',
  `sBankCode` VARCHAR(20) NULL DEFAULT NULL COMMENT '超级网银行号',
  `address` VARCHAR(200) NULL DEFAULT NULL COMMENT '开户行地址',
  `reference` VARCHAR(255) NULL DEFAULT NULL COMMENT '参照',
  `reserve` VARCHAR(255) NULL DEFAULT NULL COMMENT '保留域',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createUserId` INT(11) NULL DEFAULT NULL,
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '会员银行卡信息';


-- -----------------------------------------------------
-- Table `pay_bank_info` 支付增加
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_bank_info` (
  `bankNo` VARCHAR(14) NOT NULL COMMENT '支付行号',
  `status` VARCHAR(1) DEFAULT NULL COMMENT '行号状态',
  `bankClsCode` VARCHAR(3) DEFAULT NULL COMMENT '行别代码',
  `cityCode` VARCHAR(6) DEFAULT NULL COMMENT '城市代码',
  `bankName` VARCHAR(120) DEFAULT NULL COMMENT '行名全称',
  PRIMARY KEY (`bankNo`))
  ENGINE=InnoDB
  DEFAULT CHARSET=utf8
  COMMENT = '行号信息表';


-- -----------------------------------------------------
-- Table `pay_city_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_city_info` (
  `areaCode` VARCHAR(6) NOT NULL,
  `areaName` VARCHAR(60) NOT NULL,
  `areaType` VARCHAR(1) NOT NULL,
  `nodeCode` VARCHAR(4) NOT NULL,
  `topAreaCode1` VARCHAR(6) NULL DEFAULT NULL,
  `topAreaCode2` VARCHAR(6) NULL DEFAULT NULL,
  `topAreaCode3` VARCHAR(6) NULL DEFAULT NULL,
  `oraAreaCode` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`areaCode`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '城市表的表结构';


-- -----------------------------------------------------
-- Table `pay_node_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_node_info` (
  `nodeCode` VARCHAR(4) NOT NULL,
  `nodeName` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`nodeCode`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '省级表的表结构';


-- -----------------------------------------------------
-- Table `pay_third_pay_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_third_pay_account` (
  `id` VARCHAR(32) NOT NULL COMMENT 'UUID',
  `AccountId` VARCHAR(32) NULL DEFAULT NULL COMMENT '会员编码',
  `thirdType` VARCHAR(12) NULL DEFAULT NULL COMMENT '见证宝第三方会员支付类型，见证宝，支付宝，微信支付',
  `thirdPayId` VARCHAR(120) NULL DEFAULT NULL COMMENT '第三方支付用户编码，支付宝，微信支付，平台',
  `thirdPayName` VARCHAR(120) NULL DEFAULT NULL COMMENT '第三方支付用户名称',
  `totalBalance` DOUBLE(12,2) NULL DEFAULT '0.00' COMMENT '账户可用余额',
  `totalAmount` DOUBLE(12,2) NULL DEFAULT '0.00' COMMENT '账户可提现余额',
  `totalFreezeAmount` DOUBLE(12,2) NULL DEFAULT '0.00' COMMENT '账户冻结金额',
  `currency` VARCHAR(20) NULL DEFAULT NULL COMMENT '币种',
  `isDefault` BIT(1) NULL DEFAULT b'0' COMMENT '是否默认支付方式',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `reserve` VARCHAR(255) NULL DEFAULT NULL COMMENT '保留域',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `createUserId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '会员第三方支付信息';


-- -----------------------------------------------------
-- Table `pay_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pay_transaction` (
  `id` VARCHAR(32) NOT NULL COMMENT '交易流水号',
  `thirdBillNo` VARCHAR(120) NULL DEFAULT NULL COMMENT '第三方交易流水号',
  `thirdLogNo` VARCHAR(120) NOT NULL COMMENT '操作流水号',
  `tranType` INT(2) NULL DEFAULT NULL COMMENT '入账类型 0：充值 1：取现 2：支付 3：其它',
  `billNo` VARCHAR(64) NULL DEFAULT NULL COMMENT '账单号',
  `thirdType` VARCHAR(10) NULL DEFAULT NULL COMMENT '支付方式，见证宝，支付宝，微信支付',
  `inThirdPayAccountId` VARCHAR(32) NULL DEFAULT NULL COMMENT '入金员第三方支付编码',
  `inThirdPayAccountName` VARCHAR(255) NULL DEFAULT NULL COMMENT '入金会员第三方支付名称',
  `outThirdPayAccountId` VARCHAR(32) NULL DEFAULT NULL COMMENT '出金会员第三方支付编码',
  `outThirdPayAccountName` VARCHAR(255) NULL DEFAULT NULL COMMENT '出金会员第三方支付名称',
  `inAccountId` VARCHAR(32) NULL DEFAULT NULL COMMENT '入金会员编码或卡号 提现时为银行卡号',
  `inAccountName` VARCHAR(255) NULL DEFAULT NULL COMMENT '入金账户名称或银行 提现时为银行名称',
  `inAccountBalance` DOUBLE(12,2) NULL DEFAULT '0.00' COMMENT '入金账户可用余额',
  `outAccountId` VARCHAR(32) NULL DEFAULT NULL COMMENT '出金会员编码或卡号 充值时为银行卡号',
  `outAccountName` VARCHAR(255) NULL DEFAULT NULL COMMENT '出金账户名称或银行 充值时为银行名称',
  `outAccountBalance` DOUBLE(12,2) NULL DEFAULT '0.00' COMMENT '出金账户可用余额',
  `tranAmount` DOUBLE(12,2) NULL DEFAULT NULL COMMENT '金额 大于0',
  `handFee` DOUBLE(12,2) NULL DEFAULT NULL COMMENT '手续费 大于等于0',
  `currency` VARCHAR(20) NULL DEFAULT NULL COMMENT '币种',
  `tranStatus` INT(2) NULL DEFAULT NULL COMMENT '交易状态 （0：成功，1：失败，3：待确认，4：处理中，5：异常 ）',
  `note` VARCHAR(255) NULL DEFAULT NULL COMMENT '转账备注',
  `describeInfo` VARCHAR(255) NULL DEFAULT NULL COMMENT '交易描述 失败时，返回交易失败原因',
  `reserve` VARCHAR(255) NULL DEFAULT NULL COMMENT '保留域',
  `tranDate` VARCHAR(8) NULL DEFAULT NULL COMMENT '交易日期',
  `tranTime` VARCHAR(6) NULL DEFAULT NULL COMMENT '交易时间',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `createUserId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '会员交易流水信息';


-- -----------------------------------------------------
-- Table `rpt_calc_job`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_calc_job` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `startTime` DATETIME NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` DATETIME NULL DEFAULT NULL COMMENT '结束时间',
  `calcTime` DATETIME NULL DEFAULT NULL COMMENT '计算的时间',
  `workId` INT(11) NULL DEFAULT '0' COMMENT '执行者id',
  PRIMARY KEY (`id`),
  INDEX `rpt_calc_job_bizdate` (`bizDate` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rpt_sale_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_sale_area` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `week` INT(11) NOT NULL COMMENT '周',
  `type` INT(11) NOT NULL COMMENT '1物流，2贸易',
  `countryId` VARCHAR(255) NOT NULL COMMENT '国家的二字码',
  `countryName` VARCHAR(255) NULL DEFAULT NULL COMMENT '国家的name',
  `provinceId` VARCHAR(255) NULL DEFAULT NULL COMMENT '省的id',
  `provinceName` VARCHAR(255) NULL DEFAULT NULL COMMENT '省的name',
  `cityId` VARCHAR(255) NULL DEFAULT NULL COMMENT '城市',
  `cityName` VARCHAR(255) NULL DEFAULT NULL COMMENT '城市的name',
  `amount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '交易额',
  `billCount` INT(11) NULL DEFAULT '0' COMMENT '订单量',
  `volume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '货量',
  `year` INT(11) NOT NULL COMMENT '年',
  `month` INT(11) NOT NULL COMMENT '月',
  `quarter` INT(11) NOT NULL COMMENT '季',
  PRIMARY KEY (`id`),
  INDEX `rpt_sale_area_bizdate` (`bizDate` ASC),
  INDEX `rpt_sale_area_countryid` (`countryId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rpt_sale_customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_sale_customer` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `week` INT(11) NOT NULL COMMENT '周',
  `type` INT(11) NOT NULL COMMENT '1物流，2贸易',
  `customerId` INT(11) NOT NULL COMMENT '客户的id',
  `customerName` VARCHAR(255) NOT NULL COMMENT '客户的name',
  `amount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '交易额',
  `billCount` INT(11) NULL DEFAULT '0' COMMENT '订单量',
  `volume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '货量',
  `year` INT(11) NOT NULL COMMENT '年',
  `month` INT(11) NOT NULL COMMENT '月',
  `quarter` INT(11) NOT NULL COMMENT '季',
  PRIMARY KEY (`id`),
  INDEX `rpt_sale_customer_bizdate` (`bizDate` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rpt_sale_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_sale_product` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `week` INT(11) NOT NULL COMMENT '周',
  `type` INT(11) NOT NULL COMMENT '1物流，2贸易',
  `productId` INT(11) NOT NULL COMMENT '产品/商品的id',
  `productName` VARCHAR(255) NOT NULL COMMENT '产品/商品的name',
  `amount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '交易额',
  `billCount` INT(11) NULL DEFAULT '0' COMMENT '订单量',
  `volume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '货量',
  `year` INT(11) NOT NULL COMMENT '年',
  `month` INT(11) NOT NULL COMMENT '月',
  `quarter` INT(11) NOT NULL COMMENT '季',
  PRIMARY KEY (`id`),
  INDEX `rpt_sale_product_bizdate` (`bizDate` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rpt_sale_summary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_sale_summary` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `week` INT(11) NOT NULL COMMENT '周',
  `logisticsAmount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '物流交易额',
  `logisticsBillCount` INT(11) NULL DEFAULT '0' COMMENT '物流订单量',
  `logisticsVolume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '物流货量',
  `logisticsNewSupCount` INT(11) NULL DEFAULT '0' COMMENT '物流新增供应商',
  `logisticsNewCustCount` INT(11) NULL DEFAULT '0' COMMENT '物流新增客户',
  `tradeAmount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '贸易交易额',
  `tradeBillCount` INT(11) NULL DEFAULT '0' COMMENT '贸易订单量',
  `tradeVolume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '贸易货量',
  `tradeNewSupCount` INT(11) NULL DEFAULT '0' COMMENT '贸易新增供应商',
  `tradeNewCustCount` INT(11) NULL DEFAULT '0' COMMENT '贸易新增客户',
  `year` INT(11) NOT NULL COMMENT '年',
  `month` INT(11) NOT NULL COMMENT '月',
  `quarter` INT(11) NOT NULL COMMENT '季',
  PRIMARY KEY (`id`),
  INDEX `rpt_sale_summary_bizdate` (`bizDate` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `rpt_sale_supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rpt_sale_supplier` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bizDate` DATETIME NOT NULL COMMENT '业务日期',
  `week` INT(11) NOT NULL COMMENT '周',
  `type` INT(11) NOT NULL COMMENT '1物流，2贸易',
  `supplierId` INT(11) NOT NULL COMMENT '供应商的id',
  `supplierName` VARCHAR(255) NOT NULL COMMENT '供应商的name',
  `amount` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '交易额',
  `billCount` INT(11) NULL DEFAULT '0' COMMENT '订单量',
  `volume` DECIMAL(18,3) NULL DEFAULT '0.000' COMMENT '货量',
  `year` INT(11) NOT NULL COMMENT '年',
  `month` INT(11) NOT NULL COMMENT '月',
  `quarter` INT(11) NOT NULL COMMENT '季',
  PRIMARY KEY (`id`),
  INDEX `rpt_sale_supplier_bizdate` (`bizDate` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sup_supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_supplier` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `simpleName` VARCHAR(255) NOT NULL COMMENT '简称',
  `code` VARCHAR(255) NOT NULL COMMENT '编码',
  `website` VARCHAR(255) NULL DEFAULT NULL COMMENT '网址',
  `country` VARCHAR(255) NULL DEFAULT NULL COMMENT '所属国家',
  `rank` DECIMAL(5,2) NULL DEFAULT NULL COMMENT '评分',
  `fax` VARCHAR(255) NULL DEFAULT NULL COMMENT '传真',
  `bizScope` VARCHAR(255) NULL DEFAULT NULL COMMENT '业务范围',
  `mainService` VARCHAR(512) NULL DEFAULT NULL COMMENT '主要服务内容',
  `taxpayerNumber` VARCHAR(255) NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `lealPerson` VARCHAR(255) NULL DEFAULT NULL COMMENT '法人代表',
  `bankAccount` VARCHAR(255) NULL DEFAULT NULL COMMENT '银行账号',
  `bankName` VARCHAR(255) NULL DEFAULT NULL COMMENT '开户银行',
  `address` VARCHAR(255) NULL DEFAULT NULL COMMENT '注册地址',
  `companyTel` VARCHAR(64) NULL DEFAULT NULL COMMENT '公司电话',
  `picture` VARCHAR(64) NULL DEFAULT NULL COMMENT '组织徽标',
  `signerId` INT(11) NULL DEFAULT NULL COMMENT '签订组织的人',
  `orgId` INT(11) NULL DEFAULT NULL COMMENT '与此供应商签署协议的组织',
  `isAudited` BIT(1) NULL DEFAULT NULL COMMENT '供应商自己提供的资料需要后台审核，运营建的不需要审核',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `isDeleted` BIT(1) NULL DEFAULT NULL COMMENT '软删除标记',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `auditUserId` INT(11) NULL DEFAULT NULL COMMENT '审核者',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `type` INT(11) NULL DEFAULT NULL COMMENT '1物流 2贸易 3物流加贸易',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商表';


-- -----------------------------------------------------
-- Table `sup_supplier_bd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_supplier_bd` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `supplierId` INT(11) UNSIGNED NOT NULL COMMENT '供应商ID',
  `bdId` INT(11) NOT NULL COMMENT '对接人ID',
  `responsibility` VARCHAR(255) NULL DEFAULT NULL COMMENT '职责',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  INDEX `sup_supplierbd_supid` (`supplierId` ASC),
  CONSTRAINT `sup_supplierbd_supid`
  FOREIGN KEY (`supplierId`)
  REFERENCES `sup_supplier` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商我方对接人';


-- -----------------------------------------------------
-- Table `sup_supplier_contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_supplier_contacts` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `supplierId` INT(11) UNSIGNED NOT NULL COMMENT '供应商ID',
  `name` VARCHAR(255) NOT NULL COMMENT '姓名',
  `position` VARCHAR(255) NULL DEFAULT NULL COMMENT '职务',
  `department` VARCHAR(255) NULL DEFAULT NULL COMMENT '部门',
  `responsibility` VARCHAR(255) NULL DEFAULT NULL COMMENT '职责',
  `telephone` VARCHAR(255) NULL DEFAULT NULL COMMENT '座机',
  `mobilephone` VARCHAR(255) NULL DEFAULT NULL COMMENT '手机',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  INDEX `supplier_contacts_supid` (`supplierId` ASC),
  CONSTRAINT `supplier_contacts_supid`
  FOREIGN KEY (`supplierId`)
  REFERENCES `sup_supplier` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商联系人表';


-- -----------------------------------------------------
-- Table `sup_supplier_type_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_supplier_type_rel` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `supplierId` INT(11) UNSIGNED NOT NULL COMMENT '供应商ID',
  `serviceTypeCode` VARCHAR(64) NOT NULL COMMENT '服务类型代码',
  PRIMARY KEY (`id`),
  INDEX `sup_type_rel_supplierid` (`supplierId` ASC),
  CONSTRAINT `sup_type_rel_supplierid`
  FOREIGN KEY (`supplierId`)
  REFERENCES `sup_supplier` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商-类型参照表';


-- -----------------------------------------------------
-- Table `sup_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userName` VARCHAR(64) NOT NULL COMMENT '客户名称',
  `fullName` VARCHAR(64) NULL DEFAULT NULL COMMENT '客户全名',
  `password` VARCHAR(64) NULL DEFAULT NULL COMMENT '密码',
  `credentialSalt` VARCHAR(48) NULL DEFAULT NULL COMMENT '加密盐',
  `email` VARCHAR(255) NULL DEFAULT NULL COMMENT '电子邮箱',
  `mobilePhone` VARCHAR(64) NULL DEFAULT NULL COMMENT '联系电话',
  `isSystem` BIT(1) NULL DEFAULT b'0' COMMENT '是否系统用户 0：不是 1：是',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '逻辑删除 1：删除 0：未删除',
  `isLocked` BIT(1) NULL DEFAULT b'0' COMMENT '用户锁定状态 1：被锁 0：未锁',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最新一次修改时间',
  `refSupplier` INT(11) NULL DEFAULT NULL COMMENT '客户主账号ID',
  `userAvatar` VARCHAR(64) NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户表';


-- -----------------------------------------------------
-- Table `sup_user_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_user_config` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` INT(11) UNSIGNED NOT NULL,
  `paramName` VARCHAR(64) NOT NULL,
  `paramValue` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `sup_user_config_userId_FK` (`userId` ASC),
  CONSTRAINT `sup_user_config_userId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `sup_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sup_user_login_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sup_user_login_info` (
  `userName` VARCHAR(64) NOT NULL COMMENT '用户名',
  `userCode` VARCHAR(64) NOT NULL COMMENT '用户编码',
  `loginTime` DATETIME NOT NULL COMMENT '登录时间',
  `loginStatus` BIT(1) NOT NULL COMMENT '登录状态',
  `loginIp` VARCHAR(64) NOT NULL COMMENT '登录IP',
  `errCode` INT(11) NOT NULL COMMENT '错误代码',
  PRIMARY KEY (`userName`),
  INDEX `userName_FK_idx` (`userName` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '供应商用户登录信息表';


-- -----------------------------------------------------
-- Table `sys_authorization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_authorization` (
  `Id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `roleId` INT(11) UNSIGNED NOT NULL COMMENT '角色id',
  `resType` TINYINT(4) NOT NULL COMMENT '资源类型 1：菜单 2：权限项',
  `resId` INT(11) NOT NULL DEFAULT '0' COMMENT '资源id，这里会存放菜单id',
  `permissionId` INT(11) NOT NULL DEFAULT '0' COMMENT '权限id，当resType为2时，这里会存放数据，数据为权限的id',
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC),
  INDEX `permission_roleId_FK_idx` (`roleId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '角色与资源（资源指菜单id与权限id）关系表';


-- -----------------------------------------------------
-- Table `sys_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `catalog` VARCHAR(64) NULL DEFAULT NULL COMMENT '类目',
  `keyName` VARCHAR(255) NOT NULL COMMENT '键名',
  `value` VARCHAR(1024) NULL DEFAULT NULL COMMENT '键值',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `domainId` INT(11) NOT NULL DEFAULT '1' COMMENT '域ID',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '配置表';


-- -----------------------------------------------------
-- Table `sys_dict`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `catalog` VARCHAR(128) NULL DEFAULT NULL COMMENT '类型（标识）',
  `code` VARCHAR(45) NULL DEFAULT NULL COMMENT '编码',
  `name` VARCHAR(64) NULL DEFAULT NULL COMMENT '名字',
  `ordinal` INT(11) NOT NULL DEFAULT '0' COMMENT '序号',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '系统字典表';


-- -----------------------------------------------------
-- Table `sys_dict_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_dict_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '字典表id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言种类',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '国际化名称',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sys_dict_i18n_UN` (`refId` ASC, `language` ASC),
  CONSTRAINT `sys_dict_i18n_sys_dict_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `sys_dict` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '字典表国际化表';


-- -----------------------------------------------------
-- Table `sys_domain`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_domain` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `name` VARCHAR(45) NOT NULL COMMENT '域的名称',
  `parentId` INT(11) NULL DEFAULT '0' COMMENT '父域ID',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '域表';


-- -----------------------------------------------------
-- Table `sys_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_file` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `fileId` VARCHAR(64) NOT NULL COMMENT '文件Id',
  `catalog` VARCHAR(255) NULL DEFAULT 'default' COMMENT '分类目录',
  `fileName` VARCHAR(255) NOT NULL COMMENT '文件名称',
  `fileSize` BIGINT(11) NOT NULL DEFAULT '0' COMMENT '文件大小',
  `contentType` VARCHAR(128) NOT NULL DEFAULT 'application/octet-stream' COMMENT '文件类型',
  `hashCode` VARCHAR(255) NULL DEFAULT NULL COMMENT 'hash',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `fileContent` MEDIUMBLOB NULL DEFAULT NULL COMMENT '文件内容',
  `fileLocation` VARCHAR(512) NULL DEFAULT NULL COMMENT '文件外部存储地址',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `isTempFile` BIT(1) NULL DEFAULT b'1' COMMENT '是否为临时文件',
  `domainId` INT(11) NULL DEFAULT NULL COMMENT '域Id',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `fileId_UNIQUE` (`fileId` ASC),
  INDEX `fileId_INDEX` (`fileId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '文件表';


-- -----------------------------------------------------
-- Table `sys_file_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_file_group` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `groupId` VARCHAR(64) NOT NULL COMMENT '组ID',
  `fileId` VARCHAR(64) NOT NULL COMMENT '文件ID',
  `owners` VARCHAR(128) NULL DEFAULT NULL COMMENT '所有者',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `domainId` INT(11) NULL DEFAULT '1' COMMENT '域ID',
  PRIMARY KEY (`id`),
  INDEX `groupId` (`groupId` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '文件组名';


-- -----------------------------------------------------
-- Table `sys_language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_language` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `code` VARCHAR(45) NOT NULL COMMENT '语言编码',
  `name` VARCHAR(45) NOT NULL COMMENT '语言名称',
  `localName` VARCHAR(45) NOT NULL COMMENT '本地化名称',
  `isHidden` BIT(1) NULL DEFAULT b'0' COMMENT '是否隐藏',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `langCode_UNIQUE` (`code` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '语言表';


-- -----------------------------------------------------
-- Table `sys_log_apicost`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_log_apicost` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `api` VARCHAR(255) NOT NULL COMMENT '应用编程接口',
  `count` DECIMAL(18,0) NULL DEFAULT '0' COMMENT '被调用次数',
  `total` FLOAT NULL DEFAULT '0' COMMENT '调用耗时总计',
  `min` FLOAT NULL DEFAULT '0' COMMENT '单次调用最少耗时',
  `max` FLOAT NULL DEFAULT '0' COMMENT '单次调用最多耗时',
  `last` FLOAT NULL DEFAULT '0' COMMENT '最近一次调用耗时',
  `lastTimestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次调用时间',
  PRIMARY KEY (`id`),
  INDEX `apicost_api` (`api` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = 'API耗时记录表';


-- -----------------------------------------------------
-- Table `sys_logging`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_logging` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `timestmp` BIGINT(20) NOT NULL COMMENT '时间戳',
  `formattedMessage` TEXT NOT NULL COMMENT '格式化消息',
  `loggerName` VARCHAR(254) NOT NULL COMMENT '记录名',
  `levelString` VARCHAR(254) NOT NULL COMMENT '等级字符串',
  `threadName` VARCHAR(254) NULL DEFAULT NULL COMMENT '线程名',
  `callerFilename` VARCHAR(254) NULL DEFAULT NULL COMMENT '调用文件名',
  `callerClass` VARCHAR(254) NULL DEFAULT NULL COMMENT '调用类',
  `callerMethod` VARCHAR(254) NULL DEFAULT NULL COMMENT '调用方法',
  `callerLine` CHAR(4) NULL DEFAULT NULL COMMENT '调用行号',
  `agentId` VARCHAR(45) NULL DEFAULT NULL COMMENT '日志发送者Id，标识服务实例',
  `domainId` INT(11) NULL DEFAULT '1' COMMENT '域Id',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '日志记录表';


-- -----------------------------------------------------
-- Table `sys_mail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_mail` (
  `id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `sender` VARCHAR(128) NOT NULL COMMENT '发送者邮箱',
  `receiver` VARCHAR(512) NOT NULL COMMENT '接受者邮箱列表',
  `cc` VARCHAR(512) NULL DEFAULT NULL COMMENT '抄送人邮件列表',
  `subject` VARCHAR(256) NOT NULL COMMENT '主题',
  `body` MEDIUMTEXT NOT NULL COMMENT '邮件内容',
  `priority` INT(11) NULL DEFAULT '1' COMMENT '优先级：1-normal，2-high',
  `catalog` VARCHAR(64) NULL DEFAULT NULL COMMENT '分类目录',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `scheduledTime` DATETIME NOT NULL COMMENT '计划发送时间',
  `isSent` BIT(1) NULL DEFAULT b'0' COMMENT '已发送',
  `sendTime` DATETIME NULL DEFAULT NULL COMMENT '发送时间',
  `retry` INT(11) NULL DEFAULT '0' COMMENT '重发次数',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `domainId` INT(11) NULL DEFAULT '1' COMMENT '域ID',
  `template` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮件模板',
  `errorCode` VARCHAR(1024) NULL DEFAULT NULL COMMENT '发送错误信息',
  `errorMsg` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '邮件表';


-- -----------------------------------------------------
-- Table `sys_menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` VARCHAR(255) NOT NULL COMMENT '菜单名称',
  `parentId` INT(11) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `url` VARCHAR(1024) NULL DEFAULT '#' COMMENT '菜单Url',
  `icon` VARCHAR(255) NULL DEFAULT NULL COMMENT '菜单图标',
  `target` VARCHAR(255) NULL DEFAULT NULL COMMENT '超链接打开目标',
  `ordinal` INT(11) NULL DEFAULT '0' COMMENT '菜单的顺序',
  `isSystem` BIT(1) NULL DEFAULT b'0' COMMENT '是否是系统菜单，系统初始化进来的菜单为1，别的情况为0.',
  `isHidden` BIT(1) NULL DEFAULT b'0' COMMENT '0表示显示，1代表隐藏',
  `domainId` INT(11) NOT NULL DEFAULT '1' COMMENT '域ID',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `menuId_UNIQUE` (`id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '系统菜单表';


-- -----------------------------------------------------
-- Table `sys_menu_i18n`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_menu_i18n` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单国际化id',
  `refId` INT(11) UNSIGNED NOT NULL COMMENT '菜单id',
  `language` VARCHAR(16) NOT NULL COMMENT '语言区域',
  `localName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对应的国家的菜单名字',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `i18nId_UNIQUE` (`id` ASC),
  UNIQUE INDEX `sys_menu_i18n_UN` (`language` ASC, `refId` ASC),
  INDEX `i18n_menuId_FK_idx` (`refId` ASC),
  CONSTRAINT `i18n_menuId_FK`
  FOREIGN KEY (`refId`)
  REFERENCES `sys_menu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '菜单国际化';


-- -----------------------------------------------------
-- Table `sys_menu_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_menu_permission` (
  `Id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `menuId` INT(11) UNSIGNED NOT NULL COMMENT '菜单id',
  `permissionId` INT(11) UNSIGNED NOT NULL COMMENT '权限id',
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC),
  INDEX `privilege_menuId_FK_idx` (`menuId` ASC),
  INDEX `privilege_privilegeId_FK_idx` (`permissionId` ASC),
  CONSTRAINT `privilege_menuId_FK`
  FOREIGN KEY (`menuId`)
  REFERENCES `sys_menu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '菜单权限表（主要用于存放哪个菜单下挂着哪些权限id）';


-- -----------------------------------------------------
-- Table `sys_operation_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_operation_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `accountId` INT(11) NOT NULL DEFAULT '0' COMMENT '账户Id',
  `module` VARCHAR(255) NULL DEFAULT NULL COMMENT '模块',
  `subModule` VARCHAR(255) NULL DEFAULT NULL COMMENT '子模块',
  `action` VARCHAR(64) NULL DEFAULT NULL COMMENT '动作: create/update/delete/login',
  `operation` VARCHAR(2048) NOT NULL COMMENT '操作日志',
  `operationTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `dataBackup` MEDIUMTEXT NULL DEFAULT NULL COMMENT '数据备份区',
  `level` INT(11) NOT NULL DEFAULT '1' COMMENT '日志级别:1:normal(一般型日志), 2:important(重要日志),3:critical(永久留存)',
  `userIP` VARCHAR(64) NULL DEFAULT NULL COMMENT '用户IP',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '扩展描述',
  `domainId` INT(11) NOT NULL DEFAULT '1' COMMENT '域Id',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '操作记录表';


-- -----------------------------------------------------
-- Table `sys_org`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_org` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `parentId` INT(10) NOT NULL COMMENT '父id',
  `orgGroupId` INT(11) NOT NULL COMMENT '1为组织机构 2为用户组',
  `level` INT(11) NOT NULL COMMENT '层次 第几层',
  `isLeaf` BIT(1) NULL DEFAULT NULL COMMENT '是否叶子节点',
  `isSystem` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否系统预设',
  `name` VARCHAR(64) NOT NULL COMMENT '组织名称',
  `shortName` VARCHAR(64) NOT NULL COMMENT '简称',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `longCode` VARCHAR(512) NULL DEFAULT NULL COMMENT '组织长编码 格式为父节点的编码中间用!分割',
  `code` VARCHAR(64) NOT NULL COMMENT '编码',
  `ordinal` INT(11) NOT NULL COMMENT '序号',
  `address` VARCHAR(255) NULL DEFAULT NULL COMMENT '地址',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`),
  INDEX `orgCode_index` (`code` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '组织机构表';


-- -----------------------------------------------------
-- Table `sys_org_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_org_role` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `orgId` INT(10) UNSIGNED NOT NULL COMMENT '组织ID',
  `roleId` INT(10) UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '组织机构角色表';


-- -----------------------------------------------------
-- Table `sys_permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `catalog` VARCHAR(255) NULL DEFAULT NULL COMMENT '权限分类:系统管理=sys_sys,主数据管理=sys_basedata;客户管理=sys_customer;财务管理=sys_finance;驾驶舱=sys_cockpit;客服=sys_cusservice;',
  `code` VARCHAR(45) NOT NULL COMMENT '权限的编码',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '权限的名字',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '权限的描述',
  `domainId` INT(11) NOT NULL DEFAULT '1' COMMENT '权限的域',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `privilegeId_UNIQUE` (`id` ASC),
  UNIQUE INDEX `privilegeCode_UNIQUE` (`code` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '权限表';


-- -----------------------------------------------------
-- Table `sys_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_sequence` (
  `name` VARCHAR(64) NOT NULL COMMENT '序列名称',
  `currentValue` INT(10) UNSIGNED NOT NULL COMMENT '当前值',
  `stepValue` INT(10) UNSIGNED NOT NULL DEFAULT '1' COMMENT '步长',
  PRIMARY KEY (`name`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '序列表';


-- -----------------------------------------------------
-- Table `sys_sms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_sms` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `phone` VARCHAR(64) NOT NULL COMMENT '短信接收者电话号',
  `templateCode` VARCHAR(64) NULL DEFAULT NULL COMMENT '短信模板，即阿里给返回的标识指定模板的code',
  `body` MEDIUMTEXT NOT NULL COMMENT 'key：value;形式字符串，短信内容',
  `isSent` BIT(1) NULL DEFAULT b'0' COMMENT '是否已经发送 0未发送 1已发送',
  `retry` INT(11) NULL DEFAULT '0' COMMENT '重复发送',
  `sendTime` DATETIME NULL DEFAULT NULL COMMENT '发送时间',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '模板内容里面的key值，用逗号隔开',
  `errorCode` VARCHAR(1024) NULL DEFAULT NULL COMMENT '发送错误信息',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '发送短信表';


-- -----------------------------------------------------
-- Table `sys_template`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_template` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `owner` INT(11) NULL DEFAULT NULL COMMENT '所有者（type为1时，表示公司id）',
  `projectId` INT(11) NULL DEFAULT NULL COMMENT '项目',
  `type` INT(11) NULL DEFAULT NULL COMMENT '1.为贸易订单',
  `path` VARCHAR(256) NULL DEFAULT NULL COMMENT '模板路径',
  `templateName` VARCHAR(256) NULL DEFAULT NULL COMMENT '模板名称',
  `remark` VARCHAR(256) NULL DEFAULT NULL COMMENT '备注',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '模板表';


-- -----------------------------------------------------
-- Table `sys_unit_conversion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_unit_conversion` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `type` VARCHAR(64) NOT NULL COMMENT '单位分类',
  `code` VARCHAR(64) NOT NULL COMMENT '单位编码',
  `value` DECIMAL(18,10) NOT NULL COMMENT '换算值',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '单位换算关系表';


-- -----------------------------------------------------
-- Table `sys_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `code` VARCHAR(64) NULL DEFAULT NULL COMMENT '工号',
  `userName` VARCHAR(64) NOT NULL COMMENT '账号',
  `password` VARCHAR(64) NOT NULL COMMENT '密码',
  `credentialSalt` VARCHAR(48) NULL DEFAULT NULL COMMENT '盐值',
  `email` VARCHAR(255) NULL DEFAULT NULL COMMENT '电子邮件',
  `mobilephone` VARCHAR(64) NULL DEFAULT NULL COMMENT '移动电话',
  `isSystem` BIT(1) NULL DEFAULT b'0' COMMENT '是否系统预设',
  `isLocked` BIT(1) NULL DEFAULT b'0' COMMENT '是否锁定',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `description` VARCHAR(512) NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`userName` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `username_INDEX` (`userName` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户表';


-- -----------------------------------------------------
-- Table `sys_user_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user_config` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(11) UNSIGNED NOT NULL COMMENT '用户ID',
  `paramName` VARCHAR(64) NOT NULL COMMENT '参数名',
  `paramValue` VARCHAR(256) NOT NULL COMMENT '参数值',
  PRIMARY KEY (`id`),
  INDEX `sys_user_config_userId_FK` (`userId` ASC),
  CONSTRAINT `sys_user_config_userId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `sys_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户配置表';


-- -----------------------------------------------------
-- Table `sys_user_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user_detail` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(11) UNSIGNED NOT NULL COMMENT '用户id',
  `fullName` VARCHAR(64) NULL DEFAULT NULL COMMENT '姓名',
  `sex` VARCHAR(4) NULL DEFAULT NULL COMMENT '性别',
  `telephone` VARCHAR(64) NULL DEFAULT NULL COMMENT '座机电话',
  `birthday` DATETIME NULL DEFAULT NULL COMMENT '生日',
  `address` VARCHAR(64) NULL DEFAULT NULL COMMENT '住址',
  `emergencyContact` VARCHAR(20) NULL DEFAULT NULL COMMENT '紧急联系人',
  `emergencyPhone` VARCHAR(20) NULL DEFAULT NULL COMMENT '紧急联系人电话',
  `positionCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '职位代码',
  `avatarId` VARCHAR(64) NULL DEFAULT NULL COMMENT '头像Id',
  PRIMARY KEY (`id`),
  INDEX `id` (`id` ASC),
  INDEX `sys_user_detail_userId_FK` (`userId` ASC),
  CONSTRAINT `sys_user_detail_userId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `sys_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户详情表';


-- -----------------------------------------------------
-- Table `sys_user_login_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user_login_info` (
  `userName` VARCHAR(64) NOT NULL COMMENT '自增型主键',
  `userCode` VARCHAR(64) NOT NULL COMMENT '用户代码',
  `loginTime` DATETIME NOT NULL COMMENT '登录时间',
  `loginStatus` BIT(1) NOT NULL COMMENT '登录状态',
  `loginIp` VARCHAR(64) NOT NULL COMMENT '登录IP',
  `errCode` INT(11) NOT NULL COMMENT '错误代码',
  PRIMARY KEY (`userName`),
  INDEX `userName_FK_idx` (`userName` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户登录信息表';


-- -----------------------------------------------------
-- Table `sys_user_org`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user_org` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `orgId` INT(10) UNSIGNED NOT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `sys_user_org_UK1` (`userId` ASC, `orgId` ASC),
  INDEX `sys_user_org_FK1` (`userId` ASC),
  CONSTRAINT `sys_user_org_FK1`
  FOREIGN KEY (`userId`)
  REFERENCES `sys_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户组织表';


-- -----------------------------------------------------
-- Table `sys_user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `userId` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `roleId` INT(10) UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `userId_FK` (`userId` ASC),
  INDEX `roleId_FK` (`roleId` ASC),
  CONSTRAINT `roleId_FK`
  FOREIGN KEY (`roleId`)
  REFERENCES `sys_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userId_FK`
  FOREIGN KEY (`userId`)
  REFERENCES `sys_user` (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '用户角色表';


-- -----------------------------------------------------
-- Table `trd_billing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_billing` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `billNo` VARCHAR(64) NOT NULL COMMENT '账单号',
  `bizCompanyId` INT(11) NULL DEFAULT NULL COMMENT '业务方id',
  `platformId` INT(11) NULL DEFAULT NULL COMMENT '平台方id',
  `type` INT(2) NOT NULL COMMENT '1.应收账单2.应付账单',
  `releaseTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `currencyType` VARCHAR(255) NULL DEFAULT NULL COMMENT '币种',
  `total` DECIMAL(20,5) NULL DEFAULT NULL COMMENT '总计',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `status` VARCHAR(10) NULL DEFAULT NULL COMMENT '账单状态',
  `isActive` BIT(1) NULL DEFAULT b'1' COMMENT '激活状态标记',
  `isRejected` BIT(1) NULL DEFAULT b'0' COMMENT '拒绝状态标记',
  `startEffectTime` TIMESTAMP NULL DEFAULT NULL COMMENT '账单起始时间',
  `endEffectTime` TIMESTAMP NULL DEFAULT NULL COMMENT '账单结束时间',
  `actualTotal` DECIMAL(20,5) NULL DEFAULT NULL COMMENT '实际金额',
  `adjustmentTypeCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整类型： 上调、下调',
  `adjustmentValue` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整基数',
  `adjustmentUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '调整单位： 百分比或者其他',
  `adjustmentRemark` VARCHAR(512) NULL DEFAULT NULL COMMENT '调整说明',
  `adjustmentTime` TIMESTAMP NULL DEFAULT NULL COMMENT '调整的时间',
  `adjustmentByUser` INT(11) NULL DEFAULT NULL COMMENT '调整人',
  `taskCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '任务编码',
  `billTime` TIMESTAMP NULL DEFAULT NULL COMMENT '出帐时间',
  `url` VARCHAR(255) NULL DEFAULT NULL COMMENT '附加信息链接',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账单表';


-- -----------------------------------------------------
-- Table `trd_billing_order_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_billing_order_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `taskCode` VARCHAR(64) NOT NULL COMMENT '任务代码',
  `billNo` VARCHAR(64) NOT NULL COMMENT '账单号',
  `orderNo` VARCHAR(64) NOT NULL COMMENT '订单号',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账单订单参照表';


-- -----------------------------------------------------
-- Table `trd_billing_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_billing_task` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `taskCode` VARCHAR(64) NOT NULL COMMENT '任务编码',
  `type` INT(2) NOT NULL COMMENT '1.应收账单2.应付账单',
  `orderStartTime` TIMESTAMP NULL DEFAULT NULL COMMENT '订单开始时间',
  `orderEndTime` TIMESTAMP NULL DEFAULT NULL COMMENT '订单结束时间',
  `orderQty` INT(11) NULL DEFAULT NULL COMMENT '订单数量',
  `completeOrderQty` INT(11) NULL DEFAULT NULL COMMENT '完成订单数量',
  `skippedOrderQty` INT(11) NULL DEFAULT NULL COMMENT '跳过订单数量',
  `bizCompanyQty` INT(11) NULL DEFAULT NULL COMMENT '客户/供应商数量',
  `skippedBizCompanyQty` INT(11) NULL DEFAULT NULL COMMENT '跳过客户/供应商数量',
  `completeBizCompanyQty` INT(11) NULL DEFAULT NULL COMMENT '完成客户/供应商数量',
  `taskStartTime` TIMESTAMP NULL DEFAULT NULL COMMENT '任务开始时间',
  `taskEndTime` TIMESTAMP NULL DEFAULT NULL COMMENT '任务结束时间',
  `taskStatus` INT(10) NULL DEFAULT NULL COMMENT '任务状态1已完成2处理中3异常',
  `isRegenerate` BIT(1) NULL DEFAULT b'0' COMMENT '是否重新生成',
  `regenerateTimes` INT(11) NULL DEFAULT NULL COMMENT '重新生成次数',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `bizCompanyIds` LONGTEXT NULL DEFAULT NULL COMMENT '选择生成账单的客户/供应商',
  `platformIds` LONGTEXT NULL DEFAULT NULL COMMENT '选择生成账单的平台方',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '账单任务表';


-- -----------------------------------------------------
-- Table `trd_goods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_goods` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `businessType` VARCHAR(45) NULL DEFAULT NULL COMMENT '业务类型 1、物流 2、贸易',
  `goodsTypeId` INT(11) NULL DEFAULT NULL COMMENT '商品类别 从表trd_goods_type获取',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '名称',
  `code` VARCHAR(45) NULL DEFAULT NULL COMMENT '编码',
  `hsCode` VARCHAR(255) NULL DEFAULT NULL COMMENT 'HS编码（海关编码）',
  `standardsName` VARCHAR(45) NULL DEFAULT NULL COMMENT '规格名称',
  `standardsCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '规格编码',
  `unit` VARCHAR(45) NULL DEFAULT NULL COMMENT '计量单位',
  `price` DECIMAL(18,5) NULL DEFAULT NULL COMMENT '单价',
  `supplierId` INT(11) NULL DEFAULT NULL COMMENT '供应商',
  `supplierType` INT(11) NULL DEFAULT NULL COMMENT '供应商类型： 1： 供应商；2： 平台公司',
  `purchaserLimit` INT(1) NULL DEFAULT NULL COMMENT '采购限制： 0： 全部企业； 1： 指定企业',
  `projectLimit` INT(1) NULL DEFAULT NULL COMMENT '项目限制： 0: 全部项目； 1： 指定项目',
  `status` INT(11) NULL DEFAULT NULL COMMENT '状态',
  `isEnabled` BIT(1) NULL DEFAULT b'0' COMMENT '启用，停用',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `currencyType` VARCHAR(45) NULL DEFAULT NULL COMMENT '币种',
  `image` VARCHAR(255) NULL DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '商品表';


-- -----------------------------------------------------
-- Table `trd_goods_project_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_goods_project_rel` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `goodsId` INT(11) NULL DEFAULT NULL COMMENT '商品ID',
  `projectId` INT(11) NULL DEFAULT NULL COMMENT '项目ID',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '商品项目限制关系';


-- -----------------------------------------------------
-- Table `trd_goods_purchase_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_goods_purchase_rel` (
  `id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `goodsId` INT(11) NULL DEFAULT NULL COMMENT '商品ID',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `customerCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户编码',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '商品-客户参照表';


-- -----------------------------------------------------
-- Table `trd_goods_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_goods_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR(32) NULL DEFAULT NULL COMMENT '名称',
  `code` VARCHAR(32) NULL DEFAULT NULL COMMENT '编码',
  `ordinal` INT(11) NULL DEFAULT NULL COMMENT '排序',
  `parentId` INT(11) NOT NULL DEFAULT '0' COMMENT '父id',
  `descption` VARCHAR(200) NULL DEFAULT NULL COMMENT '描述',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `level` INT(11) NULL DEFAULT '1' COMMENT '级别',
  `isLeaf` BIT(1) NOT NULL COMMENT '叶子节点标记',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '商品类型表';


-- -----------------------------------------------------
-- Table `trd_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `businessType` VARCHAR(45) NULL DEFAULT NULL COMMENT '业务类型1： 物流； 2： 贸易',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单号',
  `orderName` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单名称',
  `customerId` INT(11) NULL DEFAULT NULL COMMENT '客户id',
  `cusUserId` INT(11) NULL DEFAULT NULL COMMENT '子帐号',
  `orderChannel` INT(11) NULL DEFAULT NULL COMMENT '下单渠道： 1： 客户下单； 2： 后台代下单',
  `orderType` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单类型',
  `projectId` INT(11) NULL DEFAULT NULL COMMENT '项目id',
  `projectCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '项目编码',
  `sellerId` INT(11) NULL DEFAULT NULL COMMENT '销售方 1、上海至精供应链管理股份有限公司 2、上海尚融供应链管理有限公司',
  `purchaserId` INT(11) NULL DEFAULT NULL COMMENT '采购方',
  `customerNote` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户留言',
  `orderStatus` VARCHAR(45) NULL DEFAULT '1' COMMENT '订单状态 1、草稿 2、待确认 3、待发货 4、运输中 4、已签收',
  `acceptStatus` INT(11) NULL DEFAULT NULL COMMENT '受理的状态，默认为0，代表未处理。1代表受理成功。其它编码代表受理失败',
  `orderTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单日期',
  `deliveryTypeId` INT(11) NULL DEFAULT NULL COMMENT '交货方式',
  `deliverySite` VARCHAR(255) NULL DEFAULT NULL COMMENT '交货地点',
  `receiptTime` TIMESTAMP NULL DEFAULT NULL COMMENT '收货时间',
  `deliveryTime` TIMESTAMP NULL DEFAULT NULL COMMENT '交货日期',
  `receiverName` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiverPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货人联系电话',
  `transportTypeId` INT(11) NULL DEFAULT NULL COMMENT '运输方式',
  `checkTypeId` INT(11) NULL DEFAULT NULL COMMENT '验收方式',
  `settlementTypeId` INT(11) NULL DEFAULT NULL COMMENT '结算方式',
  `packingTypeId` INT(11) NULL DEFAULT NULL COMMENT '包装方式',
  `otherClauses` VARCHAR(45) NULL DEFAULT NULL COMMENT '其他条款',
  `amountDue` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '应收金额',
  `amountReceived` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '实收金额',
  `currencyCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '货币类型',
  `custCurrencyAmount` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '客户币种应收金额',
  `custCurrencyAmountReceived` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '客户币种实收金额',
  `custCurrencyCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '客户币种',
  `exchangeRate` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '当时汇率',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  `isSaleBilled` BIT(1) NULL DEFAULT b'0' COMMENT '是否已生成销售账单',
  `isPurchaseBilled` BIT(1) NULL DEFAULT b'0' COMMENT '是否已生成采购账单',
  `businessNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '业务单号',
  `vat` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '增值税',
  `noVat` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '含增值税的金额',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '贸易订单表';


-- -----------------------------------------------------
-- Table `trd_order_confirmation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order_confirmation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `confirmationNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '确认单号',
  `dealTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '制表日期',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单号',
  `custCompanyName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对方公司名称',
  `custCompanyNickName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对方称呼',
  `custCompanyAddress` VARCHAR(2000) NULL DEFAULT NULL COMMENT '对方公司地址',
  `custOrderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '对方订单编号',
  `shipName` VARCHAR(255) NULL DEFAULT NULL COMMENT '船名',
  `imoCode` VARCHAR(45) NULL DEFAULT NULL COMMENT 'IMO编号',
  `destinationPort` VARCHAR(255) NULL DEFAULT NULL COMMENT '目的港',
  `mooringSite` VARCHAR(255) NULL DEFAULT NULL COMMENT '停泊位置',
  `transportStartTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '运输开始时间',
  `transportEndTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '运输结束时间',
  `transportDuration` DOUBLE NULL DEFAULT NULL COMMENT '运输时长',
  `transportDurationUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '运输时长单位',
  `sailStartTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '预计开航开始时间',
  `sailEndTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '预计开航结束时间',
  `reachStartTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '预计抵达开始时间',
  `reachEndTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '预计抵达结束时间',
  `paymentClause` VARCHAR(2000) NULL DEFAULT NULL COMMENT '付款条款',
  `purchaser` VARCHAR(2000) NULL DEFAULT NULL COMMENT '采购方',
  `agent` VARCHAR(2000) NULL DEFAULT NULL COMMENT '代理',
  `note` VARCHAR(2000) NULL DEFAULT NULL COMMENT '留言',
  `contractClause` VARCHAR(2000) NULL DEFAULT NULL COMMENT '合同条款',
  `representativeName` VARCHAR(45) NULL DEFAULT NULL COMMENT '我方代表姓名',
  `representativePhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '我方代表联系电话',
  `companyAddress` VARCHAR(2000) NULL DEFAULT NULL COMMENT '我方公司地址',
  `companyPhone` VARCHAR(45) NULL DEFAULT NULL COMMENT '我方电话',
  `companyFax` VARCHAR(45) NULL DEFAULT NULL COMMENT '我方传真',
  `companyEmail` VARCHAR(45) NULL DEFAULT NULL COMMENT '我方邮箱',
  `fileId` VARCHAR(45) NULL DEFAULT NULL COMMENT '文件id',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '修改者',
  `jsonData` LONGTEXT NULL DEFAULT NULL COMMENT '请求数据',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单确认表';


-- -----------------------------------------------------
-- Table `trd_order_delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order_delivery` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `deliveryNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '运单号',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单号',
  `custCompanyName` VARCHAR(255) NULL DEFAULT NULL COMMENT '对方公司名称',
  `custCompanyAddress` VARCHAR(2000) NULL DEFAULT NULL COMMENT '对方公司地址',
  `dealer` VARCHAR(45) NULL DEFAULT NULL COMMENT '交易员',
  `dealTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '制表日期',
  `shipName` VARCHAR(255) NULL DEFAULT NULL COMMENT '船名',
  `destinationPort` VARCHAR(2000) NULL DEFAULT NULL COMMENT '目的港',
  `receiptTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '收货日期',
  `vat` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '增值税',
  `noVat` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '不含增值税总金额',
  `paymentTime` VARCHAR(45) NULL DEFAULT NULL COMMENT '最迟付款日期',
  `bank` VARCHAR(255) NULL DEFAULT NULL COMMENT '银行',
  `bankAddress` VARCHAR(2000) NULL DEFAULT NULL COMMENT '银行地址',
  `swiftCode` VARCHAR(45) NULL DEFAULT NULL COMMENT 'Swift代码',
  `account` VARCHAR(45) NULL DEFAULT NULL COMMENT '帐号',
  `accountHolder` VARCHAR(45) NULL DEFAULT NULL COMMENT '开户人',
  `currencyCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '币种',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `fileId` VARCHAR(45) NULL DEFAULT NULL COMMENT '生成的文件id',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '修改者',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `jsonData` LONGTEXT NULL DEFAULT NULL COMMENT '请求数据',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单派送表';


-- -----------------------------------------------------
-- Table `trd_order_file_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order_file_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单号',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '名称',
  `orderStatus` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '订单状态',
  `type` INT(11) NULL DEFAULT NULL COMMENT '附件类型的code,引用sys_dict,trd.order.file.type',
  `fileId` VARCHAR(45) NULL DEFAULT NULL COMMENT '文件',
  `remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  `createUserId` INT(11) NULL DEFAULT NULL COMMENT '创建者',
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL COMMENT '最近一次修改者',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单文件参照表';


-- -----------------------------------------------------
-- Table `trd_order_goods_rel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order_goods_rel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单号',
  `goodsId` INT(11) NULL DEFAULT NULL COMMENT '商品id',
  `goodsNum` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '采购数量',
  `amountDue` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '采购金额',
  `isDeleted` BIT(1) NULL DEFAULT b'0' COMMENT '软删除标记',
  `goodsCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '商品代码',
  `goodsName` VARCHAR(255) NULL DEFAULT NULL COMMENT '商品名称',
  `goodsTypeId` INT(11) NULL DEFAULT NULL COMMENT '商品类型ID',
  `goodsHSCode` VARCHAR(45) NULL DEFAULT NULL COMMENT '商品海关编码',
  `goodsUnit` VARCHAR(45) NULL DEFAULT NULL COMMENT '商品计量单位',
  `goodsPrice` DECIMAL(35,5) NULL DEFAULT NULL COMMENT '商品单价',
  `goodsCurrencyType` VARCHAR(45) NULL DEFAULT NULL COMMENT '商品币种',
  `type` INT(1) NULL DEFAULT '1' COMMENT '1: 用户下单商品； 2： 收货确认单商品',
  `minLimit` DECIMAL(35,4) NULL DEFAULT NULL COMMENT '采购下限',
  `maxLimit` DECIMAL(35,4) NULL DEFAULT NULL COMMENT '采购上限',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单-商品参照表';


-- -----------------------------------------------------
-- Table `trd_order_tracking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_order_tracking` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `orderNo` VARCHAR(45) NULL DEFAULT NULL COMMENT '订单编号',
  `fromStatus` VARCHAR(45) NULL DEFAULT NULL COMMENT '原始订单状态',
  `toStatus` VARCHAR(45) NULL DEFAULT NULL COMMENT '变更后订单状态',
  `message` VARCHAR(255) NULL DEFAULT NULL COMMENT '变更说明',
  `updateUserId` INT(11) NULL DEFAULT NULL COMMENT '操作人',
  `updateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '订单跟踪表';


-- -----------------------------------------------------
-- Table `trd_platform_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_platform_company` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `code` VARCHAR(255) NULL DEFAULT NULL COMMENT '代码',
  `name` VARCHAR(255) NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '平台企业表';


-- -----------------------------------------------------
-- Table `trd_platform_company_bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_platform_company_bank` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `companyId` INT(11) NULL DEFAULT NULL COMMENT '平台公司id',
  `companyCode` VARCHAR(255) NULL DEFAULT NULL COMMENT '平台公司编码',
  `bank` VARCHAR(255) NULL DEFAULT NULL COMMENT '银行',
  `address` VARCHAR(255) NULL DEFAULT NULL COMMENT '银行地址',
  `swift` VARCHAR(255) NULL DEFAULT NULL COMMENT 'swift编码',
  `accountNo` VARCHAR(255) NULL DEFAULT NULL COMMENT '帐号',
  `holder` VARCHAR(255) NULL DEFAULT NULL COMMENT '持卡人',
  `currency` VARCHAR(255) NULL DEFAULT NULL COMMENT '货币类型',
  `reference` VARCHAR(255) NULL DEFAULT NULL COMMENT 'Reference编码',
  `isDeleted` BIT(1) NULL DEFAULT NULL COMMENT '是否有效',
  `isDefault` BIT(1) NULL DEFAULT NULL COMMENT '是否默认银行',
  `createTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdateTime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createUserId` INT(11) NULL DEFAULT NULL,
  `lastUpdateUserId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `trd_project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `bizTypeCode` VARCHAR(64) NOT NULL COMMENT '业务类型',
  `name` VARCHAR(255) NOT NULL COMMENT '项目名称',
  `code` VARCHAR(255) NOT NULL COMMENT '项目编码',
  `orgId` INT(11) NOT NULL COMMENT '负责部门id',
  `profile` VARCHAR(1000) NULL DEFAULT NULL COMMENT '概况',
  `value` VARCHAR(1000) NULL DEFAULT NULL COMMENT '价值',
  `risk` VARCHAR(1000) NULL DEFAULT NULL COMMENT '风险',
  `summary` VARCHAR(2000) NULL DEFAULT NULL COMMENT '项目总结',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0：未删除 1删除',
  `isReject` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否被打回',
  `firstAuditMsg` VARCHAR(1000) NULL DEFAULT NULL COMMENT '初审意见',
  `finalAuditMsg` VARCHAR(1000) NULL DEFAULT NULL COMMENT '终审意见',
  `firstAuditTime` TIMESTAMP NULL DEFAULT NULL COMMENT '初审时间',
  `finalAuditTime` TIMESTAMP NULL DEFAULT NULL COMMENT '终审时间',
  `riskManId` INT(11) NULL DEFAULT NULL COMMENT '风控人',
  `financeManId` INT(11) NULL DEFAULT NULL COMMENT '财务审核人',
  `operationManId` INT(11) NULL DEFAULT NULL COMMENT '运营审核人',
  `finalManId` INT(11) NULL DEFAULT NULL COMMENT '最终审核人',
  `status` INT(2) NULL DEFAULT NULL COMMENT '项目状态1.草稿 2.待初审3.待终审 4已立项 5已结项 6已关闭',
  `createUserId` INT(11) NOT NULL COMMENT '创建人',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `lastUpdateUserId` INT(11) NOT NULL COMMENT '最后修改人',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '贸易项目表';


-- -----------------------------------------------------
-- Table `trd_project_audit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project_audit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `projectId` INT(11) NOT NULL COMMENT '项目id',
  `type` INT(11) NULL DEFAULT NULL COMMENT '1.风控 2.财务3.运营',
  `auditorId` INT(11) NOT NULL COMMENT '审核人id',
  `isPass` BIT(1) NULL DEFAULT NULL COMMENT '是否通过',
  `msg` VARCHAR(1000) NULL DEFAULT NULL COMMENT '意见',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '项目审核表';


-- -----------------------------------------------------
-- Table `trd_project_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project_company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `projectId` INT(11) NOT NULL COMMENT '项目id trd_project',
  `type` INT(11) NOT NULL COMMENT '1.供应商2.客户3.平台',
  `companyId` INT(11) NOT NULL COMMENT '企业id',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '项目关联企业表';


-- -----------------------------------------------------
-- Table `trd_project_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project_file` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `projectId` INT(11) NOT NULL COMMENT '项目id',
  `type` INT(2) NULL DEFAULT NULL COMMENT '1项目文件2问询文件3合同文件',
  `fileId` VARCHAR(64) NOT NULL COMMENT '文件ID',
  `remark` VARCHAR(256) NULL DEFAULT NULL COMMENT '文件描述',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `name` VARCHAR(64) NULL DEFAULT NULL COMMENT '文件名称',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '项目文件表';


-- -----------------------------------------------------
-- Table `trd_project_kpi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project_kpi` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `projectName` VARCHAR(255) NOT NULL COMMENT '项目名称',
  `annualizedReturn` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '年化收益率',
  `turnoverDay` INT(11) NULL DEFAULT NULL COMMENT '周转天数',
  `overdueRate` DECIMAL(13,3) NULL DEFAULT NULL COMMENT '逾期率',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除，0：未删除。1：已删除',
  `createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `trd_project_question`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trd_project_question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `projectId` INT(11) NOT NULL COMMENT '项目id',
  `type` INT(11) NULL DEFAULT NULL COMMENT '1风控问题2财务问题3运营问题',
  `userId` INT(11) NULL DEFAULT NULL COMMENT '参与人id',
  `fileIds` VARCHAR(256) NULL DEFAULT NULL COMMENT '文件列表',
  `isDeleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 0：未删除 1删除',
  `content` VARCHAR(1000) NULL DEFAULT NULL COMMENT '问题或回答的内容',
  `createTime` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
  `questionId` INT(11) NULL DEFAULT NULL COMMENT '问题Id',
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COMMENT = '项目问题表';