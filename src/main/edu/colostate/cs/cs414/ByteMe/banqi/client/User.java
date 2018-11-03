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

	private void enterCredentials() {
		// TODO Auto-generated method stub
		
	}

	public void initiateGame(User invitee) {
		BanqiGame game = new BanqiGame(this, invitee);
		
	}

	public void sendInvite(String nickname) {
		// TODO Auto-generated method stub
		
	}

	public String getInviteStatus() {
		// TODO Auto-generated method stub
		return null;
	}

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
