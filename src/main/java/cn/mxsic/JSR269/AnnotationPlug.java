package cn.mxsic.JSR269;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function: AnnotationPlugger <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-11-19 16:08:00
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AnnotationPlug {

    String value();
}
