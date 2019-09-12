package cn.mxsic.crypt.open;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-23 17:16:00
 */
public class Test {
    public static void main(String[] args) throws Exception {
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

        String encodedData =Encryptor.publicEncrypt( data);

        System.err.println("加密后: " + encodedData);

        String decodedData = Decryptor.privateDecrypt(encodedData);

        System.out.println(decodedData);
    }
}
