package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Horse;

public class HorseTest {

	private static Horse horse, horseNull;

	@BeforeAll
	public static void initialize(){
		horse = new Horse("Red", 1, 1);
    	}
	
	@Test 
	public void testNullHorse() throws NullPointerException {
		assertNull(horseNull, "Test null Horse");
	}
	
	@Test
	public void testNotNullHorse() throws NullPointerException {
		assertNotNull(horse, "Test not null Horse");
	}

}
