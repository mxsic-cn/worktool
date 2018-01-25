package cn.mysic.flyway;

import org.flywaydb.core.*;
/**
 * Function: flyway db  <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-24 10:00:00
 */

public class App {
    public static void main(String[] args) {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
        // Point it to the database  jdbc:h2:file:./target/foobar
        flyway.setDataSource("jdbc:mysql://127.0.0.1/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "root");
        flyway.setLocations("mysql/migration");
        flyway.setBaselineVersionAsString("2");
        //合并
//        flyway.migrate();
        //校对
//        flyway.validate();

        //信息
//        flyway.info();
        // Start the migration
        //清空
        flyway.clean();
    }
}