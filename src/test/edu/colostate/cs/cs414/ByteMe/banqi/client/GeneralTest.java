package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.General;

class GeneralTest {
	
	static General general;
	
	@BeforeClass
	public static void initialize(){
		general = new General("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(general);
	}
	
	@Test
	void moveTest() {
		assertTrue(general.movePiece());
	}

}
