package cn.mxsic.java8;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:02:00
 */
public class DefaulabelImpl implements Defaulable {
    @Override
    public void printName() {
        System.out.println("abel");
    }

    public static void main(String[] args) {
        Defaulable d  = new DefaulabelImpl();
        d.printName();
        d.printAge();
        Defaulable.printSex();
    }
}
