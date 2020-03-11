package cn.mxsic.inter;

/**
 * Function: IType <br>
 * Note: cancel the name . the name must use the dict in db
 *
 * @author: siqishangshu <br>
 * @date: 2019-09-17 10:30:00
 */
public interface IType<T> {
    /**
     * 获取类型编码
     */
    T get();
}
