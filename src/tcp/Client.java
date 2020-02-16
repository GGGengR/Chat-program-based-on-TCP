package tcp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Client extends JFrame implements Runnable, ActionListener {
	JButton JB = new JButton("发送");
	JTextField field = new JTextField();
	JTextArea area = new JTextArea("聊天内容：\n");
	int Port = 9999;
	Socket s=null;
	public Client() {
		this.setTitle("客户端");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(area, BorderLayout.CENTER);
		this.add(JB, BorderLayout.SOUTH);
		JB.addActionListener(this);
		this.add(field, BorderLayout.NORTH);
		this.setSize(180, 220);
		this.setResizable(true);
		this.setVisible(true);
		try {
			InetAddress address = InetAddress.getLocalHost();//返回本地主机
			s=new Socket(address,Port);
			String str = "有一个客户已连接";
			OutputStream sout=s.getOutputStream();
			DataOutputStream dos=new DataOutputStream(sout);
			dos.writeUTF(str);
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			while (true) {
				InetAddress address = InetAddress.getLocalHost();//返回本地主机
				InputStream s1in=s.getInputStream();
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
			String str = "客户端说：" + field.getText();
			area.append(str + '\n');
			InetAddress address = InetAddress.getLocalHost();//返回本地主机
			OutputStream s1out=s.getOutputStream();
			DataOutputStream dos=new DataOutputStream(s1out);
			dos.writeUTF(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Client();
	}
}
