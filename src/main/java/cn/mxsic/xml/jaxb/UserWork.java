package cn.mxsic.xml.jaxb;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stream")
@Data
public class UserWork extends User implements Serializable {

    private Computer row;

    public UserWork() {
        super();
    }
    public UserWork(Computer row) {
        this.row = row;
    }

    public UserWork(int userId, String userName, String password, Date birthday, double money, Computer row) {
        super(userId, userName, password, birthday, money);
        this.row = row;
    }

    @Override
    public String toString() {
        return "UserWork{" +
                super.toString()+
                "Computer=" + row +
                '}';
    }
}
