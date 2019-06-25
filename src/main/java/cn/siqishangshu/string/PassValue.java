package cn.siqishangshu.string;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-11 15:01:00
 */
public class PassValue {



    public PassValue() {
    }

    public static void main(String[] args) {
        PassValue passValue = new PassValue();
        User user = passValue.new User();
        user.setName("main");
        user.setAge(20);
        System.out.println(user.toString());
        passValue.pass(user);
        System.out.println(user.toString());


        String name = "hooooo";
        passValue.pass(name);
        System.out.println(name);
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
            return "User :name "+name +" age "+age;
        }
    }
}
