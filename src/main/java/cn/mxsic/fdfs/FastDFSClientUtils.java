package cn.mxsic.fdfs;


import java.io.IOException;

/**
 * Function: FastDFSHelper <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 18:50:00
 */

public class FastDFSClientUtils {


    // 配置文件路径



    public FastDFSClientUtils() throws Exception{

    }

    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 上传文件
     * @param data
     * @param fileName
     * @return
     * @throws IOException
     */
    public String uploadFile(byte[] data,String fileName) throws IOException,Exception {
        return null;
    }
}
