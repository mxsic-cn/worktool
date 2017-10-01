package cn.mysic.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.istuary.common.idl.RiskLevel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lxu
 * Date: 12/30/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserComplexRule implements Comparable<UserComplexRule> {
    private String ruleID;
    private String rID;
    private PolicyGroup group;
    private Map<String, RegulationValue> regulation;
    private String rawRegulation;
    private RuleAction action;
    private final Set<String> depolyedTo;
    private int numSupport;
    private boolean active = true;
    private RiskLevel riskLevel;
    private static Gson gson = new GsonBuilder().create();

    public UserComplexRule(String ruleID) {
        this.ruleID = ruleID;
        this.rID = "";
        this.group = new PolicyGroup();
        this.regulation = new LinkedHashMap<>();
        this.action = new RuleAction();
        this.depolyedTo = new HashSet<>();
        this.active = true;
    }

    public Pair<String, String> getSrcDest ()
    {
        PolicyGroup group =this.getGroup();
        if(null == group)
            return null;
        else
            return Pair.of(group.getSourceName(), group.getDestinationName());
    }


    public boolean getActive(){
        return this.active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public int getNumSupport() {
        return numSupport;
    }
    public void setNumSupport(int numSupport) {
        this.numSupport = numSupport;
    }

    public String getRuleID() {
        return ruleID;
    }
    public String getRID() {
        return rID;
    }

    public Set<String> getDepolyedTo() {
        return this.depolyedTo;
    }

    public void setDepolyedTo(Set<String> depolyedTo) {
        this.depolyedTo.addAll(depolyedTo);
    }

    public void addDepolyedTo(String oneIPS) {
        this.depolyedTo.add(oneIPS);
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }
    public void setRID(String rID) {
        this.rID = rID;
    }

    public Set<String> getSource() {
        return this.group.getSourceGroup();
    }

    public void setSource(Set<String> source) {
        this.group.setSourceGroup(source);
    }

    public String getSourceName() {
        return this.group.getSourceName();
    }

    public void setSourceName(String sourceName) {
        this.group.setSourceName(sourceName);
    }

    public String getDestinationName() {
        return this.group.getDestinationName();
    }

    public void setDestinationName(String destinationName) {
        this.group.setDestinationName(destinationName);
    }

    public Set<String> getDestination() {
        return this.group.getDestinationGroup();
    }

    public void setDestination(Set<String> destination) {
        this.group.setDestinationGroup(destination);
    }

    public String getRawRegulation() {
        return this.rawRegulation;
    }

    public void setRawRegulation(String rawRegulation) {
        this.rawRegulation = rawRegulation;
        this.regulation = gson.fromJson(rawRegulation, new TypeToken<Map<String, RegulationValue>>() {
        }.getType());
    }

    public PolicyGroup getGroup() {
        return this.group;
    }

    public void setGroup(PolicyGroup group) {
        this.group = group;
    }

    public Map<String, RegulationValue> getRegulation() {
        return regulation;
    }

    public void setRegulation(Map<String, RegulationValue> regulation) {
        Gson gson = new Gson();
        this.regulation = regulation;
        this.rawRegulation = gson.toJson(regulation);
//        this.rawRegulation = gson.toJson(regulation).replace("\"", "");
    }

    public RuleAction getAction() {
        return action;
    }

    public void setAction(RuleAction action) {
        this.action = action;
    }

    public RiskLevel getRiskLevel() { return riskLevel; }

    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
    //
//    @Override
//    public int compareTo(UserComplexRule r1){
//        return this.getUUID().compareTo(r1.getUUID());
//    }
//
//    @Override
//    public boolean equals(Object r1)
//    {
//        if (this.getUUID().equals(((UserComplexRule) r1).getUUID()))
//            return true;
//        else
//            return false;
//    }

    @Override
    public int compareTo(UserComplexRule r1){
        return this.hashCode() - (r1.hashCode());
    }

    @Override
    public boolean equals(Object r1)
    {
        if (this.hashCode() == (((UserComplexRule) r1).hashCode()))
            return true;
        else
            return false;
    }


    public UUID getUUID(){
        Map<String,String> information = new TreeMap<>();
        information.put("source", this.group.getSourceName());
        information.put("destination", this.group.getDestinationName());

        for (String oneField : this.regulation.keySet()){
            RegulationValue oneValue = this.regulation.get(oneField);
            if (oneValue.getOp().equals(LibraryMap.keyword.inValue)){
                information.put(oneField, oneValue.getStringValue());
            }
            if (oneValue.getOp().equals(LibraryMap.keyword.inSet)){
                information.put(oneField, oneValue.getOpSet().toString());
            }
            if (oneValue.getOp().equals(LibraryMap.keyword.inRange)){
                information.put(oneField, oneValue.getOpRange().toString());
            }
        }

        return UUID.nameUUIDFromBytes(information.toString().getBytes());
    }

    @Override
    public int hashCode(){
        Map<String,String> information = new TreeMap<>();
        information.put("source", this.group.getSourceName());
        information.put("destination", this.group.getDestinationName());

        for (String oneField : this.regulation.keySet()){
            RegulationValue oneValue = this.regulation.get(oneField);
            if (oneValue.getOp().equals(LibraryMap.keyword.inValue)){
                information.put(oneField, oneValue.getStringValue());
            }
            if (oneValue.getOp().equals(LibraryMap.keyword.inSet)){
                information.put(oneField, oneValue.getOpSet().toString());
            }
            if (oneValue.getOp().equals(LibraryMap.keyword.inRange)){
                information.put(oneField, oneValue.getOpRange().toString());
            }
        }

        return information.toString().hashCode();
    }

    public RuleRelationType compareRuleActionAlert(UserComplexRule r2) {
        SetRelation groupRelation = FindSetRelation.comparePolicyGroup(this.getGroup(), r2.getGroup());
        boolean actionCompare = this.getAction().checkAction(r2.getAction());
        SetRelation actionRelation = SetRelation.DISJOINT;
        if (actionCompare) {
            actionRelation = SetRelation.EQUAL;
        }
        SetRelation regulationRelation = CompareComplexMap.compareTwoMap(this.getRegulation(), r2.getRegulation());

        SetRelation ruleRelation = SetRelation.OVERLAP;
        if (groupRelation.equals(SetRelation.DISJOINT) || regulationRelation.equals(SetRelation.DISJOINT)) {
            if (ruleRelation.equals(SetRelation.OVERLAP))
                ruleRelation = SetRelation.DISJOINT;
        }

        if (groupRelation.equals(SetRelation.EQUAL) && regulationRelation.equals(SetRelation.EQUAL)) {
            if (ruleRelation.equals(SetRelation.OVERLAP))
                ruleRelation = SetRelation.EQUAL;
        }

        if ((groupRelation.equals(SetRelation.EQUAL) || groupRelation.equals(SetRelation.LARGER)) &&
                (regulationRelation.equals(SetRelation.EQUAL) || regulationRelation.equals(SetRelation.LARGER))) {
            if (ruleRelation.equals(SetRelation.OVERLAP))
                ruleRelation = SetRelation.LARGER;
        }

        if ((groupRelation.equals(SetRelation.EQUAL) || groupRelation.equals(SetRelation.SMALLER)) &&
                (regulationRelation.equals(SetRelation.EQUAL) || regulationRelation.equals(SetRelation.SMALLER))) {
            if (ruleRelation.equals(SetRelation.OVERLAP))
                ruleRelation = SetRelation.SMALLER;
        }

        if (actionRelation.equals(SetRelation.DISJOINT)) {
            if (ruleRelation.equals(SetRelation.EQUAL))
                return RuleRelationType.C_E;
            if (ruleRelation.equals(SetRelation.LARGER))
                return RuleRelationType.C_L;
            if (ruleRelation.equals(SetRelation.SMALLER))
                return RuleRelationType.C_S;
            if (ruleRelation.equals(SetRelation.OVERLAP))
                return RuleRelationType.C_O;
        }

        return RuleRelationType.DISJOINT;
    }
}
