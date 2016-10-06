package RemoteMonitorV2;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 被控端发来的鼠标，键盘事件对象
 * 
 * @author YangKang
 * 
 */
public class RemoteRobotThread extends Thread {

	private ObjectInputStream oins = null;// 对象输入流
	private Robot robot;// 机器人对象
	private boolean isRun = true;// 连接标志

	/**
	 * 构造方法
	 * 
	 * @param s
	 */
	public RemoteRobotThread(Socket s) {
		try {
			robot = new Robot();
			oins = new ObjectInputStream(s.getInputStream());
		} catch (AWTException e) {
			LogTool.ERROR("被控端发送事件线程类中线程Robot出错：" + e.getMessage());
		} catch (IOException e) {
			LogTool.ERROR("被控端发送事件线程类中线程IO出错：" + e.getMessage());
		}
	}

	/**
	 * 在线程中读取控制端发来的事件对象并对之处理
	 */
	@Override
	public void run() {
		try {
			while (isRun) {
				InputEvent ine = (InputEvent) oins.readObject();
				if (ine != null) {
					handleEvent(ine);
				}
			}
		} catch (Exception e) {
			LogTool.ERROR("被控端发送事件线程类中线程IO出错：" + e.getMessage());
		}
	}

	/**
	 * 处理控制端发送来的事件对象，调用Robot对象来执行相应的操作
	 * 
	 * @param event
	 *            网络收到的控制端事件对象
	 */
	private void handleEvent(InputEvent event) {
		MouseEvent mouseEvent = null;
		MouseWheelEvent mouseWheelEvent = null;
		KeyEvent keyEvent = null;

		int mouseButtonMask = -1;
		switch (event.getID()) {
		case MouseEvent.MOUSE_MOVED:// 鼠标移动事件
			mouseEvent = (MouseEvent) event;
			robot.mouseMove((int) (mouseEvent.getX()),
					(int) (mouseEvent.getY()));
			break;

		case MouseEvent.MOUSE_PRESSED:// 鼠标按键按下事件
			mouseEvent = (MouseEvent) event;
			mouseButtonMask = getButtonMask(mouseEvent.getButton());// 获取按键标志
			robot.mousePress(mouseButtonMask);
			break;

		case MouseEvent.MOUSE_RELEASED:// 鼠标按键释放事件
			mouseEvent = (MouseEvent) event;
			mouseButtonMask = getButtonMask(mouseEvent.getButton());// 获取按键标志
			robot.mouseRelease(mouseButtonMask);
			break;

		case MouseEvent.MOUSE_WHEEL:// 鼠标中键滚动事件
			mouseWheelEvent = (MouseWheelEvent) event;
			robot.mouseWheel(mouseWheelEvent.getWheelRotation());
			break;

		case MouseEvent.MOUSE_DRAGGED:// 鼠标拖拽事件
			mouseEvent = (MouseEvent) event;
			robot.mouseMove((int) (mouseEvent.getX()),
					(int) (mouseEvent.getY()));
			break;

		case KeyEvent.KEY_PRESSED:// 键盘按键按下事件
			keyEvent = (KeyEvent) event;
			robot.keyPress(keyEvent.getKeyCode());
			break;

		case KeyEvent.KEY_RELEASED:// 键盘按键释放事件
			keyEvent = (KeyEvent) event;
			robot.keyRelease(keyEvent.getKeyCode());
			break;

		default:
			LogTool.INFO("unknown event" + event.getID());
			break;
		}
	}

	/**
	 * 根据发送的Mouse事件对象，转变为通用的Mouse按键代码
	 * 
	 * @param button
	 * @return
	 */
	private int getButtonMask(int button) {
		if (button == MouseEvent.BUTTON1) {
			return InputEvent.BUTTON1_MASK;
		}
		if (button == MouseEvent.BUTTON2) {
			return InputEvent.BUTTON2_MASK;
		}
		if (button == MouseEvent.BUTTON3) {
			return InputEvent.BUTTON3_MASK;
		}
		return -1;
	}

}
