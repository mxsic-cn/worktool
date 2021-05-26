package cn.mxsic.file;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import cn.mxsic.fdfs.FastDFSClientUtils;

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
                String fileName = UUID.randomUUID() + ".pdf";
                FastDFSClientUtils fastDFSClientUtils = new FastDFSClientUtils();
                InputStream inputStream = httpConnection.getInputStream();
                //随机生成文件名
//                File file = File.createTempFile("ticket_", ".pdf");
//                System.out.println("local:" + file.getAbsolutePath());
//                FileOutputStream outputStream = new FileOutputStream(file);
//                IOUtils.copy(inputStream ,outputStream );
//                outputStream.flush();
//                IOUtils.closeQuietly(outputStream);
//                IOUtils.closeQuietly(inputStream);
//                FileInputStream fileInputStream = new FileInputStream(file);
//                byte[]  bytes = new byte[fileInputStream.available()];
//                fileInputStream.read(bytes);

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                byte[] buffer = new byte[2048];
                int readSize;
                while ((readSize = inputStream.read(buffer)) >= 0) out.write(buffer, 0, readSize);
                inputStream.close();
                String fileId = fastDFSClientUtils.uploadFile(out.toByteArray(), fileName);
                System.out.println("upload:" + fileId);


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
