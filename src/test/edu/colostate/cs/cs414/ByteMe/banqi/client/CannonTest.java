package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Cannon;

public class CannonTest {

	private static Cannon cannon, cannonNull;
	
	@BeforeAll
	public static void initialize(){
		cannon = new Cannon("Red", 1, 1);
    	}

	@Test 
	public void testNullCannon() throws NullPointerException {
		assertNull(cannonNull, "Test null Cannon");
	}
	
	@Test
	public void testNotNullCannon() throws NullPointerException {
		assertNotNull(cannon, "Test not null Cannon");
	}

}
