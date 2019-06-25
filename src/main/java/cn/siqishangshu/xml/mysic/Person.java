package cn.siqishangshu.xml.mysic;

import lombok.Data;

/**
 * Created by siqishangshu on 17/10/13.
 */
@Data
public class Person {

    private String perId;
    private String perName ;
    private String perAge ;
    private String perSex ;
    private String email ;

    public Person() {
        // TODO Auto-generated constructor stub
    }

    public Person(String perId, String perName, String perAge, String perSex,String email) {
        this.perId = perId;
        this.perName = perName;
        this.perAge = perAge;
        this.perSex = perSex;
        this.email = email ;
    }


}