package cn.mxsic.proxy.jdk;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Function: IndirectEntry <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:21:00
 */

public class IndirectEntry extends Entry {
    private int tag;
    private short index0;
    private short index1;

    public IndirectEntry(int tag, short index0) {
        super();
        this.tag = tag;
        this.index0 = index0;
        this.index1 = 0;
    }

    public IndirectEntry(int tag, short index0, short index1) {
        super();
        this.tag = tag;
        this.index0 = index0;
        this.index1 = index1;
    }

    @Override
    public int hashCode() {
        return this.tag + this.index0 + this.index1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IndirectEntry) {
            IndirectEntry indirectEntry = (IndirectEntry) obj;
            if (this.tag == indirectEntry.tag && this.index0 == indirectEntry.index0 && this.index1 == indirectEntry.index1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void write(DataOutputStream writer) throws IOException {
        writer.writeByte(this.tag);
        writer.writeShort(this.index0);
        if (this.tag == ProxyConstant.CONSTANT_FIELD || this.tag == ProxyConstant.CONSTANT_METHOD
                || this.tag == ProxyConstant.CONSTANT_INTERFACEMETHOD || this.tag == ProxyConstant.CONSTANT_NAMEANDTYPE) {
            writer.writeShort(this.index1);
        }
    }
}
