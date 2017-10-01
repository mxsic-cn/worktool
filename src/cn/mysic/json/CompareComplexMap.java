package cn.mysic.json;

import java.util.*;

/**
 * Created by lxu on 3/4/14.
 */
public class CompareComplexMap {
    public static SetRelation compareRegulationValue(RegulationValue v1, RegulationValue v2) {

        // value - value
        if (v1.getOp().equals(LibraryMap.keyword.inValue) && v2.getOp().equals(LibraryMap.keyword.inValue)) {
            if (v1.getOpValue().equals(v2.getOpValue())) {
                return SetRelation.EQUAL;
            }
            if (v1.getOpValue().equals("ANY")) {
                return SetRelation.LARGER;
            }
            if (v2.getOpValue().equals("ANY")) {
                return SetRelation.SMALLER;
            }
        }
        // value - set
        if (v1.getOp().equals(LibraryMap.keyword.inValue) && v2.getOp().equals(LibraryMap.keyword.inSet)) {
            if (v1.getOpValue().equals("ANY") && v2.getOpSet().contains("ANY")) {
                return SetRelation.EQUAL;
            }
            if (v2.getOpSet().size() == 1 && v2.getOpSet().contains(v1.getOpValue())) {
                return SetRelation.EQUAL;
            }
            if (v1.getOpValue().equals("ANY")) {
                return SetRelation.LARGER;
            }
            if (v2.getOpSet().contains("ANY") || v2.getOpSet().contains(v1.getOpValue())) {
                return SetRelation.SMALLER;
            }
        }
        // value - range
        if (v1.getOp().equals(LibraryMap.keyword.inValue) && v2.getOp().equals(LibraryMap.keyword.inRange)) {
            if (v1.getOpValue().equals("ANY")) return SetRelation.LARGER;
            if (isNumeric(v1.getOpValue())) {
                int value1 = Integer.parseInt(v1.getOpValue());
                List<String> tmp = v2.getOpRange();
                int v20 = Integer.parseInt(tmp.get(0));
                int v21 = Integer.parseInt(tmp.get(1));
                if (v20 == value1 && v21 == value1) return SetRelation.EQUAL;
                if (v20 <= value1 && v21 >= value1) {
                    return SetRelation.SMALLER;
                }
            }
        }
        // set - value
        if (v1.getOp().equals(LibraryMap.keyword.inSet) && v2.getOp().equals(LibraryMap.keyword.inValue)) {
            if (v2.getOpValue().equals("ANY") && v1.getOpSet().contains("ANY")) {
                return SetRelation.EQUAL;
            }
            if (v1.getOpSet().size() == 1 && v1.getOpSet().contains(v2.getOpValue())) {
                return SetRelation.EQUAL;
            }
            if (v2.getOpValue().equals("ANY")) {
                return SetRelation.SMALLER;
            }
            if (v1.getOpSet().contains("ANY") || v1.getOpSet().contains(v2.getOpValue())) {
                return SetRelation.LARGER;
            }

        }
        // set - set
        if (v1.getOp().equals(LibraryMap.keyword.inSet) && v2.getOp().equals(LibraryMap.keyword.inSet)) {
            Set<String> tmp = new HashSet<>();
            tmp.addAll(v1.getOpSet());
            tmp.addAll(v2.getOpSet());
            if (tmp.size() == v1.getOpSet().size() && v1.getOpSet().size() == v2.getOpSet().size())
                return SetRelation.EQUAL;
            if (tmp.size() == v1.getOpSet().size()) return SetRelation.LARGER;
            if (tmp.size() == v2.getOpSet().size()) return SetRelation.SMALLER;
            if (tmp.size() < v1.getOpSet().size() + v2.getOpSet().size()) return SetRelation.OVERLAP;
        }
        // set - range
        if (v1.getOp().equals(LibraryMap.keyword.inSet) && v2.getOp().equals(LibraryMap.keyword.inRange)) {
            boolean allNum = true;
            List<String> tmp = v2.getOpRange();
            int v20 = Integer.parseInt(tmp.get(0));
            int v21 = Integer.parseInt(tmp.get(1));
            int rangeSize = v21 - v20 + 1;
            boolean outSider = false;
            Set<Integer> inSider = new HashSet<>();
            for (String oneItem : v1.getOpSet()) {
                if (!isNumeric(oneItem)) {
                    allNum = false;
                    break;
                } else {
                    int value1 = Integer.parseInt(oneItem);
                    if (value1 > v21 || value1 < v20) {
                        outSider = true;
                    } else {
                        inSider.add(value1);
                    }
                }
            }
            if (allNum) {
                if (rangeSize == inSider.size() && !outSider) return SetRelation.EQUAL;
                if (rangeSize > inSider.size() && !outSider) return SetRelation.SMALLER;
                if (rangeSize == inSider.size() && outSider) return SetRelation.LARGER;
                if (inSider.size() > 0 && outSider) return SetRelation.OVERLAP;
            }
        }
        // range - value
        if (v1.getOp().equals(LibraryMap.keyword.inRange) && v2.getOp().equals(LibraryMap.keyword.inValue)) {
            if (v2.getOpValue().equals("ANY")) return SetRelation.SMALLER;
            if (isNumeric(v2.getOpValue())) {
                int value2 = Integer.parseInt(v2.getOpValue());
                List<String> tmp = v1.getOpRange();
                int v10 = Integer.parseInt(tmp.get(0));
                int v11 = Integer.parseInt(tmp.get(1));

                if (v10 == value2 && v11 == value2) return SetRelation.EQUAL;
                if (v10 <= value2 && v11 >= value2) {
                    return SetRelation.LARGER;
                }
            }
        }
        // range - set
        if (v1.getOp().equals(LibraryMap.keyword.inRange) && v2.getOp().equals(LibraryMap.keyword.inSet)) {
            boolean allNum = true;
            List<String> tmp = v1.getOpRange();
            int v10 = Integer.parseInt(tmp.get(0));
            int v11 = Integer.parseInt(tmp.get(1));

            int rangeSize = v11 - v10 + 1;
            boolean outSider = false;
            Set<Integer> inSider = new HashSet<>();
            for (String oneItem : v2.getOpSet()) {
                if (!isNumeric(oneItem)) {
                    allNum = false;
                    break;
                } else {
                    int value1 = Integer.parseInt(oneItem);
                    if (value1 > v11 || value1 < v10) {
                        outSider = true;
                    } else {
                        inSider.add(value1);
                    }
                }
            }
            if (allNum) {
                if (rangeSize == inSider.size() && !outSider) return SetRelation.EQUAL;
                if (rangeSize > inSider.size() && !outSider) return SetRelation.LARGER;
                if (rangeSize == inSider.size() && outSider) return SetRelation.SMALLER;
                if (inSider.size() > 0 && outSider) return SetRelation.OVERLAP;
            }
        }
        // range -range
        if (v1.getOp().equals(LibraryMap.keyword.inRange) && v2.getOp().equals(LibraryMap.keyword.inRange)) {
            List<String> tmp1 = v1.getOpRange();
            List<String> tmp2 = v2.getOpRange();
            int v10 = Integer.parseInt(tmp1.get(0));
            int v11 = Integer.parseInt(tmp1.get(1));
            int v20 = Integer.parseInt(tmp2.get(0));
            int v21 = Integer.parseInt(tmp2.get(1));
            if (v10 == v20 && v11 == v21) return SetRelation.EQUAL;
            if (v10 <= v20 && v11 >= v21) return SetRelation.LARGER;
            if (v20 <= v10 && v21 >= v11) return SetRelation.SMALLER;
            if (v10 <= v20 && v11 <= v21 && v11 >= v20)
                return SetRelation.OVERLAP;
            if (v20 <= v10 && v21 <= v11 && v21 >= v10)
                return SetRelation.OVERLAP;
        }
        return SetRelation.DISJOINT;
    }

    private static boolean isNumeric(String input) {
        if (input.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
            return true;
        else
            return false;
    }


    // we only care about larger small and equal
    public static SetRelation compareTwoMap(Map<String, RegulationValue> mm1, Map<String, RegulationValue> mm2) {
        Map<String, RegulationValue> m1 = cleanMap(mm1);
        Map<String, RegulationValue> m2 = cleanMap(mm2);
        Set<String> key1 = m1.keySet();
        Set<String> key2 = m2.keySet();
        boolean m1Larger = false;
        boolean m2Larger = false;
        boolean m12Overlap = false;
        Set<String> allKey = new HashSet<>(key1);
        allKey.addAll(key2);
        // something unique for m1 and m2
        if (allKey.size() > key1.size() && allKey.size() > key2.size()) {
            return SetRelation.DISJOINT;
        }

        // since we only allow equal so far. The only relation is equal or disjoint
        // size smaller have a chance to be larger

        if (key1.size() < key2.size()) {
            m1Larger = true;
            for (String oneKey : key1) {
                SetRelation oneCompare = compareRegulationValue(m1.get(oneKey), m2.get(oneKey));
                if (oneCompare.equals(SetRelation.SMALLER)) m2Larger = true;
                if (oneCompare.equals(SetRelation.OVERLAP)) m12Overlap = true;
                if (oneCompare.equals(SetRelation.DISJOINT)) {
                    return SetRelation.DISJOINT;
                }
            }
            if (m1Larger && !m2Larger && !m12Overlap) {
                return SetRelation.LARGER;
            } else {
                return SetRelation.OVERLAP;
            }
        } else if (key1.size() > key2.size()) {
            m2Larger = true;
            for (String oneKey : key2) {
                SetRelation oneCompare = compareRegulationValue(m1.get(oneKey), m2.get(oneKey));
                if (oneCompare.equals(SetRelation.LARGER)) m1Larger = true;
                if (oneCompare.equals(SetRelation.OVERLAP)) m12Overlap = true;
                if (oneCompare.equals(SetRelation.DISJOINT)) {
                    return SetRelation.DISJOINT;
                }
            }
            if (!m1Larger && m2Larger  && !m12Overlap) {
                return SetRelation.SMALLER;
            } else {
                return SetRelation.OVERLAP;
            }

        } else {
            for (String oneKey : key2) {
                SetRelation oneCompare = compareRegulationValue(m1.get(oneKey), m2.get(oneKey));
                if (oneCompare.equals(SetRelation.LARGER)) m1Larger = true;
                if (oneCompare.equals(SetRelation.SMALLER)) m2Larger = true;
                if (oneCompare.equals(SetRelation.OVERLAP)) m12Overlap = true;
                if (oneCompare.equals(SetRelation.DISJOINT)) {
                    return SetRelation.DISJOINT;
                }
            }
            if (!m1Larger && m2Larger && !m12Overlap) return SetRelation.SMALLER;
            if (m1Larger && !m2Larger && !m12Overlap) return SetRelation.LARGER;
            if (!m1Larger && !m2Larger && !m12Overlap) return SetRelation.EQUAL;
            return SetRelation.OVERLAP;
        }
    }

    private static Map<String, RegulationValue> cleanMap(Map<String, RegulationValue> input) {
        Map<String, RegulationValue> result = new HashMap<>();
        for (String oneKey : input.keySet()) {
            if (!input.get(oneKey).getOp().equals(LibraryMap.keyword.inValue) || !input.get(oneKey).getOpValue().equals("ANY")) {
                result.put(oneKey, input.get(oneKey));
            }
        }
        return result;
    }

}
