package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Tile;

class BoardTest {

	static Board board;
	Tile tile;

	@BeforeClass
	public static void initialize(){
		board = new Board();
    }

	@Test
	void initializeTest() {
		assertNotNull(board);
	}
	
	@Test
	void getTileInfoTest() {
		tile = board.getTileInfo(1,1);
		assertNotNull(tile);
	}
}
