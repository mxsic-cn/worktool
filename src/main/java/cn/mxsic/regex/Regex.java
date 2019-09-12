package cn.mxsic.regex;

import java.util.regex.Pattern;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-15 18:12:00
 */
public class Regex {
    public static void main(String[] args) {
//        String content = "pish pish Iron is 3910 Credits".trim();

//        String pattern = "^((?:([a-zA-Z0-9_]+)+ )+)(([a-zA-Z0-9_]+)\\w+) is (\\d+) ([credits|Credits])$";
//        String pattern = " is (\\d+) (Credits|credits)$";
//        String pattern = "^([a-zA-Z0-9_]+){2,} is (\\d+) Credits$";
//        System.out.println(Pattern.matches(pattern,content));
//        System.out.println(intToRoman(99));
//        System.out.println(romanToInt("XCIX"));

        String tmp = "PUT/api/v1/m/orders/TR6970565161130/status";

        String description = Pattern.compile("\\d*[0-9]").matcher(tmp).replaceAll("{arg}");
        System.out.println(description);
    }

    public static String intToRoman(int number) {
        int[] base = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        String[] str = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX",
                "V", "IV", "I" };
        String roman = "";
        int i = 0;
        while (number != 0) {
            if (number >= base[i]) {
                number -= base[i];
                roman += str[i];
            } else
                i++;
        }
        return roman;
    }

    public static int romanToInt(String s) {
        if (s.length() < 1)
            return 0;
        int result = 0;
        int sub = getRomanValue(s.charAt(0));
        int lastv = sub;
        for (int i = 1; i < s.length(); ++i) {
            char curc = s.charAt(i);
            int curv = getRomanValue(curc);
            if (curv == lastv)
                sub += curv;
                // 当前一个字符大于当前字符时，情况比较特殊，这个时候，只能讲last包括last的字符算在sub内，相加得到result，
                // 至于curr还需要根据curr后面的字符判断
            else if (curv < lastv) {
                result += sub;
                sub = curv;
            } else {
                sub = curv - sub;
            }
            lastv = curv;
        }
        result += sub;
        return result;
    }


    public static int getRomanValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

}
