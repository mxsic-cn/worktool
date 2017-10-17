package cn.mysic.pay.citic.constant;

/**
 * Created by siqishangshu on 17/10/11.
 *
 *      批量支付交易状态
 *    编码	        名称
    CP07815	    查不出此批的客户流水号
    CP07816	    根据客户流水号查询的制单状态为空
    CP07817	    该笔单子现处于审核拒绝状态
    CP07818	    该笔单子现处于审核中状态
    CP07819	    该笔单子现处审核完成,等待发送主机状态
    CP07820	    该笔单子现处于预约被取消状态
    CP07821	    该笔单子交易处理失败
    CP07822	    该笔单子现处于撤销状态
    CP07823	    该笔单子现处于线程处理中状态
    CP07824	    该笔单子现处于撤销状态未知
    CP07825	    该笔单子全部处理失败
    AAAAAAA	    交易成功
    BBBBBBB	    交易部分成功
    AAAAAAB	    待审核
    AAAAAAC	    预约成功
    CCCCCCC	    交易处理中
    AAAAAAF	    该笔单子提交成功
    UNKNOWN	    交易状态未知
 */
public final class BulkPaymentStatus {
    public static final String CAN_FIND_CUSTOMER_SERIAL_NUMBER  = "CP07815";	    //查不出此批的客户流水号
    public static final String INQUIRY_SYSTEM_STATUS_EMPTY      = "CP07816";	    //根据客户流水号查询的制单状态为空
    public static final String AUDIT_REFUSED                    = "CP07817";	    //该笔单子现处于审核拒绝状态
    public static final String UNDER_REVIEW                     = "CP07818";	    //该笔单子现处于审核中状态
    public static final String AUDIT_DONE_WAITTING_SEND_TO_HOST = "CP07819";	    //该笔单子现处审核完成,等待发送主机状态
    public static final String BOOKING_CANCELED                 = "CP07820";	    //该笔单子现处于预约被取消状态
    public static final String TRANSACTION_FAILED               = "CP07821";	    //该笔单子交易处理失败
    public static final String WITHDRAWN                        = "CP07822";	    //该笔单子现处于撤销状态
    public static final String THREAD_PROCESSING                = "CP07823";	    //该笔单子现处于线程处理中状态
    public static final String REVOCATION_UNKNOWN               = "CP07824";	    //该笔单子现处于撤销状态未知
    public static final String ALL_PROCESSING_FAILED            = "CP07825";	    //该笔单子全部处理失败
    public static final String TRANSACTION_SUCCESSFUL           = "AAAAAAA";	    //交易成功
    public static final String TRANSACTION_PART_SUCCESSFUL      = "BBBBBBB";	    //交易部分成功
    public static final String PENDING_REVIEW                   = "AAAAAAB";	    //待审核
    public static final String RESERVATION_SUCCESSFUL           = "AAAAAAC";	    //预约成功
    public static final String TRANSACTION_PROCESSING           = "CCCCCCC";	    //交易处理中
    public static final String SUBMITTED_SUCCESSFULLY           = "AAAAAAF";	    //该笔单子提交成功
    public static final String TRANSCATION_UNKNOWN              = "UNKNOWN";	    //交易状态未知

}
