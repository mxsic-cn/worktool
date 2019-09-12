package cn.mxsic.pingan.server;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server extends Thread{

	public static Logger logger = Logger.getLogger("Server.Class");
	ServerSocket mSocket = null;
	boolean mStopped = false;

	public Server()
	{
		logger.info("创建新的服务器");
		start();
	}

	public void run()
	{
		try
		{
			logger.info("即将创建服务端ServerSocket..");
			createSocket();
			logger.info("创建服务端ServerSocket成功.");
			while (! mStopped)
			{
				Socket currentSocket = mSocket.accept();
				logger.info("获得客户端连接");
				//处理客户端连接
				MessageHandler handler = new MessageHandler(currentSocket);
				handler.start();
			}
		}catch(Exception e)
		{
			System.out.println("创建服务器异常，退出.");
			e.printStackTrace();
		}
	}

	/**
	 * 创建server socket
	 */
	public void createSocket() throws Exception
	{
		logger.info("创建服务器端 ServerSocket...");
		Exception throwMe = null;
		boolean success = false;

		try {
			int port = 3001;     //设定端口号
			mSocket = new ServerSocket(port);
			success = true;

		} catch (Exception e) {
			throwMe = e;
		}

		if (! success)
			throw throwMe;

	}

	/**
	 * 停止服务
	 */
	public void stopServer() {
		mStopped = true;

		try {
			closeServerSocket();
		} catch (Exception e) {
			// ignore--we're stopping.
		}
	}

	/**
	 * 关闭 server socket.
	 */
	void closeServerSocket() throws java.io.IOException
	{
		if (mSocket != null)
		{
			mSocket.close();
		}
	}

	public static void main(String[] args)
	{
		Server server = new Server();
	}

}
