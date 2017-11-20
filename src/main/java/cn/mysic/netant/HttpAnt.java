package cn.mysic.netant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuchuan on 6/12/17.
 */
public class HttpAnt {

    private static int MAXPAGE = 7823;
    private static int threadNum = 200;

<<<<<<< HEAD
    public static void main(String[] agrs) {
        Map<String, String[]> filsMap = new HashMap<>();
        startGetTheData();
    }

    public static void startGetTheData() {
        ResultInfo resultInfo = new ResultInfo();
        List<Integer> list = new ArrayList<>();
        list.add(6638);
        list.add(6645);
        list.add(6670);
        list.add(6671);
        list.add(6697);
        list.add(6702);
        list.add(6715);
        list.add(6729);
        list.add(6730);
        list.add(6735);
        list.add(6738);
        list.add(6739);
        list.add(6741);
        list.add(6742);
        list.add(6743);
        list.add(6753);
        list.add(6785);
        list.add(6798);
        list.add(6837);
        list.add(6844);
        list.add(6857);
        for (int i : list) {
=======
    public static void main(String[] agrs) {  Map<String,String[]> filsMap = new HashMap<String,String[]>();
        startGetTheData();
    }
   public static void startGetTheData(){
       ResultInfo resultInfo = new ResultInfo();
       List<Integer> list = new ArrayList<Integer>();
       list.add(6638);
       list.add(6645);
       list.add(6670);
       list.add(6671);
       list.add(6697);
       list.add(6702);
       list.add(6715);
       list.add(6729);
       list.add(6730);
       list.add(6735);
       list.add(6738);
       list.add(6739);
       list.add(6741);
       list.add(6742);
       list.add(6743);
       list.add(6753);
       list.add(6785);
       list.add(6798);
       list.add(6837);
       list.add(6844);
       list.add(6857);
       for (int i:list) {
>>>>>>> dcf34e6e4673af23187ed47095fc456fbba96e1e
//       for (int i = 6634 ; i <= MAXPAGE; i++) {
            new HttpTask(i, resultInfo).run();
        }
//       threadPoolExe(resultInfo);
    }

    private static void threadPoolExe(ResultInfo resultInfo) {
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        for (int i = 1; i <= MAXPAGE; i++) {

            //       List<Integer> list = new ArrayList<>();

            //       for (int i:list) {
            service.execute(new HttpTask(i, resultInfo));
            i++;
            if (i % threadNum == 0) {
                if (!service.isShutdown()) {
                    service.shutdown();
                }
                try {
                    for (boolean bool = false; !bool; bool = service.awaitTermination(15, TimeUnit.SECONDS)) ;
                    service = Executors.newFixedThreadPool(threadNum);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (!service.isShutdown()) {
            service.shutdown();
        }
        try {
            for (boolean bool = false; !bool; bool = service.awaitTermination(15, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e.getMessage());
        }
    }

}
