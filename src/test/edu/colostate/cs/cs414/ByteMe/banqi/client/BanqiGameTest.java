package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

class BanqiGameTest {

	static BanqiGame game;
	Board board;
	static UserProfile up1;
	static UserProfile up2;
	static User u1;
	static User u2;

	@BeforeClass
	public static void initialize(){
		up1 = new UserProfile("test1", "email", "pass", "date", 0,0,0,0);
		up2 = new UserProfile("test2", "email", "pass", "date", 0,0,0,0);
		u1 = new User(up1);
		u2 = new User(up2);
		game = new BanqiGame(u1, u2);
    }

	@Test
	void initializeTest() {
		assertNotNull(game);
	}
	
	@Test
	void setUpBoardTest() {
		game.setUpBoard();
		assertNotNull(game.getBoard().getTileInfo(1, 1));
	}
	
	@Test
	void makeMoveTest() throws IOException {
		game.makeMove(u1);
		assertNotNull(game.getBoard());
		
	}
	
	@Test
	void recordStatsTest() {
		game.recordStats();
		assertNotNull(game.getBoard());
		
	}
	
	@Test
	void getStateOfBoardTest() {		
		assertTrue(game.getStateOfBoard());
		
	}

}
