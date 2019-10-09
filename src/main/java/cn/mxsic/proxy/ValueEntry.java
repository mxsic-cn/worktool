package cn.mxsic.proxy;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Function: ValueEntry <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:25:00
 */
public class ValueEntry extends Entry {
    private Object value;

    public ValueEntry(Object value) {
        super();
        this.value = value;
    }

    @Override
    public void write(DataOutputStream writer) throws IOException {
        if ((this.value instanceof String)) {
            writer.writeByte(ProxyConstant.CONSTANT_UTF8);//String
            writer.writeUTF((String) this.value);
        } else if (this.value instanceof Integer) {
            writer.writeByte(ProxyConstant.CONSTANT_INTEGER);
            writer.writeInt((Integer) this.value);
        } else if (this.value instanceof Float) {
            writer.writeByte(ProxyConstant.CONSTANT_FLOAG);
            writer.writeFloat((Float) this.value);
        } else if (this.value instanceof Long) {
            writer.writeByte(ProxyConstant.CONSTANT_LONG);
            writer.writeLong((Long) this.value);
        } else {
            if (!(this.value instanceof Double)) {
                throw new InternalError("bogus value entry: " + this.value);
            }
            writer.writeDouble(ProxyConstant.CONSTANT_DOUBLE);
            writer.writeDouble((Double) this.value);
        }
    }
}