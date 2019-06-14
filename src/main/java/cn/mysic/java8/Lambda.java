package cn.mysic.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:23:00
 */
public class Lambda {

    public static void main(String[] args) {
        System.out.println("lambda");
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strs.add(i + "");
        }

        strs.forEach((s) -> System.out.println(s + ""));

        strs.forEach((s -> {
            System.out.println(s);
            System.out.println(s + "else");
        }));


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runing");
            }
        }).start();

        new Thread(() -> System.out.println("running")).start();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        };
        Runnable runnable1 = () -> System.out.println("Hello world");
        Runnable runnable2 = (() -> {
            strs.forEach(s -> System.out.println(s));
        });

        runnable.run();
        runnable1.run();
        runnable2.run();


        List<String> strings = Arrays.asList("abel", "don", "bruce", "sean");
        strings.sort((e1, e2) -> e1.compareTo(e2));
        strings.forEach(s -> System.out.println(s));
        strings.sort((e1, e2) -> e2.length() - e1.length());
        strings.forEach(s -> System.out.println(s));
        strings.forEach(System.out::println);
        System.out.println("-----------------------");
        Comparator<String> sortByName = (String e1,String e2)->e1.length()-e2.length();

        strings.sort(sortByName);
        strings.forEach(s -> System.out.println(s));
    }
}
