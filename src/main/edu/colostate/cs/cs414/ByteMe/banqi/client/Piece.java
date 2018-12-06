package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public interface Piece {
	
	int X_position = -1;
	int Y_position = -1;
	boolean isCaptured = false;
	boolean visible = false;
	String color = "Blank";
	String name = "Piece";
	int rank = 0;
	
	
	public boolean movePiece();
	public String getName();
	public String getColor();
	public void makeVisible();
	public boolean isVisible();
	public void isCaptured();
	public int getRank();

}
