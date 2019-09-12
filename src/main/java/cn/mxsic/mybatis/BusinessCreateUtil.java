package cn.mxsic.mybatis;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessCreateUtil {

	/**表名:List<字段名称(大写)>*/
	private Map<String,List<String>> cloNames = new HashMap<String, List<String>>();
	/**表名:List<字段名称(符合bean字段名)>*/
	private Map<String,List<String>> cloFixNames = new HashMap<String, List<String>>();
	/**表名对照key:数据库表名大写,value:xml的表名简写*/
	private Map<String,String> tableValue = new HashMap<String, String>();
	/**表名:List<字段类型(String,long,int,Date)>*/
	private Map<String,List<String>> cloTypes= new HashMap<String, List<String>>();
	/**表名:List<字段注释>*/
	private Map<String,List<String>> cloComments= new HashMap<String, List<String>>();
	/**主表描述*/
	private String tableComment;
	/**生成的业务层文件名前缀*/
	private String businessFix;
	/**生成xml文件的数据*/
	StringBuffer xml = new StringBuffer();
	/**生成bean文件的数据*/
	StringBuffer bean = new StringBuffer();
	/**生成dao文件的数据*/
	StringBuffer dao = new StringBuffer();
	/**生成info文件的数据*/
	StringBuffer info = new StringBuffer();
	
	/**
	 *  获取数据库连接
	 *  @return Connection
	 */
	public Connection getConn(String className, String url, String user,String password) {
		try {
			Class.forName(className).newInstance();
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			throw new RuntimeException("获取数据库连接失败:"+e);
		}
	}
	
	/**
	 * 关闭连接
	 *  @param conn 连接
	 */
	void closeConnection(Connection conn) {
		try {
			if(conn!=null)
				conn.close();
			System.out.println("连接关闭成功!");
		} catch (Exception e) {
			throw new RuntimeException("关闭连接失败:"+e);
		}
	}
	
	/**获取数据库中的表字段信息
	 * @param tableName 表名
	 * @param fields 获取的指定字段字符串,多个用逗号隔开
	 * @param conn 数据库连接
	 * */
	public void getTableInfo(String tableName,String fields,Connection conn){
		//获取指定字段信息
		String [] field = null;
		if (fields!=null && fields.length()>0) {
			if (fields.indexOf(",")>-1) {
				field = fields.split(",");
			}else{
				field = new String []{fields};
			}
		}
		//获取生成的文件的后缀名
		if (businessFix==null || businessFix.length()==0) {
			int index = -1;
			businessFix = tableName.toLowerCase();
			businessFix = businessFix.substring(0,1).toUpperCase()+businessFix.substring(1);
			while ((index=businessFix.indexOf("_"))>-1) {//cs_xj_family-->CsXjFamily
				businessFix = businessFix.replaceFirst("_", "");//csxj_family
				if (businessFix.length()>index) {
					businessFix = businessFix.substring(0, index)+  //cs
							businessFix.substring(index, index+1).toUpperCase()+//X
							businessFix.substring(index+1);//j_family
				}
			}
		}
			
		if (!cloNames.containsKey(tableName)) {
			cloNames.put(tableName, new ArrayList<String>());
		}
		if (!cloTypes.containsKey(tableName)) {
			cloTypes.put(tableName, new ArrayList<String>());
		}
		if (!cloComments.containsKey(tableName)) {
			cloComments.put(tableName, new ArrayList<String>());
		}
		PreparedStatement pst = null;
		StringBuffer sb = new StringBuffer();//记录所有字段名
		try {
			try {
				pst = conn.prepareStatement("select * from "+tableName+" where 1=2");
				ResultSetMetaData rsd = pst.executeQuery().getMetaData();
				for (int i = 0; i < rsd.getColumnCount(); i++) {
					String type = rsd.getColumnTypeName(i + 1);//NUMBER,VARCHAR2,DATE,TIMESTAMP
					String name = rsd.getColumnName(i + 1);
					if (field==null) {
						cloNames.get(tableName).add(name);
						if (type.equals("DATE") || type.equals("TIMESTAMP")) {
							cloTypes.get(tableName).add("Date");
						}else if (type.equals("NUMBER")) {
							if (name.indexOf("ID")>-1) {
								cloTypes.get(tableName).add("long");
							}else{
								cloTypes.get(tableName).add("int");
							}
						}else{
							cloTypes.get(tableName).add("String");
						}
						sb.append("'"+name+"',");
					}else{
						for (String string : field) {
							if (string.toUpperCase().equals(name)) {
								cloNames.get(tableName).add(name);
								if (type.equals("DATE") || type.equals("TIMESTAMP")) {
									cloTypes.get(tableName).add("Date");
								}else if (type.equals("NUMBER")) {
									if (name.indexOf("ID")>-1) {
										cloTypes.get(tableName).add("long");
									}else{
										cloTypes.get(tableName).add("int");
									}
								}else{
									cloTypes.get(tableName).add("String");
								}
								sb.append("'"+name+"',");
							}
						}
					}
//				System.out.print("java类型：" + rsd.getColumnClassName(i + 1));
//				System.out.print("  数据库类型:" + rsd.getColumnTypeName(i + 1));//NUMBER,VARCHAR2,DATE,TIMESTAMP
//				System.out.print("  字段名称:" + rsd.getColumnName(i + 1));
//				System.out.print("  字段长度:" + rsd.getColumnDisplaySize(i + 1));
				}
				if (sb.length()>0) {
					sb.setLength(sb.length()-1);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}finally{
				try {
					pst.close();
					pst = null;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			if (tableComment==null || tableComment.length()==0) {
				//获取表描述
				PreparedStatement pst2 = null;
				ResultSet rs = null;
				try {
					pst2 = conn.prepareStatement("select COMMENTS from USER_TAB_COMMENTS t where  t.table_name = '"+tableName.toUpperCase()+"' and table_type = 'TABLE'");
					rs = pst2.executeQuery();
					if (rs.next()) {
						tableComment = rs.getString("COMMENTS");
//						System.out.println("表描述:"+tableComment);
					}
				} catch (SQLException e) {
					throw new Exception(e);
				}finally{
					if (pst2!=null) {
						try {
							pst2.close();
							pst2 = null;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}
					if (rs!=null) {
						try {
							rs.close();
							rs = null;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
			
			//获取字段描述
			Map<String,String> comments = new HashMap<String,String>();
			if (sb.length()>0) {
				//获取表描述
				PreparedStatement pst3 = null;
				ResultSet rs2 = null;
				try {
					pst3 = conn.prepareStatement("select column_name clo,comments from user_col_comments where Table_Name='"+tableName.toUpperCase()+"' and column_name in ("+sb.toString()+")");
					rs2 = pst3.executeQuery();
					while (rs2.next()) {
						String colComment = rs2.getString("COMMENTS");
						String cloName = rs2.getString("CLO");
						comments.put(cloName, colComment);
//						System.out.println("字段:"+cloName+"--描述:"+colComment);
					}
				} catch (SQLException e) {
					throw new Exception(e);
				}finally{
					if (pst3!=null) {
						try {
							pst3.close();
							pst3 = null;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}
					if (rs2!=null) {
						try {
							rs2.close();
							rs2 = null;
						} catch (SQLException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
			if (comments.size()>0) {
				for (String name : cloNames.get(tableName)) {
					if (comments.containsKey(name)) {
						cloComments.get(tableName).add(comments.get(name));
					}else{
						cloComments.get(tableName).add(" ");
					}
				}
			}
		} catch (Exception e1) {
			System.err.println("第一步,获取表信息失败--!");
			throw new RuntimeException(e1);
		}
	}
	
	/**
	 * 生成bean文件
	 * @param tableName 主表名
	 * @param path 文件包名 如:cn.business.jtglxt
	 * @param joinTables 连接表{key=连接表名,value = {key=查询连接表的字段,value = 连接表条件}}   jointable1: {{_findField:jointable.a1,ointable.a2},{mainFile1:joinFile1,mainFile2:joinFile2...}}   
	 * @param filePath 生成的文件保存路径 如:d://test/
	 * */
	@SuppressWarnings("unused")
	public void createBean(String tableName,String path,Map<String,Map<String,String>> joinTables,String filePath){
		//处理字段名称简写
		if (cloNames.size()>0) {
			//先处理主表的
			String mainTableName = tableName.toLowerCase().replaceAll("_", "");
			tableValue.put(tableName, mainTableName);
			List<String> mainFile = cloNames.get(tableName);//主表的字段
			List<String> cloFix = new ArrayList<String>();
			if (mainFile!=null && mainFile.size()>0) {
				String filed;
				int index = -1;
				for (String string : mainFile) {
					filed = string.toLowerCase();
					while ((index=filed.indexOf("_"))>-1) {//cs_xj_family
						filed = filed.replaceFirst("_", "");//csxj_family
						if (filed.length()>index) {
							filed = filed.substring(0, index)+filed.substring(index, index+1).toUpperCase()+filed.substring(index+1);
						}
					}
					cloFix.add(filed);
				}
			}
			cloFixNames.put(tableName,cloFix);
			for (String table : cloNames.keySet()) {
				if (table.equals(tableName)) {
					continue;//主表已处理
				}else if (cloNames.get(table)!=null && cloNames.get(table).size()>0) {
					cloFix = new ArrayList<String>();
					String cloField;
					int index = -1;
					for (String string : cloNames.get(table)) {
						cloField = string.toLowerCase();
						while ((index=cloField.indexOf("_"))>-1) {//cs_xj_family
							cloField = cloField.replaceFirst("_", "");//csxj_family
							if (cloField.length()>index) {
								cloField = cloField.substring(0, index)+cloField.substring(index, index+1).toUpperCase()+cloField.substring(index+1);
							}
						}
						cloFix.add(cloField);
					}
					String cloTableName = table.toLowerCase().replaceAll("_", "");
					cloFixNames.put(table,cloFix);
					tableValue.put(table, cloTableName);
				}
			}
			
			String xmlName = businessFix+"Mapper";
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
			xml.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"+"\n");
			xml.append("<mapper namespace=\""+path.replace("business", "dao.mapper")+"."+xmlName+"\">"+"\n\n");
			//显示记录对应列
			xml.append("<!-- 显示的记录对应的列-->"+"\n");
			xml.append(" <sql id=\""+businessFix+"Columns\">"+"\n");
			List<String> fs = cloFixNames.get(tableName);
			List<String> ufs = cloNames.get(tableName);
			xml.append("	");
			for (int i = 0; i < fs.size(); i++) {
				xml.append(mainTableName+"."+ufs.get(i)+" as "+fs.get(i)+",");
			}
			List<String> beanField = new ArrayList<String>();//获取的所有字段简写
			List<String> beanFieldType = new ArrayList<String>();//获取的所有字段简写对应的字段类型
			List<String> beanFieldBelong = new ArrayList<String>();//获取的所有字段简写对应的字段所属简表名
			beanField.addAll(fs);
			beanFieldType.addAll(cloTypes.get(tableName));
			for (String type:beanFieldType) {
				beanFieldBelong.add(mainTableName);
			}
			String table;//join表简写
			for (String string : cloFixNames.keySet()) {
				if (string.equals(tableName)) {
					continue;
				}
				table = tableValue.get(string);
				fs = cloFixNames.get(string);
				ufs = cloNames.get(string);
				for (int i = 0; i < fs.size(); i++) {
					xml.append(table+"."+ufs.get(i)+" as "+fs.get(i)+",");
					beanField.add(fs.get(i));
					beanFieldType.add(cloTypes.get(string).get(i));
					beanFieldBelong.add(table);
				}
			}
			xml.setLength(xml.length()-1);//去除最后一个,
			xml.append("\n");
			xml.append(" </sql>"+"\n\n");
			
			// <!-- 列表对应的查询条件组合  -->
			xml.append("<!-- 列表对应的查询条件组合  -->\n");
			xml.append(" <sql id=\"queryOptions\">\n");
			xml.append("	<where>\n");// <if test="position!=null"> AND csxjfamily.position like #{position}</if>
			for (int i = 0; i < beanField.size(); i++) {
				if (beanFieldType.get(i).equals("Date")) {//日期的话需要设置两个条件
					xml.append("			<if test=\""+(beanField.get(i)+"1!=null")+"\">")
					.append(" And "+beanFieldBelong.get(i)+"."+beanField.get(i)+" &gt; #{"+beanField.get(i)+"1}")
					.append("</if>\n");
					xml.append("			<if test=\""+(beanField.get(i)+"2!=null")+"\">")
					.append(" And "+beanFieldBelong.get(i)+"."+beanField.get(i)+" &lt; #{"+beanField.get(i)+"2}")
					.append("</if>\n");
				}else{
					xml.append("			<if test=\""+(beanFieldType.get(i).equals("String")?(beanField.get(i)+"!=null"):(beanField.get(i)+">=0"))+"\">")
					.append(" And "+beanFieldBelong.get(i)+"."+beanField.get(i)+(beanFieldType.get(i).equals("String")?" like ":" = ")+"#{"+beanField.get(i)+"}")
					.append("</if>\n");
				}
			}
			xml.append("	</where> \n");
			xml.append(" </sql>\n\n");
			
			//<!-- 列表查询对应的排序选项  -->
			xml.append("<!-- 列表查询对应的排序选项  -->\n");
			xml.append(" <sql id=\"orderControl\">\n");
			xml.append(" 	<if test=\"orderList!=null\">\n");
			xml.append(" 		<trim prefix=\"ORDER BY\" prefixOverrides=\",\">\n");
			xml.append(" 			<foreach collection=\"orderList\" item=\"order\" open=\"\"  separator=\",\" close=\"\" >\n");
			xml.append(" 				<choose> \n");//<when test="order.columnName=='cdata'"> xjteacher.cdata ${order.type} </when> 
			for (int i = 0; i < beanField.size(); i++) {
				xml.append(" 				<when test=\"order.columnName=='"+beanField.get(i)+"'\">"+ beanFieldBelong.get(i)+"."+beanField.get(i)+" ${order.type} </when> 	\n");	
			}
			xml.append(" 				</choose>\n");
			xml.append(" 			</foreach>\n");
			xml.append(" 		</trim>\n");
			xml.append("	</if> \n");
			xml.append(" </sql>\n\n");
			
			//<!-- 列表查询对应的表关系SQL  -->
			xml.append(" <!-- 列表查询对应的表关系SQL  -->\n");
			xml.append(" <sql id=\"querySqlMain\">\n");
			xml.append("	"+tableName+" "+mainTableName+"\n");
			if (tableValue.size()>1) {
				for (String tableN : joinTables.keySet()) {
					if (tableN.equals(tableName)) {
						continue;
					}else{
						xml.append("	LEFT JOIN "+tableN+" "+tableValue.get(tableN)+" ON ");
						for (String mainFiled : joinTables.get(tableN).keySet()) {//_findField
							if (mainFiled.equals("_findField")) {
								continue;
							}else{
								String [] part = mainFiled.split("\\.");
								if (tableValue.containsKey(part[0])) {
									String cl = joinTables.get(tableN).get(mainFiled);//连接子表字段大写
									xml.append(tableValue.get(tableN)+"."+cl+"="+ tableValue.get(part[0])+"."+part[1]+"\n");
								}
							}
						}
					}
				}
			}
			xml.append(" </sql>\n\n");
			
			//返回的实体路径
			String resultPath = path.replaceFirst("business", "dao.entity")+"."+businessFix+"Entry";
			
			//<!-- 根据ID查询记录 -->
			xml.append(" <!-- 根据ID查询记录 -->\n");
			xml.append(" <select id=\"findOne\" resultType=\""+resultPath+"\">\n");
			xml.append("	SELECT  <include refid=\""+businessFix+"Columns\"/>\n");
			xml.append(" 	FROM <include refid=\"querySqlMain\"/>\n");
			xml.append("	where "+mainTableName+".id=#{id}\n");
			xml.append(" </select>\n\n");
			
			//<!-- 分页查询对应的记录 -->
			xml.append(" <!-- 分页查询对应的记录 -->\n");
			xml.append(" <select id=\"qeury"+businessFix+"s\" resultType=\""+ resultPath+"\">\n");
			xml.append("	SELECT * FROM (  \n");
			xml.append("		SELECT A.*,ROWNUM RN FROM (\n");
			xml.append("			SELECT  <include refid=\""+businessFix+"Columns\"/>\n");
			xml.append("			FROM <include refid=\"querySqlMain\"/>\n");
			xml.append("			<include refid=\"queryOptions\"/>\n");
			xml.append("			<include refid=\"orderControl\"/>\n");
			xml.append("		) A WHERE ROWNUM &lt;=${startRow}+${pageSize}-1 ) \n");
			xml.append("	WHERE RN &gt;=#{startRow}  \n");
			xml.append(" </select>\n\n");
			
			//<!-- 返回记录总数的语句 -->
			xml.append(" <!-- 返回记录总数的语句 -->\n");
			xml.append(" <select id=\"qeury"+businessFix+"sRecordCount\" resultType=\"int\">\n");
			xml.append("	SELECT count(*) num  FROM  <include refid=\"querySqlMain\"/>\n");
			xml.append("	<include refid=\"queryOptions\"/>\n");
			xml.append(" </select>\n\n");
			
			// <!-- 新增记录 -->
			xml.append(" <!-- 新增记录 -->\n");
			xml.append(" <insert id=\"insert"+businessFix+"\" useGeneratedKeys=\"false\" >\n");
			xml.append(" 	<selectKey resultType=\"long\" order=\"AFTER\" keyProperty=\""+mainTableName+".id\">\n");
			xml.append("		SELECT "+tableName+"_SEQ.currval FROM  DUAL \n");
			xml.append("	</selectKey>\n");
			xml.append("		INSERT INTO "+tableName+" (");
			List<String> sqlType = new ArrayList<String>();
			String typ ;
			for (int i = 0; i < cloNames.get(tableName).size(); i++) {
				xml.append(cloNames.get(tableName).get(i)+",");
				typ = cloTypes.get(tableName).get(i);
				if (typ.equals("String")) {
					sqlType.add(",jdbcType=VARCHAR");
				}else if(typ.equals("long") || typ.equals("int")){
					sqlType.add(",jdbcType=NUMERIC");
				}else{
					sqlType.add(",jdbcType=TIMESTAMP");
				}
			}
			xml.setLength(xml.length()-1);
			xml.append(")\n");
			xml.append("		VALUES(");
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {
				xml.append("#{");
				xml.append(mainTableName+"."+cloFixNames.get(tableName).get(i));
				if (sqlType.get(i)!=null && sqlType.get(i).length()>0) {
					xml.append(sqlType.get(i));
				}
				xml.append("},");
			}
			xml.setLength(xml.length()-1);
			xml.append(")\n");
			xml.append(" </insert>\n\n");
			
			// <!-- 更新记录 -->
			xml.append(" <!-- 更新记录 -->\n");
			xml.append(" <update id=\"update"+businessFix+"\" >\n");
			xml.append("	UPDATE "+tableName+"\n");
			xml.append("	<set>\n");
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {
				if (sqlType.get(i)!=null && sqlType.get(i).length()>0) {
					xml.append("		"+cloNames.get(tableName).get(i)+"=#{"+mainTableName+"."+cloFixNames.get(tableName).get(i)+sqlType.get(i)+"},\n");
				}else {
					xml.append("		"+cloNames.get(tableName).get(i)+"=#{"+mainTableName+"."+cloFixNames.get(tableName).get(i)+"},\n");
				}
			}
			xml.append("	</set>\n");
			xml.append("	 	WHERE id = #{"+mainTableName+".id}\n");
			xml.append(" </update>\n\n");
			
			
			//<!-- 删除记录 -->
			xml.append(" <!-- 删除记录 -->\n");
			xml.append(" <delete id=\"delete"+businessFix+"\">\n");
			xml.append("	 DELETE FROM "+tableName+"\n");
			xml.append("	 WHERE id in \n");
			xml.append("	<foreach collection=\"ids\" item=\"id\" open=\"(\"  separator=\",\" close=\")\" >\n");
			xml.append("		 #{id}\n");
			xml.append("	 </foreach>\n");
			xml.append(" </delete>\n\n");
			
			
			xml.append(" <!-- 以下是自定义的配置信息 -->\n");
			xml.append("</mapper>");
/**********************************************xml文件生成完毕*****************************************************/		
			String beanName = businessFix+"Entry";
			//生成bean文件
			bean.append("package "+path.replaceFirst("business", "dao.entity")+";"+"\n\n");
			bean.append("import cn.dao.batch.BatchData;\nimport java.util.*;"+"\n\n");
			bean.append("/**\n").append(" * @description "+tableComment+" 对应的实体\n").append(" */\n").append("@SuppressWarnings(\"serial\")\n");
			bean.append("public class "+beanName+" implements BatchData {\n");
			StringBuffer getSetMethod = new StringBuffer();
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {//主表字段
				bean.append("	private "+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+";	//"+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");
				//set方法
				getSetMethod.append("	/**\n")
				.append("	 * @param "+cloFixNames.get(tableName).get(i)+" "+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");//添加get及set方法
				getSetMethod.append("	 */\n")
				.append("	public void set"+cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1))
				.append("("+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+"){\n")
				.append("	   this."+cloFixNames.get(tableName).get(i)+"="+cloFixNames.get(tableName).get(i)+";\n")
				.append("	}\n\n");
				//get方法
				getSetMethod.append("	/**\n")
				.append("	 * @return "+cloFixNames.get(tableName).get(i)+" "+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");//添加get及set方法
				getSetMethod.append("	 */\n")
				.append("	public "+cloTypes.get(tableName).get(i)+" get"+cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1))
				.append("(){\n")
				.append("	   return this."+cloFixNames.get(tableName).get(i)+";\n")
				.append("	}\n\n");
			}
			if (cloFixNames.size()>1) {
				bean.append("\n\n\n	//子表字段\n");
			}
			for (String string : cloFixNames.keySet()) {//子表字段
				if (string.equals(tableName)) {
					continue;
				}else{
					for (int i = 0; i < cloFixNames.get(string).size(); i++) {
						bean.append("	private "+cloTypes.get(string).get(i)+" "+cloFixNames.get(string).get(i)+";	//"+(cloComments.get(string).get(i)!=null?cloComments.get(string).get(i):"")+"\n");
						//set方法
						getSetMethod.append("	/**\n")
						.append("	 * @param "+cloFixNames.get(string).get(i)+" "+(cloComments.get(string).get(i)!=null?cloComments.get(string).get(i):"")+"\n");//添加get及set方法
						getSetMethod.append("	 */\n")
						.append("	public void set"+cloFixNames.get(string).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(string).get(i).substring(1))
						.append("("+cloTypes.get(string).get(i)+" "+cloFixNames.get(string).get(i)+"){\n")
						.append("	   this."+cloFixNames.get(string).get(i)+"="+cloFixNames.get(string).get(i)+";\n")
						.append("	}\n\n");
						//get方法
						getSetMethod.append("	/**\n")
						.append("	 * @return "+cloFixNames.get(string).get(i)+" "+(cloComments.get(string).get(i)!=null?cloComments.get(string).get(i):"")+"\n");//添加get及set方法
						getSetMethod.append("	 */\n")
						.append("	public "+cloTypes.get(string).get(i)+" get"+cloFixNames.get(string).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(string).get(i).substring(1))
						.append("(){\n")
						.append("	   return this."+cloFixNames.get(string).get(i)+";\n")
						.append("	}\n\n");
					}
				}
			}
			bean.append("\n");
			bean.append("	public "+beanName+"(){}");//默认构造函数
			bean.append("	\n\n\n");
			bean.append(getSetMethod.toString());
			bean.append("}");
/**********************************************bean文件生成完毕*****************************************************/			
			String daoName = businessFix+"Mapper";
			String daoPath = path.replaceFirst("business", "dao.mapper");
			dao.append("package "+daoPath+";\n\n");
			dao.append("import java.util.*;\n");
			dao.append("import org.apache.ibatis.annotations.Param;\n");
			dao.append("import org.springframework.stereotype.Component;\n");
			dao.append("import cn.dao.batch.BatchMapper;\n");
			dao.append("import cn.dao.mapper.MyBatisMapper;\n");
			dao.append("import cn.dao.mapper.OrderItem;\n");
			dao.append("import "+resultPath+";\n\n");
			dao.append("/**\n").append(" * @description "+tableComment+" 对应的Mapper（持久化接口类）\n").append(" */\n");
			dao.append("@Component(\""+daoName.substring(0,1).toLowerCase()+daoName.substring(1)+"\")\n");
			dao.append("public interface "+daoName+" extends MyBatisMapper , BatchMapper {\n\n");
			//findOne方法
			dao.append("	/**\n")
			.append("	 * 根据主键查询对应的记录\n")
			.append("	 * @param id  记录对应的主键\n")
			.append("	 * @return "+businessFix+"Entry\n")
			.append("	 */\n");
			dao.append("	public "+businessFix+"Entry findOne(@Param(\"id\")long id);\n\n");
			//qeuryXjTeachers方法
			StringBuffer params = new StringBuffer();
			StringBuffer paramsC = new StringBuffer();
			dao.append("	/**\n")
			.append("	 * 列表查询\n")
			.append("	 * @param startRow   开始记录的行数\n")
			.append("	 * @param pageSize   设置每页显示的记录数\n");
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {//主表字段
				if (cloTypes.get(tableName).get(i).equals("Date")) {//日期型的要弄两个
					paramsC.append("	 * @param "+cloFixNames.get(tableName).get(i)+"1   "+(cloComments.get(tableName).get(i)!=null?("大于等于"+cloComments.get(tableName).get(i)):"")+"\n");
					paramsC.append("	 * @param "+cloFixNames.get(tableName).get(i)+"2   "+(cloComments.get(tableName).get(i)!=null?("小于等于"+cloComments.get(tableName).get(i)):"")+"\n");
					params.append("@Param(\""+cloFixNames.get(tableName).get(i)+"1\")"+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+"1"+((i<cloFixNames.get(tableName).size()|| cloFixNames.size()>1)?",":"")+(i%8==0?"\n	 	":""));
					params.append("@Param(\""+cloFixNames.get(tableName).get(i)+"2\")"+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+"2"+((i<cloFixNames.get(tableName).size()|| cloFixNames.size()>1)?",":"")+(i%8==0?"\n	 	":""));
				}else{
					paramsC.append("	 * @param "+cloFixNames.get(tableName).get(i)+"   "+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");
					params.append("@Param(\""+cloFixNames.get(tableName).get(i)+"\")"+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+((i<cloFixNames.get(tableName).size() || cloFixNames.size()>1)?",":"")+(i%8==0?"\n	 	":""));
				}
			}
			for (String tab : cloFixNames.keySet()) {
				if (tab.equals(tableName)) {
					continue;
				}else{
					for (int i = 0; i < cloFixNames.get(tab).size(); i++) {
						if (cloTypes.get(tab).get(i).equals("Date")) {//日期型的要弄两个
							paramsC.append("	 * @param "+cloFixNames.get(tab).get(i)+"1   "+(cloComments.get(tab).get(i)!=null?("大于等于"+cloComments.get(tab).get(i)):"")+"\n");
							paramsC.append("	 * @param "+cloFixNames.get(tab).get(i)+"2   "+(cloComments.get(tab).get(i)!=null?("小于等于"+cloComments.get(tab).get(i)):"")+"\n");
							params.append("@Param(\""+cloFixNames.get(tab).get(i)+"1\")"+cloTypes.get(tab).get(i)+" "+cloFixNames.get(tab).get(i)+"1"+((i<cloFixNames.get(tab).size()|| cloFixNames.size()>2)?",":"")+(i%8==0?"\n	 	":""));
							params.append("@Param(\""+cloFixNames.get(tab).get(i)+"2\")"+cloTypes.get(tab).get(i)+" "+cloFixNames.get(tab).get(i)+"2"+((i<cloFixNames.get(tab).size()|| cloFixNames.size()>2)?",":"")+(i%8==0?"\n	 	":""));
						}else{
							paramsC.append("	 * @param "+cloFixNames.get(tab).get(i)+"   "+(cloComments.get(tab).get(i)!=null?cloComments.get(tab).get(i):"")+"\n");
							params.append("@Param(\""+cloFixNames.get(tab).get(i)+"\")"+cloTypes.get(tab).get(i)+" "+cloFixNames.get(tab).get(i)+((i<cloFixNames.get(tab).size()|| cloFixNames.size()>2)?",":"")+(i%8==0?"\n	 	":""));
						}
					}
				}
			}
			dao.append(paramsC.toString());
			dao.append("	 * @param orderList  控制排序\n")
			.append("	 * @return "+businessFix+"Entry\n")
			.append("	 */\n");
			dao.append("	 public List<"+businessFix+"Entry> qeury"+businessFix+"s(@Param(\"startRow\")int startRow, @Param(\"pageSize\")int pageSize,");
			dao.append(params.toString().replaceAll(",,",","))
			.append((params.toString().substring(params.toString().length()-1).equals(",")?"@Param(\"orderList\")List<OrderItem>orderList":",@Param(\"orderList\")List<OrderItem>orderList"));
			dao.append(");\n\n");
			//qeuryXjTeachersRecordCount方法
			dao.append("	/**\n")
			.append("	 * 列表记录总数\n");
			dao.append(paramsC.toString())
			.append("	 * @return int\n")
			.append("	 */\n");
			dao.append("	 public int qeury"+businessFix+"sRecordCount(");
			if (params.toString().trim().length()==(params.toString().trim().lastIndexOf(",")+1)) {
				dao.append(params.toString().substring(0,params.toString().lastIndexOf(",")).replaceAll(",,",","));
			}else{
				dao.append(params.toString().replaceAll(",,",","));
			}
			dao.append(");\n\n");
			//insertXjTeacher方法
			String entryParam  = businessFix.substring(0, 1).toLowerCase()+businessFix.substring(1)+"Entry";
			dao.append("	/**\n")
			.append("	 * 新增记录\n")
			.append("	 * @param "+businessFix.toLowerCase()+"\n")
			.append("	 * @return \n")
			.append("	 */\n");
			dao.append("	 public void insert"+businessFix+"(@Param(\""+businessFix.toLowerCase()+"\")"+businessFix+"Entry "+businessFix.toLowerCase()+");\n\n");
			//updateXjTeacher方法
			dao.append("	/**\n")
			.append("	 * 更新记录\n")
			.append("	 * @param "+businessFix.toLowerCase()+"\n")
			.append("	 * @return \n")
			.append("	 */\n");
			dao.append("	 public void update"+businessFix+"(@Param(\""+businessFix.toLowerCase()+"\")"+businessFix+"Entry "+businessFix.toLowerCase()+");\n\n");
			//deleteXjTeacher方法
			dao.append("	/**\n")
			.append("	 * 删除记录\n")
			.append("	 * @param ids  记录对应的主键\n")
			.append("	 * @return \n")
			.append("	 */\n");
			dao.append("	 public void delete"+businessFix+"(@Param(\"ids\") long[] ids);\n\n");
			dao.append("}");
/**********************************************dao文件生成完毕*****************************************************/	
			String InfoName = businessFix+"Info";
			info.append("package "+path+";\n\n");
			info.append("import java.util.*;\n");
			info.append("import org.apache.log4j.Logger;\n");
			info.append("import org.springframework.context.annotation.Scope;\n");
			info.append("import org.springframework.jdbc.datasource.DataSourceTransactionManager;\n");
			info.append("import org.springframework.stereotype.Service;\n");
			info.append("import org.springframework.transaction.TransactionDefinition;\n");
			info.append("import org.springframework.transaction.TransactionStatus;\n");
			info.append("import org.springframework.transaction.support.DefaultTransactionDefinition;\n");
			info.append("import cn.business.SpringUtil;\n");
			info.append("import cn.business.annotation.SeacherFun;\n");
			info.append("import cn.business.annotation.SearchParameter;\n");
			info.append("import cn.business.base.BaseBusiness;\n");
			info.append("import cn.business.exceptions.BusinessException;\n");
			info.append("import cn.dao.batch.DataBatchDao;\n");
			info.append("import "+resultPath+";\n");
			info.append("import cn.dao.mapper.OrderItem;\n");
			info.append("import "+daoPath+"."+daoName+";\n");
			info.append("import com.mobile.server.util.ObjectUtil;\n\n\n");
			
			info.append("/**\n").append(" * @description "+tableComment+" 对应的（业务逻辑类）\n").append(" */\n");
			info.append("@SuppressWarnings(\"serial\")\n").append("@Scope(\"prototype\")\n").append("@Service(\""+businessFix.substring(0, 1).toLowerCase()+businessFix.substring(1)+"Info\")\n");
			info.append("public class "+businessFix+"Info extends BaseBusiness {\n");
			info.append("	/**日志服务对象*/\n")
			.append("	static Logger logger = Logger.getLogger("+businessFix+"Info.class);\n\n");
			info.append("	/**实体属性*/\n");
			info.append("	private "+beanName+" "+entryParam+";\n\n");
			info.append("	/** 默认构造函数*/\n");
			info.append("	public "+ InfoName+"(){\n");
			info.append("		"+entryParam+" = new "+beanName+"();\n");
			info.append("	}\n\n");
			info.append("	/** 构造函数*/\n");
			info.append("	public "+ InfoName+"("+beanName+" "+entryParam+"){\n");
			info.append("		this."+entryParam+" = "+entryParam+";\n");
			info.append("	}\n\n\n");
			
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {//主表字段
				//set方法
				info.append("	/**\n")
				.append("	 * @param "+cloFixNames.get(tableName).get(i)+" "+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");//添加get及set方法
				info.append("	 */\n")
				.append("	public void set"+cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1))
				.append("("+cloTypes.get(tableName).get(i)+" "+cloFixNames.get(tableName).get(i)+"){\n")
				.append("	   this."+entryParam+".set"+cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1)+"("+cloFixNames.get(tableName).get(i)+");\n")
				.append("	}\n\n");//this.xjTeacherEntry.setId(id);   
				//get方法
				info.append("	/**\n")
				.append("	 * @return "+cloFixNames.get(tableName).get(i)+" "+(cloComments.get(tableName).get(i)!=null?cloComments.get(tableName).get(i):"")+"\n");//添加get及set方法
				info.append("	 */\n")
				.append("	public "+cloTypes.get(tableName).get(i)+" get"+cloFixNames.get(tableName).get(i).substring(0,1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1))
				.append("(){\n")
				.append("	   return this."+entryParam+".get"+cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1)+"();\n")
				.append("	}\n\n");//return this.xjTeacherEntry.getId( );   
			}
			for (String string : cloFixNames.keySet()) {//子表字段
				if (string.equals(tableName)) {
					continue;
				}else{
					for (int i = 0; i < cloFixNames.get(string).size(); i++) {
						//set方法
						info.append("	/**\n")
						.append("	 * @param "+cloFixNames.get(string).get(i)+" "+(cloComments.get(string).get(i)!=null?cloComments.get(string).get(i):"")+"\n");//添加get及set方法
						info.append("	 */\n")
						.append("	public void set"+cloFixNames.get(string).get(i).substring(0,1).toUpperCase()+cloFixNames.get(string).get(i).substring(1))
						.append("("+cloTypes.get(string).get(i)+" "+cloFixNames.get(string).get(i)+"){\n")
						.append("	   this."+entryParam+".set"+cloFixNames.get(string).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(string).get(i).substring(1)+"("+cloFixNames.get(string).get(i)+");\n")
						.append("	}\n\n");//this.xjTeacherEntry.setId(id);   
						//get方法
						info.append("	/**\n")
						.append("	 * @return "+cloFixNames.get(string).get(i)+" "+(cloComments.get(string).get(i)!=null?cloComments.get(string).get(i):"")+"\n");//添加get及set方法
						info.append("	 */\n")
						.append("	public "+cloTypes.get(string).get(i)+" get"+cloFixNames.get(string).get(i).substring(0,1).toUpperCase()+cloFixNames.get(string).get(i).substring(1))
						.append("(){\n")
						.append("	   return this."+entryParam+".get"+cloFixNames.get(string).get(i).substring(0,1).toUpperCase()+cloFixNames.get(string).get(i).substring(1)+"();\n")
						.append("	}\n\n");//return this.xjTeacherEntry.getId( );  
					}
				}
			}
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 设置业务逻辑功能标识 对应数据表的功能标识\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	public String getBusinessName(){\n");
			info.append("	   return \""+tableComment+"\";\n");
			info.append("	}\n\n");
			
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 设置业务逻辑名称\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	public String getFunctionFlag(){\n");
			info.append("		return \""+tableComment+"\";\n");
			info.append("	}\n\n");
			
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 设置获取模块名称\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	public String getModel(){\n");
			info.append("	   return \""+tableComment+"\";\n");
			info.append("	}\n\n");
			
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * BaseBusiness过滤关键字方法(进行增删改查操作前进行过滤)\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	public void filterContent() throws BusinessException {\n");
			info.append("		//TODO Auto-generated method stub \n");
			info.append("	}\n\n");
			
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 获取数据访问层方法\n").append("	 */\n");
			info.append("	public "+daoName+" getMapper(){\n");
			info.append("	   return SpringUtil.getSpringBean("+daoName+".class,\""+daoName.substring(0,1).toLowerCase()+daoName.substring(1)+"\");\n");
			info.append("	}\n\n\n");
			//新增方法
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 新增方法\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	protected void onAdd() throws BusinessException {\n");
			info.append("	   getMapper().insert"+businessFix+"("+entryParam+");\n");
			info.append("	}\n\n");
			//修改方法
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 修改方法\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	protected void onModify() throws BusinessException {\n");
			info.append("	   getMapper().update"+businessFix+"("+entryParam+");\n");
			info.append("	}\n\n");
			//删除方法
			info.append("\n\n\n");
			info.append("	/**\n").append("	 * 删除方法\n").append("	 */\n");
			info.append("	@Override\n");
			info.append("	protected void onDelete(long ids[]) throws BusinessException {\n");
			info.append("	   getMapper().delete"+businessFix+"(ids);\n");
			info.append("	}\n\n");
			//findOne方法
			info.append("\n\n\n");
			info.append("	/**\n")
			.append("	 * 根据主键（id）返回单条记录\n")
			.append("	 * @param id id主键\n")
			.append("	 * @return  "+InfoName+"\n")
			.append("	 */\n");
			info.append("	public "+InfoName+" findOne(long id) throws BusinessException {\n");
			info.append("		"+beanName+" "+entryParam+" = getMapper().findOne(id);\n");
			info.append("		if ("+entryParam+" != null){\n");
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {//主表字段
				String par = cloFixNames.get(tableName).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(tableName).get(i).substring(1);
				//set方法
				info.append("		   this.set"+par+"("+entryParam+".get"+par+"());\n");   			 
			}
			for (String string : cloFixNames.keySet()) {//子表字段
				if (string.equals(tableName)) {
					continue;
				}else{
					for (int i = 0; i < cloFixNames.get(string).size(); i++) {
						String par = cloFixNames.get(string).get(i).substring(0, 1).toUpperCase()+cloFixNames.get(string).get(i).substring(1);
						//set方法
						info.append("		   this.set"+par+"("+entryParam+".get"+par+"());\n");  
					}
				}
			}
			info.append("		}else{\n");
			info.append("			this."+entryParam+" = new "+beanName+"();\n");
			info.append("		}\n");
			info.append("		return this;\n");
			info.append("	}\n\n");

			//查询方法
			info.append("	/**\n");
			info.append("	 * 列表查询 \n");
			StringBuffer searchParam = new StringBuffer();
			StringBuffer param = new StringBuffer();
			searchParam.append("	  @SearchParameter(defaultValue = \"1\",name = \"startRow\")int startRow,");
			searchParam.append("@SearchParameter(defaultValue = \"2000\",name = \"pageSize\")int pageSize,");
			info.append("	 * @param startRow   开始记录的行数 \n");
			info.append("	 * @param pageSize   设置每页显示的记录数 \n");
			for (int i = 0; i < cloFixNames.get(tableName).size(); i++) {//主表字段
				String name = cloFixNames.get(tableName).get(i);
				String type = cloTypes.get(tableName).get(i);
				if (cloTypes.get(tableName).get(i).equals("Date")) {//日期类型的要两个
					info.append("	 * @param "+name+"1 "+(cloComments.get(tableName).get(i)==null?"":("大于等于"+cloComments.get(tableName).get(i)))+"\n");
					info.append("	 * @param "+name+"2 "+(cloComments.get(tableName).get(i)==null?"":("小于等于"+cloComments.get(tableName).get(i)))+"\n");
					searchParam.append("@SearchParameter(name = \""+name+"1\" "+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(tableName).get(i)+" "+name+"1,"+(i%8==0?"\n	  ":""));
					searchParam.append("@SearchParameter(name = \""+name+"2\" "+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(tableName).get(i)+" "+name+"2,"+(i%8==0?"\n	  ":""));
					param.append(name+"1,"+name+"2,");
				}else{
					info.append("	 * @param "+name+" "+(cloComments.get(tableName).get(i)==null?"":cloComments.get(tableName).get(i))+"\n");
					searchParam.append("@SearchParameter(name = \""+name+"\" "+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(tableName).get(i)+" "+name+","+(i%8==0?"\n	  ":""));
					param.append(name+",");
				}
			}
			for (String string : cloFixNames.keySet()) {//子表字段
				if (string.equals(tableName)) {
					continue;
				}else{
					for (int i = 0; i < cloFixNames.get(string).size(); i++) {
						String name = cloFixNames.get(string).get(i);
						String type = cloTypes.get(string).get(i);
						if (cloTypes.get(string).get(i).equals("Date")) {//日期类型的要两个
							info.append("	 * @param "+name+"1 "+(cloComments.get(string).get(i)==null?"":("大于等于"+cloComments.get(string).get(i)))+"\n");
							info.append("	 * @param "+name+"2 "+(cloComments.get(string).get(i)==null?"":("小于等于"+cloComments.get(string).get(i)))+"\n");
							searchParam.append("@SearchParameter(name = \""+name+"1\" "+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(string).get(i)+" "+name+"1,"+(i%8==0?"\n	  ":""));
							searchParam.append("@SearchParameter(name = \""+name+"2\" "+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(string).get(i)+" "+name+"2,"+(i%8==0?"\n	  ":""));
							param.append(name+"1,"+name+"2,");
						}else{
							info.append("	 * @param "+name+" "+(cloComments.get(string).get(i)==null?"":cloComments.get(string).get(i))+"\n");
							searchParam.append("@SearchParameter(name = \""+name+"\""+((type.equals("long")||type.equals("int"))?",defaultValue = \"-1\"":"")+")"+cloTypes.get(string).get(i)+" "+name+","+(i%8==0?"\n	  ":""));
							param.append(name+",");
						}
					}
				}
			}
			param.setLength(param.length()-1);
			info.append("	 * @param orderList  控制排序 \n");
			searchParam.append("@SearchParameter(name=\"orderList\")List<OrderItem> orderList)throws BusinessException{\n");
			info.append("	 * @return List<"+InfoName+"> \n");
			info.append("	 * */ \n");
			info.append("	@SeacherFun(nameAlias=\"qeury"+InfoName+"s\")\n");
			info.append("	public List<"+InfoName+"> qeury"+InfoName+"s(\n");
			info.append(searchParam.toString());
			info.append("\n		List<"+InfoName+"> list = new ArrayList<"+InfoName+">();\n");
			String daoParam = daoName.substring(0,1).toLowerCase()+daoName.substring(1);
			info.append("		"+daoName+" "+daoParam+" = getMapper();\n");
			info.append("		this.setQeuryRecordTotalNum("+daoParam+".qeury"+businessFix+"sRecordCount("+param+"));\n");//
			info.append("		List<"+beanName+"> entryList = "+daoParam+".qeury"+businessFix+"s(startRow,pageSize,"+param+",orderList);\n");
			info.append("		if (entryList != null){\n"); 
			info.append("		  for ("+beanName+" entry : entryList) {\n"); 
			info.append("		     list.add(new "+InfoName+"(entry));\n"); 
			info.append("		     entry = null;\n"); 
			info.append("		  }\n"); 
			info.append("		  entryList = null;\n"); 
			info.append("		}\n"); 
			info.append("		return list;\n"); 
			info.append("	}\n\n");
			
			String infoparam = InfoName.substring(0,1).toLowerCase()+InfoName.substring(1);
			//批量新增接口
			info.append("	/**\n")
			.append("	 * 批量新增记录\n")
			.append("	 * @param "+infoparam+"s info集合\n")
			.append("	 * @return  void \n")
			.append("	 */\n");
			info.append("	 @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n");
			info.append("	 public static void batchInsert(List<"+InfoName+"> "+infoparam+"s) throws Exception{\n");
			info.append("	   //事务控制 \n");
			info.append("	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();\n");
			info.append("	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);\n");
			info.append("	    DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,\"transactionManager\");\n");
			info.append("	    TransactionStatus status = txManager.getTransaction(def);\n");
			info.append("	    try {\n");
			info.append("	      if("+infoparam+"s==null)\n");
			info.append("	        return;\n");
			info.append("	      DataBatchDao dao = new DataBatchDao();\n");
			info.append("	      Collection entryList = new ArrayList<"+beanName+">();\n");
			info.append("	      for("+InfoName+" info:"+infoparam+"s){\n");
			info.append("	         entryList.add(ObjectUtil.copyObjectVals(info,"+beanName+".class));\n");
			info.append("	      }\n");
			info.append("	      dao.insert("+daoName+".class,entryList);\n");
			info.append("	      txManager.commit(status);\n");
			info.append("	   }catch (Exception e) {\n");
			info.append("	      txManager.rollback(status);//事务失败回滚\n");
			info.append("	      logger.error(\""+tableComment+"批量新增失败:\"+e);\n");
			info.append("	   }\n");
			info.append("	 }\n\n");
			
			//批量更新方法
			info.append("	/**\n")
			.append("	 * 批量更新记录\n")
			.append("	 * @param "+infoparam+"s info集合\n")
			.append("	 * @return  void \n")
			.append("	 */\n");
			info.append("	 @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n");
			info.append("	 public static void batchUpdate(List<"+InfoName+"> "+infoparam+"s) throws Exception{\n");
			info.append("	   //事务控制 \n");
			info.append("	    DefaultTransactionDefinition def = new DefaultTransactionDefinition();\n");
			info.append("	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);\n");
			info.append("	    DataSourceTransactionManager txManager =  SpringUtil.getSpringBean(DataSourceTransactionManager.class,\"transactionManager\");\n");
			info.append("	    TransactionStatus status = txManager.getTransaction(def);\n");
			info.append("	    try {\n");
			info.append("	      if("+infoparam+"s==null)\n");
			info.append("	        return;\n");
			info.append("	      DataBatchDao dao = new DataBatchDao();\n");
			info.append("	      Collection entryList = new ArrayList<"+beanName+">();\n");
			info.append("	      for("+InfoName+" info:"+infoparam+"s){\n");
			info.append("	         entryList.add(ObjectUtil.copyObjectVals(info,"+beanName+".class));\n");
			info.append("	      }\n");
			info.append("	      dao.update("+daoName+".class,entryList);\n");
			info.append("	      txManager.commit(status);\n");
			info.append("	   }catch (Exception e) {\n");
			info.append("	      txManager.rollback(status);//事务失败回滚\n");
			info.append("	      logger.error(\""+tableComment+"批量更新失败:\"+e);\n");
			info.append("	   }\n");
			info.append("	 }\n\n");
			
			info.append("}");
/**********************************************info文件生成完毕*****************************************************/	
			
//			System.out.println(xml.toString());
//			System.out.println(dao.toString());
//			System.out.println(info.toString());
//			System.out.println(bean.toString());
			String d = daoName+".java";
			String x = daoName+".xml";
			String e = beanName+".java";
			String i = InfoName+".java";
			
			createFile(filePath+e, bean.toString());
			createFile(filePath+x, xml.toString());
			createFile(filePath+d, dao.toString());
			createFile(filePath+i, info.toString());
			bean.delete(0, bean.length());
			xml.delete(0, bean.length());
			dao.delete(0, bean.length());
			info.delete(0, bean.length());
			System.out.println("生成完毕");
		}
	}
	
	/**生成文件
	 * @param path 文件保存路径
	 * @param data 数据字符串
	 * */
	private void createFile(String path,String data){
		try {
			FileOutputStream out = new FileOutputStream(path);
			out.write(data.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("生成文件["+path+"]失败:"+e);
		}
	}
	
	/**生成business
	 * @param tableName 主表名
	 * @param oraclUrl oracle数据库地址
	 * @param oraclUser oracle数据库用户名
	 * @param oraclPsw  oracle数据库密码
	 * @param packagePath 包路径,如:cn.business.jtglxt
	 * @param joinTables 连接表{key=连接表名,value = {key=查询连接表的字段,value = 连接表条件}}   jointable1: {{_findField:jointable.a1,ointable.a2},{table.file:joinFile1,table2.file:joinFile2...}}   
	 * @param filePath 生成的文件保存路径   d://test/
	 * */
	public void createBusiness(String tableName,String packagePath,String oraclUrl,String oraclUser,String oraclPsw,Map<String,Map<String,String>> joinTables,String filePath){//a.*,b.* from a left join b on b.id = a.bId and ....
		Connection conn = null;
		try {
			 conn = getConn("oracle.jdbc.driver.OracleDriver", oraclUrl, oraclUser, oraclPsw);
			//1.获取数据库字段信息
			//1.1获取主表信息
			if (tableName!=null && tableName.length()>0) {
				getTableInfo(tableName,null,conn);
				//1.2获取连接表信息
				if (joinTables!=null && joinTables.size()>0) {
					for (String joinTableName : joinTables.keySet()) {//遍历获取每个连接表信息
						getTableInfo(joinTableName,joinTables.get(joinTableName).get("_findField"),conn);//获取连接表的连接信息
					}
				}
				//2生成符合bean的字段及生成xml文件语句及文件
				createBean(tableName,packagePath,joinTables,filePath);
			}
		} catch (Exception e) {
			throw new RuntimeException("business层自动生成工具类生成访问层["+tableName+"]失败:"+e);
		}finally{
			if (conn!=null) {
				closeConnection(conn);
			}
		}
//			System.out.println(tableComment);
//			System.out.println(cloNames.get(tableName));
//			System.out.println(cloFixNames.get(tableName));
//			System.out.println(cloTypes.get(tableName));
//			System.out.println(cloComments.get(tableName));
//			if (joinTables!=null && joinTables.size()>0) {
//				for (String table:joinTables.keySet()) {
//					System.out.println(cloNames.get(table));
//					System.out.println(cloFixNames.get(table));
//					System.out.println(cloTypes.get(table));
//					System.out.println(cloComments.get(table));
//				}
//			}
//			System.out.println(businessFix);
	}
	
	
	
	public static void main(String[] args) {
		BusinessCreateUtil u = new BusinessCreateUtil();
//		String tableName = "yzm_groupsend";//主表名
//
//		u.createBusiness(tableName,"qtone.xxt.business.qtone",
//				"jdbc:oracle:thin:@172.16.100.19:1521:sddb",
//				"sdxxt","sd%16!QwED",null,"/Users/liuruimin/Documents/work/");

		String tableName = "SA_EXAMINE_V2";//主表名

		u.createBusiness(tableName,"qtone.xxt.business.sa",
				"jdbc:oracle:thin:@172.16.100.19:1521:sddb",
				"sdxxt","sd%16!QwED",null,"/Users/liuruimin/Documents/work/");
	}
}
