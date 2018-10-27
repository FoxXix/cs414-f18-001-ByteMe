package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BanqiController {
	
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	
	public static void main(System args[]) throws IOException {
		BanqiController banqi = new BanqiController();
		banqi.initialize();
	}
	
	public BanqiController() {
		
	}
	
	//login mechanism - if your profile exists, log-in
	//else - create profile
	public void initialize() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to Banqi game!");
		boolean b = false;
		while (!b) {
			System.out.println("To log in press 1");
			System.out.println("To create a profile, press 2");
			
			String choice = read.readLine();
			
			if(choice.equals("1")) {
				System.out.println("Please Enter your nickname");
				String name = read.readLine();
				UserProfile user = getOwnUser(name);
			}
		}
		read.close();
	}

	
	//load your personal user profile
	//if it does not exist, create a new UserProfile
	public UserProfile getOwnUser(String nickname) {
		boolean found = false;
		for(UserProfile prof : listOfProfiles) {
			if(prof.getUserName().equals(nickname)) {
				//log in, by entering password
				enterCredentials();
			}
		}
		return null;
	}

	private void enterCredentials() {
		// TODO Auto-generated method stub
		
	}
	
	//creates new userProfile and adds the profile to the listOfProfiles 
	private void makeNewUser() throws IOException {
		boolean passMatch = false;
		String nickname = "";
		String email = "";
		String password = "";
		
		//Enter data using BufferReader 
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
        
        System.out.println("Please Enter a Nickname:");
        nickname = reader.readLine();
        
        System.out.println("Please Enter an Email Address:");
        email = reader.readLine();
        
        while(!passMatch) {
            System.out.println("Please Enter a Password:");
            password = reader.readLine();
            
            System.out.println("Please Re-enter Your Password:");
            String reEntered = reader.readLine();
            
            if(password.equals(reEntered)) {
            	passMatch = true;
            }
            else {
            	System.out.println("Passwords Do Not Match!");
            }
        }
        reader.close();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  

		UserProfile newUser = new UserProfile(nickname, email, password, dtf.format(now));
		listOfProfiles.add(newUser);
		
	}

}
