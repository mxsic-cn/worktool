package cn.siqishangshu.java8;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:00:00
 */
public interface Defaulable {
    void printName();
    default void printAge(){
        System.out.println(19);
    }

    static void printSex(){
        System.out.println("女");
    }
}
