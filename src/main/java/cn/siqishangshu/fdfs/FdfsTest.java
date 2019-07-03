package cn.siqishangshu.fdfs;

import java.io.File;
import java.io.FileInputStream;

/**
 * Function: FdfsTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 18:58:00
 */


public class FdfsTest {

    /**
     * 上传
     */
    public static void upload() throws Exception {
        File file = new File("e6cc8f04-505b-4dc5-983e-d73d1e6ccf0a.pdf");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        FastDFSClientUtils fastDFSClientUtils = new FastDFSClientUtils();

        String fileId = fastDFSClientUtils.uploadFile(bytes, file.getName());

        System.out.println("本地文件：" + file + "，上传成功！ 文件ID为：" + fileId);
    }



    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

//        inputStream, size, fileName
         upload();

    }

}