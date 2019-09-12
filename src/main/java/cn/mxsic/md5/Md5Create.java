package cn.mxsic.md5;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by siqishangshu on 17/9/30.
 */
public class Md5Create {
    public static void main(String[] args) {
        System.out.println(new Md5Hash("Zz7366231", "5bdd35").toString().toUpperCase());
////        C9E6D8090421B076E78A2F763DDE338A  111111
//          C9E6D8090421B076E78A2F763DDE338A
//        'C9E6D8090421B076E78A2F763DDE338A','8c5414',
//        System.out.println("873354F1271EE8E3789429E8E8E56E22");

        Double d = 100.0;
        System.out.println(0-d);
        Md5Create md5Create = (Md5Create)null;
        if (md5Create !=null) {
            System.out.println(md5Create);
        }
//        System.out.println(Optional.of(md5Create));
//        System.out.println("null = " + md5Create);
    }
}
