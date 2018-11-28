package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
	
	private UserProfile userProfile;
	protected ArrayList<Invite> invites;
	
	private String nickname;
	private String email;
	private String password;
	
	public User(UserProfile user) {
		this.userProfile = user;
		this.nickname = user.getUserName();
	}

	public UserProfile seeProfile(String nickname) {
		User player = BanqiController.getUser(nickname);		
		System.out.println(nickname + "'s Profile\n");
		
		System.out.println("Joined: " + player.userProfile.getJoinedDate());
		System.out.println("Wins: " + player.userProfile.getWins());
		System.out.println("Losses: " + player.userProfile.getLosses());
		System.out.println("Draws: " + player.userProfile.getDraws());
		System.out.println("Forfeits: " + player.userProfile.getForfeits());
		
		return player.userProfile;
	}
	
	/*To be implemented: For purposes of security, the User will eventually need to enter their credentials
	in order to access components of the system associated with registered users.*/
	private void enterCredentials() {
		// TODO Auto-generated method stub
	}

	public void initiateGame(User invitee) {
		new BanqiGame(this, invitee);		
	}
	
	/*To be implemented: A User may invite any number of other Users to play a new Banqi Game
	by providing the nickname of the User they wish to invite.  While they can send out unlimited invites,
	only the first to accept an invitation can play the game.*/
	public void sendInvite(String nickname) {
		new Invite(this, nickname);
	}

	
	/*Prints out the invites for the current user*/
	public void getInviteStatus() {
		System.out.println("Current invites:\n");
		int count = 1;
		for (Invite invite : invites) {
			System.out.println(count + ") From: " + invite.getFrom().nickname + " Time: " + invite.getTime() + " Status: " + invite.getStatus());
			count++;
		}
	}
	
	/*To be implemented: A User will be able to accept or decline an invite
	and the response will be recorded within the invite and sent to the inviter.*/
	public Boolean respondToInvite() {
		System.out.println("Type the corresponding number of the invite to accept it");
		Scanner scanner = new Scanner( System.in );
		int number = 0;
		boolean valid = false;
		do {
			getInviteStatus();
			
			String choice = scanner.nextLine();
			try {
				number = Integer.parseInt(choice);
				if (invites.size() > 0 && number > 0 && number < invites.size()-1)
					valid = true;
			} catch( NumberFormatException e) {
				System.out.println("Invalid input, try again");
			}
		} while(!valid);
		scanner.close();
		
		int count = 1;
		for (Invite invite : invites) {
			if (count == number) {
				invite.setStatus(false);
				return true;
			}
			count++;
		}
		return false;
	}


	public UserProfile getUserProfile(User user) {
		return user.getUserProfile();
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}


	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
