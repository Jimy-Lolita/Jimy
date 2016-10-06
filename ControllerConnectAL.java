package RemoteMonitorV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class ControllerConnectAL implements ActionListener {
	private JTextField ipJTextField;// ip地址输入框
	private JTextField portJTextField;// 端口号输入框

	public ControllerConnectAL(JTextField ipJTextField, JTextField portJTextField) {
		this.ipJTextField = ipJTextField;
		this.portJTextField = portJTextField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String romoteIP = ipJTextField.getText();
		String sPort = portJTextField.getText();
		int port = Integer.parseInt(sPort);
		//开启连接被控端线程
		ControllerThread cct = new ControllerThread(romoteIP,port);
		new Thread(cct).start();
	}

}
