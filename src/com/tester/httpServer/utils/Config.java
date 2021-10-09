package com.tester.httpServer.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config {
	static Properties prop = null;
	
	public static void saveConfig() {
		try {
			prop.store(new FileOutputStream(System.getProperty("user.dir") + "\\"
						+ "HttpMockServerTool.ini"), "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String get(String key) {
		if( null == prop) {
			prop = new Properties();
			try {
				prop.load(new FileInputStream(System.getProperty("user.dir") + "\\"
						+ "HttpMockServerTool.ini"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return "";
			}
		}
		return prop.getProperty(key);
	}
	public static void set(String key, String value) {
		if( null == prop) {
			prop = new Properties();
		}
		if(null!=value) {
			prop.setProperty(key, value);
		}
	}
}
