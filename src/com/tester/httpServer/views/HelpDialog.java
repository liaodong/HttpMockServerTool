package com.tester.httpServer.views;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelpDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public HelpDialog(JFrame owner) {
		super(owner, "帮助", true);
		add(new JLabel(
				"<html><p>使用前请仔细阅读工具使用指南：</p>"
						+ "<p>1、安装jdk1.6以上的版本；</p>"
						+ "<p>2、GUI包含三部分，端口设置，请求和响应文件路径设置，控制台；</p>"
						+ "<p>3、端口设置为需要监听的本地的端口，范围是1-65532；</p>"
						+ "<p>4、请求和响应文件路径设置，从名字上都很好理解，填写正确请求和响应文件路径即可；</p>"
						+ "<p>5、注意：当输入完成后，需要点击表格的其他行取消鼠标焦点，这样工具才能识别到当前的输入框被修改，OSX系统下复制粘贴的快捷键依然是ctr+c/v；</p>"
						+ "<p>6、控制台分为两部分，三个按钮和一个文本显示区域，按钮的作用从名称上都能理解，文本显示区域显示收到的HTTP请求的URL，消息体，右击有清空按钮；</p>"
						+ "<p>7、需要监听多个端口复制多个jar文件打开即可。</p></html>"), "Center");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		setSize(450, 280);
		setLocation(screenWidth * 3 / 8, screenHeight * 3 / 8);
		setResizable(false);
	}
}
