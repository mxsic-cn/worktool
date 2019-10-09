package cn.mxsic.proxy;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Function: MProxyGenerator <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-09-23 14:42:00
 */
public class MProxyGenerator {


    private static Method hashCodeMethod;
    private static Method equalsMethod;
    private static Method toStringMethod;
    public String className;
    private Class<?>[] interfaces;
    private int accessFlags;
    public ConstantPool cp = new ConstantPool();
    private List<FieldInfo> fields = new ArrayList();
    private List<MethodInfo> methods = new ArrayList();
    private Map<String, List<ProxyMethod>> proxyMethods = new HashMap();
    public int proxyMethodCount = 0;

    static {
        try {
            hashCodeMethod = Object.class.getMethod("hashCode");
            equalsMethod = Object.class.getMethod("equals", Object.class);
            toStringMethod = Object.class.getMethod("toString");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    public MProxyGenerator(String proxyName, Class<?>[] interfaces, int accessFlags) {
        this.className = proxyName;
        this.interfaces = interfaces;
        this.accessFlags = accessFlags;
    }

    public static byte[] generateProxyClass(String proxyName, Class<?>[] interfaces) {
        return generateProxyClass(proxyName, interfaces, ProxyConstant.CLASSFILE_MAJOR_VERSION);
    }

    public static byte[] generateProxyClass(String proxyName, Class<?>[] interfaces, int accessFlags) {
        MProxyGenerator mProxyGenerator = new MProxyGenerator(proxyName, interfaces, accessFlags);
        final byte[] bytes = mProxyGenerator.generateClassFile();
        if (ProxyConstant.saveGeneratedFiles) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    int len = proxyName.lastIndexOf('.');
                    Path path;
                    try {

                        if (len > 0) {
                            Path tem = Paths.get(proxyName.substring(0, len).replace('.', '/'));
                            Files.createDirectories(tem);
                            path = tem.resolve(proxyName.substring(len + 1, proxyName.length()) + ProxyConstant.CLASS_SUFFIX);
                        } else {
                            path = Paths.get(proxyName + ProxyConstant.CLASS_SUFFIX);
                        }
                        Files.write(path, bytes, new OpenOption[0]);
                    } catch (IOException e) {
                        throw new InternalError("I/O exception saving generatoed file: " + bytes);
                    }
                    return null;

                }
            });
        }
        return bytes;
    }

    private byte[] generateClassFile() {
        this.addProxyMethod(hashCodeMethod, Object.class);
        this.addProxyMethod(equalsMethod, Object.class);
        this.addProxyMethod(toStringMethod, Object.class);
        for (Class<?> clazz : this.interfaces) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                this.addProxyMethod(method, clazz);
            }
        }
        Iterator iterator = this.proxyMethods.values().iterator();
        List<ProxyMethod> list;
        try {
            while (iterator.hasNext()) {
                list = (List<ProxyMethod>) iterator.next();
                checkReturnType(list);
            }
            Iterator it;
            this.methods.add(this.generateConstructor());
            iterator = this.proxyMethods.values().iterator();
            while (iterator.hasNext()) {
                list = (List<ProxyMethod>) iterator.next();
                it = list.iterator();
                while (it.hasNext()) {
                    ProxyMethod proxyMethod = (ProxyMethod) it.next();
                    this.fields.add(new FieldInfo(proxyMethod.methodFieldName, "Ljava/lang/reflect/Method;", ProxyConstant.CONSTANT_METHOD,this));
                    this.methods.add(proxyMethod.generateMethod());
                }
            }
            this.methods.add(this.generateStaticInitializer());

        } catch (IOException e) {
            throw new InternalError("unexpected I/O Exception ", e);
        }
        if (this.methods.size() > 65535) {
            throw new IllegalArgumentException("method limit exceeded");
        } else if (this.fields.size() > 65535) {
            throw new IllegalArgumentException("field limit exceeded");
        } else {
            this.cp.getClass(ProxyUtil.dotToSlash(this.className));
            this.cp.getClass(ProxyConstant.superclassName);
            for (Class<?> clazz : this.interfaces) {
                this.cp.getClass(ProxyUtil.dotToSlash(clazz.getName()));
            }
            this.cp.setReadOnly();
            ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
            DataOutputStream dataWriter = new DataOutputStream(byteWriter);
            try {
                /**
                 * todo why ?
                 */
                dataWriter.writeInt(-889275714);
                dataWriter.writeShort(ProxyConstant.CLASSFILE_MINOR_VERSION);
                dataWriter.writeShort(ProxyConstant.CLASSFILE_MAJOR_VERSION);
                this.cp.write(dataWriter);
                dataWriter.writeShort(this.accessFlags);
                dataWriter.writeShort(this.cp.getClass(ProxyUtil.dotToSlash(this.className)));
                dataWriter.writeShort(this.cp.getClass(ProxyConstant.superclassName));
                dataWriter.writeShort(this.interfaces.length);
                for (Class<?> anInterface : this.interfaces) {
                    dataWriter.writeShort(this.cp.getClass(ProxyUtil.dotToSlash(anInterface.getName())));
                }

                dataWriter.writeShort(this.fields.size());
                iterator = this.fields.iterator();
                while (iterator.hasNext()) {
                    FieldInfo fieldInfo = (FieldInfo) iterator.next();
                    fieldInfo.writer(dataWriter);
                }
                dataWriter.writeShort(this.methods.size());
                iterator = this.methods.iterator();
                while (iterator.hasNext()) {
                    MethodInfo methodInfo = (MethodInfo) iterator.next();
                    methodInfo.writer(dataWriter);
                }
                dataWriter.writeShort(ProxyConstant.CLASSFILE_MINOR_VERSION);
                return byteWriter.toByteArray();
            } catch (IOException e) {
                throw new InternalError("unexpected I/O Exception", e);
            }
        }
    }

    private MethodInfo generateStaticInitializer() throws IOException {
        MethodInfo methodInfo = new MethodInfo("<clinit>", "()V", ProxyConstant.ACC_FINAL,this);
        byte index = 1;
        byte startPc = 0;
        DataOutputStream dataWriter = new DataOutputStream(methodInfo.code);
        Iterator iterator = this.proxyMethods.values().iterator();
        while (iterator.hasNext()) {
            List<ProxyMethod> list = (List) iterator.next();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ProxyMethod proxyMethod = (ProxyMethod) it.next();
                proxyMethod.codeFieldInitialization(dataWriter);
            }
        }

        dataWriter.writeByte(ProxyConstant.OPC_RETURN);
        short handlerPc;
        short endPc = handlerPc = (short) methodInfo.code.size();
        methodInfo.exceptionTable.add(new ExceptionTableEntry(startPc, endPc, handlerPc, this.cp.getClass("java/lang/NoSuchMethodException")));
        ProxyUtil.code_astore(index, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_NEW);
        dataWriter.writeShort(this.cp.getClass("java/lang/NoSuchMethodError"));
        dataWriter.writeByte(ProxyConstant.OPC_DUP);
        ProxyUtil.code_aload(index, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_INVOKEVIRTUAL);
        dataWriter.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
        dataWriter.writeByte(ProxyConstant.OPC_INVOKESPECIAL);
        dataWriter.writeShort(this.cp.getMethodRef("java/lang/NoSuchMethodError", "<init>", "(Ljava/lang/String;)V"));
        dataWriter.writeByte(ProxyConstant.OPC_ATHROW);
        handlerPc = (short) methodInfo.code.size();
        methodInfo.exceptionTable.add(new ExceptionTableEntry(startPc, endPc, handlerPc, this.cp.getClass("java/lang/ClassNotFoundException")));
        ProxyUtil.code_astore(index, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_NEW);
        dataWriter.writeShort(this.cp.getClass("java/lang/NoClassDefFoundError"));
        dataWriter.writeByte(ProxyConstant.OPC_DUP);
        ProxyUtil.code_aload(index, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_INVOKEVIRTUAL);
        dataWriter.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
        dataWriter.writeByte(ProxyConstant.OPC_INVOKESPECIAL);
        dataWriter.writeShort(this.cp.getMethodRef("java/lang/NoClassDefFoundError", "<init>", "(Ljava/lang/String;)V"));
        dataWriter.writeByte(ProxyConstant.OPC_ATHROW);
        if (methodInfo.code.size() > 65535) {
            throw new IllegalArgumentException("code size limit exceeded");
        } else {
            methodInfo.maxStack = 10;
            methodInfo.maxLocals = (short) (index + 1);
            methodInfo.declaredExceptions = new short[0];
            return methodInfo;
        }
    }


    private MethodInfo generateConstructor() throws IOException {
        MethodInfo methodInfo = new MethodInfo("<init>", "(Ljava/lang/reflect/InvocationHandler;)V", ProxyConstant.ACC_PUBLIC,this);
        DataOutputStream writer = new DataOutputStream(methodInfo.code);
        ProxyUtil.code_aload(0, writer);
        ProxyUtil.code_aload(1, writer);
        writer.writeByte(ProxyConstant.OPC_INVOKESPECIAL);
        writer.writeShort(this.cp.getMethodRef(ProxyConstant.superclassName, "<init>", "(Ljava/lang/reflect/InvocationHandler;)V"));
        writer.writeByte(ProxyConstant.OPC_RETURN);
        methodInfo.maxStack = 10;
        methodInfo.maxLocals = 2;
        methodInfo.declaredExceptions = new short[0];
        return methodInfo;
    }




    private void checkReturnType(List<ProxyMethod> list) {
        if (list.size() >= 2) {
            LinkedList linkedList = new LinkedList();
            Iterator iterator = list.iterator();
            boolean over;
            label49:
            do {
                while (iterator.hasNext()) {
                    ProxyMethod proxyMethod = (ProxyMethod) iterator.next();
                    Class<?> clazz = proxyMethod.returnType;
                    if (clazz.isPrimitive()) {
                        throw new IllegalArgumentException("method with same signature"
                                + getFriendMethodSignature(proxyMethod.methodName, proxyMethod.parameterTypes)
                                + " but incompatible return types :" + clazz.getName() + " and others");
                    }
                    over = false;
                    ListIterator listIterator = linkedList.listIterator();
                    if (listIterator.hasNext()) {
                        Class<?> cla = (Class<?>) listIterator.next();
                        if (clazz.isAssignableFrom(cla)) {
                            continue label49;
                        }
                        if (cla.isAssignableFrom(clazz)) {
                            if (!over) {
                                listIterator.set(clazz);
                                over = true;
                            } else {
                                listIterator.remove();
                            }
                        }
                    }
                    if (!over) {
                        linkedList.add(clazz);
                    }

                    if (linkedList.size() > 1) {
                        ProxyMethod proxyMethod1 = list.get(0);
                        throw new IllegalArgumentException("method with same signature "
                                + getFriendMethodSignature(proxyMethod1.methodName, proxyMethod1.parameterTypes)
                                + " but incompatible return types :" + clazz.getName() + " and others");
                    }
                }
                return;
            } while (!over);
            throw new AssertionError();
        }

    }

    private String getFriendMethodSignature(String methodName, Class<?>[] parameterTypes) {
        StringBuilder stringBuilder = new StringBuilder(methodName);
        stringBuilder.append('(');
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                stringBuilder.append('.');
            }
            Class clazz = parameterTypes[i];
            int len;
            for (len = 0; clazz.isArray(); ++len) {
                clazz = clazz.getComponentType();
            }
            stringBuilder.append(clazz.getName());
            while (len-- > 0) {
                stringBuilder.append("[]");
            }
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    private void addProxyMethod(Method method, Class<?> clazz) {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        String desc = methodName + ProxyUtil.getParameterDescriptor(parameterTypes);
        List<ProxyMethod> methodLists = this.proxyMethods.get(desc);
        if (methodLists != null) {
            for (ProxyMethod m : methodLists) {
                if (returnType == m.returnType) {
                    ArrayList arr = new ArrayList();
                    ProxyUtil.collectCompatibleTypes(exceptionTypes, m.exceptionTypes, arr);
                    ProxyUtil.collectCompatibleTypes(m.exceptionTypes, exceptionTypes, arr);
                    m.exceptionTypes = new Class[arr.size()];
                    m.exceptionTypes = (Class[]) arr.toArray(m.exceptionTypes);
                    return;
                }
            }
        } else {
            methodLists = new ArrayList<>(3);
            this.proxyMethods.put(desc, methodLists);
        }
        methodLists.add(new ProxyMethod(methodName, parameterTypes, returnType, exceptionTypes, clazz,this));
    }




}
