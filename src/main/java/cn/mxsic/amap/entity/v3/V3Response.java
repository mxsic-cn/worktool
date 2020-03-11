package cn.mxsic.amap.entity.v3;

import cn.mxsic.amap.entity.AMapResponse;
import lombok.Data;

/**
 * Function: BaseResponse <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:44:00
 */
@Data
public class V3Response implements AMapResponse {

    /**
     * 返回结果状态值
     * 返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
     */
    private int status;

    /**
     * 返回结果数目
     * 返回结果的个数。
     */
    private int count;

    /**
     * 返回状态说明
     * 当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参阅info状态表
     */
    private String info;
    /**
     * 返回状态说明
     * 当 status 为 0 时，infocode 会返回具体错误原因，否则返回“0”。详情可以参阅info状态表
     */
    private String infocode;

    @Override
    public boolean isSucceed() {
        return status == 1;
    }
}
