package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Cannon;

class CannonTest {

static Cannon cannon;
	
	@BeforeClass
	public static void initialize(){
		cannon = new Cannon("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(cannon);
	}
	
	@Test
	void moveTest() {
		assertTrue(cannon.movePiece());
	}

}
