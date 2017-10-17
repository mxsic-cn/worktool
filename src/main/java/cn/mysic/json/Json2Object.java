package cn.mysic.json;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by liuchuan on 10/20/16.
 */
public class Json2Object {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.print("SDFDFs");
        List<String> list =  AIConfig.getProtocolFileName();
        for (String string: list) {
            System.out.print(string);
        }

    }
}
