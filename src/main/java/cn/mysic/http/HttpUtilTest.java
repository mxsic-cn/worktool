package cn.mysic.http;

import java.util.UUID;

/**
 * Created by liuchuan on 10/29/16.
 */
public class HttpUtilTest {
    public static void main(String[] args) {
        String url = "http://192.168.110.78:3000/api/v2.0/topologies/baa1668a-70cb-4dcb-8646-c831b774a83e/nodes";
        String tokenname = "accesstoken";
        String tokenvalue = UUID.randomUUID().toString();
    }
}