package cn.siqishangshu.performance.memory;

/**
 * Created by liuchuan on 2/14/17.
 */
public class MemoryUtil {
        private static Runtime runtime;
        public static void main(String[] agrs){
            runtime=Runtime.getRuntime();
            System.out.println("处理器的数目"+runtime.availableProcessors());
            System.out.println("空闲内存量："+runtime.freeMemory()/ 1024L/1024L + "M av");
            System.out.println("使用的最大内存量："+runtime.maxMemory()/ 1024L/1024L + "M av");
            System.out.println("内存总量："+runtime.totalMemory()/ 1024L/1024L + "M av");
        }

}
