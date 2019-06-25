package cn.siqishangshu.interw.type;

import lombok.Builder;
import lombok.Data;

/**
 * Function: TaxFotGoods <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:48:00
 */
@Data
@Builder
public class TaxForGoods {
    private TaxType taxType;
    private boolean free ;
}
