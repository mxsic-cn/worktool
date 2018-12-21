package cn.mysic.java8;

import java.util.Arrays;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:35:00
 */
public class Streams {
    public static void main(String[] args) {
        System.out.println("------------");
        Arrays.stream("1231:1231:1231:1231:".split(":")).forEach(s -> {
            System.out.println(s);
        });

//        List<String> list = Arrays.asList("abel", "don", "bruce", "sean");
//        list.stream().filter((String e1) -> (e1.length() > 3)).forEach((e) -> System.out.println(e));
//        list.forEach((e) -> System.out.println(e));
//        System.out.println("--------------------predicate------------------");
//
//        Predicate<String> lengthFilter = (String e1) -> (e1.length() > 3);
//        Predicate<String> conFilter = (String e1) -> (e1.contains("e"));
//        list.stream().filter(lengthFilter).filter(conFilter).forEach((e) -> System.out.println(e));
//        System.out.println("--------------------limit------------------");
//
//        list.stream().sorted((e1, e2) -> e1.compareTo(e2)).limit(3).forEach((e) -> System.out.println(e));
//        System.out.println("--------------------max min------------------");
//        System.out.println("max" + list.stream().max((e1, e2) -> (e1.length() - e2.length())).get());
//        System.out.println("mix" + list.stream().min((e1, e2) -> (e1.length() - e2.length())).get());
//        System.out.println("--------------------summaryStatistics------------------");
//
//        System.out.println(list.stream().parallel().mapToInt(p->p.length()).sum());
//
//        IntSummaryStatistics summaryStatistics = list.stream().mapToInt(p->p.length()).summaryStatistics();
//
//        System.out.println("average"+summaryStatistics.getAverage());
//        System.out.println("min"+summaryStatistics.getMin());
//        System.out.println("max"+summaryStatistics.getMax());
//        System.out.println("sum"+summaryStatistics.getSum());
//        System.out.println("fsdaljjjjjjjjjjjjjjk");
//        final String separato = ",";
//        Arrays.asList("a","b","d").forEach((String e)-> System.out.println(e+separato));

    }
}
