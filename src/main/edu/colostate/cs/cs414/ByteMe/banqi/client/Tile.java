package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Tile {
	
	private int X_position;
	private int Y_position;
	
	private Piece piece = null;

	public Tile(int X, int Y) {
		X_position = X;
		Y_position = Y;
	}
	
	/*Provides the current position of a Piece on the Game Board.
	The position is given in the form of x and y coordinates on the 4x8 board. */
	public int[] getPosition() {
		int [] position = new int[2];
		position[0] = X_position;
		position[1] = Y_position;
		return position;
	}
	
	public void setPiece(Piece P) {
		piece = P;
	}
	
	/*When a piece is captured by a piece that can capture it from the opposing team,
	the piece is cleared from the board.  At this point, the given piece is no longer active in the game,
	so it is set to null as it does not physically or visually exist in the game anymore. */
	public void clearPiece() {
		piece = null;
	}
	
	public boolean isEmpty() {
		return piece == null;
	}

	public Piece getPiece() {		
		return piece;
	}
}
