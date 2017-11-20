package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *      证件类型
 *
   证件类型代码	证件类型
        0	    居民身份证
        1	    户口簿
        2	    护照
        3	    军官证
        4	    士兵证
        5	    港澳居民来往内地通行证
        6	    台湾同胞来往内地通行证
        7	    临时身份证
        java8	    外国人居留证
        9	    警官证
        X	    其他证件
 */
public final class CertificateCode {

    public static final char IDENTIFICATION_CARD        = '0';	    //居民身份证
    public static final char RESIDENCE_BOOKLET          = '1';	    //户口簿
    public static final char PASSPORT                   = '2';	    //护照
    public static final char OFFICER_CERTIFICATE        = '3';	    //军官证
    public static final char SOLDIER_CERTIFICATE        = '4';	    //士兵证
    public static final char HONGKONG_MACAU_ID_CARD     = '5';	    //港澳居民来往内地通行证
    public static final char TAIWAN_MAINLAND_PASS       = '6';	    //台湾同胞来往内地通行证
    public static final char TEMPORARY_ID_CARD          = '7';	    //临时身份证
    public static final char ALIENS_RESIDENCE_CARD      = '8';	    //外国人居留证
    public static final char POLICE_OFFICER             = '9';	    //警官证
    public static final char OTHER                      = 'X';	    //其他证件
}
