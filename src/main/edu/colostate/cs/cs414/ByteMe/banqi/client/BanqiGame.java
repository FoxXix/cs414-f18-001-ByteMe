package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BanqiGame {

	private Board board;
	private User user1;
	private User user2;
	//private UserProfile userProfile1;
	//private UserProfile userProfile2;
	private int emptyTiles 	= 32;
	private int redPieces 	= 16;
	private int blackPieces = 16;
	private boolean forfeit = false;
	private boolean won = false;

	private BufferedReader read;
	
	HashMap<String, String> map = new HashMap<>();
//	Scanner scanner = new Scanner( System.in );
	Tile atTile;
	String choice;
	
	 /**
	 * Sets up a new BanqiGame between two Users, on a Board, who agreed to play together.
	 * @param User u1, the first player of the game (inviter)
	 * @param User u2, the second player in the game (invitee)
	 */
	public BanqiGame(User u1, User u2) {
		board = new Board();
		user1 = u1;
		user2 = u2;

	}
	
	/**
	* 
	* @param BufferedReader read, the tool used to read what pertains to this Banqi Game
	*/
	public void setReader(BufferedReader read) {
		this.read = read;
	}
	
	/**
	* 
	* @return User user2, the second User in the game (invitee)
	*/
	public User getUser2() {
		return user2;
	}
	
	/**
	* 
	* @return Board board, the board which this BanqiGame is on
	*/
	public Board getBoard() {
		return board;
	}
	
	/**
	* 
	* @param Board board, set the board to it's current state for this Banqi Game
	*/
	public void updateBoard(Board board) {
		this.board = board;
	}
	
	/**
	* Calls the setPieces method to add both Red and Black pieces to the Banqi Game Board
	*/
	public void setUpBoard() {
		setPieces("Red");
		setPieces("Black");		
	}
	
//	public void play() throws IOException {
//		List<User> players = new ArrayList<User>();
//		players.add(user1);
//		players.add(user2);
//		int turns = 0;
//		while (!won) {
//			//this should work, each turn we will need to get the state of the board
//			//and call the sendBoard method in server to communicate each move that was made
//			makeMove(players.get(turns % 2));
//			turns++;
//		}
//	}
	
	/**
	* On the given User's turn, they may make a valid move.
	* If the piece is not visible, it is turned over.
	* If the piece is visible, the User can move up, down, left or right on the board.
	* If the move captures a piece (and meets capturing criteria) the captured piece is removed from the board.
	* By the end, a move has been made with a Piece.
	* @param User user, the User who's turn it is in the Banqi Game
	*/
	public void makeMove(User user) throws IOException{
		
		System.out.println(user.getNickname() + ", your turn!");
		if (getColor(user.getNickname()) == null) {
			System.out.println("You are not assigned a color yet!");
		} else {
			System.out.println("You are: " + getColor(user.getNickname()));
		}
		
		while (!startMove(user));
		

		if (!forfeit && !won) {
			continueGame(user);
		} else { //forfeit
			System.out.println("You forfeited, loser");
		}
	}
	
	private void continueGame(User user) throws IOException {
		int[] atPosition = getPosition(choice);
		atTile = board.getTileInfo(atPosition);

		if (!atTile.getPiece().isVisible()) {
			atTile.getPiece().makeVisible();
			if (!map.containsKey(user.getNickname())) {
				manageColorAssignments(user);
			}			
			printBoard();
		} 
		else {
			int[] toPosition =  null;
			do {
				toPosition = chooseMove(atPosition, toPosition);
			} while (toPosition != null);//while ("udlrUDLR".indexOf(choice.charAt(0)) == -1);				
			
			// can this piece take the other piece?
			checkAbilityToCapture(atPosition, toPosition);
			printBoard();			
		}
	}
	
	private void manageColorAssignments(User user) {
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

	private int[] chooseMove(int[] atPosition, int[] toPosition) throws IOException {
		System.out.println("What direction do you want to move " + atTile.getPiece().getName() + "? (Up/Down/Left/Right)");

		choice = read.readLine();

		if ("udlrUDLR".indexOf(choice.charAt(0)) == -1) {
			System.out.println("Move unknown");
		} else {
			toPosition = validateBounds(atPosition, choice.charAt(0));
		}
		return toPosition;
	}

	private void checkAbilityToCapture(int[] atPosition, int[] toPosition) {
		if (board.getTileInfo(atPosition).getPiece().getRank() >= board.getTileInfo(toPosition).getPiece().getRank()) {
			if (board.getTileInfo(toPosition).getPiece().getColor() == "Red") { // keep track of # of player pieces
				redPieces--;
			} else {
				blackPieces--;
			}
			board.getTileInfo(toPosition).setPiece(board.getTileInfo(atPosition).getPiece());
			board.getTileInfo(atPosition).clearPiece();

			// check if winning state
			if (redPieces == 0) { // black wins
				System.out.println("BLACK WINS!!!");
				won = true;
			} else if (blackPieces == 0) { // red wins
				System.out.println("RED WINS!!!");
				won = true;
			}
		}
	}
	
	
	/** 
	* This method takes in the position of the piece that is to be
	* moved and the direction it wants to be moved to. It validates
	* that the move would be valid and within the bounds of the 
	* board.
	* @param int[] at, the position of the Piece.
	* @param char direction, the way (up,down,left,right) that the Piece is trying to go
	* @return int[] toPosition, the validated bounds of the location moving to
	*/
	private int[] validateBounds(int[] at, char direction) {
		int[] toPosition = null;
		switch (choice.charAt(0)) {
			case 'u':
			case 'U':
				if (at[1] == 0) return null;
				toPosition = new int[]{at[0], at[1] - 1};
				break;
			case 'd':
			case 'D': 
				if (at[1] == 7) return null;
				toPosition = new int[]{at[0], at[1] + 1};
				break;
			case 'l':
			case 'L': 
				if (at[0] == 0) return null;
				toPosition = new int[]{at[0] - 1, at[1]};
				break;
			case 'r':
			case 'R': 
				if (at[0] == 3) return null;
				toPosition = new int[]{at[0] + 1, at[1]};
				break;
		}
		return toPosition;
	}
	
	/**
	* This method ensures that a User is selecting a valid move, and that the Piece they're trying to move is their own.
	* This way, a User knows if the move can be made prior to entering the makeMove method.
	* @param User user, the User who is now making a move
	* @return boolean validMove, a true/false value indicating whether the move can/can't happen
	*/
	private boolean startMove(User user) throws IOException {
		System.out.println("Enter a coordinate to select a piece.");
		System.out.println("To forfeit, type 'forfeit' and press Enter");
		choice = read.readLine();
		choice = choice.toUpperCase();
		String validXInputs = "1234";
		String validYInputs = "ABCDEFGH";
		
		 if (choice.equals("FORFEIT")) { // forfeit
			forfeit = true;
			won = true;
			return true; 
		} else if (choice.length() != 2) {   // not the right length
			System.out.println("Input not recognized");
		} else if ((validXInputs+validYInputs).indexOf(choice.charAt(0)) == -1 || (validXInputs+validYInputs).indexOf(choice.charAt(1)) == -1) { // not valid character
			System.out.println("Invalid coordinate");
		} else if ((validXInputs.indexOf(choice.charAt(0)) != -1 && validXInputs.indexOf(choice.charAt(1)) != -1) ||
				   (validYInputs.indexOf(choice.charAt(0)) != -1 && validYInputs.indexOf(choice.charAt(1)) != -1)) { // gave "valid input", but invalid coordinate eg: ff or 23
			System.out.println("Invalid coordinate, double axis");
		} else if (board.getTileInfo(getPosition(choice)).getPiece().isVisible() &&
			board.getTileInfo(getPosition(choice)).getPiece().getColor() != getColor(user.getNickname())) { // picked opposite color AND it's visible
			System.out.println("That's not your piece! Try again!");
		} else {			
			return true; // all looks good, go!
		}
		
		return false;
	}
	
	private int[] getPosition(String in) {
		int x = -1, y = -1;
		if ("1234".indexOf(in.charAt(0)) != -1){
			x = (int)in.charAt(0) - 49;
			y = (int)in.charAt(1) - 65;
		} else {
			x = (int)in.charAt(1) - 49;
			y = (int)in.charAt(0) - 65;
		}
		
		int[] position = {x,y};
		return position;
	}
	
	/**
	* To be implemented: Will record the stats of the game to the game's respective Users.
	*/
	public void recordStats() {
		
	}
	
	/**
	* 
	* @return boolean false, for the state of the board
	*/
	public boolean getStateOfBoard() {
		return false;		
	}
	
	/**
	* Assigns and places 16 Pieces for the given color on the Board (red or black pieces).
	* Each of the individual pieces are set up on the board in starting positions.
	* The number of each type of piece is based on the Banqi Game structure. 
	* @param String color, the color which the pieces should be set to
	*/
	private void setPieces(String color) {

		List<Piece> pieces = new ArrayList<Piece>();

		setGeneralPiece(color, pieces);
		setAdvisorPiece(color, pieces, 2);
		setCannonPiece(color, pieces, 2);
		setChariotPiece(color, pieces, 2);
		setElephantPiece(color, pieces, 2);
		setHorsePiece(color, pieces, 2);
		setSoldierPiece(color, pieces, 5);

	}

	private void setGeneralPiece(String color, List<Piece> pieces) {
		int[] position;
		position = getRandomEmptyTilePosition();
		General general = getGeneral(color, position);
		pieces.add(general);
		board.getTileInfo(position).setPiece(general);
	}

	private void setSoldierPiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Soldier soldier = getSoldier(color, position);
			pieces.add(soldier);
			board.getTileInfo(position).setPiece(soldier);
			count--;
		}
	}

	private void setHorsePiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Horse horse = getHorse(color, position);
			pieces.add(horse);
			board.getTileInfo(position).setPiece(horse);
			count--;
		}
	}

	private void setElephantPiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Elephant elephant = getElephant(color, position);
			pieces.add(elephant);
			board.getTileInfo(position).setPiece(elephant);
			count--;
		}
	}

	private void setChariotPiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Chariot chariot = getChariot(color, position);
			pieces.add(chariot);
			board.getTileInfo(position).setPiece(chariot);
			count--;
		}
	}

	private void setCannonPiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Cannon cannon = getCannon(color, position);
			pieces.add(cannon);
			board.getTileInfo(position).setPiece(cannon);
			count--;
		}
	}

	private void setAdvisorPiece(String color, List<Piece> pieces, int count) {
		int[] position;
		while(count > 0) {
			position = getRandomEmptyTilePosition();
			Advisor advisor = getAdvisor(color, position);
			pieces.add(advisor);
			board.getTileInfo(position).setPiece(advisor);
			count--;
		}
	}
	
	/**
	* This is used to ensure that the pieces are assigned randomly to tiles on the Board.
	* When setting the pieces for the game, this is used to find a place to put a given piece.
	* A random, empty tile (without a piece assigned to it) is returned at the end.
	* @return int [] position, the position of a random empty tile on the board
	*/
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
	
	/**
	* This prints a visual representation of the current board out.  
	* Tiles on the board that have a piece assigned to them have an 'X' and those that are empty have a `-`.
	* The board is printed with this after every move to show the current game board to the Users.
	*/
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

//	
//	public static void main(String[] args) throws IOException { 
//		UserProfile up1 = new UserProfile("User1", "email", "pass", "date", 0,0,0,0);
//		UserProfile up2 = new UserProfile("User2", "email", "pass", "date", 0,0,0,0);
//		User user1 = new User(up1);
//		User user2 = new User(up2);
//		BanqiGame b = new BanqiGame(user1, user2);
//		System.out.println(user1.getNickname());
//		b.setUpBoard();
//		b.printBoard();
//		b.makeMove(user1);
//		b.makeMove(user2);
//		b.makeMove(user1);
//    } 

}
