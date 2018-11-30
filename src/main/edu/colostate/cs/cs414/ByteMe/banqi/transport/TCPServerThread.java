package main.edu.colostate.cs.cs414.ByteMe.banqi.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;

public class TCPServerThread implements Runnable{
	int listeningPort = -1; 
	EventFactory m_eventFactory = null;
	private ServerSocket serverSocket = null;
	private static TCPSender send = null;
	private String serverName = null;
	private int nodeType = 0;
//	private  Socket clientSocket = null;
	
	
	
	public TCPServerThread(int port) throws IOException {
		listeningPort = port;
		serverSocket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

		while(true) {
			
			Socket sock = null;
			try {
				sock = serverSocket.accept();
				new TCPConnection(sock).initialize();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}
	
	public int getPort() {
		return serverSocket.getLocalPort();
	}
	
	public byte[] getIpAddress() {
//		System.out.println(Arrays.toString(serverSocket.getInetAddress().getAddress()));
		return this.serverSocket.getInetAddress().getAddress();
	}
	
	public void setServerInfo(String server) {
		serverName = server;
	}
	
	public TCPSender getSendThread() {
		return send;
	}

}
