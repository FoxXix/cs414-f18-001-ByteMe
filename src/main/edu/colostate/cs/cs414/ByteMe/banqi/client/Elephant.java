package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Elephant implements Piece{
	
	int X_position;
	int Y_position;
	boolean isCaptured = false;
	boolean visible = false;
	String color;
	String name = "Elephant";
	int rank = 5;

	public Elephant(String color, int x, int y) {
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
}
