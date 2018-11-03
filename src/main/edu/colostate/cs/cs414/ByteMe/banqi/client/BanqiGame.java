package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BanqiGame {

	private Board board;
	private User user1;
	private User user2;
	private UserProfile userProfile1;
	private UserProfile userProfile2;
	private int emptyTiles = 32;
	
	HashMap<String, String> map = new HashMap<>(); 
	
	public BanqiGame(User u1, User u2) {
		board = new Board();
		user1 = u1;
		user2 = u2;
		userProfile1 = u1.getUserProfile();
		userProfile2 = u2.getUserProfile();
	}
	
	public Board getBoard() {
		return board;
	}

	public void setUpBoard() {
		setPieces("Red");
		setPieces("Black");		
	}

	public void makeMove(User user) throws IOException{
		
		System.out.println(user.getNickname() + ", your turn!");
		if (getColor(user.getNickname()) == null) {
			System.out.println("You are not assigned a color yet!");
		} else {
			System.out.println("You are: " + getColor(user.getNickname()));
		}
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		boolean b = false;
		while (b == false) {			
			String choice;
			
			do {
				System.out.println("Enter a coordinate to select a piece.");
				System.out.println("To forfeit, type 'forfeit' and press Enter");
				choice = read.readLine();				
				if (choice.length() != 2)
					System.out.println("Input not recognized");
				else if ("1234ABCDEFGH".indexOf(choice.charAt(0)) == -1 || "1234ABCDEFGH".indexOf(choice.charAt(1)) == -1) {
						System.out.println("Invalid coordinate");
				} else if (choice.equals("forfeit")) {
					b = true;
					break;
				} else {
					System.out.println("Input not recognized");
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
				if (!map.containsKey(user.getNickname())) {
					setColor(user.getNickname(), atTile.getPiece().getColor());
					String oppositeColor = "Red";
					if (atTile.getPiece().getColor().equals("Red")) {
						oppositeColor = "Black";
					}
					if (user.getNickname().equals(user1.getNickname())) {
						setColor(user2.getNickname(), oppositeColor);
					} else {
						setColor(user1.getNickname(), oppositeColor);
					}
				}
				
				b = true;
			} 
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
				
				b = true;
			
				printBoard();
				
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
	
	private void setColor(String nickname, String color) {
		map.put(nickname, color);
	}
	
	private String getColor(String nickname) {
		return map.get(nickname);
	}
	
	public static void main(String[] args) throws IOException 
    { 

		UserProfile up1 = new UserProfile("poop", "email", "pass", "date", 0,0,0,0);
		UserProfile up2 = new UserProfile("pee", "email", "pass", "date", 0,0,0,0);
		User user1 = new User(up1);
		User user2 = new User(up2);
		BanqiGame b = new BanqiGame(user1, user2);
		System.out.println(user1.getNickname());
		b.setUpBoard();
		b.printBoard();
		b.makeMove(user1);
		b.makeMove(user2);
		b.makeMove(user1);
    } 
	

}
