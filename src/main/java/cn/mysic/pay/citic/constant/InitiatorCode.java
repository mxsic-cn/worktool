package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *
 *    发起方标志
 *
 * 代码	发起方标志含义
    B	银行网点
    M	第三方
    G	银联
    S	自助网点
    N	网上银行
    W	手机银行
    L	国际业务系统
    R	客服中心
    D	业务管理渠道
 */
public final class InitiatorCode {
    public static final char BANK_OUTLETS                   = 'B';      //银行网点
    public static final char THIRD_PARTY                    = 'M';      //第三方
    public static final char UNIONPAY                       = 'G';      //银联
    public static final char SELF_SERVICE_OUTLETS           = 'S';      //自助网点
    public static final char ONLINE_BANKING                 = 'N';      //网上银行
    public static final char MOBILE_BANKING                 = 'W';      //手机银行
    public static final char INTERNATIONAL_BUSINESS_SYSTEM  = 'L';      //国际业务系统
    public static final char CUSTOMER_SERVICE_CENTER        = 'R';      //客服中心
    public static final char BUSINESS_MANAGEMENT_CHANNEL    = 'D';      //业务管理渠道
}
