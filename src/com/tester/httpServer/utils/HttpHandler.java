package com.tester.httpServer.utils;

import java.awt.Color;
import java.awt.Font;

public class HttpHandler {

	public static HttpContext httpContext = new HttpContext();

	public void readFile(String file) {
		// System.out.println(file);
//		if (file.indexOf("/") == -1) {
//			file = System.getProperty("user.dir") + "\\" + file;
//		}
		// System.out.println(file);
		HttpHandler.httpContext.setFileList(FileUtils.xlsTables(file));
	};

	public static String doGet(String url) {
		String responseStr = "";
		String acutreUrl = "";
		String acutreQuery = "";
		if(url.indexOf("?")!=-1) {
			acutreUrl = url.substring(0, url.indexOf("?"));
			acutreQuery = url.substring(url.indexOf("?") + 1);
		} else {
			acutreUrl = url;
			acutreQuery = "";
		}
		// System.out.println("acutreUrl=" + acutreUrl);
		// System.out.println("acutreQuery=" + acutreQuery);
		httpContext.setRequestMethod("GET");
		httpContext.setRequestUrl(acutreUrl);
		httpContext.setRequestQuery(acutreQuery);
		// HttpHandler.httpContext.getAreaOfConsole().append(
		// httpContext.getRequestMethod() + "\r\n" + url + "\r\n"
		// + httpContext.getRequestQuery() + "\r\n\r\n");
		boolean paramsmatched = false;
		String[][] list = HttpHandler.httpContext.getFileList();
		for (int i = 0; i < list.length; i++) {
			// System.out.println("进入handlerfor循环:" + i);
			// 预期请求方式,excel中第二列
			String expectMethod = list[i][1].toUpperCase();
			// 预期请求地址,excel中第三列
			String expectUrl = list[i][2];
			if (expectUrl.equals(acutreUrl) && expectMethod.equals("GET")) {
				httpContext.setExpectUrl(expectUrl);
				httpContext.setExpectMethod(expectMethod);
				// 请求包含内容,excel中第四列
				String expectParams = list[i][3];
				String[] params = expectParams.split(",");
				for (int j = 0; j < params.length; j++) {
					// 只对实际请求中包含预期参数做处理,
					// 实际请求中缺失预期参数的不做处理
					if (acutreQuery.indexOf(params[j]) == -1) {
						paramsmatched = false;
					} else {
						paramsmatched = true;
						break;
					}
				}
				if (paramsmatched) {
					// 正常响应
					httpContext.setExpectParams(expectParams);
					String responseCode = list[i][4];
					String responseHead = list[i][5];
					String responseBody = list[i][6];
					if (responseHead.equals("")) {
						responseHead += "HTTP/1.1 " + responseCode + " OK\r\n";
						responseHead += "Connection: keep-alive" + "\r\n";
						responseHead += "Content-Type: text/html;charset=UTF-8";
					}
					// System.out.println("if后" + responseHead);
					responseStr += responseHead;
					responseStr += "\r\n\r\n";
					responseStr += responseBody;
					break;
				}
			}
		}
		if (!paramsmatched) {
			responseStr = "HTTP/1.1 404 NOT FOUND\r\n"
					+ "content-type: text/html; charset=utf-8\r\n\r\n"
					+ "GET请求中，参数错误或不存在，请检查请求和xlsx中信息";
		}
		return responseStr;
	}

	public static String doPost(String url, String body) {
		String responseStr = "";
		// System.out.println(body);
		String acutreUrl = null;
		if (url.indexOf("?") == -1) {
			acutreUrl = url;
		} else {
			acutreUrl = url.substring(0, url.indexOf("?"));
		}
		// System.out.println(acutreUrl);

		httpContext.setRequestMethod("POST");
		httpContext.setRequestUrl(acutreUrl);
		httpContext.setRequestBody(body);
		// HttpHandler.httpContext.getAreaOfConsole().append(
		// httpContext.getRequestMethod() + "\r\n"
		// + httpContext.getRequestUrl() + "\r\n"
		// + httpContext.getRequestBody() + "\r\n\r\n");
		boolean paramsmatched = false;
		String[][] list = HttpHandler.httpContext.getFileList();
		// System.out.println(list);
		for (int i = 0; i < list.length; i++) {
			// System.out.println("进入handlerfor循环:" + i);
			// 预期请求方式,excel中第二列
			String expectMethod = list[i][1].toUpperCase();
			// 预期请求地址,excel中第三列
			String expectUrl = list[i][2];
			if (expectUrl.equals(acutreUrl) && expectMethod.equals("POST")) {
				// System.out.println("url匹配行:" + i);
				httpContext.setExpectUrl(expectUrl);
				httpContext.setExpectMethod(expectMethod);
				// 请求包含内容,excel中第四列
				String expectParams = list[i][3];
				String[] params = expectParams.split(",");
				for (int j = 0; j < params.length; j++) {
					// 只对实际请求中包含预期参数做处理,
					// 实际请求中缺失预期参数的不做处理
					if (body.indexOf(params[j]) == -1) {
						paramsmatched = false;
					} else {
						paramsmatched = true;
						break;
					}
				}
				if (paramsmatched) {
					// 正常响应
					httpContext.setExpectParams(expectParams);
					String responseCode = list[i][4];
					String responseHead = list[i][5];
					String responseBody = list[i][6];
					if (responseHead.equals("")) {
						responseHead += "HTTP/1.1 " + responseCode + " OK\r\n";
						responseHead += "Connection: keep-alive" + "\r\n";
						responseHead += "Content-Type: application/json;charset=UTF-8";
					}
					responseStr += responseHead;
					responseStr += "\r\n\r\n";
					responseStr += responseBody;
					break;
				}
			}
		}
		if (!paramsmatched) {
			responseStr = "HTTP/1.1 404 NOT FOUND\r\n"
					+ "content-type: text/html; charset=utf-8\r\n" + "\r\n"
					+ "POST请求中，参数错误或不存在，请检查请求和xlsx中信息";
		}
		return responseStr;
	}
	public static void setConsole(String message, int type) {
		httpContext.getAreaOfConsole().append(message);
		httpContext.getAreaOfConsole().setCaretPosition(
				httpContext.getAreaOfConsole().getDocument().getLength()); 
	}
}
