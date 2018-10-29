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
		
		
	}

	public boolean getStateOfBoard() {
		return false;		
	}
	
	private void setPieces(String color) {
		List<Piece> pieces = new ArrayList<Piece>();
		int[] position;
		int count = 2;
		
		position = getRandomEmptyTilePosition();
		General general = getGeneral(color, position);
		pieces.add(general);
		board.getTileInfo(position).setPiece(general);
		
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Advisor advisor = getAdvisor(color, position);
			pieces.add(advisor);
			board.getTileInfo(position).setPiece(advisor);
			count--;
		}
		
		count = 2;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Cannon cannon = getCannon(color, position);
			pieces.add(cannon);
			board.getTileInfo(position).setPiece(cannon);
			count--;
		}
		
		count = 2;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Chariot chariot = getChariot(color, position);
			pieces.add(chariot);
			board.getTileInfo(position).setPiece(chariot);
			count--;
		}
		
		count = 2;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Elephant elephant = getElephant(color, position);
			pieces.add(elephant);
			board.getTileInfo(position).setPiece(elephant);
			count--;
		}
		
		count = 2;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Horse horse = getHorse(color, position);
			pieces.add(horse);
			board.getTileInfo(position).setPiece(horse);
			count--;
		}
		
		count = 5;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Soldier soldier = getSoldier(color, position);
			pieces.add(soldier);
			board.getTileInfo(position).setPiece(soldier);
			count--;
		}
		
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
	
	private Advisor getAdvisor(String color, int[] position) {
		return new Advisor(color, position[0], position[1]);
	}
	
	private Cannon getCannon(String color, int[] position) {
		return new Cannon(color, position[0], position[1]);
	}
	
	private Chariot getChariot(String color, int[] position) {
		return new Chariot(color, position[0], position[1]);
	}
	
	private Elephant getElephant(String color, int[] position) {
		return new Elephant(color, position[0], position[1]);
	}
	
	private Horse getHorse(String color, int[] position) {
		return new Horse(color, position[0], position[1]);
	}
	
	private Soldier getSoldier(String color, int[] position) {
		return new Soldier(color, position[0], position[1]);
	}

}
