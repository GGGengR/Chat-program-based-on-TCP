package tcp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;
public class Server extends JFrame implements Runnable, ActionListener {
	ServerSocket ss=null;
	int Port = 9999;
	Socket s1=null;
	JButton JB = new JButton("发送");
	JTextField field = new JTextField();
	JTextArea area = new JTextArea("聊天内容：\n");
	public Server() {
		this.setTitle("tcp服务器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用 System exit 方法退出应用程序
		this.add(JB, BorderLayout.SOUTH);
		JB.addActionListener(this);
		this.add(field, BorderLayout.NORTH);
		this.add(area, BorderLayout.CENTER);
		this.setSize(180, 220);//设置窗口大小
		this.setResizable(true);//窗口是否可以调整
		this.setVisible(true);
		try {
			ss=new ServerSocket(Port);
			s1=ss.accept();
			new Thread(this).start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void run() {
		try {
			while (true) {
				InputStream s1in=s1.getInputStream();
				DataInputStream dis=new DataInputStream(s1in);
				String str = dis.readUTF();
				area.append(str + '\n');
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		try {
			String str = "服务端说：" + field.getText();
			area.append(str + '\n');
			OutputStream s1out=s1.getOutputStream();
			DataOutputStream dos=new DataOutputStream(s1out);
			dos.writeUTF(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Server();
	}
}
