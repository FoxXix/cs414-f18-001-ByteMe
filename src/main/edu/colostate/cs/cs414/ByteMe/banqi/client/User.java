package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	private UserProfile userProfile;
	
	private String nickname;
	private String email;
	private String password;
	
	public User(UserProfile user) {
		this.userProfile = user;
		this.nickname = user.getUserName();
	}


	public UserProfile seeProfile(String nickname) {
		return null;
	}
	
	/*To be implemented: For purposes of security, the User will eventually need to enter their credentials
	in order to access components of the system associated with registered users.*/
	private void enterCredentials() {
		// TODO Auto-generated method stub
	}

	public void initiateGame(User invitee) {
		BanqiGame game = new BanqiGame(this, invitee);
		
	}
	
	/*To be implemented: A User may invite any number of other Users to play a new Banqi Game
	by providing the nickname of the User they wish to invite.  While they can send out unlimited invites,
	only the first to accept an invitation can play the game.*/
	public void sendInvite(String nickname) {
		// TODO Auto-generated method stub
		
	}

	public String getInviteStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*To be implemented: A User will be able to accept or decline an invite
	and the response will be recorded within the invite and sent to the inviter.*/
	public Boolean respondToInvite() {
		// TODO Auto-generated method stub
		return null;
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
