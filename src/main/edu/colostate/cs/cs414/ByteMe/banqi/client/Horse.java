package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Horse implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	String color;
	String name = "Horse";
	int rank = 3;

	Horse(String color, int x, int y) {
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
