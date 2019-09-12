package cn.mxsic.flyway;

import org.flywaydb.core.Flyway;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-31 16:43:00
 */

public class FlyWayRunner implements Runnable {

    private String name;

    public FlyWayRunner(String name){
        this.name = name;
    }
    @Override
    public void run() {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
        // Point it to the database  jdbc:h2:file:./target/foobar
        flyway.setDataSource("jdbc:mysql://127.0.0.1/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "root");
        flyway.setLocations("mysql/migration");
        flyway.setBaselineVersionAsString("1");
        flyway.setIgnoreFutureMigrations(true); //DEFAULT TURE
        flyway.setIgnoreMissingMigrations(true);
        System.out.println("RUNNER: "+name);
        //合并
//        flyway.baseline();
        flyway.migrate();
        System.out.println("RUNNER: "+name);
        //校对
//        flyway.validate();

        //信息
//        flyway.info();
        // Start the migration
        //清空
//        flyway.clean();
    }
}
