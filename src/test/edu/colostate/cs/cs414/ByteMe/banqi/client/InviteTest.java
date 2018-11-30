package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Invite;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;


class InviteTest {

	private static UserProfile up1;
	private static UserProfile up2;
	private static User from;
	private static User to;
	private static Invite invite;
	private static String nickname1;
	private static String nickname2;


	@BeforeAll
	public static void initialize(){
		nickname1 = "Billy";
		nickname2 = "Robert";
		String email1 = "billy@gmail.com";
		String email2 = "robert@gmail.com";
		up1 = new UserProfile(nickname1, email1, "1234", "1/12/18", 0, 0, 0, 0);
		up2 = new UserProfile(nickname2, email2, "5678", "1/12/18", 0, 0, 0, 0);
		from = new User(up1);
		to = new User(up2);
		invite = new Invite(from, to);
    	}
	
	@Test
	public void testNotNullInvite() {
		assertNotNull(invite, "Inviter not-null test");
	}
	
	@Test
	public void testInviteToString() {
		String inviteData = "From: " + from.getNickname() + " Date: " + invite.getTime();
		assertEquals(inviteData, invite.toString(), "Invite toString() test");	
	}

}
