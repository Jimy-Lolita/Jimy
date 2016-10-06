package RemoteMonitorV2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 被控端主界面
 * 
 * @author YangKang
 * 
 */
public class RemoteControlled extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font font = new Font("微软雅黑", Font.PLAIN, 14);

	public static void main(String[] args) {
		RemoteControlled rc = new RemoteControlled();
		rc.initGUI();
	}

	/**
	 * 初始化界面
	 */
	public void initGUI() {
		this.setTitle("Shadow Walker--\"指尖上的遥控\" 受控端");
		this.setSize(new Dimension(380, 295));
		this.setDefaultCloseOperation(3);// 点击关闭窗口的形式为点击关闭为关闭所有窗口
		this.setResizable(false);// 设置禁止调整窗体的大小
		this.setLocationRelativeTo(null);// 设置窗体显示在屏幕的中央

		/******************** 窗体背景 *************************/
		// 实例化一个图标对象
		ImageIcon backgroudImage = new ImageIcon("RMimages/bg.jpg");
		// 设置图片的大小
		backgroudImage.setImage(backgroudImage.getImage().getScaledInstance(
				this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT));
		// 实例化一个标签对象，让标签显示图标对象
		JLabel bgJLabel = new JLabel(backgroudImage);
		// 设置标签的起始位置和大小
		// backgroudJla.setBounds(0,0,backgroudImage.getIconWidth(),backgroudImage.getIconHeight());
		bgJLabel.setBounds(0, 0, this.getWidth(), this.getHeight());

		// 添加到窗体的LayeredPanel面板的最底层（第二层面板）
		this.getLayeredPane().add(bgJLabel, new Integer(Integer.MIN_VALUE));

		// 获取窗体的第一层面板
		JPanel contentPanel = (JPanel) this.getContentPane();
		// 设置第一层面板为透明
		contentPanel.setOpaque(false);
		/************************************************/

		/****************** 北边的面板 **********************/
		JPanel northJPanel = new JPanel();
		northJPanel.setOpaque(false);// 设置面板为透明
		northJPanel.setPreferredSize(new Dimension(0, 90));
		// 设置面板的布局方式为边框布局，JPanel默认布局方式是流式布局
		// 实例化一个JLabel的对象，并且让标签上显示图标
		JLabel title = new JLabel("\"指尖上的遥控\"V1.0 --受控端");
		title.setFont(new Font("楷体", Font.BOLD, 24));

		JLabel title2 = new JLabel("             --By Shadow Walker",
				JLabel.RIGHT);
		title2.setFont(new Font("楷体", Font.BOLD, 18));
		northJPanel.add(title);
		northJPanel.add(title2);
		northJPanel.setOpaque(false);
		this.add(northJPanel, BorderLayout.NORTH);
		/**************************************************/

		/*********************** 中间的面板 ******************/

		JPanel centerJPanel = new JPanel();
		centerJPanel.setOpaque(false);// 设置面板为透明
		// 设置面板的布局方式为流式布局,靠左侧显示组件，JPanel默认的布局方式是流式布局
		centerJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));

		// 添加端口号输入提示标签
		JLabel portJLabel = new JLabel("   开启端口号", JLabel.CENTER);
		portJLabel.setFont(font);
		centerJPanel.add(portJLabel);

		JTextField portJTextField = new JTextField();
		// 设置组件的大小
		portJTextField.setPreferredSize(new Dimension(200, 30));
		portJTextField.setText("8023");
		portJTextField.setFont(font);
		centerJPanel.add(portJTextField);

		// 添加被控制端IP输入框提示标签
		JLabel pwdJLabel = new JLabel("设置监控密码", JLabel.CENTER);
		pwdJLabel.setFont(font);
		centerJPanel.add(pwdJLabel);

		// 添加控制密码输入框
		JPasswordField pwdField = new JPasswordField();
		// 设置组件的大小
		pwdField.setPreferredSize(new Dimension(200, 30));
		pwdField.setFont(font);
		centerJPanel.add(pwdField);

		// 将centerJPanel添加到this窗体的中间
		this.add(centerJPanel, BorderLayout.CENTER);
		/**************************************************/

		/*********************** 南边的面板 ******************/
		JPanel southJPanel = new JPanel();
		southJPanel.setOpaque(true);
		// 添加连接按钮
		JButton connJButton = new JButton("开            	启");
		connJButton.setPreferredSize(new Dimension(155, 30));
		southJPanel.add(connJButton);
		southJPanel.setPreferredSize(new Dimension(0, 70));
		this.add(southJPanel, BorderLayout.SOUTH);
		/**************************************************/

		// 给开启按钮添加事件监听器
		ControlledConnAL sal = new ControlledConnAL(portJTextField);
		connJButton.addActionListener(sal);

		this.setVisible(true);
	}

}
