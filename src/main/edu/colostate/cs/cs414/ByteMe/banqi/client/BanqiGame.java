package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BanqiGame {

	private Board board;
	private User user1;
	private User user2;
	private UserProfile userProfile1;
	private UserProfile userProfile2;
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

	public void makeMove() throws IOException{
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		boolean b = false;
		while (b == false) {
			System.out.println("To select piece press 1");
			System.out.println("To forfeit, type 'forfeit' and press Enter");
			
			String choice = read.readLine();
			
			if (choice.equals("1")) {
				do {
					System.out.println("What coordinate?");
					choice = read.readLine();				
					if (choice.length() != 2)
						System.out.println("Input not recognized");
					else if ("1234ABCDEFGH".indexOf(choice.charAt(0)) == -1 || "1234ABCDEFGH".indexOf(choice.charAt(1)) == -1) {
							System.out.println("Invalid coordinate");
					}
				} while ("1234ABCDEFGH".indexOf(choice.charAt(0)) == -1 || "1234ABCDEFGH".indexOf(choice.charAt(1)) == -1);
				
				int x = -1, y = -1;
				if ("1234".indexOf(choice.charAt(0)) != -1){
					x = (int)choice.charAt(0) - 49;
					y = (int)choice.charAt(1) - 65;
				} else {
					x = (int)choice.charAt(1) - 49;
					y = (int)choice.charAt(0) - 65;
				}
				
				int[] atPosition = {x,y};
				
				Tile atTile = board.getTileInfo(atPosition);
				
				if (!atTile.getPiece().isVisible()) {
					atTile.getPiece().makeVisible();
				} 
//				else if (atTile.getPiece().getColor() == user.) {
//					
//				} 
				else {
					do {
						System.out.println("What direction do you want to move " + atTile.getPiece().getName() + "? (Up/Down/Left/Right)");
						
						choice = read.readLine();	
						
						if ("udlrUDLR".indexOf(choice.charAt(0)) == -1) {
							System.out.println("Move unknown");
						}
					} while ("udlrUDLR".indexOf(choice.charAt(0)) == -1);
					int[] toPosition = null;
					switch (choice.charAt(0)) {
						case 'u':
						case 'U': 
							toPosition = new int[]{atPosition[0], atPosition[1] - 1};
							break;
						case 'd':
						case 'D': 
							toPosition = new int[]{atPosition[0], atPosition[1] + 1};
							break;
						case 'l':
						case 'L': 
							toPosition = new int[]{atPosition[0] - 1, atPosition[1]};
							break;
						case 'r':
						case 'R': 
							toPosition = new int[]{atPosition[0] + 1, atPosition[1]};
							break;
					}
					System.out.println(toPosition[0]);
					System.out.println(toPosition[1]);
					if (board.getTileInfo(atPosition).getPiece().getRank() >= board.getTileInfo(toPosition).getPiece().getRank()) {
						board.getTileInfo(toPosition).setPiece(board.getTileInfo(atPosition).getPiece());
						board.getTileInfo(atPosition).clearPiece();
					}
				}
				
				printBoard();
				
			} else if (choice.equals("forfeit")) {
				
			} else {
				System.out.println("Input not recognized");
			}
		}
		
		read.close();
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
		Random rand = new Random();
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
	
	public void printBoard() {
		Tile t;
		System.out.flush();
		for (int x = 0; x < 4; x++) {
			System.out.format("%-20s", "          " + (x+1));
		}		
		System.out.print("\n");
		for (int y = 0, c = 65; y < 8; y++, c++) {
			System.out.println("---------------------------------------------------------------------------------");
			for (int x = 0; x < 4; x++) {
				t = board.getTileInfo(x, y);
				if (t.getPiece() == null) {
					System.out.format("%-20s", "|");
				} else if (t.getPiece().isVisible()) {
					System.out.format("%-20s", "|   " + t.getPiece().getName() + "-" + t.getPiece().getColor());
				} else {
					System.out.format("%-20s", "|         X");
				}
			}
			System.out.print("|\t" +(char)c);
			System.out.print("\n");
		}
		System.out.println("---------------------------------------------------------------------------------");
    }
	
	public static void main(String[] args) throws IOException 
    { 
		BanqiGame b = new BanqiGame();
		b.setUpBoard();
		b.printBoard();
		b.makeMove();
    } 
	

}
