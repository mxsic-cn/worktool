package cn.mxsic.proxy.jdk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Function: ProxyMethod <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:16:00
 */
public class ProxyMethod {
    public String methodName;
    public Class<?>[] parameterTypes;
    public Class<?> returnType;
    public Class<?>[] exceptionTypes;
    public Class<?> fromClass;
    public String methodFieldName;
    private MProxyGenerator generator;

    public ProxyMethod(String methodName, Class<?>[] parameterTypes, Class<?> returnType, Class<?>[] exceptionTypes, Class<?> clazz ,MProxyGenerator generator) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.exceptionTypes = exceptionTypes;
        this.fromClass = clazz;
        this.generator = generator;
        this.methodFieldName = "m" + this.generator.proxyMethodCount++;
    }

    public MethodInfo generateMethod() throws IOException {
        String desc = ProxyUtil.getMethodDescriptor(this.parameterTypes, this.returnType);
        MethodInfo methodInfo = new MethodInfo(this.methodName, desc, ProxyConstant.OPC_SIPUSH,this.generator);
        int[] parameterTypeLength = new int[this.parameterTypes.length];
        int index = 1;
        for (int i = 0; i < parameterTypeLength.length; ++i) {
            parameterTypeLength[i] = index;
            index += ProxyUtil.getWordsPerType(this.parameterTypes[i]);
        }
        byte startPc = 0;
        DataOutputStream dataWriter = new DataOutputStream(methodInfo.code);
        ProxyUtil.code_aload(0, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_GETFIELD);
        dataWriter.writeShort(this.generator.cp.getFieldRef(ProxyConstant.superclassName, ProxyConstant.handlerFieldName, "Ljava/lang/reflect/InvocationHandler;"));
        ProxyUtil.code_aload(0, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_GETSTATIC);
        dataWriter.writeShort(this.generator.cp.getFieldRef(ProxyUtil.dotToSlash(this.generator.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
        if (this.parameterTypes.length > 0) {
            ProxyUtil.code_ipush(this.parameterTypes.length, dataWriter);
            dataWriter.writeByte(ProxyConstant.OPC_ANEWARRAY);
            dataWriter.writeShort(this.generator.cp.getClass("java/lang/Object"));
            for (int i = 0; i < this.parameterTypes.length; ++i) {
                dataWriter.writeByte(ProxyConstant.OPC_DUP);
                ProxyUtil.code_ipush(i, dataWriter);
                this.codeWrapArgument(this.parameterTypes[i], parameterTypeLength[i], dataWriter);
                dataWriter.writeByte(ProxyConstant.OPC_AASTORE);
            }
        } else {
            dataWriter.writeByte(1);
        }
        dataWriter.writeByte(ProxyConstant.OPC_INVOKEINTERFACE);
        dataWriter.writeShort(this.generator.cp.getInterfaceMethodRef("java/lang/reflect/InvocationHandler", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;"));
        dataWriter.writeByte(ProxyConstant.ACC_STATIC);
        dataWriter.writeByte(0);
        if (this.returnType == Void.TYPE) {
            dataWriter.writeByte(ProxyConstant.OPC_POP);
            dataWriter.writeByte(ProxyConstant.OPC_RETURN);
        } else {
            this.codeUnwrapReturnValue(this.returnType, dataWriter);
        }

        short handlerPc;
        short endPc = handlerPc = (short) methodInfo.code.size();
        List list = ProxyUtil.computeUniqueCatchList(this.exceptionTypes);
        if (list.size() > 0) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Class clazz = (Class) iterator.next();
                methodInfo.exceptionTable.add(new ExceptionTableEntry(startPc, endPc, handlerPc, this.generator.cp.getClass(ProxyUtil.dotToSlash(clazz.getName()))));
            }
            dataWriter.writeByte(ProxyConstant.OPC_ATHROW);
            handlerPc = (short) methodInfo.code.size();
            methodInfo.exceptionTable.add(new ExceptionTableEntry(startPc, endPc, handlerPc, this.generator.cp.getClass("java/lang/Throwable")));
            ProxyUtil.code_astore(index, dataWriter);
            dataWriter.writeByte(ProxyConstant.OPC_NEW);
            dataWriter.writeShort(this.generator.cp.getClass("java/lang/reflect/UndeclaredThrowableException"));
            dataWriter.writeByte(ProxyConstant.OPC_DUP);
            ProxyUtil.code_aload(index, dataWriter);
            dataWriter.writeByte(ProxyConstant.OPC_INVOKESPECIAL);
            dataWriter.writeShort(this.generator.cp.getMethodRef("java/lang/reflect/UndeclaredThrowableException", "<init>", "(Ljava/lang/Throwable;)V"));
            dataWriter.writeByte(ProxyConstant.OPC_ATHROW);
        }
        if (methodInfo.code.size() > 65535) {
            throw new IllegalArgumentException("code size limit exceeded");
        } else {
            methodInfo.maxStack = 10;
            methodInfo.maxLocals = (short) (index + 1);
            methodInfo.declaredExceptions = new short[this.exceptionTypes.length];
            for (int i = 0; i < this.exceptionTypes.length; ++i) {
                methodInfo.declaredExceptions[i] = this.generator.cp.getClass(ProxyUtil.dotToSlash(this.exceptionTypes[i].getName()));
            }
            return methodInfo;
        }
    }

    private void codeUnwrapReturnValue(Class<?> returnType, DataOutputStream dataWriter) throws IOException {
        if (returnType.isPrimitive()) {
            PrimitiveTypeInfo primitiveTypeInfo = PrimitiveTypeInfo.get(returnType);
            dataWriter.writeByte(ProxyConstant.OPC_CHECKCAST);
            dataWriter.writeShort(this.generator.cp.getClass(primitiveTypeInfo.wrapperClassName));
            dataWriter.writeByte(ProxyConstant.OPC_INVOKEVIRTUAL);
            dataWriter.writeShort(this.generator.cp.getMethodRef(primitiveTypeInfo.wrapperClassName, primitiveTypeInfo.unwrapMethodName, primitiveTypeInfo.unwrapMethodDesc));
            if (returnType != Integer.TYPE && returnType != Boolean.TYPE && returnType != Byte.TYPE && returnType != Character.TYPE && returnType != Short.TYPE) {
                if (returnType == Long.TYPE) {
                    dataWriter.writeByte(ProxyConstant.OPC_LRETURN);
                } else if (returnType == Float.TYPE) {
                    dataWriter.writeByte(ProxyConstant.OPC_FRETURN);
                } else {
                    if (returnType != Double.TYPE) {
                        throw new AssertionError();
                    }
                    dataWriter.writeByte(ProxyConstant.OPC_DRETURN);
                }
            } else {
                dataWriter.writeByte(ProxyConstant.OPC_IRETUEN);
            }
        } else {
            dataWriter.writeByte(ProxyConstant.OPC_CHECKCAST);
            dataWriter.writeShort(this.generator.cp.getClass(ProxyUtil.dotToSlash(returnType.getName())));
            dataWriter.writeByte(ProxyConstant.OPC_ARETURN);
        }
    }

    private void codeWrapArgument(Class<?> parameterType, int index, DataOutputStream dataWriter) throws IOException {
        if (parameterType.isPrimitive()) {
            PrimitiveTypeInfo primitiveTypeInfo = PrimitiveTypeInfo.get(parameterType);
            if (parameterType != Integer.TYPE && parameterType != Boolean.TYPE && parameterType != Byte.TYPE && parameterType != Character.TYPE && parameterType != Short.TYPE) {
                if (parameterType == Long.TYPE) {
                    ProxyUtil.code_lload(index, dataWriter);
                } else if (parameterType == Float.TYPE) {
                    ProxyUtil.code_fload(index, dataWriter);
                } else {
                    if (parameterType != Double.TYPE) {
                        throw new AssertionError();
                    }
                    ProxyUtil.code_dload(index, dataWriter);
                }
            } else {
                ProxyUtil.code_iload(index, dataWriter);
            }
            dataWriter.writeByte(ProxyConstant.OPC_INVOKESTATIC);
            dataWriter.writeShort(this.generator.cp.getMethodRef(primitiveTypeInfo.wrapperClassName, "valueOf", primitiveTypeInfo.wrapperValueOfDesc));
        } else {
            ProxyUtil.code_aload(index, dataWriter);
        }
    }

    public void codeFieldInitialization(DataOutputStream dataWriter) throws IOException {
        codeClassForName(this.fromClass, dataWriter);
        ProxyUtil.code_ldc(this.generator.cp.getString(this.methodName), dataWriter);
        ProxyUtil.code_ipush(this.parameterTypes.length, dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_ANEWARRAY);
        dataWriter.writeShort(this.generator.cp.getClass("java/lang/Class"));

        for (int i = 0; i < this.parameterTypes.length; ++i) {
            dataWriter.writeByte(ProxyConstant.OPC_DUP);
            ProxyUtil.code_ipush(i, dataWriter);
            if (this.parameterTypes[i].isPrimitive()) {
                PrimitiveTypeInfo primitiveTypeInfo = PrimitiveTypeInfo.get(this.parameterTypes[i]);
                dataWriter.writeByte(ProxyConstant.OPC_GETSTATIC);
                dataWriter.writeShort(this.generator.cp.getFieldRef(primitiveTypeInfo.wrapperClassName, "TYPE", "Ljava/lang/Class;"));
            } else {
                codeClassForName(this.parameterTypes[i], dataWriter);
            }
            dataWriter.writeByte(ProxyConstant.OPC_AASTORE);
        }
        dataWriter.writeByte(ProxyConstant.OPC_INVOKEVIRTUAL);
        dataWriter.writeShort(this.generator.cp.getMethodRef("java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        dataWriter.writeByte(ProxyConstant.OPC_PUTSTATIC);
        dataWriter.writeShort(this.generator.cp.getFieldRef(ProxyUtil.dotToSlash(this.generator.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
    }

    public void codeClassForName(Class<?> fromClass, DataOutputStream dataWriter) throws IOException {
        ProxyUtil.code_ldc(this.generator.cp.getString(fromClass.getName()), dataWriter);
        dataWriter.writeByte(ProxyConstant.OPC_INVOKESTATIC);
        dataWriter.writeShort(this.generator.cp.getMethodRef("java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
    }

}

