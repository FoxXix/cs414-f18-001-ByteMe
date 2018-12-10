package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Piece;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Tile;

public class TileTest {

	private static Tile tile, tileNull;
	Piece piece;

	@BeforeAll
	public static void initialize(){
		tile = new Tile(1, 1);
    	}
	
	@Test
	public void initializeTest() {
		assertNotNull(tile, "Test Tile is not null after initialization");
	}
	
	@Test
	public void testNullTile() {
		assertNull(tileNull, "Test Tile is null before initialization");
	}
	
	@Test
	public void testGetPiece() {
		piece = tile.getPiece();
		
		assertNull(piece, "Test Piece is null");
	}

	@Test
	public void testGetPosition() {
		int [] tilePosition = {1,1};
		int [] testPosition = tile.getPosition();
		String tilePositionString = "(" + tilePosition[0] + "," + tilePosition[1] + ")";
		String testPositionString = "(" + testPosition[0] + "," + testPosition[1] + ")";
		
		assertEquals(tilePositionString, testPositionString, "Test Tile getPosition()"); 
	}

}
