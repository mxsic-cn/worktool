package cn.mysic.netant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by liuchuan on 12/1/16.
 */
public class LogUtil {
    private static String filePath = "/home/liuchuan/Documents/data";
    private static String fileName = "alexa.rawdata";

    public static  void writeLog(String log ){
        writeLog(log,LogUtil.fileName);
    }
    /**
     * the sql log should be write one day a file .
     */
    public static void writeLog(String log,String fileName){
//        LocalLogUtil.fileName = fileName;
        File  file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = null;
        String logInfo = makelog(log);
        try {
            file = new File(filePath+File.separator+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            randomFile = new RandomAccessFile(file.getAbsolutePath(), "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(logInfo);
            randomFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String makelog(String log) {
        return log+"\r\n";
    }
}
