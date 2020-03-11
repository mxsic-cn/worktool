package cn.mxsic.amap.entity.v4;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;

/**
 * Function: GeoFence <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 16:10:00
 */
@Data
public class QueryGeoFenceResponse extends V4Response {


    /**
     * 当前页码
     */
    private int page_no;


    /**
     * 每页记录数
     */
    private int page_size;


    /**
     * 围栏列表
     */
    private List<GeoFence> rs_list = Lists.newArrayList();


    /**
     * 总记录数
     */
    private int total_record;


}
