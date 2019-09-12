package cn.mxsic.xml.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Created by siqishangshu on 17/10/13.
 */
@Data
@XStreamAlias("row")
public class Atrribute {          //xml转化成bean时，该注解不起作用，主要用在json生成上
    private String key = Math.random()+"";
}
