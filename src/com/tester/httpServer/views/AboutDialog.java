package com.tester.httpServer.views;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	public AboutDialog(JFrame owner) {
		super(owner, "关于", true);
		add(new JLabel("<html><p>Writen By Kevin Du</p><br/>"
				+ "<p>Email:dldsryx@126.com</p><br/>"
				+ "<p>Updated By East Fox</p><br/>"
				+ "<p>Email:eastfox@sina.com</p></html>"), "Center");

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;

		setSize(170, 150);
		setLocation(screenWidth * 7 / 16, screenHeight * 7 / 16);
		setResizable(false);
	}
}
