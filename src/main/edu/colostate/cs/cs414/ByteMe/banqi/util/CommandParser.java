package main.edu.colostate.cs.cs414.ByteMe.banqi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

import main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;
import main.edu.colostate.cs.cs414.ByteMe.banqi.server.Server;

public class CommandParser {
	
	Scanner inputScanner = new Scanner(System.in);

	//may not need these
	public void registryCommands(Server registry) {
		while(true) {
			
			System.out.print("Enter a command: ");
			String input = inputScanner.nextLine();
			String[] args = input.split(" ");
			
			if(args[0].equals("list-messaging-nodes")) { 
				System.out.println("Printing all messaging nodes");
				
				//print the list of Hashmaps (hostname, port-number, node ID)
				registry.printListOfNodes();
			}
			else if(args[0].equals("setup-overlay")) {
				int sizeRout = Integer.parseInt(args[1]);
				System.out.println("Setting up the overlay " + sizeRout);
				//reigstry should set up the overlay (Registry-Sends-Node _manifest)
//				try {
////					registry.setupOverlay((byte)sizeRout);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			else if(args[0].equals("list-routing-tables")) {
				System.out.println("Printing info about each routing table");
				//list information about routing table for each node
//				registry.printRoutingTables();
			}
			else {
				continue;
			}
		}
		
	}
	
}