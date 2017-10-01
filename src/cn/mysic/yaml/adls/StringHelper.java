package cn.mysic.yaml.adls;

import cn.mysic.json.*;
import com.google.common.net.InternetDomainName;
import com.istuary.common.idl.Action;
import com.istuary.common.idl.ResultDevice;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lxu on 1/6/14.
 */
public class StringHelper {
    private static final Map<LibraryMap.keyword, Integer> myIntLib = LibraryMap.myIntLib;
    public static SecureRandom rnd = new SecureRandom();
    private final static int domainIDMin = 200000;
    private final static int domainIDMax = 100000;
    private final static String ipSuff = "";

    public static HashMap<String,String> netMaskNumMap = new HashMap<>();

    static{
        netMaskNumMap.put("32","255.255.255.255");
        netMaskNumMap.put("31","255.255.255.254");
        netMaskNumMap.put("30","255.255.255.252");
        netMaskNumMap.put("29","255.255.255.248");
        netMaskNumMap.put("28","255.255.255.240");
        netMaskNumMap.put("27","255.255.255.224");
        netMaskNumMap.put("26","255.255.255.192");
        netMaskNumMap.put("25","255.255.255.128");
        netMaskNumMap.put("24","255.255.255.0");

        netMaskNumMap.put("23","255.255.254.0");
        netMaskNumMap.put("22","255.255.252.0");
        netMaskNumMap.put("21","255.255.248.0");
        netMaskNumMap.put("20","255.255.240.0");
        netMaskNumMap.put("19","255.255.224.0");
        netMaskNumMap.put("18","255.255.192.0");
        netMaskNumMap.put("17","255.255.128.0");
        netMaskNumMap.put("16","255.255.0.0");

        netMaskNumMap.put("15","255.254.0.0");
        netMaskNumMap.put("14","255.252.0.0");
        netMaskNumMap.put("13","255.248.0.0");
        netMaskNumMap.put("12","255.240.0.0");
        netMaskNumMap.put("11","255.224.0.0");
        netMaskNumMap.put("10","255.192.0.0");
        netMaskNumMap.put("9","255.128.0.0");
        netMaskNumMap.put("8","255.0.0.0");

        netMaskNumMap.put("7","254.0.0.0");
        netMaskNumMap.put("6","252.0.0.0");
        netMaskNumMap.put("5","248.0.0.0");
        netMaskNumMap.put("4","240.0.0.0");
        netMaskNumMap.put("3","224.0.0.0");
        netMaskNumMap.put("2","192.0.0.0");
        netMaskNumMap.put("1","128.0.0.0");
        netMaskNumMap.put("0","0.0.0.0");

    }

    /**
     * combine a set of string in the format of 'a', 'b', 'c'
     *
     * @param input : string set
     * @return combined string
     */

    public static String setToString(Set<String> input) {
        String result = "";
        int index = 0;
        for (String oneString : input) {
            if (index > 0) {
                result = result + ", '" + oneString + "'";
            } else {
                result = result + "'" + oneString + "'";
            }
            index++;
        }
        return result;
    }

    public static String getDeviceBoxID(String input){
        return input.split("-")[0];
    }

    public static void switchDirection(Map<String, String> ruleTemplateInput, Set<Integer> allID){
        String sip = ruleTemplateInput.get("SOURCE_IP");
        String dip = ruleTemplateInput.get("DESTINATION_IP");
        String sport = ruleTemplateInput.get("SOURCE_PORT");
        String dport = ruleTemplateInput.get("DESTINATION_PORT");

        ruleTemplateInput.put("SOURCE_IP", dip);
        ruleTemplateInput.put("SOURCE_PORT", dport);
        ruleTemplateInput.put("DESTINATION_IP", sip);
        ruleTemplateInput.put("DESTINATION_PORT", sport);
        ruleTemplateInput.put("RULE_ID", Integer.toString(generateRandom(rnd, allID)));
    }

    /**
     * Assign string with values based on map
     *
     * @param text   : original string (with special keys)
     * @param params : map keys with values
     * @return new string
     */
    public static String replaceAll(String text, Map<String, String> params) {
        return replaceAll(text, params, '&', '&');
    }


    /**
     * Actual implementation of above code
     *
     * @param text     : original string (with special keys)
     * @param params   : map keys with values
     * @param leading  : start sign for matching key in original string
     * @param trailing : end sign for matching key in original string
     * @return new string
     */

    private static String replaceAll(String text, Map<String, String> params,
                                     char leading, char trailing) {
        String pattern = "";
        if (leading != 0) {
            pattern += leading;
        }
        pattern += "(\\w+)";
        if (trailing != 0) {
            pattern += trailing;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        boolean result = m.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                String replacement = params.get(m.group(1));
                if (replacement == null) {
                    replacement = m.group();
                }
                m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
                result = m.find();
            } while (result);
            m.appendTail(sb);
            return sb.toString();
        }
        return text;
    }

    /**
     * map zone to string for DPI
     *
     * @param input : string set
     * @return new string
     * Note: {ANY} --> only return any
     * {a,b,b} return [a,b,c]
     * {a} return a
     */
    public static String setToStringDPI(Set<String> input) {
        String result;
        if (input.size() == 1) {
            String oneString = input.iterator().next();
            if (oneString.equals("ANY")) {
                return "any";
            }
            return oneString;

        }
        int index = 0;
        result = "[";
        for (String oneString : input) {
            if (index > 0) {
                result = result + "," + oneString;
            } else {
                result = result + oneString;
            }
            index++;
        }
        result = result + "]";
        return result;
    }

    /**
     * From IPS rule ID to original user ID
     *
     * @param input : new ID for each IPS
     * @return original ID from UI rule
     */
    public static String getID(String input) {
        String result[] = input.split("_");
        return result[0];
    }

    /**
     * For IPS/IDS, translate original action to real action
     *
     * @param aa   : original action
     * @param type : real action for IPS/IDS
     * @return dpi supported action
     */
    public static String getIPSAction(RuleAction aa, NodeType type) {
        if (type.equals(NodeType.IPS)) {
            if (aa.getAction().equals(ActionType.ALLOW) && aa.getReport().equals(ReportType.REPORT)) {
                return "alert";
            }
            if (aa.getAction().equals(ActionType.ALLOW) && aa.getReport().equals(ReportType.IGNORE)) {
                return "pass";
            }
            if (aa.getAction().equals(ActionType.DENY) && aa.getReport().equals(ReportType.REPORT)) {
                return "drop";
            }
            if (aa.getAction().equals(ActionType.DENY) && aa.getReport().equals(ReportType.IGNORE)) {
                return "sdrop";
            }
            if (aa.getAction().equals(ActionType.REJECT) && aa.getReport().equals(ReportType.REPORT)) {
                return "reject";
            }
            if (aa.getAction().equals(ActionType.REJECT) && aa.getReport().equals(ReportType.IGNORE)) {
                return "sreject";
            }
            if (aa.getAction().equals(ActionType.REJECTBOTH) && aa.getReport().equals(ReportType.REPORT)) {
                return "rejectboth";
            }
            if (aa.getAction().equals(ActionType.REJECTBOTH) && aa.getReport().equals(ReportType.IGNORE)) {
                return "srejectboth";
            }
        }
        if (type.equals(NodeType.IDS)) {
            if (aa.getAction().equals(ActionType.ALLOW) && aa.getReport().equals(ReportType.REPORT)) {
                return "alert";
            }
            if (aa.getAction().equals(ActionType.ALLOW) && aa.getReport().equals(ReportType.IGNORE)) {
                return "pass";
            }
            if (aa.getAction().equals(ActionType.DENY) && aa.getReport().equals(ReportType.REPORT)) {
                return "alert";
            }
            if (aa.getAction().equals(ActionType.DENY) && aa.getReport().equals(ReportType.IGNORE)) {
                return "pass";
            }
            if (aa.getAction().equals(ActionType.REJECT) && aa.getReport().equals(ReportType.IGNORE)) {
                return "pass";
            }
            if (aa.getAction().equals(ActionType.REJECT) && aa.getReport().equals(ReportType.REPORT)) {
                return "alert";
            }
            if (aa.getAction().equals(ActionType.REJECTBOTH) && aa.getReport().equals(ReportType.IGNORE)) {
                return "pass";
            }
            if (aa.getAction().equals(ActionType.REJECTBOTH) && aa.getReport().equals(ReportType.REPORT)) {
                return "alert";
            }
        }
        return "pass";
    }

    public static RuleAction translationActionFromUI(Action input) {
        RuleAction result = new RuleAction();
        switch (input) {
            case ALLOW:
                result.setAction(ActionType.ALLOW);
                result.setReport(ReportType.IGNORE);
                break;
            case ALERT:
                result.setAction(ActionType.ALLOW);
                result.setReport(ReportType.REPORT);
                break;
            case DENY:
                result.setAction(ActionType.DENY);
                result.setReport(ReportType.REPORT);
                break;
            case REJECTBOTH:
                result.setAction(ActionType.REJECTBOTH);
                result.setReport(ReportType.REPORT);
                break;
            default:
                System.out.println("Error, Action type not exist!");
                break;
        }
        return result;
    }

    /**
     * From UI action code to MW action
     *
     * @param input : UI action code (integer)
     * @return MW RuleAction
     */
    public static RuleAction translationActionFromUI(String input) {
        RuleAction result = new RuleAction();
        if (input.equals("1")) {
            result.setAction(ActionType.ALLOW);
            result.setReport(ReportType.IGNORE);
        }
        if (input.equals("2")) {
            result.setAction(ActionType.ALLOW);
            result.setReport(ReportType.REPORT);
        }
        if (input.equals("4")) {
            result.setAction(ActionType.REJECT);
            result.setReport(ReportType.REPORT);
        }
        if (input.equals("3")) {
            result.setAction(ActionType.DENY);
            result.setReport(ReportType.REPORT);
        }
        if (input.equals("6")) {
            result.setAction(ActionType.REJECT);
            result.setReport(ReportType.IGNORE);
        }
        if (input.equals("5")) {
            result.setAction(ActionType.DENY);
            result.setReport(ReportType.IGNORE);
        }
        return result;
    }


    /**
     * From MW action to UI action code
     *
     * @param input : MW RuleAction
     * @return UI action code (integer)
     */
    public static String translationActionToUI(RuleAction input) {
        String result = "1";
        if (input.getAction().equals(ActionType.ALLOW) && input.getReport().equals(ReportType.IGNORE)) {
            result = "1";
        }
        if (input.getAction().equals(ActionType.ALLOW) && input.getReport().equals(ReportType.REPORT)) {
            result = "2";
        }
        if (input.getAction().equals(ActionType.REJECT) && input.getReport().equals(ReportType.REPORT)) {
            result = "4";
        }
        if (input.getAction().equals(ActionType.DENY) && input.getReport().equals(ReportType.REPORT)) {
            result = "3";
        }
        if (input.getAction().equals(ActionType.REJECT) && input.getReport().equals(ReportType.IGNORE)) {
            result = "6";
        }
        if (input.getAction().equals(ActionType.DENY) && input.getReport().equals(ReportType.IGNORE)) {
            result = "5";
        }
        return result;
    }

    public static Action translationActionToIDL(RuleAction input) {
        Action result = Action.ALLOW;
        if (input.getAction().equals(ActionType.ALLOW) && input.getReport().equals(ReportType.IGNORE)) {
            result = Action.ALLOW;
        }
        if (input.getAction().equals(ActionType.ALLOW) && input.getReport().equals(ReportType.REPORT)) {
            result = Action.ALERT;
        }
        if (input.getAction().equals(ActionType.REJECT) && input.getReport().equals(ReportType.REPORT)) {
            result = Action.DENY;
        }
        if (input.getAction().equals(ActionType.DENY) && input.getReport().equals(ReportType.REPORT)) {
            result = Action.DENY;
        }
        if (input.getAction().equals(ActionType.REJECTBOTH) && input.getReport().equals(ReportType.REPORT)) {
            result = Action.REJECTBOTH;
        }
        /*if (input.getAction().equals(ActionType.REJECT) && input.getReport().equals(ReportType.IGNORE)) {
            result = Action.;
        }*/
//        if (input.getAction().equals(ActionType.DENY) && input.getReport().equals(ReportType.IGNORE)) {
//            result = Action.DENY;
//        }
        return result;
    }


    /**
     * Combine a string set with ","
     *
     * @param input : a set of string
     * @return join string set with ","
     */
    public static String joinStringSet(Set<String> input) {
        return String.join(",", input);

    }

    public static String joinStringList(List<String> input) {
        return String.join(",", input);
    }

    public static String joinStringList(List<String> input, String sep) {
        List<String> realInput = new ArrayList<>();
        for (String oneString : input){
            if (!checkEmpty(oneString)) realInput.add(oneString);
        }
        return String.join(sep, realInput);
    }

    public static String translateToMac(String inputMac, boolean dpi) {
        String newMac = "NA";
        if (inputMac != null && !inputMac.isEmpty() && !inputMac.equals("NA")) {
            String tmp = inputMac.replaceAll("-", "").replaceAll(":", "").toLowerCase();
            if (tmp.length() == 12) {
                if (dpi) {
                    newMac = tmp.replaceAll("(.{2})", "$1:").substring(0, 17);
                } else {
                    newMac = tmp;
                }
            }
        }
        return newMac;
    }

    public static String translateToMac(String inputMac) {
        String newMac = "NA";
        if (inputMac != null && !inputMac.isEmpty() && !inputMac.equals("NA")) {
            String PATTERN = "^(([A-Fa-f0-9]{2}){5}[A-Fa-f0-9]{2}[,]?)+$";
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(inputMac);
            if (matcher.matches()) {
                return inputMac.toLowerCase();
            }
        }
        return newMac;
    }

    public static boolean checkMAC(String inputMac) {
        if (inputMac != null && !inputMac.isEmpty() && !inputMac.equals("NA")) {
            String tmp = inputMac.replaceAll("-", "").replaceAll(":", "").
                    replaceAll("/", "").replaceAll("\\.", "").toLowerCase();
            if (tmp.length() == 12) {
                return true;
            }
        }
        return false;
    }

    public static List<String> readFile(String fileName) throws Exception {
        List<String> result = new ArrayList<>();
        try {
            InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);

            if (stream == null) {
                stream = new FileInputStream(fileName);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            try {
                String line = br.readLine();
                while (line != null && !line.isEmpty()) {
                    result.add(line);
                    line = br.readLine();
                }

            } finally {
                br.close();
                stream.close();
            }

        } catch (Exception e) {
            throw e;
        }
        return result;
    }


    public static boolean checkIPV4(String ip) {
//        String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        String PATTERN = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        if (matcher.matches()) {
            return true;
        } else {
            SubnetHelper sb = new SubnetHelper(ip);
            if (sb.isVoid)
                return true;
            else
                return false;
        }
    }

    public static boolean isIPOverLap(String first, String second) {
        if (first.equals(second)) return true;
        SubnetHelper sb1 = new SubnetHelper(first);
        SubnetHelper sb2 = new SubnetHelper(second);
        if (sb1.isVoid && sb2.isVoid) {
            if (sb1.checkOverlap(sb2))
                return true;
            else
                return false;
        }
        if (sb1.isVoid && !sb2.isVoid) {
            if (sb1.contains(second))
                return true;
            else
                return false;
        }
        if (!sb1.isVoid && sb2.isVoid) {
            if (sb2.contains(first))
                return true;
            else
                return false;
        }
        return false;
    }

    public static boolean isContainIPSubnet(String input, List<String> allInput) {
        for (String oneInput : allInput) {
            if (isIPOverLap(oneInput, input)) return true;
        }
        return false;
    }

    public static String noPorts(Set<String> openPorts){
        String result = joinStringSet(openPorts);
        return "![" + result + "]";

    }
    public static String joinIP(Set<String> openPorts){
        String result = joinStringSet(openPorts);
        return "[" + result + "]";

    }

    public static boolean checkMask(String mask) {
        return checkMask(mask, false);
    }

    public static boolean checkMask(String mask, boolean checkAsNum) {
        if(checkAsNum){
            try{
                int maskNum = Integer.parseInt(mask);
                if((maskNum < 0) || (maskNum > 32))
                    return false;
            } catch(Exception ex){
                return false;
            }

            return true;
        }
        else{
            if (!checkIPV4(mask))
                return false;
            String result = "";
            for (String onepart : mask.split("\\.")) {
                //ystem.out.println(onepart);
                result = result + String.format("%8s", Integer.toBinaryString(Integer.valueOf(onepart))).replace(' ', '0');
            }
            int counter = 0;
            for (int mm = 0; mm < result.length() - 1; mm++) {
                if (result.charAt(mm) != result.charAt(mm + 1)) {
                    counter++;
                }
            }
            if (result.charAt(0) == 0 || counter != 1)
                return false;
            else
                return true;
        }

    }

//    public static List<Map<String, String>> fieldsTranslation(List<Map<String, String>> input,
//                                                              Map<String, String> keyMap) {
//        List<Map<String, String>> result = new ArrayList<>();
//        for (Map<String, String> oneInput : input) {
//            Map<String, String> oneResult = new HashMap<>();
//            oneResult.put("field", oneInput.get("field"));
//            oneResult.put("value", oneInput.get("value"));
//            if (keyMap.get(oneInput.get("field")) != null && !keyMap.get(oneInput.get("field")).isEmpty()) {
//                oneResult.put("field", keyMap.get(oneInput.get("field")));
//            }
//            result.add(oneResult);
//        }
//        return result;
//    }

    public static void displayByteBufferList(List<ByteBuffer> bf) throws Exception {
        for (ByteBuffer oneItem : bf) {
            System.out.println(Charset.forName("UTF-8").newDecoder().decode(oneItem.asReadOnlyBuffer()));
        }
    }

    public static String getMD5(String input) {
        String hashText = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(input.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashText = bigInt.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
        } catch (Exception e) {
            System.out.println("Error, MD5 fail!");
        }
        return hashText;
    }

    public static int generateRandom(SecureRandom rnd, Set<Integer> allID) {
        int tmpID = myIntLib.get(LibraryMap.keyword.randMin) + rnd.nextInt(myIntLib.get(LibraryMap.keyword.randMax));
        while (allID.contains(tmpID)) {
            tmpID = myIntLib.get(LibraryMap.keyword.randMin) + rnd.nextInt(myIntLib.get(LibraryMap.keyword.randMax));
        }
        allID.add(tmpID);
        return tmpID;
    }

    public static int generateRandomDomainID(SecureRandom rnd, Set<Integer> allID) {
        int tmpID = domainIDMin + rnd.nextInt(domainIDMax);
        while (allID.contains(tmpID)) {
            tmpID = domainIDMin + rnd.nextInt(domainIDMax);
        }
        allID.add(tmpID);
        return tmpID;
    }

    public static boolean checkEmpty(String input) {
        if (input == null || input.equals("NA") || input.isEmpty())
            return true;
        else
            return false;
    }

    public static boolean isAlpha(String name) {
        if (checkEmpty(name)) return false;
        return name.matches("[A-Z]+");
    }

    public static boolean checkEmptyString(String input) {
        if (input == null || input.isEmpty() || input.equals("NA") || input.equals("null") || input.trim().isEmpty())
            return true;
        else
            return false;
    }

    public static List<SubnetHelper> findSubnet(Map<String, Set<String>> groups) {
        List<SubnetHelper> result = new ArrayList<>();
        Set<String> orginSubnetString = new HashSet<>();
        for (String oneKey : groups.keySet()) {
            for (String oneValue : groups.get(oneKey)) {
//                System.out.println(oneKey);
                if (!orginSubnetString.contains(oneValue)) {
                    SubnetHelper sb = new SubnetHelper(oneValue);
                    if (sb.isVoid) result.add(sb);
                    orginSubnetString.add(oneValue);
                }
            }
        }
        return result;
    }

    public static String replaceSubnet(String input, Map<String, String> MacIpMap, List<SubnetHelper> candidateSubnet) {
        String result = input;
        for (SubnetHelper oneSubnet : candidateSubnet) {
            if (oneSubnet.contains(input)) {
                result = oneSubnet.origin;
                break;
            }
        }
        if (null != MacIpMap.get(result)) {
            result = MacIpMap.get(result);
        }
        return result;
    }

    public static String findSubnetFromIP(String input, List<SubnetHelper> candidateSubnet) {
        String result = input;
        for (SubnetHelper oneSubnet : candidateSubnet) {
            if (oneSubnet.contains(input)) {
                result = oneSubnet.origin;
                break;
            }
        }
        return result+ipSuff;
    }

    public static String replaceSubnet(String input, Map<String, String> MacIpMap,
                                       List<SubnetHelper> candidateSubnet,
                                       Map<String, Set<String>> groups, Map<String, String> nodeZoneMap) {
        if (null != nodeZoneMap.get(input)) return nodeZoneMap.get(input);
        String result = "UNKNOWN";
        String realNode = input;
        for (SubnetHelper oneSubnet : candidateSubnet) {
            if (oneSubnet.contains(input)) {
                realNode = oneSubnet.origin;
                break;
            }
        }
        if (null != MacIpMap.get(input)) {
            realNode = MacIpMap.get(input);
        }
        for (String oneGroup : groups.keySet()) {
            if (groups.get(oneGroup).contains(realNode)) {
                result = oneGroup;
                break;
            }
        }
        nodeZoneMap.put(input, result);
        return result;
    }


    public static String subnetFormatter(String input) {
        SubnetHelper tmp = new SubnetHelper(input);
        if (tmp.isVoid) {
            return tmp.getCIDR();
        }
        return null;
    }

    public static void initRuleSourceTarget(UserComplexRule r1, Map<String, String> IpMacMap,
                                            Set<String> source, Set<String> destination, boolean isL2) {
        for (String oneSource : r1.getSource()) {
            if (isL2) {
                if (checkIPV4(oneSource)) {
                    if (null != IpMacMap.get(oneSource)) {
                        source.add(IpMacMap.get(oneSource));
                    }
                } else {
                    source.add(oneSource);
                }
            } else {
                if (checkIPV4(oneSource)) {
                    source.add(oneSource);
                } else {
                    if (oneSource.equals("ANY"))
                        source.add(oneSource);
                }
            }
        }

        for (String oneDestination : r1.getDestination()) {
            if (isL2) {
                if (checkIPV4(oneDestination)) {
                    if (null != IpMacMap.get(oneDestination)) {
                        destination.add(IpMacMap.get(oneDestination));
                    }
                } else {
                    destination.add(oneDestination);
                }
            } else {
                if (checkIPV4(oneDestination)) {
                    destination.add(oneDestination);
                } else {
                    if (oneDestination.equals("ANY"))
                        destination.add(oneDestination);
                }
            }
        }
    }

    public static String removeNewLine(String input) {
        return StringUtils.removeEnd(input, "\n");
    }

    public static <T> void sortIntLinkedHashMap(LinkedHashMap<T, Integer> input, boolean bigFirst) {
        List<Map.Entry<T, Integer>> entries =
                new ArrayList<>(input.entrySet());
        if (bigFirst)
            Collections.sort(entries, new Comparator<Map.Entry<T, Integer>>() {
                public int compare(Map.Entry<T, Integer> a, Map.Entry<T, Integer> b) {
                    return b.getValue().compareTo(a.getValue());
                }
            });
        else
            Collections.sort(entries, new Comparator<Map.Entry<T, Integer>>() {
                public int compare(Map.Entry<T, Integer> a, Map.Entry<T, Integer> b) {
                    return a.getValue().compareTo(b.getValue());
                }
            });
        input.clear();
        for (Map.Entry<T, Integer> entry : entries) {
            input.put(entry.getKey(), entry.getValue());
        }
    }


    public static <T> void sortDoubleLinkedHashMap(LinkedHashMap<T, Double> input, boolean bigFirst) {
        List<Map.Entry<T, Double>> entries =
                new ArrayList<>(input.entrySet());
        if (bigFirst)
            Collections.sort(entries, new Comparator<Map.Entry<T, Double>>() {
                public int compare(Map.Entry<T, Double> a, Map.Entry<T, Double> b) {
                    return b.getValue().compareTo(a.getValue());
                }
            });
        else
            Collections.sort(entries, new Comparator<Map.Entry<T, Double>>() {
                public int compare(Map.Entry<T, Double> a, Map.Entry<T, Double> b) {
                    return a.getValue().compareTo(b.getValue());
                }
            });
        input.clear();
        for (Map.Entry<T, Double> entry : entries) {
            input.put(entry.getKey(), entry.getValue());
        }
    }

    // domain name handle
    public static List<String> extractDomainName(String input){
        input = input.trim().split("/")[0].split(":")[0];
        InternetDomainName inputName = InternetDomainName.from(input);
        List<String> result = new LinkedList<>();
        if (inputName.isUnderPublicSuffix()) {
//            return inputName.topPrivateDomain().toString();
            List<String> tmpResult = inputName.topPrivateDomain().parts();
            if (!tmpResult.isEmpty()) {
                result.add(tmpResult.get(0));
                result.add(inputName.publicSuffix().toString());
            }
            return result;
        } else {
            return result;
        }
    }

    public static String extractPrivateDomain(String input){
        String result = "NA";
        InternetDomainName inputName;
        try {
            input = input.trim().split("/")[0].split(":")[0];
            inputName = InternetDomainName.from(input);
        } catch (Exception e) {
            return result;
        }

        if (inputName.isUnderPublicSuffix()) {
            result = inputName.topPrivateDomain().toString();
        }
        return result;
    }

    public static boolean containDomin(String oneDomin, String parDomin){
        InternetDomainName inputName1 = InternetDomainName.from(oneDomin);
        InternetDomainName inputName2 = InternetDomainName.from(parDomin);
        if (inputName1.isUnderPublicSuffix() && inputName2.isUnderPublicSuffix()) {
            if (oneDomin.endsWith(parDomin)) return true;
        }
        return false;
    }

    public static String ipInt2String(long ip){
        if (ip > 4294967295l || ip < 0) {
            throw new IllegalArgumentException("invalid ip");
        }
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            ipAddress.append((ip & (0xff << shift)) >> shift);
            if (i > 0) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }

    public static void updateIpMacMap(Map<String, String> ipMacMap, String ip, String mac){
        if (ipMacMap.containsKey(ip)){
           String oldMac = ipMacMap.get(ip);
           if (null != oldMac && !oldMac.equals(mac)){
               ipMacMap.put(ip, null);
           }
        } else {
            ipMacMap.put(ip, mac);
        }
    }

    //CS-7313 report IP address instead of MAC address if it is possible
    public static List<String> transalteMacToIp(Set<String> macAddress, Map<String, String> MacIpMap){
        Set<String> result = new HashSet<>();
        for (String oneAddress : macAddress){
            if (MacIpMap.containsKey(oneAddress)){
                result.add(MacIpMap.get(oneAddress));
            } else {
                result.add(oneAddress);
            }
        }
        List<String> listResult = new LinkedList<>();
        listResult.addAll(result);
        return listResult;
    }

    public static Set<String> getIPfromGroupName(String groupName){
        Set<String> result = new HashSet<>();
        result.add(groupName.split("___")[0]);
        return result;
    }

    public static String getIPStringfromGroupName(String groupName){
        return groupName.split("___")[0];
    }

    public static String getGroupStringfromIP(String ip){
        if (ip.equals("ANY")) {
            return ip;
        }
        return ip + ipSuff;
    }

    public static Set<ResultDevice> transalteMacToIpInResultDevice(Set<ResultDevice> nodes, Map<String, String> macIpMap){
        nodes.forEach(node->{
            if(StringHelper.checkEmpty(node.getIp())){
                if(macIpMap.containsKey(node.getMac()))
                    node.setIp(macIpMap.get(node.getMac()));
            }
        });
        return nodes;
    }
}
