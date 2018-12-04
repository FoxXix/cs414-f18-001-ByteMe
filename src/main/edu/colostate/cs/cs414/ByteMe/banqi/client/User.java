package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
	
	private UserProfile userProfile;
	protected ArrayList<Invite> invites;
	protected ArrayList<String> invitedUsers;
	protected ArrayList<String> gamesInvitedTo;
	
	private String nickname;
	private String email;
	private String password;
	
	public User(UserProfile user) {
		this.userProfile = user;
		this.nickname = user.getUserName();
		invites = new ArrayList<Invite>();
		invitedUsers = new ArrayList<String>();
		gamesInvitedTo = new ArrayList<String>();
	}

	public UserProfile seeProfile(String nickname) throws NullPointerException {
		if (BanqiController.getUser(nickname).equals(null))
			throw new NullPointerException("User does not exist in the Banqi system.");
		
		User player = BanqiController.getUser(nickname);
		
		System.out.println(nickname + "'s Profile\n");
		System.out.println("Joined: " + player.userProfile.getJoinedDate());
		System.out.println("Wins: " + player.userProfile.getWins());
		System.out.println("Losses: " + player.userProfile.getLosses());
		System.out.println("Draws: " + player.userProfile.getDraws());
		System.out.println("Forfeits: " + player.userProfile.getForfeits());
		
		return userProfile;
	}
	
	/*To be implemented: For purposes of security, the User will eventually need to enter their credentials
	in order to access components of the system associated with registered users.*/

	public void initiateGame(User invitee) throws NullPointerException {
		if (BanqiController.getUser(invitee.getNickname()).equals(null))
			throw new NullPointerException("User does not exist in the system and can't be your opponent.");
		new BanqiGame(this, invitee);		
	}
	
	/*A User may invite any number of other Users to play a new Banqi Game.  A single call to this method
	is associated with a single invite.  The required paramter is the nickname of the User they wish to invite.  
	This creates the association relationship between the User (host) and the invited User.*/
	public void sendInvite(String nickname) throws NullPointerException {
		if (BanqiController.getUser(nickname).equals(null))
			throw new NullPointerException("User does not exist in the Banqi system and can't be invited.");
		Invite newInvite = new Invite(this, nickname);
		System.out.println("Invited " + nickname + " to play Banqi!\n");
		invites.add(newInvite);
		invitedUsers.add(nickname);
		gamesInvitedTo.add(this.nickname);
	}
	
	public Invite getInvite(User from) {
		for (Invite invite: invites) {
			if (invite.getFrom().equals(from)) {
				return invite;
			}
		}
		return null;
	}
	
	/*Unless a User has no game invites, this prints out any invites for a given User.  
	Each invite has an associated invitee, time of invite and whether it's accepted/declined.*/
	public void getInviteStatus() {
		System.out.println("Current invites:\n");
		int count = 1;
		for (Invite invite : invites) {
			System.out.println(count + ") From: " + invite.getFrom().nickname + " Time: " + invite.getTime() + " Status: " + invite.getStatus());
			count++;
		}
		count = 1;
		for (String inviter : invitedUsers) {
			System.out.println(count + ") From: " + inviter);
		}
		if (invites.size() == 0) {
			System.out.println("Empty");
		}
	}
	
	/*A User responds to a Banqi Game invite.  The User either accepts or declines it,
	and the response will be recorded within the invite and sent to the inviter.
	The response is given via command line, with a valid Banqi Game user.  The system
	then processes the User's response to that invite.
	Checks:
		A game without any outstanding invites cannot have an accepted invite.
		Invites are stored with a number, so the system requires a valid number to accept an invite response.
	*/
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

	public UserProfile getUserProfile(User user) throws NullPointerException {
		if (BanqiController.getUser(user.getNickname()).equals(null))
			throw new NullPointerException("User does not exist in the Banqi system.");
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
	
	public ArrayList<String> getInvitedUsers(){
		return invitedUsers;
	}
	
	public ArrayList<String> getGamesInvitedTo(){
		return gamesInvitedTo;
	}
}
