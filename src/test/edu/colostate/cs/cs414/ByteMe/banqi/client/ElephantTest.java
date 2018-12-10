package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Elephant;

public class ElephantTest {

	private static Elephant elephant, elephantNull;

	@BeforeAll
	public static void initialize(){
		elephant = new Elephant("Red", 1, 1);
    	}
	
	@Test 
	public void testNullElephant() throws NullPointerException {
		assertNull(elephantNull, "Test null Elephant");
	}
	
	@Test
	public void testNotNullElephant() throws NullPointerException {
		assertNotNull(elephant, "Test not null Elephant");
	}

}
