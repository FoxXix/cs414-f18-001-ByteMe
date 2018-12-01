package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Tile;

class BoardTest {

	private static Board board;
	private static Tile tile;
	
	@BeforeAll
	public static void initialize(){
		board = new Board();
		tile = new Tile(1,1);
    	}

	@Test
	void testBoardConstructor() {
		assertNotNull(board, "Board Constructor Test");
	}
	
	@Test
	void testGetTileInfoAsString() {
		Tile newTile = (board.getTileInfo(1,1));
		assertEquals(tile.toString(), newTile.toString(), "Board getTileInfo() test");
	}
}
