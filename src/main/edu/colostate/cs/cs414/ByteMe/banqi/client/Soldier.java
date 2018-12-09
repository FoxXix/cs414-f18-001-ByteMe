package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Soldier implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Soldier";
	int rank = 2;
	
	/**
	* The color is either red of black and there are five Soldiers per color.
	* The position has an x coordinate and a y coordinate to indicate the Piece's position on the Banqi Game Board.
	* @param String color, the color of the Soldier (red/black)
	* @param int x, the x-coordinate of the Soldier of the (x,y) Board position
	* @param int y, the y-coordinate of the Soldier of the (x,y) Board position
	*/
	public Soldier(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}
	
	/**
	* To be implemented.  
	* This method determines whether the move intended for the Horse is a valid move in the system.  
	* If the system finds this to be a valid move, returns true, to indicate that a given move can legally be made. 
	* @param boolean false
	*/
	@Override
	public boolean movePiece() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	* 
	* @return String name, Soldier for this Piece
	*/
	@Override
	public String getName() {
		return name;
	}
	
	/**
	* 
	* @return String color of the peice, either red or black
	*/
	@Override
	public String getColor() {
		return color;
	}
	
	/** 
	* If a Soldier Piece is currently face down, this method changes it's visibility,
	* changing it to being face up from the perspective of the system.
	* The boolean variable 'visible' associated with the Soldier is set to true.
	*/	
	@Override
	public void makeVisible() {
		visible = true;
	}
	
	/**
	* Gives the visibility of the Soldier Piece, giving whether it is still face-down or face-up.
	* @return boolean visible (true if visible, false if not visible)
	*/
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	/**
	* 
	* @return int rank, the rank/power of this Soldier
	*/
	@Override
	public int getRank() {
		return rank;
	}
	
	/** 
	* When a Soldier Piece is captured, this method changes it's state to captured.
	* Captured means one of the opposing User's Pieces captured this Soldier.
	* A Piece can only capture Pieces of lower/equal rank.
	*/
	@Override
	public void isCaptured() {
		isCaptured = true;
		
	}
}
