package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Advisor;

public class AdvisorTest {

	private static Advisor advisor, advisorNull;
	
	@BeforeAll
	public static void initialize() {
		advisor = new Advisor("Red", 1, 1);
    	}
	
	@Test 
	public void testNullAdvisor() throws NullPointerException {
		assertNull(advisorNull, "Test null Advisor");
	}
	
	@Test
	public void testNotNullAdvisor() throws NullPointerException {
		assertNotNull(advisor, "Test not null Advisor");
	}
}
