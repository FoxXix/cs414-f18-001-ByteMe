package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Horse implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Horse";
	int rank = 3;

	public Horse(String color, int x, int y) {
		this.color =  color;
		X_position = x;
		Y_position = y;
	}

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
	
	@Override
	public void makeVisible() {
		visible = true;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public int getRank() {
		return rank;
	}
}
