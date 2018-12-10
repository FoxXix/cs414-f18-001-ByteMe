package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

public class BanqiControllerTest {

	BanqiController contr, contrNull;
	private String profilesFile = "/Users/evansalzman/cs414/UserProfiles.txt";
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();

	@BeforeEach
	public void initialize(){
		contr = new BanqiController(profilesFile);
    	}
	
	@Test
	public void testCtorThrowsException() throws NullPointerException {
		assertNull(contrNull, "Test null BanqiController");
	}
	
	@Test
	public void testReadUserInvalidFile() throws FileNotFoundException {
		BanqiController b = new BanqiController("UserProfiles.txt");
		assertThrows(FileNotFoundException.class,
	            ()->{  b.readUsers();}, 
	            "Test BanqiContoller readUsers w/ invalid filepath throws FileNotFoundException");
	}
	
	@Test
	public void testReadUsersListUsers() throws IOException {
		contr.readUsers();
		assertNotNull(contr.getListUsers(), "Test read users input into listOfUsers");
	}
	
	@Test
	public void testReadUsersListProfiles() throws IOException {
		assertNotNull(contr.getListProfiles(), "Test read users input into listOfProfiles");
	}
}
