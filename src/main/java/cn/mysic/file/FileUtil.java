package cn.mysic.file;

import java.io.*;

/**
 * Created by liuchuan on 12/9/16.
 */
public class FileUtil {


        private static String filePath = "/home/liuchuan/tem/";


        /**
         * the sql log should be write one day a file .
         */
        public static void writeObject(Object o){
            String fileName = "obj.log";
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = null;
            try {
                file = new File(filePath+File.separator+fileName);
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file,false));
                oos.writeObject(o);
                oos.flush();
                oos.close();
                getFileSize("Object",file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static void writeJson(String log ){
        String fileName = "json.log";
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        // 打开一个随机访问文件流，按读写方式
        RandomAccessFile randomFile = null;
        try {
            file = new File(filePath+File.separator+fileName);
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            randomFile = new RandomAccessFile(file.getAbsolutePath(), "rw");
            randomFile.writeBytes(log);
            randomFile.close();
            getFileSize("JSON",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void getFileSize(String string,File file) {
        System.out.println(string +":"+ Math.floor(file.length() / (1024 * 1024)) + "MB");
    }


}
