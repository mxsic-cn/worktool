package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/10.
 *
 *      交易类型
 *
 * 制单状态	状态描述
     00	    汇票
     01	    中信内转账
     02	    大额支付
     03	    小额支付
     04	    同城票交
     05	    账户信息查询
     06	    账户交易明细查询
     07	    协议代理收款
     08	    网银跨行支付
     09	    授权查询协议签约
     10	    授权查询协议解除
     11	    授权支付协议签约
     12	    授权支付协议解除
     13	    网银贷计
     14	    网银借记
     15	    第三方贷记申请
     16	    他行帐号授权查询协议签约申请
     17	    他行帐号授权查询协议解约申请
     18	    他行帐号授权支付协议签约申请
     19	    他行帐号授权支付协议解约申请
     20	    我行帐号授权查询协议签约
     21	    我行帐号授权查询协议解约
     22	    我行帐号授权支付协议签约
     23	    我行帐号授权支付协议解约
     24	    交易业务状态查询
 */
public final class TransactionType {
    public static final int MONEY_ORDER                                                         = 0;
    public static final int INTRA_BANK_TRANSFER                                                 = 1;
    public static final int LARGE_PAYMENT                                                       = 2;
    public static final int SMALL_PAYMENT                                                       = 3;
    public static final int CITY_TICKET_TRANSACTION                                             = 4;
    public static final int QUERY_ACCOUNT_INFO                                                  = 5;
    public static final int QUERY_ACCOUNT_TRANSACTION_DETAIL                                    = 6;
    public static final int AGENCY_COLLECTION                                                   = 7;
    public static final int ONLINE_BANKING_CROSS_PAYMENT                                        = 8;
    public static final int AUTHORIZED_INQUIRY_AGREEMENT_SIGN                                   = 9;
    public static final int AUTHORIZED_INQUIRY_AGREEMENT_TERMINATED                             = 10;
    public static final int AUTHORIZED_PAYMENT_AGREEMENT_SIGN                                   = 11;
    public static final int AUTHORIZED_PAYMENT_AGREEMENT_TERMINATED                             = 12;
    public static final int ONLINE_BANKING_CREDIT                                               = 13;
    public static final int ONLINE_BANKING_DEBIT                                                = 14;
    public static final int THIRD_PARTY_CREDIT_APPLY                                            = 15;
    public static final int OTHER_BANK_ACCOUNT_AUTHORIZED_INQUIRY_AGREEMENT_SIGN_APPLY          = 16;
    public static final int OTHER_BANK_ACCOUNT_AUTHORIZED_INQUIRY_AGREEMENT_TERMINATED_APPLY    = 17;
    public static final int OTHER_BANK_ACCOUNT_AUTHORIZED_PAYMENT_AGREEMENT_SIGN_APPLY          = 18;
    public static final int OTHER_BANK_ACCOUNT_AUTHORIZED_PAYMENT_AGREEMENT_TERMINATED_APPLY    = 19;
    public static final int OUR_BANK_ACCOUNT_AUTHORIZED_INQUIRY_AGREEMENT_SIGN                  = 20;
    public static final int OUR_BANK_ACCOUNT_AUTHORIZED_INQUIRY_AGREEMENT_TERMINATED            = 21;
    public static final int OUR_BANK_ACCOUNT_AUTHORIZED_PAYMENT_AGREEMENT_SIGN                  = 22;
    public static final int OUR_BANK_ACCOUNT_AUTHORIZED_PAYMENT_AGREEMENT_TERMINATED            = 23;
    public static final int TRANSACTOIN_BUSINESS_STATUS_QUERY                                   = 24;
}
