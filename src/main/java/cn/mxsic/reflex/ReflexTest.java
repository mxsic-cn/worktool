package cn.mxsic.reflex;

import cn.mxsic.xml.mysic.Person;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-27 20:02:00
 */
public class ReflexTest {
    public static String getStringAbel(List<?> o, Class<?> c, String field) {
        StringBuffer result = new StringBuffer();
        if (StringUtils.isNoneBlank(field)) {
            Field[] fields = c.getDeclaredFields();
            int pos;
            for (pos = 0; pos < fields.length; pos++) {
                if (field.equals(fields[pos].getName())) {
                    break;
                }
            }
            for (Object o1 : o) {
                try {
                    fields[pos].setAccessible(true);
                    result.append(fields[pos].get(o1) + ",");
                } catch (Exception e) {
                    System.out.println("error--------" + "Reason is:" + e.getMessage());
                }
            }
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }
    //调用
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setPerName("sdflsejlasdf");
        personList.add(person);

        Person person1 = new Person();
        person1.setPerName("sdflsejlasdf");
        personList.add(person1);

        System.out.println(getStringAbel(personList, Person.class, "perName"));
    }
}
