package cn.mysic.md5;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by siqishangshu on 17/9/30.
 */
public class Md5Create {
    public static void main(String[] args) {
        System.out.println(new Md5Hash("Rootroot1!", "8c5414").toString().toUpperCase());
//        C9E6D8090421B076E78A2F763DDE338A  111111
        System.out.println("873354F1271EE8E3789429E8E8E56E22");
    }
}
