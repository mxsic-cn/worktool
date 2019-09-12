package cn.mxsic.snmp.util;

import cn.mxsic.snmp.mib.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;



public class SNMPUtils {
	private static final Logger LOG = LoggerFactory.getLogger(SNMPUtils.class);
	private int SNMPV = 0;
	private Snmp snmp;
	private Target target;
	private static String mibpath = "cn.mysic.snmp.mib.";
	private static String get = "get";

	/**
	 * 初始化SNMP服务
	 *
	 * @param address IP地址
	 * @param config	端口
	 *  port	端口
	 *  timeout	超时时间
	 *  retries	重试次数
	 *  securityLevel	认证加密等级
	 *  user	用户名
	 *  authAlgorithm	认证方式
	 *  authPassword	认证密码
	 *  privcyAlgorithm	加密方式
	 *  privacyPassword	加密密码
	 */
	public void start(String address, SNMPConfig config) throws IOException {
		this.SNMPV = config.getVersion();
		if (this.SNMPV != SnmpConstants.version3){
			this.snmp = new Snmp(new DefaultUdpTransportMapping());
			this.snmp.listen();
			this.target = new CommunityTarget();
			((CommunityTarget)this.target).setCommunity(new OctetString(config.getReadCommunity()));
			this.target.setVersion(this.SNMPV);
			this.target.setAddress(new UdpAddress(address+"/"+config.getPort()));
			this.target.setTimeout(config.getTimeout());
			this.target.setRetries(config.getReTryies());
		}else{
			this.snmp = new Snmp(new DefaultUdpTransportMapping());
			USM usm = new USM(SecurityProtocols.getInstance(),
					new OctetString(MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);
			snmp.listen();
			OID AUTH_SECURITY_OID =  getAuth_security_oid(config.getAuthAlgorithm());
			OID PRIV_SECURITY_OID =  getPriv_security_oid(config.getPrivacyAlgorithm());
			UsmUser usmUser = null;
			if(config.getSecurityLevel() == SecurityLevel.NOAUTH_NOPRIV){//不认证也不加密
				usmUser = new UsmUser(
						new OctetString(config.getSnmpUser()),
						null, null, null, null);
			}else if(config.getSecurityLevel() == SecurityLevel.AUTH_NOPRIV){//认证但是不加密
				usmUser = new UsmUser(
						new OctetString(config.getSnmpUser()),
						AUTH_SECURITY_OID, new OctetString(config.getAuthPassword()), null, null);
			}else if(config.getSecurityLevel() == SecurityLevel.AUTH_PRIV){//既认证又加密
				usmUser = new UsmUser(
						new OctetString(config.getSnmpUser()),
						AUTH_SECURITY_OID, new OctetString(config.getAuthPassword()),
						PRIV_SECURITY_OID, new OctetString(config.getPrivacyPassword()));
			}
			snmp.getUSM().addUser(new OctetString(config.getSnmpUser()), usmUser);
			this.target = new UserTarget();
			target.setVersion(SnmpConstants.version3);
			target.setAddress(new UdpAddress(address + "/" + config.getPort()));
			target.setSecurityLevel(config.getSecurityLevel());
			target.setSecurityName(new OctetString(config.getSnmpUser()));
			target.setTimeout(config.getTimeout());
			target.setRetries(config.getReTryies());
		}

	}

	public boolean connectTest(String address,SNMPConfig snmpConfig){
		boolean flag = false;
		try {
			String sysName = SnmpConstants.sysName.toString();  // ".1.3.6.1.2.1.1.5.0";//SysName;
			start(address, snmpConfig);
			flag = isReadable();
			sysName = getVariable(sysName).toString();
			end();
			if (flag && snmpConfig.getWriteCommunity() != null) {
				snmpConfig.setReadCommunity(snmpConfig.getWriteCommunity());
				start(address, snmpConfig);
				flag = isWriteable(sysName);
			}
			end();
		}catch(Exception e){
			flag = false;
		}finally {
			end();
			return flag;
		}
	}
	public boolean isReadable(){
		boolean isReadable = false;
		String des ;
		try{
			String reg = "^\\d+$";
			des = getVariable(SnmpConstants.sysDescr.toString()).toString();
			if ( des != null){
				isReadable = true;
			}
			if(SNMPV == SnmpConstants.version3){
				if(des.matches(reg)){
					isReadable = false;
				}
			}
		}catch (Exception e){
			LOG.error("SNMP Connect Error {}",e);
			isReadable = false;
		}finally {
			return isReadable;
		}
	}

	public boolean isWriteable(String sysName){

		String oid = SnmpConstants.sysName.toString();//SysName;
		boolean isWriteable = false;
		try{
			if ( setVariable(oid,sysName)){
				isWriteable = true;
			}
		}catch (Exception e){
			LOG.error("SNMP Connect Error {}",e);
			isWriteable = false;
		}finally {
			return isWriteable;
		}
	}
	/**
	 * end SNMP service
	 */
	public void end(){
		try{
			if (this.snmp != null){
				this.snmp.close();
			}
		}catch(Exception e){
		}
	}

	/**
	 * use oid get value from target
	 * @param oid
	 * @return
	 * @throws IOException
	 */
	public Variable getVariable(String oid)throws Exception{
		if(SnmpConstants.version3 == this.SNMPV){
			ScopedPDU pdu = new ScopedPDU();
			pdu.setType(PDU.GET);
			pdu.add(new VariableBinding(new OID(oid)));
			ResponseEvent responseEvent = snmp.send(pdu, target);
			PDU response = responseEvent.getResponse();
			Variable  var = null;
			if (response != null) {
				if (response.getErrorStatus() == PDU.noError) {
					Vector<? extends VariableBinding> vbs = response.getVariableBindings();
					for (VariableBinding vb : vbs) {
						var = vb.getVariable();
						break;
					}
				} else {
					LOG.error("Error:" , response.getErrorStatusText());
				}
			}else{
				LOG.error("Error: no Response ",target.getAddress());
			}
			return var;
		}else{
			PDU pdu = new PDU();
			pdu.setType(PDU.GET);
			pdu.add(new VariableBinding(new OID(oid)));
			ResponseEvent responseEvent = this.snmp.send(pdu,this.target);
			PDU response = responseEvent.getResponse();
			Variable  var = null;
			if (response != null){
				if(response.getErrorStatus() == PDU.noError){
					Vector<? extends VariableBinding> vbs = response.getVariableBindings();
					for (VariableBinding vb : vbs) {
						var = vb.getVariable();
						break;
					}
				}else {
					LOG.error("Error:" , response.getErrorStatusText());
				}
			}else{
				LOG.error("Error: no Response " , target.getAddress());
			}
			return var;
		}
	}

	public boolean setVariable(String oid,String value)throws IOException{
		boolean result = false;

		PDU pdu = new PDU();
		pdu.setType(PDU.SET);
		pdu.add(new VariableBinding(new OID(oid),new OctetString(value)));
		ResponseEvent responseEvent = this.snmp.send(pdu,this.target);
		PDU response = responseEvent.getResponse();
		Variable  var = null;
		if (response != null){
			if(response.getErrorStatus() == PDU.noError){
				Vector<? extends VariableBinding> vbs = response.getVariableBindings();
				for (VariableBinding vb : vbs) {
					var = vb.getVariable();
					if (value.equals(var.toString())){
						result = true;
						break;
					}
				}
			}else{
				LOG.error("Error:" , response.getErrorStatusText());
			}
		}else{
			LOG.error("Error: no Response " , target.getAddress());
		}
		return result;
	}

	public List getTable(Class clazz)throws Exception {
		List list = new ArrayList<>();
		if (SNMPV != SnmpConstants.version1) {
			int type = PDU.GETBULK;
			TableUtils utils = new TableUtils(this.snmp, new DefaultPDUFactory(type));
			utils.setMaxNumRowsPerPDU(5);
			String tableOID = ((SNMPOID) clazz.getAnnotation(SNMPOID.class)).value();
			OID[] oids = new OID[]{new OID(tableOID)};
			List<TableEvent> events = utils.getTable(this.target, oids, null, null);
			String index = null;

			int lineIndex = 0;
			for (TableEvent e : events) {
				if (e.getStatus() != -1 && e.getIndex() != null) {
					String indexId = e.getIndex().toString();
					String trueIndex = indexId.substring(0, indexId.indexOf("."));
					if (index == null) {
						index = trueIndex;
						lineIndex++;
					} else {
						if (trueIndex != null && !trueIndex.equals(index)) {
							break;
						} else {
							lineIndex++;
						}
					}
				}

			}
			if (lineIndex == 0) {
				return list;
			}
			int rowIndex = events.size() / lineIndex;
			 /*
				make table to the array
			  */
			TableEvent[][] eventarr = null;
			int jj = 0, kk = 0;
			TableEvent event = null;
			try {
				eventarr = new TableEvent[rowIndex][lineIndex];
				for (int i = 0; i < rowIndex * lineIndex; i++, kk++) {
					event = events.get(i);
					eventarr[jj][kk] = event;
					if ((i + 1) % lineIndex == 0) {
						jj++;
						kk = -1;
					}
				}
				if (rowIndex * lineIndex != events.size()) {
					LOG.error(" ERROR :", this.target.getAddress());
				}
			} catch (Exception e) {
				LOG.error("Get table Error {}", e);
			}
			/**
			 * reflect to object list;
			 */
			for (int i = 0; i < lineIndex; i++) {
				list.add(clazz.newInstance());
			}
			Field[] fields = clazz.getFields();//get attributes;

			for (int i = 0; i < eventarr.length; i++) {
				String fieldName = null;
				for (int j = 0; j < eventarr[i].length; j++) {
					for (Field field : fields) {
						String oid = ((SNMPOID) field.getAnnotation(SNMPOID.class)).value();
						if (eventarr[i][j].getIndex() != null) {
							String indexid = eventarr[i][j].getIndex().toString();
							String trueIndex = indexid.substring(0, indexid.indexOf("."));
							if (Integer.valueOf(trueIndex) == Integer.valueOf(oid)) {
								fieldName = field.getName();
								continue;
							}
						}
					}
					if (fieldName != null) {
						String fieldType = clazz.getField(fieldName).getType().getSimpleName();
						if (eventarr[i][j] != null && eventarr[i][j].getColumns() != null) {
							String fieldValue = eventarr[i][j].getColumns()[0].toValueString();
							if ("int".equals(fieldType) || "Integer".equals(fieldType)) {
								clazz.getField(fieldName).set(list.get(j), Integer.valueOf(fieldValue));
							} else if ("float".equals(fieldType) || "Float".equals(fieldType)) {
								clazz.getField(fieldName).set(list.get(j), Float.valueOf(fieldValue));
							} else if ("double".equals(fieldType) || "Double".equals(fieldType)) {
								clazz.getField(fieldName).set(list.get(j), Double.valueOf(fieldValue));
							} else if ("long".equals(fieldType) || "Long".equals(fieldType)) {
								clazz.getField(fieldName).set(list.get(j), Long.valueOf(fieldValue));
							} else {
								clazz.getField(fieldName).set(list.get(j), fieldValue);
							}
						}
					}
				}
			}
		}
		return list;
	}


	public Object getObjectInfo(Class clazz) throws Exception{
		Object obj = clazz.newInstance();
		try {
			Field[] fields = clazz.getDeclaredFields();
			String objOID = ((SNMPOID) clazz.getAnnotation(SNMPOID.class)).value();
			for (Field field : fields) {
				if (field.getAnnotation(SNMPOID.class) != null) {
					String fieldType = field.getType().getSimpleName();
					String fieldName = field.getName();
					if ("List".equals(fieldType) && SNMPV!=SnmpConstants.version1) {
						String className = (mibpath + fieldName.substring(0, 1).toUpperCase() +
								fieldName.substring(1)).toString();
						Class cla = Class.forName(className);
						List list = getTable(cla);
						clazz.getField(fieldName).set(obj, list);
					} else {
						String oid = objOID + "." + field.getAnnotation(SNMPOID.class).value() + ".0";
						Variable var = getVariable(oid);
						String value = null;
						if (var == null || "noSuchInstance".equals(var.toString()) || "noSuchObject".equals(var.toString())) {
							LOG.error(clazz.getName() + "-" + fieldName + " oid(" + oid + ") is" + var);
							continue;
						}
						if (var != null && var.toString() != "") {
							value = var.toString();
							if ("int".equals(fieldType) || "Integer".equals(fieldType)) {
								clazz.getField(fieldName).set(obj, Integer.valueOf(value));
							} else if ("float".equals(fieldType) || "Float".equals(fieldType)) {
								clazz.getField(fieldName).set(obj, Float.valueOf(value));
							} else if ("double".equals(fieldType) || "Double".equals(fieldType)) {
								clazz.getField(fieldName).set(obj, Double.valueOf(value));
							} else if ("long".equals(fieldType) || "Long".equals(fieldType)) {
								clazz.getField(fieldName).set(obj, Long.valueOf(value));
							} else {
								clazz.getField(fieldName).set(obj, value);
							}
						}
					}

				}
			}
		}catch (MessageException e){
			LOG.error("Message: ",e.getMessage());
		}
		return obj;
	}

	public boolean setObjectInfo(Object object) throws  Exception{
		boolean result = false;
		Class clazz = object.getClass();
		Field[] fields =  clazz.getDeclaredFields();
		String objOID = ((SNMPOID) clazz.getAnnotation(SNMPOID.class)).value();
		for (Field field : fields) {
			if (field.getAnnotation(SNMPOID.class) != null) {
				String fieldType = field.getType().getSimpleName();
				if ("List".equals(fieldType)){
					// do not set the table info
					continue;
				}else{
					String fieldName = field.getName();
					String oid = objOID+"."+field.getAnnotation(SNMPOID.class).value()+".0";
					String  value = null;
					fieldName = (get+fieldName.substring(0,1).toUpperCase()+
							fieldName.substring(1)).toString();

					Method method = clazz.getMethod(fieldName);
					Object va = method.invoke(object);
					if (va != null ){
						value = va.toString();
						if (setVariable(oid,value)){
							LOG.info("SET:"+clazz.getName()+"-> oid("+oid+")"+"value [" +value+"]");
							result = true;
							continue;
						}
					}

				}

			}
		}
		return result;
	}

	private OID getAuth_security_oid(int authAlgorithm) {
		OID auth_security_oid;
		//认证 OID
		auth_security_oid = AuthMD5.ID;
		if(AuthMD5.ID.toInt() == authAlgorithm){
			auth_security_oid = AuthMD5.ID;
		}else if(AuthSHA.ID.toInt() == authAlgorithm){
			auth_security_oid = AuthSHA.ID;
		}
		return auth_security_oid;
	}

	private OID getPriv_security_oid(int privcyAlgorithm) {
		OID priv_security_oid;
		//隐私 OID
		priv_security_oid = PrivDES.ID;
		if(Priv3DES.ID.toInt() == privcyAlgorithm){
			priv_security_oid = Priv3DES.ID;
		}else if(PrivAES128.ID.toInt() == privcyAlgorithm){
			priv_security_oid = PrivAES128.ID;
		}else if(PrivDES.ID.toInt() == privcyAlgorithm){
			priv_security_oid = PrivDES.ID;
		}else if(PrivAES192.ID.toInt() == privcyAlgorithm){
			priv_security_oid = PrivAES192.ID;
		}else if(PrivAES256.ID.toInt() == privcyAlgorithm){
			priv_security_oid = PrivAES256.ID;
		}
		return priv_security_oid;
	}
	public Mib getMIBInfo() throws Exception{
		Mib mib = new Mib();
		mib.setSystem((SystemInfo) getObjectInfo(SystemInfo.class));
		mib.setInterfaces((InterfacesInfo)getObjectInfo(InterfacesInfo.class));
		mib.setAt((AtInfo)getObjectInfo(AtInfo.class));
		mib.setIp((IpInfo)getObjectInfo(IpInfo.class));
		mib.setIcmp((IcmpInfo)getObjectInfo(IcmpInfo.class));
		mib.setTcp((TcpInfo)getObjectInfo(TcpInfo.class));
		mib.setUdp((UdpInfo)getObjectInfo(UdpInfo.class));
		mib.setEgp((EgpInfo)getObjectInfo(EgpInfo.class));
		mib.setSnmp((SnmpInfo) getObjectInfo(SnmpInfo.class));
		return mib;
	}

	public int getIfNumbers(){
		int count = 0;
		try {
			String var =  getVariable(".1.3.6.1.2.1.2.1.0").toString();
			if(Pattern.compile("^[0-9]*$").matcher(var).find()){
				count = Integer.parseInt(var);
			}
		}catch (Exception e){
			LOG.error("Get If count Info error {}" , e);
		}finally {
			return count;
		}
	}

	public List getInterfaceData(Class clazz)throws Exception{
		List list = new ArrayList<>();
		if(SNMPV!=SnmpConstants.version1){
			int type = PDU.GETBULK;
			TableUtils utils = new TableUtils(this.snmp,new DefaultPDUFactory(type));
			utils.setMaxNumRowsPerPDU(5);
			String tableOID = ((SNMPOID) clazz.getAnnotation(SNMPOID.class)).value();
			OID[] oids = new OID[]{new OID(tableOID)};
			List<TableEvent> events = utils.getTable(this.target,oids,null,null);
			String index = null;

			int lineIndex = 0;
			List<Integer> indexTwo = new ArrayList<>();
			for (TableEvent e : events) {
				if(e.getStatus() != -1 && e.getIndex() != null){
					String indexId = e.getIndex().toString();
					String trueIndex = indexId.substring(0,indexId.indexOf("."));
					if (index == null){
						index = trueIndex;
						indexTwo.add(e.getIndex().getValue()[1]);
						lineIndex++;
					}else{
						if (trueIndex != null && !trueIndex.equals(index)){
							break;
						}else{
							indexTwo.add(e.getIndex().getValue()[1]);
							lineIndex++;
						}
					}
				}

			}
			if (lineIndex == 0){
				return list;
			}
			//int rowIndex = events.size()/lineIndex;
			int rowIndex = events.get(events.size()-1).getIndex().getValue()[0];
		 /*
			make table to the array
		  */
			TableEvent[][] eventarr = null;
			int jj = 0,kk = 0,aa=0,ii=0;
			TableEvent event = null;
			try{
				eventarr = new TableEvent[rowIndex][lineIndex];
				for (int i = 0; i < events.size(); i++,kk++) {
					event = events.get(i);
					eventarr[event.getIndex().getValue()[0]-1][indexTwo.indexOf(event.getIndex().getValue()[1])]=event;
				}
				if (rowIndex*lineIndex != events.size()){
					LOG.error(" ERROR :" , this.target.getAddress());
				}
			}catch(Exception e){


				LOG.error("Get table Error {}",e);
			}
			/**
			 * reflect to object list;
			 */
			for (int i = 0; i < lineIndex; i++) {
				list.add(clazz.newInstance());
			}
			Field[] fields = clazz.getFields();//get attributes;

			for (int i = 0; i < eventarr.length; i++) {
				String fieldName = null;
				for (int j = 0; j < eventarr[i].length; j++) {
					for (Field field : fields) {
						String oid = ((SNMPOID)field.getAnnotation(SNMPOID.class)).value();
						if (eventarr[i][j]!= null && eventarr[i][j].getIndex() != null){
							String indexid = eventarr[i][j].getIndex().toString();
							String trueIndex = indexid.substring(0,indexid.indexOf("."));
							if (Integer.valueOf(trueIndex) == Integer.valueOf(oid)){
								fieldName = field.getName();
								continue;
							}
						}
					}
					if(fieldName != null){
						String fieldType = clazz.getField(fieldName).getType().getSimpleName();

						if (eventarr[i][j]!=null && eventarr[i][j].getColumns() != null){

							String fieldValue = eventarr[i][j].getColumns()[0].toValueString();
							if ("int".equals(fieldType)||"Integer".equals(fieldType)){
								clazz.getField(fieldName).set(list.get(j),Integer.valueOf(fieldValue));
							}else if ("float".equals(fieldType)||"Float".equals(fieldType)){
								clazz.getField(fieldName).set(list.get(j),Float.valueOf(fieldValue));
							}else if ("double".equals(fieldType)||"Double".equals(fieldType)){
								clazz.getField(fieldName).set(list.get(j),Double.valueOf(fieldValue));
							}else if ("long".equals(fieldType)||"Long".equals(fieldType)){
								clazz.getField(fieldName).set(list.get(j),Long.valueOf(fieldValue));
							}else{
								clazz.getField(fieldName).set(list.get(j),fieldValue);
							}
						}
					}
				}
			}
		}
		return list;
	}

}
