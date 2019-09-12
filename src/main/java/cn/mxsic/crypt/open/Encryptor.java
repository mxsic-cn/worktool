package cn.mxsic.crypt.open;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Function: 加密机 <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-23 15:26:00
 */
public class Encryptor {

    private static RSACoder rsaCoder = new RSACoder();

    /**
     * 私钥加密
     *
     * @param data
     * @return
     */

    public static String privateEncrypt(byte[] data) throws Exception {
        RSAPrivateKey privateKey = rsaCoder.getPrivateKey();
        Cipher cipher = Cipher.getInstance(OpenConstant.KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64URLSafeString(rsaCoder.rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data, privateKey.getModulus().bitLength()));
    }

    /**
     * 公钥加密
     *
     * @param data
     * @return
     */
    public static String publicEncrypt(byte[] data) throws Exception {
        RSAPublicKey publicKey = rsaCoder.getPublicKey();
        Cipher cipher = Cipher.getInstance(OpenConstant.KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64URLSafeString(rsaCoder.rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data, publicKey.getModulus().bitLength()));
    }

}
