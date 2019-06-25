package cn.siqishangshu.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Copyright 2014 Istuary Innovation Lab Inc.
 * All rights reserved.
 * Created by wzhong on 2/4/14.
 */
public class Settings {
    public static final String PREFIX = "/data/sw/";
    public static final String FILE_NAME = "southwest.conf";
    private static AtomicReference<Config> instance = new AtomicReference<Config>();

    static {
        refresh();
    }

    public static Config getInstance() {
        return instance.get();
    }


    public static void refresh() {
        String path = System.getProperty(Settings.class.getName(), "cn/siqishangshu/java/start/config/southwest.conf");

        Config fallback = null;
        try {
            fallback = ConfigFactory.load(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fullPath = PREFIX + FILE_NAME;

        File configFile = new File(fullPath);
        if (configFile.exists()) {
            Config config = ConfigFactory.parseFile(configFile);
            config = config.withFallback(fallback);
            instance.set(config);
        } else {
            instance.set(fallback);
        }
    }


    public static void copyValueWithDefault(Map configuration, String destPath, String srcPath, String defaultValue){
        try {
            String value = getInstance().getString(srcPath);
            configuration.put(destPath, value);
        } catch (Exception e) {
            e.printStackTrace();
            configuration.put(destPath, defaultValue);
        }
    }

    public static void copyValueWithDefault(Map configuration, String destPath, String srcPath, int defaultValue){
        try {
            int value = getInstance().getInt(srcPath);
            configuration.put(destPath, value);
        } catch (Exception e) {
            e.printStackTrace();
            configuration.put(destPath, defaultValue);
        }
    }

    public static void copyValueWithDefault(Map configuration, String destPath, String srcPath, double defaultValue){
        try {
            double value = getInstance().getDouble(srcPath);
            configuration.put(destPath, value);
        } catch (Exception e) {
            e.printStackTrace();
            configuration.put(destPath, defaultValue);
        }
    }

    public static void copyValueWithDefault(Map configuration, String destPath, String srcPath, boolean defaultValue){
        try {
            boolean value = getInstance().getBoolean(srcPath);
            configuration.put(destPath, value);
        } catch (Exception e) {
            e.printStackTrace();
            configuration.put(destPath, defaultValue);
        }
    }

    public static String getOrDefault(Map configuration, String key, String defaultValue){
        return Optional.ofNullable(configuration)
                .map(conf->(String)configuration.getOrDefault(key, defaultValue))
                .orElse(defaultValue);
    }

    public static int getOrDefault(Map configuration, String key, int defaultValue){
        return Optional.ofNullable(configuration)
                .map(conf->(int)configuration.getOrDefault(key, defaultValue))
                .orElse(defaultValue);
    }

    public static double getOrDefault(Map configuration, String key, double defaultValue){
        return Optional.ofNullable(configuration)
                .map(conf->(double)configuration.getOrDefault(key, defaultValue))
                .orElse(defaultValue);
    }
}
