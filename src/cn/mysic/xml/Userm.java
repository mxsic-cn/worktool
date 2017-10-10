package cn.mysic.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Userm")
@XmlType(propOrder = {
        "computer",
})
public class Userm extends User implements Serializable {
    private Computer computer;

    public Userm() {
        super();
    }
    public Userm(Computer computer) {
        this.computer = computer;
    }

    public Userm(int userId, String userName, String password, Date birthday, double money, Computer computer) {
        super(userId, userName, password, birthday, money);
        this.computer = computer;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public String toString() {
        return super.toString()+
                "Userm{" +
                "computer=" + computer +
                '}';
    }
}
