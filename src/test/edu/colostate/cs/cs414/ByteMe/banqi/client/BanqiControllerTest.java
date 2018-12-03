package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

public class BanqiControllerTest {
	
//	static BanqiGame game;
//	Board board;
	BanqiController contr;
	private String profilesFile;
	private String s = "~/cs414/Banqi/cs414-f18-001-ByteMe/src/main/edu/colostate/cs/cs414/ByteMe/banqi/client/UserProfiles.txt";
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	User U;

	@BeforeEach
	public void initialize(){
		contr = new BanqiController(s);
    }
	
	@Test
	public void testCtorNameNull() {
		try {
			String file = null;
			BanqiController b = new BanqiController(file);
		} 
		catch(NullPointerException e) {
			return;
		}
		fail("Should have nullpointer exception");
	}
}
