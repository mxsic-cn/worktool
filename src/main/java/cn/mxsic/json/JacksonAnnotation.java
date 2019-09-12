package cn.mxsic.json;

import java.util.Date;

/**
 * Function: JacksonAnnotation <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-12-20 15:46:00
 */
public class JacksonAnnotation {

    public static void main(String[] args) {
        SupUser supUser = SupUser.builder()
                .code("code")
                .createTime(new Date())
                .password("password")
                .isDeleted(true)
                .isLocked(true)
                .name("name").build();
//        System.out.println(supUser);
        System.out.println(JsonUtil.objectToJson(supUser));

        String json = "{\"id\":null,\"code\":\"code\",\"name\":\"name\",\"email\":null,\"mobilePhone\":null,\"isMaster\":null,\"isDeleted\":true,\"isLocked\":true,\"supplierId\":null,\"avatar\":null,\"needResetPwd\":true}";

//        System.out.println(JsonUtil.jsonToPojo(json, SupUser.class));
    }
}
