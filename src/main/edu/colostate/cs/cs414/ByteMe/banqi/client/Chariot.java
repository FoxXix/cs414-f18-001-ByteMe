package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Chariot implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	String color;;
	String name = "Chariot";
	int rank = 4;

	Chariot(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}

	@Override
	public boolean movePiece() {
		// TODO Auto-generated method stub
		return false;
	}

}
