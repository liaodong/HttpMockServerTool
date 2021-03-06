package com.tester.httpServer.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tester.httpServer.utils.Config;
import com.tester.httpServer.utils.FileUtils;
import com.tester.httpServer.utils.HttpContext;
import com.tester.httpServer.utils.HttpHandler;
import com.tester.httpServer.utils.HttpMockServer;
import com.tester.httpServer.views.AboutDialog;
import com.tester.httpServer.views.HelpDialog;

public class MainInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textOfPort;
	private JTextField textOfFile;
	private JButton startButton;
	private JButton stopButton;
	private JButton rebootButton;
	private JTextArea areaOfConsole;
	private JComboBox<Object> comboBoxOfServerType;
	private JComboBox<Object> comboBoxOfProtocolType;
	private JTextField textOfCertFile;
	private JTextField textOfCertPwd;
	
	private Thread t;
	
	private int rate= 2;
	private Font font;
	private int panelHeight=1600;
	private int panelWidth=1024;

	// ?????????
	public static void main(String[] args) {
		MainInterface mainInterface = new MainInterface();
		mainInterface.setDefaultCloseOperation(3);
		mainInterface.setVisible(true);
	}

	public MainInterface() {
		initFrame(rate);
		initMenuBar(rate);
		initLayout(rate);
	}

	// ????????????
	private void initFrame(int rate) {
		try {
			font = new Font("??????",Font.PLAIN,15*rate);
			UIManager.put("Button.font",font);
			UIManager.put("ToggleButton.font",font);
			UIManager.put("RadioButton.font",font);
			UIManager.put("CheckBox.font",font);
			UIManager.put("ColorChooser.font",font);
			UIManager.put("ToggleButton.font",font);
			UIManager.put("ComboBox.font",font);
			UIManager.put("ComboBoxItem.font",font);
			UIManager.put("InternalFrame.titleFont",font);
			UIManager.put("Label.font",font);
			UIManager.put("List.font",font);
			UIManager.put("MenuBar.font",font);
			UIManager.put("Menu.font",font);
			UIManager.put("MenuItem.font",font);
			UIManager.put("RadioButtonMenuItem.font",font);
			UIManager.put("CheckBoxMenuItem.font",font);
			UIManager.put("PopupMenu.font",font);
			UIManager.put("OptionPane.font",font);
			UIManager.put("Panel.font",font);
			UIManager.put("ProgressBar.font",font);
			UIManager.put("ScrollPane.font",font);
			UIManager.put("Viewport",font);
			UIManager.put("TabbedPane.font",font);
			UIManager.put("TableHeader.font",font);
			UIManager.put("TextField.font",font);
			UIManager.put("PasswordFiled.font",font);
			UIManager.put("TextArea.font",font);
			UIManager.put("TextPane.font",font);
			UIManager.put("EditorPane.font",font);
			UIManager.put("TitledBorder.font",font);
			UIManager.put("ToolBar.font",font);
			UIManager.put("ToolTip.font",font);
			UIManager.put("Tree.font",font);
			UIManager.put("Table.font",font);
			UIManager.put("ScrollPane.font",font);
			
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		
		setResizable(true);
		setSize(panelWidth+200, panelHeight);
		setLocation(screenWidth * 1 / 8, screenHeight * 1 / 8);
		String fileName = FileName();
		setTitle(fileName + "?????????????????????(v3.0)");
		
		this.addComponentListener(new ComponentAdapter() {//?????????????????????????????????
			@Override
			public void componentResized(ComponentEvent e) {
				panelHeight=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			}
		});
	}

	// ???????????????
	private void initMenuBar(int rate) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(Box.createRigidArea(new Dimension(0, 25*rate)));
		setJMenuBar(menuBar);
		// ????????????
		JMenu fileMenu = new JMenu("??????");
		menuBar.add(fileMenu);
		JMenuItem helpItem = new JMenuItem("??????");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpDialog dialog = new HelpDialog(MainInterface.this);
				dialog.setVisible(true);
			}
		});
		fileMenu.add(helpItem);
		// ????????????
		JMenu helpMenu = new JMenu("??????");
		menuBar.add(helpMenu);
		JMenuItem aboutItem = new JMenuItem("??????");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog dialog = new AboutDialog(MainInterface.this);
				dialog.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
	}

	// ???????????????/??????????????????/????????????????????????
	private void initLayout(int rate) {
		JPanel jpanel = new JPanel();
		add(jpanel);
		JPanel portConfigPanel = initPortJPanel();
		JPanel configPanel = initConfigJPanel();
		JPanel consolePanel = initConsoleJPanel();
		JPanel sslPanel= initSSLJPanel();
		jpanel.add(portConfigPanel,BorderLayout.NORTH);
		jpanel.add(configPanel,BorderLayout.CENTER);
		jpanel.add(sslPanel);
		jpanel.add(consolePanel);
	}

	// ????????????/??????????????????/????????????????????????
	private JPanel initPortJPanel() {
		JPanel portConfigPanel = new JPanel();
		portConfigPanel.setLayout(new BorderLayout());
		portConfigPanel.setBorder(BorderFactory
				.createTitledBorder("????????????&???????????????&??????????????????"));
		portConfigPanel.setPreferredSize(new Dimension(panelWidth, 50*rate));

		JPanel settings = new JPanel();
		// ????????????
		settings.setLayout(new GridLayout(1, 3));
		this.textOfPort = new JTextField();
		this.textOfPort.setText(Config.get("port"));
		settings.add(this.textOfPort);
		// ?????????????????????
		// TODO ??????https????????????
		String[] serverTypeStrings = { "HTTP" , "HTTPS" };
		this.comboBoxOfServerType = new JComboBox<Object>(serverTypeStrings);
		settings.add(this.comboBoxOfServerType);
		
		// ????????????????????????
		String[] protocolTypeStrings = { "SSL", "SSLv2", "SSLv3", "TLS",
				"TLSv1", "TLSv1.1", "TLSv1.2" };
		this.comboBoxOfProtocolType = new JComboBox<Object>(protocolTypeStrings);
		
		settings.add(this.comboBoxOfProtocolType);
		portConfigPanel.add(settings, "North");
		
		if("1".equals(Config.get("https"))) {
			comboBoxOfServerType.setSelectedIndex(1);
		} else {
			comboBoxOfProtocolType.setEnabled(false);
		}
		comboBoxOfProtocolType.setSelectedItem(Config.get("protocol"));
		
		comboBoxOfServerType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String)comboBoxOfServerType.getSelectedItem();
				if("HTTP".contentEquals(selected)) {
					comboBoxOfProtocolType.setEnabled(false);
				} else {
					comboBoxOfProtocolType.setEnabled(true);
				}
			}
		});
		return portConfigPanel;
	}

	// URL???response????????????????????????
	private JPanel initConfigJPanel() {
		final JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		configPanel.setBorder(BorderFactory.createTitledBorder("?????????????????????????????????"));
		configPanel.setPreferredSize(new Dimension(panelWidth, 50*rate));
		// ??????????????????
		Object[][] urlsAndPaths = null;
		try {
			urlsAndPaths = initConfigTableData();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] columnName = { "???????????????????????????" };
		
		configPanel.add(new JLabel("???????????????????????????"));
		textOfFile = new JTextField();
		textOfFile.setText(Config.get("xlsx"));
		configPanel.add(textOfFile);
		
		JButton reloadButton = new JButton("??????") ;
		reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String file = showFileOpenDialog(configPanel,"xlsx");
				if(file.length()>0) {
					textOfFile.setText(file);
					HttpMockServer.httpHandler.readFile(file);
					HttpMockServer.httpHandler.setConsole("????????????...\r\n", -1);
				}
			}
		});
		configPanel.add(reloadButton, BorderLayout.EAST);
		
		return configPanel;
	}

	// ?????????????????????
	private JPanel initConsoleJPanel() {
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		consolePanel.setBorder(BorderFactory.createTitledBorder("?????????"));
		consolePanel.setPreferredSize(new Dimension(panelWidth, panelHeight - 500));
		// ????????????
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 3));
		this.startButton = new JButton("??????");
		this.stopButton = new JButton("??????");
		this.rebootButton = new JButton("????????????");
		buttons.add(this.startButton);
		buttons.add(this.stopButton);
		buttons.add(this.rebootButton);
		consolePanel.add(buttons, "North");
		// ???????????????????????????
		this.areaOfConsole = new JTextArea();
		this.areaOfConsole.setEditable(false);
		this.areaOfConsole.setLineWrap(true);
		consolePanel.add(new JScrollPane(this.areaOfConsole));
		httpContext.setAreaOfConsole(this.areaOfConsole);
		
		final JPopupMenu clearConsoleMenu = new JPopupMenu();
		JMenuItem itemOfClearConsole = new JMenuItem("??????");
		clearConsoleMenu.add(itemOfClearConsole);
		// ??????????????????
		this.startButton.addActionListener(new StartListener());
		this.stopButton.addActionListener(new StopListener());
		this.stopButton.setEnabled(false);
		this.rebootButton.addActionListener(new RebootListener());
		itemOfClearConsole.addActionListener(new ClearListener());
		// ??????????????????
		this.areaOfConsole.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				triggerEvent(event);
			}

			public void mouseReleased(MouseEvent event) {
				triggerEvent(event);
			}

			private void triggerEvent(MouseEvent event) {
				if (event.isPopupTrigger()) {
					clearConsoleMenu.show(event.getComponent(), event.getX(),
							event.getY());
				}
			}
		});
		this.areaOfConsole.setComponentPopupMenu(clearConsoleMenu);

		return consolePanel;
	}
	
	// HTTPS????????????
	private JPanel initSSLJPanel() {
		final JPanel sslPanel = new JPanel();
		sslPanel.setLayout(new GridLayout(2,1));
		sslPanel.setBorder(BorderFactory.createTitledBorder("HTTPS????????????"));
		sslPanel.setPreferredSize(new Dimension(panelWidth, 80 * rate));
		// ????????????

		textOfCertFile = new JTextField();
		textOfCertPwd = new JTextField();
		textOfCertFile.setText(Config.get("certFile"));
		textOfCertPwd.setText(Config.get("certPwd"));
		JButton fileButton = new JButton("??????") ;
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String file = showFileOpenDialog(sslPanel, "p12");
				if(file.length()>0) {
					textOfCertFile.setText(file);
				}
			}
		});
		JPanel certpanel=new JPanel();
		certpanel.setLayout(new BorderLayout());
		certpanel.add(new JLabel("????????????: "),BorderLayout.WEST);
		certpanel.add(textOfCertFile);
		certpanel.add(fileButton, BorderLayout.EAST);
		sslPanel.add(certpanel);
		JPanel pwdpanel=new JPanel();
		pwdpanel.setLayout(new BorderLayout());
		pwdpanel.add(new JLabel("????????????: "),BorderLayout.WEST);
		pwdpanel.add(textOfCertPwd);
		sslPanel.add(pwdpanel);
		return sslPanel;
	}

	HttpContext httpContext = HttpHandler.httpContext;

	// ????????????
	private void startListener() {
		// ???????????????????????????
		httpContext.setStopMe(false);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		// ????????????
		t = new Thread(new HttpMockServer());
		// System.out.println("??????????????????:" + df.format(new Date()));
		// ????????????
		httpContext.setPort(Integer.valueOf(this.textOfPort.getText())
				.intValue());
		Config.set("port", this.textOfPort.getText());
		// ????????????
		httpContext.setHTTPS(this.comboBoxOfServerType.getSelectedItem().equals("HTTPS"));
		httpContext.setProtocol((String)this.comboBoxOfProtocolType.getSelectedItem());
		Config.set("https", httpContext.isHTTPS()?"1":"0");
		Config.set("protocol", httpContext.getProtocol());
		
		// ??????????????????????????????
		String file = this.textOfFile.getText();
		if (!file.equals("")) {
			// System.out.println("????????????xlsx:" + df.format(new Date()));
			// HttpHandler httpHandler = new HttpHandler();
			HttpMockServer.httpHandler.readFile(file);
			// System.out.println("xlsx????????????:" + df.format(new Date()));
			HttpHandler.httpContext.setPort(httpContext.getPort());
			t.start();
			// System.out.println("??????????????????:" + df.format(new Date()));

			Config.set("xlsx", file);
		}
		// ??????????????????
		String cert = this.textOfCertFile.getText();
		if (!cert.equals("")) {
			Config.set("certFile", cert);
		}
		String pwd = this.textOfCertPwd.getText();
		if (!pwd.equals("")) {
			Config.set("certPwd", pwd);
		}
		
		

		if (this.comboBoxOfServerType.getSelectedItem().equals("HTTP")) {
			this.areaOfConsole.append("HTTP Server???????????????????????????"
					+ this.textOfPort.getText() + "???\r\n");
		} else {
			this.areaOfConsole.append("HTTPS Server???????????????????????????"
					+ this.textOfPort.getText() + "???" + "???????????????"
					+ (String) this.comboBoxOfProtocolType.getSelectedItem()
					+ "???\r\n");
		}
		Config.saveConfig();
	}

	private void stopListener() {
		try {
			httpContext.setStopMe(true);
			httpContext.getWorkingSocket().close();
//			t.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.areaOfConsole.append("?????????????????????\r\n");
	}

	private void rebootListener() {
		//??????????????????
//		stopListener();
//		startListener();
		this.areaOfConsole.setText("");
	}

	// ???????????????????????????
	private Object[][] initConfigTableData()
			throws UnsupportedEncodingException {
		Object[][] urlsAndPaths = new Object[1][1];
		ArrayList<String> configLines = null;
		String fileName = System.getProperty("user.dir") + "\\"
				+ "HttpMockServerTool.config";
		configLines = FileUtils.readConfigFile(fileName);
		if (configLines != null) {
			int sizeOfTable = configLines.size();
			if (sizeOfTable > 9) {
				sizeOfTable = 9;
			}
			for (int i = 0; i < sizeOfTable; i++) {
				String configLine = (String) configLines.get(i);
				configLine = new String(configLine.getBytes(), "utf-8");
				String[] urlAndPath = configLine.split(",");
				urlsAndPaths[0][0] = urlAndPath[0];
			}
		}
		return urlsAndPaths;
	}

	/*
     * ????????????
     */
    private static String showFileOpenDialog(Component parent, String filter) {
        // ????????????????????????????????????
        JFileChooser fileChooser = new JFileChooser();

        // ????????????????????????????????????????????????
        fileChooser.setCurrentDirectory(new File("."));

        // ??????????????????????????????????????????????????????????????????????????????????????????
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // ????????????????????????
        fileChooser.setMultiSelectionEnabled(true);

        // ?????????????????????????????????FileNameExtensionFilter ???????????????????????????, ??????????????????????????????????????? ???????????????
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(filter, filter));
        // ????????????????????????????????????
        fileChooser.setFileFilter(new FileNameExtensionFilter(filter, filter));

        // ??????????????????????????????????????????, ???????????????????????????
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // ???????????????"??????", ??????????????????????????????
            File file = fileChooser.getSelectedFile();

            // ??????????????????????????????, ????????????????????????????????????????????????
            // File[] files = fileChooser.getSelectedFiles();

            return file.getAbsolutePath();
        }
        return "";
    }
    
	private class StartListener implements ActionListener {
		private StartListener() {
		}

		public void actionPerformed(ActionEvent e) {
			MainInterface.this.startListener();
			MainInterface.this.startButton.setEnabled(false);
			MainInterface.this.stopButton.setEnabled(true);
		}
	}

	private class StopListener implements ActionListener {
		private StopListener() {
		}

		public void actionPerformed(ActionEvent e) {
			MainInterface.this.stopListener();
			MainInterface.this.startButton.setEnabled(true);
			MainInterface.this.stopButton.setEnabled(false);
		}
	}

	private class RebootListener implements ActionListener {
		private RebootListener() {
		}

		public void actionPerformed(ActionEvent e) {
			MainInterface.this.rebootListener();
		}
	}

	private class ClearListener implements ActionListener {
		private ClearListener() {
		}

		public void actionPerformed(ActionEvent e) {
			MainInterface.this.areaOfConsole.setText("");
		}
	}

	// ?????????????????????jar????????????
	private static String FileName() {
		String fileName = "";
		File file = new File("./");
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			String tmpName = fileList[i].getName();
			if (fileList[i].isFile()) {
				if (tmpName.substring(tmpName.indexOf(".")).equals(".jar")) {
					fileName = tmpName.substring(0, tmpName.indexOf("."))
							+ " - ";
				}
			}
		}
		return fileName;
	}
}
