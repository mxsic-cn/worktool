package cn.mxsic.file;

import java.io.*;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-27 15:18:00
 */
public class BankInfoData {

    private static void readData(String filePath)  {
        try {
            int X = 0;
//            String path = BankInfoData.class.getClassLoader().getResource(fileName).getPath();
            String encoding = "GBK";
//            String encoding = "UTF-8";
//            String encoding = "ISO-8859-1";
//            String encoding = "ISO-8859-2";
//            String encoding = "ISO-8859-3";
//            String encoding = "ISO-8859-4";
//            String encoding = "ISO-8859-5";
//            String encoding = "ISO-8859-6";
//            String encoding = "ISO-8859-7";
//            String encoding = "ISO-8859-8";
//            String encoding = "ISO-8859-9";
//            String encoding = "ISO-8859-10";
//            String encoding = "ISO-8859-11";
//            String encoding = "ISO-8859-13";
//            String encoding = "ISO-8859-14";
//            String encoding = "ISO-8859-15";
//            String encoding = "GBK";
            File file = new File(filePath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                while ((lineTxt = bufferedReader.readLine()) != null ){
                    System.out.println(lineTxt);
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


    public static void main(String[] args) {
        System.out.println("SDF");
        BankInfoData.readData("/Users/siqishangshu/Downloads/201907_支付宝帐单/20880319448527100156_201907_业务明细_1.csv");
    }
}
