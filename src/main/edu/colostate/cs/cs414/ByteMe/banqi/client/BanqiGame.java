package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BanqiGame {

	private Board board;
	private UserProfile userProfile;
	private int emptyTiles = 32;
	
	public BanqiGame() {
		board = new Board();
	}
	
	public Board getBoard() {
		return board;
	}

	public void setUpBoard() {
		setPieces("Red");
		setPieces("Black");
		
	}

	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	public void recordStats() {
		// TODO Auto-generated method stub
		
	}

	public boolean getStateOfBoard() {
		return false;		
	}
	
	private void setPieces(String color) {
		List<Piece> pieces = new ArrayList<Piece>();
		int[] position;
		
		position = getRandomEmptyTilePosition();
		General general = getGeneral(color, position);
		pieces.add(general);
		board.getTileInfo(position).setPiece(general);
		
	}
	
	private int[] getRandomEmptyTilePosition() {
		if (emptyTiles == 0)
			return null;
		Tile t;
		Random rand = null;
		int y;
		int x;
		do {
			y = rand.nextInt(8);
			x = rand.nextInt(4);
			t = board.getTileInfo(x, y);				
		} while (t.getPiece() != null);
		emptyTiles--;
		return t.getPosition();
	}
	
	private General getGeneral(String color, int[] position) {
		return new General(color, position[0], position[1]);
	}

}
