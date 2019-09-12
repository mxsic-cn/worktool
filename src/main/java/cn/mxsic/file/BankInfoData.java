package cn.mxsic.file;

import java.io.*;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-27 15:18:00
 */
public class BankInfoData {

    private static void readData(String fileName)  {
        try {
            int X = 0;
            String path = BankInfoData.class.getClassLoader().getResource(fileName).getPath();
            String encoding = "GBK";
            File file = new File(path);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                while ((lineTxt = bufferedReader.readLine()) != null ){
                    String str = lineTxt;
                    String string =
                            new String(lineTxt.replace("insert into zjjz_cnaps_bankinfo (BANKNO, STATUS, BANKCLSCODE, CITYCODE, BANKNAME)",
                                    "pay_bank_info (bankno, status, bankclscode, citycode, bankname) " ) );
//                    if(str.contains("values")){
//                        System.out.println(str);
//                        System.out.println(++X);
////                        LogUtil.writeLog( str,"new.data");
//                    }
                    char[] chars = str.toCharArray();
                    for (int i= 1; i< chars.length; i++ ) {
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


    public static void main(String[] args) {
        System.out.println("SDF");
        BankInfoData.readData("olddata.sql");
    }
}
