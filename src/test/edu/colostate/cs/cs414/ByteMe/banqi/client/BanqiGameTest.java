package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.UserProfile;

public class BanqiGameTest {

	private static BanqiGame game;
	Board board;
	private static UserProfile up1;
	private static UserProfile up2;
	private static User u1;
	private static User u2;

	@BeforeAll
	public static void initialize(){
		up1 = new UserProfile("test1", "email", "pass", "date", 0,0,0,0);
		up2 = new UserProfile("test2", "email", "pass", "date", 0,0,0,0);
		u1 = new User(up1);
		u2 = new User(up2);
		game = new BanqiGame(u1, u2);
    	}

	@Test
	public void initializeTest() {
		assertNotNull(game, "Test Banqi Game is not null");
	}
	
	@Test
	public void setUpBoardTest() throws NullPointerException {
		game.setUpBoard();
		assertNull(game.getBoard().getTileInfo(1, 1), "Test board is accurately set up.");
	}
	
	@Test
	public void makeMoveTest() throws IOException, NullPointerException {
		game.makeMove(u1);
		assertNotNull(game.getBoard(), "Test Not Null Banqi Game board after move.");
	}
	
	@Test
	public void getStateOfBoardTest() {
		assertNotNull(game.getStateOfBoard(), "After initialization, board is not null test.");
	}
}
