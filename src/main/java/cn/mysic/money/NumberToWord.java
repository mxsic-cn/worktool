package cn.mysic.money;

import java.util.Scanner;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-09 17:27:00
 */
public class NumberToWord {

    private static String input;

    private static Scanner sc;

    private static String[] num = {"零","一","二","三","四","五","六","七","八","九","十"};

    private static String[] unit = {"","十","百","千","万","十","百","千","亿","十","百","千","兆"};

    private static String[] result;

    private static void input() {
        System.out.println("请输入一串数字最多9位");
        sc = new Scanner(System.in);
        input = sc.nextLine();
    }

    public static String transfer(String input) {
        String out = "";
        result = new String[input.length()];
        for(int i=0; i<result.length; i++) {
            result[i] = String.valueOf(input.charAt(i));
        }
        for(int i=0; i<result.length; i++) {
            int back;
            if(!result[i].equals("0")) {
                back = result.length - i - 1;
                out += num[Integer.parseInt(result[i])];
                out += unit[back];
            } else {
                //最后一位不考虑
                if(i == (result.length - 1)) {

                } else {
                    //九位数，千万，百万，十万，万位都为0，则不加“万”
                    if(result.length == 9 && result[1].equals("0") && result[2].equals("0") && result[3].equals("0") && result[4].equals("0")) {

                    } else {
                        //大于万位，连着的两个数不为0，万位等于0则加上“万”
                        if(result.length > 4 && !result[i+1].equals("0") && result[result.length-5].equals("0")){
                            out += unit[4];
                        }
                    }
                    //万位之后的零显示
                    if(i == result.length-4 && !result[i+1].equals("0")) {
                        out += num[0];
                    }
                }
            }
        }
        return out;
    }
    public static void main(String[] args) {
        input();
        System.out.println(transfer(input));
    }
}
