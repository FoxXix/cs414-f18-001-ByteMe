package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.Route;
import main.edu.colostate.cs.cs414.ByteMe.banqi.routing.RoutingTable;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPSender;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
//import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
//import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
//import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
//import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import cs455.overlay.wireformats.SendDeregistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Server extends Node {
	
	
	private EventFactory eventFactory;
	private static byte[] originalAddress = null;
	private static int[] nodeIds = new int[128];
	private static Map<Integer, Tuple<byte[], Integer>> nodesRegistered = new HashMap<Integer, Tuple<byte[], Integer>>();
	private static TCPServerThread server = null;
	private static byte[] regInfoString = null;
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
}