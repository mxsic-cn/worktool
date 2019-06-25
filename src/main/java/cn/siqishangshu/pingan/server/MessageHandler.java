package cn.siqishangshu.pingan.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class MessageHandler extends Thread{
	public static Logger logger = Logger.getLogger("MessageHandler.Class");
	Socket mSocket = null;
	boolean mStopped = false;
	BufferedWriter mWriter = null;
	BufferedReader mReader = null;

	/**
	 * 创建新的消息处理器
	 * @param pSocket
	 */
	public MessageHandler(Socket pSocket)
	{
		logger.info("创建新的消息处理器");
		mSocket = pSocket;
	}

	//打开并监听socket
	public void run()
	{
		char tagChar[];
		tagChar = new char[1024];
		int len;
		String temp;
		String rev = "";
		try {
			while (! mStopped && ! mSocket.isClosed()) {
				logger.info("处理消息...");
				mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
				mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));

				if ((len = mReader.read(tagChar)) != -1) {
					temp = new String(tagChar, 0, len);
					rev += temp;
					temp = null; }
				handleMessage(rev,mWriter);
				closeSocket();
			}
		} catch (Exception e) {
			System.out.println("error in MessageHandler -- closing down.");
			e.printStackTrace();
		}

	}

	/**
	 * 处理接收到的消息
	 * @param pMessage
	 * @throws java.io.IOException
	 */
	public void handleMessage(String pMessage, BufferedWriter bwriter) throws java.io.IOException
	{
		//todo:此处填写消息处理代码
		System.out.println(pMessage);

		//以【1301】会员开销户接口为例
		HashMap retKeyDict = new HashMap();//存放解析出来的请求报文的参数
		HashMap parmaKeyDict = new HashMap();//存放准备返回的报文参数
		BankInterface msg=new BankInterface();
		retKeyDict= msg.parsingTranMessageString(pMessage);

		//读取交易码
		String TranFunc=(String)retKeyDict.get("TranFunc");

		//读取报文体内容
		String FuncFlag=(String)retKeyDict.get("FuncFlag");//功能标志
		String SupAcctId=(String)retKeyDict.get("SupAcctId");//资金汇总账户
		String CustAcctId=(String)retKeyDict.get("CustAcctId");//子账户
		String CustName=(String)retKeyDict.get("CustName");//子账户名称
		String ThirdCustId=(String)retKeyDict.get("ThirdCustId");//会员代码
		String IdType=(String)retKeyDict.get("IdType");//证件类型
		String IdCode=(String)retKeyDict.get("IdCode");//证件号码
		String CustFlag=(String)retKeyDict.get("CustFlag");//子账户类别
		String TotalAmount=(String)retKeyDict.get("TotalAmount");//总金额
		String TotalBalance=(String)retKeyDict.get("TotalBalance");//可用金额
		String TotalFreezeAmount=(String)retKeyDict.get("TotalFreezeAmount");//冻结金额
		String Reserve=(String)retKeyDict.get("Reserve");//保留域

		System.out.println("ThirdCustId:" + ThirdCustId);
		System.out.println("IdType:" + IdType);
		System.out.println("IdCode:" + IdCode);

		//组返回报文

        /*生成随机数:当前精确到秒的时间再加6位的数字随机序列*/
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String rdNum=df.format(new Date());
		Random random = new Random();
		int ird = random.nextInt(999999);
		String srd= String.format("%06d", ird);
		String thirdLogNo = rdNum + srd;

        /*报文参数赋值*/
		parmaKeyDict.put("RspCode", "000000");//应答码
		parmaKeyDict.put("RspMsg", "交易成功");//应答描述
		parmaKeyDict.put("TranFunc", TranFunc);//交易码
		parmaKeyDict.put("Qydm", "8545");
		parmaKeyDict.put("ThirdLogNo", thirdLogNo); //返回流水号
		parmaKeyDict.put("Reserve", "保留域");

		String back = msg.getReturnTranMessage(parmaKeyDict);
		System.out.println("back:" + back);
		bwriter.write(back +"\r\n");
		bwriter.flush();
		bwriter.close();
	}
	/**
	 * 关闭socket.
	 */
	public void closeSocket() throws java.io.IOException
	{
		mSocket.close();
	}

	/**
	 * 停止处理器.
	 */
	void stopHandler()
	{
		mStopped = true;

		try {
			closeSocket();
		} catch (Exception e) {
			// ignore--we're stopping.
		}
	}
}
