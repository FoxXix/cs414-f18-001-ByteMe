package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.General;

public class GeneralTest {
	
	private static General general, generalNull;
	
	@BeforeAll
	public static void initialize(){
		general = new General("Red", 1, 1);
    	}
	
	@Test 
	public void testNullGeneral() throws NullPointerException {
		assertNull(generalNull, "Test null General");
	}
	
	@Test
	public void testNotNullGeneral() throws NullPointerException {
		assertNotNull(general, "Test not null General");
	}

}
