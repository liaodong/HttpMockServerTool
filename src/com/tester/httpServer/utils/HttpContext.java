package com.tester.httpServer.utils;

import java.net.ServerSocket;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class HttpContext {
	private int port;
	private String protocol;
	private ServerSocket workingSocket;
	private boolean isHTTPS;
	private String requestUrl;
	private String requestMethod;
	private String[][] requestHeads;
	private String requestQuery;
	private String requestBody;
	private String responseCode;
	private String[][] responseHeads;
	private String responseBody;
	private String expectUrl;
	private String expectParams;
	private String expectMethod;
	private JTextArea areaOfConsole;
	private String[][] fileList;
	private boolean stopMe;

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String url) {
		if (url.substring(0, 1).equals("/")) {
			this.requestUrl = url;
		} else {
			this.requestUrl = ("/" + url);
		}
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestQuery() {
		return requestQuery;
	}

	public void setRequestQuery(String requestQuery) {
		this.requestQuery = requestQuery;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public JTextArea getAreaOfConsole() {
		return areaOfConsole;
	}

	public void setAreaOfConsole(JTextArea areaOfConsole) {
		this.areaOfConsole = areaOfConsole;
	}

	public String[][] getResponseHeads() {
		return responseHeads;
	}

	public void setResponseHeads(String[][] responseHeads) {
		this.responseHeads = responseHeads;
	}

	public String[][] getRequestHeads() {
		return requestHeads;
	}

	public void setRequestHeads(String[][] requestHeads) {
		this.requestHeads = requestHeads;
	}

	public String getExpectUrl() {
		return expectUrl;
	}

	public void setExpectUrl(String expectUrl) {
		this.expectUrl = expectUrl;
	}

	public String getExpectMethod() {
		return expectMethod;
	}

	public void setExpectMethod(String expectMethod) {
		this.expectMethod = expectMethod;
	}

	public String getExpectParams() {
		return expectParams;
	}

	public void setExpectParams(String expectParams) {
		this.expectParams = expectParams;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String[][] getFileList() {
		return fileList;
	}

	public void setFileList(String[][] fileList) {
		this.fileList = fileList;
	}

	public boolean isStopMe() {
		return stopMe;
	}

	public void setStopMe(boolean stopMe) {
		this.stopMe = stopMe;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isHTTPS() {
		return isHTTPS;
	}

	public void setHTTPS(boolean isHTTPS) {
		this.isHTTPS = isHTTPS;
	}

	public ServerSocket getWorkingSocket() {
		return workingSocket;
	}

	public void setWorkingSocket(ServerSocket workingSocket) {
		this.workingSocket = workingSocket;
	}
}
