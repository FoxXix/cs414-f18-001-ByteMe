package test.edu.colostate.cs.cs414.ByteMe.banqi.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.Soldier;

class SoldierTest {

	static Soldier soldier;

	@BeforeClass
	public static void initialize(){
		soldier = new Soldier("Red", 1, 1);
    }

	@Test
	void initializeTest() {
		assertNotNull(soldier);
	}
	
	@Test
	void moveTest() {
		assertTrue(soldier.movePiece());
	}

}
