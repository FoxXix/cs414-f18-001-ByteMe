package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Board;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Tile;

class BoardTest {

	private static Board board;
	private static Tile tile;
	private static List boardTiles = new ArrayList<List<Tile>>();
	private static List row = new ArrayList<List<Tile>>();
	
	@BeforeClass
	public static void initialize(){
		board = new Board();
		tile = new Tile(1,1);

		for (int y = 0; y < 8; y++) {
			row = new ArrayList<Tile>();
			for (int x = 0; x < 4; x++) {
				tile = new Tile(x, y);
				row.add(tile);
			}
			boardTiles.add(row);
		}
    }

	@Test
	void testBoardConstructor() {
		assertNull(board, "Board Constructor Test");
	}
	
	@Test
	void testGetTileInfo() {
		Tile newTile = board.getTileInfo(1,1);
		assertEquals(tile, newTile, "Board getTileInfo() test");
	}
}
