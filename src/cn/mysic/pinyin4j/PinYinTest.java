package cn.mysic.pinyin4j;

import cn.mysic.netant.LogUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;

/**
 * Created by liuchuan on 10/29/16.
 */
public class PinYinTest {
    private static String path = "/home/liuchuan/Documents/txt";
    public static void main(String[] args) {
        PinYinTest pinYinTest = new PinYinTest();
        String[] files = new File(path+File.separator).list();
        for (String name : files) {
            pinYinTest.readTxt(name);
        }
        }


    public void readTxt(String name){
        try {
            String encoding = "UTF-8";
            File file = new File(path+File.separator+name);
            System.out.println("now : "+file.getAbsolutePath());
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding
                );
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt =  null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((lineTxt = bufferedReader.readLine()) != null ){

                    String str = lineTxt;
                    for (char c: str.toCharArray()) {
                        stringBuilder.append(covertHanZi(c));
                    }
                    LogUtil.writeLog(stringBuilder.toString(),name);
                    stringBuilder = new StringBuilder();
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

    private  String covertHanZi(char chineseCharacter) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        String[] pinyinArray = null;
        try
        {
            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chineseCharacter, outputFormat);
        } catch (BadHanyuPinyinOutputFormatCombination e1)
        {
            e1.printStackTrace();
        }
        return pinyinArray == null ? chineseCharacter+"" : pinyinArray[0];
    }


    /**
     * @param pinyinArray
     * @return
     */
    private String concatPinyinStringArray(String[] pinyinArray)
    {
        StringBuffer pinyinStrBuf = new StringBuffer();

        if ((null != pinyinArray) && (pinyinArray.length > 0))
        {
            for (int i = 0; i < pinyinArray.length; i++)
            {
                pinyinStrBuf.append(pinyinArray[i]);
                pinyinStrBuf.append(System.getProperty("line.separator"));
            }
        }
        String outputString = pinyinStrBuf.toString();
        return outputString;
    }
}
