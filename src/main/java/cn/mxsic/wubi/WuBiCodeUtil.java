package cn.mxsic.wubi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Function: DealTheFiles <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-05-18 10:22:00
 */
public class WuBiCodeUtil {

    private static final String BASE_PATH = "/Users/siqishangshu/Documents/mine/五笔资料/";

    public static void main(String[] args) throws IOException {
        dealFile();
    }

    public static void dealFile() throws IOException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(BASE_PATH, "98.txt");
            File fileOut = new File(BASE_PATH, "98.sql");
            if (file.exists()) {
                if (file.isFile()) {
                    if (!fileOut.exists()) {
                        fileOut.createNewFile();
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    bufferedWriter = new BufferedWriter(new FileWriter(fileOut));
                    List<String> list = bufferedReader.lines().collect(Collectors.toList());
//                    read98Txt(list,bufferedWriter);
                    read98Html(list,bufferedWriter);
                } else if (file.isDirectory()) {
                    System.out.println("get other file");
                }
            } else {
                System.out.println("file not found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
        System.out.println("END");
    }

    private static void read98Html(List<String> list, BufferedWriter bufferedWriter) throws IOException {

        Map<String,String> map = new HashMap<>();
        for (int i = 2; i < list.size(); i = i+9) {
             String key,value;
             key = list.get(i);
             value = list.get(i+5);
            if (map.containsKey(key)) {
                String wb = map.get(key);
                if (wb.length()>value.length()) {
                    map.put(key,value);
                }
            }else{
                map.put(key,value);
            }
        }
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i % 500 == 0) {
                if (i != 0) {
                    bufferedWriter.write(";");
                }
                bufferedWriter.write("INSERT IGNORE INTO `wb_chinese_wubi_new` ( `chinese`, `wubi`)\n" +
                        "VALUES");
                bufferedWriter.write("('" + entry.getKey() + "', '" + entry.getValue() + "')\n");
            } else {
                bufferedWriter.write(",('" + entry.getKey() + "', '" + entry.getValue() + "')\n");
            }
            i++;
        }
        bufferedWriter.write(";");
    }


    private static void read98Txt(List<String> list, BufferedWriter bufferedWriter) throws IOException {

        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i).split("\t");
            if (arr.length != 2) {
                System.out.println(list.get(i));
                continue;
            }
            if (map.containsKey(arr[0])) {
                String wb = map.get(arr[0]);
                if (wb.length()>arr[1].length()) {
                    map.put(arr[0],arr[1]);
                }
            }else{
                map.put(arr[0],arr[1]);
            }
        }
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i % 500 == 0) {
                if (i != 0) {
                    bufferedWriter.write(";");
                }
                bufferedWriter.write("INSERT IGNORE INTO `wb_chinese_wubi_new` ( `chinese`, `wubi`)\n" +
                        "VALUES");
                bufferedWriter.write("('" + entry.getKey() + "', '" + entry.getValue() + "')\n");
            } else {
                bufferedWriter.write(",('" + entry.getKey() + "', '" + entry.getValue() + "')\n");
            }
            i++;
        }
        bufferedWriter.write(";");
    }

}
