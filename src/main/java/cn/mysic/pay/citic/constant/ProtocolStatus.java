package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *       协议状态
 *
 *状态代码	状态含义
    01	    有效
    02	    无效
    03	    处理中
    04	    未生效
 */
public final class ProtocolStatus {
    public static final String VALID        = "01";     //有效
    public static final String INVALID      = "02";     //无效
    public static final String PROCESSING   = "03";     //处理中
    public static final String NOT_EFFECT   = "04";     //未生效
}
