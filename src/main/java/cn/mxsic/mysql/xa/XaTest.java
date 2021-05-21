package cn.mxsic.mysql.xa;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * Function: XaTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-03-22 16:28:00
 */
public class XaTest {


    public static MysqlXADataSource getDataSource(String connStr, String user, String pwd) {

        try {

            MysqlXADataSource ds = new MysqlXADataSource();
            ds.setUrl(connStr);
            ds.setUser(user);
            ds.setPassword(pwd);

            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] arg) {
        String connStr1 = "jdbc:mysql://192.168.2.69:3306/logistics";
        String connStr2 = "jdbc:mysql://192.168.2.69:3307/logistics";

        try {
            //从不同数据库获取数据库数据源
            MysqlXADataSource ds1 = getDataSource(connStr1, "root", "Rootroot1!");
            MysqlXADataSource ds2 = getDataSource(connStr2, "root", "Rootroot1!");

            //数据库1获取连接
            XAConnection xaConnection1 = ds1.getXAConnection();
            XAResource xaResource1 = xaConnection1.getXAResource();
            Connection connection1 = xaConnection1.getConnection();
            Statement statement1 = connection1.createStatement();

            //数据库2获取连接
            XAConnection xaConnection2 = ds2.getXAConnection();
            XAResource xaResource2 = xaConnection2.getXAResource();
            Connection connection2 = xaConnection2.getConnection();
            Statement statement2 = connection2.createStatement();

            //创建事务分支的xid
            Xid xid1 = new MysqlXid(new byte[]{0x010}, new byte[]{0x020}, 1000);
            Xid xid2 = new MysqlXid(new byte[]{0x021}, new byte[]{0x022}, 1000);

            try {
                //事务分支1关联分支事务sql语句
                xaResource1.start(xid1, XAResource.TMNOFLAGS);
                int update1Result = statement1.executeUpdate("update xa set money=money +2 where id=1");
                xaResource1.end(xid1, XAResource.TMSUCCESS);

                //事务分支2关联分支事务sql语句
                xaResource2.start(xid2, XAResource.TMNOFLAGS);
                int update2Result = statement2.executeUpdate("update xa set money= money + 1 where id=1");
                xaResource2.end(xid2, XAResource.TMSUCCESS);

                // 两阶段提交协议第一阶段
                int ret1 = xaResource1.prepare(xid1);
                int ret2 = xaResource2.prepare(xid2);

                // 两阶段提交协议第二阶段
                if (XAResource.XA_OK == ret1 && XAResource.XA_OK != ret2) {
                    xaResource1.commit(xid1, false);
                    xaResource2.commit(xid2, false);

                    System.out.println("reslut1:" + update1Result + ", result2:" + update2Result);
                } else { //rollback
                    if (XAResource.XA_OK != ret1) {
                        xaResource1.rollback(xid1);
                    }
                    if (XAResource.XA_OK != ret2) {
                        xaResource2.rollback(xid2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
