package cn.mxsic.interw.type;

import java.math.BigDecimal;

/**
 * Function: TaxPriceUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-30 12:51:00
 */
public class TaxPriceUtil {

    public static BigDecimal convert(BigDecimal tax) {
       return tax.setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
