package cn.mxsic.file;

import cn.mxsic.time.DateGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-24 13:44:00
 */
public class ByteFileUtil {
    private  String filePath = "/Users/siqishangshu/Documents/temp";

    public void writeFile(byte[] bytes) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File bFile = new File(filePath + File.separator + DateGenerator.getyyyyMMddHHmmss() + ".byte");
        FileOutputStream outputStream = null;
        if (!bFile.exists()) {
            bFile.createNewFile();
        }
        try {
            outputStream = new FileOutputStream(bFile);
            for (byte aByte : bytes) {
                outputStream.write(aByte);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
