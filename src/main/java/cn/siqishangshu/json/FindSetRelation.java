package cn.siqishangshu.json;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lxu
 * Date: 12/30/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class FindSetRelation {
    /**
     * Find relation between two set of endpoints
     * @param input1 : endpoint set 1
     * @param input2 : endpoint set 2
     * @return relation
     */
    public static SetRelation compareSet(Set<String> input1, Set<String> input2) {
        Set tmpSet = new HashSet<>();
        if (input1.contains("ANY")) {
            if (input2.contains("ANY"))
                return SetRelation.EQUAL;
            if (input2.contains("OTHER"))
                return SetRelation.SMALLER;
            return SetRelation.LARGER;
        }
        if (input2.contains("ANY")) {
            if (input1.contains("ANY"))
                return SetRelation.EQUAL;
            if (input1.contains("OTHER"))
                return SetRelation.LARGER;
            return SetRelation.SMALLER;
        }
        if (input1.contains("OTHER")) {
            if (input2.contains("OTHER"))
                return SetRelation.EQUAL;
            return SetRelation.LARGER;

        }
        if (input2.contains("OTHER")) {
            if (input1.contains("OTHER"))
                return SetRelation.EQUAL;
            return SetRelation.SMALLER;
        }
        tmpSet.addAll(input1);
        tmpSet.addAll(input2);
        int size1 = input1.size();
        int size2 = input2.size();
        int sizeCombin = tmpSet.size();
        if (sizeCombin == (size1 + size2)) {
            return SetRelation.DISJOINT;
        }
        if (sizeCombin == size1) {
            if (size1 == size2) {
                return SetRelation.EQUAL;
            }
            return SetRelation.LARGER;
        }

        if (sizeCombin == size2) {
            if (size1 == size2) {
                return SetRelation.EQUAL;
            }
            return SetRelation.SMALLER;
        }

        return SetRelation.OVERLAP;
    }

    /**
     * Compare two groups (source and destination)
     * @param input1 : group 1
     * @param input2 : group 2
     * @return relation
     */
    public static SetRelation comparePolicyGroup(PolicyGroup input1, PolicyGroup input2) {
        SetRelation sRelation = compareSet(input1.getSourceGroup(), input2.getSourceGroup());
        SetRelation dRelation = compareSet(input1.getDestinationGroup(), input2.getDestinationGroup());

//        System.out.println(input1.getDestinationGroup() + " " + input2.getDestinationGroup() + " " + dRelation );
//        System.out.println(input1.getSourceGroup() + " " + input2.getSourceGroup() + " " + sRelation );
        if (sRelation.equals(SetRelation.EQUAL) && dRelation.equals(SetRelation.EQUAL))
            return SetRelation.EQUAL;
        if ((sRelation.equals(SetRelation.EQUAL) || sRelation.equals(SetRelation.LARGER))
                && (dRelation.equals(SetRelation.EQUAL) || dRelation.equals(SetRelation.LARGER)))
            return SetRelation.LARGER;
        if ((sRelation.equals(SetRelation.EQUAL) || sRelation.equals(SetRelation.SMALLER))
                && (dRelation.equals(SetRelation.EQUAL) || dRelation.equals(SetRelation.SMALLER)))
            return SetRelation.SMALLER;
        if (sRelation.equals(SetRelation.DISJOINT) || dRelation.equals(SetRelation.DISJOINT))
            return SetRelation.DISJOINT;
        return SetRelation.OVERLAP;
    }

    /**
     * Find learnCommon elements in two sets
     * @param input1: set 1
     * @param input2: set 2
     * @return combine two sets into one
     * Note: Find learnCommon elements in two sets
     */
    public static Set<String> setAnd(Set<String> input1, Set<String> input2) {
        Set<String> result = new HashSet<>();

        if (input1.contains("OTHER")) {
            result.addAll(input2);
            return result;
        }
        if (input2.contains("OTHER")) {
            result.addAll(input1);
            return result;
        }
        if (input1.contains("ANY")) {
            result.addAll(input2);
            return result;
        }
        if (input2.contains("ANY")) {
            result.addAll(input1);
            return result;
        }
        result.addAll(input1);
        result.retainAll(input2);
        return result;
    }

    /**
     * combine two sets into one
     * @param input1: set 1
     * @param input2: set 2
     * @return combine two sets into one
     * Note: with ANY, special treatment should be applied
     */
    public static Set<String> setOr(Set<String> input1, Set<String> input2) {
        Set<String> result = new HashSet<>();

        if (input1.contains("OTHER")) {
            result.add("OTHER");
            return result;
        }
        if (input2.contains("OTHER")) {
            result.add("OTHER");
            return result;
        }
        if (input1.contains("ANY")) {
            result.add("ANY");
            return result;
        }
        if (input2.contains("ANY")) {
            result.add("ANY");
            return result;
        }
        result.addAll(input1);
        result.addAll(input2);
        return result;
    }
}
