package cn.mysic.xml;

import java.io.File;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
public class XmlMain {

    public static void main(String[] args) {
        System.out.printf("xml ");
        Computer computer = new Computer("12311","23423",new Date(),23.42);
        // 创建需要转换的对象
        Userm user = new Userm(1, "Steven", "@sun123", new Date(),1000.0,computer);
        System.out.println("---将对象转换成string类型的xml Start---");
        // 将对象转换成string类型的xml
        String str = XmlUtil.convertToXml(user);
        // 输出
        System.out.println(str);
        System.out.println("---将对象转换成string类型的xml End---");
        System.out.println();
        System.out.println("---将String类型的xml转换成对象 Start---");
        Userm userTest = (Userm) XmlUtil.convertXmlStrToObject(Userm.class, str);
        System.out.println(userTest);
        System.out.println("---将String类型的xml转换成对象 End---");
//        convertInMemery();
//        convertFile();
    }

    private static void convertFile() {
        User user = new User(1, "Steven", "@sun123", new Date(),100.0);

        String path = "src/cn/mysic/xml/user.xml";
        File file = new File(path);

        System.out.println("---"+file.getAbsolutePath()+"---");
        System.out.println("---将对象转换成File类型的xml Start---");
        XmlUtil.convertToXml(user, path);
        System.out.println("---将对象转换成File类型的xml End---");
        System.out.println();
        System.out.println("---将File类型的xml转换成对象 Start---");
        User user2 = (User) XmlUtil.convertXmlFileToObject(User.class, path);
        System.out.println(user2);
        System.out.println("---将File类型的xml转换成对象 End---");
    }


    private static void convertInMemery() {
        // 创建需要转换的对象
        User user = new User(1, "Steven", "@sun123", new Date(),1000.0);
        System.out.println("---将对象转换成string类型的xml Start---");
        // 将对象转换成string类型的xml
        String str = XmlUtil.convertToXml(user);
        // 输出
        System.out.println(str);
        System.out.println("---将对象转换成string类型的xml End---");
        System.out.println();
        System.out.println("---将String类型的xml转换成对象 Start---");
        User userTest = (User) XmlUtil.convertXmlStrToObject(User.class, str);
        System.out.println(userTest);
        System.out.println("---将String类型的xml转换成对象 End---");
    }
}
