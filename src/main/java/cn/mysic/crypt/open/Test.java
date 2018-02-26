package cn.mysic.crypt.open;


import cn.mysic.file.ByteFileUtil;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-02-26 11:27:00
 */
public class Test {
    public static void main(String[] args) throws Exception {
//         RSACoder rsaCoder = new RSACoder();
//         rsaCoder.createKeys();

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

        System.out.println("\r明文大小：\r\n" + inputStr.getBytes().length);
        String encodedData = Encryptor.publicEncrypt(inputStr);
//        String encodedData = Encryptor.privateEncrypt(inputStr);
        System.out.println("密文：\r\n" + encodedData);
        ByteFileUtil byteFileUtil = new ByteFileUtil();
        byteFileUtil.writeFile(encodedData.getBytes());
        String decodedData = Decryptor.privateDecrypt(encodedData);
//        String decodedData = Decryptor.publicDecrypt(encodedData);
        System.out.println("解密后文字: \r\n" + decodedData);
        String priStrg="RYnJ9vEfKH7dpyfz4_O8v3C90cC5hw5XHNYHqFaG-uGmUJQLy004MaAnLGIP5e5k8Ggrek7iQfVRKuFNZski43WhDydFWrbVtfN_WBd0iQ99YHqQobC8VK33UFnBAGbtkCdMfQh_dg432CcISX7dpJCDlVLXPm5Bxxcjshr_Chg";
        String depriData = Decryptor.publicDecrypt(priStrg);
        System.out.println(depriData);
    }

}
