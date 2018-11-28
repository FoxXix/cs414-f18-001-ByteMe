package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.time.LocalTime;

public class Invite {
  
	private String id;
	private LocalTime time;
	private String status;
	private User from;
	private User to;
	
	public Invite(User from, User to) {
		this.from 	= from;
		this.to		= to;
		time = LocalTime.now();
		status = "Open";
	}

	public String getId() {
		return id;
	}
  
	public LocalTime getTime() {
		return time;
	}
	
	public String getStatus() {
		return status;
	}
	
	public User getFrom() {
		return from;
	}
	
	public User getTo() {
		return to;
	}
}
