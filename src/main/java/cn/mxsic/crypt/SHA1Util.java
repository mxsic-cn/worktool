package cn.mxsic.crypt;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
/**
 * Function: SHA1工具类（不可逆）： 使用 SHA1 方法进行加密<br/>
 * SHA1方法加密是不可逆的，不能解密，要想解密就必须使用暴力解密<br/>
 * <p/>
 * 方法中的 res 参数：原始的数据<br/>
 * 方法中的 key 参数：密钥，可以随便写<br/>
 * @author: siqishangshu <br>
 * @date: 2018-02-02 15:05:00
 */


public class SHA1Util {
    public static final String SHA1 = "SHA1";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String charset = null; // 编码格式；默认null为GBK

    private static SHA1Util instance;

    private SHA1Util() {
    }

    // 单例
    public static SHA1Util getInstance() {
        if (instance == null) {
            synchronized (SHA1Util.class) {
                if (instance == null) {
                    instance = new SHA1Util();
                }
            }
        }
        return instance;
    }

    /**
     * 使用 SHA1 方法加密（无密码）
     */
    public String encode(String res) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA1);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return BASE64Util.getInstance().encode(md.digest(resBytes).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用 SHA1 方法加密（可以设密码）
     */
    public String encode(String res, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(HmacSHA1);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, HmacSHA1);
            }
            Mac mac = Mac.getInstance(HmacSHA1);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return BASE64Util.getInstance().encode(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}