package cn.mxsic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class CreatePartitionDemo {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DBURL = "jdbc:oracle:thin:@10.142.50.230:1521:eism";
	public static final String DBUSER = "monitoruser";
	public static final String DBPASS = "eism";
	
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = null;
		Class.forName(DBDRIVER);
		conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		
		StringBuffer sb = new StringBuffer();
		sb.append("---------start crate partition----------\n");
		
		Statement st = conn.createStatement();
		String sql = "select t.tablename from t_dm_monitor t where t.tabletype ='指标' ";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()){
			String tableName = rs.getString("tablename");
			sb.append("drop table " + tableName +"; \n");
			sb.append("create table " + tableName +" \n");
			sb.append("( " +" \n");
			sb.append("  id          VARCHAR2(32) primary key," +" \n");
			sb.append("  resourceid  VARCHAR2(32), " +" \n");
			sb.append("  guidelineid VARCHAR2(100), " +" \n");
			sb.append("  monitordate DATE, " +" \n");
			sb.append("  monvalue    VARCHAR2(200) " +" \n");
			sb.append(") " +" \n");
			sb.append("PARTITION BY RANGE(monitordate) " +" \n");
			sb.append("( " +" \n");
			
			Date startdate = DateUtil.parse("2015-01-01", DateUtil.DEFAULT_DATE_FORMAT);
			Long limittime = DateUtil.parse("2016-01-01", DateUtil.DEFAULT_DATE_FORMAT).getTime();
			while(startdate.getTime() < limittime){
				startdate = DateUtil.minuteCalculate(startdate, 24*60L);
				String dateStr = DateUtil.format(startdate, DateUtil.DEFAULT_DATE_FORMAT);
				String partitionName = "P"+dateStr.replace("-", "");
				sb.append("PARTITION "+partitionName+" VALUES LESS THAN (TO_DATE('"+dateStr+"','YYYY-MM-DD')) TABLESPACE monitortbspace, " +" \n");
			}
			sb.append("PARTITION P20151130 VALUES LESS THAN (TO_DATE('2015-11-30','YYYY-MM-DD')) TABLESPACE monitortbspace " +" \n");
			sb.append(");" +" \n");
			
		}
		
		sb.append("---------end crate partition----------\n");
		System.out.println(sb.toString());
		conn.close();
	}

}
