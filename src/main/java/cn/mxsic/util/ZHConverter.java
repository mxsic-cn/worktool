package cn.mxsic.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 此工具用于中文，简体与繁体之间的转换。
 *
 * 资源文件有很多词是为了提高效率
 */
public class ZHConverter {


    private Properties charMap = new Properties();
    private Set conflictingSets = new HashSet();

    public static final int TRADITIONAL = 0;
    public static final int SIMPLIFIED = 1;
    private static final int NUM_OF_CONVERTERS = 2;
    private static final ZHConverter[] converters = new ZHConverter[NUM_OF_CONVERTERS];
    private static final String[] propertyFiles = new String[NUM_OF_CONVERTERS];

    static {
        propertyFiles[TRADITIONAL] = "zh2Han/zh2Hant.properties";
        propertyFiles[SIMPLIFIED] = "zh2Han/zh2Hans.properties";
    }


    /**
     * @param converterType 0 for traditional and 1 for simplified
     */
    public static ZHConverter getInstance(int converterType) {

        if (converterType >= 0 && converterType < NUM_OF_CONVERTERS) {

            if (converters[converterType] == null) {
                synchronized (ZHConverter.class) {
                    if (converters[converterType] == null) {
                        converters[converterType] = new ZHConverter(propertyFiles[converterType]);
                    }
                }
            }
            return converters[converterType];

        } else {
            return null;
        }
    }

    public static String convert(String text, int converterType) {
        ZHConverter instance = getInstance(converterType);
        return instance.convert(text);
    }


    private ZHConverter(String propertyFile) {
        System.out.println(propertyFile);
        InputStream is = null;
        String file = ZHConverter.class.getClassLoader().getResource(propertyFile).getPath();
        BufferedReader reader = null;
        try {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            charMap.load(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null){
                    reader.close();
                }
                if (is != null){
                    is.close();
                }

            } catch (IOException e) {
            }
        }
        initializeHelper();
    }

    private void initializeHelper() {
        Map stringPossibilities = new HashMap();
        Iterator iter = charMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (key.length() >= 1) {

                for (int i = 0; i < (key.length()); i++) {
                    String keySubstring = key.substring(0, i + 1);
                    if (stringPossibilities.containsKey(keySubstring)) {
                        Integer integer = (Integer) (stringPossibilities.get(keySubstring));
                        stringPossibilities.put(keySubstring, new Integer(
                                integer.intValue() + 1));

                    } else {
                        stringPossibilities.put(keySubstring, new Integer(1));
                    }

                }
            }
        }

        iter = stringPossibilities.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (((Integer) (stringPossibilities.get(key))).intValue() > 1) {
                conflictingSets.add(key);
            }
        }
    }

    public String convert(String in) {
        StringBuilder outString = new StringBuilder();
        StringBuilder stackString = new StringBuilder();

        for (int i = 0; i < in.length(); i++) {

            char c = in.charAt(i);
            String key = "" + c;
            stackString.append(key);

            if (conflictingSets.contains(stackString.toString())) {
            } else if (charMap.containsKey(stackString.toString())) {
                outString.append(charMap.get(stackString.toString()));
                stackString.setLength(0);
            } else {
                CharSequence sequence = stackString.subSequence(0, stackString.length() - 1);
                stackString.delete(0, stackString.length() - 1);
                flushStack(outString, new StringBuilder(sequence));
            }
        }

        flushStack(outString, stackString);

        return outString.toString();
    }


    private void flushStack(StringBuilder outString, StringBuilder stackString) {
        while (stackString.length() > 0) {
            if (charMap.containsKey(stackString.toString())) {
                outString.append(charMap.get(stackString.toString()));
                stackString.setLength(0);

            } else {
                outString.append("" + stackString.charAt(0));
                stackString.delete(0, 1);
            }

        }
    }


//    public static void main(String[] args) {
//        System.out.println(ZHConverter.convert("汉语，即汉族的传统语言，是中国通用语言，国际通用语言之一，属汉藏语系，同中国境内的藏语、壮语、傣语、侗语、黎语、彝语、苗语、瑶语，中国境外的泰语、缅甸语等都是亲属语言。 [1] \n" +
//                "汉语历史悠久，使用人数最多，世界上使用汉语的人数至少15亿 [2]  ，超过世界总人口的20%，是中国、新加坡的官方语言，亦是联合国六种工作语言之一，主要流通于中国和新加坡、马来西亚、缅甸、泰国等东南亚国家以及美国、加拿大、澳大利亚、新西兰、日本等国的汉族华人社区。\n" +
//                "汉语是孤立语，一般有三到十五种声调。汉语的文字系统汉字是一种意音文字，兼具表意和表音功能。汉语包含口语和书面语两部分，古代书面汉语被称为文言文，现代书面汉语被称为白话文，以现代标准汉语为规范。\n" +
//                "汉语有标准语和方言之分。现代标准汉语即普通话，以北京语音为标准音、以官话为基础方言、以典范的现代白话文著作为语法规范。现代标准汉语中，除轻声外共有四个声调。\n" +
//                "汉语一般划分为13种方言（如国际标准化组织ISO 639-3 [3]  国际语种代号标准），各方言可分成多种次方言。十三种汉语一级方言分别是：官话、晋语、粤语、湘语、淮语(有争议)、吴语、徽语、赣语、客家语、闽北语、闽南语、闽东语、闽中语、莆仙话。另有平话等争议较大的方言。", ZHConverter.TRADITIONAL));
//    }

}
