package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class Piece {
	
	private String name;
	private String color;
	private int rank;
	private int X_position;
	private int Y_position;
	
	Piece(String name, String color, int rank) {
		this.name = name;
		this.color = color;
		this.rank = rank;
	}
	
	public void setPosition(int X, int Y) {
		X_position = X;
		Y_position = Y;
	}
	
	public int[] getPosition() {
		int [] position = new int[2];
		position[0] = X_position;
		position[1] = Y_position;
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getRank() {
		return rank;
	}
	
//	public boolean isCaptured() {
//		
//	}

}
