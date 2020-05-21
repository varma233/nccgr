//package sms;
//
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.HttpURLConnection;
//import java.io.OutputStream;
//import java.io.InputStream;
//import java.io.BufferedInputStream;
//
//public class SMS {
//	public static String send(String json) throws Exception {
//		final URL url = new URL(System.getenv("TILL_URL"));
//		final URLConnection conn = url.openConnection();
//
//		HttpURLConnection http = null;
//		try {
//			http = (HttpURLConnection) conn;
//			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//			http.setRequestProperty("Content-Length", "" + json.getBytes().length);
//			http.setRequestMethod("POST");
//			http.setRequestProperty("User-Agent", "Mozilla/5.0");
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			http.setUseCaches(false);
//
//			OutputStream os = null;
//			try {
//				os = http.getOutputStream();
//				os.write(json.getBytes("UTF-8"));
//				os.flush();
//			} finally {
//				if (os != null) {
//					os.close();
//				}
//			}
//
//			InputStream in = null;
//			try {
//				in = new BufferedInputStream(conn.getInputStream());
//				byte[] contents = new byte[1024];
//				int bytesRead = 0;
//				StringBuffer outStr = new StringBuffer();
//				while ((bytesRead = in.read(contents)) != -1) {
//					outStr.append(new String(contents, 0, bytesRead));
//				}
//				return outStr.toString();
//			} finally {
//				if (in != null) {
//					in.close();
//				}
//			}
//		} finally {
//			if (http != null) {
//				http.disconnect();
//			}
//		}
//	}
//
//}
