package cn.mxsic.interw.goods;

import java.util.List;

import cn.mxsic.interw.type.GoodsType;
import cn.mxsic.interw.type.TaxForGoods;
import lombok.Builder;
import lombok.Data;

/**
 * Function: GoodsTax <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 13:56:00
 */

@Data
@Builder
public class GoodsTax{
    private GoodsType goodsType;
    private List<TaxForGoods> taxForGoods;
}