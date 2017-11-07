package cn.mysic.java8;

import java.util.Optional;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:18:00
 */
public class OptionalS {

    public static void main(String[] args) {
        Car car = Car.create(Car::new);
        car = null;
        boolean b = Optional.ofNullable(car).isPresent();
        System.out.println(b);
    }
}
