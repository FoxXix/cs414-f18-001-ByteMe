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
	
	public UserProfile(String nickname, String email, String password, String joinedDate) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.joinedDate = joinedDate;
	}
	
	public String getUserName() {
		return nickname;
	}
}
