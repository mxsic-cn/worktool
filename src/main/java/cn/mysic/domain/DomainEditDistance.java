package cn.mysic.domain;

import cn.mysic.log.print.LocalLogUtil;
import cn.mysic.util.NumberUtil;
import com.sun.xml.internal.bind.v2.util.EditDistance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuchuan on 12/14/16.
 */
public class DomainEditDistance {
    private static String fileName = "malicious.txt";
    private static Map<String,Double> mapR = new HashMap<>();
    private static Map<String,Integer> mapC = new HashMap<>();
    static final String EDCPath = "cn/mysic/domain/resource/editDistanceConstant.txt";//  0   goodDomain   badDomain
    static final Map<String,List<Double>> EDC;
    static{

        InputStream edcStream = ClassLoader.getSystemResourceAsStream(EDCPath);
        EDC = new HashMap<>();
        if (edcStream != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(edcStream));
            List<List<String>> rawDataList = br.lines().map(l -> Arrays.stream(l.trim().split("\t", 3))
                    .collect(Collectors.toList())).collect(Collectors.toList());
            for (List<String> raw:rawDataList) {
                String key = new String(raw.get(0));
                List<Double> vals = new ArrayList<>();
                vals.add(Double.parseDouble(raw.get(1)));
                vals.add(Double.parseDouble(raw.get(2)));
                EDC.put(key,vals);
            }
        }
    }
    public static Set<String> maliciousDomaineditDistanceFilter(List<String> todoDomainED, List<String> maliciousED) {
        Map<String,Set<String>> lenMap = new HashMap<>();
        Set<String> maliciousDomain = new HashSet<>();
        for (String domain : maliciousED) {
            int len = domain.length();
            if(lenMap.containsKey(String.valueOf(len))){
                lenMap.get(String.valueOf(len)).add(domain);
            }else{
                Set<String> set = new HashSet<>();
                set.add(domain);
                lenMap.put(String.valueOf(len),set);
            }
        }
        for (String todoD:todoDomainED) {
            if (lenMap.containsKey(String.valueOf(todoD.length()))) {
                Set<String> sameLenDo = lenMap.get(String.valueOf(todoD.length()));
                Map<String, Integer> map = new HashMap<>();
                for (String str : sameLenDo) {
                    if (todoD.equals(str)) {
                        continue;
                    }
                    int distance = EditDistance.editDistance(todoD, str);
                    if (map.containsKey(String.valueOf(distance))) {
                        map.put(String.valueOf(distance), map.get(String.valueOf(distance)) + 1);
                    } else {
                        map.put(String.valueOf(distance), 1);
                    }
                }
                LocalLogUtil.writeSqllog("----------" + todoD + "---------", fileName);
                if (anaylzeEditDistance(todoD.length(), map)) {
                    maliciousDomain.add(todoD);
                }
              }
        }
        return maliciousDomain;
    }
        private static boolean anaylzeEditDistance(int len,Map<String, Integer> map) {
            boolean result = false;
            Set<String> keys = new TreeSet<>(map.keySet());
            int max = 0;
            int count = 0;
            for (String key:keys) {
                int tmp = map.get(key);
                if(max<tmp){
                    max = tmp;
                    count += tmp;

                }
            }
            double e = (max*1.0)/count;

//            double goodc = 0;
//            double badc = 0;
//            if(EDC.containsKey(String.valueOf(len))){
//                List<Double> edcs = EDC.get(String.valueOf(len));
//                goodc = edcs.get(0);
//                badc = edcs.get(1);
//            }
//            if(goodc != 0&&badc != 0){
//                double mid = (goodc+badc)/2;
//                if((e>mid&&badc>goodc)||(e<mid&&badc<goodc) ){
//                    result = true;
//                }else{
//                    result = false;
//                }
//            }
//            if(goodc == 0&&badc == 0){
//                result = false;
//            }
//            if(goodc == 0&&badc != 0){
//                result = true;
//            }
//             befor anaylze
            if(count>0){
//                double e = (max*1.0)/count;
                if(mapR.containsKey(String.valueOf(len))){
                    int c = mapC.get(String.valueOf(len));
                    double d = mapR.get(String.valueOf(len));
                    mapC.put(String.valueOf(len),1+c);
                    mapR.put(String.valueOf(len), (c*d+e)/(c+1));
                }else{
                    mapC.put(String.valueOf(len),1);
                    mapR.put(String.valueOf(len), e);
                }
            }
            return result;
        }

        public static void  main(String[] args){
            List<String> maliciousED = new ArrayList<>();
            List<String> todoED = new ArrayList<>();

//            maliciousED.addAll(DomainDetection.goodDomain);
            maliciousED.addAll(DomainDetection.badDomain);
            todoED.addAll(DomainDetection.goodDomain);
//            maliciousED = sortLen(maliciousED);
            Set<String> list = maliciousDomaineditDistanceFilter(todoED,maliciousED);
//            LocalLogUtil.writeSqllog(list.size()+"  true");
//            for (String str : list) {
//                if(todoED.contains(str)){
//                LocalLogUtil.writeSqllog(str, fileName);
//                }else{
//                    System.out.println(str+"  true");
//                }
//            }
            print();
        }

    private static void print() {
        Set<String> set = new HashSet<>(mapR.keySet());
        for (String key : set) {
            LocalLogUtil.writeSqllog(key+" "+NumberUtil.formats(mapR.get(key)), fileName);
            System.out.println(key+" "+NumberUtil.formats(mapR.get(key)));
        }
    }

    private static List<String> sortLen(List<String> maliciousED) {
        List<String> newset = new ArrayList<>();
        Map<String,List<String>> lenMap = new HashMap<>();
        for (String domain : maliciousED) {
            int len = domain.length();
            if(lenMap.containsKey(String.valueOf(len))){
                lenMap.get(String.valueOf(len)).add(domain);
            }else{
                List<String> set = new ArrayList<>();
                set.add(domain);
                lenMap.put(String.valueOf(len),set);
            }
        }
        Set<String> keys = new HashSet<>(lenMap.keySet());
        for (String key:keys){
            if (lenMap.get(key).size()>10){//more than 10 simple so can be ai
                newset.addAll(lenMap.get(key));
            }
        }
        return newset;
    }
}

