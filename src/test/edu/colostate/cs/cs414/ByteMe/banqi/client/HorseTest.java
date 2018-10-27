package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Horse;

class HorseTest {

	static Horse horse;

	@BeforeClass
	public static void initialize(){
		horse = new Horse("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(horse);
	}
	
	@Test
	void moveTest() {
		assertTrue(horse.movePiece());
	}

}
