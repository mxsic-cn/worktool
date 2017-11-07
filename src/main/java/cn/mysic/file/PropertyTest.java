package cn.mysic.file;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-31 14:56:00
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertyTest {

    public static void main(String[] args) {
        Properties prop = new Properties();
        try{
            //读取属性文件common.properties
            String path = PropertyTest.class.getClassLoader().getResource("common.properties").getPath();
            InputStream in = new BufferedInputStream (new FileInputStream(path));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();

            ///保存属性到b.properties文件
//            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
//            prop.setProperty("phone", "10086");
//            prop.store(oFile, "The New properties file");
//            oFile.close();
        }
        catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}