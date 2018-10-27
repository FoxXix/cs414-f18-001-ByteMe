package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Chariot;

class ChariotTest {
	
	static Chariot chariot;

	@BeforeClass
	public static void initialize(){
		chariot = new Chariot("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(chariot);
	}
	
	@Test
	void moveTest() {
		assertTrue(chariot.movePiece());
	}

}
