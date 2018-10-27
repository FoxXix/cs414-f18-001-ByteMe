package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Elephant;

class ElephantTest {

	static Elephant elephant;

	@BeforeClass
	public static void initialize(){
		elephant = new Elephant("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(elephant);
	}
	
	@Test
	void moveTest() {
		assertTrue(elephant.movePiece());
	}

}
