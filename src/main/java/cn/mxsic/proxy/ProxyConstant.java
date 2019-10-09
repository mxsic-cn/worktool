package cn.mxsic.proxy;

/**
 * Function: ProxyConstant <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:17:00
 */
public class ProxyConstant {

    public static final int CLASSFILE_MINOR_VERSION = 0;
    public static final int CLASSFILE_MAJOR_VERSION = 49;

    public static final int CONSTANT_UTF8 = 1;
    public static final int CONSTANT_UNICODE = 2;

    public static final int CONSTANT_INTEGER = 3;
    public static final int CONSTANT_FLOAG = 4;
    public static final int CONSTANT_LONG = 5;
    public static final int CONSTANT_DOUBLE = 6;
    public static final int CONSTANT_CLASS = 7;
    public static final int CONSTANT_STRING = 8;
    public static final int CONSTANT_FIELD = 9;
    public static final int CONSTANT_METHOD = 10;
    public static final int CONSTANT_INTERFACEMETHOD = 11;
    public static final int CONSTANT_NAMEANDTYPE = 12;

    public static final int ACC_PUBLIC = 1;//Modifier.PUBLIC;//1
    public static final int ACC_PRIVATE = 2;//Modifier.PRIVATE;//2
    public static final int ACC_STATIC = 4;//Modifier.STATIC;//4
    public static final int ACC_FINAL = 8;//Modifier.FINAL;//8
    public static final int ACC_SUPER = 32;//Modifier.INTERFACE;//32

    public static final int OPC_ACONST_NULL = 1;
    public static final int OPC_ICONST_0 = 3;
    public static final int OPC_BIPUSH = 16;
    public static final int OPC_SIPUSH = 17;
    public static final int OPC_IDC = 18;
    public static final int OPC_IDC_W = 19;
    public static final int OPC_ILOAD = 21;
    public static final int OPC_LLOAD = 22;
    public static final int OPC_FLOAD = 23;
    public static final int OPC_DLOAD = 24;
    public static final int OPC_ALOAD = 25;
    public static final int OPC_ILOAD_0 = 26;
    public static final int OPC_LLOAD_0 = 30;
    public static final int OPC_FLOAD_0 = 34;
    public static final int OPC_DLOAD_0 = 38;
    public static final int OPC_ALOAD_0 = 42;
    public static final int OPC_ASTORE = 58;
    public static final int OPC_ASTORE_0 = 75;
    public static final int OPC_AASTORE = 83;

    public static final int OPC_POP = 87;
    public static final int OPC_DUP = 89;

    public static final int OPC_IRETUEN = 172;
    public static final int OPC_LRETURN = 173;
    public static final int OPC_FRETURN = 174;
    public static final int OPC_DRETURN = 175;
    public static final int OPC_ARETURN = 176;
    public static final int OPC_RETURN = 177;

    public static final int OPC_GETSTATIC = 178;
    public static final int OPC_PUTSTATIC = 179;
    public static final int OPC_GETFIELD = 180;
    public static final int OPC_INVOKEVIRTUAL = 182;
    public static final int OPC_INVOKESPECIAL = 183;
    public static final int OPC_INVOKESTATIC = 184;
    public static final int OPC_INVOKEINTERFACE = 185;

    public static final int OPC_NEW = 187;
    public static final int OPC_ANEWARRAY = 189;
    public static final int OPC_ATHROW = 191;
    public static final int OPC_CHECKCAST = 192;
    public static final int OPC_WIDE = 196;

    public static final String superclassName = "cn/mxsic/proxy/MProxy";
    //    public static final String superclassName = "java/lang/reflect/Proxy";
    public static final String handlerFieldName = "h";
    public static final boolean saveGeneratedFiles = Boolean.valueOf(System.getProperty("cn.mxsic.ProxyGenerator.saveGeneratedFiles"));
    public static final String CLASS_SUFFIX = ".class";
    public static final int ZERO = 0;
    public static final String EXCEPTIONS = "Exceptions";
    public static final String CODE = "Code";
}
