package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.Route;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.RoutingTable;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPSender;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.CreateProfile;
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
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendLogOff;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendInvite;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendPassword;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import cs455.overlay.wireformats.SendDeregistration;

import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.ValidProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendUser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Server extends Node {
	
	private BanqiController banqiController;
	private List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	private List<String> listOfUsersByNickname = new ArrayList<String>();
	private Map<BanqiGame, Map<String, String>> listCurrentGames = new HashMap<BanqiGame, Map<String, String>>();
	private Map<String, String> listOfInvites = new HashMap<String, String>();
	private Map<String, Integer> nameToNode = new HashMap<String, Integer>();
//	private List<User> listOfUsers = new ArrayList<User>();
	
	private EventFactory eventFactory;
	private static int[] nodeIds = new int[128];
	private static Map<Integer, Tuple<byte[], Integer>> nodesRegistered = new HashMap<Integer, Tuple<byte[], Integer>>();
	private static TCPServerThread server = null;;
//	ArrayList<RoutingTable> routeTables = null;
	int[] allMessNodes = null;
	TCPCache cache = null;
	
	//is it fixed?
	private void Initialize(int port) throws IOException
	{	
		
		new Thread (() -> new CommandParser().registryCommands(this)).start();

		eventFactory = new EventFactory(this);
		server = new TCPServerThread(port);
//		this.routeTables = new ArrayList<RoutingTable>();
		this.cache = new TCPCache();
		//read in all of the profiles
		banqiController = new BanqiController("/home/brian/Documents/CS414/Banqi/UserProfiles.txt");
		banqiController.readUsers();
		listOfProfiles = banqiController.getListProfiles();
		
		//for each user, put the nickname into a new list of Strings
		for(UserProfile u : listOfProfiles) {
			listOfUsersByNickname.add(u.getUserName());
		}
		
//		listOfUsers = banqiController.getListUsers();
//		System.out.println(listOfProfiles);
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
//		System.out.println("In OnEvent for RegistryServer");
		
		byte type = e.getType();
		
		switch (type) {
		case Protocol.SendRegistration:
			SendRegistration sr = (SendRegistration) e;
			int iD = registerNode(sr.getType(), sr.getIpAddr(), sr.getPortNumber());
//			System.out.println("adding id and connection to cache" + connect);
			cache.addMap(iD, connect);
			RegistryReportsRegistrationStatus regStatus = new RegistryReportsRegistrationStatus();
			String message = "Registration request successful. The number of Users currently registered"
					+ "is (" + nodesRegistered.size() + ")";
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
			if(checkNickNameExists(nickname)) {
//				System.out.println("nickname exists");
				if(checkPassword(nickname, password)) {
//					System.out.println("Password is correct");
					//store the nickname with the nodeID in map
					nameToNode.put(nickname, lIn.getNodeId());
					for(Map.Entry<String, Integer> g : nameToNode.entrySet()) {
						System.out.println(g);
						System.out.println(nameToNode.get(nickname));
						System.out.println(cache.getById(nameToNode.get(nickname)));
						System.out.println("\n");
					}
					//return the User
					for(UserProfile prof : listOfProfiles) {
						if(prof.getUserName().equals(nickname)) {
							System.out.println("creating user to send to UserNode");
							SendUser sendU = new SendUser();
							sendU.setInfo(prof.getUserName().getBytes(), (byte)prof.getUserName().getBytes().length, prof.getEmail().getBytes(), (byte)prof.getEmail().getBytes().length,
									prof.getPassword().getBytes(), (byte)prof.getPassword().getBytes().length, prof.getJoinedDate().getBytes(), (byte)prof.getJoinedDate().getBytes().length,
									prof.getWins(), prof.getLosses(), prof.getDraws(), prof.getForfeits());
							Byte[] namesLengths = new Byte[listOfUsersByNickname.size()];
							List<byte[]> names = new ArrayList<byte[]>();
							for(int i = 0; i < namesLengths.length; i++) {
								namesLengths[i] = (byte)listOfUsersByNickname.get(i).getBytes().length;
								byte[] toByte = listOfUsersByNickname.get(i).getBytes();
								names.add(toByte);
							}
							sendU.setListUsers(namesLengths, names);
							connect.sendMessage(sendU.getBytes());
						}
					}
				}
			}
			else {}
			break;
		case Protocol.SendInvite:
			SendInvite invite = (SendInvite) e;
			//get inviter
			byte[] invF = invite.getInviteFrom();
			String inviteFrom = new String(invF);
			//get invitee
			byte[] invi = invite.getInvitee();
			String invitee = new String(invi);
			//add to hash map Invitee is key, invite from is value
			System.out.println("invitee: " + invitee);
			System.out.println("invite from: : " + inviteFrom);
			listOfInvites.put(invitee, inviteFrom);
			//send invite if invitee is logged on
			if(nameToNode.containsKey(invitee)) {
				sendInvite(invitee);
			}

			//****************************************************************
			//write invite to file so that we can access them later!!
			//****************************************************************
			break;
		case Protocol.CreateProfile:
			CreateProfile cp = (CreateProfile) e;
			System.out.println("Inside createProfile");
			byte[] n = cp.getNickname();
			String pname = new String(n);
			byte[] em = cp.getEmail();
			String email = new String(em);
			byte[] p = cp.getPassword();
			String passw = new String(p);
			ValidProfile vp = new ValidProfile();
			
			if(checkNickNameExists(pname)) {
				System.out.println("Nickname already exists");
				vp.setValidProfile(false);
				connect.sendMessage(vp.getBytes());
				break;
			}
			if(checkEmailExists(email)) {
				System.out.println("Email already exists");
				vp.setValidProfile(false);
				connect.sendMessage(vp.getBytes());
				break;
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			UserProfile newUser = new UserProfile(pname, email, passw, dtf.format(now), 0, 0, 0, 0);
			listOfProfiles.add(newUser);
			//users.add(new User(newUser));
			writeToFile(newUser);		
			vp.setValidProfile(true);
			System.out.println("About to send back to usernode");
			connect.sendMessage(vp.getBytes());			
			break;
		case Protocol.SendAccept:
			SendAccept acc = (SendAccept) e;
			byte[] accep = acc.getInvitee();
			String acceptor = new String(accep);
			byte[] sen = acc.getInviteFrom();
			String sender = new String(sen);
			//start a new game
			startNewGame(sender, acceptor);
      break;
		case Protocol.SendLogOff:
			SendLogOff lOut = (SendLogOff) e;
			RegistryReportsDeregistrationStatus deregStatus = new RegistryReportsDeregistrationStatus();
			connect.sendMessage(deregStatus.getBytes());
      break;
		}
	}
	
	/*This writes a new file to the storage system, in order to record the current details
	of a User Profile.  The file will contain everything on the User Profile:
	  - nickname, email, password, registration date
	  - wins, losses, draws, forfeits
	This file will be stored with the records of the Banqi Game system.
	*/
	private void writeToFile(UserProfile u) {
		String s = u.getUserName() + " " + u.getEmail() + " " + u.getPassword() + " " + u.getJoinedDate()
		 + " " + u.getWins() + " " + u.getLosses() + " " + u.getDraws() + " " + u.getForfeits() + "\n";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(banqiController.profilesFile, true));
			out.write(s);
			out.close();
		} catch(IOException e) {
			System.out.println("error writing file");
		}
		
	}
  
	public static int registerNode(byte type, byte[] address, int port) {
		byte sentType = type;
		byte[] sentAddress = address;
		int sentPort = port;
		boolean unique = false;
		int rand = 0;
		
		//new node is trying to register, check the IPAddress given vs the IP address from the socket
		//if match, generate random number between 0 and 127, and store the info in (hashmap?)


		Random randomGenerator = new Random();
		while (!unique) {
			rand = randomGenerator.nextInt(128);
//			System.out.println("Random number generated: " + rand);
			if (nodeIds[rand] != rand) {
				// this number hasn't been generated yet, add number to array
				nodeIds[rand] = rand;
				unique = true;
			} else {
//				System.out.println("random number has already been generated, need a new number");
			}
		}
		// generate an entry in hashmap?
		nodesRegistered.put(rand, new Tuple<byte[], Integer>(sentAddress, port));
//		System.out.println(nodesRegistered.size());
		for (Entry<Integer, Tuple<byte[], Integer>> key : nodesRegistered.entrySet()) {
			Integer k = key.getKey();
			Tuple<byte[], Integer> value = key.getValue();
			byte[] ip = value.s1;
			int p = value.s2;
//			System.out.println(k + " " + ip + " " + p);
		}
		
		return rand;

	}
	
	// load your personal user profile
	private boolean checkNickNameExists(String nickname) throws IOException {
		for (UserProfile prof : listOfProfiles) {
//			System.out.println("UserName is: " + prof.getUserName());
			if (prof.getUserName().equals(nickname)) {
				// log in, by entering password
				return true;
			}
		}
		return false;
	}
	
	private boolean checkEmailExists(String email) throws IOException {
		for (UserProfile prof : listOfProfiles) {
//			System.out.println("UserName is: " + prof.getUserName());
			if (prof.getEmail().equals(email)) {
				// log in, by entering password
				return true;
      }
    }
    return false;
  }

	private boolean checkPassword(String nickname, String password) {
		for(UserProfile prof : listOfProfiles) {
			if(prof.getUserName().equals(nickname)) {
				if(prof.getPassword().equals(password)) {
					return true;
				}
			}
		return false;
	}
	
	private void sendInvite(String invitee) throws IOException {

		TCPConnection connect = cache.getById(nameToNode.get(invitee));
		for(Map.Entry<String, String> entry : listOfInvites.entrySet()) {
			if(entry.getKey().equals(invitee)) {
				//get both values and send to the invitee node
				String invTo = invitee;
				String invFr = entry.getValue();
				SendInvite sendInv = new SendInvite();
				sendInv.setInvitee((byte)invTo.getBytes().length, invTo.getBytes());
				sendInv.setInviteFrom((byte)invFr.getBytes().length, invFr.getBytes());
				connect.sendMessage(sendInv.getBytes());
			}
			else {
				//do nothing
			}
		}
	}
	
	private void startNewGame(String sender, String acceptor) throws IOException {
		UserProfile sendProfile = null;
		UserProfile accepProfile = null;
		for(UserProfile up : listOfProfiles) {
			if(up.getUserName().equals(sender)) {
				sendProfile = up;
			}
			if(up.getUserName().equals(acceptor)) {
				 accepProfile = up;
			}
			else {
				System.out.println("A profile was not found, this shouldn't have happened!");
			}
		}
		User player1 = new User(sendProfile);
		User player2 = new User(accepProfile);
		BanqiGame game = new BanqiGame(player1, player2);
		game.setServer(this);
		game.setUpBoard();
		game.play();
		
	}
	
	// sends the state of the board to the other player, justPlayed is the name of the player has just taken a turn.
	// toPlay is the name of the player who plays next
	// parameters definitely subject to change
	public void sendBoardState(String justPlayed, String toPlay) {
		
	}

	public static void deregisterNode(byte typeRec, byte[] ipAddrRec, int portNumRec, int nodeIDRec) {
		//remove the entry in the hashmap that corresponds to nodeIDRec
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
