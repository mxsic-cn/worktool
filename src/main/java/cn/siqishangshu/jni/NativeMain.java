package cn.siqishangshu.jni;

/**
 * Function: NativeMain <br>
 * @author: siqishangshu <br>
 * @date: 2019-07-16 10:54:00
 *
 */

public class NativeMain {
    public static native void getNativeString();
    static {
        System.loadLibrary("NativeLib");
    }
    public static void main(String[] args) {
        System.out.println("native");

    }


}