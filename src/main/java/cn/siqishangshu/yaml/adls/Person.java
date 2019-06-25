package cn.siqishangshu.yaml.adls;



import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by liuchuan on 9/27/16.
 */
public  class Person {
    private String name;
    private int age;
    private Person[] children;

    public Person(String name) {
        this.name = name;
    }
    public Person( Person person) {
        this.name = person.getName();
        this.age = person.getAge();
         if(person.getChildren()!=null &&person.getChildren().length>0){
             Stream<Person> str = Arrays.stream(person.getChildren());
                str.forEach(person1 -> {
                System.out.println(person1.getName());
             });
            this.children = new Person[person.getChildren().length];
            for (int i = 0; i < person.getChildren().length; i++) {
                this.children[i] = new Person(person.getChildren()[i]);
            }
    }
    }
    public Person( ) {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person[] getChildren() {
        return children;
    }

    public void setChildren(Person[] children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
