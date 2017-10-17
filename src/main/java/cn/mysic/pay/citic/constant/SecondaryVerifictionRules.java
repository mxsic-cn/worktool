package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
    二级核验规则对照表
 
 S5BM101	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
 S5BM102	备付金银行已增加备付金银行账户余额、支付机构业务系统未增加客户资金账户发生额
 S5BM103	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
 S5BM201	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
 S5BM202	备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户发生额
 S5BM203	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
 S5BM204	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
 S5BM301	备付金非活期存款转活期存款
 S5BM302	备付金活期存款转非活期存款
 S5BM303	备付金非活期账户发生额
 S5BM401	备付金银行账户变动金额
 S5BM501	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
 S5BM502	备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户发生额
 S5BM503	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
 S5BM504	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
 S5BM601	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
 S5BM602	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
 S5BM603	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
 S5BM604	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
 S5BM701	客户资金期末余额
 S5BM702	本期业务系统中客户资金账户借方发生额
 S5BM703	本期业务系统中客户资金账户贷方发生额
 S5BM704	本期出入金业务客户资金账户变动金额
 S5BM801	向备付金银行缴存现金形式的备付金
 S5BM802	向备付金银行缴存现金形式预付卡押金
 S5BM803	办理预付卡先行现金赎回业务
 S5BM804	以转账方式退回购卡押金
 S5BM805	办理预付卡押金先行现金赎回业务
 S5BM806	备付金银行间头寸调拨
 S5BM807	收到利息收入
 S5BM808	备付金非活期存款转活期存款
 S5BM809	备付金活期存款转非活期存款
 S5BM810	备付金活期存款转非活期存款
 S5BM901	期初客户资金账户余额
 S5BM902	期末客户资金账户余额
 S5BM903	客户资金账户余额余额净增加（减少）
 S5BM904	减：本期接受现金形式的客户备付金金额
 S5BM905	加：本期向备付金银行缴存现金备付金
 S5BM906	加：本期以自有资金先行赎回预付卡的金额
 S5BM907	减：本期向备付金存管银行办理预付卡先行赎回资金结转业务金额
 S5BM908	加：本期实现的手续费收入
 S5BM909	减：本期支付机构已增加客户资金余额，备付金银行未增加备付金银行账户余额
 S5BM910	加：本期支付机构已减少客户资金余额，备付金银行未减少备付金银行账户余额
 S5BM911	加：本期备付金银行已增加备付金银行账户余额，支付机构未增加客户资金余额
 S5BM912	减：本期备付金银行已减少备付金银行账户余额，支付机构未减少客户资金余额
 S5BM913	加：本期收到利息收入
 S5BM914	加：本期申请存放的自有资金
 S5BM915	减：本期银行扣取的手续费、管理费等费用
 S5BM916	减：本期结转风险准备金
 S5BM917	减：本期结转利息收入
 S5BM918	减：本期结转手续费收入
 S5BM919	减：本期提取原申请存放的自有资金
 S5BM921	试算值：（客户资金账户余额变动加减调整项后的数值）
 S5BM923	备付金银行账户余额期末值
 S5BM924	备付金银行账户余额净增加（减少）
 S5BM925	实际值减式算值
 S5BM926	在备付金银行存放的预付卡押金本期净增加数
 S5BMA01	备付金银行账户余额
 S5BMA02	减：备付金银行账户中未结转的备付金银行存款利息余额（累计实现的利息收入总额-累计计提的风险准备金-累计结转的利息收入）
 S5BMA03	减：备付金银行账户中累计申请存放的自有资金余额（累计申请存放的-累计申请提回的）
 S5BMA04	减：期未结转的支付业务净收入余额（累计实现的收入-累计扣取的手续费支出-累计结转的手续费收入）
 S5BMA05	加：期末以现金形式持有的客户备付金余额（累计接受的现金形式客户备付金-累计缴存备付金银行金额）
 S5BMA06	减：本期期末仍存在的以自有资金先行偿付的预付卡赎回金额（累计以自有资金先行偿付金额-累计向存管银行申请结转金额）
 S5BMA07	加：支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额
 S5BMA08	减：支付机构业务系统已减少客户资金账户余额，备付金银行未减少备付金银行账户余额
 S5BMA09	减：备付金银行已增加备付金银行备付金账户余额，支付机构业务系统未增加客户资金账户余额
 S5BMA10	加：备付金银行已减少备付金银行备付金账户余额，支付机构业务系统未减少客户资金账户余额
 S5BMA12	支付机构客户资金账户余额试算值（备付金账户余额扣减调整项及未达账后得出的数值）
 S5BMA13	支付机构客户资金账户余额实际值
 S5BMA14	实际值-试算值
 S5BMA15	在备付金银行存放的预付卡押金金额
 S5BMA16	未达账项中应收押金金额的合计数
 S5BMB01	本期业务系统中借记客户资金账户金额
 S5BMB02	本期银行实际出金金额
 S5BMB03	分账户表与汇总表关系
 S5BMC01	备付金银行账户变动额∑X1-∑X2=L24
 S5BMC02	备付金银行账户变动额∑X1-∑X2=t日M1-(t-1)日M1
 S5BMC03	备付金银行账户变动额∑X1-∑X2=t日∑O5-(t-1)日∑O5
 S5BMD01	备付金银行账户期末余额合计
 S5BMD02	管理账户合计-期末账户数量
 S5BMD03	管理账户合计-期末余额
 S5BMD04	管理账户合计余额
 S5BME01	本期支付机构业务系统已反映且本期实际入金金额
 S5BME02	本期支付机构业务系统已反映且本期实际收到款项
 S5BME03	应收入金业务金额
 S5BME04	支付机构业务系统中未反映但银行已经收到的款项
 S5BME05	本期支付机构业务系统中客户资金账户借方发生额
 S5BME06	期末现金形式的客户备付金余额
 S5BME07	表1-8期末以自有资金先行赎回预付卡的余额
 S5BME08	表1-13期末以自有资金先行赎回预付卡的余额
 */
public final class SecondaryVerifictionRules {

    public static final String  PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_OCCURRENCE_101              = "S5BM101";   	//	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
    public static final String  PROVISION_BANK_INCREASED_PAYMENT_AGENCY_NOT_INCREAS_CUSTOMER_FUNDS_OCCURRENCE_102               = "S5BM102";   	//	备付金银行已增加备付金银行账户余额、支付机构业务系统未增加客户资金账户发生额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_OCCURRENCE_103                  = "S5BM103";   	//	备付金银行已减少备付金银行账户余额、支付机构业务系统未减少客户资金账户发生额
    public static final String  PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_OCCURRENCE_201              = "S5BM201";   	//	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
    public static final String  PROVISION_BANK_INCREASED_PAYMENT_AGENCY_NOT_INCREAS_CUSTOMER_FUNDS_OCCURRENCE_202               = "S5BM202";   	//	备付金银行已增加备付金银行账户余额、支付机构业务系统未增加客户资金账户发生额
    public static final String  PAYMENT_AGENCY_REDUCED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_REDUCE_OCCURRENCE_203                  = "S5BM203";   	//	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_OCCURRENCE_204                  = "S5BM204";   	//	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
    public static final String  PROVISION_NON_CURRENT_TO_CURRENT_301                                                            = "S5BM301";   	//	备付金非活期存款转活期存款
    public static final String  PROVISION_CURRENT_TO_NON_CURRENT_302                                                            = "S5BM302";   	//	备付金活期存款转非活期存款
    public static final String  PROVISION_NON_CURRENT_ACCOUNT_OCCURRENCE                                                        = "S5BM303";   	//	备付金非活期账户发生额
    public static final String  PROVISION_BANK_ACCOUNT_OCCURRENCE                                                               = "S5BM401";   	//	备付金银行账户变动金额
    public static final String  PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_OCCURRENCE_501              = "S5BM501";   	//	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
    public static final String  PROVISION_BANK_INCREASED_PAYMENT_AGENCY_NOT_INCREAS_CUSTOMER_FUNDS_OCCURRENCE_502               = "S5BM502";   	//	备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户发生额
    public static final String  PAYMENT_AGENCY_REDUCED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_REDUCE_OCCURRENCE_503                  = "S5BM503";   	//	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_OCCURRENCE_504                  = "S5BM504";   	//	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
    public static final String  PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_OCCURRENCE_601              = "S5BM601";   	//	支付机构业务系统已增加客户资金账户金额、备付金银行未增加备付金银行账户发生额
    public static final String  PAYMENT_AGENCY_REDUCED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_REDUCE_OCCURRENCE_602                  = "S5BM602";   	//	支付机构业务系统已减少客户资金账户金额、备付金银行未减少备付金银行账户发生额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_OCCURRENCE_603                  = "S5BM603";   	//	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_OCCURRENCE_604                  = "S5BM604";   	//	备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户发生额
    public static final String  CUSTOMER_FUNDS_ENDING_BALANCE                                                                   = "S5BM701";   	//	客户资金期末余额
    public static final String  THIS_PERIOD_BUSINESS_CUSTOMER_ACCOUNT_DEBIT_OCCURRENCE                                          = "S5BM702";   	//	本期业务系统中客户资金账户借方发生额
    public static final String  THIS_PERIOD_BUSINESS_CUSTOMER_ACCOUNT_LENDER_OCCURRENCE                                         = "S5BM703";   	//	本期业务系统中客户资金账户贷方发生额
    public static final String  THIS_PERIOD_DISCREPANCY_BUSINESS_CUSTOMER_ACCOUNT_OCCURRENCE                                    = "S5BM704";   	//	本期出入金业务客户资金账户变动金额
    public static final String  PROVISION_CASH_TO_PROVISION_BANK                                                                = "S5BM801";   	//	向备付金银行缴存现金形式的备付金
    public static final String  PROVISION_CASH_TO_PROVISION_BANK_PREPAID_CARD_DEPOSIT                                           = "S5BM802";   	//	向备付金银行缴存现金形式预付卡押金
    public static final String  PREPAID_CARD_FIRST_CASH_REDEMPTION_BUSINESS_                                                    = "S5BM803";   	//	办理预付卡先行现金赎回业务
    public static final String  RETURN_DEPOSIT_BY_TRANSFER                                                                      = "S5BM804";   	//	以转账方式退回购卡押金
    public static final String  PREPAID_DEPOSIT_PREPAID_CASH_REDEMPTION_BUSINESS                                                = "S5BM805";   	//	办理预付卡押金先行现金赎回业务
    public static final String  PROVISION_BANKS_POSITION_ALLOCATION                                                             = "S5BM806";   	//	备付金银行间头寸调拨
    public static final String  RECEIVE_INTEREST_INCOME                                                                         = "S5BM807";   	//	收到利息收入
    public static final String  PROVISION_NON_CURRENT_TO_CURRENT_808                                                            = "S5BM808";   	//	备付金非活期存款转活期存款
    public static final String  PROVISION_CURRENT_TO_NON_CURRENT_809                                                            = "S5BM809";   	//	备付金活期存款转非活期存款
    public static final String  PROVISION_CURRENT_TO_NON_CURRENT_810                                                            = "S5BM810";   	//	备付金活期存款转非活期存款
    public static final String  BEGINNING_CLIENT_FUNDS_ACCOUNT_BALANCE                                                          = "S5BM901";   	//	期初客户资金账户余额
    public static final String  CLOSING_CLIENT_FUNDS_ACCOUNT_BALANCE                                                            = "S5BM902";   	//	期末客户资金账户余额
    public static final String  CUSTOMER_ACCOUNT_BALANCE_NET_INCREASE_OR_DECREASE                                               = "S5BM903";   	//	客户资金账户余额余额净增加（减少）
    public static final String  THIS_PERIOD_ACCEPTED_PROVISION_IN_CASH_REDUCE                                                   = "S5BM904";   	//	减：本期接受现金形式的客户备付金金额
    public static final String  THIS_PERIOD_DEPOSIT_PROVISION_TO_PROVISION_BANK_IN_CASH_PLUS                                    = "S5BM905";   	//	加：本期向备付金银行缴存现金备付金
    public static final String  THIS_PERIOD_OWN_FUNDS_PRIOR_REDEMPTION_PREPAID_CARD_MONEY_PLUS                                  = "S5BM906";   	//	加：本期以自有资金先行赎回预付卡的金额
    public static final String  THIS_PERIOD_PROVISION_BANK_HANDLE_PREPAID_CARD_PRIOR_REDEMPTION_TRANSFER_FUNDS_MONEY_REDUCE     = "S5BM907";   	//	减：本期向备付金存管银行办理预付卡先行赎回资金结转业务金额
    public static final String  THIS_PERIOD_SERVICE_CHARGES_INCOMDE_PLUS                                                        = "S5BM908";   	//	加：本期实现的手续费收入
    public static final String  THIS_PERIOD_PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_BALANCE_REDUCE  = "S5BM909";   	//	减：本期支付机构已增加客户资金余额，备付金银行未增加备付金银行账户余额
    public static final String  THIS_PERIOD_PROVISION_BANK_INCREASED_CUSTOMER_FUNDS_PAYMENT_AGENCY_NOT_INCREASE_BALANCE_PLUS    = "S5BM910";   	//	加：本期支付机构已减少客户资金余额，备付金银行未减少备付金银行账户余额
    public static final String  THIS_PERIOD_PROVISION_BANK_INCREASED_ACCOUNT_PAYMENT_AGENCY_NOT_INCREASE_CUSTOMER_BALANCE_PLUS  = "S5BM911";   	//	加：本期备付金银行已增加备付金银行账户余额，支付机构未增加客户资金余额
    public static final String  THIS_PERIOD_PROVISION_BANK_REDUCED_ACCOUNT_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_BALANCE_REDUCE    = "S5BM912";   	//	减：本期备付金银行已减少备付金银行账户余额，支付机构未减少客户资金余额
    public static final String  THIS_PERIOD_RECEIVE_INTEREST_INCOME_PLUS                                                        = "S5BM913";   	//	加：本期收到利息收入
    public static final String  THIS_PERIOD_APPLY_DEPOSIT_OWN_FUNDS_PLUS                                                        = "S5BM914";   	//	加：本期申请存放的自有资金
    public static final String  THIS_PERIOD_BANK_DEDUCT_SERVICE_MANAGEMENT_FEE_ETC_REDUCE                                       = "S5BM915";   	//	减：本期银行扣取的手续费、管理费等费用
    public static final String  THIS_PERIOD_CARRY_FORWARD_RISK_RESERVE_REDUCE                                                   = "S5BM916";   	//	减：本期结转风险准备金
    public static final String  THIS_PERIOD_CARRY_FORWARD_INTEREST_INCOME_REDUCE                                                = "S5BM917";   	//	减：本期结转利息收入
    public static final String  THIS_PERIOD_CARRY_FORWARD_FEE_INCOME_REDUCE                                                     = "S5BM918";   	//	减：本期结转手续费收入
    public static final String  THIS_PERIOD_DRAW_DEPOSIT_FUNDS_REDUCE                                                           = "S5BM919";   	//	减：本期提取原申请存放的自有资金
    public static final String  TRIAL_VALUE                                                                                     = "S5BM921";   	//	试算值：（客户资金账户余额变动加减调整项后的数值）
    public static final String  PROVISION_BANK_ACCOUNT_BALANCE_END_VALUE                                                        = "S5BM923";   	//	备付金银行账户余额期末值
    public static final String  PROVISION_BANK_ACCOUNT_BALANCE_NET_INCREASE_OR_REDUCE                                           = "S5BM924";   	//	备付金银行账户余额净增加（减少）
    public static final String  ACTUAL_BALUE_REDUCED_TRIAL_VALUE                                                                = "S5BM925";   	//	实际值减式算值
    public static final String  THIS_PERIOD_PROVISION_BANK_PREPAID_CARD_DEPOSIT_NET_INCREASE                                    = "S5BM926";   	//	在备付金银行存放的预付卡押金本期净增加数
    public static final String  PROVISION_BANK_ACCOUNT_BALANCE                                                                  = "S5BMA01";   	//	备付金银行账户余额
    public static final String  PROVISION_BANK_ACCOUNT_MON_CARRYOVER_INTEREST_DEPOSIT_BALANCE_REDUCE                            = "S5BMA02";   	//	减：备付金银行账户中未结转的备付金银行存款利息余额（累计实现的利息收入总额-累计计提的风险准备金-累计结转的利息收入）
    public static final String  PROVISION_BANK_ACCOUNT_COUNT_DEPOSIT_OWN_FUNDS_BALANCE_REDUCE                                   = "S5BMA03";   	//	减：备付金银行账户中累计申请存放的自有资金余额（累计申请存放的-累计申请提回的）
    public static final String  FINAL_CARRY_FORWARD_PAYMENT_SERVICE_NET_INCOME_BALANCE                                          = "S5BMA04";   	//	减：期未结转的支付业务净收入余额（累计实现的收入-累计扣取的手续费支出-累计结转的手续费收入）
    public static final String  FINAL_CASH_CUSTOMER_DEPOSIT_BALANCE_PLUS                                                        = "S5BMA05";   	//	加：期末以现金形式持有的客户备付金余额（累计接受的现金形式客户备付金-累计缴存备付金银行金额）
    public static final String  THIS_PERIOD_FINAL_CAPITAL_FRIST_PAYMENT_PREPAID_CARD_REDEMPTION_AMOUNT                          = "S5BMA06";   	//	减：本期期末仍存在的以自有资金先行偿付的预付卡赎回金额（累计以自有资金先行偿付金额-累计向存管银行申请结转金额）
    public static final String  PAYMENT_AGENCY_INCREASED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_INCREASE_BALANCE_PLUS                = "S5BMA07";   	//	加：支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额
    public static final String  PAYMENT_AGENCY_REDUCED_CUSTOMER_FUNDS_PROVISION_BANK_NOT_REDUCE_BALANCE_REDUCE                  = "S5BMA08";   	//	减：支付机构业务系统已减少客户资金账户余额，备付金银行未减少备付金银行账户余额
    public static final String  PROVISION_BANK_INCREASED_PAYMENT_AGENCY_NOT_INCREAS_CUSTOMER_FUNDS_BALANCE_REDUCE               = "S5BMA09";   	//	减：备付金银行已增加备付金银行备付金账户余额，支付机构业务系统未增加客户资金账户余额
    public static final String  PROVISION_BANK_REDUCED_PAYMENT_AGENCY_NOT_REDUCE_CUSTOMER_FUNDS_BALANCE_PLUS                    = "S5BMA10";   	//	加：备付金银行已减少备付金银行备付金账户余额，支付机构业务系统未减少客户资金账户余额
    public static final String  PAYMENT_AGENCY_CUSTOMER_ACCOUNT_TRIAL_VALUE                                                     = "S5BMA12";   	//	支付机构客户资金账户余额试算值（备付金账户余额扣减调整项及未达账后得出的数值）
    public static final String  PAYMENT_AGENCY_CUSTOMER_ACCOUNT_BALANCE                                                         = "S5BMA13";   	//	支付机构客户资金账户余额实际值
    public static final String  ACTUAL_BALUE_TRIAL_VALUE                                                                        = "S5BMA14";   	//	实际值-试算值
    public static final String  PROVISION_BANK_DEPOSIT_PREPAID_CARD_BALANCE                                                     = "S5BMA15";   	//	在备付金银行存放的预付卡押金金额
    public static final String  OUTSTANDING_ENTRY_DEPOSIT_RECEIVABLE_MONEY_IN_TOTAL                                             = "S5BMA16";   	//	未达账项中应收押金金额的合计数
    public static final String  THIS_PERIOD_BUSINESS_DEBIT_CUSTOMER_ACCOUNT_MONEY                                               = "S5BMB01";   	//	本期业务系统中借记客户资金账户金额
    public static final String  THIS_PERIOD_BANK_ACTUAL_CASH_VALUE                                                              = "S5BMB02";   	//	本期银行实际出金金额
    public static final String  SPLIT_ACCOUNT_TABLE_RELATION_TO_SUMMARY_TABLE                                                   = "S5BMB03";   	//	分账户表与汇总表关系
    public static final String  PROVISION_BANK_ACCOUNT_OCCURRENCE_L24                                                           = "S5BMC01";   	//	备付金银行账户变动额∑X1-∑X2        =L24
    public static final String  PROVISION_BANK_ACCOUNT_OCCURRENCE_TM1                                                           = "S5BMC02";   	//	备付金银行账户变动额∑X1-∑X2        =t日M1-(t-1)日M1
    public static final String  PROVISION_BANK_ACCOUNT_OCCURRENCE_T05                                                           = "S5BMC03";   	//	备付金银行账户变动额∑X1-∑X2        =t日∑O5-(t-1)日∑O5
    public static final String  PROVISION_BANK_ACCOUNT_ENDING_TOTAL                                                             = "S5BMD01";   	//	备付金银行账户期末余额合计
    public static final String  TOTAL_MANAGEMENT_ACCOUNTS_FINAL_ACCOUNT_NUMBER                                                  = "S5BMD02";   	//	管理账户合计-期末账户数量
    public static final String  TOTAL_MANAGEMENT_ACCOUNTS_FINAL_BALANCE                                                         = "S5BMD03";   	//	管理账户合计-期末余额
    public static final String  TOTAL_MANAGEMENT_ACCOUNTS_BALANCE                                                               = "S5BMD04";   	//	管理账户合计余额
    public static final String  THIS_PERIOD_PAYMENT_AGENCY_REFLECTED_PRACTICE_DEPOSIT_AMOUNT                                    = "S5BME01";   	//	本期支付机构业务系统已反映且本期实际入金金额
    public static final String  THIS_PERIOD_PAYMENT_AGENCY_REFLECTED_PRACTICE_RECEIVE_MONEY                                     = "S5BME02";   	//	本期支付机构业务系统已反映且本期实际收到款项
    public static final String  AMOUNT_MONEY_PAYABLE                                                                            = "S5BME03";   	//	应收入金业务金额
    public static final String  PAYMENT_AGENCY_UNREFLECTED_BANK_DEPOSIT_AMOUNT                                                  = "S5BME04";   	//	支付机构业务系统中未反映但银行已经收到的款项
    public static final String  THIS_PERIOD_PAYMENT_AGENCY_CUSTOMER_ACCOUNT_DEBIT_OCCURRENCE                                    = "S5BME05";   	//	本期支付机构业务系统中客户资金账户借方发生额
    public static final String  THE_FINAL_CASH_CUSTOMER_PROVISION_BALANCE                                                       = "S5BME06";   	//	期末现金形式的客户备付金余额
    public static final String  AT_1_8_FINAL_OWN_FUNDS_PRIOR_REDEMPTION_PREPAID_CARD_BALANCE                                    = "S5BME07";   	//	表1-8期末以自有资金先行赎回预付卡的余额
    public static final String  AT_1_13_FINAL_OWN_FUNDS_PRIOR_REDEMPTION_PREPAID_CARD_BALANCE                                   = "S5BME08";   	//	表1-13期末以自有资金先行赎回预付卡的余额
}