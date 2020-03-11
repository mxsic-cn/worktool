package cn.mxsic.amap.dto;

import lombok.Data;

/**
 * Function: ResponseDto <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-17 17:02:00
 */
@Data
public class ResponseDto<T extends AMapEntity> {
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

}
