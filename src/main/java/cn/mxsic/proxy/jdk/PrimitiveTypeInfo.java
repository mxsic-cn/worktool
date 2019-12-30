package cn.mxsic.proxy.jdk;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Function: PrimitiveTypeInfo <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:11:00
 */
public class PrimitiveTypeInfo {
        public String baseTypeString;
        public String wrapperClassName;
        public String wrapperValueOfDesc;
        public String unwrapMethodName;
        public String unwrapMethodDesc;


        private static Map<Class<?>, PrimitiveTypeInfo> table = new HashMap<>();

        public PrimitiveTypeInfo(Class<?> clazz, Class<?> clazz1) {
            assert clazz.isPrimitive();

            this.baseTypeString = Array.newInstance(clazz, 0).getClass().getName().substring(1);
            this.wrapperClassName = ProxyUtil.dotToSlash(clazz1.getName());
            this.wrapperValueOfDesc = "(" + this.baseTypeString + ")L" + this.wrapperClassName + ";";
            this.unwrapMethodName = clazz.getName() + "Value";
            this.unwrapMethodDesc = "()" + this.baseTypeString;

        }


        private static void add(Class<?> clazz, Class<?> clazz1) {
            table.put(clazz, new PrimitiveTypeInfo(clazz, clazz1));
        }

        public static PrimitiveTypeInfo get(Class<?> parameterType) {
            return table.get(parameterType);
        }

        static {
            add(Byte.TYPE, Byte.class);
            add(Character.TYPE, Character.class);
            add(Double.TYPE, Double.class);
            add(Float.TYPE, Float.class);
            add(Integer.TYPE, Integer.class);
            add(Long.TYPE, Long.class);
            add(Short.TYPE, Short.class);
            add(Boolean.TYPE, Boolean.class);
        }

    }
