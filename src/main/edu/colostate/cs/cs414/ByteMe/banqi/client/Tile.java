package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Tile {
	
	private int X_position;
	private int Y_position;
	
	private Piece piece = null;

	public Tile(int X, int Y) {
		X_position = X;
		Y_position = Y;
	}
	
	public int[] getPosition() {
		int [] position = new int[2];
		position[0] = X_position;
		position[1] = Y_position;
		return position;
	}
	
	public void setPiece(Piece P) {
		piece = P;
	}
	
	public boolean isEmpty() {
		return piece == null;
	}
}
