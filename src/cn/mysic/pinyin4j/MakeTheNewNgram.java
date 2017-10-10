package cn.mysic.pinyin4j;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by liuchuan on 6/29/17.
 */
public class MakeTheNewNgram {

    public static void main(String[] args) {
        writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/guiyi/SingleCharCounter.txt"),1),1);
        writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/guiyi/DoubleCharCounter.txt"),2),2);
        writeCharCounter(sortCharCounter(readCounter("/home/liuchuan/Documents/txt/guiyi/ThreeCharCounter.txt"),3),3);
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

    public static Map<String,Double> sortCharCounter(Map<String,Double> map,int i){
        charMapInit(map,i);
        //        ValueComparator bvc =  new ValueComparator(map);
        TreeMap<String,Double> sorted_map = null  ;//= new TreeMap<String,Double>(bvc);
        sorted_map.putAll(map);
        return sorted_map;
    }

    private static void charMapInit(Map<String,Double> map,int i) {
        InputStream stream = ClassLoader.getSystemResourceAsStream("ngram.freq");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        List<String> rawDataList = br.lines().collect(Collectors.toList());
        for (String str : rawDataList) {
            String[] arr = str.split(" ");
            if(arr.length == 3){
                if(Integer.parseInt(arr[0]) == i){
                    if(map.containsKey(arr[1])){
                        map.put(arr[1], map.get(arr[1])+Double.parseDouble(arr[2]));
                    }else{
                        map.put(arr[1], Double.parseDouble(arr[2]));
                    }
                }
            }
        }
    }


    public static void writeCharCounter(Map<String,Double> map,int i){
        for (Map.Entry<String,Double> entry:map.entrySet()) {
            if(entry.getValue().intValue() >0){
                LogUtil.writeLog(i+" "+entry.getKey()+" "+entry.getValue().intValue(),"hy_ngram.freq");
                System.out.println(i+" "+entry.getKey()+" "+entry.getValue().intValue());
            }
        }
    }
}
