package cn.mysic.xml.mysic;


/**
 * Created by siqishangshu on 17/10/13.
 */
public class App {

    public static void main(String[] args) {
        String reqXml = getXml();
        System.out.println("输出XML："+"\n"+reqXml);

        System.out.println("解析XML-->Object"+"===========================================");
        Person person = getPerson(reqXml);
        System.out.println("id="+person.getPerId()+",name="+person.getPerName()+",age="+person.getPerAge()+",sex="+person.getPerSex()+",email="+person.getEmail());
    }


    /**
     * 方法名称: getXml<br>
     * 描述： Object --> XML
     * 作者: liqijing
     * 修改日期：2015-4-6下午07:46:03
     * @param body
     * @param encoding
     * @return
     */
    public static String getXml(){
        Body body = new Body();
        body.setRow(initPerson());
        String reqXml = JaxbMapper.objectToXml(body);
        if(reqXml == null){
            return "组装XML出错！" ;
        }
        return reqXml ;
    }


    /**
     * 方法名称: getPerson<br>
     * 描述：XML ---> Object
     * 作者: liqijing
     * 修改日期：2015-4-6下午07:47:21
     * @param reqXml
     * @return
     */
    public static Person getPerson(String reqXml){
        Body body = JaxbMapper.xmlToObject(reqXml, Body.class);
        return body.getRow() ;
    }


    /**
     * 方法名称: initPerson<br>
     * 描述：初始化person对象
     * 作者: liqijing
     * 修改日期：2015-4-6下午07:38:12
     * @return
     */
    public static Person initPerson(){
        Person person = new Person("100000","马云","24","男","lijinginsistsmile@163.com");
        return person ;
    }

}