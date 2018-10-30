package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiGame;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;

class BanqiGameTest {

	static BanqiGame game;
	Board board;

	@BeforeClass
	public static void initialize(){
		game = new BanqiGame();
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
		game.makeMove();
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
