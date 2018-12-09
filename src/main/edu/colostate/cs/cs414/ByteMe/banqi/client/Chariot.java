package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Chariot implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Chariot";
	int rank = 4;

	/**
	* The color is either red of black and there is one Chariot per color.
	* The position has an x coordinate and a y coordinate to indicate it's current position on the Banqi Game Board.
	*/
	public Chariot(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}

	/**
	* To be implemented.  
	* This method determines whether the move intended for the Chariot is a valid move in the system.  
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
	* If a Chariot Piece is currently face down, this method changes it's visibility,
	* changing it to being face up from the perspective of the system.
	* The boolean variable 'visible' associated with the Advisor is set to true.
	*/
	@Override
	public void makeVisible() {
		visible = true;
	}
	
	/**
	* Returns the visibility of the Chariot Piece, giving whether it is still face-down or face-up.
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

	/** 
	* When a Chariot Piece is captured, this method changes it's state to captured.
	* Captured means one of the opposing User's Pieces captured this Chariot.
	* A Piece can only capture Pieces of lower/equal rank.
	*/
	@Override
	public void isCaptured() {
		isCaptured = true;
		
	}
}
