package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Advisor;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Cannon;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Chariot;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Elephant;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.General;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Horse;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Piece;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Soldier;
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
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendAccept;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendDeregistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendUser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.StartGame;

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
			System.out.println("\nPlease choose how to proceed. \n1) Play existing game");
			System.out.println("2) Manage invites");
			System.out.println("3) View profile");
			break;
		case Protocol.StartGame:
			StartGame start = (StartGame) e;
			byte[] oppoPlayer = start.getPlayerName();
			String opponent = new String(oppoPlayer);
			System.out.println(opponent);
			boolean turn = start.getTurn();
			ArrayList<byte[]> pieName = new ArrayList<byte[]>();
			ArrayList<byte[]> colName = new ArrayList<byte[]>();
			List<String[]> pieceNames = new ArrayList<String[]>();
			List<String[]> colNames = new ArrayList<String[]>();
			List<boolean[]> visible = new ArrayList<boolean[]>();
			for(int i = 0; i < 8; i++) {
//				System.out.println(i);
				String[] strName = new String[4];
				String[] colNam = new String[4];
				if(i == 0) {
					pieName = start.getNam0();
					colName = start.getcolC0();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis0());
				} else if(i == 1) {
					pieName = start.getNam1();
					colName = start.getcolC1();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis1());
				} else if(i == 2) {
					pieName = start.getNam2();
					colName = start.getcolC2();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis2());
				} else if(i == 3) {
					pieName = start.getNam3();
					colName = start.getcolC3();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis3());
				} else if(i == 4) {
					pieName = start.getNam4();
					colName = start.getcolC4();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis4());
				} else if(i == 5) {
					pieName = start.getNam5();
					colName = start.getcolC5();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis5());
				} else if(i == 6) {
					pieName = start.getNam6();
					colName = start.getcolC6();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis6());
				} else if(i == 7) {
					pieName = start.getNam7();
					colName = start.getcolC7();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(start.getVis7());
				} else {}
				pieceNames.add(strName);
				colNames.add(colNam);
			}
			makeBoard(pieceNames, colNames, visible);
			banqi.startGame(opponent, turn);
			break;
		}		
	}
	
	private void makeBoard(List<String[]> pieceNames, List<String[]> colNames, List<boolean[]> visible) {
		Board b = new Board();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Piece piece = null;
				if(pieceNames.get(i)[j].equals("Soldier")){
					piece = new Soldier(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("Advisor")) {
					piece = new Advisor(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("Cannon")) {
					piece = new Cannon(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("Chariot")) {
					piece = new Chariot(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("Elephant")) {
					piece = new Elephant(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("General")) {
					piece = new General(colNames.get(i)[j], i, j);
				} else if(pieceNames.get(i)[j].equals("Horse")) {
					piece = new Horse(colNames.get(i)[j], i, j);
				}  else {
					//piece is null, do not add a piece
				}
				b.getTileInfo(j, i).setPiece(piece);
			}
		}
		banqi.setBoard(b);
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
 		System.out.println("sending password to server");
 		SendPassword sendPass = new SendPassword();
 		sendPass.setPassword((byte)password.getBytes().length, password.getBytes());
 		connection.sendMessage(sendPass.getBytes());
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