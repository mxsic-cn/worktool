package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *   一级核验规则对照表(BFJHDNR)
 *
 S5BM100	归属我行的未达账项余额核对校验一
 S5BM200	归属我行的未达账项余额核对校验二
 S5BM300	归属我行的特殊业务项目间勾稽关系核验
 S5BM400	归属我行的备付金银行账户余额变动核验
 S5BM500	归属备付金银行的未达账项余额核对校验一
 S5BM600	归属备付金银行的未达账项余额核对校验二
 S5BM700	t日日终客户账户余额核验
 S5BM800	各银行特殊业务项目间勾稽关系核验
 S5BM900	客户资金账户金额变动勾稽关系核验
 S5BMA00	客户资金账户余额勾稽关系核验
 S5BMB00	出金业务项目间勾稽关系核验
 S5BMC00	各银行账户余额发生额核对
 S5BMD00	客户资金管理账户核对
 S5BME00	其他勾稽关系
 */
public final class FirstVerifictionRules {
    public static final String  OUR_BANK_UNSETTLEMENT_ACCOUNT_BALANCE_VERIFICATION_ONE                  = "S5BM100";   	//  归属我行的未达账项余额核对校验一
    public static final String  OUR_BANK_UNSETTLEMENT_ACCOUNT_BALANCE_VERIFICATION_TWO                  = "S5BM200";   	//  归属我行的未达账项余额核对校验二
    public static final String  OUR_BANK_SPECIAL_BUSINESS_RELATIONSHIP_VERIFICATION                     = "S5BM300";   	//	归属我行的特殊业务项目间勾稽关系核验
    public static final String  OUR_BANK_PROVISIONS_ACCOUNT_BALANCE_CHANGES_VERIFICATION_               = "S5BM400";   	//	归属我行的备付金银行账户余额变动核验
    public static final String  HOME_PROVISIONS_BANK_UNSETTLEMENT_ACCOUNT_BALANCE_VERIFICATION_ONE      = "S5BM500";   	//	归属备付金银行的未达账项余额核对校验一
    public static final String  HOME_PROVISIONS_BANK_UNSETTLEMENT_ACCOUNT_BALANCE_VERIFICATION_TWO      = "S5BM600";   	//	归属备付金银行的未达账项余额核对校验二
    public static final String  T_DAY_DAY_END_CUSTONMERS_ACCOUNT_BALANCE_VERIFICATION                   = "S5BM700";   	//	t日日终客户账户余额核验
    public static final String  BANKS_SPECIAL_BUSINESS_RELAATIONSHIP_VERIFICATION                       = "S5BM800";   	//	各银行特殊业务项目间勾稽关系核验
    public static final String  CUSTOMER_ACCOUNT_CHANGES_RELATIONSHIP_VERIFICATION                      = "S5BM900";   	//	客户资金账户金额变动勾稽关系核验
    public static final String  CUSTOMER_ACCOUNT_BANLANCE_RELATIONSHIP_VERIFICATION                     = "S5BMA00";   	//	客户资金账户余额勾稽关系核验
    public static final String  CASH_OUT_BUSINESS_RELATIONSHIP_VERIFICATION                             = "S5BMB00";   	//	出金业务项目间勾稽关系核验
    public static final String  BANKS_ACCOUNT_BALANCE_VERFICATION                                       = "S5BMC00";   	//	各银行账户余额发生额核对
    public static final String  CUSTOMER_FUNDS_MANAGEMENT_ACCOUNT_VERIFICATION                          = "S5BMD00";   	//	客户资金管理账户核对
    public static final String  OTHER_HOOK_RELATIONSHIP                                                 = "S5BME00";   	//	其他勾稽关系
}
