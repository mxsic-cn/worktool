package cn.mxsic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * MockApplication <br>
 *
 * @author apus <br>
 * @date 2018-11-09 10:16:00
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn"})
@PropertySource(value = {"classpath:/mock-application.properties"})
public class MockApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }
}
