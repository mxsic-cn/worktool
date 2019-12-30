package cn.mxsic.proxy.jdk;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Function: MethodInfo <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:15:00
 */
public class MethodInfo {
    public int accessFlags;
    public String name;
    public String descriptor;
    public short maxStack;
    public short maxLocals;
    public ByteArrayOutputStream code = new ByteArrayOutputStream();
    public List<ExceptionTableEntry> exceptionTable = new ArrayList();
    public short[] declaredExceptions;
    private MProxyGenerator generator;

    public MethodInfo(String name, String descriptor, int accessFlags,MProxyGenerator generator) {
        this.name = name;
        this.descriptor = descriptor;
        this.accessFlags = accessFlags;
        this.generator = generator;
        generator.cp.getUtf8(name);
        generator.cp.getUtf8(descriptor);
        generator.cp.getUtf8(ProxyConstant.CODE);
        generator.cp.getUtf8(ProxyConstant.EXCEPTIONS);

    }

    public void writer(DataOutputStream writer) throws IOException {
        writer.writeShort(this.accessFlags);
        writer.writeShort(generator.cp.getUtf8(this.name));
        writer.writeShort(generator.cp.getUtf8(this.descriptor));
        writer.writeShort(ProxyConstant.ACC_PRIVATE);
        writer.writeShort(generator.cp.getUtf8(ProxyConstant.CODE));

        writer.writeInt(ProxyConstant.CONSTANT_NAMEANDTYPE + this.code.size() + ProxyConstant.ACC_FINAL * this.exceptionTable.size());
        writer.writeShort(this.maxStack);
        writer.writeShort(this.maxLocals);
        writer.writeInt(this.code.size());
        this.code.writeTo(writer);
        writer.writeShort(this.exceptionTable.size());
        Iterator iterator = this.exceptionTable.iterator();

        while (iterator.hasNext()) {
            ExceptionTableEntry tableEntry = (ExceptionTableEntry) iterator.next();
            writer.writeShort(tableEntry.startPc);
            writer.writeShort(tableEntry.endPc);
            writer.writeShort(tableEntry.handlerPc);
            writer.writeShort(tableEntry.catchType);
        }

        writer.writeShort(ProxyConstant.ZERO);
        writer.writeShort(generator.cp.getUtf8(ProxyConstant.EXCEPTIONS));

        writer.writeInt(ProxyConstant.CONSTANT_UNICODE + ProxyConstant.ACC_PRIVATE * this.declaredExceptions.length);
        for (short s : this.declaredExceptions) {
            writer.writeShort(s);
        }
    }
}
