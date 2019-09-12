package cn.mxsic.domain;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuchuan on 12/20/16
 */

public class DomainDetection {
    static final String goodDomainPath = "cn/mxsic/domain/resource/goodDomain.txt";
    static final String badDomainPath = "cn/mxsic/domain/resource/badDomain.txt";
    static final List<String> goodDomain;
    static final List<String> badDomain;

    static{
        InputStream goodDomainStream = ClassLoader.getSystemResourceAsStream(goodDomainPath);
        if (goodDomainStream != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(goodDomainStream));
            goodDomain = br.lines().collect(Collectors.toList());
        } else {
            goodDomain = new LinkedList<>();
        }
        InputStream badDomainStream = ClassLoader.getSystemResourceAsStream(badDomainPath);
        if (badDomainStream != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(badDomainStream));
            badDomain = br.lines().collect(Collectors.toList());
        } else {
            badDomain =new LinkedList<>();
        }
    }


    static double entropy (String str) {
        return str.chars().boxed().collect(Collectors.toMap(s -> s, s -> 1, Integer::sum)).entrySet()
                .stream().mapToDouble(i -> 1.0*i.getValue()/str.length()).map(i -> -1.0 * i * Math.log(i)).sum();
    }

    static int charCount (String str, Set<Integer> set){
       return  (int)str.chars().filter(set::contains).count();
    }

    static int repeatLetter (String str) {
        return (int) str.chars().boxed().collect(Collectors.toMap(c -> c, c -> 1, Integer::sum)).entrySet()
                .stream().mapToInt(Map.Entry::getValue).filter(i -> i > 1).count();
    }

    static int consecutivePattern (String str, Set<Integer> set) {
        List<Integer> strList = str.chars().boxed().collect(Collectors.toList());
        int consecutiveCount = 0;
        int cnt = 0;

        for (Integer c: strList) {
            if (set.contains(c)) {
                cnt++;
                if (cnt == 2) {
                    consecutiveCount += 2;
                }
                if (cnt > 2) {
                    consecutiveCount += 1;
                }
            }
            else {
                cnt = 0;
            }
        }
        return consecutiveCount;
    }

    static List<String> nGram (int n, String str)
    {
        List<String> grams = new ArrayList<>();
        for(int i = 0; i<= str.length()-n; i++)
            grams.add(str.substring(i,i+n));
        return grams;
    }


    static List<Integer> rankSimple (List<Double> vec) {
        List<Pair<Double, Integer>> vecPair = new ArrayList<>();
        Pair<Double, Integer> pairElem;
        Integer cnt = 0;
        for (Double v : vec) {
            pairElem = Pair.of(v, cnt);
            vecPair.add(pairElem);
            cnt ++;
        }
        return vecPair.stream().sorted((a, b) -> (int) (a.getLeft() - b.getLeft()))
                .map(Pair::getRight).collect(Collectors.toList());
    }

    static List<Double> rankData (List<Double> vec) {
        int n = vec.size();
        List<Integer> iVec = rankSimple(vec);
        List<Double> sVec = iVec.stream().map(vec::get).collect(Collectors.toList());
        double sumRanks = 0.0;
        int dupCount = 0;
        List<Double> newArray = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newArray.add(0.0);
        }

        for (int i = 0; i < n; i ++) {
            sumRanks += i;
            dupCount += 1;
            if (i == n - 1 || ! sVec.get(i).equals(sVec.get(i + 1))) {
                double aveRank = sumRanks / dupCount + 1;
                for (int j = i - dupCount + 1; j < i + 1; j ++) {
                    newArray.set(iVec.get(j), aveRank);
                }
                sumRanks = 0.0;
                dupCount = 0;

            }
        }
        return newArray;
    }
}
