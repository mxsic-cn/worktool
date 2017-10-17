package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/10.
 *
 *      制单状态
 *
 * 制单状态	状态描述
     0	    未审核
     1	    审核拒绝
     2	    审核成功，等待继续审核
     3	    审核完成
     4	    预约成功
     5	    预约被取消
     6	    主机处理中
     7	    交易成功
     8	    交易失败
     9	    撤销
     10	    状态未知
     11	    线程处理中
     12	    撤销状态未知
 *
 */
public final class MakingStatus {
    public static final int NOT_AUDIT                       = 0;        //未审核
    public static final int AUDIT_REFUSED                   = 1;        //审核拒绝
    public static final int AUDIT_SUCCEED_PENDING_REVIEW    = 2;        //审核成功，等待继续审核
    public static final int REVIEW_COMPLETED                = 3;        //审核完成
    public static final int BOOKING_SUCCEED                 = 4;        //预约成功
    public static final int BOOKING_CANCELLED               = 5;        //预约被取消
    public static final int HOST_PROCESSING                 = 6;        //主机处理中
    public static final int DEAL_SUCCEED                    = 7;        //交易成功
    public static final int DEAL_FAILURE                    = 8;        //交易失败
    public static final int REVOCATION                      = 9;        //撤销
    public static final int UNKNOWN                         = 10;       //状态未知
    public static final int THREAD_PROCESSING               = 11;       //线程处理中
    public static final int UNDO_STATE_UNKNOWN              = 12;       //撤销状态未知
}
