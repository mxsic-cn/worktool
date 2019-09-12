package cn.mxsic.xml.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.util.List;

/**
 * Created by siqishangshu on 17/10/13.
 */
@Data
@XStreamAlias("")
public class Product {

    @XStreamAlias("name")
    private List list;

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name ;
}