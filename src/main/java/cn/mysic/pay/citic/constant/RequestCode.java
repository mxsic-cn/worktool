package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *              交易请求码列表
 *
 *  交易名称	                        交易请求代码
    内部转账	                        DLINETRN
    对外支付	                        DLOUTTRN
    财务报销	                        DLEXPENS
    手动归集	                        CMMANSWP
    手动下拨	                        CMDWNSWP
    附属账户支付转账	                DLCTNSUB
    附属账户内部转账	                DLSINSUB
    附属账户资金初始化	                DLINISUB
    附属账户调账	                    DLMIXSUB
    活期转定期经办	                DLFIXSUB
    定期转活期经办	                DLADVSUB
    活期转通知经办	                DLNTOPEN
    取款通知经办	                    DLNTDRAW
    通知取消经办	                    DLNTSTOP
    通知存款提前支取	                DLNTADVA
    证券转银行经办	                DLAGBSUB
    银行转证券经办	                DLBKASUB
    应付帐单	                        DLOWNPAY
    额度管理经办	                    DLEDGLSQ
    自动归集参数设置经办	            DLPARSU0
    自动下拨参数设置经办	            DLPARSU1
    联动下拨参数设置经办	            DLPARSU2
    不归集额度设置经办	                DLCMGSUB
    不归集额度计划新增经办	            DLCPLSUB
    不归集额度计划管理	                DLPLMSUB
    成员单位利息计算经办	            DLPRCSUB
    成员单位利息分配经办	            DLPPDSUB
    附属账户利息计算经办	            DLSJXSUB
    附属账户利息分配经办	            DLLFPSUB
    附属账户费用计算经办	            DLFEESUB
    附属账户参数设置经办	            DLSUBPAR
    协议代理收款经办	                DLPAGSUB
    保证金开立经办	                DLDPTSUB
    代理支付转账	                    DLETPXPY
    通知型智能存款解除关联	            DLFITMNT
    定期型智能存款解除关联	            DLFITMFX
    预付卡备付金支付经办	            DLBFJYPY
    非预付卡备付金支付经办	            DLBFJFPY
    对外支付（香港账户-集团客户）	    DLCBITRN
    信银国际外汇买卖（香港账户-集团客户）	DLFXDTRN
    备付金归集经办	                DLBFJCPY
    备付金特殊交易	                DLBFJPAY
 */
public final class RequestCode {
    public static final String INTERNAL_TRANSFER                                                        = "DLINETRN";
    public static final String EXTERNAL_PAYMENT                                                         = "DLOUTTRN";
    public static final String FINANCE_EXPENSE	                                                        = "DLEXPENS";
    public static final String MANUAL_COLLECTION                                                        = "CMMANSWP";
    public static final String MANUAL_ALLOCATED                                                         = "CMDWNSWP";
    public static final String SUB_ACCOUNT_PAYMENT_TRANSFER                                             = "DLCTNSUB";
    public static final String SUB_ACCOUNT_INTERNAL_TRANSFER                                            = "DLSINSUB";
    public static final String SUB_ACCOUNT_FUND_INIT                                                    = "DLINISUB";
    public static final String SUB_ACCOUNT_TRANSFER                                                     = "DLMIXSUB";
    public static final String CURRENT_TO_REGULAR_HANDLE                                                = "DLFIXSUB";
    public static final String REGULAR_TO_CURRENT_HANDLE                                                = "DLADVSUB";
    public static final String CURRENT_TO_NOTICE_HANDLE                                                 = "DLNTOPEN";
    public static final String WITHDRAWALS_NOTICE_HANDLE                                                = "DLNTDRAW";
    public static final String NOTICE_CANCEL_CHANGRE                                                    = "DLNTSTOP";
    public static final String NOTICE_DEPOSIT_EARLY_WITHDRAWAL                                          = "DLNTADVA";
    public static final String SECURITIES_TO_BANK_HANDLE                                                = "DLAGBSUB";
    public static final String BANK_TO_SECURITIES_HANDLE                                                = "DLBKASUB";
    public static final String SHOULD_PAY_BILL                                                          = "DLOWNPAY";
    public static final String QUOTA_MANAGEMENT_HANDLE                                                  = "DLEDGLSQ";
    public static final String AUTO_COLLECTION_PARAMETER_SETTING_HANDLE                                 = "DLPARSU0";
    public static final String AUTO_ALLOCATED_PARAMETER_SETTING_HANDLE                                  = "DLPARSU1";
    public static final String LINKAGE_ALLOCATED_PARAMETER_SETTING_HANDLE                               = "DLPARSU2";
    public static final String NON_COLLECTION_QUOTA_SETTING_HANDLE                                      = "DLCMGSUB";
    public static final String NON_COLLECTION_QUOTA_PLAN_ADD_HANDLE                                     = "DLCPLSUB";
    public static final String NON_COLLECTION_QUOTA_PLAN_MANAGEMENT                                     = "DLPLMSUB";
    public static final String MEMBER_UNIT_INTEREST_CALCULATION_HANDLE                                  = "DLPRCSUB";
    public static final String MEMBER_UNIT_INTEREST_DISTRIBUTION_HANDLE                                 = "DLPPDSUB";
    public static final String SUB_ACCOUNT_INTEREST_CALCULATION_HANDLE                                  = "DLSJXSUB";
    public static final String SUB_ACCOUNT_INTEREST_DISTRIBUTION_HANDLE                                 = "DLLFPSUB";
    public static final String SUB_ACCOUNT_COST_CALCULATION_HANDLE                                      = "DLFEESUB";
    public static final String SUB_ACCOUNT_PARAMETER_SETTING_HANDLE                                     = "DLSUBPAR";
    public static final String PROTOCOL_AGENT_RECEIPT_HANDLE                                            = "DLPAGSUB";
    public static final String MARGIN_OPEN_ACCOUNT_HANDLE                                               = "DLDPTSUB";
    public static final String PROXY_PAYMENT_TRANSFER                                                   = "DLETPXPY";
    public static final String NOTIFICATION_SMART_DEPOSIT_DEALLOCATED                                   = "DLFITMNT";
    public static final String REGULAR_SMART_DEPOSIT_DEALLOCATED                                        = "DLFITMFX";
    public static final String PREPAID_CARD_PREPAID_PAYMENT_HANDLE                                      = "DLBFJYPY";
    public static final String NON_PREPAID_CARD_PREPAID_PAYMENT_HANDLE                                  = "DLBFJFPY";
    public static final String EXTERNAL_PAYMENT_HK_ACCOUNT_GROUP_CUSTOMERS                              = "DLCBITRN";
    public static final String CITIC_FOREIGN_CURRENCY_TRADING_HK_ACCOUNT_GROUP_CUSTOMERS                = "DLFXDTRN";
    public static final String RESERVE_PAYMENT_COLLECTION_HANDLE                                        = "DLBFJCPY";
    public static final String RESERVE_PAYMENT_SPECIAL_TRANSACTION                                      = "DLBFJPAY";
}
