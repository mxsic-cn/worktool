
alter table  table_1  add name4 varchar(32);

-- update version
SET SQL_SAFE_UPDATES = 0;
UPDATE `sys_config` SET `value` = '15.0' WHERE `catalog` = 'sys.database.tables.schema' AND `keyName`= 'version';

-- v14.1

ALTER TABLE biz_verifyRules RENAME TO biz_verify_rules;

ALTER TABLE  `edi_syn_companyaccount`
  DROP INDEX `uni_syn_commpany_type_bizid`,
  ADD  UNIQUE INDEX `uni_syn_commpany_type_bizid` (`bizId`, `type`, `bizAccountId`);

DROP TABLE IF EXISTS `cus_customer_business_feature`;

CREATE TABLE `cus_customer_business_feature` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增型主键',
  `refCustomerId` int(11) DEFAULT NULL COMMENT '客户参照ID',
  `isDeleted` bit(1) DEFAULT b'0' COMMENT '软删除标记',
  `shipBooking` bit(1) NOT NULL DEFAULT b'0' COMMENT '订舱功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1273 DEFAULT CHARSET=utf8 COMMENT='客户业务功能模块表';


-- v14.2

INSERT INTO `sys_permission` (id,`catalog`,code,name,description,domainId) VALUES
  (166300,'sys-funds-account','base-funds-account:mgt','base funds account management','base funds account management',1);
INSERT INTO `sys_menu_permission` (`menuId`,`permissionId`) VALUES
  (166300,166300);

-- 更新物流状态表的数据顺序
SET foreign_key_checks=0;
TRUNCATE table ord_order_status_code;
INSERT INTO ord_order_status_code
(`rank`, code, `type`, operType, isReviseStatus, orderStatus, createTime, lastUpdateTime, messageTemplate, description)
VALUES
  (1, '201', 1, 2, 1, '102', sysdate(), sysdate(), '下单成功，等待系统确认', '已提交')
  ,(3, '202', 1, 2, 0, '', sysdate(), sysdate(), '订单已发送至供应商，等待受理', NULL)
  ,(1, '203', 1, 2, 1, '103', sysdate(), sysdate(), '已成功受理，通知收货', '已受理')
  ,(1, '204', 1, 2, 0, '', sysdate(), sysdate(), '货物正在分拣扫描', NULL)
  ,(2, '205', 2, 2, 0, '', sysdate(), sysdate(), '受理失败，产品已停用', NULL)
  ,(3, '206', 2, 2, 0, '', sysdate(), sysdate(), '受理失败，供应商变更', NULL)
  ,(2, '207', 2, 2, 0, '', sysdate(), sysdate(), '客户催件', NULL)
  ,(3, '209', 2, 2, 0, '', sysdate(), sysdate(), '供应商修改订单信息', NULL)
  ,(2, '210', 2, 2, 0, '', sysdate(), sysdate(), '运单破损', NULL)
  ,(3, '211', 2, 2, 0, '', sysdate(), sysdate(), '客户问题（公司重组、拆分、破产等）导致订单中断', NULL)
  ,(3, '212', 2, 2, 0, '', sysdate(), sysdate(), '货物遗失', NULL)
  ,(3, '213', 2, 2, 0, '', sysdate(), sysdate(), '货物有问题（违法、扣查等）', NULL)
  ,(3, '214', 2, 2, 0, '', sysdate(), sysdate(), '供应商问题（公司重组、拆分、破产等）导致订单中断', NULL)
  ,(2, '215', 2, 2, 0, '', sysdate(), sysdate(), '受理失败，客户账户异常', NULL)
  ,(1, '301', 1, 3, 0, '', sysdate(), sysdate(), '正在前往【{0}】收货', NULL)
  ,(2, '302', 1, 3, 0, '', sysdate(), sysdate(), '货物信息核实无误', NULL)
  ,(1, '303', 1, 3, 0, '', sysdate(), sysdate(), '货物已到达【{0}】，正在入库', NULL)
  ,(1, '304', 1, 3, 1, '104', sysdate(), sysdate(), '货物已到达【{0}】，入仓完成', '已收货')
  ,(2, '305', 2, 3, 0, '', sysdate(), sysdate(), '揽收失败，无法联系客户', NULL)
  ,(2, '306', 2, 3, 0, '', sysdate(), sysdate(), '揽收失败，货物类型不符', NULL)
  ,(2, '307', 2, 3, 0, '', sysdate(), sysdate(), '揽收失败，货物重量不符', NULL)
  ,(2, '308', 2, 3, 0, '', sysdate(), sysdate(), '揽收失败，货物件数不符', NULL)
  ,(1, '401', 1, 4, 1, '105', sysdate(), sysdate(), '货物已出仓，下一站【{0}】', '运输中')
  ,(1, '402', 1, 4, 0, '', sysdate(), sysdate(), '货物已到达【{0}】，下一站【{1}】', NULL)
  ,(1, '403', 1, 4, 0, '', sysdate(), sysdate(), '货物已到达目的地【{0}】，运输完成准备配送', NULL)
  ,(1, '404', 1, 4, 0, '', sysdate(), sysdate(), '货物已到达目的地【{0}】，运输完成等待自提', NULL)
  ,(1, '405', 2, 4, 0, '', sysdate(), sysdate(), '战争、经济制裁无法运输', NULL)
  ,(1, '406', 2, 4, 0, '', sysdate(), sysdate(), '天气或自然灾害无法运输', NULL)
  ,(1, '407', 2, 4, 0, '', sysdate(), sysdate(), '客户更改地址', NULL)
  ,(2, '408', 2, 4, 0, '', sysdate(), sysdate(), '报关延迟，报关材料不全', NULL)
  ,(2, '501', 1, 5, 0, '', sysdate(), sysdate(), '海关抽检', NULL)
  ,(1, '502', 1, 5, 0, '', sysdate(), sysdate(), '【{0}】报关中', NULL)
  ,(1, '503', 1, 5, 0, '', sysdate(), sysdate(), '【{0}】报关完毕，下一站【{1}】', NULL)
  ,(1, '504', 1, 5, 0, '', sysdate(), sysdate(), '【{0}】清关中', NULL)
  ,(1, '505', 1, 5, 0, '', sysdate(), sysdate(), '【{0}】海关放行，下一站【{1}】', NULL)
  ,(3, '506', 2, 5, 0, '', sysdate(), sysdate(), '海关扣押，货物为违禁品', NULL)
  ,(2, '507', 2, 5, 0, '', sysdate(), sysdate(), '海关扣押，申报价值不符', NULL)
  ,(1, '601', 1, 6, 1, '106', sysdate(), sysdate(), '货物正在配送', '配送中')
  ,(1, '602', 1, 6, 1, '107', sysdate(), sysdate(), '客户已签收，正常签收', '已签收')
  ,(1, '603', 1, 6, 1, '107', sysdate(), sysdate(), '客户已签收，代签收', '已签收')
  ,(1, '604', 1, 6, 1, '107', sysdate(), sysdate(), '客户已签收，客户自提', '已签收')
  ,(1, '605', 2, 6, 0, '', sysdate(), sysdate(), '客户更改地址', NULL)
  ,(2, '606', 2, 6, 0, '', sysdate(), sysdate(), '收件地址为敏感部门', NULL)
  ,(2, '607', 2, 6, 0, '', sysdate(), sysdate(), '签收延迟，收件人要求延迟配送', NULL)
  ,(1, '608', 2, 6, 0, '', sysdate(), sysdate(), '签收失败，收件人无法联系', NULL)
  ,(2, '609', 2, 6, 0, '', sysdate(), sysdate(), '签收失败，货物破损，收件人拒收', NULL)
  ,(2, '610', 2, 6, 0, '', sysdate(), sysdate(), '签收失败，货物类型不符，收件人拒收', NULL)
  ,(2, '611', 2, 6, 0, '', sysdate(), sysdate(), '签收失败，货物重量不符，收件人拒收', NULL)
  ,(2, '612', 2, 6, 0, '', sysdate(), sysdate(), '签收失败，费用有异议，收件人拒收', NULL)
  ,(3, '613', 2, 6, 0, '', sysdate(), sysdate(), '派错件、漏件、件数不符', NULL);

-- 更改pay_account表 `mobilePhone`字段和`idCode`字段
Alter TABLE `pay_account`
  modify column `idCode` VARCHAR(64) DEFAULT NULL COMMENT '会员证件号码',
  modify column `mobilePhone` VARCHAR(64) DEFAULT NULL COMMENT '手机号码';

-- 删除pay_account表中垃圾数据
DELETE FROM pay_account WHERE accountType = 7;

-- 删除pay_third_pay_account表中垃圾数据
DELETE FROM pay_third_pay_account WHERE NOT EXISTS
(SELECT pay_account.id FROM pay_account  WHERE pay_account.id
                                               = pay_third_pay_account.AccountId);

--  v14.3

-- 删除pay_account表中垃圾数据
DELETE FROM pay_account WHERE accountType = 128;


INSERT INTO  trd_platform_company
( code, name)
VALUES('PLAT004', '上海至精国际贸易有限公司');

