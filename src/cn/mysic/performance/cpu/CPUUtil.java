package cn.mysic.performance.cpu;

/**
 * Created by liuchuan on 2/14/17.
 */
public class CPUUtil {

    public static void main(String[] agrs){

    }

    /** *//**
     * 获得CPU使用率.
     * @return 返回cpu使用率
     */
//    private double getCpuRatioForWindows() {
//        try {
//            String procCmd = System.getenv("windir")
//                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
//                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
//            // 取进程信息
//            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
//            Thread.sleep(CPUTIME);
//            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
//            if (c0 != null && c1 != null) {
//                long idletime = c1[0] - c0[0];
//                long busytime = c1[1] - c0[1];
//                return Double.valueOf(
//                        PERCENT * (busytime) / (busytime + idletime))
//                        .doubleValue();
//            } else {
//                return 0.0;
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return 0.0;
//        }
//    }
}
