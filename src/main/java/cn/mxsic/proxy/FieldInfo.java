package cn.mxsic.proxy;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Function: FieldInfo <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:15:00
 */
public class FieldInfo {
    public int accessFlags;
    public String name;
    public String descriptor;
    private MProxyGenerator generator;

    public FieldInfo(String name, String descriptor, int accessFlags,MProxyGenerator generator) {
        this.name = name;
        this.descriptor = descriptor;
        this.accessFlags = accessFlags;
        this.generator = generator;
        generator.cp.getUtf8(name);
        generator.cp.getUtf8(descriptor);
    }

    public void writer(DataOutputStream writer) throws IOException {
        writer.writeShort(this.accessFlags);
        writer.writeShort(generator.cp.getUtf8(this.name));
        writer.writeShort(generator.cp.getUtf8(this.descriptor));
        writer.writeShort(ProxyConstant.ZERO);
    }

}