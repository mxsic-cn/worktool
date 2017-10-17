package cn.mysic.pinyin4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuchuan on 6/28/17.
 */
public class PinYinSingleChar {
    public static final String DATA_PATH = "/home/liuchuan/Documents/pinyin";
    public static final String COUNT_DATA_PATH = "/home/liuchuan/Documents/txt/";
    private static Map<String,Integer> charMap = new HashMap<>();

    public static void countCharStart(){
        String[] paths = new File(DATA_PATH).list();
        for (String name : paths) {
            String fileName = new String(DATA_PATH+File.separator+name);
            countChar(fileName,name);
            System.out.println(fileName+"   ");
        }
    }

    public static void countChar(String input,String output){
        charMapInit();
        try {
            String encoding = "UTF-8";
            File file = new File(input);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                while ((lineTxt = bufferedReader.readLine()) != null ){
                    String str = lineTxt;
                    System.out.println(lineTxt);
                    for (char c: str.toCharArray()) {
                        if(charMap.containsKey(c+"")){
                            charMap.put(c+"",charMap.get(c+"")+1);
                        }
                    }
                }
                read.close();
            }
            for (Map.Entry<String,Integer> entry:charMap.entrySet()) {
                LogUtil.writeLog(entry.getKey()+"#"+entry.getValue(),output);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void countCharPuls(String input){
        try {
            String encoding = "UTF-8";
            File file = new File(input);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                while ((lineTxt = bufferedReader.readLine()) != null ){
                    System.out.println(lineTxt);
                    String[] arr = lineTxt.split("#");
                    if(charMap.containsKey(arr[0]+"")){
                        charMap.put(arr[0]+"",charMap.get(arr[0]+"")+Integer.parseInt(arr[1]));
                    }
                }
                read.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void charMapInit() {
        charMap.put(0+"",0);
        charMap.put(1+"",0);
        charMap.put(2+"",0);
        charMap.put(3+"",0);
        charMap.put(4+"",0);
        charMap.put(5+"",0);
        charMap.put(6+"",0);
        charMap.put(7+"",0);
        charMap.put(8+"",0);
        charMap.put(9+"",0);

        charMap.put("a",0);
        charMap.put("b",0);
        charMap.put("c",0);
        charMap.put("d",0);
        charMap.put("e",0);
        charMap.put("f",0);
        charMap.put("g",0);
        charMap.put("h",0);
        charMap.put("i",0);
        charMap.put("j",0);
        charMap.put("k",0);
        charMap.put("l",0);
        charMap.put("m",0);
        charMap.put("n",0);
        charMap.put("o",0);
        charMap.put("p",0);
        charMap.put("q",0);
        charMap.put("r",0);
        charMap.put("s",0);
        charMap.put("t",0);
        charMap.put("u",0);
        charMap.put("v",0);
        charMap.put("w",0);
        charMap.put("x",0);
        charMap.put("y",0);
        charMap.put("z",0);
    }

    private static void countPinYinAndEnglish() {
        Map<String,Integer> map = new HashMap<>();
        map.put("i",811771411);
        map.put("n",660938948);
        map.put("a",601102260);
        map.put("e",465027906);
        map.put("u",445179467);
        map.put("h",415795467);
        map.put("g",354981707);
        map.put("o",326419311);
        map.put("d",201145253);
        map.put("z",192115946);
        map.put("s",190338098);
        map.put("y",177208568);
        map.put("l",124249354);
        map.put("j",119497122);
        map.put("x",98462130);
        map.put("b",91162157);
        map.put("t",79513075);
        map.put("m",73652209);
        map.put("w",73575454);
        map.put("c",69439184);
        map.put("q",65175660);
        map.put("r",62401980);
        map.put("f",42787386);
        map.put("k",39515666);
        map.put("p",19569998);
        map.put("1",895616);
        map.put("0",719990);
        map.put("7",696182);
        map.put("2",607488);
        map.put("5",511123);
        map.put("3",437891);
        map.put("6",411207);
        map.put("4",403403);
        map.put("9",375525);
        map.put("8",309642);
        map.put("v",137123);

        for (Map.Entry<String,Integer> entry:map.entrySet()) {
            LogUtil.writeLog(entry.getKey()+"   "+ Math.floor(((entry.getValue()-137123)/811771411.0)*100000),"countPinYinAndEnglish.txt");
        }

    }
}
