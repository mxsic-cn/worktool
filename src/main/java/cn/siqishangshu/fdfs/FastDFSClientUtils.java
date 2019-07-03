package cn.siqishangshu.fdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * Function: FastDFSHelper <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 18:50:00
 */

public class FastDFSClientUtils {

    private TrackerServer trackerServer;

    // 配置文件路径



    public FastDFSClientUtils() throws Exception{
        // 加载配置文件
        ClientGlobal.init("/Users/siqishangshu/workspace/idea/worktool/src/main/resources/fdfs_client.conf");
        // 创建 tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        // 获取tracker连接
        trackerServer = trackerClient.getConnection();
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
     * @throws MyException
     * @throws IOException
     */
    public String uploadFile(byte[] data,String fileName) throws IOException, MyException {
        // 查看storage客户端
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);

        // 上传
        return storageClient.upload_file1(data, getFileExtension(fileName), null);
    }
}
