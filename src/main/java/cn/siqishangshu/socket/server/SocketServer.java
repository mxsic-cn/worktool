import cn.siqishangshu.socket.server.Server;
import cn.siqishangshu.socket.server.ServerThread;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class SocketServer {

	private static final Logger logger = Logger.getLogger(SocketServer.class);

	public void connect() {
		try {
			while (true) {
				Socket socket = Server.server.accept();
//                socket.getRemoteSocketAddress();
				new Thread(new ServerThread(socket)).start();
			}
		} catch (IOException e) {
			logger.error("SocketServer set up error: ", e);
		}

	}

	public static void main(String[] args) {
		SocketServer ss = new SocketServer();
		ss.connect();
	}

}