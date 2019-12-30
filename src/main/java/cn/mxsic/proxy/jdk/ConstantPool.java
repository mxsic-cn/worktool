package cn.mxsic.proxy.jdk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Function: ConstantPool <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:13:00
 */
public class ConstantPool {
    private List<Entry> pool;
    private Map<Object, Short> map;
    private boolean readOnly;

    public ConstantPool() {
        this.pool = new ArrayList<>(32);
        this.map = new HashMap<>(16);
        this.readOnly = false;
    }

    public short addEntry(Entry entry) {
        this.pool.add(entry);
        if (this.pool.size() >= 65535) {
            throw new IllegalArgumentException("constant pool size limit exceeded");
        }
        return (short) this.pool.size();
    }

    public short getClass(String className) {
        short classV = this.getUtf8(className);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_CLASS, classV));
    }

    public int getString(String methodName) {
        short u1 = this.getUtf8(methodName);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_STRING, u1));
    }

    public int getFieldRef(String superclassName, String name, String type) {
        short u1 = this.getClass(superclassName);
        short u2 = this.getNameAndType(name, type);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_FIELD, u1, u2));
    }

    public short getMethodRef(String className, String name, String type) {
        short u1 = this.getClass(className);
        short u2 = this.getNameAndType(name, type);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_METHOD, u1, u2));
    }

    public int getInterfaceMethodRef(String s, String name, String type) {
        short u1 = this.getClass(s);
        short u2 = this.getNameAndType(name, type);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_INTERFACEMETHOD, u1, u2));
    }

    private short getNameAndType(String name, String type) {
        short u1 = this.getUtf8(name);
        short u2 = this.getUtf8(type);
        return this.getIndirect(new IndirectEntry(ProxyConstant.CONSTANT_NAMEANDTYPE, u1, u2));
    }

    public short getUtf8(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        return this.getValue(str);
    }

    public short getInteger(int i) {
        return this.getValue(new Integer(i));
    }

    public short getFloat(float f) {
        return this.getValue(new Float(f));
    }

    public void setReadOnly() {
        this.readOnly = true;
    }

    public void write(DataOutputStream dataWriter) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(dataWriter);
        outputStream.writeShort(this.pool.size() + 1);
        Iterator iterator = this.pool.iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            entry.write(outputStream);
        }
    }

    public short getValue(Object key) {
        Short value = this.map.get(key);
        if (value != null) {
            return value;
        } else if (this.readOnly) {
            throw new InternalError("late constant pool addition: " + key);
        } else {
            short newValue = this.addEntry(new ValueEntry(key));
            this.map.put(key, new Short(newValue));
            return newValue;
        }
    }

    public short getIndirect(IndirectEntry indirectEntry) {
        Short sv = this.map.get(indirectEntry);
        if (sv != null) {
            return sv;
        } else if (this.readOnly) {
            throw new InternalError("late constant pool addition");
        } else {
            short s = this.addEntry(indirectEntry);
            this.map.put(indirectEntry, new Short(s));
            return s;
        }
    }
}