package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.time.LocalDateTime;

public class UserProfile {
	private String nickname = "";
	private String email = "";
	private String password = "";
	private String joinedDate = null;
	private int wins = 0;
	private int losses = 0;
	private int draws = 0;
	private int forfeits = 0;
	
	public UserProfile(String nickname, String email, String password, String joinedDate, int wins, int losses, int draws, int forfeits) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.joinedDate = joinedDate;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.forfeits = 0;
	}
	
	public String getUserName() {
		return nickname;
	}
	
	public void setUserName(String newName) {
		this.nickname = newName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}
	
	public String getJoinedDate() {
		return joinedDate;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void incrementWins() {
		wins += 1;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public void incrementLosses() {
		losses += 1;
	}
	
	public int getDraws() {
		return draws;
	}
	
	public void incrementDraws() {
		draws += 1;
	}
	
	public int getForfeits() {
		return forfeits;
	}
	
	public void incrementForfeits() {
		forfeits += 1;
	}
}
