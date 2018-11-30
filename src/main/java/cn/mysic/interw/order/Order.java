package cn.mysic.interw.order;

import cn.mysic.interw.goods.Goods;
import lombok.Builder;
import lombok.Data;

/**
 * Function:  <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:19:00
 */
@Data
@Builder
public class Order {
    private Goods goods;
    private int count;
}
