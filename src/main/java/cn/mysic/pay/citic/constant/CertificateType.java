package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/10.
 *
 *       证件类型
 *
 *  证件类型代码	证件类型
 *      0	    居民身份证
 *      1	    武装警察身份证
 *      2	    护照
 *      3	    军人身份证
 *      4	    港澳居民往来身份证
 *      7	    台湾居民大陆往来通行证
 *      java8	    其他
 */
public final class  CertificateType {
    public static final int IDENTIFICATION_CARD     = 0;        //居民身份证
    public static final int ARMED_POLICE_ID_CARD    = 1;        //武装警察身份证
    public static final int PASSPORT                = 2;        //护照
    public static final int MILITARY_ID_CARD        = 3;        //军人身份证
    public static final int HONGKONG_MACAU_ID_CARD  = 4;        //港澳居民往来身份证
    public static final int TAIWAN_MAINLAND_PASS    = 7;        //台湾居民大陆往来通行证
    public static final int OTHER                   = 8;        //其他
}
