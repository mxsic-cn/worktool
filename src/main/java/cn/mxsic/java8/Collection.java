package cn.mxsic.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Function: Conllectiono <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-26 16:00:00
 */
public class Collection {

    public static void main(String[] args) {
        List<Pojo> list = new ArrayList<>();
//        list.add(Pojo.builder().right(false).build());
//        list.add(Pojo.builder().right(false).build());
//        list.add(Pojo.builder().right(false).build());
//        System.out.println(list.stream().noneMatch(Pojo::isRight));
        Predicate<Pojo> predicate = Pojo::isRight;
        System.out.println(predicate.test(Pojo.builder().right(false).build()));
        Consumer<Pojo> consumer = x ->{
             x.setGood("2");
            System.out.println(x);
         };
        consumer.andThen(x->{
            System.out.println(x);
        });
        Pojo pojo= Pojo.builder().good("43").build();
        consumer.accept(pojo);
//        System.out.println(pojo);

        Supplier<String> supplier = String::new;
        System.out.println(supplier.get());
        Supplier<Pojo> supplier1 = Pojo::new;
        Pojo pojo1 =supplier1.get();
        pojo1.setGood("good");
        System.out.println(pojo1);

        /**
         * if obj,need to convert
         */
        Function<String,Pojo> f = x ->{
          Pojo pojo2 = new Pojo();
          pojo2.setGood(x);
          return pojo2;
        };
        System.out.println(f.apply("fsjlief"));
        if ("dsfe".equalsIgnoreCase(null)) {
            System.out.println("null");
        }
    }

    /**
     * toList
     */
    public static void toListTest(){
        List<PersonModel> data = getData();
        List<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toList());
    }

    /**
     * toSet
     */
    public static void toSetTest(){
        List<PersonModel> data = getData();
        Set<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toSet());
    }

    private static List<PersonModel> getData() {
        return new ArrayList<>();
    }

    /**
     * toMap
     */
    public static void toMapTest(){
        List<PersonModel> data = getData();
        Map<String, Integer> collect = data.stream()
                .collect(
                        Collectors.toMap(PersonModel::getName, PersonModel::getAge)
                );

        data.stream()
                .collect(Collectors.toMap(per->per.getName(), value->{
                    return value+"1";
                }));
    }

    /**
     * 指定类型
     */
    public static void toTreeSetTest(){
        List<PersonModel> data = getData();
        TreeSet<PersonModel> collect = data.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
    }

    /**
     * 分组
     */
    public static void toGroupTest(){
        List<PersonModel> data = getData();
        Map<Boolean, List<PersonModel>> collect = data.stream()
                .collect(Collectors.groupingBy(per -> "男".equals(per.getSex())));
        System.out.println(collect);
    }

    /**
     * 分隔
     */
    public static void toJoiningTest(){
        List<PersonModel> data = getData();
        String collect = data.stream()
                .map(personModel -> personModel.getName())
                .collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect);
    }

    /**
     * 自定义
     */
    public static void reduce(){
        List<String> collect = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect);
    }
}
