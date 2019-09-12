package cn.mxsic.yaml;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by liuchuan on 9/28/16.
 */
public class MapYaml {
    public void getYamlFile() throws FileNotFoundException {
        /* read from doc */
        File f = new File("/home/liuchuan/Documents/tmp/write_two.yaml");
        System.out.println(f.getAbsolutePath());
        Object hashMap = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), HashMap.class);
//        System.out.println(((HashMap)hashMap.get("user-defined-protocol")).get("protocol-name"));
        System.out.println("SDF");

    }
    public static void main(String[] args) throws FileNotFoundException {
        MapYaml t = new MapYaml();
        t.getYamlFile();
    }
}
