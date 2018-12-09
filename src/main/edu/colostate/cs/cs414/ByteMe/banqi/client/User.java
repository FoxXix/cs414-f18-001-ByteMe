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
	
	
	/**
	 * Construct a new User, which has it's own nickname, email and password
	 * The input for this comes in through the command line display for the Banqi Game,
	 * when a user selects 'Create Profile'
	 * Information regarding the User's invites, sent and received is also held here.
	 * @param UserProfile user, an instance of a UserProfile for Banqi
	 */
	public User(UserProfile user) {
		this.userProfile = user;
		this.nickname = user.getUserName();
		invites = new ArrayList<Invite>();
		invitedUsers = new ArrayList<String>();
		gamesInvitedTo = new ArrayList<String>();
	}
	
	/**
	 * Enter a nickname of a registered User in the Banqi system to see their profile,
	 * which includes their registration date, wins, losses, draws and forfeit counts in Banqi.
	 * The BanqiController finds the User in the system.
	 * @param String nickname, the nickname of the User who's profile is desired
	 * @return UserProfile userProfile, the profile associated with the nickname parameter
	 * @throws NullPointerException if the nickname is not associated with a registered User
	 */
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
	
	/**
	 * Start a new Banqigame between this User instance and the User given as the parameter.
	 * By the end of this a new Banqi Game instance is created, which is tied to both Users.
	 * @param User invitee, the User who received an invite to play Banqi
	 * @throws NullPointerException, if the given User is not a registered User in the Banqi Game system
	 */
	public void initiateGame(User invitee) throws NullPointerException {
		if (BanqiController.getUser(invitee.getNickname()).equals(null))
			throw new NullPointerException("User does not exist in the system and can't be your opponent.");
		new BanqiGame(this, invitee);		
	}
	
	/**
	 * A User may invite any number of other Users to play a new Banqi Game.  A single call to this method
	 * is associated with a single invite.  The required parameter is the nickname of the User they wish to invite.
	 * This creates the invite relationship between the User (host) and the invited User
	 * @param String nickname, the nickname of the User to send the invite to.
	 * @throws NullPointerException, if the nickname chosen for the invite does not exist in the Banqi Game system.
	 */
	public void sendInvite(String nickname) throws NullPointerException {
		if (BanqiController.getUser(nickname).equals(null))
			throw new NullPointerException("User does not exist in the Banqi system and can't be invited.");
		Invite newInvite = new Invite(this, nickname);
		System.out.println("Invited " + nickname + " to play Banqi!\n");
		invites.add(newInvite);
		invitedUsers.add(nickname);
		gamesInvitedTo.add(this.nickname);
	}
	
	/**
	 *
	 * @param User from, the User who sent the invite
	 * @return Invite invite, the specific data pertaining to the invite the from User sent.
	 */
	public Invite getInvite(User from) {
		for (Invite invite: invites) {
			if (invite.getFrom().equals(from)) {
				return invite;
			}
		}
		return null;
	}
	
	/**
	 * 	Unless a User has no game invites, this prints out any invites for a given User.
	 * 	Format is: #) From: [fromUser] Time: [timeSent] Status: [accepted/rejected]
	 * 	Each of the invites are printed out starting with the number 1.
	 */
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
	
	/**
	 * A User responds to a Banqi Game invite.  The User either accepts or declines it,
	 * and the response will be recorded within the invite and sent to the inviter.
	 * The response is given via command line, with a valid Banqi Game user.  The system
	 * then processes the User's response to that invite.
	 * Checks:
	 * 		A game without any outstanding invites cannot have an accepted invite.
	 * 		Invites are stored with a number, so the system requires a valid number to accept an invite response.
	 * @return boolean response, true if the invite is accepted, false otherwise.
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
	
	/**
	 * Search for and return the UserProfile (joinedDate, wins, losses, draws, forfeits) of the User user
	 * @param User user, the User who's UserProfile is desired
	 * @return UserProfile userProfile, the UserProfile associated/requested with the parameter user
	 * @throws NullPointerException, if the requested User is not in the Banqi Game system
	 */
	public UserProfile getUserProfile(User user) throws NullPointerException {
		if (BanqiController.getUser(user.getNickname()).equals(null))
			throw new NullPointerException("User does not exist in the Banqi system.");
		return user.getUserProfile();
	}

	/**
	 * 
	 * @return UserProfile userProfile, the profile of this User instance
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * 
	 * @param UserProfile userProfile, the userProfile to be set to this User 
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * 
	 * @return String nickname, the nickname of this User user
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 
	 * @param String nickname, the nickname to be set to this User 
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 
	 * @return String email, the email of this User
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param String email, the email to be set for this User
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return String password, the password associated with this User's account/profile
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param String password, the password to be set for this User
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return ArrayList<String> invitedUsers, the list of all Users this User has invited to play Banqi
	 */
	public ArrayList<String> getInvitedUsers(){
		return invitedUsers;
	}

	/**
	 * 
	 * @return ArrayList<String> gamesInvitedTo, the list of all Users this User has received invites from
	 */
	public ArrayList<String> getGamesInvitedTo(){
		return gamesInvitedTo;
	}
}
