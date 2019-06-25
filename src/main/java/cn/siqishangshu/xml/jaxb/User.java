package cn.siqishangshu.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML the root sign in file
public class User implements Serializable {

    // 用户Id
    private int userId;
    // 用户名
    private String userName;
    // 用户密码
    private String password;
    // 用户生日
    private Date birthday;
    // 用户钱包
    private double money;

    public User() {
        super();
    }

    public User(int userId, String userName, String password, Date birthday, double money) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.money = money;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
           if( ((User) obj).getUserId() == this.userId){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", money=" + money +
                '}';
    }
}
