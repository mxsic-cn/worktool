package cn.mxsic.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Created by siqishangshu on 17/10/10.
 *
 * 封装了XML转换成object，object转换成XML的代码
 *
 *
 */
public class XmlUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //change the diff in citic xml
        return sw.toString().replace(" standalone=\"yes\"","").
                replace("<row>", "<list name=\"userDataList\">\n    <row>")
                .replace("</row>", "</row>\n    </list>");
    }

    /**
     * 将对象根据路径转换成xml文件
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     *   //change the diff in citic xml
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            xmlStr = xmlStr.replace("<list name=\"userDataList\">","").replace("</list>","");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
}