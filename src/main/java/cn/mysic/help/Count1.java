package cn.mysic.help;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2017-11-20 09:06:00
 */
public class Count1 {
    public static void main(String[] args) {
        System.out.println(count1(111));
        System.out.println(compareString("abcqwefasfd##acdasdfdfe"));
        System.out.println(queue61("1 3 9 20 45 29 18 45 5"));
        System.out.println(sumWait("5 10 8 7"));
    }

    private static int sumWait(String s) {
        String[] array = s.split(" ");
        int sum = 0;
        Set<Integer> set = new HashSet<>();
        for (String s1 : array) {
            set.add(Integer.parseInt(s1));
        }
        int size = set.size();
        Iterator it = set.iterator();
        while (it.hasNext()){
            int tem = (int) it.next();
            sum = sum + tem * size;
            size --;
        }

        return sum;
    }

    private static String queue61(String s) {
        StringBuffer sb = new StringBuffer();
        String[] array = s.split(" ");
        int len =  array.length -1;
        int move = Integer.parseInt(array[len]);
        String[] arr = new String[len];
        for (int i = 0; i < len; i++) {
            arr[i] = array[i];
        }
        for (int i = 0; i < len ; i++) {
            sb.append(arr[(i+(len - move)) % len]+" ");
        }

        return sb.toString();
    }

    private static String compareString(String ss) {
        if( !ss.contains("##")){
            return null;
        }
        String s = ss.split("##")[0];
        String s1 = ss.split("##")[1];
        StringBuffer sb = new StringBuffer();
        if (s.length() >= s1.length()){
            char[] chars = s1.toCharArray();
            for (char c : chars) {
                if(s.contains(c+"")){
                    sb.append(c);
                }
            }

        }else{
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if(s1.contains(c+"")){
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static int count1(int i) {
        String str = Integer.toBinaryString(i);
        int count = 0;
        char[] chars = str.toCharArray();
        for (char c : chars){
            if (c == '1'){
                count ++;
            }
        }
        return count;
    }
}
