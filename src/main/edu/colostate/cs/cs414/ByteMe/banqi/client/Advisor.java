package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Advisor implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Advisor";
	int rank = 6;

	public Advisor(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}
	
	/**
	* To be implemented.  
	* This method determines whether the move intended for the Advisor is a valid move in the system.  
	* If the system finds this to be a valid move, returns true, to indicate that a given move can legally be made. 
	*/
	@Override
	public boolean movePiece() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getColor() {
		return color;
	}
	
	/** 
	* If an Advisor Piece is currently face down, this method changes it's visibility,
	* changing it to being face up from the perspective of the system.
	* The boolean variable 'visible' associated with the Advisor is set to true.
	*/
	@Override
	public void makeVisible() {
		visible = true;
	}

	/**
	* Returns the visibility of the Advisor Piece, giving whether it is still face-down or face-up.
	* - true for pieces that are face-down
	* - false for pieces that are face-down
	*/
	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public void isCaptured() {
		isCaptured = true;
		
	}
}
