package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Horse implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Horse";
	int rank = 3;
	
	/**
	* The color is either red of black and there are two Horses per color.
	* The position has an x coordinate and a y coordinate to indicate the Piece's position on the Banqi Game Board.
	* @param String color, the color of the Horse (red/black)
	* @param int x, the x-coordinate of the Horse of the (x,y) Board position
	* @param int y, the y-coordinate of the Horse of the (x,y) Board position
	*/
	public Horse(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}

	/**
	* @return String name, Horse for this Piece
	*/
	@Override
	public String getName() {
		return name;
	}
	
	/**
	* @return String color of the peice, either red or black
	*/
	@Override
	public String getColor() {
		return color;
	}
		
	/** 
	* If an Elephant Piece is currently face down, this method changes it's visibility,
	* changing it to being face up from the perspective of the system.
	* The boolean variable 'visible' associated with the Horse is set to true.
	*/
	@Override
	public void makeVisible() {
		visible = true;
	}
	
	/**
	* Gives the visibility of the Horse Piece, giving whether it is still face-down or face-up.
	* @return boolean visible (true if visible, false if not visible)
	*/
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	/**
	* @return int rank, the rank/power of this Horse
	*/
	@Override
	public int getRank() {
		return rank;
	}

	/** 
	* When a Horse Piece is captured, this method changes it's state to captured.
	* Captured means one of the opposing User's Pieces captured this Horse.
	* A Piece can only capture Pieces of lower/equal rank.
	*/
	@Override
	public void isCaptured() {
		isCaptured = true;
		
	}
}
