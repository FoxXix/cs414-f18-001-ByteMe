package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

class UserTest {

	static User user;
	static UserProfile up;

	@BeforeClass
	public static void initialize(){
		user = new User(up);
    }

	@Test
	void initializeTest() {
		assertNotNull(user);
	}
	
	@Test
	void seeProfileTest() {
		up = user.seeProfile("Test");
		assertNotNull(up);
	}
	
	@Test
	void initiateGameTest() {
		user.initiateGame(null);
	}
	
	@Test
	void sendInviteTest() {
		user.sendInvite("Test");
	}
	
	@Test
	void getInviteStatusTest() {
		String status = user.getInviteStatus();
		assertNotNull(status);
	}
	
	@Test
	void respondToInviteTest() {
		Boolean response = user.respondToInvite();
		assertTrue(response);
	}

}
