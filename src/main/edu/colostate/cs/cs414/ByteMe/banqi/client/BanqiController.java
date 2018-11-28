package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BanqiController {

	private String profilesFile;
	//stores all created UserProfiles
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	protected static List<User> users = new ArrayList<User>();
	User U;
	
	//read user inputs
	private static BufferedReader read;

	//constructor
	public BanqiController(String file) {
		this.profilesFile = file;
	}
	
	/* Reads a file for a User  and adds the contents to the list of User Profiles.
	The file contains the unique account details as well as the user's game performance.
	With all of the data from this file, a new User Profile gets created.
	*/
	private void readUsers() throws IOException {
		FileReader file = new FileReader(profilesFile);
		BufferedReader buff = new BufferedReader(file);
		String line = null;
		while ((line = buff.readLine()) != null) {
			String name = "";
			String email = "";
			String pass = "";
			String date = "";
			int wins = 0;
			int losses = 0;
			int draws = 0;
			int forf = 0;
			String[] parts = line.split(" ");
			for (int i = 0; i < parts.length; i++) {
				if (i == 0) {
					name = parts[i];
				} else if (i == 1) {
					email = parts[i];
				} else if (i == 2) {
					pass = parts[i];
				} else if (i == 3) {
					date = parts[i] + " ";
				} else if (i == 4) {
					date += parts[i];
				} else if (i == 5) {
					wins = Integer.parseInt(parts[i]);
				} else if (i == 6) {
					losses = Integer.parseInt(parts[i]);
				} else if (i == 7) {
					draws = Integer.parseInt(parts[i]);
				} else if (i == 8) {
					forf = Integer.parseInt(parts[i]);
				} else {
					System.out.println("There shouldn't be anything more on this line");
				}
			}
			UserProfile us = new UserProfile(name, email, pass, date, wins, losses, draws, forf);
			listOfProfiles.add(us);
			User user = new User(us);
			users.add(user);
		}
		buff.close();
	
	}

	/* The method controls the entrance into anf exit from the game system as well as registration.
	While controlling the running of the system, this calls upon other methods to do these tasks.
	It allows for and connects to the following tasks:
	  - Log in (for exsiting users) [enter '1']
	  - Create an account/profile (for new users) [enter '2']
	  - Log out [enter 'exit']
	If the input is not one of the previous options, the user is prompted that the input is not recognized.
	*/
	private void runProgram() throws IOException {
		readUsers();
		String choice;
		read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Banqi game!");
		boolean b = false;
		while (b == false) {
			System.out.println("To log in enter '1' and press Enter");
			System.out.println("To create a profile, enter '2' and press Enter");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();

			// attempt log-in
			if (choice.equals("1")) {
				initialize();
				b = true;
			} else if (choice.equals("2")) {
				makeNewUser();
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
		// user has logged in
		b = false;
		while (!b) {
			System.out.println("\n1) Play existing game");
			System.out.println("2) Manage invites");
			System.out.println("3) View profile");
			System.out.println("To exit, type 'exit' and press Enter");
			
			choice = read.readLine();
			if (choice.equals("1")) {
				
			} else if (choice.equals("2")) {
				
			} else if (choice.equals("3")) {
				viewProfile();
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
		read.close();
	}
	
	private void viewProfile() throws IOException {
		boolean b = false;
		String choice;
		U.seeProfile(U.getNickname());
		while (!b) {			
			System.out.println("\n1) Search for player");
			System.out.println("To exit, type 'exit' and press Enter");
			
			choice = read.readLine();
			if (choice.equals("1")) {
				boolean c = false;
				while (!c) {
					System.out.println("Type the nickname of the user to view");
					choice = read.readLine();
					for (User user : users) {
						if (user.getNickname().equals(choice)) {
							user.seeProfile(user.getNickname());
							c = true;
							break;
						}
					}
					System.out.println("User not found");
				}
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
	}

	/* This method is called from the runProgram method, for when a User enters '1' for login,
	the runProgram method calls this, asking the User to enter their nickname.
	The following checks are done:
	  - If a profile exists with that nickname, log-in
	  - Else, the User is prompted that they have no profile and logging in ends*/
	public void initialize() throws IOException {
//		System.out.println(listOfProfiles.size());
//		for(UserProfile t : listOfProfiles) {
//			System.out.println(t.getUserName());
//		}
		String name = "";
		System.out.println("Please Enter your nickname");
		name = read.readLine();
		System.out.println("name entered is: " + name);
		UserProfile profile = getOwnUser(name);
//		System.out.println("new Profile: " + profile);
		if (profile != null) {
			U = new User(profile);
		} else {
			System.out.println("We could not find a profile with name of: " + name);
		}
	}

	/* This method permits a User to view their own Profile, so they can see their game stats.
	With the entrance of nickname that exists in the system, 
	the User Profile associated with that nickname is presented to the User.
	*/
	private UserProfile getOwnUser(String nickname) throws IOException {
		for (UserProfile prof : listOfProfiles) {
//			System.out.println("UserName is: " + prof.getUserName());
			if (prof.getUserName().equals(nickname)) {
				// log in, by entering password
				boolean success = enterCredentials(prof);
				if (success) {
					System.out.println("Welcome User " + prof.getUserName() + "!");
					return prof;
				}
			}
		}
		return null;
	}

	/*This method verifies the credentials of a User, to ensure they are registered in the system.
	In addition it implements a security measure, by giving the User no more than three attempts
	to enter a valid password.  The verification process is cancelled
	if the User does not input a password associated with the given nickname.
	*/
	private boolean enterCredentials(UserProfile prof) throws IOException {
		int count = 0;
		while (count < 3) {
			System.out.println("Please enter your password");
			String password = read.readLine();
//			System.out.println(prof.getPassword());
			if(!(password.equals(prof.getPassword()))) {
				count++;
				System.out.println("Password was incorrect, you have " + (3 - count) + " attempts remaining");
			}
			else {
				return true;
			}
		}
		return false;
	}

	/* This implements the functionality needed to register a new User.
	The User enters:
	  - a unique nickname (not in the system)
	  - an email
	  - a valid password, entered twice for verification
	The system takes care:
	  - date/time of the account creation
	  - creating the new User Profile */
	private void makeNewUser() throws IOException {
		boolean passMatch = false;
		String nickname = "";
		String email = "";
		String password = "";

		// Enter data using BufferReader
		System.out.println("Please Enter a Nickname:");
		nickname = read.readLine();

		System.out.println("Please Enter an Email Address:");
		email = read.readLine();

		while (!passMatch) {
			System.out.println("Please Enter a Password:");
			password = read.readLine();

			System.out.println("Please Re-enter Your Password:");
			String reEntered = read.readLine();

			if (password.equals(reEntered)) {
				passMatch = true;
			} else {
				System.out.println("Passwords Do Not Match!");
			}
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		UserProfile newUser = new UserProfile(nickname, email, password, dtf.format(now), 0, 0, 0, 0);
		listOfProfiles.add(newUser);
		users.add(new User(newUser));
//		System.out.println(listOfProfiles.size());
		writeToFile(newUser);

	}

	/*This writes a new file to the storage system, in order to record the current details
	of a User Profile.  The file will contain everything on the User Profile:
	  - nickname, email, password, registration date
	  - wins, losses, draws, forfeits
	This file will be stored with the records of the Banqi Game system.
	*/
	private void writeToFile(UserProfile u) {
		String s = u.getUserName() + " " + u.getEmail() + " " + u.getPassword() + " " + u.getJoinedDate()
		 + " " + u.getWins() + " " + u.getLosses() + " " + u.getDraws() + " " + u.getForfeits();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(profilesFile, true));
			out.write(s);
			out.close();
		} catch(IOException e) {
			
		}
		
	}
	
	/*This function returns a user object given the users nickname. Null is returned if not found.
	*/
	public static User getUser(String nickname) {
		for (User user : users) {
			if (user.getNickname().equals(nickname)) {
				return user;
			}
		}
		return null;
	}
	
	public static void main(String args[]) throws IOException {
		BanqiController banqi = new BanqiController(args[0]);
		//UserProfile up1 = new UserProfile("scoobs", "scoobs@email.com", "pass", "2018/10/27 17:45:45", 0,0,0,0);
		//UserProfile up2 = new UserProfile("Brian", "firefox@rams.colostate.edu", "123", "2018/11/28 13:27:42", 0,0,0,0);
		banqi.runProgram();
	}

}
