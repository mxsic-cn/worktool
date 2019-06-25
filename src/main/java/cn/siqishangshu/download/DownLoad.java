package cn.siqishangshu.download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoad {
	public static void main(String[] args) {
		DownLoad pic = new DownLoad();

		String photoUrl = "http://www.guofenchaxun.com/captchaa.php";
//		String urlTwo = "http://www.guofenchaxun.com/captchaa.php?0.590208588866517";
		String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));

		String filePath = "/Users/siqishangshu/Downloads/guofenpic";

		boolean flag = pic.saveUrlAs(photoUrl, filePath + fileName);

//		System.out.println("Run ok!\n Get URL file " + flag);
//		System.out.println(filePath);
//		System.out.println(fileName);
	}

	public static boolean saveUrlAs(String fileUrl, String savePath) {

		try {
			URL url = new URL(fileUrl);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());

			DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));

			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
				System.out.println(buffer);
			}
			out.close();
			in.close();
			connection.disconnect();
			return true;

		} catch (Exception e) {
			System.out.println(e + fileUrl + savePath);
			return false;
		}
	}
}
