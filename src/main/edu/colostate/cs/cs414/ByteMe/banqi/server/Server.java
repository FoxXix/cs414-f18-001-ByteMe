package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.Route;
import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.RoutingTable;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPSender;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.LogIn;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.NicknameDoesNotExist;
//import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
//import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
//import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
//import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RequestPassword;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import cs455.overlay.wireformats.SendDeregistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Server extends Node {
	
	private BanqiController banqiController;
	private List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	private List<BanqiGame> listCurrentGames;
	
	private EventFactory eventFactory;
	private static int[] nodeIds = new int[128];
	private static Map<Integer, Tuple<byte[], Integer>> nodesRegistered = new HashMap<Integer, Tuple<byte[], Integer>>();
	private static TCPServerThread server = null;;
	ArrayList<RoutingTable> routeTables = null;
	int[] allMessNodes = null;
	TCPCache cache = null;
	
	private void Initialize(int port) throws IOException
	{	
		
		new Thread (() -> new CommandParser().registryCommands(this)).start();
//		new Thread (() -> new CommandParser().registryCommands(this)).start();
		eventFactory = new EventFactory(this);
		server = new TCPServerThread(port);
		this.routeTables = new ArrayList<RoutingTable>();
		this.cache = new TCPCache();
		//read in all of the profiles
		banqiController = new BanqiController("/s/bach/l/under/sporsche/cs414/Banqi/UserProfiles.txt");
		banqiController.readUsers();
		listOfProfiles = banqiController.getListProfiles();
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
//			System.out.println("Nickname is:::::::" + nickname);
			if(checkNickNameExists(nickname)) {
//				System.out.println("nickname exists");
				RequestPassword reqPass = new RequestPassword();
				connect.sendMessage(reqPass.getBytes());
			}
			else {
				NicknameDoesNotExist nomatch = new NicknameDoesNotExist();
				connect.sendMessage(nomatch.getBytes());
			}
//		case Protocol.NodeReportsOverlaySetupStatus:
//			NodeReportsOverlaySetupStatus nR = (NodeReportsOverlaySetupStatus) e;
//			int stat = nR.getStatus();
//			byte[] info = nR.getInfoString();
//			String s = new String(info);
////			System.out.println(s + stat);
//			break;
//		case Protocol.OverlayNodeReportsTaskFinished:
//			OverlayNodeReportsTaskFinished taskFinished = (OverlayNodeReportsTaskFinished) e;
//			checkCompletion(taskFinished.getID());
//			break;
//		case Protocol.SendDeregistration:
//			SendDeregistration dereg = (SendDeregistration) e;
//			RegistryReportsDeregistrationStatus rrds = new RegistryReportsDeregistrationStatus();
////			System.out.println("saldjflskdf" + this.cache.getById(dereg.getNodeId()));
//			this.cache.getById(dereg.getNodeId()).sendMessage(rrds.getBytes());
//			this.cache.remove(dereg.getNodeId());
//			this.nodesRegistered.remove(dereg.getNodeId());
//			break;
		}

	}
	
	public static int registerNode(byte type, byte[] address, int port) {
		byte sentType = type;
		byte[] sentAddress = address;
		int sentPort = port;
		boolean unique = false;
		int rand = 0;
		
//		System.out.println("sent address is: " + sentAddress);
//		System.out.println("original ip Address is: " + originalAddress);
//		System.out.println("port is: " + sentPort);
		
		//new node is trying to register, check the IPAddress given vs the IP address from the socket
		//if match, generate random number between 0 and 127, and store the info in (hashmap?)
//		System.out.println("they are the same");

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

	public static void deregisterNode(byte typeRec, byte[] ipAddrRec, int portNumRec, int nodeIDRec) {
		//remove the entry in the hashmap that corresponds to nodeIDRec
	}
	
//	public void sendPackets(int numPackets) throws IOException {
//		
//		for (Integer i = 0; i < allMessNodes.length; i++) {
//			TCPConnection connect = cache.getById(allMessNodes[i]);
//			RegistryRequestsTaskInitiate initiateTask = new RegistryRequestsTaskInitiate();
//			initiateTask.setInfo(numPackets);
//			connect.sendMessage(initiateTask.getBytes());
//		}
//	}
	
	
	public void printListOfNodes() {
		for (Entry<Integer, Tuple<byte[], Integer>> key : nodesRegistered.entrySet()) {
			Integer k = key.getKey();
			Tuple<byte[], Integer> value = key.getValue();
			byte[] ip = value.s1;
			int p = value.s2;
			System.out.println(k + " " + ip + " " + p);
		}
	}
	
	// reads the Users file and adds them to the list of Profiles
//	public void readUsers() throws FileNotFoundException, IOException {
//		FileReader file = new FileReader("/s/bach/l/under/sporsche/cs414/Banqi/UserProfiles.txt");
//		BufferedReader buff = new BufferedReader(file);
//		String line = null;
//		while ((line = buff.readLine()) != null) {
//			String name = "";
//			String email = "";
//			String pass = "";
//			String date = "";
//			int wins = 0;
//			int losses = 0;
//			int draws = 0;
//			int forf = 0;
//			String[] parts = line.split(" ");
//			for (int i = 0; i < parts.length; i++) {
//				if (i == 0) {
//					name = parts[i];
//				} else if (i == 1) {
//					email = parts[i];
//				} else if (i == 2) {
//					pass = parts[i];
//				} else if (i == 3) {
//					date = parts[i] + " ";
//				} else if (i == 4) {
//					date += parts[i];
//				} else if (i == 5) {
//					wins = Integer.parseInt(parts[i]);
//				} else if (i == 6) {
//					losses = Integer.parseInt(parts[i]);
//				} else if (i == 7) {
//					draws = Integer.parseInt(parts[i]);
//				} else if (i == 8) {
//					forf = Integer.parseInt(parts[i]);
//				} else {
//					System.out.println("There shouldn't be anything more on this line");
//				}
//			}
//			UserProfile us = new UserProfile(name, email, pass, date, wins, losses, draws, forf);
//			listOfProfiles.add(us);
//		}
//		buff.close();
//
//	}
}