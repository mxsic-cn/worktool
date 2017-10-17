package cn.mysic.domain;

import cn.mysic.log.print.LocalLogUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuchuan on 12/19/16.
 */
public class FrequencyIndex {
    private static double fre = 1.0;
    private static int count = 0;
    private static double timeSpace = 24*60*60;
    private static Calendar calendar = Calendar.getInstance();
    private static DecimalFormat  df = new DecimalFormat("#0.00");
    public static  void  main(String[] args){

        System.out.println(df.format(4561.6545));
        Date t1 = calendar.getTime();

        for (int i = 1 ;i < timeSpace;i++){
            Date t2 = calendar.getTime();
            t2.setTime((long) (t2.getTime()+Math.ceil((Math.random()*1000))*(Math.random()*1000)));
            countFrequency(i,t1,t2,fre);
        }

    }

    public static double countFrequency(int count, Date lastTime, Date now, double fre){
        double time = (now.getTime() - lastTime.getTime())/1000;
        if(time <= 0){
            time = 1;
        }
        fre = Double.parseDouble(df.format(Math.abs((Math.log(count)/fre)*(timeSpace/time)))) ;
        LocalLogUtil.writeSqllog(df.format(time/60)+"min  "+fre+ "");
        return fre;
    }
}
