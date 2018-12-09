/**
 * Piece.java is an interface class that sets up the appearance and functionality of all of the Pieces in Banqi.
 *
 * There are seven types of Pieces in Banqi, whose functionality implement this Piece interface.
 * The Pieces in Banqi are:
 * 		- General
 * 		- Advisor
 * 		- Elephant
 * 		- Chariot
 * 		- Horse
 * 		- Cannon
 * 		- Soldier
 *
 * Each piece has a position on the Bangi Game Board also has the following attributes:
 * 		- x position (the position of the piece horizontally)
 * 		- y position (the position of the piece vertically)
 * 		- color: indicates whether the piece is on the black or red team
 *
 * As well as the other gameplay-facilitating variables:
 * 		-isCaptured: indicates whether a Piece has been captured (t/f)
 * 		-visible: indicates whether the Piece has been turned over or not since the game started
 * 		-name: the name of the Piece (i.e. General)
 *
 * The methods defined in this interface will all be defined and overridden by the classes which implement it.
 * In order to fulfill the rules of Banqi and effectively implement this game, these functions must be defined
 * by each individual Piece (the ones named above).
 *
 * @author Team ByteMe
 * @since 12/09/2018
 */
package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public interface Piece {
	
	int X_position = -1;
	int Y_position = -1;
	boolean isCaptured = false;
	boolean visible = false;
	String color = "Blank";
	String name = "Piece";
	int rank = 0;
	
	/**
	 * Make an actual, valid move with a given Piece.
	 * @return boolean false
	 */
	public boolean makeMove();
	
	/**
	 * Every type of Piece has a name to distinguish it in the system against other Pieces.
	 * @return String name, the name of the Piece
	 */
	public String getName();

	/**
	 * The color of a Piece is either red or black, depending on the team it's on.
	 * @return String color, the color of the Piece
	 */
	public String getColor();

	/**
	 * Make a piece that was not "turned over", "turned over", when a User first comes upon it.
	 * This must be done for every Piece in the game at some point, before that Piece can be played with.
	 */
	public void makeVisible();

	/**
	 * This method checks the visibility of a given Piece in the Banqi Game.
	 * @return boolean visibility, true or false depending on whether the Piece is/is not turned over
	 */
	public boolean isVisible();

	/**
	 * When an enemy Piece captures a Piece,
	 * call this method to set the status of a Piece that has not been captured, to captured
	 */
	public void isCaptured();

	/**
	 * Each Piece has a rank, indicating it's ability to capture other Pieces of lower/equal rank
	 * @return int rank, the rank/power of a Piece in relation to other Pieces
	 */
	public int getRank();

}
