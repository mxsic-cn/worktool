package cn.mxsic.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageTool {
	public static void main(String[] args) throws Exception {  
        URL url = new URL("http://www.guofenchaxun.com/captchaa.php");  
        URLConnection con = url.openConnection();  
        //不超时  
        con.setConnectTimeout(0);  
          
        //不允许缓存  
        con.setUseCaches(false);  
        con.setDefaultUseCaches(false);  
        InputStream is = con.getInputStream();  
          
        //先读入内存  
        ByteArrayOutputStream buf = new ByteArrayOutputStream(8192);  
        byte[] b = new byte[1024];  
        int len;  
        while ((len = is.read(b)) != -1) {  
            buf.write(b, 0, len);  
        }  
        //读图像  
        is=new ByteArrayInputStream(buf.toByteArray());  
        BufferedImage image = ImageIO.read(is);  
  
        System.out.println(image);  
    }  
}
