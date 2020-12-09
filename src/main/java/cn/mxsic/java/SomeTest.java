package cn.mxsic.java;

/**
 * Function: Obj <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-24 15:22:00
 */
public class SomeTest {
    public static void main(String[] args) {
//        for (int i = 0; i < 26; i++) {
//            char c = (char) (97 + i);
//            System.out.println(("" + c).hashCode());
//            System.out.println(Objects.hashCode("" + c));
//        }
//        int[] arr = {975,985,959,1050};
//        String s = new String(arr,0,3);
//        System.out.println(s);
//        System.out.println(("中").hashCode());
//        System.out.println(("国").hashCode());
//        System.out.println(("人").hashCode());
//        System.out.println(("民").hashCode());
//        for (int i = 0; i < 27665; i++) {
//            char c = (char) i;
//            System.out.println(("" + c) + "=>" + ("" + c).hashCode());
//        }
//        String k = new String("a");
//        System.out.println(k.hashCode());
//        System.out.println(Integer.MIN_VALUE);
//        byte b = 127;
//        System.out.println((long) b);
       // 二值互换，则取其一为二其计算结果，后再复算出另一值。

        Integer integer = new Integer(null);
        System.out.println(add(123, integer));
    }

    private static Integer add(int i, Integer integer) {
        return i + integer;
    }
}
