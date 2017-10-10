package cn.mysic.svm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;

/**
 * Created by liuchuan on 5/26/17.
 */
public class Print {

        /**
         * @param args
         */
        public static void main(String[] args) {
            //定义训练集点a{10.0, 10.0} 和 点b{-10.0, -10.0}，对应lable为{1.0, -1.0}
            System.out.println(getHardwareVersion());
        }


    public static String getHardwareVersion() {

        File versionTxt = new File("/hardware.json");
        String hearWareName = "modelName";
        if (versionTxt.exists() && versionTxt.canRead()) {
            try{
                JsonParser parse =new JsonParser();
                JsonObject json =(JsonObject)parse.parse(new FileReader(versionTxt));
                return json.get(hearWareName).getAsString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
