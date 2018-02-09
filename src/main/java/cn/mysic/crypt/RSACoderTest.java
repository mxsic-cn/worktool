package cn.mysic.crypt;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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

    @Before
    public void setUp() throws Exception {
        Map<String, Object> keyMap = RSACoder.initKey();

        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
    }

    @Test
    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String inputStr = "{\n" +
                "\"people\":[\n" +
                "{\n" +
                "\"firstName\": \"Brett\",\n" +
                "\"lastName\":\"McLaughlin\"\n" +
                "},\n" +
                "{\n" +
                "\"firstName\":\"Jason\",\n" +
                "\"lastName\":\"Hunter\"\n" +
                "}\n" +
                "]\t" +
                "}";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKjeWlAk0Y5LG664ctMza27qNRW4NCMeLGD8365kgC/Hhq29G76H1qaJmsxe2N+42cUbbm1OsOCH9TJVhqjSOX1QmuTn34X++4QZin4vlGkYT8ao7IY68Dv6ti7ob/+xUDfN2IHAwDO6XKDab9/UFudN4PNx742n/HWiBOcXtxmwIDAQAB");

        System.out.println(encodedData);

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMqN5aUCTRjksbrrhy0zNrbuo1Fbg0Ix4sYPzfrmSAL8eGrb0bvofWpomazF7Y37jZxRtubU6w4If1MlWGqNI5fVCa5Offhf77hBmKfi+UaRhPxqjshjrwO/q2Luhv/7FQN83YgcDAM7pcoNpv39QW503g83Hvjaf8daIE5xe3GbAgMBAAECgYAxznCAga9ZhqqZTt7/Y0957fYkxD7jYQblMu+wWT2t1kz54bGR5hooty3KiSTfu9JXdNRnsEVYPwin9k/ma2wrsi5lKU5gsu8esy85qzHN20TPZehtZSEA37cQ/KNPLh47D9aOTHOpxKHwIXfWnzo06rdoKS8HnghXGxJ70QYf8QJBAPcwItfy/uIZuKB+GcTmy2bhB/F0xVD1iXij8eMd7gQwbMYKBuiQ9x7OTKoqFIDowj5VrohXjUTHgKs2+g6uTakCQQDRxm2+1Kcmf6IWuTmdNvxlhMKw/Z6F39+mO9Z235C+DAO0p+lthy47D66EWy9dzPhzRmPRmQFKtSkIpB3r2WejAkEAyB/VpeZayy0t1+of7NU6CjHYH3l9op0Xzm9VD56UrBmUhwVpMHLlT7CQSxhEf1Vypcrywfm4Kt4mYvfgnXAq+QJAXwpDln3J7QtOaHUqwSu0Vlo7yuxc8dJoBWx+0gSgEzpmQ2b/vvvL2j5UluqpHxpKRg/SEZf8mdhR6vRQChbJeQJAFPwHXjTB4MAnvbBdPHV5sOBCyzyfCBlTJSYaaLi4r7AOAo/XL0X+EpsN1VSdb6oZ5/1nY5tyLbDKLEsUZhbJ1Q==");
        System.out.println(decodedData);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        assertEquals(inputStr, outputStr);
    }
}