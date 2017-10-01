package cn.mysic.util;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by liuchuan on 12/21/16.
 */
public class NumberUtil {
    private static Calendar calendar = Calendar.getInstance();
    private static DecimalFormat df = new DecimalFormat("#0.0000");
    public static String format(double d){
        return df.format(d);
    }
    public static String formats(double d){
        return  d+"";
    }
}
