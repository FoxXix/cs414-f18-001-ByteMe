package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;

class UserTest {

	private static User user1, user2, user3;
	private static UserProfile uProfile1, uProfile2;	
	private static String nickname1, nickname2;
	private static String email1, email2;
	private static String password;
	
	@BeforeAll
	public static void initialize(){
		nickname1 = "Evan";
		nickname2 = "Brian";
		email1 = "evan@mail.colostate.edu";
		email2 = "brian@mail.colostat.edu";
		password = "iloveBANQI";
		uProfile1 = new UserProfile(nickname1, email1, password, "11/28/18", 1, 1, 0, 0);
		uProfile2 = new UserProfile(nickname2, email2, password, "11/28/18", 1, 0, 0, 0);
		user1 = new User(uProfile1);
		user2 = new User(uProfile2);
		BanqiController controller = new BanqiController("⁩banqi/src/test/edu/colostate/cs/cs414/ByteMe/banqi/client/Users.txt");
		controller.getListProfiles().add(uProfile2);
		controller.getListUsers().add(user2);
    	}

	@Test
	public void testNotNullUser() throws NullPointerException {
		assertNotNull(user1, "Test not null User");
	}
	
	@Test
	public void testNullUser() throws NullPointerException {
		assertNull(user3, "Test null User");
	}
	
	@Test
	public void testUserConstructor() {
		User newUser = new User(uProfile2);
		assertEquals("Brian", newUser.getNickname(), "Test User.java constructor");
	}
	
	@Test
	public void testSeeProfile() throws NullPointerException, IOException {
		UserProfile newProfile = user1.seeProfile("Brian");
		assertEquals(uProfile2, newProfile, "Test seeProfile(String nickname");
	}
	
	@Test
	public void testSeeProfileNonUser() throws NullPointerException{
	    assertThrows(NullPointerException.class,
	            ()->{ user1.seeProfile("NotaUser"); }, "Test null entry into seeProfile(String nickname)");
	}
	
	@Test
	public void testSendInviteNonUser() throws NullPointerException{
	    assertThrows(NullPointerException.class,
	            ()->{ user1.sendInvite("NotaUser"); }, "Test null entry into sendInvite(String nickname)");
	}
	
	@Test
	public void testgetUserProfileNonUser() throws NullPointerException{
	    assertThrows(NullPointerException.class,
	            ()->{ user1.getUserProfile(null); }, "Test null entry into getUserProfile(User user)");
	}
	
	@Test
	public void testInitiateGameNonUser() throws NullPointerException{
	    assertThrows(NullPointerException.class,
	            ()->{ user1.initiateGame(null); }, "Test null entry into initiateGame(User user)");
	}

}
