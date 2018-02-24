package cn.mysic.crypt;

import cn.mysic.file.ByteFileUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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

//        publicKey = RSACoder.getPublicKey(keyMap);
//        privateKey = RSACoder.getPrivateKey(keyMap);
//        System.err.println("公钥: \n\r" + publicKey);
//        System.err.println("私钥： \n\r" + privateKey);
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

        byte[] encodedData = RSACoder.encryptByPublicKey(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAc4JCNQASeL8TUlwiBSeGyMUo6Gi2tV5vTIlMZemPwYZxouiZn8/Q9lRVl92lSZIFTEDT2yWJ2aeda1CX/GZ7GpjnoJ00ewhuS2a1JuruqRi1XuFByHmFiyIJ2WA6m/vvKaA9D6eavwO/S0A4mt88ACHo+03zj2U9HtM3YjOynQIDAQAB");

//        System.out.println(encodedData);

        System.err.println("加密后: " + new String(encodedData));

        ByteFileUtil byteFileUtil = new ByteFileUtil();
        byteFileUtil.writeFile(encodedData);
        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIBzgkI1ABJ4vxNSXCIFJ4bIxSjoaLa1Xm9MiUxl6Y/BhnGi6Jmfz9D2VFWX3aVJkgVMQNPbJYnZp51rUJf8ZnsamOegnTR7CG5LZrUm6u6pGLVe4UHIeYWLIgnZYDqb++8poD0Pp5q/A79LQDia3zwAIej7TfOPZT0e0zdiM7KdAgMBAAECgYBqC/LsaglHNry5XPOHeq3pTvPHFCjvPslaJdvsYO70qhcu1gomWpQdtmBl0d1BSn3tNckHnxNcvX7uLdz5vX101TCLIzhZ9CcKiwYw3/CurtqPQg3sfkjejrz9P5+82YecAnCa1T8nIHlbHqn/5MMeVS89BB07VzlAkBq1xSkpwQJBALrOJFu4QS4pYiJ8e3098LFlAIiTB7CsboI2LDuFxRdmvaTQ3FxGITncBohVICvoB+Dv9d1udEAc5x1vsjRx42UCQQCwB+3wQEV23OpBAQgFD6Y9OsKtr2iLnuNZrhBCWhLVLRqIfPzW4E3jcPdvI0/aphKrRsumZKlyJB9XXNl3KArZAkAd3zkRMbW6nAOFYEKfFHiY5X3gWSKxreE+EUuheRK7W0KYSI30c4J8riKdSn+KheA8qCK49iDA395VZrcoi83BAkEApOXIrtaGO+KWTm545MTUJWd2Ft3KQYCF2OyFrqJSIgAuas0qzN8yF20rXKDnnRsVQfz/qM+DK7lK4rvHuyd3gQJBAJKhks9+KSq9nCkkBkJTQ1X+xS6uVoofm1WKQZbwIXBbvIgQYBK3OQcKgpJ7GmdSEPEA6s5gVLaUVhGV/879OiU=");

        System.out.println(decodedData);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);
    }
}