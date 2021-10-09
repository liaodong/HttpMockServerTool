package com.tester.httpServer.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class HttpMockServer implements Runnable {

	public static HttpHandler httpHandler = new HttpHandler();
	public static HttpContext httpContext = HttpHandler.httpContext;
	public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public void run() {
		boolean stopMe = httpContext.isStopMe();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		// System.out.println("进入run():" + df.format(new Date()));
		try {
			// 启动socket服务,5个线程,端口从httpContext获取
			int port = httpContext.getPort();
			ServerSocket svrSocket = CreateServerSocket(port, httpContext.isHTTPS(), new String[] {httpContext.getProtocol()});
			httpContext.setWorkingSocket(svrSocket);
			Socket socket = null;
			while (!stopMe) {
				try {
					socket = svrSocket.accept();
					byte[] buf = new byte[1024 * 1024];
					InputStream in = socket.getInputStream();
					int byteRead = in.read(buf, 0, 1024 * 1024);
					String dataString = new String(buf, 0, byteRead);
					dataString = new String(dataString.getBytes(), "utf-8");
					// System.out.println(dataString);
					String date = df.format(new Date());
					HttpHandler.setConsole(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\r\n" + "Request-Time: "
							+ date + "\r\n" + "-------------------------------------------\r\n" + dataString + "\r\n"
							+ "--------------------------------------------\r\n\r\n", 1);
					String requestHeaders = dataString.substring(0, dataString.indexOf("\r\n\r\n"));
					String[] data = requestHeaders.split("\r\n");
					String[] firstLine = data[0].split(" ");
					String requedtMethod = firstLine[0].toUpperCase();
					// 获取请求地址
					String requestUrl = firstLine[1];
					OutputStream out = socket.getOutputStream();
					OutputStreamWriter outSW = new OutputStreamWriter(out, "UTF-8");
					BufferedWriter bw = new BufferedWriter(outSW);
					String response = null;
					if (requedtMethod.equals("POST")) {
						// System.out.println(requestUrl + "进入POST循环");
						// 获取请求体数据
						String requestBody = dataString.substring(dataString.indexOf("\r\n\r\n") + 1);
						// 获取post请求结果
						// System.out.println("requestUrl=" + requestUrl);
						// System.out.println("requestBody= + requestBody);
						response = HttpHandler.doPost(requestUrl, requestBody);
					} else if (requedtMethod.equals("GET")) {
						// 获取get的请求内容
						response = HttpHandler.doGet(requestUrl);
					}
					String date2 = df.format(new Date());
					HttpHandler.setConsole("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + "\r\n" + "Response-Time: "
							+ date2 + "\r\n" + "-------------------------------------------\r\n" + response + "\r\n"
							+ "-------------------------------------------\r\n\r\n", 2);
					bw.write(response); // 向客户端反馈消息，加上分行符以便客户端接收
					bw.flush();
					bw.close();
					outSW.close();
					out.close();
					in.close();
					socket.close();
					stopMe = httpContext.isStopMe();
					if (stopMe) {
						break;
					}
				} catch (Exception e) {
					HttpHandler.setConsole(
							"-----------------Exception---------------------" + "\r\n" + e.getMessage() + "\r\n", -1);
				}
				stopMe = httpContext.isStopMe();
			}
			svrSocket.close();
		} catch (Exception e) {
			HttpHandler.setConsole("-----------------Exception---------------------" + "\r\n" + e.getMessage() + "\r\n",
					-1);
			httpContext.setStopMe(true);
		}
		
	}

	/*
	 * parameters: 1 port 监听端口 2 isHttps 是否开启https 3
	 */
	private ServerSocket CreateServerSocket(int serverListenPort, boolean isHttps, String[] protocols) throws Exception {
		int serverRequestQueueSize = 5;

		if (!isHttps) {
			ServerSocket serverSocket = new ServerSocket(serverListenPort, serverRequestQueueSize);
			return serverSocket;
		}

		SSLServerSocket sslServerSocket;
		SSLContext sslContext;
		SSLServerSocketFactory sslServerSocketFactory;

		int authority = 2;

		sslContext = Auth.getSSLContext();
		sslServerSocketFactory = sslContext.getServerSocketFactory();
		// Just create a TCP connection.SSL shake hand does not begin.
		// The first time either side(server or client) try to get a socket input stream
		// or output stream will case the SSL shake hand begin.
		sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket();
		String[] pwdsuits = sslServerSocket.getSupportedCipherSuites();
		sslServerSocket.setEnabledCipherSuites(pwdsuits);
		sslServerSocket.setEnabledProtocols(protocols);
		// Use client mode.Must prove its identity to the client side.
		// Client mode is the default mode.
		sslServerSocket.setUseClientMode(false);
		if (authority == 1) {
			// The communication will stop if the client side doesn't show its identity.
			sslServerSocket.setNeedClientAuth(true);
		} else {
			// The communication will go on although the client side doesn't show its
			// identity.
			sslServerSocket.setWantClientAuth(true);
		}

		sslServerSocket.setReuseAddress(true);
		sslServerSocket.setReceiveBufferSize(128 * 1024);
		sslServerSocket.setPerformancePreferences(3, 2, 1);
		sslServerSocket.bind(new InetSocketAddress(serverListenPort), serverRequestQueueSize);
		return sslServerSocket;
	}
}