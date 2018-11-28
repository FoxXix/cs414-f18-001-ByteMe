package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPCache;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPServerThread;
import main.edu.colostate.cs.cs414.ByteMe.banqi.util.CommandParser;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.EventFactory;
//import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
//import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
//import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
//import cs455.overlay.wireformats.OverlayNodeSendsData;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Protocol;
//import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.RegistryReportsRegistrationStatus;
//import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
//import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
//import cs455.overlay.wireformats.RegistrySendsNodeManifest;
//import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendDeregistration;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.SendRegistration;

import java.io.*;

public class User extends Node{

	private static TCPServerThread clientSock = null;
	private static EventFactory eventFactory;
	private static int port = -1;
	private static int nodeID = -1;
	private static Socket client = null;
	private static Map<Integer, Tuple<byte[], Integer>> relayTable = new HashMap<Integer, Tuple<byte[], Integer>>();
	private int[] allNodes = null;
	private int[] routingTable = null;
	Thread t = null;
	TCPCache cache = null;
	private volatile int sendTracker = 0;
	private volatile int receiveTracker = 0;
	private volatile int packetsRelayed = 0;
	private volatile long sendSummation = 0;
	private volatile long receiveSummation = 0;
	
	private int prevSendTracker = 0;
	private int prevReceiveTracker = 0;
	private int prevPacketsRelayed = 0;
	private long prevSendSummation= 0;
	private long prevReceiveSummation= 0;
	TCPConnection connection = null;
	
	private void Initialize(String serverName, int port) throws IOException
	{
		this.eventFactory = new EventFactory(this);
		clientSock = new TCPServerThread(0);
//		Thread t  = new Thread(clientSock);
//		t.start();
		
		new Thread (() -> new CommandParser().messagingCommands(this)).start();

		sendRegMessage(serverName, port);
	}
	
	public static void main(String[] args) {
		String serverName = args[0];
		port = Integer.parseInt(args[1]);
		
		User messageNode = new User();
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
//		case Protocol.RegistrySendsNodeManifest:
//			RegistrySendsNodeManifest nodeManifest = (RegistrySendsNodeManifest) e;
//			cache = new TCPCache();
//			byte size = nodeManifest.getSize();
//			connectRoutingTable(size, nodeManifest, connect);
////			System.out.println("routing table size is: " + size);
//			break;
//		case Protocol.RegistryRequestsTaskInitiate:
//			RegistryRequestsTaskInitiate taskInit = (RegistryRequestsTaskInitiate) e;
//			int packToSend = taskInit.getNumPacketsToSend();
//			sendPackets(packToSend, connect);
//			break;
//		case Protocol.OverlayNodeSendsData:
//			OverlayNodeSendsData sendsData = (OverlayNodeSendsData) e;
////			System.out.println("sink from unpack: " + sendsData.getDestination());
//			relayMessage(sendsData.getDestination(), sendsData.getSource(), sendsData.getPayload(), sendsData.getNodesTraversed());
//			break;
		case Protocol.RegistryReportsDeregistrationStatus:
//			RegistryReportsDeregistrationStatus rrd = (RegistryReportsDeregistrationStatus) e;
			System.out.println("Exiting Overlay");
			System.exit(0);
		}		
	}

	
//	private void sendPackets(int num, TCPConnection registryConnection) throws IOException {
////		System.out.println(Arrays.toString(allNodes));
////		System.out.println(Arrays.toString(routingTable));
////		System.out.println(nodeID);
//		Random randomGenerator = new Random();
//		Random packetGenerator = new Random();
//		int rand = 0;
//		int packet = 0;
//		int sinkID = 0;
//		int sendToID = 0;
//		int sourceID = nodeID;
//		boolean unique = false;
//		OverlayNodeSendsData sendDataNode = new OverlayNodeSendsData();
//		TCPConnection connect;
//		for (int i = 0; i < num; i++) {
//			while ((sinkID = allNodes[randomGenerator.nextInt(allNodes.length)]) == nodeID); 
////				rand = randomGenerator.nextInt(allNodes.length);
////				System.out.println("Random number generated: " + rand);
//					packet = packetGenerator.nextInt();
////					System.out.println("source and destination id's" + sourceID + " "
////					+ sinkID );
//			
//			int sinkIndex = getIndex(sinkID);
//			int sourceIndex = getIndex(sourceID);
//			
////			System.out.println("SourceIndex: " + sourceIndex + " " + "SinkIndex: " + sinkIndex);
//			
//			sendToID = findDestination(sourceIndex, sinkIndex);
//			
//			int[] dissTrace = new int[1];
//			dissTrace[0] = nodeID;
////			System.out.println("sending message: " + packet+ " sink is: " + sinkID);
//			connect = cache.getById(sendToID);
//
//			sendDataNode.setInfo(sinkID, sourceID, packet, dissTrace.length, dissTrace);
//			connect.sendMessage(sendDataNode.getBytes());
//			
//			//synchronize the counters
//			synchronized(this) {
//				sendTracker++;
//				sendSummation += packet;
//			}
//		}
////		System.out.println("SendTracker :" + sendTracker);
////		System.out.println(sendSummation);
////		
//		//have thread sleep to allow rest of packets to come through pipeline
//		try {
//			t.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//send the message that node has completed sending its messages
//
//		OverlayNodeReportsTaskFinished onrtf = new OverlayNodeReportsTaskFinished();
//		onrtf.setInfo(InetAddress.getLocalHost().getAddress(), port, nodeID);
//		registryConnection.sendMessage(onrtf.getBytes());
//	}
	
//	//deregister from the overlay
//	public void deregister() throws IOException {
//		SendDeregistration dereg = new SendDeregistration();
//		dereg.setInfo(InetAddress.getLocalHost().getAddress(), port, nodeID);
//		connection.sendMessage(dereg.getBytes());
//	}
	
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