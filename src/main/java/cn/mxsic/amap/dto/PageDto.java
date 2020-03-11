package cn.mxsic.amap.dto;


import java.util.List;

/**
 * Function: PageDto <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-17 17:15:00
 */
public class PageDto<T extends AMapEntity> extends AMapEntity {
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
    private List<T> rs_list;
    /**
     * 总记录数
     */
    private int total_record;

}
