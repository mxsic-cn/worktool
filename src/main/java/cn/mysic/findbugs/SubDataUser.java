package cn.mysic.findbugs;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-06-01 13:52:00
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class doesn't override equals in superclass
 This class extends a class that defines an equals method and adds fields,
 but doesn't define an equals method itself. Thus,
 equality on instances of this class will ignore the identity of the subclass and the added fields.
 Be sure this is what is intended, and that you don't need to override the equals method.
 Even if you don't need to override the equals method,
 consider overriding it anyway to document the fact that the equals method for the subclass just return the result of
 invoking super.equals(o).
 */
public class SubDataUser extends DataUser  implements Serializable {
    private String subName;
    private List<Object> areas = new ArrayList<>();
    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubName() {
        return subName;
    }

    /**
     * Class defines equals() and uses Object.hashCode()
     This class overrides equals(Object), but does not override hashCode(), and inherits the implementation of hashCode() from java.lang.Object (which returns the identity hash code, an arbitrary value assigned to the object by the VM).  Therefore, the class is very likely to violate the invariant that equal objects must have equal hashcodes.
     If you don't think instances of this class will ever be inserted into a HashMap/HashTable, the recommended hashCode implementation to use is:
     public int hashCode() {
     assert false : "hashCode not designed";
     return 42; // any arbitrary constant will do
     }
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


}
