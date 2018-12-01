package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.LogIn;
//import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
//import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
//import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
//import cs455.overlay.wireformats.OverlayNodeSendsData;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
//import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RequestPassword;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendDeregistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendUser;

import java.io.*;

public class UserNode extends Node{

	private static TCPServerThread clientSock = null;
	private static EventFactory eventFactory;
	private static int port = -1;
	private static int nodeID = -1;
	private static Socket client = null;
	Thread t = null;
	TCPCache cache = null;
	TCPConnection connection = null;
	BanqiController banqi = new BanqiController(this);
	
	private UserProfile userProfile;
	private User user;
	
	private void Initialize(String serverName, int port) throws IOException, InterruptedException
	{
		this.eventFactory = new EventFactory(this);
		clientSock = new TCPServerThread(0);
//		Thread t  = new Thread(clientSock);
//		t.start();
		
//		new Thread (() -> {
//			try {
//				new CommandParser().messagingCommands(this);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}).start();

		sendRegMessage(serverName, port);
		banqi.runProgram();
	}
	
	public static void main(String[] args) throws InterruptedException {
		String serverName = args[0];
		port = Integer.parseInt(args[1]);
		
		UserNode messageNode = new UserNode();
		try {
			messageNode.Initialize(serverName, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientSock.setServerInfo(serverName);
	}
	
	private void sendRegMessage(String serverName, int port) throws IOException {
		
		connection = new TCPConnection(new Socket(serverName, port));
		connection.initialize();

		//send the information to the SendRegistration class
		SendRegistration sendReg = new SendRegistration();
		sendReg.setPortInetAddress(clientSock.getPort(), InetAddress.getLocalHost().getAddress());
		
		//get the marshalled bytes from SendRegistration
		byte[] marshalled = sendReg.getBytes();
		connection.sendMessage(marshalled);	
	
	}
	
	//send a message to the server requesting to LogIn to an existing account
	public void logIn(String nickname, String password) throws IOException {
		LogIn lIn = new LogIn();
		lIn.setNickname((byte)nickname.getBytes().length, nickname.getBytes());
		lIn.setPassword((byte)password.getBytes().length, password.getBytes());
		System.out.println("sending message");
		connection.sendMessage(lIn.getBytes());
	}

	public void OnEvent(Event e, TCPConnection connect) throws IOException {
//		System.out.println("In OnEvent Messaging");
		byte type = e.getType();
		
		switch(type) {
		case Protocol.RegistryReportsRegistrationStatus:
			RegistryReportsRegistrationStatus regStatus = (RegistryReportsRegistrationStatus) e;
			nodeID = regStatus.getStatus();
//			System.out.println("Node ID = " + nodeID);
//			System.out.println(Arrays.toString(regStatus.getInfoString()));
			break;
		case Protocol.RequestPassword:
			RequestPassword reqP = (RequestPassword) e;
			banqi.setRequestPassword();
			break;
		case Protocol.SendUser:
			SendUser sendU = (SendUser) e;
			
			//get userprofile info and create User instance
			byte[] name = sendU.getNickname();
			String nickname = new String(name);
			byte[] em = sendU.getEmail();
			String email = new String(em);
			byte[] pas = sendU.getPassword();
			String password = new String(pas);
			byte[] dat = sendU.getDate();
			String date = new String(dat);
			int wins = sendU.getWins();
			int losses = sendU.getLosses();
			int draws = sendU.getDraws();
			int forfeits = sendU.getForfeits();
			userProfile = new UserProfile(nickname, email, password, date, wins, losses, draws, forfeits);
			user = new User(userProfile);
			banqi.setUser(user);
			banqi.setUserStatus(true);
			break;
		case Protocol.RegistryReportsDeregistrationStatus:
//			RegistryReportsDeregistrationStatus rrd = (RegistryReportsDeregistrationStatus) e;
			System.out.println("Exiting Overlay");
			System.exit(0);
			break;
		}		
	}
	
	public String askPassword() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please Enter Your Password");
		String password = read.readLine();
		System.out.println(password);
		read.close();
		return password;
	}
	
	public void sendPassword(String password) throws IOException {
		System.out.println("sending password to server");
		SendPassword sendPass = new SendPassword();
		sendPass.setPassword((byte)password.getBytes().length, password.getBytes());
		connection.sendMessage(sendPass.getBytes());
	}
	
	public void logOff() throws IOException {
		SendLogOff sendOff = new SendLogOff();
		connection.sendMessage(sendOff.getBytes());
	}
	
	//convert IP to bytes
	private String convertIP(byte[] ip) {
		String s = "";
		for(int i = 0; i < ip.length; i++) {
			s += ip[i] & 0xff;
			if(i != ip.length - 1) {
				s += ".";
			}
		}
//		System.out.println(s);
		return s;
	}

}
