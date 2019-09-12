package cn.mxsic.interw.goods;

import java.math.BigDecimal;

import cn.mxsic.interw.type.GoodsType;
import lombok.Builder;
import lombok.Data;

/**
 * Function: Goods <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:19:00
 */
@Data
@Builder
public class Goods {
    private String name;
    private String code;
    private BigDecimal price;
    private GoodsType type;
}
