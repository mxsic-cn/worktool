package cn.mysic.crypt;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-09 11:08:00
 */
public class EncryptCoder {

    private final static String DES = "DES";

    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 正式执行加密操作
        return cipher.doFinal(src);
    }
    /**
     *
     * @param password 密码
     * @param key 加密字符串
     * @return
     */
    public final static String encrypt(String password, String key) {
        try {
            return byte2String(encrypt(password.getBytes(), key.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }

    public static String byte2String(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
    public static String DataEncrypt(String str,byte[] key){


        String encrypt = null;
        try{
            byte[] ret = encrypt(str.getBytes("UTF-8"),key);
            encrypt = new String(Base64.encode(ret));
        }catch(Exception e){
            System.out.print(e);
            encrypt = str;
        }
        return encrypt;
    }
    public static void main(String[] args){
        String message = "{\n" +
                "    \"name\": \"中国\",\n" +
                "    \"province\": [{\n" +
                "        \"name\": \"黑龙江\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"哈尔滨\", \"大庆\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"广东\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"广州\", \"深圳\", \"珠海\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"台湾\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"台北\", \"高雄\"]\n" +
                "        }\n" +
                "    }, {\n" +
                "        \"name\": \"新疆\",\n" +
                "        \"cities\": {\n" +
                "            \"city\": [\"乌鲁木齐\"]\n" +
                "        }\n" +
                "    }]\n" +
                "}";
        System.out.println("加密前:"+message);
        String key = "FKielOEjsldjw8234*923840)234'-+039499234－—－01239";
        String password = "FKielOEjsldjw8234*923840)234'-+039499234－—－01239";
        String encryptString = EncryptCoder.encrypt(message,password);
        System.out.println("加密后:"+encryptString);
        encryptString = EncryptCoder.encrypt(encryptString, key);
        System.out.println("加密后:"+encryptString);
        String decryptString = DecryptCoder.decrypt(encryptString, password);
        System.out.println("解密后:"+decryptString);
        decryptString = DecryptCoder.decrypt(decryptString, key);
        System.out.println("解密后:"+decryptString);


    }
    //输出：B00542E93695F4CFCE34FC4393C2F4BF
}

