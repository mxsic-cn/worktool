package cn.mxsic.print;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by liuchuan on 3/14/17.
 */
public class Formart {


    public static void main(String[] args){
        Formatter fmt = new Formatter();
        String[] sn = new String[]{"HUGE", "Huge Fruit, Inc.", "Fruit Titanesque, Inc."};
        fmt.format("%s", sn);                   //   -> "Huge Fruit, Inc."
        fmt.format("%s", sn.toString());        //   -> "HUGE - Huge Fruit, Inc."
        fmt.format("%#s", sn);                  //   -> "HUGE"
        fmt.format("%-10.8s", sn);              //   -> "HUGE      "
        fmt.format("%.12s", sn);                //   -> "Huge Fruit,*"
        fmt.format(Locale.FRANCE, "%25s", sn);  //   -> "   Fruit Titanesque, Inc."
    }
}
