package cn.mxsic.string;

import java.util.regex.Pattern;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-11 15:01:00
 */
public class PassValue {

    public static final String URL_SPILT = "/";

    public static final int URL_WORD_MAX_LEN = 26;
    private static final String ARG = "{arg}";
    private static final String ARG_WITH = "/" + ARG;
    private static Pattern pattern = Pattern.compile("/\\d*[0-9]");

    public PassValue() {
    }


    public static void main(String[] args) {
        String newCode = "xian_shanghai_p_21_02_23";
        if (newCode.length()>20) {
            newCode = newCode.substring(newCode.length()-20,newCode.length());
        }
        System.out.println(newCode);

//        System.out.print("minibus_small:");
//        System.out.println("minibus_small".hashCode());
//        System.out.print("minibus_big:");
//        System.out.println("minibus_big".hashCode());
//        System.out.print("flat_small:");
//        System.out.println("flat_small".hashCode());
//        System.out.print("van_big:");
//        System.out.println("van_big".hashCode());
//        System.out.print("flag_large:");
//        System.out.println("flag_large".hashCode());
//        System.out.print("flag_big_5:");
//        System.out.println("flag_big_5".hashCode());
//        System.out.print("flag_big_6:");
//        System.out.println("flag_big_6".hashCode());
//        System.out.print("flag_big_7:");
//        System.out.println("flag_big_7".hashCode());
//        System.out.print("flag_big_9:");
//        System.out.println("flag_big_9".hashCode());
//        String url = "/api/v1/wechat/16546465434/be5cfd4bc4cc38fc4a1d221957730";
//        String[] arr = url.split(URL_SPILT);
//        for (String s : arr) {
//            if (s.length() > URL_WORD_MAX_LEN) {
//                url = url.replace(s, ARG);
//            }
//        }
//        String urls = pattern.matcher(url).replaceAll(ARG_WITH);
//        System.out.println(urls);
//        PassValue passValue = new PassValue();
//        User user = passValue.new User();
//        user.setName("main");
//        user.setAge(20);
//        System.out.println(user.toString());
//        passValue.pass(user);
//        System.out.println(user.toString());
//
//
//        String name = "hooooo";
//        passValue.pass(name);
//        System.out.println(name);
    }

    private void pass(String name) {
        name = "hkkkkkkk";
        System.out.println("pass " + name);
    }

    private void pass(User user) {
        user.setAge(21);
        user.setName("pass");
        System.out.println("pass " + user);
    }


    class User {

        private String name;
        private int age;

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User :name " + name + " age " + age;
        }
    }
}
