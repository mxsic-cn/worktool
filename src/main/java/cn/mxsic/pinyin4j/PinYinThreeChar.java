package cn.mxsic.pinyin4j;


import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liuchuan on 6/12/17.
 */
public class PinYinThreeChar {
    public static final String DATA_PATH = "/home/liuchuan/Documents/pinyin";
    public static final String COUNT_DATA_PATH = "/home/liuchuan/Documents/txt/three";
    private static Map<String,Integer> charMap = new HashMap<String,Integer>();


    public static void countCharStart() {
        String[] paths = new File(DATA_PATH).list();
        for (String name : paths) {
            String fileName = new String(DATA_PATH+File.separator+name);
            countChar(fileName,name);
            System.out.println(fileName+"   ");
        }
    }

    private static void countChar(String fileName, String name) {
        try {
            charMapInit();
            String encoding = "UTF-8";
            File file = new File(fileName);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                while ((lineTxt = bufferedReader.readLine()) != null ){
                    String str = lineTxt;
                    System.out.println(lineTxt);
                    char[] chars = str.toCharArray();
                    for (int i= 2; i< chars.length; i++ ) {
                        if(charMap.containsKey(chars[i-2]+""+chars[i-1]+""+chars[i])){
                            charMap.put(chars[i-2]+""+chars[i-1]+""+chars[i],charMap.get(chars[i-2]+""+chars[i-1]+""+chars[i])+1);
                        }
                    }
                }
                read.close();
            }
            for (Map.Entry<String,Integer> entry:charMap.entrySet()) {
                if(entry.getValue() > 0){
                    LogUtil.writeLog(entry.getKey()+"#"+entry.getValue(),name);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void countCharPlusStart(){
        String[] paths = new File(COUNT_DATA_PATH).list();
        for (String name : paths) {
            String fileName = new String(COUNT_DATA_PATH+File.separator+name);
            countCharPuls(fileName);
            System.out.println(fileName+"   ");
        }
        for (Map.Entry<String,Integer> entry:charMap.entrySet()) {
            LogUtil.writeLog(entry.getKey()+"   "+entry.getValue(),"ThreeCharCount.txt");
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
                    if(arr.length ==2){
                        if(charMap.containsKey(arr[0]+"")){
                            charMap.put(arr[0]+"",charMap.get(arr[0]+"")+Integer.parseInt(arr[1]));
                        }else{
                            charMap.put(arr[0]+"",Integer.parseInt(arr[1]));
                        }
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
        InputStream stream = ClassLoader.getSystemResourceAsStream("new_there_ngram.freq");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        List<String> rawDataList = br.lines().collect(Collectors.toList());
        for (String str : rawDataList) {
            charMap.put(str,0);
        }
    }
}
