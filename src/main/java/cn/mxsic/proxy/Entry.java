package cn.mxsic.proxy;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Function: Entry <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:20:00
 */
public abstract class Entry {
    public Entry() {
    }
    public abstract void write(DataOutputStream writer) throws IOException;
}