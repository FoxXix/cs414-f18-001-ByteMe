package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

class UserProfileTest {

	private static UserProfile uProfile1, uProfile2;
	private static String nickname1;
	private static String email1;
	private static String password;
	
	@BeforeAll
	public static void initialize() {
		nickname1 = "Evan";
		email1 = "evan@mail.colostate.edu";
		password = "iloveBANQI";
		uProfile1 = new UserProfile(nickname1, email1, password, "11/28/18", 1, 1, 0, 0);
	}
	
	@Test
	public void testConstructor() throws NullPointerException {
		assertEquals(uProfile1.getUserName(), "Evan", "UserProfile.java Constructor test");
	}
	
	@Test
	public void testNullUserProfile() throws NullPointerException {
		assertNull(uProfile2, "Test User Profile is null");
	}
	
	@Test
	public void testNotNullUserProfile() throws NullPointerException {
		assertNotNull(uProfile1, "Test User Profile is not null");
	}

	@Test
	public void testIncrementWins() {
		uProfile1.incrementWins();
		assertEquals(2, uProfile1.getWins(), "Test UserProfile.java incrementWins()");
	}
	
	@Test
	public void testIncrementLosses() {
		uProfile1.incrementLosses();
		assertEquals(2, uProfile1.getLosses(), "Test UserProfile.java incrementLosses()");
	}
	
	@Test
	public void testIncrementDraws() {
		uProfile1.incrementDraws();
		assertEquals(1, uProfile1.getDraws(), "Test UserProfile.java incrementDraws()");
	}
	
	@Test
	public void testIncrementForfeits() {
		uProfile1.incrementForfeits();
		assertEquals(1, uProfile1.getForfeits(), "Test UserProfile.java incrementForfeits()");
	}
}
