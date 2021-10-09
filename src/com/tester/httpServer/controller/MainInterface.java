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

	// 主方法
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

	// 绘制窗体
	private void initFrame(int rate) {
		try {
			font = new Font("宋体",Font.PLAIN,15*rate);
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
		setTitle(fileName + "接口测试桩工具(v3.0)");
		
		this.addComponentListener(new ComponentAdapter() {//让窗口响应大小改变事件
			@Override
			public void componentResized(ComponentEvent e) {
				panelHeight=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			}
		});
	}

	// 添加主菜单
	private void initMenuBar(int rate) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(Box.createRigidArea(new Dimension(0, 25*rate)));
		setJMenuBar(menuBar);
		// 帮助按钮
		JMenu fileMenu = new JMenu("帮助");
		menuBar.add(fileMenu);
		JMenuItem helpItem = new JMenuItem("帮助");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpDialog dialog = new HelpDialog(MainInterface.this);
				dialog.setVisible(true);
			}
		});
		fileMenu.add(helpItem);
		// 关于按钮
		JMenu helpMenu = new JMenu("关于");
		menuBar.add(helpMenu);
		JMenuItem aboutItem = new JMenuItem("关于");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog dialog = new AboutDialog(MainInterface.this);
				dialog.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
	}

	// 初始化端口/服务器端类型/加密协议类型区域
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

	// 绘制端口/服务器端类型/加密协议类型区域
	private JPanel initPortJPanel() {
		JPanel portConfigPanel = new JPanel();
		portConfigPanel.setLayout(new BorderLayout());
		portConfigPanel.setBorder(BorderFactory
				.createTitledBorder("端口设置&服务端类型&加密协议类型"));
		portConfigPanel.setPreferredSize(new Dimension(panelWidth, 50*rate));

		JPanel settings = new JPanel();
		// 端口相关
		settings.setLayout(new GridLayout(1, 3));
		this.textOfPort = new JTextField();
		this.textOfPort.setText(Config.get("port"));
		settings.add(this.textOfPort);
		// 服务端类型相关
		// TODO 加上https相关请求
		String[] serverTypeStrings = { "HTTP" , "HTTPS" };
		this.comboBoxOfServerType = new JComboBox<Object>(serverTypeStrings);
		settings.add(this.comboBoxOfServerType);
		
		// 加密协议类型相关
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

	// URL和response文件路径设置区域
	private JPanel initConfigJPanel() {
		final JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		configPanel.setBorder(BorderFactory.createTitledBorder("请求和响应文件路径设置"));
		configPanel.setPreferredSize(new Dimension(panelWidth, 50*rate));
		// 表格绘制区域
		Object[][] urlsAndPaths = null;
		try {
			urlsAndPaths = initConfigTableData();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] columnName = { "请求和响应文件路径" };
		
		configPanel.add(new JLabel("请求和响应文件路径"));
		textOfFile = new JTextField();
		textOfFile.setText(Config.get("xlsx"));
		configPanel.add(textOfFile);
		
		JButton reloadButton = new JButton("载入") ;
		reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String file = showFileOpenDialog(configPanel,"xlsx");
				if(file.length()>0) {
					textOfFile.setText(file);
					HttpMockServer.httpHandler.readFile(file);
					HttpMockServer.httpHandler.setConsole("载入配置...\r\n", -1);
				}
			}
		});
		configPanel.add(reloadButton, BorderLayout.EAST);
		
		return configPanel;
	}

	// 控制台区域绘图
	private JPanel initConsoleJPanel() {
		JPanel consolePanel = new JPanel();
		consolePanel.setLayout(new BorderLayout());
		consolePanel.setBorder(BorderFactory.createTitledBorder("控制台"));
		consolePanel.setPreferredSize(new Dimension(panelWidth, panelHeight - 500));
		// 添加按钮
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 3));
		this.startButton = new JButton("启动");
		this.stopButton = new JButton("停止");
		this.rebootButton = new JButton("清除日志");
		buttons.add(this.startButton);
		buttons.add(this.stopButton);
		buttons.add(this.rebootButton);
		consolePanel.add(buttons, "North");
		// 添加输出屏区域属性
		this.areaOfConsole = new JTextArea();
		this.areaOfConsole.setEditable(false);
		this.areaOfConsole.setLineWrap(true);
		consolePanel.add(new JScrollPane(this.areaOfConsole));
		httpContext.setAreaOfConsole(this.areaOfConsole);
		
		final JPopupMenu clearConsoleMenu = new JPopupMenu();
		JMenuItem itemOfClearConsole = new JMenuItem("清空");
		clearConsoleMenu.add(itemOfClearConsole);
		// 添加控制按钮
		this.startButton.addActionListener(new StartListener());
		this.stopButton.addActionListener(new StopListener());
		this.stopButton.setEnabled(false);
		this.rebootButton.addActionListener(new RebootListener());
		itemOfClearConsole.addActionListener(new ClearListener());
		// 添加鼠标响应
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
	
	// HTTPS区域绘图
	private JPanel initSSLJPanel() {
		final JPanel sslPanel = new JPanel();
		sslPanel.setLayout(new GridLayout(2,1));
		sslPanel.setBorder(BorderFactory.createTitledBorder("HTTPS相关配置"));
		sslPanel.setPreferredSize(new Dimension(panelWidth, 80 * rate));
		// 添加按钮

		textOfCertFile = new JTextField();
		textOfCertPwd = new JTextField();
		textOfCertFile.setText(Config.get("certFile"));
		textOfCertPwd.setText(Config.get("certPwd"));
		JButton fileButton = new JButton("载入") ;
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
		certpanel.add(new JLabel("证书文件: "),BorderLayout.WEST);
		certpanel.add(textOfCertFile);
		certpanel.add(fileButton, BorderLayout.EAST);
		sslPanel.add(certpanel);
		JPanel pwdpanel=new JPanel();
		pwdpanel.setLayout(new BorderLayout());
		pwdpanel.add(new JLabel("证书密码: "),BorderLayout.WEST);
		pwdpanel.add(textOfCertPwd);
		sslPanel.add(pwdpanel);
		return sslPanel;
	}

	HttpContext httpContext = HttpHandler.httpContext;

	// 启动操作
	private void startListener() {
		// 将关闭标签设置为否
		httpContext.setStopMe(false);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
		// 添加线程
		t = new Thread(new HttpMockServer());
		// System.out.println("新建线程完成:" + df.format(new Date()));
		// 设置端口
		httpContext.setPort(Integer.valueOf(this.textOfPort.getText())
				.intValue());
		Config.set("port", this.textOfPort.getText());
		// 设置协议
		httpContext.setHTTPS(this.comboBoxOfServerType.getSelectedItem().equals("HTTPS"));
		httpContext.setProtocol((String)this.comboBoxOfProtocolType.getSelectedItem());
		Config.set("https", httpContext.isHTTPS()?"1":"0");
		Config.set("protocol", httpContext.getProtocol());
		
		// 设置响应读取文件地址
		String file = this.textOfFile.getText();
		if (!file.equals("")) {
			// System.out.println("开始读取xlsx:" + df.format(new Date()));
			// HttpHandler httpHandler = new HttpHandler();
			HttpMockServer.httpHandler.readFile(file);
			// System.out.println("xlsx读取完成:" + df.format(new Date()));
			HttpHandler.httpContext.setPort(httpContext.getPort());
			t.start();
			// System.out.println("线程启动完成:" + df.format(new Date()));

			Config.set("xlsx", file);
		}
		// 读取证书文件
		String cert = this.textOfCertFile.getText();
		if (!cert.equals("")) {
			Config.set("certFile", cert);
		}
		String pwd = this.textOfCertPwd.getText();
		if (!pwd.equals("")) {
			Config.set("certPwd", pwd);
		}
		
		

		if (this.comboBoxOfServerType.getSelectedItem().equals("HTTP")) {
			this.areaOfConsole.append("HTTP Server启动成功，端口号为"
					+ this.textOfPort.getText() + "。\r\n");
		} else {
			this.areaOfConsole.append("HTTPS Server启动成功，端口号为"
					+ this.textOfPort.getText() + "，" + "加密协议为"
					+ (String) this.comboBoxOfProtocolType.getSelectedItem()
					+ "。\r\n");
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
		this.areaOfConsole.append("接口服务停止。\r\n");
	}

	private void rebootListener() {
		//清除日志信息
//		stopListener();
//		startListener();
		this.areaOfConsole.setText("");
	}

	// 初始化配置文件对象
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
     * 打开文件
     */
    private static String showFileOpenDialog(Component parent, String filter) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(filter, filter));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter(filter, filter));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
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

	// 获取当前目录下jar包的名称
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
