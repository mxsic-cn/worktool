package cn.mxsic.proxy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: ProxyUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:13:00
 */
public class ProxyUtil {

    public static String dotToSlash(String str) {
        return str.replace('.', '/');
    }

    public static void code_astore(int index, DataOutputStream dataWriter) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_ASTORE, ProxyConstant.OPC_ASTORE_0, dataWriter);
    }

    public static void code_aload(int index, DataOutputStream writer) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_ALOAD, ProxyConstant.OPC_ALOAD_0, writer);

    }

    public static void code_iload(int index, DataOutputStream dataWriter) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_ILOAD, ProxyConstant.OPC_ILOAD_0, dataWriter);
    }

    public static void code_fload(int index, DataOutputStream dataWriter) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_FLOAD, ProxyConstant.OPC_FLOAD_0, dataWriter);
    }

    public static void code_lload(int index, DataOutputStream dataWriter) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_LLOAD, ProxyConstant.OPC_LLOAD_0, dataWriter);
    }

    public static void code_dload(int index, DataOutputStream dataWriter) throws IOException {
        codeLocalLoadStore(index, ProxyConstant.OPC_DLOAD, ProxyConstant.OPC_DLOAD_0, dataWriter);
    }

    private static void codeLocalLoadStore(int index, int opc_load, int opc_load_0, DataOutputStream writer) throws IOException {
        /**
         * can't have to much methods
         */
        assert index >= 0 && index <= 65535;
        /**
         * equals hashCode toString
         */
        if (index <= 3) {
            writer.writeByte(opc_load_0 + index);
        } else if (index <= 255) {
            writer.writeByte(opc_load);
            writer.writeByte(index & 255);
        } else {
            writer.writeByte(ProxyConstant.OPC_WIDE);
            writer.writeByte(opc_load);
            writer.writeShort(index & '\uffff');
        }

    }

    public static void code_ldc(int index, DataOutputStream dataWriter) throws IOException {
        assert index >= 0 && index <= 65535;
        if (index <= 255) {
            dataWriter.writeByte(ProxyConstant.OPC_IDC);
            dataWriter.writeByte(index & 255);
        } else {
            dataWriter.writeByte(ProxyConstant.OPC_IDC_W);
            dataWriter.writeShort(index & '\uffff');
        }
    }

    public static void code_ipush(int index, DataOutputStream dataWriter) throws IOException {
        if (index >= -1 && index <= 5) {
            dataWriter.writeByte(ProxyConstant.OPC_ICONST_0 + index);
        } else if (index >= -128 && index <= 127) {
            dataWriter.writeByte(ProxyConstant.OPC_BIPUSH);
            dataWriter.writeByte(index & 255);
        } else {
            if (index < -32768 || index > 32767) {
                throw new AssertionError();
            }
            dataWriter.writeByte(ProxyConstant.OPC_SIPUSH);
            dataWriter.writeShort(index & '\uffff');
        }
    }

    public static void collectCompatibleTypes(Class<?>[] cla1, Class<?>[] cla2, ArrayList arr) {
        for (Class<?> c : cla1) {
            if (!arr.contains(c)) {
                for (Class<?> c1 : cla2) {
                    if (c1.isAssignableFrom(c)) {
                        arr.add(c);
                        break;
                    }
                }
            }
        }
    }

    public static String getParameterDescriptor(Class<?>[] parameterTypes) {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class parameterType : parameterTypes) {
            stringBuilder.append(getFieldType(parameterType));
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public static String getFieldType(Class<?> parameterType) {
        if (parameterType.isPrimitive()) {
            return PrimitiveTypeInfo.get(parameterType).baseTypeString;
        } else {
            return parameterType.isArray() ? parameterType.getName().replace('.', '/') : "L" + ProxyUtil.dotToSlash(parameterType.getName()) + ";";
        }
    }

    public static int getWordsPerType(Class<?> clazz) {
        return clazz != Long.TYPE && clazz != Double.TYPE ? 1 : 2;
    }

    public static String getMethodDescriptor(Class<?>[] parameterTypes, Class<?> returnType) {
        return getParameterDescriptor(parameterTypes) + (returnType == Void.TYPE ? "V" : getFieldType(returnType));
    }

    public static List computeUniqueCatchList(Class<?>[] exceptionTypes) {
        ArrayList list = new ArrayList();
        list.add(Error.class);
        list.add(RuntimeException.class);
        Class<?>[] classes = exceptionTypes;
        int len = exceptionTypes.length;
        label36:
        for (int i = 0; i < len; ++i) {
            Class clazz = classes[i];
            if (clazz.isAssignableFrom(Throwable.class)) {
                list.clear();
                break;
            }
            if (Throwable.class.isAssignableFrom(clazz)) {
                int index = 0;
                while (index < list.size()) {
                    Class clas = (Class) list.get(index);
                    if (clas.isAssignableFrom(clazz)) {
                        continue label36;
                    }
                    if (clazz.isAssignableFrom(clas)) {
                        list.remove(index);
                    } else {
                        ++index;
                    }
                }
                list.add(clazz);
            }
        }
        return list;
    }



}
