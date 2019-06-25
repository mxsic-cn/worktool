package cn.siqishangshu.pinyin4j;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuchuan on 6/12/17.
 */
public class PinYinCounter {


    public static void main(String[] args) {
        System.out.println("SDF");
//        countSingleCharPlusStart();
//        countPinYinAndEnglish();
//        PinYinDoubleChar.countCharStart();
//        PinYinDoubleChar.countCharPlusStart();
//        PinYinThreeChar.countCharStart();
//        PinYinThreeChar.countCharPlusStart();
//         writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/SingleCharCount.txt")),"SingleCharCounter.txt");
//         writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/DoubleCharCount.txt")),"DoubleCharCounter.txt");
//         writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/ThreeCharCount.txt")),"ThreeCharCounter.txt");
    }

    public static Map<String,Double> readCounter(String filePath){
        Map<String,Double> map = new HashMap<>();
            try {
                String encoding = "UTF-8";
                File file = new File(filePath);
                System.out.println("now : "+file.getAbsolutePath());
                if(file.isFile() && file.exists()){
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file),encoding
                    );
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt =  null;
                    while ((lineTxt = bufferedReader.readLine()) != null ){
                        String[] strs = lineTxt.split("   ");
                        if(strs.length == 2){
                            map.put(strs[0],Double.parseDouble(strs[1]));
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
        return map;
        }

    public static Map<String,Double> sortCharCounter(Map<String,Double> map){
//        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> sorted_map = null  ;//= new TreeMap<String,Double>(bvc);
        sorted_map.putAll(map);
        return sorted_map;
    }

    public static void writeCharCounter(Map<String,Double> map,String fileName){
        for (Map.Entry<String,Double> entry:map.entrySet()) {
            LogUtil.writeLog(entry.getKey()+"   "+Math.floor((entry.getValue()/114532701.0)*3535),fileName);
            System.out.println(entry.getKey()+"   "+Math.floor((entry.getValue()/114532701.0)*3535));
        }
    }
}
