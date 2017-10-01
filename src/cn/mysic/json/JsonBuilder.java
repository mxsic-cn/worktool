package cn.mysic.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

/**
 * Created by liuchuan on 12/9/16.
 */
public class JsonBuilder {
    public static GsonBuilder createBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new CustomExclusionStrategy());
        return gsonBuilder;
    }

    private static class CustomExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (f.getName().equals("__isset_bitfield")) {
                return true;
            } else if (f.getName().equals("optionals")) {
                return true;
            } else if (f.getName().equals("deleted")) {
                return true;
            } else if (f.getName().endsWith("taskData")) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
