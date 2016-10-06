package RemoteMonitorV2;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 被控端定时发送屏幕信息给控制端类
 * 
 * @author YangKang
 * 
 */
@SuppressWarnings("restriction")
public class RemoteScreenThread extends Thread {
	private static final int FPS = 20;// 设置每秒帧率
	private DataOutputStream dous = null;// 网络输出流对象
	private boolean flag = true;// 网络连接标志
	private Robot robot;// 机器人对象

	/**
	 * 构造方法
	 * 
	 * @param s
	 */
	public RemoteScreenThread(Socket s) {
		try {
			dous = new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			LogTool.ERROR("被控端发送截图类中创建数据输出流失败：" + e.getMessage());
		}
	}

	/**
	 * 在线程中抓屏
	 */
	@Override
	public void run() {
		try {
			// 获取屏幕大小
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			// 创建同屏幕大小的方框对象
			Rectangle rect = new Rectangle(0, 0, (int) screenSize.getWidth(),
					(int) screenSize.getHeight());

			robot = new Robot();
			while (flag) {
				
				long begin = System.currentTimeMillis();
				
				// 抓去一张屏幕大小的图片
				byte[] bgImg = createImage(rect);

				dous.writeInt(bgImg.length);

				dous.write(bgImg);

				dous.flush();

				long end = System.currentTimeMillis();

				LogTool.INFO("cost time" + (end - begin) + "\tbgImg size:"
						+ (bgImg.length));

				Thread.sleep(1000 / FPS);
			}
		} catch (IOException e) {
			flag = false;
			LogTool.ERROR("被控端发送截屏线程类中IO出错：" + e.getMessage().toString());
		} catch (InterruptedException e) {
			flag = false;
			LogTool.ERROR("被控端发送截屏线程类中线程休眠出错：" + e.getMessage().toString());
		} catch (AWTException e) {
			flag = false;
			LogTool.ERROR("被控端发送截屏线程类中Robot对象出错：" + e.getMessage().toString());
		}
	}

	/**
	 * 将背景图片压缩成JPEG并发送到控制端
	 * 
	 * @param rec
	 * @return
	 * @throws IOException
	 */
	private byte[] createImage(Rectangle rec) throws IOException {

		BufferedImage bfImage = robot.createScreenCapture(rec);

		ByteArrayOutputStream baous = new ByteArrayOutputStream();

		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baous);

		encoder.encode(bfImage);

		return baous.toByteArray();

	}
}
