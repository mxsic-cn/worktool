package cn.mxsic.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Function: BigNumberTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-31 11:08:00
 */
public class BigNumberTest {
    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(132);
        System.out.println(b1.add(new BigDecimal(-32.3)).setScale(2, RoundingMode.HALF_UP));
        System.out.println(b1.add(b1.multiply(new BigDecimal(43 / 100.0))).setScale(2, RoundingMode.HALF_UP));
    }
}
