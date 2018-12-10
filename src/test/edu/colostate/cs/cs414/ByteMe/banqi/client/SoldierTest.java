package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Soldier;

public class SoldierTest {

	private static Soldier soldier, soldierNull;

	@BeforeAll
	public static void initialize(){
		soldier = new Soldier("Red", 1, 1);
    	}

	@Test 
	public void testNullSoldier() throws NullPointerException {
		assertNull(soldierNull, "Test null Soldier");
	}
	
	@Test
	public void testNotNullSoldier() throws NullPointerException {
		assertNotNull(soldier, "Test not null Soldier");
	}

}
