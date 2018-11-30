package main.edu.colostate.cs.cs414.ByteMe.banqi.transport;

import java.net.*;
import java.util.Arrays;

import main.edu.colostate.cs.cs414.ByteMe.banqi.server.Server;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;

import java.io.*;

public class TCPReceiverThread implements Runnable{
	
	private Socket socket;
	private DataInputStream din;
	private EventFactory receive_ef = null;
	
	public TCPReceiverThread(Socket socket, EventFactory ef) throws IOException {
		this.socket = socket;
		receive_ef = ef;
		din = new DataInputStream(socket.getInputStream());
	}
	
	public void run() {
		
		int dataLength;
		while (socket != null) {
			try {
				dataLength = din.readInt();
				
				byte[] data = new byte[dataLength];
				din.readFully(data, 0, dataLength);
//				System.out.println("finished processing");
//				System.out.println(Arrays.toString(data));
//				EventFactory.getInstance().processMessage(data, );
//				RegistryServer.sendMessage();
			}
			catch (SocketException se) {
				System.out.println(se.getMessage());
				break;
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}

}