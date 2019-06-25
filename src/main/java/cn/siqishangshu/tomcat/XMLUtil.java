package cn.siqishangshu.tomcat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Function: XMLUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 16:03:00
 */
public class XMLUtil {


    public static Map<Integer, Map<String, Object>> parseWebXML() throws Exception{

        Map<Integer, Map<String, Object>> result = new HashMap<Integer, Map<String,Object>>();
        //DOM解析器工厂实例
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //获取DOM解析器
        DocumentBuilder db = dbf.newDocumentBuilder();
        //把要解析的XML文档转化为输入流，让DOM解析器解析，从src根目录开始读取
        InputStream in =new FileInputStream(new File(XMLUtil.class.getClassLoader().getResource("web.xml").getPath()));
        //解析输入流，获得document对象
        Document document = db.parse(in);
        //得到xml的根节点
        Element root = document.getDocumentElement();
//		System.out.println("rootName:" + root.getTagName());
        //得到根节点的子节点
        NodeList xmlNodes = root.getChildNodes();
        //循环读取
        for(int i = 0; i < xmlNodes.getLength(); i++) {
            Node config = xmlNodes.item(i);
            //判断是否为元素节点
            if(config != null && config.getNodeType()==Node.ELEMENT_NODE) {
                String nodeName1 = config.getNodeName();
//				System.out.println("nodeName1:" + nodeName1);

                if("servlet".equals(nodeName1)) {
                    Map<String, Object> servletMaps = null;
                    if(result.containsKey(0)) {
                        servletMaps = result.get(0);
                    }else {
                        servletMaps = new HashMap<String, Object>();
                    }

                    //获取元素节点的所有子节点
                    NodeList childNodes = config.getChildNodes();
                    //创建servlet实体类准备接受数据
                    Servlet servlet = new Servlet();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node node = childNodes.item(j);
                        //判断是否为元素节点
                        if(node != null && node.getNodeType()==Node.ELEMENT_NODE) {
                            //读取servlet-name和servlet-class
                            String nodeName2 = node.getNodeName();
//							System.out.println("nodeName2:" + nodeName2);
                            //读取文本内容
                            String textContent = node.getTextContent();
//							System.out.println("textContent:" + textContent);
                            if(nodeName2.equals("servlet-name")) {
                                servlet.setName(textContent);
                            }else if(nodeName2.equals("servlet-class")) {
                                servlet.setClazz(textContent);
                            }
                        }
                    }
                    //结果放到Map中
                    servletMaps.put(servlet.getName(), servlet);
                    result.put(0, servletMaps);
                }else if (nodeName1.equals("servlet-mapping")) {
                    Map<String, Object> servletMappingMaps = null;
                    if(result.containsKey(1)) {
                        servletMappingMaps = result.get(1);
                    }else {
                        servletMappingMaps = new HashMap<String, Object>();
                    }
                    //获取元素节点的所有子节点
                    NodeList childNodes = config.getChildNodes();
                    //创建实体类
                    ServletMapping servletMapping = new ServletMapping();

                    for(int j = 0; j < childNodes.getLength(); j++) {
                        Node node = childNodes.item(j);
                        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                            String nodeName2 = node.getNodeName();
//							System.out.println("nodeName2:" + nodeName2);
                            String textContent = node.getTextContent();
//							System.out.println("textContent:" + textContent);
                            if(nodeName2.equals("servlet-name")) {
                                servletMapping.setName(textContent);
                            }else if (nodeName2.equals("url-pattern")) {
                                servletMapping.setUrl(textContent);
                            }
                        }
                    }
                    servletMappingMaps.put(servletMapping.getUrl(), servletMapping);
                    result.put(1, servletMappingMaps);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(parseWebXML());
    }

}
