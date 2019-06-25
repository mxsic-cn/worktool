package cn.siqishangshu.crypt.open;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Function: 解密机 <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-23 15:26:00
 */
public class Decryptor {


    private static RSACoder rsaCoder = new RSACoder();
    /**
     * 公钥解密
     *
     * @param data
     * @return
     */

    public static String publicDecrypt(String data) throws Exception {
        RSAPublicKey publicKey = rsaCoder.getPublicKey();
        Cipher cipher = Cipher.getInstance(OpenConstant.KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(rsaCoder.rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()));
    }

    /**
     * 私钥解密
     *
     * @param data
     * @return
     */

    public static String privateDecrypt(String data) throws Exception {
        RSAPrivateKey privateKey = rsaCoder.getPrivateKey();
        Cipher cipher = Cipher.getInstance(OpenConstant.KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(rsaCoder.rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()));
    }
}
