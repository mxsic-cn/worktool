package cn.mysic.log.logback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by siqishangshu on 17/10/17.
 */
public class Program {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Program.class);
        logger.info("log slf4j logger");
        System.out.println("SDFl");
    }
}
