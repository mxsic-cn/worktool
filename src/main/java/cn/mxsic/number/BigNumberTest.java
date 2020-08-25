package cn.mxsic.number;

import java.util.regex.Pattern;

/**
 * Function: BigNumberTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-31 11:08:00
 */
public class BigNumberTest {
    private static Pattern NUMBER_PATTERN = Pattern.compile("^([0-9]*[.])?[0-9]+$");

    public static void main(String[] args) {
//        BigDecimal b1 = new BigDecimal(111.05);
//        BigDecimal b2 = new BigDecimal(222.93);
//         System.out.println("UP:"+  b1.setScale(0,BigDecimal.ROUND_UP));
//         System.out.println("UP:"+  b2.setScale(0,BigDecimal.ROUND_UP));
//         System.out.println("DOWN:"+b1.setScale(0,BigDecimal.ROUND_DOWN));
//         System.out.println("DOWN:"+b2.setScale(0,BigDecimal.ROUND_DOWN));
//         System.out.println("CEILING:"+b1.setScale(0,BigDecimal.ROUND_CEILING));
//         System.out.println("CEILING:"+b2.setScale(0,BigDecimal.ROUND_CEILING));
//         System.out.println("FLOOR:"+b1.setScale(0,BigDecimal.ROUND_FLOOR));
//         System.out.println("FLOOR:"+b2.setScale(0,BigDecimal.ROUND_FLOOR));
//         System.out.println("HALF_UP:"+b1.setScale(0,BigDecimal.ROUND_HALF_UP));
//         System.out.println("HALF_UP:"+b2.setScale(0,BigDecimal.ROUND_HALF_UP));
//         System.out.println("HALF_DOWN:"+b1.setScale(0,BigDecimal.ROUND_HALF_DOWN));
//         System.out.println("HALF_DOWN:"+b2.setScale(0,BigDecimal.ROUND_HALF_DOWN));
//         System.out.println("HALF_EVEN:"+b1.setScale(0,BigDecimal.ROUND_HALF_EVEN));
//         System.out.println("HALF_EVEN:"+b2.setScale(0,BigDecimal.ROUND_HALF_EVEN));
//        for (int i = 0; i < 10000; i++) {
//            BigDecimal b1 = new BigDecimal(Math.random() * 1000);
//            if (!b1.setScale(1, BigDecimal.ROUND_UP).equals(b1.setScale(1, BigDecimal.ROUND_CEILING))) {
//                System.out.println("UP:"+b1.setScale(0, BigDecimal.ROUND_UP)+" CEILING:"+b1.setScale(0, BigDecimal.ROUND_CEILING));
//            }
//            if (!b1.setScale(1, BigDecimal.ROUND_DOWN).equals(b1.setScale(1, BigDecimal.ROUND_FLOOR))) {
//                System.out.println("DOWN:"+b1.setScale(0, BigDecimal.ROUND_DOWN) +" FLOOR:"+ b1.setScale(0, BigDecimal.ROUND_FLOOR));
//            }
//
//        }

        String value = "0.343";
         if (!NUMBER_PATTERN.matcher(value).matches()) {

             System.out.println(("金额格式应该为数字类型，肯大于0！"));

         }

    }
}
