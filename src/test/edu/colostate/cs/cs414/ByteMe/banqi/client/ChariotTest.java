package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Chariot;

public class ChariotTest {

	private static Chariot chariot, chariotNull;
	
	@BeforeAll
	public static void initialize() {
		chariot = new Chariot("Red", 1, 1);
    	}
	
	@Test 
	public void testNullChariot() throws NullPointerException {
		assertNull(chariotNull, "Test null Chariot");
	}
	
	@Test
	public void testNotNullChariot() throws NullPointerException {
		assertNotNull(chariot, "Test not null Chariot");
	}
}
