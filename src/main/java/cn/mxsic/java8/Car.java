package cn.mxsic.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:04:00
 */
public class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void prepair() {
        System.out.println("Repaired " + this.toString());
    }


    public static void main(String[] args) {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        cars.forEach(Car::collide);

        cars.forEach(Car::prepair);

        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }
}
