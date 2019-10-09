package cn.mxsic.proxy;

/**
 * Function: ExceptionTableEntry <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-09 17:14:00
 */
public class ExceptionTableEntry {
    public short startPc;
    public short endPc;
    public short handlerPc;
    public short catchType;

    public ExceptionTableEntry(short startPc, short endPc, short handlerPc, short catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }
}
