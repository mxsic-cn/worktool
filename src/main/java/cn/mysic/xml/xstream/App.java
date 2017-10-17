package cn.mysic.xml.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siqishangshu on 17/10/13.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("xstram...");
        testObj2XmlNew();
    }




    public static void testObj2XmlNew(){
        List list = new ArrayList();
        Product product = new Product();
        List list1 = new ArrayList();
        list1.add(new Atrribute());
        list1.add(new Atrribute());
        product.setList(list1);
//        product.setName("userDataList");
        list.add(product);
//        list.add(product);

        Table table = new Table();
        table.setProducts(list);
        table.setUserName("hna");

        //解决了早期bug（"_"转化到xml时会变为"__"）
        //XStream xStream=new XStream(new DomDriver());
        XStream xStream=new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.alias("stream", Table.class);
        xStream.autodetectAnnotations(true);
        String str= xStream.toXML(table);
        System.out.println(str);
    }



//    private static RootBean getRootBean(File xmlFile)  {
//        InputStreamReader reader = null;
//        try {
//            reader = new InputStreamReader(new FileInputStream(xmlFile), "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        XStream xStream=new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
//        xStream.alias("root", RootBean.class);
//        xStream.autodetectAnnotations(true);
//        return (RootBean) xStream.fromXML(reader);
//    }
}
