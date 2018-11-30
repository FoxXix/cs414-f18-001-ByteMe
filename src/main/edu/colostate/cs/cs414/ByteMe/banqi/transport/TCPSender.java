package main.edu.colostate.cs.cs414.ByteMe.banqi.transport;

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class TCPSender implements Runnable{
	
	private Socket socket;
	private DataOutputStream dout;
	
	public TCPSender(Socket socket) throws IOException {
		this.socket = socket;
		dout = new DataOutputStream(socket.getOutputStream());
	}
	
	public void sendData(byte[] dataToSend) throws IOException {

//		System.out.println(Arrays.toString(dataToSend));
		int dataLength = dataToSend.length;
	//	System.out.println(dataLength);
		dout.writeInt(dataLength);
//		System.out.println("In sendData method");
		dout.write(dataToSend, 0, dataLength);
	//	System.out.println("In sendData method");
		dout.flush();
//		System.out.println("finished sending?");
	}

	@Override
	public void run() {
		
		
	}

}