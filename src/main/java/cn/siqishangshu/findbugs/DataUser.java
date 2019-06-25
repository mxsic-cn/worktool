package cn.siqishangshu.findbugs;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Function:  @Data  覆盖问题  get  SerialNoImportFileResultDto <br>
 *  Setter
 *  Getter
 *  E
 *  ToString
 *
 *  UrF: Unread field (URF_UNREAD_FIELD)
 This field is never read.  Consider removing it from the class.

 * @author: siqishangshu <br>
 * @date: 2018-06-01 10:49:00
 */

@Data
public class DataUser {
    private Integer size;
    private Integer age;
    private String name;
    private List list;
    private Date date;
    private String subName;

    public Integer getSize(){
        return list.size();
    }

    /**
     * May expose internal representation by returning reference to mutable object
     Returning a reference to a mutable object value stored in one of the object's fields exposes the internal
     representation of the object.  If instances are accessed by untrusted code, and unchecked changes to
     the mutable object would compromise security or other important properties, you will need to do something different.
     Returning a new copy of the object is better approach in many situations.
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * May expose internal representation by incorporating reference to mutable object
     This code stores a reference to an externally mutable object into the internal representation of the object. 
     If instances are accessed by untrusted code, and unchecked changes to the mutable object would
     compromise security or other important properties, you will need to do something different.
     Storing a copy of the object is better approach in many situations.
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
