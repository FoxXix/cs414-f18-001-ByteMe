/**
 * This class implements the builder design pattern.  This is not one of the concepts in the domain of the system.
 * Seemingly, this class is here to separate the creation of objects from their actual representation.
 * From the client package, most of the functionality of the system happens here or at least runs through here, 
 * while much less is done in the other classes which are part of the domain.
 */
package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode;

public class BanqiController {
  //stores all created UserProfiles
	public String profilesFile = "/home/brian/Documents/CS414/Banqi/UserProfiles.txt";	
	protected List<UserProfile> listOfProfiles = new ArrayList<UserProfile>();
	protected static List<User> users = new ArrayList<User>();
	private List<String> userNames = new ArrayList<String>();
	private List<String> openInvites = new ArrayList<String>();

	User U;
	UserNode usernode;

	BanqiGame game;
	Board board;

	private boolean isUser = false;
	// private boolean requestPass = false;

	/**
	 *
	 * @param u, a User to be set
	 */
	public void setUser(User u) {
		this.U = u;
	}

	private boolean validProfile = false;

	/**
	 * If a User has an assigned valid profile, this sets validProfile to true.
	 * @param bool, the presence or absence of a UserProfile
	 */
	public void setValidProfile(boolean bool) {
		validProfile = bool;
	}
	
	//read user inputs
	private static BufferedReader read;

	/**
	 * Constructs a BanqiController with a profile of registered Users.
	 * @param file, a UserProfiles.txt file containing the data for all of the registered Users in the system.
	 */
	public BanqiController(String file) {
		this.profilesFile = file;
	}
	
	/**
	 * Constructs a BanqiController that connects to a UserNode instance.
	 * @param usernode, a UserNode connection to the Server for the Banqi Game system.
	 */
	public BanqiController(UserNode usernode) {
		this.usernode = usernode;
	}
	
	/**
	 * @param names, an ArrayList of all of the nickname for registered Users.
	 */
	public void setNames(List<String> names) {
		this.userNames = names;

	}
	
	/**
	 * @param board, the board to be set
	 */
	public void setBoard(Board board) {
		this.board = board;
//		game.updateBoard(board);
	}

	/**
	 * @return an ArrayList of registered UserProfiles
	 */
	public List<UserProfile> getListProfiles() {
		return listOfProfiles;
	}
	
	/**
	 * @return an ArrayList of Users who are registered in the system
	 */
	public List<User> getListUsers() {
		return users;
	}

	/**
	 * 	Reads a file for a User  and adds the contents to the list of User Profiles.
	 * 	The file contains the unique account details as well as the user's game performance.
	 * 	With all of the data from this file, a new User Profile gets created.
	 * @throws IOException if the profilesFile cannot be read or found.
	 */
	public void readUsers() throws IOException {

		FileReader file = new FileReader(profilesFile);
		BufferedReader buff = new BufferedReader(file);
		String line = null;
		while ((line = buff.readLine()) != null) {
			String name = "";
			String email = "";
			String pass = "";
			String date = "";
			int wins = 0;
			int losses = 0;
			int draws = 0;
			int forf = 0;
			String[] parts = line.split(" ");
			for (int i = 0; i < parts.length; i++) {
				if (i == 0) {
					name = parts[i];
				} else if (i == 1) {
					email = parts[i];
				} else if (i == 2) {
					pass = parts[i];
				} else if (i == 3) {
					date = parts[i] + " ";
				} else if (i == 4) {
					date += parts[i];
				} else if (i == 5) {
					wins = Integer.parseInt(parts[i]);
				} else if (i == 6) {
					losses = Integer.parseInt(parts[i]);
				} else if (i == 7) {
					draws = Integer.parseInt(parts[i]);
				} else if (i == 8) {
					forf = Integer.parseInt(parts[i]);
				} else {
					System.out.println("There shouldn't be anything more on this line");
				}
			}
			UserProfile us = new UserProfile(name, email, pass, date, wins, losses, draws, forf);
			listOfProfiles.add(us);
			// LOAD USERS INTO LIST OF USERS
			User user = new User(us);
			users.add(user);
		}
		buff.close();

	}

	/**
	 * The method controls the entrance into and exit from the game system as well
	 * as registration. While controlling the running of the system, this calls upon
	 * other methods to do these tasks. It allows for and connects to the following
	 * tasks: - Log in (for existing users) [enter '1'] - Create an account/profile
	 * (for new users) [enter '2'] - Log out [enter 'exit'] If the input is not one
	 * of the previous options, the user is prompted that the input is not
	 * recognized.
	 * @throws IOException if the UserProfiles.txt file cannot be read or found.
	 * @throws InterruptedException if the connection to the Server while running is interrupted
	 */
	public void runProgram() throws IOException, InterruptedException {
		readUsers();
		String choice;
		read = new BufferedReader(new InputStreamReader(System.in));
		printTitle();
		boolean existingUser = false;
		boolean exitSystem = false;
		int loginAttempts = 0;
		while (existingUser == false) {
			if (loginAttempts > 0) {
				System.out.println(
						"\nThe nickname and/or password entered is not associated with a registered User.  Please reenter your credentials.");
			}
			System.out.println("1) Login");
			System.out.println("2) Create profile");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
			printSpacer();
			// attempt log-in
			if (choice.equals("1")) {
				getUserName();
				TimeUnit.SECONDS.sleep(1);

				if (isUser == true) {
					existingUser = true;
				}
				loginAttempts++;

			} else if (choice.equals("2")) {
				makeNewUser();
			} else if (choice.equals("exit")) {
				exitSystem = true;
				existingUser = true;
			} else {
				System.out.println("Input not recognized");
			}
			if (exitSystem) {
				read.close();
			}
		}
		// user has logged in
		while (!exitSystem) {
			printSpacer();
			System.out.println("\nWelcome to Banqi!  Please enter the number of what you'd like to do.");
			System.out.println("\n1) Play existing game");
			System.out.println("2) Manage invites");
			System.out.println("3) View profile");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
			printSpacer();
			if (choice.equals("1")) {

			} else if (choice.equals("2")) {
				manageInvites();
			} else if (choice.equals("3")) {
				viewProfile();
			} else if (choice.equals("exit")) {
				exitSystem = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
		read.close();
	}

	private void manageInvites() throws IOException {
		boolean exitStatus = false;
		String choice;

		while (!exitStatus) {
			System.out.println("\n1) Accept invite");
			System.out.println("2) Send Invite");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
			printSpacer();
			if (choice.equals("1")) {
				acceptInvite();
			} else if (choice.equals("2")) {
				sendNewInvite();
			} else if (choice.equals("exit")) {
				exitStatus = true;
			} else {
				System.out.println("Input not recognized");
			}
			exitStatus = true;
		}
	}
  
	private void sendNewInvite() throws IOException {
		String choice;
		boolean exitSystem2 = false;
		while (!exitSystem2) {
			System.out.println("Select a user from this list to send invite to:");

			int count = 1;
			int myIndex = -1;
			for (String s : userNames) {
				if (!s.equals(U.getNickname())) {
					System.out.println(count +") " + s);
					count++;
				} else {
					myIndex = count-1;
				}

			}
			System.out.println("\nTo exit, type 'exit' and press Enter");
			
			choice = read.readLine();
			int number = 0;
		    if (choice.equals("exit")) {
		    	exitSystem2 = true;
			} else {
				do {
					try {
						number = Integer.parseInt(choice);
					} catch (NumberFormatException e) {
						System.out.println("Input not recognized, try again");
						choice = read.readLine();
					}
				} while (number == 0);
				
				String invitee;
			    if (number <= myIndex) {
			    	invitee = userNames.get(number - 1);
//					    	invitee = users.get(number - 1);
			    } else {
			    	invitee = userNames.get(number);
//					    	invitee = users.get(number);
			    }
//				new Invite(U, invitee);
			    System.out.println("Sent invite to " + invitee);
			    usernode.sendInvite(invitee);
			    U.sendInvite(invitee);
			    U.gamesInvitedTo.add(invitee);
			    System.out.println(U.gamesInvitedTo);
			}				    
		}
	}

	private void acceptInvite() throws IOException {
		String choice;
		boolean exitStatus = false;
		System.out.println("Select an invite from this list to accept:");
		while (!exitStatus) {
			int count = 1;
			for (String inviter : usernode.getGamesInvitedTo()) {
				openInvites.add(inviter);
				System.out.println(count + ") " + inviter);
				count++;					
			}
			System.out.println("To exit, type 'exit' and press Enter");
			
			choice = read.readLine();
			int number = 0;
			if (choice.equals("exit")) {
				exitStatus = true;
			}
			else {
				do {
					try {
						number = Integer.parseInt(choice);
					} catch (NumberFormatException e) {
						System.out.println("Input not recognized, try again");
						choice = read.readLine();
					}
				} while (number == 0);

				String inviter = openInvites.get(number-1);
				usernode.sendAccept(U.getNickname(), inviter);
				//startNewGame(BanqiController.getUser(inviter)); // user to play with
				exitStatus = true;
			}
		}
	}
	
	private void viewProfile() throws IOException {
		boolean exitStatus = false;
		String choice;
		System.out.println(U.getNickname());
		U.seeProfile(U.getNickname());
		while (!exitStatus) {			
			System.out.println("\n1) Search for player");
			System.out.println("To exit, type 'exit' and press Enter");
			
			choice = read.readLine();
			printSpacer();
			if (choice.equals("1")) {
				boolean exitStatus2 = false;
				while (!exitStatus2) {
					System.out.println("Enter the number of the user to view or type 'exit'");
					int count = 1;
					int myIndex = -1;
					for (String s : userNames) {
						if (!s.equals(U.getNickname())) {
							System.out.println(count + ") " + s);
							count++;
						} else {
							myIndex = count - 1;
						}
					}
					choice = read.readLine();
					
					int number = 0;
				    if (choice.equals("exit")) {
				    	exitStatus2 = true;
					} else {
						do {
							try {
								number = Integer.parseInt(choice);
								if (number >= userNames.size() || number <= 0) {
									number = 0;
									throw new NumberFormatException();
								}
							} catch (NumberFormatException e) {
								System.out.println("Input not recognized, try again");
								choice = read.readLine();
							}
						} while (number == 0);
						
						String user;
					    if (number <= myIndex) {
					    	user = userNames.get(number - 1);
					    	users.get(number-1).seeProfile(user);
					    } else {
					    	user = userNames.get(number);
					    	users.get(number).seeProfile(user);
					    }
					}
					    
					exitStatus2 = true;
				}
			} else if (choice.equals("exit")) {
				exitStatus = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
	}
	
	private void searchForUser() throws IOException {
		String choice;
		boolean exitStatus = false;
		while (!exitStatus) {
			System.out.println("Type the nickname of the user to view or type 'exit'");
			choice = read.readLine();
			if (!choice.equals("exit")) {						
				for (User user : users) {
					if (user.getNickname().equals(choice)) {
						user.seeProfile(user.getNickname());
						exitStatus = true;
						break;
					}
				}
				System.out.println("User not found");
			}
			exitStatus = true;
		}
	}

	/**
	 * This method is called from the runProgram method, for when a User enters '1'
	 * for login, the runProgram method calls this, asking the User to enter their
	 * nickname. The following checks are done: - If a profile exists with that
	 * nickname, log-in - Else, the User is prompted that they have no profile and
	 * logging in ends
	 */
	private void getUserName() throws IOException {
		java.io.Console console = System.console();
		String name = "";
		System.out.println("Please Enter your nickname");
		name = read.readLine();
		String password = new String(console.readPassword("Please enter your password: "));
		//System.out.println(password);
		usernode.logIn(name, password);
	}

	/**
	 * Updates the board of a Banqi Game when first making the board, or whenever one of the Users
	 * makes a move.
	 * @param pieceNames, an ArrayList of the Pieces on the Banqi Game Board
	 * @param pieceColors, an ArrayList of the colors of all the Pieces on the board
	 * @param pieceVis, an ArrayList stating the visibility of each of the Pieces on the board
	 * @return Board board, the board of a Banqi Game after any change/move
	 */
  	public Board updateBoard(List<String[]> pieceNames, List<String[]> pieceColors, List<boolean[]> pieceVis) {
		System.out.println("in updateboard");
		Board board = new Board();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				Tile tile = board.getTileInfo(j, i);
				Piece piece = null;
				if (pieceNames.get(i)[j].equals("Soldier")) {
					piece = new Soldier(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("Advisor")) {
					piece = new Advisor(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("Cannon")) {
					piece = new Cannon(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("Chariot")) {
					piece = new Chariot(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("Elephant")) {
					piece = new Elephant(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("General")) {
					piece = new General(pieceColors.get(i)[j], j, i);
				} else if (pieceNames.get(i)[j].equals("Horse")) {
					piece = new Horse(pieceColors.get(i)[j], j, i);
				} else {
					// the piece is null
				}
				tile.setPiece(piece);
			}
		}
		System.out.println("null check" + board);
		return board;
	}

  	/** This implements the functionality needed to register a new User.
	 * The User enters:
	 * - a unique nickname (not in the system)
	 * - an email
	 * - a valid password, entered twice for verification
	 *The system takes care:
	 * - date/time of the account creation
	 * - creating the new User Profile
	 * @throws IOException if the input from the UserNode has an issue
	 * @throws InterruptedException if the connection to the Server is interrupted
	 */
	private void makeNewUser() throws IOException, InterruptedException {
		java.io.Console console = System.console();
		String nickname = "";
		String email = "";
		String password = "";
		String password2 = "";
		do {
			System.out.println("Please Enter a Nickname:");
			nickname = read.readLine();
	
			System.out.println("Please Enter an Email Address:");
			email = read.readLine();
			Boolean match = false;
			do {
				password = new String(console.readPassword("Please enter a password: "));
				
				password2 = new String(console.readPassword("Please re-enter password: "));
				
				if (password.equals(password2)) {
					match = true;
				} else {
					System.out.print("Passwords do not match, try again\n");
				}
			} while (!match);
					
			usernode.createProfile(nickname, email, password);
			TimeUnit.SECONDS.sleep(1);
			
		} while (!validProfile);
	}

	/**
	 * @param nickname, the name of the User to get
	 * @return a User, unless the nickname is not registered with a User, then returns null
	 */
	public static User getUser(String nickname) {
		for (User user : users) {
			if (user.getNickname().equals(nickname)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * @param userStatus, set the Status of the User (i.e. registered)
	 */
	public void setUserStatus(boolean userStatus) {
		this.isUser = userStatus;
	}

	private void printTitle() {
		String title = "=======         =        ==    ==     ======      ========\n"
				+ "===  ===       ===       ===   ==   =========     ========\n"
				+ "===  ===      == ==      ====  ==  ====   ====       ==    \n"
				+ "======       ==   ==     === = ==  ====   ====       ==     \n"
				+ "===  ===    =========    ===  ===  ====   ====       ==    \n"
				+ "===  ===   ===     ===   ===   ==   ===========   ========\n"
				+ "=======   ===       ===  ===    =    =======  ==  ========\n";

		System.out.println(title);
	}

	/**
	 * Initializes a game, and sets the board as given from the server
	 * @param opponent, the other User whose turn it is not
	 * @param turn, true or false, depending on whether it's this user's turn or the other's
	 */
	public void startGame(String opponent, boolean turn) {
		game = new BanqiGame(U, BanqiController.getUser(opponent));
		game.setReader(read);
		game.updateBoard(board);
		System.out.println("startgame board null check " + game.getBoard());
		if(turn == false) {
			game.printBoard();
		}
	}

	/**
	 * Prints a series of lines between printouts in the terminal to make things clearer for the Users
	 */
	public void printSpacer() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	
	/**
	 * Makes a Move.  If it is not a User's turn, the program will wait on that end until the other User plays.
	 * @param turn, the User who is allowed to move
	 * @param opponent, the User who is not currently allowed to move
	 * @param gameID, the unique ID associated with the game
	 * @throws IOException if there is an error with the UserNode input.
	 */
	public void makeMove(boolean turn, String opponent, int gameID) throws IOException {
		game.updateBoard(board);
		game.printBoard();
		System.out.println("Please wait for your turn");
		while (turn != true) {
			//wait
		}
		System.out.println("It is your turn, please make a move");
		game.makeMove(U);
		turn = false;
		
		// move has been made, get state of the game board
		List<String[]> pieceNameArray = new ArrayList<String[]>(8);
		List<String[]> pieceColorArray = new ArrayList<String[]>(8);
		List<boolean[]> pieceVisArray = new ArrayList<boolean[]>(8);

		for (int i = 0; i < 8; i++) {
			String[] pieceNames = new String[4];
			String[] pieceColors = new String[4];
			boolean[] isVis = new boolean[4];
			for (int j = 0; j < 4; j++) {
				// System.out.println("j: " + j + "i: " + i);
				pieceNames[j] = game.getBoard().getTileInfo(j, i).getPiece().getName();
//				System.out.println(pieceNames[j] = game.getBoard().getTileInfo(j, i).getPiece().getName());
				// System.out.println(pieceNames[j]);
				pieceColors[j] = game.getBoard().getTileInfo(j, i).getPiece().getColor();
				// System.out.println(pieceColors[j]);
				isVis[j] = game.getBoard().getTileInfo(j, i).getPiece().isVisible();
			}
			pieceNameArray.add(pieceNames);
			pieceColorArray.add(pieceColors);
			pieceVisArray.add(isVis);
		}
		
		for(int i = 0; i < pieceVisArray.size(); i++) {
			System.out.println(Arrays.toString(pieceVisArray.get(i)));
		}
//		System.out.println("User2: " + game.getUser2());
		usernode.sendMove(pieceNameArray, pieceColorArray, pieceVisArray, game.getUser2().getNickname(), gameID);
	}

	// public static void main(String args[]) throws IOException {
	// BanqiController banqi = new BanqiController(args[0]);
	// banqi.runProgram();
	// }

}
