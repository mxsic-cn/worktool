package cn.mysic.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * Created by lxu on 1/29/14.
 */
public class LibraryMap {
    private static final String stringMap = "{" +
            "protocol:protocol," +
            "modbus:modbus," +
            "icmp:icmp," +
            "ip:ip," +
            "tcp:tcp," +
            "udp:udp," +
            "all:all," +
            "opcda:opcda," +
            "profinetio:profinetio," +
            "blocktype:blocktype," +
            "opc:opc," +
            "func:func," +
            "support:support," +
            "uuid:uuid," +
            "opnum:opnum," +
            "asdu_type:asdu_type," +
            "causetx_type:causetx_type," +
            "OTHER:OTHER," +
            "ANY:ANY," +
            "any:any," +
            "modbusTemplate:modbusTemplate," +
            "unseen:*," +
            "dnp3:dnp3," +
            "mms:mms," +
            "pduType:pduType," +
            "ServiceRequest:ServiceRequest," +
            "unknownNode:unknownNode," +
            "nonstandard:\"\"," +
//            "nonstandard:__Nonstandard," +
            "iec104:iec104" +
            "}";

    private static final String integerMap = "{" +
            "default3:999997," +
            "default2:999998," +
            "default1:999999," +
            "randMin:400000," +
            "randMax:500000," +
            "lineMax:100000" +
            "}";

    private static final String usedFieldMap = "{" +
            "modbus:[protocol,func]," +
            "dnp3:[protocol,func]," +
            "iec104:[protocol,asdu_type,causetx_type]," +
            "opcda:[protocol,uuid,opnum]" +
            "}";

    public static enum keyword {
        protocol, modbus, nonstandard, ip, udp, tcp, icmp, all, support, opc, dnp3, opcda, func, uuid, opnum, asdu_type, causetx_type,
        iec104, OTHER, ANY, modbusTemplate, any, opcdaHack, default3, default2, default1, randMin, randMax,
        lineMax, unseen, unknownNode, inRange, inSet, inValue, mms, ServiceRequest, pduType, blocktype, profinetio
    }

    public static final Map<keyword, String> myLib = buildLib();
    public static final Map<keyword, Integer> myIntLib = buildIntLib();
    public static Map<String, List<String>> myUsedFieldLib = buildUsedFieldMap();

    public static final List<String> defaultProtocol = Arrays.asList(myLib.get(keyword.all),
            myLib.get(keyword.ip), myLib.get(keyword.udp), myLib.get(keyword.support),
            myLib.get(keyword.icmp), myLib.get(keyword.tcp),myLib.get(keyword.unknownNode));

    public static final List<String> induProtocol = Arrays.asList(myLib.get(keyword.modbus),
            myLib.get(keyword.mms), myLib.get(keyword.iec104), myLib.get(keyword.opcda), myLib.get(keyword.profinetio),
            myLib.get(keyword.dnp3));

    private static Map<keyword, String> buildLib() {
        Gson gson = new Gson();
        return gson.fromJson(stringMap, new TypeToken<Map<keyword, String>>() {
        }.getType());
    }

    private static Map<keyword, Integer> buildIntLib(){
        Gson gson = new Gson();
        return gson.fromJson(integerMap, new TypeToken<Map<keyword, Integer>>() {
        }.getType());
    }

    private static Map<String, List<String>> buildUsedFieldMap(){
        Gson gson = new Gson();
        Map<keyword, List<keyword>> tmpResult = gson.fromJson(usedFieldMap, new TypeToken<Map<keyword, List<keyword>>>() {
        }.getType());
        Map<String,List<String>> result = new HashMap<>();
        for (keyword oneKey : tmpResult.keySet()){
            List<String> tmpString = new ArrayList<>();
            for (keyword another : tmpResult.get(oneKey)){
                tmpString.add(myLib.get(another));
            }
            result.put(myLib.get(oneKey),tmpString);
        }
        return result;
    }
}
