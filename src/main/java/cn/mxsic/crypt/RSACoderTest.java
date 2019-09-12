package cn.mxsic.crypt;

import cn.mxsic.file.ByteFileUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-09 13:37:00
 */


public class RSACoderTest {

    private static String publicKey;
    private static String privateKey;

    private Set<String> pubList = new HashSet<>();
    private Set<String> priList = new HashSet<>();


    public static void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.genKeyPair();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }




    public static void test1() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "3301239118231921182300102018020914234400202320180209142348000000{\n" +
                "    \"animals\": {\n" +
                "        \"dog\": [\n" +
                "            {\n" +
                "                \"name\": \"Rufus\",\n" +
                "                \"age\":15\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Marty\",\n" +
                "                \"age\": null\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
//        byte[] encodedData = RSACoder.encryptByPublicKey(data,publicKey);

//        System.out.println(encodedData);

        System.err.println("加密后: " + new String(encodedData));

        ByteFileUtil byteFileUtil = new ByteFileUtil();
        byteFileUtil.writeFile(encodedData);
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
//        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

        String outputStr = new String(decodedData);
        System.out.println(outputStr);
    }

    public static void main(String[] args) throws Exception {
        setUp();
        test();
        testSign();
        testHttpSign();
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

    static void testHttpSign() throws Exception {
        String param = "id=1&name=张三";
        byte[] encodedData = RSACoder.encryptByPrivateKey(param.getBytes(), privateKey);
        System.out.println("加密后：" + encodedData);

        byte[] decodedData = RSACoder.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后：" + new String(decodedData));

        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("签名：" + sign);

        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("签名验证结果：" + status);
    }

}