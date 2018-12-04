
package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.Route;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.RoutingTable;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPSender;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.LogIn;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.NicknameDoesNotExist;
//import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
//import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
//import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsDeregistrationStatus;
//import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendAccept;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendMove;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import cs455.overlay.wireformats.SendDeregistration;

import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendUser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.StartGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Server extends Node {

	private BanqiController banqiController;
	private List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	private List<String> listOfUsersByNickname = new ArrayList<String>();
	private Map<BanqiGame, Map<String, String>> listCurrentGames = new HashMap<BanqiGame, Map<String, String>>();
	private Map<String, String> listOfInvites = new HashMap<String, String>();
	private Map<String, Integer> nameToNode = new HashMap<String, Integer>();
	// private List<User> listOfUsers = new ArrayList<User>();

	private EventFactory eventFactory;
	private static int[] nodeIds = new int[128];
	private static Map<Integer, Tuple<byte[], Integer>> nodesRegistered = new HashMap<Integer, Tuple<byte[], Integer>>();
	private static TCPServerThread server = null;;
	// ArrayList<RoutingTable> routeTables = null;
	int[] allMessNodes = null;
	TCPCache cache = null;

	// is it fixed?
	private void Initialize(int port) throws IOException {

		new Thread(() -> new CommandParser().registryCommands(this)).start();

		eventFactory = new EventFactory(this);
		server = new TCPServerThread(port);
		// this.routeTables = new ArrayList<RoutingTable>();
		this.cache = new TCPCache();
		// read in all of the profiles
		banqiController = new BanqiController("/s/bach/l/under/sporsche/cs414/Banqi/UserProfiles.txt");
		banqiController.readUsers();
		listOfProfiles = banqiController.getListProfiles();

		// for each user, put the nickname into a new list of Strings
		for (UserProfile u : listOfProfiles) {
			listOfUsersByNickname.add(u.getUserName());
		}

		// listOfUsers = banqiController.getListUsers();
		// System.out.println(listOfProfiles);
		Thread t = new Thread(server);
		t.start();

	}

	public static void main(String[] args) {

		int port = Integer.parseInt(args[0]);

		Server registryServer = new Server();
		try {
			registryServer.Initialize(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnEvent(Event e, TCPConnection connect) throws IOException {
		// System.out.println("In OnEvent for RegistryServer");

		byte type = e.getType();

		switch (type) {
		case Protocol.SendRegistration:
			SendRegistration sr = (SendRegistration) e;
			int iD = registerNode(sr.getType(), sr.getIpAddr(), sr.getPortNumber());
			// System.out.println("adding id and connection to cache" + connect);
			cache.addMap(iD, connect);
			RegistryReportsRegistrationStatus regStatus = new RegistryReportsRegistrationStatus();
			String message = "Registration request successful. The number of Users currently registered" + "is ("
					+ nodesRegistered.size() + ")";
			regStatus.setStatus(iD, (byte) message.getBytes().length, message.getBytes());
			connect.sendMessage(regStatus.getBytes());
			break;
		case Protocol.LogIn:
			LogIn lIn = (LogIn) e;
			byte[] name = lIn.getNickname();
			String nickname = new String(name);
			byte[] pass = lIn.getPassword();
			String password = new String(pass);
			System.out.println("Nickname is:::::::" + nickname);
			if (checkNickNameExists(nickname)) {
				// System.out.println("nickname exists");
				if (checkPassword(nickname, password)) {
					// System.out.println("Password is correct");
					// store the nickname with the nodeID in map
					nameToNode.put(nickname, lIn.getNodeId());
					for (Map.Entry<String, Integer> g : nameToNode.entrySet()) {
						System.out.println(g);
						System.out.println(nameToNode.get(nickname));
						System.out.println(cache.getById(nameToNode.get(nickname)));
						System.out.println("\n");
					}
					// return the User
					for (UserProfile prof : listOfProfiles) {
						if (prof.getUserName().equals(nickname)) {
							System.out.println("creating user to send to UserNode");
							SendUser sendU = new SendUser();
							sendU.setInfo(prof.getUserName().getBytes(), (byte) prof.getUserName().getBytes().length,
									prof.getEmail().getBytes(), (byte) prof.getEmail().getBytes().length,
									prof.getPassword().getBytes(), (byte) prof.getPassword().getBytes().length,
									prof.getJoinedDate().getBytes(), (byte) prof.getJoinedDate().getBytes().length,
									prof.getWins(), prof.getLosses(), prof.getDraws(), prof.getForfeits());
							Byte[] namesLengths = new Byte[listOfUsersByNickname.size()];
							List<byte[]> names = new ArrayList<byte[]>();
							for (int i = 0; i < namesLengths.length; i++) {
								namesLengths[i] = (byte) listOfUsersByNickname.get(i).getBytes().length;
								byte[] toByte = listOfUsersByNickname.get(i).getBytes();
								names.add(toByte);
							}
							sendU.setListUsers(namesLengths, names);
							connect.sendMessage(sendU.getBytes());
						}
					}
				}
			} else {
			}
			break;
		case Protocol.SendInvite:
			SendInvite invite = (SendInvite) e;
			// get inviter
			byte[] invF = invite.getInviteFrom();
			String inviteFrom = new String(invF);
			// get invitee
			byte[] invi = invite.getInvitee();
			String invitee = new String(invi);
			// add to hash map Invitee is key, invite from is value
			System.out.println("invitee: " + invitee);
			System.out.println("invite from: : " + inviteFrom);
			listOfInvites.put(invitee, inviteFrom);
			// send invite if invitee is logged on
			if (nameToNode.containsKey(invitee)) {
				sendInvite(invitee);
			}

			// ****************************************************************
			// write invite to file so that we can access them later!!
			// ****************************************************************
			break;
		case Protocol.SendAccept:
			SendAccept acc = (SendAccept) e;
			byte[] accep = acc.getInvitee();
			String acceptor = new String(accep);
			byte[] sen = acc.getInviteFrom();
			String sender = new String(sen);
			// start a new game
			startNewGame(sender, acceptor);
			break;
		case Protocol.SendMove:
			SendMove sendMove = (SendMove) e;
			byte[] oppoPlayer = sendMove.getPlayerName();
			String opponent = new String(oppoPlayer);
			System.out.println(opponent);
			boolean turn = sendMove.getTurn();
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
					pieName = sendMove.getNam0();
					colName = sendMove.getcolC0();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis0());
				} else if(i == 1) {
					pieName = sendMove.getNam1();
					colName = sendMove.getcolC1();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis1());
				} else if(i == 2) {
					pieName = sendMove.getNam2();
					colName = sendMove.getcolC2();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis2());
				} else if(i == 3) {
					pieName = sendMove.getNam3();
					colName = sendMove.getcolC3();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis3());
				} else if(i == 4) {
					pieName = sendMove.getNam4();
					colName = sendMove.getcolC4();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis4());
				} else if(i == 5) {
					pieName = sendMove.getNam5();
					colName = sendMove.getcolC5();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis5());
				} else if(i == 6) {
					pieName = sendMove.getNam6();
					colName = sendMove.getcolC6();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis6());
				} else if(i == 7) {
					pieName = sendMove.getNam7();
					colName = sendMove.getcolC7();
					System.out.println(pieName.size());
					for(int b = 0; b < 4; b++) {
						String s = new String(pieName.get(b));
						strName[b] = s;
						String sC = new String(colName.get(b));
						colNam[b] = sC;
					}
					visible.add(sendMove.getVis7());
				} else {}
				pieceNames.add(strName);
				colNames.add(colNam);
			}
			//now send the Move to the opposing user
			
			break;
		case Protocol.SendLogOff:
			SendLogOff lOut = (SendLogOff) e;
			RegistryReportsDeregistrationStatus deregStatus = new RegistryReportsDeregistrationStatus();
			connect.sendMessage(deregStatus.getBytes());
			break;
		}
	}

	public static int registerNode(byte type, byte[] address, int port) {
		byte sentType = type;
		byte[] sentAddress = address;
		int sentPort = port;
		boolean unique = false;
		int rand = 0;

		// new node is trying to register, check the IPAddress given vs the IP address
		// from the socket
		// if match, generate random number between 0 and 127, and store the info in
		// (hashmap?)

		Random randomGenerator = new Random();
		while (!unique) {
			rand = randomGenerator.nextInt(128);
			// System.out.println("Random number generated: " + rand);
			if (nodeIds[rand] != rand) {
				// this number hasn't been generated yet, add number to array
				nodeIds[rand] = rand;
				unique = true;
			} else {
				// System.out.println("random number has already been generated, need a new
				// number");
			}
		}
		// generate an entry in hashmap?
		nodesRegistered.put(rand, new Tuple<byte[], Integer>(sentAddress, port));
		// System.out.println(nodesRegistered.size());
		for (Entry<Integer, Tuple<byte[], Integer>> key : nodesRegistered.entrySet()) {
			Integer k = key.getKey();
			Tuple<byte[], Integer> value = key.getValue();
			byte[] ip = value.s1;
			int p = value.s2;
			// System.out.println(k + " " + ip + " " + p);
		}

		return rand;

	}

	// load your personal user profile
	private boolean checkNickNameExists(String nickname) throws IOException {
		for (UserProfile prof : listOfProfiles) {
			// System.out.println("UserName is: " + prof.getUserName());
			if (prof.getUserName().equals(nickname)) {
				// log in, by entering password
				return true;
			}
		}
		return false;
	}

	private boolean checkPassword(String nickname, String password) {
		for (UserProfile prof : listOfProfiles) {
			if (prof.getUserName().equals(nickname)) {
				if (prof.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	private void sendInvite(String invitee) throws IOException {

		TCPConnection connect = cache.getById(nameToNode.get(invitee));
		for (Map.Entry<String, String> entry : listOfInvites.entrySet()) {
			if (entry.getKey().equals(invitee)) {
				// get both values and send to the invitee node
				String invTo = invitee;
				String invFr = entry.getValue();
				SendInvite sendInv = new SendInvite();
				sendInv.setInvitee((byte) invTo.getBytes().length, invTo.getBytes());
				sendInv.setInviteFrom((byte) invFr.getBytes().length, invFr.getBytes());
				connect.sendMessage(sendInv.getBytes());
			} else {
				// do nothing
			}
		}
	}

	private void startNewGame(String sender, String acceptor) throws IOException {

		// send message to both users to start the game
		TCPConnection connectSender = cache.getById(nameToNode.get(sender));
		TCPConnection connectAcceptor = cache.getById(nameToNode.get(acceptor));

		System.out.println("In start new game");
		UserProfile sendProfile = null;
		UserProfile accepProfile = null;
		for (UserProfile up : listOfProfiles) {
			if (up.getUserName().equals(sender)) {
				sendProfile = up;
			}
			if (up.getUserName().equals(acceptor)) {
				accepProfile = up;
			} else {
				System.out.println("A profile was not found, this shouldn't have happened!");
			}
		}
		User player1 = new User(sendProfile);
		User player2 = new User(accepProfile);
		BanqiGame game = new BanqiGame(player1, player2);
		game.setUpBoard();

		List<String[]> pieceNameArray = new ArrayList<String[]>(8);
		List<String[]> pieceColorArray = new ArrayList<String[]>(8);
		List<boolean[]> pieceVisArray = new ArrayList<boolean[]>(8);

		for (int i = 0; i < 8; i++) {
			String[] pieceNames = new String[4];
			String[] pieceColors = new String[4];
			boolean[] isVis = new boolean[4];
			for (int j = 0; j < 4; j++) {
				// System.out.println("j: " + j + "i: " + i);
				pieceNames[j] = game.getBoard().getTileInfo(j, i).getPiece().getName();
				// System.out.println(pieceNames[j]);
				pieceColors[j] = game.getBoard().getTileInfo(j, i).getPiece().getColor();
				// System.out.println(pieceColors[j]);
				isVis[j] = game.getBoard().getTileInfo(j, i).getPiece().isVisible();
			}
			pieceNameArray.add(pieceNames);
			pieceColorArray.add(pieceColors);
			pieceVisArray.add(isVis);
		}

		for (int k = 0; k < pieceNameArray.size(); k++) {
			System.out.println(Arrays.toString(pieceNameArray.get(k)));
			System.out.println(Arrays.toString(pieceColorArray.get(k)));
			System.out.println(Arrays.toString(pieceVisArray.get(k)));
			System.out.println();
		}

		StartGame startG = new StartGame();
		startG.setPlayerName((byte) acceptor.getBytes().length, acceptor.getBytes());
		startG.setTurn(true);

		for (int i = 0; i < pieceNameArray.size(); i++) {
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] nN = new byte[4];
			// System.out.println(pieceNameArray.get(i).length);
			for (int j = 0; j < pieceNameArray.get(i).length; j++) {
				byte nLength = (byte) pieceNameArray.get(i)[j].getBytes().length;
				nN[j] = nLength;
				byteList.add(pieceNameArray.get(i)[j].getBytes());

			}
			if (i == 0) {
				startG.setNameLengths(nN);
				startG.setNames0(byteList);
			} else if (i == 1) {
				startG.setNameLengths1(nN);
				startG.setNames1(byteList);
			} else if (i == 2) {
				startG.setNameLengths2(nN);
				startG.setNames2(byteList);
			} else if (i == 3) {
				startG.setNameLengths3(nN);
				startG.setNames3(byteList);
			} else if (i == 4) {
				startG.setNameLengths4(nN);
				startG.setNames4(byteList);
			} else if (i == 5) {
				startG.setNameLengths5(nN);
				startG.setNames5(byteList);
			} else if (i == 6) {
				startG.setNameLengths6(nN);
				startG.setNames6(byteList);
			} else if (i == 7) {
				startG.setNameLengths7(nN);
				startG.setNames7(byteList);
			}

		}

		List<ArrayList<byte[]>> pieceColorBytes = new ArrayList<ArrayList<byte[]>>();

		System.out.println("before color loop");
		System.out.println(pieceColorArray.size());
		for (int i = 0; i < pieceColorArray.size(); i++) {
			System.out.println(i);
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] cN = new byte[4];
			boolean[] v = pieceVisArray.get(i);
			for (int j = 0; j < pieceColorArray.get(i).length; j++) {
				byte cLength = (byte) pieceColorArray.get(i)[j].getBytes().length;
				cN[j] = cLength;
				byteList.add(pieceColorArray.get(i)[j].getBytes());

			}
			if (i == 0) {
				System.out.println(Arrays.toString(cN));
				startG.setColorLengths0(cN);
				startG.setColor0(byteList);
				startG.setVis0(v);
			} else if (i == 1) {
				startG.setColorLengths1(cN);
				startG.setColor1(byteList);
				startG.setVis1(v);
			} else if (i == 2) {
				startG.setColorLengths2(cN);
				startG.setColor2(byteList);
				startG.setVis2(v);
			} else if (i == 3) {
				startG.setColorLengths3(cN);
				startG.setColor3(byteList);
				startG.setVis3(v);
			} else if (i == 4) {
				startG.setColorLengths4(cN);
				startG.setColor4(byteList);
				startG.setVis4(v);
			} else if (i == 5) {
				startG.setColorLengths5(cN);
				startG.setColor5(byteList);
				startG.setVis5(v);
			} else if (i == 6) {
				startG.setColorLengths6(cN);
				startG.setColor6(byteList);
				startG.setVis6(v);
			} else if (i == 7) {
				startG.setColorLengths7(cN);
				startG.setColor7(byteList);
				startG.setVis7(v);
			}

		}
		connectSender.sendMessage(startG.getBytes());


		StartGame startG2 = new StartGame();
		startG2.setPlayerName((byte) acceptor.getBytes().length, acceptor.getBytes());
		startG2.setTurn(false);

		for (int i = 0; i < pieceNameArray.size(); i++) {
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] nN = new byte[4];
			// System.out.println(pieceNameArray.get(i).length);
			for (int j = 0; j < pieceNameArray.get(i).length; j++) {
				byte nLength = (byte) pieceNameArray.get(i)[j].getBytes().length;
				nN[j] = nLength;
				byteList.add(pieceNameArray.get(i)[j].getBytes());

			}
			if (i == 0) {
				startG2.setNameLengths(nN);
				startG2.setNames0(byteList);
			} else if (i == 1) {
				startG2.setNameLengths1(nN);
				startG2.setNames1(byteList);
			} else if (i == 2) {
				startG2.setNameLengths2(nN);
				startG2.setNames2(byteList);
			} else if (i == 3) {
				startG2.setNameLengths3(nN);
				startG2.setNames3(byteList);
			} else if (i == 4) {
				startG2.setNameLengths4(nN);
				startG2.setNames4(byteList);
			} else if (i == 5) {
				startG2.setNameLengths5(nN);
				startG2.setNames5(byteList);
			} else if (i == 6) {
				startG2.setNameLengths6(nN);
				startG2.setNames6(byteList);
			} else if (i == 7) {
				startG2.setNameLengths7(nN);
				startG2.setNames7(byteList);
			}

		}

		//List<ArrayList<byte[]>> pieceColorBytes = new ArrayList<ArrayList<byte[]>>();

		System.out.println("before color loop");
		System.out.println(pieceColorArray.size());
		for (int i = 0; i < pieceColorArray.size(); i++) {
			System.out.println(i);
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] cN = new byte[4];
			boolean[] v = pieceVisArray.get(i);
			for (int j = 0; j < pieceColorArray.get(i).length; j++) {
				byte cLength = (byte) pieceColorArray.get(i)[j].getBytes().length;
				cN[j] = cLength;
				byteList.add(pieceColorArray.get(i)[j].getBytes());

			}
			if (i == 0) {
				System.out.println(Arrays.toString(cN));
				startG2.setColorLengths0(cN);
				startG2.setColor0(byteList);
				startG2.setVis0(v);
			} else if (i == 1) {
				startG2.setColorLengths1(cN);
				startG2.setColor1(byteList);
				startG2.setVis1(v);
			} else if (i == 2) {
				startG2.setColorLengths2(cN);
				startG2.setColor2(byteList);
				startG2.setVis2(v);
			} else if (i == 3) {
				startG2.setColorLengths3(cN);
				startG2.setColor3(byteList);
				startG2.setVis3(v);
			} else if (i == 4) {
				startG2.setColorLengths4(cN);
				startG2.setColor4(byteList);
				startG2.setVis4(v);
			} else if (i == 5) {
				startG2.setColorLengths5(cN);
				startG2.setColor5(byteList);
				startG2.setVis5(v);
			} else if (i == 6) {
				startG2.setColorLengths6(cN);
				startG2.setColor6(byteList);
				startG2.setVis6(v);
			} else if (i == 7) {
				startG2.setColorLengths7(cN);
				startG2.setColor7(byteList);
				startG2.setVis7(v);
			}

		}
		connectAcceptor.sendMessage(startG2.getBytes());

	}

	// sends the state of the board to the other player, justPlayed is the name of
	// the player has just taken a turn.
	// toPlay is the name of the player who plays next
	// parameters definitely subject to change
	public void sendBoardState(String justPlayed, String toPlay) {

	}

	public static void deregisterNode(byte typeRec, byte[] ipAddrRec, int portNumRec, int nodeIDRec) {
		// remove the entry in the hashmap that corresponds to nodeIDRec
	}

	public void printListOfNodes() {
		for (Entry<Integer, Tuple<byte[], Integer>> key : nodesRegistered.entrySet()) {
			Integer k = key.getKey();
			Tuple<byte[], Integer> value = key.getValue();
			byte[] ip = value.s1;
			int p = value.s2;
			System.out.println(k + " " + ip + " " + p);
		}
	}
}