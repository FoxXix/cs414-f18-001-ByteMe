package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Chariot implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;;
	String name = "Chariot";
	int rank = 4;

	public Chariot(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}

	/*To be implemented.  This method determines whether the move intended for the Chariot
	is a valid move in the system.  If the system finds this to be a valid move, true is returned,
	to indicate that a given move can legally be made. */
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
	
	/*If a Chariot Piece is currently face down, this method changes it's visibility,
	changing it to being face up from the perspective of the system.
	The boolean variable 'visible' associated with the Chariot is set to true.*/
	@Override
	public void makeVisible() {
		visible = true;
	}
	
	/*Returns the status of the Chariot Piece, giving whether it is still face-down or face-up.*/
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
