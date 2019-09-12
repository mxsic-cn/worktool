package cn.mxsic.netant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 6/12/17.
 */
public class ResultInfo {
    private List<Integer>  faildIndex = new ArrayList<Integer>();

    public synchronized void addFiledIndex(Integer integer){
        faildIndex.add(integer);
        LogUtil.writeLog("list.add(" +integer+ ");","faid.txt");
    }

}
