package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Piece;
import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Tile;

class TileTest {

	static Tile tile;
	Piece piece;

	@BeforeClass
	public static void initialize(){
		tile = new Tile(1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(tile);
	}
	
	@Test
	void getPieceTest() {
		piece = tile.getPiece();
		assertNotNull(piece);
	}

}
