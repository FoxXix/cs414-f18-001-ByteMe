package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.time.LocalTime;
import java.util.UUID;

public class Invite {
  
	private String id;
	private LocalTime time;
	private boolean status;
	private User from;
	private User to;
	
	public Invite(User from, String to) {
		id = UUID.randomUUID().toString();
		this.from 	= from;
		this.to = BanqiController.getUser(to);
		time = LocalTime.now();
		status = true;
		
		// add invite to the list of the invitee
		this.to.invites.add(this);
	}
	
	public Invite(User from, User to) {
		id = UUID.randomUUID().toString();
		this.from 	= from;
		this.to		= to;
		time = LocalTime.now();
		status = true;
		
		// add invite to the list of the invitee
		this.to.invites.add(this);
	}
	
	public String toString() {
		return "From: " + from + " Date: " + time;		
	}

	public String getId() {
		return id;
	}
  
	public LocalTime getTime() {
		return time;
	}
	
	public String getStatus() {
		return status ? "Open" : "Closed";
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public User getFrom() {
		return from;
	}
	
	public User getTo() {
		return to;
	}
}
