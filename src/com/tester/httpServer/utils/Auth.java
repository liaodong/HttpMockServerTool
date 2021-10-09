package com.tester.httpServer.utils;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.Properties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;


public class Auth {
	private static SSLContext sslContext;
	
	public static SSLContext getSSLContext() throws Exception{
		
		String sCertificatePwd=Config.get("certPwd");
		String sCertificateFile=Config.get("certFile");
		String cCertificatePwd=Config.get("certPwd");
		String cCertificateFile=Config.get("certFile");
		String protocol=Config.get("protocol");
		
		//KeyStore class is used to save certificate.
		char[] s_pwd = sCertificatePwd.toCharArray();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");  
		keyStore.load(new FileInputStream(sCertificateFile), s_pwd);  
		
		//KeyManagerFactory class is used to create KeyManager class.
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509"); 
		keyManagerFactory.init(keyStore, s_pwd); 
		//KeyManager class is used to choose a certificate 
		//to prove the identity of the server side. 
		KeyManager[] kms = keyManagerFactory.getKeyManagers();
		
		TrustManager[] tms = null;
		
		//KeyStore class is used to save certificate.
		char[] c_pwd = cCertificatePwd.toCharArray();
		keyStore = KeyStore.getInstance("PKCS12");  
		keyStore.load(new FileInputStream(cCertificateFile), c_pwd);  
		
		//TrustManagerFactory class is used to create TrustManager class.
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509"); 
		trustManagerFactory.init(keyStore); 
		//TrustManager class is used to decide weather to trust the certificate 
		//or not. 
		tms = trustManagerFactory.getTrustManagers();
		
		//SSLContext class is used to set all the properties about secure communication.
		//Such as protocol type and so on.
		sslContext = SSLContext.getInstance(protocol);
		sslContext.init(kms, /*tms*/ null, null);  
		
		return sslContext;
	}
	
	public static void main(String args[]) throws Exception {
		Auth.getSSLContext();
	}
	
}