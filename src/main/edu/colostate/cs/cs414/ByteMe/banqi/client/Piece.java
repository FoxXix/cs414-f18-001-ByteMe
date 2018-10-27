package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public interface Piece {
	
	int X_position = -1;
	int Y_position = -1;
	boolean isCaptured = true;
	String color = "Blank";
	String name = "Piece";
	int rank = 0;
	
	
	public boolean movePiece();

}
