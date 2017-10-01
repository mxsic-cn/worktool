package cn.mysic.json;

import adl.StringHelper;

import java.util.*;

/**
 * Created by lxu on 3/4/14.
 */
public class RegulationValue {
    private LibraryMap.keyword op;
    private final Set<String> opSet = new LinkedHashSet<>();
    private String opValue = "NA";
    private final List<String> opRange = new ArrayList<>();

    public RegulationValue(){
        this.op = LibraryMap.keyword.inValue;
    }

    public RegulationValue(LibraryMap.keyword op, String input) {
        this.op = op;
        if (op.equals(LibraryMap.keyword.inValue)){
            opValue = input;
        }
        if (op.equals(LibraryMap.keyword.inRange)){
            String [] valuePair = input.split("-");
            if (valuePair.length ==2){
                opRange.add(valuePair[0]);
                opRange.add(valuePair[1]);
            } else {
                String [] valuePair2 = input.split(",");
                if (valuePair2.length ==2){
                    opRange.add(valuePair2[0]);
                    opRange.add(valuePair2[1]);
                }
            }
        }
        if (op.equals(LibraryMap.keyword.inSet)){
            String [] valuePair = input.split(",");
            opSet.addAll(Arrays.asList(valuePair));
        }

    }

    public LibraryMap.keyword getOp() {
        return op;
    }

    public Set<String> getOpSet() {
        return opSet;
    }

    public String getOpValue() {
        return opValue;
    }

    public List<String> getOpRange() {
        return opRange;
    }

    public void setOp(LibraryMap.keyword op) {
        this.op = op;
    }

    public void setOpValue(String opValue) {
        this.opValue = opValue;
    }

    public String getStringValue(){
        String result = "";
        if (this.op.equals(LibraryMap.keyword.inValue))
            result = "{" +  this.opValue + "}";
        if (this.op.equals(LibraryMap.keyword.inSet)) {
            result = this.opSet.toString().replaceAll("\\[|\\]|\\s", "");
            result = "{" + result + "}";
        }
        if (this.op.equals(LibraryMap.keyword.inRange)) {
            result = this.opRange.toString().replaceAll("\\[|\\]|\\s", "");
            result = "[" + result + "]";
        }
        return result;
    }


    public String getPortStringValue(){
        String result = "";
        if (this.op.equals(LibraryMap.keyword.inValue))
            result = this.opValue;
        if (this.op.equals(LibraryMap.keyword.inSet)) {
            result = StringHelper.joinStringSet(this.opSet);
            result = "[" + result + "]";
        }
        if (this.op.equals(LibraryMap.keyword.inRange)) {
            result = StringHelper.joinStringList(this.opRange, ":");
            result = "[" + result + "]";
        }
        return result;
    }

}
