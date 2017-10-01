package cn.mysic.yaml;


import cn.mysic.yaml.adls.User_Defined_Protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by liuchuan on 9/27/16.
 */
public class ObjectYaml {

    public void getYamlFile() throws FileNotFoundException{
        /* read from doc */
//        File f = new File("/home/liuchuan/Documents/tmp/write_two.yaml");
        File f = new File("/home/liuchuan/Documents/yaml/BX_01.yaml");
        System.out.println(f.getAbsolutePath());
        User_Defined_Protocol uerprotocol = Yaml.loadType(new FileInputStream(f.getAbsolutePath()), User_Defined_Protocol.class);
        System.out.println( uerprotocol.getUser_defined_protocol().get(0).getProtocol_name());
//        System.out.println(uerprotocol.getUser_defined_protocol().size());
        /* write to doc */
//        File dumpFile = new File("/home/liuchuan/Documents/tmp/write_two.yaml");
//        User_Defined_Protocol udp = new User_Defined_Protocol();
//        Yaml.dump(udp,dumpFile);

        /* Yaml Parser*/

//        File file = new File("/home/liuchuan/Documents/yaml/BX_01.yaml");
//        YamlParser.parse(file);
//        InputStream is = new FileInputStream(file.getAbsoluteFile());
//        YamlDecoder dec = new YamlDecoder(is);
//
//        System.out.print(Yaml.load(file));

    }
    public static void main(String[] args) throws FileNotFoundException {
        ObjectYaml t = new ObjectYaml();
        t.getYamlFile();
//        Set<Integer> vowelSet = "aeiou".chars().boxed().collect(Collectors.toSet());
//        Set<Integer> numberSet = "1234567890".chars().boxed().collect(Collectors.toSet());
//        Set<Integer> consonantSet = "bcdfghjklmnpqrstvwxyz".chars().boxed().collect(Collectors.toSet());
//        String str = "www.124baidu.com";
//
//        System.out.print(str.chars().filter(vowelSet::contains).count());
//
//
//        String rawDataPath = "/home/liuchuan/Documents/tmp/conficker_alexa.rawdata";
//        InputStream stream = ClassLoader.getSystemResourceAsStream(rawDataPath);
//        Map<String, List<String>> result = new HashMap<>();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
//        List<List<String>> rawDataList = br.lines().map(l -> Arrays.stream(l.trim().split("\t", 2))
//                .collect(Collectors.toList())).collect(Collectors.toList());
//        System.out.print(rawDataList);



    }
}
