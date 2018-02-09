package cn.mysic.crypt;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-02 15:08:00
 */
public class Test {
        public static void main(String[] args) {
            int xorNum = 12345;
//            String res = "name&password";
            String res = "12132182948273611213218294827361121321829482736112132182948273611213218294827361121321829482736112132182948273611213218294827361";
            String key = "12132182948273611213218294827361121321829482736112132182948273611213218294827361121321829482736112132182948273611213218294827361";
            System.out.println("-------------------------BASE64 可逆--------------------------");
            String base64_encodedStr = BASE64Util.getInstance().encode(res);
            System.out.println("加密：" + base64_encodedStr);
            System.out.println("解密：" + BASE64Util.getInstance().decode(base64_encodedStr));
            System.out.println("-------------------------MD5 不可逆--------------------------");
            String md5_encodedStr = MD5Util.getInstance().encode(res);
            System.out.println("无密码加密：" + md5_encodedStr);
            System.out.println("有密码加密：" + MD5Util.getInstance().encode(md5_encodedStr, key));
            System.out.println("-------------------------SHA1 不可逆--------------------------");
            String sha1_encodedStr = SHA1Util.getInstance().encode(res);
            System.out.println("无密码加密：" + sha1_encodedStr);
            System.out.println("有密码加密：" + SHA1Util.getInstance().encode(sha1_encodedStr, key));
            System.out.println("-------------------------AES 可逆--------------------------");
            String aes_encodedStr = AESUtil.getInstance().encode(res, key);
            System.out.println("加密：" + aes_encodedStr);
            System.out.println("解密：" + AESUtil.getInstance().decode(aes_encodedStr, key));
            System.out.println("-------------------------DES 可逆--------------------------");
            String des_encodedStr = DESUtil.getInstance().encode(res, key);
            System.out.println("加密：" + des_encodedStr);
            System.out.println("解密：" + DESUtil.getInstance().decode(des_encodedStr, key));
            System.out.println("-------------------------XOR 可逆--------------------------");
            String xor_encodedStr = XORUtil.getInstance().encode(res, key);
            System.out.println("文本加密：" + xor_encodedStr);
            System.out.println("文本解密：" + XORUtil.getInstance().decode(xor_encodedStr, key));
            int xor_encodedNum = XORUtil.getInstance().code(xorNum, key);
            System.out.println("数字加密：" + xor_encodedNum);
            System.out.println("数字解密：" + XORUtil.getInstance().code(xor_encodedNum, key));
        }
    }