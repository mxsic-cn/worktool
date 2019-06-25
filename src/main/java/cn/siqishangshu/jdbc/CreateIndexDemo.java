package cn.siqishangshu.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateIndexDemo {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DBURL = "jdbc:oracle:thin:@10.142.50.230:1521:eism";
	public static final String DBUSER = "monitoruser";
	public static final String DBPASS = "eism";
	
	
	public static void main(String[] args) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("---------start crate index----------\n");
		
		Connection conn = null;
//		Class.forName(DBDRIVER);
//		conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		
//		Statement st = conn.createStatement();
//		String sql = "select t.tablename from t_dm_monitor t where t.tabletype ='告警' ";
//		ResultSet rs = st.executeQuery(sql);
//		while(rs.next()){
//			String tableName = rs.getString("tablename");
//			sb.append("CREATE INDEX IX_"+tableName+"_1 ON "+tableName+"(STARTTIME);\n");
//			sb.append("CREATE INDEX IX_"+tableName+"_2 ON "+tableName+"(CURRENTTIME);\n");
//			sb.append("CREATE INDEX IX_"+tableName+"_3 ON "+tableName+"(CREATETIME);\n");
//			sb.append("CREATE INDEX IX_"+tableName+"_4 ON "+tableName+"(RESOURCEID);\n");
//		}
//		
//		sb.append("");
//		sb.append("-------------------------------------------------------------------------------------");
//		sb.append("");
		
		Statement st1 = conn.createStatement();
		String sql1 = "select t.tablename from t_dm_monitor t where t.tabletype ='指标' ";
		ResultSet rs1 = st1.executeQuery(sql1);
		while(rs1.next()){
			String tableName = rs1.getString("tablename");
			sb.append("CREATE INDEX IX_"+tableName+"_1 ON "+tableName+"(GUIDELINEID);\n");
			sb.append("CREATE INDEX IX_"+tableName+"_2 ON "+tableName+"(MONITORDATE);\n");
			sb.append("CREATE INDEX IX_"+tableName+"_3 ON "+tableName+"(RESOURCEID);\n");
		}
		
		sb.append("---------end crate index----------\n");
		System.out.println(sb.toString());
		
		conn.close();
	}
}
