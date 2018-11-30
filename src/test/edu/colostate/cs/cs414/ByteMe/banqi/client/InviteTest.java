package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.time.LocalTime;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Invite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;


class InviteTest {
	private static String id;
	private static LocalTime time;
	private static boolean status;
	private static UserProfile up1;
	private static UserProfile up2;
	private static User from = new User(up1);
	private static User to = new User(up2);
	private static Invite inviter;
	private static Invite invitee;


	@BeforeClass
	public static void initialize(){
		inviter = new Invite(from, to);
		invitee = new Invite(from, to);
		id = UUID.randomUUID().toString();
		time = LocalTime.now();
		status = true;
    	}
	
	@Test
	public void testNotNullInviter() {
		assertNotNull(inviter, "Inviter not-null test");
	}
	
	@Test
	public void testNotNullInvitee() {
		assertNotNull(invitee, "Invitee not-null test");
	}
	
	@Test
	public void testInviteToString() {
		String inviteData = "From: " + from + " Date: " + time;
		assertEquals(inviteData, inviter.toString(), "Invite toString() test");	
	}

}
