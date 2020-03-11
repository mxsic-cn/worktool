package cn.mxsic.amap.entity.v4;

import cn.mxsic.amap.entity.AMapResponse;
import lombok.Data;

/**
 * Function: BaseResponse <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:44:00
 */
@Data
public class V4Response<T> implements AMapResponse {

    /**
     * 返回数据内容消息体
     */
    private T data;

    /**
     * 错误码
     */
    private int errcode;

    /**
     * 错误描述信息
     */
    private String errmsg;

    private String errdetail;
    private String ext;

    @Override
    public boolean isSucceed() {
        return errcode == 0;
    }

}
