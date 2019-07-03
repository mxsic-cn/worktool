package cn.siqishangshu.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        Function<Pojo,Pojo> f = x ->{
          Pojo pojo2 = new Pojo();
          pojo2.setGood(x.getGood());
          return pojo2;
        };
        System.out.println(f.apply(Pojo.builder().good("fsjlief").build()));

    }
}
