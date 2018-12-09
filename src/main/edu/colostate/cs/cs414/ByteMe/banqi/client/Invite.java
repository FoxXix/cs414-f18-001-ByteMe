package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.time.LocalTime;
import java.util.UUID;

public class Invite {
  
	private String id;
	private LocalTime time;
	private boolean status;
	private User from;
	private User to;
	
	/**
	 * Construct a new invite between an inviting User and an invited User
	 * @param User from, the User who sent the invite
	 * @param User to, the User who will receive the invite
	 */	
	public Invite(User from, String to) {
		id = UUID.randomUUID().toString();
		this.from 	= from;
		this.to = BanqiController.getUser(to);
		time = LocalTime.now();
		status = true;
		
		// add invite to the list of the invitee
		this.to.invites.add(this);
	}
	
//	public Invite(User from, User to) {
//		id = UUID.randomUUID().toString();
//		this.from 	= from;
//		this.to		= to;
//		time = LocalTime.now();
//		status = true;
//		
//		// add invite to the list of the invitee
//		this.to.invites.add(this);
//	}
	

	/**
	 * Print the From: [fromUser] Date: [time] of a given invite as a string
	 * @return String s, the string representation of the important invite data
	 */
	public String toString() {
		return "From: " + from.getNickname() + " Date: " + time;		
	}

	/**
	 *
	 * @return String id, the unique id pertaining to this invite
	 */
	public String getId() {
		return id;
	}

	/**
	 *
	 * @return LocalTime time, the time the invite was sent
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 *
	 * @return String status, the status, open or closed, of the invite
	 */
	public String getStatus() {
		return status ? "Open" : "Closed";
	}

	/**
	 *
	 * @param boolean status, set the status of the invite, true = accepted, false = rejected
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 *
	 * @return User from, the User who is sending the invite
	 */
	public User getFrom() {
		return from;
	}

	/**
	 *
	 * @return User to, the User who the invite is to be sent to
	 */
	public User getTo() {
		return to;
	}
}
