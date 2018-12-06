package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

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
		this.forfeits = forfeits;
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
	
	/* Whenever a specific User wins a game, the count on their profile for the number of wins they have
	since they started playing Banqi gets incremented*/
	public void incrementWins() {
		wins += 1;
	}
	
	public int getLosses() {
		return losses;
	}
	/* Whenever a specific User loses a game, the count on their profile for the number of losses they have
	since they started playing Banqi gets incremented*/
	public void incrementLosses() {
		losses += 1;
	}
	
	public int getDraws() {
		return draws;
	}
	
	/* Whenever a specific User draws a game, the count on their profile for the number of draws they have
	since they started playing Banqi gets incremented*/	
	public void incrementDraws() {
		draws += 1;
	}
	
	public int getForfeits() {
		return forfeits;
	}
	
	/* Whenever a specific User forfeits a game, the count on their profile for the number of forfeits they have
	since they started playing Banqi gets incremented*/
	public void incrementForfeits() {
		forfeits += 1;
	}
}
