package cn.mxsic.amap.entity.v3;

import cn.mxsic.amap.entity.AMapRequest;
import lombok.Data;

/**
 * Function: BaseResponse <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 10:44:00
 */
@Data
public class V3Request implements AMapRequest {

    /**
     * 批量查询控制
     *
     * batch 参数设置为 true 时进行批量查询操作，最多支持 10 个地址进行批量查询。
     *
     * batch 参数设置为 false 时进行单点查询，此时即使传入多个地址也只返回第一个地址的解析查询结果。
     *
     * 可选 false
     */
    private boolean batch = false;


    /**
     * 数字签名
     *
     * 请参考数字签名获取和使用方法
     *
     * 可选
     *
     * 无
     */
    private String sig;


    /**
     * 返回数据格式类型
     *
     * 可选输入内容包括：JSON，XML。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     *
     * 可选
     *
     * JSON
     * never used
     */
    private String output = "JSON";


    /**
     * 回调函数
     *
     * callback 值是用户定义的函数名称，此参数只在 output 参数设置为 JSON 时有效。
     *
     * 可选
     *
     * 无
     */
    private String callback;



}
