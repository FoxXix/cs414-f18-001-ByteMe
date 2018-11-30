package main.edu.colostate.cs.cs414.ByteMe.banqi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

import main.edu.colostate.cs.cs414.ByteMe.banqi.server.User;
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
	
	public void messagingCommands(User user) throws IOException {
		while (true) {
//			System.out.print("Enter a command: ");
			System.out.println("Welcome to Banqi game!");
			System.out.println("To log in enter '1' and press Enter");
			System.out.println("To create a profile, enter '2' and press Enter");
			System.out.println("To exit, type 'exit' and press Enter");

			String choice = inputScanner.nextLine();

			// attempt log-in
			if (choice.equals("1")) {
				System.out.println("Please enter your Nickname");
				String name = inputScanner.nextLine();
				user.logIn(name);
				
			} else if (choice.equals("2")) {
				System.out.println(choice);
				// makeNewUser();
			} else if (choice.equals("exit")) {

			} else {
				System.out.println("Input not recognized");
			}

			String input = inputScanner.nextLine();
			String[] args = input.split(" ");

			if (args[0].equals("print-counters-and-diagnostics")) {
				System.out.println("printing counters and diagnostics");
				// print the information about the number of messages send/received/relayed
//				message.printCounters();
			}

			else {
				continue;
			}
		}

	}
}