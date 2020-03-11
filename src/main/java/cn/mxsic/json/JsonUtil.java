package cn.mxsic.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: JsonUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-12-20 16:08:00
 */

public class JsonUtil {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType    对象中的object类型
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        return toJSONString(object, null);
    }

    /**
     * @param object
     * @return
     */
    public static String toJSONString(Object object, PropertyNamingStrategy namingStrategy) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (namingStrategy != null){
                mapper.setPropertyNamingStrategy(namingStrategy);
            }
            String json = mapper.writeValueAsString(object);
            return json;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T toObject(String jsonStr, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonStr, classType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> T toObjectIgnoreUnknownProperties(String jsonStr, Class<T> classType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(jsonStr, classType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> List<T> toList(String str, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        List<T> beanList = null;
        try {
            beanList = mapper.readValue(str, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return beanList;
    }
}

