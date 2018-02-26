package cn.mysic.crypt;

import cn.mysic.file.ByteFileUtil;
import org.junit.Before;
import org.junit.Test;

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

    private String publicKey;
    private String privateKey;

    private Set<String> pubList = new HashSet<>();
    private Set<String> priList = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void keyGennerator() throws Exception {
        for (int i = 0; i < 10000; i++) {
            setUp();
            pubList.add(publicKey);
            priList.add(privateKey);
        }
        System.out.println("pubList: " + pubList.size());
        System.out.println("priList: " + priList.size());
    }

    @Test
    public void test() throws Exception {
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

        byte[] encodedData = RSACoder.encryptByPrivateKey(data,privateKey);
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
}