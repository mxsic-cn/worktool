package cn.mxsic.thoughtworks;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Function: Problem Three: Merchant's Guide to the Galaxy <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-15 16:53:00
 * <p>
 * You decided to give up on earth after the latest financial collapse left 99.99% of the earth's population with 0.01% of the wealth. Luckily, with the scant sum of money that is left in your account, you are able to afford to rent a spaceship, leave earth, and fly all over the galaxy to sell common metals and dirt (which apparently is worth a lot).
 * <p>
 * Buying and selling over the galaxy requires you to convert numbers and units, and you decided to write a program to help you.
 * <p>
 * The numbers used for intergalactic transactions follows similar convention to the roman numerals and you have painstakingly collected the appropriate translation between them.
 * <p>
 * Roman numerals are based on seven symbols:
 * <p>
 * Symbol  Value
 * I   1
 * V   5
 * X   10
 * L   50
 * C   100
 * D   500
 * M   1,000
 * <p>
 * Numbers are formed by combining symbols together and adding the values. For example, MMVI is 1000 + 1000 + 5 + 1 = 2006. Generally, symbols are placed in order of value, starting with the largest values. When smaller values precede larger values, the smaller values are subtracted from the larger values, and the result is added to the total. For example MCMXLIV = 1000 + (1000 − 100) + (50 − 10) + (5 − 1) = 1944.
 * <p>
 * The symbols "I", "X", "C", and "M" can be repeated three times in succession, but no more. (They may appear four times if the third and fourth are separated by a smaller value, such as XXXIX.) "D", "L", and "V" can never be repeated.
 * "I" can be subtracted from "V" and "X" only. "X" can be subtracted from "L" and "C" only. "C" can be subtracted from "D" and "M" only. "V", "L", and "D" can never be subtracted.
 * Only one small-value symbol may be subtracted from any large-value symbol.
 * A number written in [16]Arabic numerals can be broken into digits. For example, 1903 is composed of 1, 9, 0, and 3. To write the Roman numeral, each of the non-zero digits should be treated separately. Inthe above example, 1,000 = M, 900 = CM, and 3 = III. Therefore, 1903 = MCMIII.
 * (Source: Wikipedia ( [17]http://en.wikipedia.org/wiki/Roman_numerals)
 * <p>
 * Input to your program consists of lines of text detailing your notes on the conversion between intergalactic units and roman numerals.
 * <p>
 * You are expected to handle invalid queries appropriately.
 * <p>
 * Test input:
 * glob is I
 * prok is V
 * pish is X
 * tegj is L
 * glob glob Silver is 34 Credits
 * glob prok Gold is 57800 Credits
 * pish pish Iron is 3910 Credits
 * how much is pish tegj glob glob ?
 * how many Credits is glob prok Silver ?
 * how many Credits is glob prok Gold ?
 * how many Credits is glob prok Iron ?
 * how much wood could a woodchuck chuck if a woodchuck could chuck wood ?
 * <p>
 * Test Output:
 * pish tegj glob glob is 42
 * glob prok Silver is 68 Credits
 * glob prok Gold is 57800 Credits
 * glob prok Iron is 782 Credits
 * I have no idea what you are talking about
 * <p>
 * Regards,
 * Xiaofei Tian
 * ThoughtWorks Recruiting
 * <p>
 * Copyright 2012 ThoughtWorks, Inc
 * <p>
 * <p>
 * Please submit here:
 * https://app.greenhouse.io/tests/246e35ee5bdfe001514b4916732ce6fe
 */
public class MerchantsGuideToTheGalaxy {


    private static final String SPACES = " ";
    private static final String IS_REGEX = "^([a-zA-Z0-9_]+) is ([i|v|x|l|c|d|m|I|V|X|L|C|D|M])$";
    private static final String METALS_REGEX = "^([a-zA-Z0-9_ ]{1,}) is (\\d+) (Credits|credits)$";
    private static final String RESULT_REGEX = "^how (many Credits|much|many credits) is ([a-zA-Z0-9_ ]{1,}) \\?$";

    private static final String NUMBER_REGEX = "^[1-9]\\d*$";
    private static final String NO_METAL = "NO_M";
    private Map<String, Double> metalsMap = new HashMap<>();
    private Map<String, String> keyMap = new HashMap<>();
    private Map<String, Integer> words = new HashMap<>();
    private Map<String, String> calcMetal = new HashMap<>();
    private Map<String, List<String>> resultMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(" Merchant's Guide to the Galaxy");
        MerchantsGuideToTheGalaxy merchantsGuideToTheGalaxy = new MerchantsGuideToTheGalaxy();
        try {
            merchantsGuideToTheGalaxy.init();
            merchantsGuideToTheGalaxy.scanner();
            merchantsGuideToTheGalaxy.getWhatIs();
            merchantsGuideToTheGalaxy.getMetals();
            merchantsGuideToTheGalaxy.getHowMuch();
            merchantsGuideToTheGalaxy.getCalcMetals();
            merchantsGuideToTheGalaxy.showResults();

        } catch (Exception e) {
            System.out.println("Sorry there si something Wrong in your input");
        }

    }


    private void showResults() {
        resultMap.forEach((k, v) -> {
            String answer = k.replace("how many Credits is ", "")
                    .replace("how much is ", "")
                    .replace("how many credits is ", "");
            String words;
            if (v != null && v.size() == 3) {
                answer = answer.replace("?", "");
                String key = v.get(0);
                if (key.equals(NO_METAL)) {
                    answer = answer + romanToInt(v.get(1)) + " Credits";
                } else {
                    answer = answer + (int) (metalsMap.get(key) * romanToInt(v.get(1))) + " Credits";
                }
                words = v.get(2);
            } else {
                answer = v.get(0);
                words = v.get(1);
            }
            System.out.println("");
            System.out.println(words);
            System.out.println(answer);
        });

    }

    private void getHowMuch() {
        words.forEach((word, stat) -> {
            StringBuffer results = new StringBuffer();
            String key = NO_METAL;
            if (Pattern.matches(RESULT_REGEX, word)) {
                String[] split = word.split(SPACES);
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    if (keyMap.values().contains(s)
                            && !keyMap.keySet().contains(s)) {
                        results.append(getShortKey(s));
                    } else if (metalsMap.keySet().contains(s)) {
                        key = s;
                        break;
                    }
                }
                if (results != null) {
                    resultMap.put(word, new ArrayList<>(Arrays.asList(key, results.toString(), word)));
                    words.put(word, 1);
                }
            }
        });
        words.forEach((word, stat) -> {
            if ((stat == 0)) {
                resultMap.put(word, new ArrayList<>(Arrays.asList("I have no idea what you are talking about", word)));
            }
        });
    }

    private void getCalcMetals() throws Exception {
        calcMetal.forEach((k, v) -> {
            if (v != null) {
                int totalValue = romanToInt(v);
                String newWord = intToRoman(totalValue);
                if (newWord.equals(v)) {
                    metalsMap.put(k, metalsMap.get(k) / totalValue);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        });
    }


    private void getMetals() throws UnsupportedOperationException {
        words.forEach((word, stat) -> {
            StringBuffer results = new StringBuffer();
            String key = null;
            Double value = null;
            if (Pattern.matches(METALS_REGEX, word)) {
                String[] split = word.split(SPACES);
                boolean over = false;
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    if (keyMap.values().contains(s)
                            && !keyMap.keySet().contains(s)) {
                        results.append(getShortKey(s));
                    } else if (i + 1 <= (split.length - 1) && split[i + 1].equals("is")) {
                        key = s;
                    } else if (Pattern.matches(NUMBER_REGEX, s)) {
                        value = new Double(s);
                        over = true;
                    } else if (s.equals("is")) {
                        continue;
                    } else if (over) {
                        break;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
            if (results != null && key != null && value != null) {
                calcMetal.put(key, results.toString());
                metalsMap.put(key, value);
                words.put(word, 1);
            }
        });
    }

    private String getShortKey(String s) {
        for (Map.Entry<String, String> stringStringEntry : keyMap.entrySet()) {
            if (stringStringEntry.getValue().equals(s)) {
                return stringStringEntry.getKey();
            }
        }
        throw new UnsupportedOperationException();
    }

    private void getWhatIs() {
        words.forEach((word, stat) -> {
            if (Pattern.matches(IS_REGEX, word)) {
                String[] split = word.split(SPACES);
                keyMap.put(split[2], split[0]);
                words.put(word, 1);
            }
        });
    }

    private void scanner() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String read = scanner.nextLine().trim();
//            if (read.equals("")) {
//                break;
//            }
//            words.put(read,0);
//        }
        try {
            String path = MerchantsGuideToTheGalaxy.class.getClassLoader().getResource("input.txt").getPath();
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(path));
            BufferedReader bufferedReader = new BufferedReader(read);
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                words.put(word, 0);
                System.out.println(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        keyMap.put("I", "I");
        keyMap.put("V", "V");
        keyMap.put("X", "X");
        keyMap.put("L", "L");
        keyMap.put("C", "C");
        keyMap.put("D", "D");
        keyMap.put("M", "M");
    }

    private String intToRoman(int number) {
        int[] base = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] str = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX",
                "V", "IV", "I"};
        String roman = "";
        int i = 0;
        while (number != 0) {
            if (number >= base[i]) {
                number -= base[i];
                roman += str[i];
            } else {
                i++;
            }
        }
        return roman;
    }

    public int romanToInt(String s) {
        if (s.length() < 1)
            return 0;
        int result = 0;
        int sub = getRomanValue(s.charAt(0));
        int last = sub;
        for (int i = 1; i < s.length(); ++i) {
            char curc = s.charAt(i);
            int curv = getRomanValue(curc);
            if (curv == last)
                sub += curv;
            else if (curv < last) {
                result += sub;
                sub = curv;
            } else {
                sub = curv - sub;
            }
            last = curv;
        }
        result += sub;
        return result;
    }

    public int getRomanValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
