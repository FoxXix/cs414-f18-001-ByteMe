package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
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
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.CreateProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.LogIn;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendAccept;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendMove;
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
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.ValidProfile;
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

		
    	/**
     	* Initalize a UserNode and connect it to a running Server.
     	* In order to make a connection to the Server, both the Server's name and the Port of it are needed.
     	* @param serverName, the name of the active Server that this UserNode can connect to
     	* @param port, the Port number that is hosting the Server
     	* @throws IOException in the case of input/output error
     	* @throws InterruptedException if the connection to the Server from the UserNode is interrupted
     	*/
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
	
    /**
     * Send a message to the server requesting that a UserNode wants to LogIn to an existing account
     * @param nickname, the nickname of the User (from login on UserNode instance)
     * @param password, the password of the User (from login on UserNode instance)
     * @throws IOException in the case of input/output error
     */
	public void logIn(String nickname, String password) throws IOException {
		LogIn lIn = new LogIn();
		lIn.setNickname((byte)nickname.getBytes().length, nickname.getBytes());
		lIn.setPassword((byte)password.getBytes().length, password.getBytes());
		lIn.setNodeId(nodeID);
//		System.out.println("sending message");
		connection.sendMessage(lIn.getBytes());
	}
	
    /**
     * Send a message to the server saying a UserNode wants to send an invite to another User(node)
     * @param invitee, the nickname of the User for the server to send an invite message to.
     * @throws IOException int he case of input/output error
     */
	public void sendInvite(String invitee) throws IOException {
		SendInvite sendInvite = new SendInvite();
		sendInvite.setInvitee((byte)invitee.getBytes().length, invitee.getBytes());
		sendInvite.setInviteFrom((byte)userProfile.getUserName().getBytes().length, userProfile.getUserName().getBytes());
		connection.sendMessage(sendInvite.getBytes());
	}
	
    /**
     * Send a message to the Inviter that the Invitee accepted the invitation and will play Banqi.
     * The message from UserNode to router declares that 2 users have accepted a game invite.
     * @param invitee, the nickname of the User who the server invited
     * @param inviteFrom, the nickname of the User who request the invite be sent
     * @throws IOException in the case of input/output error
     */
	public void sendAccept(String invitee, String inviteFrom) throws IOException {
		SendAccept sendAcc = new SendAccept();
		sendAcc.setInvitee((byte)invitee.getBytes().length,  invitee.getBytes());
		sendAcc.setInviteFrom((byte)inviteFrom.getBytes().length,  inviteFrom.getBytes());
		connection.sendMessage(sendAcc.getBytes());
	}

    /**
     * Overrides the OnEvent abstract method from the Node.java class to handle the occurrence of
     * events on a UserNode instance.  Such events are account registration, login, checking for valid profiles,
     * logging out, unregistering, sending/accepting invites, sending moves and startingAGame (all send messages).
     * @param e, an event to be handled (such as a sendAccept message from a UserNode to another)
     * @param connect, the connection that connects a device to the Server
     * @throws IOException in the case of input/output error
     */
	public void OnEvent(Event e, TCPConnection connect) throws IOException {
		byte type = e.getType();
		
		switch(type) {
		case Protocol.RegistryReportsRegistrationStatus:
			RegistryReportsRegistrationStatus regStatus = (RegistryReportsRegistrationStatus) e;
			nodeID = regStatus.getStatus();
//			System.out.println("Node ID = " + nodeID);
//			System.out.println(Arrays.toString(regStatus.getInfoString()));
			break;
		case Protocol.SendUser:
			handleSendUser((SendUser) e);
			break;
		case Protocol.ValidProfile:
			handleValidProfile((ValidProfile) e);
			break;
		case Protocol.RegistryReportsDeregistrationStatus:
//			RegistryReportsDeregistrationStatus rrd = (RegistryReportsDeregistrationStatus) e;
			System.out.println("Exiting Overlay");
			System.exit(0);
			break;
		case Protocol.SendInvite:
			handleSendInvite((SendInvite) e);
			break;
		case Protocol.StartGame:
			handleStartGame((StartGame) e);

			break;
		case Protocol.SendMove:
			handleSendMove((SendMove) e);
		}		
	}

	private void handleValidProfile(ValidProfile e) {
		ValidProfile validProfile = e;
		banqi.setValidProfile(validProfile.isValidProfile());
		if (!validProfile.isValidProfile())
			System.out.println("Nickname and/or email already exists in our system. Try again.");
		else
			System.out.println("Profile created!");
	}

	private void handleSendInvite(SendInvite e) {
		SendInvite sendInv = e;
		byte[] inF = sendInv.getInviteFrom();
		String invFrom = new String(inF);
		gamesInvitedTo.add(invFrom);
		System.out.println("\nYou received an invite from " + invFrom + "!");
		System.out.println("\nPlease choose how to proceed. \n1) Play existing game");
		System.out.println("2) Manage invites");
		System.out.println("3) View profile");
	}

	private void handleSendMove(SendMove e) throws IOException {
		SendMove sendMove = e;
		byte[] oppoPlayer1 = sendMove.getPlayerName();
		String opponent1 = new String(oppoPlayer1);
		System.out.println(opponent1);
		boolean turn1 = sendMove.getTurn();
		int gameID1 = sendMove.getGameID();
		ArrayList<byte[]> pieName1 = new ArrayList<byte[]>();
		ArrayList<byte[]> colName1 = new ArrayList<byte[]>();
		List<String[]> pieceNames1 = new ArrayList<String[]>();
		List<String[]> colNames1 = new ArrayList<String[]>();
		List<boolean[]> visible1 = new ArrayList<boolean[]>();
		for(int i = 0; i < 8; i++) {
//				System.out.println(i);
			String[] strName = new String[4];
			String[] colNam = new String[4];
			if(i == 0) {
				sendMovePerIteration(visible1, strName, colNam, sendMove.getNam0(), sendMove.getcolC0(), sendMove.getVis0());
			} else if(i == 1) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam1(), sendMove.getcolC1(), sendMove.getVis1());
			} else if(i == 2) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam2(), sendMove.getcolC2(), sendMove.getVis2());
			} else if(i == 3) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam3(), sendMove.getcolC3(), sendMove.getVis3());
			} else if(i == 4) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam4(), sendMove.getcolC4(), sendMove.getVis4());
			} else if(i == 5) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam5(), sendMove.getcolC5(), sendMove.getVis5());
			} else if(i == 6) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam6(), sendMove.getcolC6(), sendMove.getVis6());
			} else if(i == 7) {
				sendDataPerIteration(visible1, strName, colNam, sendMove.getNam7(), sendMove.getcolC7(), sendMove.getVis7());
			} else {}
			pieceNames1.add(strName);
			colNames1.add(colNam);
		}

		for(int i = 0; i < visible1.size(); i++) {
//				System.out.println(Arrays.toString(pieceNameArray.get(i)));
//				System.out.println(Arrays.toString(pieceColorArray.get(i)));
			System.out.println(Arrays.toString(visible1.get(i)));
		}

		makeBoard(pieceNames1, colNames1, visible1);
		banqi.makeMove(turn1, opponent1, gameID1);
	}

	private void sendDataPerIteration(List<boolean[]> visible1, String[] strName, String[] colNam, ArrayList<byte[]> nam1, ArrayList<byte[]> bytes, boolean[] vis1) {
		ArrayList<byte[]> pieName1;
		ArrayList<byte[]> colName1;
		pieName1 = nam1;
		colName1 = bytes;

		for (int b = 0; b < 4; b++) {
			String s = new String(pieName1.get(b));
			strName[b] = s;
			String sC = new String(colName1.get(b));
			colNam[b] = sC;
		}
		visible1.add(vis1);
	}

	private void sendMovePerIteration(List<boolean[]> visible1, String[] strName, String[] colNam, ArrayList<byte[]> nam0, ArrayList<byte[]> bytes, boolean[] vis0) {
		ArrayList<byte[]> pieName1;
		ArrayList<byte[]> colName1;
		sendDataPerIteration(visible1, strName, colNam, nam0, bytes, vis0);
	}

	private void handleStartGame(StartGame e) throws IOException {
		StartGame start = e;
		byte[] oppoPlayer = start.getPlayerName();
		String opponent = new String(oppoPlayer);
		System.out.println(opponent);
		boolean turn = start.getTurn();
		int gameID = start.getGameID();
		ArrayList<byte[]> pieName = new ArrayList<byte[]>();
		ArrayList<byte[]> colName = new ArrayList<byte[]>();
		List<String[]> pieceNames = new ArrayList<String[]>();
		List<String[]> colNames = new ArrayList<String[]>();
		List<boolean[]> visible = new ArrayList<boolean[]>();

		for(int i = 0; i < 8; i++) {
			String[] strName = new String[4];
			String[] colNam = new String[4];
			if(i == 0) {
				sendDataPerIteration(visible, strName, colNam, start.getNam0(), start.getcolC0(), start.getVis0());
			} else if(i == 1) {
				sendDataPerIteration(visible, strName, colNam, start.getNam1(), start.getcolC1(), start.getVis1());
			} else if(i == 2) {
				sendDataPerIteration(visible, strName, colNam, start.getNam2(), start.getcolC2(), start.getVis2());
			} else if(i == 3) {
				sendDataPerIteration(visible, strName, colNam, start.getNam3(), start.getcolC3(), start.getVis3());
			} else if(i == 4) {
				sendDataPerIteration(visible, strName, colNam, start.getNam4(), start.getcolC4(), start.getVis4());
			} else if(i == 5) {
				sendDataPerIteration(visible, strName, colNam, start.getNam5(), start.getcolC5(), start.getVis5());
			} else if(i == 6) {
				sendDataPerIteration(visible, strName, colNam, start.getNam6(), start.getcolC6(), start.getVis6());
			} else if(i == 7) {
				sendDataPerIteration(visible, strName, colNam, start.getNam7(), start.getcolC7(), start.getVis7());
			} else {}
			pieceNames.add(strName);
			colNames.add(colNam);
		}
		makeBoard(pieceNames, colNames, visible);
		banqi.startGame(opponent, turn);
		if(turn == true) {
			banqi.makeMove(turn, opponent, gameID);
		} else {
			System.out.println("Please wait for your turn");
		}
	}

	private void handleSendUser(SendUser e) {
		SendUser sendU = e;

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
	}

	private void makeBoard(List<String[]> pieceNames, List<String[]> colNames, List<boolean[]> visible) {
		Board b = new Board();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Piece piece = null;
				if(pieceNames.get(i)[j].equals("Soldier")){
					piece = placeSoldier(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("Advisor")) {
					piece = placeAdvisor(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("Cannon")) {
					piece = placeCannon(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("Chariot")) {
					piece = placeChariot(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("Elephant")) {
					piece = placeElephant(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("General")) {
					piece = placeGeneral(colNames, visible, i, j);
				} else if(pieceNames.get(i)[j].equals("Horse")) {
					piece = placeHorse(colNames, visible, i, j);
				}  else {
					//piece is null, do not add a piece
				}
				b.getTileInfo(j, i).setPiece(piece);
				System.out.println(piece.isVisible());
			}
		}
		System.out.println("usernode make board null check: " + b);
		banqi.setBoard(b);
	}

	private Piece placeHorse(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Horse(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeGeneral(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new General(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeElephant(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Elephant(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeChariot(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Chariot(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeCannon(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Cannon(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeAdvisor(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Advisor(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}

	private Piece placeSoldier(List<String[]> colNames, List<boolean[]> visible, int i, int j) {
		Piece piece = new Soldier(colNames.get(i)[j], i, j);
		System.out.println(visible.get(i)[j]);
		if(visible.get(i)[j] == true) {
			piece.makeVisible();
			System.out.println(piece.isVisible());
		}
		return piece;
	}
	
    /**
     * This method sends a move from one device to the other through the Server.  When a User makes a move,
     * the server sends and shows that move to the other USer, so that the game is updating on the screens of
     * both Users.
     * @param pieceNameArray, an ArrayList containing the names of the game pieces on the board
     * @param pieceColorArray, an ArrayList containing the colors of the game pieces on the board
     * @param pieceVisArray, an ArrayList which states which pieces on board are visible(face-up_ and which aren't
     * @param opponent, a string containing the nickname of the User who did not just make the move
     * @param gameID, the unique integer value assigned to the game, which just saw the move
     * @throws IOException
     */
	public void sendMove(List<String[]> pieceNameArray, List<String[]> pieceColorArray,
		List<boolean[]> pieceVisArray, String opponent, int gameID) throws IOException {

		SendMove sendM = new SendMove();
		sendM.setPlayerName((byte)opponent.getBytes().length, opponent.getBytes());
		sendM.setTurn(false);
		sendM.setGameID(gameID);

		setSendMoveNameInfo(pieceNameArray, sendM);

		List<ArrayList<byte[]>> pieceColorBytes = new ArrayList<ArrayList<byte[]>>();
		setSendMoveColorInfo(pieceColorArray, pieceVisArray, sendM);
	}

	private void setSendMoveColorInfo(List<String[]> pieceColorArray, List<boolean[]> pieceVisArray, SendMove sendM) throws IOException {
		for (int i = 0; i < pieceColorArray.size(); i++) {
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] cN = new byte[4];
			boolean[] v = pieceVisArray.get(i);
			
			for (int j = 0; j < pieceColorArray.get(i).length; j++) {
				byte cLength = (byte) pieceColorArray.get(i)[j].getBytes().length;
				cN[j] = cLength;
				byteList.add(pieceColorArray.get(i)[j].getBytes());
			}
			
			if (i == 0) {
				sendM.setColorLengths0(cN);
				sendM.setColor0(byteList);
				sendM.setVis0(v);
			} else if (i == 1) {
				sendM.setColorLengths1(cN);
				sendM.setColor1(byteList);
				sendM.setVis1(v);
			} else if (i == 2) {
				sendM.setColorLengths2(cN);
				sendM.setColor2(byteList);
				sendM.setVis2(v);
			} else if (i == 3) {
				sendM.setColorLengths3(cN);
				sendM.setColor3(byteList);
				sendM.setVis3(v);
			} else if (i == 4) {
				sendM.setColorLengths4(cN);
				sendM.setColor4(byteList);
				sendM.setVis4(v);
			} else if (i == 5) {
				sendM.setColorLengths5(cN);
				sendM.setColor5(byteList);
				sendM.setVis5(v);
			} else if (i == 6) {
				sendM.setColorLengths6(cN);
				sendM.setColor6(byteList);
				sendM.setVis6(v);
			} else if (i == 7) {
				sendM.setColorLengths7(cN);
				sendM.setColor7(byteList);
				sendM.setVis7(v);
			}

		}
		connection.sendMessage(sendM.getBytes());
	}

	private void setSendMoveNameInfo(List<String[]> pieceNameArray, SendMove sendM) {
		for (int i = 0; i < pieceNameArray.size(); i++) {
			ArrayList<byte[]> byteList = new ArrayList<byte[]>();
			byte[] nN = new byte[4];
			for (int j = 0; j < pieceNameArray.get(i).length; j++) {
				byte nLength = (byte) pieceNameArray.get(i)[j].getBytes().length;
				nN[j] = nLength;
				byteList.add(pieceNameArray.get(i)[j].getBytes());
			}
			if (i == 0) {
				sendM.setNameLengths(nN);
				sendM.setNames0(byteList);
			} else if (i == 1) {
				sendM.setNameLengths1(nN);
				sendM.setNames1(byteList);
			} else if (i == 2) {
				sendM.setNameLengths2(nN);
				sendM.setNames2(byteList);
			} else if (i == 3) {
				sendM.setNameLengths3(nN);
				sendM.setNames3(byteList);
			} else if (i == 4) {
				sendM.setNameLengths4(nN);
				sendM.setNames4(byteList);
			} else if (i == 5) {
				sendM.setNameLengths5(nN);
				sendM.setNames5(byteList);
			} else if (i == 6) {
				sendM.setNameLengths6(nN);
				sendM.setNames6(byteList);
			} else if (i == 7) {
				sendM.setNameLengths7(nN);
				sendM.setNames7(byteList);
			}
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
	
    /**
     * Send a password, entered on the UserNode through command line, to the Server
     * to be checked for it's connection to a registered User in the system.
     * @param password, the password to be sent from the UserNode to the Server
     * @throws IOException if the password cannot be read
     */
	public void sendPassword(String password) throws IOException {
		//System.out.println("sending password to server");
		SendPassword sendPass = new SendPassword();
		sendPass.setPassword((byte)password.getBytes().length, password.getBytes());
		connection.sendMessage(sendPass.getBytes());
	}

    /**
     * Creates a UserProfile for a User by taking in information from command line that the UserNode takes,
     * and sends it over the existing connection.
     * This comes in when a UserNode selects to 'Create Profile' through the terminal and inputs credentials.
     * @param nickname, the nickname of the User who is to have a UserProfile made
     * @param email, the email associated with the User and soon to be UserProfile
     * @param password, the password associated with the User and soon to be UserProfile
     * @throws IOException, in the case of input/output errors
     */
	public void createProfile(String nickname, String email, String password) throws IOException {
		//System.out.println("sending profile to server");
		CreateProfile profile = new CreateProfile();
		profile.setNickname((byte)nickname.getBytes().length, nickname.getBytes());
		profile.setEmail((byte)email.getBytes().length, email.getBytes());
		profile.setPassword((byte)password.getBytes().length, password.getBytes());
		connection.sendMessage(profile.getBytes());
	}
	
    /**
     * Send a message to the Server that the UserNode wants to disconnect from the Server (log off).
     * This takes place when a UserNode gets 'exit' entered via command line.
     * @throws IOException in the case of an input/output error
     */
	public void logOff() throws IOException {
		SendLogOff sendOff = new SendLogOff();
		connection.sendMessage(sendOff.getBytes());
	}
	
    /**
     * 
     * @return gamesInvitedTo, an ArrayList of all the games that have invites sent out for them
     */
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
