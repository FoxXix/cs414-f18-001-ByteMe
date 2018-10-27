package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Advisor;

class AdvisorTest {

static Advisor advisor;
	
	@BeforeClass
	public static void initialize(){
		advisor = new Advisor("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(advisor);
	}
	
	@Test
	void moveTest() {
		assertTrue(advisor.movePiece());
	}

}
