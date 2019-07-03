package cn.siqishangshu.file;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import cn.siqishangshu.fdfs.FastDFSClientUtils;

/**
 * Function: DownloadUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 18:11:00
 */
public class DownloadUtil {
    public static void main(String[] args) {
        URL restServiceURL = null;
        HttpURLConnection httpConnection = null;
        try {
            restServiceURL = new URL("http://111.202.226.69:9026/zxkp/pdf?c=eb058c6c1d5d7278a53a");
            httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            if (httpConnection.getResponseCode() == 200) {
                String fileName = UUID.randomUUID()+ ".pdf";
                FastDFSClientUtils fastDFSClientUtils = new FastDFSClientUtils();
                InputStream inputStream = httpConnection.getInputStream();


                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);

//                File file = new File(fileName);
//                System.out.println("local:"+file.getAbsolutePath());
//                if (!file.exists()) {
//                    file.createNewFile();
//                    FileOutputStream outputStream = new FileOutputStream(file);
//                    outputStream.write(bytes);
//                    outputStream.close();
//                }

//                FileInputStream fileInputStream = new FileInputStream(file);
//                bytes = new byte[fileInputStream.available()];
//                fileInputStream.read(bytes);
                String fileId = fastDFSClientUtils.uploadFile(bytes,fileName);
                System.out.println("upload:"+fileId);
                //随机生成文件名


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }

        }


    }
}
