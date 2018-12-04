package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.CreateProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.LogIn;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendAccept;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.ValidProfile;
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
	private List<String> gamesInvitedTo = new ArrayList<String>();

	
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
		lIn.setNodeId(nodeID);
//		System.out.println("sending message");
		connection.sendMessage(lIn.getBytes());
	}
	
	//send invite (to be routed through server)
	public void sendInvite(String invitee) throws IOException {
		SendInvite sendInvite = new SendInvite();
		sendInvite.setInvitee((byte)invitee.getBytes().length, invitee.getBytes());
		sendInvite.setInviteFrom((byte)userProfile.getUserName().getBytes().length, userProfile.getUserName().getBytes());
		connection.sendMessage(sendInvite.getBytes());
	}
	
	//message from UserNode to router declaring that 2 users have accepted a game invite
	public void sendAccept(String invitee, String inviteFrom) throws IOException {
		SendAccept sendAcc = new SendAccept();
		sendAcc.setInvitee((byte)invitee.getBytes().length,  invitee.getBytes());
		sendAcc.setInviteFrom((byte)inviteFrom.getBytes().length,  inviteFrom.getBytes());
		connection.sendMessage(sendAcc.getBytes());
	}

	//Receives Event messages, and acts according to the type of Event that has arrived
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
			
			List<byte[]> nam = new ArrayList<byte[]>();
			List<String> names = new ArrayList<String>();
			nam = sendU.getNames();
			for(byte[] n : nam) {
				String s = new String(n);
				names.add(s);
//				System.out.println(s);
			}
			
			banqi.setNames(names);

			break;
		case Protocol.ValidProfile:
			ValidProfile validProfile = (ValidProfile) e;
			banqi.setValidProfile(validProfile.isValidProfile());
			if (!validProfile.isValidProfile()) 
				System.out.println("Nickname and/or email already exists in our system. Try again.");
			else 
				System.out.println("Profile created!");
			break;
		case Protocol.RegistryReportsDeregistrationStatus:
//			RegistryReportsDeregistrationStatus rrd = (RegistryReportsDeregistrationStatus) e;
			System.out.println("Exiting Overlay");
			System.exit(0);
			break;
		case Protocol.SendInvite:
			SendInvite sendInv = (SendInvite) e;
			byte[] inF = sendInv.getInviteFrom();
			String invFrom = new String(inF);
			gamesInvitedTo.add(invFrom);
			System.out.println("\nYou received an invite from " + invFrom + "!");
			System.out.println("\nTo respond to this invite, enter 2.\n");
			//System.out.println("\nPlease choose how to proceed. \n1) Play existing game");
			//System.out.println("2) Manage invites");
			//System.out.println("3) View profile");
		}		
	}
	
//	public String askPassword() throws IOException {
//		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Please Enter Your Password");
//		String password = read.readLine();
//		System.out.println(password);
//		read.close();
//		return password;
//	}
	
	public void sendPassword(String password) throws IOException {
		//System.out.println("sending password to server");
		SendPassword sendPass = new SendPassword();
		sendPass.setPassword((byte)password.getBytes().length, password.getBytes());
		connection.sendMessage(sendPass.getBytes());
	}
	
	public void createProfile(String nickname, String email, String password) throws IOException {
		//System.out.println("sending profile to server");
		CreateProfile profile = new CreateProfile();
		profile.setNickname((byte)nickname.getBytes().length, nickname.getBytes());
		profile.setEmail((byte)email.getBytes().length, email.getBytes());
		profile.setPassword((byte)password.getBytes().length, password.getBytes());
		connection.sendMessage(profile.getBytes());
	}
  
	public void logOff() throws IOException {
		SendLogOff sendOff = new SendLogOff();
		connection.sendMessage(sendOff.getBytes());
	}
	
	public List<String> getGamesInvitedTo(){
		return gamesInvitedTo;
	}
	//convert IP to bytes
//	private String convertIP(byte[] ip) {
//		String s = "";
//		for(int i = 0; i < ip.length; i++) {
//			s += ip[i] & 0xff;
//			if(i != ip.length - 1) {
//				s += ".";
//			}
//		}
////		System.out.println(s);
//		return s;
//	}

}
