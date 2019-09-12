package cn.mxsic.html;

import lombok.Data;

/**
 * Function: Area <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-25 17:50:00
 */
@Data
public class Area {
    private String districtCode;  // '行政区域编码',
    private String name;  //'地区名称',
    private String parentId; // '上级地区',
    private int level;//'级别',
    private int ordinal; // '排序',

    @Override
    public String toString() {
        return "INSERT INTO `dict_area_cn` (`id`,`districtCode` ,`name`,`parentId`,`level`,`ordinal`) VALUE('" +
                districtCode + "','" +
                districtCode + "','" +
                name + "','" +
                parentId + "'," +
                level + "," +
                ordinal + ");";
    }
}
