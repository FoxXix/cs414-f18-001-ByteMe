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
	//store all created UserProfiles
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	User U;
	
	//read user inputs
	private static BufferedReader read;

	public static void main(String args[]) throws IOException {
		BanqiController banqi = new BanqiController(args[0]);
		banqi.runProgram();
	}

	//constructor
	public BanqiController(String file) {
		this.profilesFile = file;
	}
	
	// reads the Users file and adds them to the list of Profiles
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
		}
		buff.close();
	
	}

	//Method that runs the program to completion
	private void runProgram() throws IOException {
		readUsers();
		
		read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Banqi game!");
		boolean b = false;
		while (b == false) {
			System.out.println("To log in enter '1' and press Enter");
			System.out.println("To create a profile, enter '2' and press Enter");
			System.out.println("To exit, type 'exit' and press Enter");

			String choice = read.readLine();

			// attempt log-in
			if (choice.equals("1")) {
				initialize();
			} else if (choice.equals("2")) {
				makeNewUser();
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
		read.close();
	}

	// login mechanism - if your profile exists, log-in
	// else - create profile
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

	// load your personal user profile
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

	//have user enter password and check that it matches the given password in UserProfile
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

	// creates new userProfile and adds the profile to the listOfProfiles
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
//		System.out.println(listOfProfiles.size());
		writeToFile(newUser);

	}

	//write the new profile to the master file
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

}
