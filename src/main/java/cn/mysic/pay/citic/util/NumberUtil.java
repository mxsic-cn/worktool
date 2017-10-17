package cn.mysic.pay.citic.util;

import java.text.DecimalFormat;

/**
 * Created by liuchuan on 12/21/16.
 */
public class NumberUtil {

    public static String format(double d){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }
}
