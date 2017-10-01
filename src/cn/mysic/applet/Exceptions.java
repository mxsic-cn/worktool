package cn.mysic.applet;

import java.io.FileInputStream;
import java.util.*;

/**
 * Created by liuchuan on 2/17/17.
 */
public class Exceptions {
    public static void main(String[] args)
    {
        try{
            int a = 9;
            assert true  : a ++;
             System.out.println(a);
            Scanner in = new Scanner(new FileInputStream("/hose/liuchuan/tmp/te.txt"));
            while (in.hasNext()) {
                System.out.println(in.next());
            }
            Throwable throwable = new Throwable("is a test",new InputMismatchException());
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
//            Map<Thread, StackTraceElement[]> threadMap =Thread.getAllStackTraces();
//            Set<Thread> set = threadMap.keySet();
//            for (Thread thread :set) {
//                StackTraceElement[] stackTraceElements = threadMap.get(thread);
//                System.out.println(thread.getName());
//
//                System.out.println(thread.toString());
//                for (StackTraceElement stackTraceElement :  stackTraceElements) {
//                    System.out.println(stackTraceElement.getClassName());
//                    System.out.println(stackTraceElement.getLineNumber());
//                }
//            }
        }
    }
}
