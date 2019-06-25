package cn.siqishangshu.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-06-12 16:42:00
 */
public class PrintException {
    public static void main(String[] args) {
        double d = printException();
        System.out.println(d);
        printCallStatck();
    }

    private static double printException() {
        Exception e = new Exception("this is a log");
//        e.printStackTrace();
        System.out.println(ExceptionUtils.getStackTrace(e));
        System.out.println("exception have been print");
        return Math.random();
    }


    public static void printCallStatck() {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                System.out.print(stackElements[i].getClassName()+":");
                System.out.print(stackElements[i].getFileName()+":");
                System.out.print(stackElements[i].getLineNumber()+":");
                System.out.println(stackElements[i].getMethodName());
                System.out.println("-----------------------------------");
            }
        }
    }

}
