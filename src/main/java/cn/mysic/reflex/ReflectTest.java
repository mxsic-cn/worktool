package cn.mysic.reflex;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-27 20:22:00
 * Java通过实例得到实体类中的属性和方法
 *
 */
public class ReflectTest {

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        Student student = new Student();

        student.setBirthday(new Date());
        student.setGender(false);
        student.setId(3);
        student.setName("王五");
        student.setScore(99);

        // 通过实例得到类
        @SuppressWarnings("rawtypes")
        Class studentClass = (Class) student.getClass(); // studentClass

        /*
         * 得到类中的所有属性集合
         */
        Field[] field = studentClass.getDeclaredFields();

        for (int i = 0; i < field.length; i++) {
            Field f = field[i];
            int size = field.length;// 属性个数
            f.setAccessible(true); // 设置些属性是可以访问的

            String type = f.getType().toString();// 得到此属性的类型
            String key = f.getName();// key:得到属性名
            Object value = null;// 得到此属性的值
            value = f.get(student);

            //System.out.println("属性个数:" + size + "\t 类型:" + type + "\t 属性名:" + key + "\t 属性值 : " + value);

            if (key.endsWith("name")) {
                f.set(student, "张三");// 给属性设值
                System.out.println(student);
            } else if (key.endsWith("id")) {
                f.set(student, 6);
                System.out.println(student);
            } else if (key.endsWith("gender")) {
                f.set(student, true);
                System.out.println(student);
            } else if (key.endsWith("birthday")) {
                f.set(student, new Date());
                System.out.println(student);
            } else if (key.endsWith("score")) {
                f.set(student, 44);
                System.out.println(student);
            }

        }

        /*
         * 得到类中所有方法的集合
         */
        Method[] methods = studentClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("get")) {
                System.out.print("类中的get方法:" + method.getName() + "\t");
                System.out.println("get方法的值:" + method.invoke(student));
            }
        }

    }

}

class Student {

    private String name;
    private Integer id;
    private boolean gender;// true:男;false:女
    private Date birthday;
    private int score;

    public Student() {
    }

    public Student(Integer id, String name, int score, Date birthday, boolean gender) {
        super();
        this.id = id;
        this.name = name;
        this.score = score;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", score=" + score + ", birthday=" + birthday + ", gender="
                + gender + "]";
    }
}