package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode;

public class BanqiController {

	private String profilesFile = "/s/bach/l/under/sporsche/cs414/Banqi/UserProfiles.txt";
	// stores all created UserProfiles
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

	public void setUser(User u) {
		this.U = u;
	}

	// public void setRequestPassword() {
	// requestPass = true;
	// }

	// read user inputs
	private static BufferedReader read;

	// constructor
	public BanqiController(String file) {
		this.profilesFile = file;
	}

	public BanqiController(UserNode usernode) {
		this.usernode = usernode;
	}

	public void setNames(List<String> names) {
		this.userNames = names;

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	/*
	 * Reads a file for a User and adds the contents to the list of User Profiles.
	 * The file contains the unique account details as well as the user's game
	 * performance. With all of the data from this file, a new User Profile gets
	 * created.
	 */

	public List<UserProfile> getListProfiles() {
		return listOfProfiles;
	}

	public List<User> getListUsers() {
		return users;
	}

	// reads the Users file and adds them to the list of Profiles
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

	/*
	 * The method controls the entrance into and exit from the game system as well
	 * as registration. While controlling the running of the system, this calls upon
	 * other methods to do these tasks. It allows for and connects to the following
	 * tasks: - Log in (for existing users) [enter '1'] - Create an account/profile
	 * (for new users) [enter '2'] - Log out [enter 'exit'] If the input is not one
	 * of the previous options, the user is prompted that the input is not
	 * recognized.
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
			} else {
				System.out.println("Input not recognized");
			}
			if (exitSystem) {
				read.close();
			}
		}
		// user has logged in
		while (!exitSystem) {
			System.out.println("\nWelcome to Banqi!  Please enter the number of what you'd like to do.");
			System.out.println("\n1) Play existing game");
			System.out.println("2) Manage invites");
			System.out.println("3) View profile");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
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
		boolean b = false;
		String choice;

		U.getInviteStatus();

		while (!b) {
			System.out.println("\n1) Accept invite");
			System.out.println("2) Send Invite");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
			if (choice.equals("1")) {
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
					} else {
						do {
							try {
								number = Integer.parseInt(choice);
							} catch (NumberFormatException e) {
								System.out.println("Input not recognized, try again");
								choice = read.readLine();
							}
						} while (number == 0);

						String inviter = openInvites.get(number - 1);
						System.out.println(inviter);
						usernode.sendAccept(U.getNickname(), inviter);
						// startNewGame(BanqiController.getUser(inviter));
						// user to play with
						exitStatus = true;
					}
				}
			} else if (choice.equals("2")) {
				boolean exitSystem2 = false;
				while (!exitSystem2) {
					System.out.println("Select a user from this list to send invite to:");

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
					System.out.println("\nTo exit, type 'exit' and press Enter");
					// for (User user : users) {
					// if (!user.getNickname().equals(U.getNickname())) {
					// System.out.println(count +") " + user.getNickname());
					// count++;
					// } else
					// myIndex = count-1;
					// }

					choice = read.readLine();
					// System.out.println(choice);
					int number = 0;
					if (choice.equals("exit")) {
						exitSystem2 = true;
					} else {
						do {
							try {
								number = Integer.parseInt(choice);
								// System.out.println(number);
							} catch (NumberFormatException e) {
								System.out.println("Input not recognized, try again");
								choice = read.readLine();
							}
						} while (number == 0);

						String invitee;
						if (number <= myIndex) {
							invitee = userNames.get(number - 1);
							// invitee = users.get(number - 1);
						} else {
							invitee = userNames.get(number);
							// invitee = users.get(number);
						}
						// send invite message
						// new Invite(U, invitee);
						System.out.println("Sent invite to " + invitee);
						usernode.sendInvite(invitee);
						U.sendInvite(invitee);
						U.gamesInvitedTo.add(invitee);
						System.out.println(U.gamesInvitedTo);
					}
				}
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
			b = true;
		}
	}

	private void viewProfile() throws IOException {
		boolean b = false;
		String choice;
		System.out.println(U.getNickname());
		U.seeProfile(U.getNickname());
		while (!b) {
			System.out.println("\n1) Search for player");
			System.out.println("To exit, type 'exit' and press Enter");

			choice = read.readLine();
			if (choice.equals("1")) {
				boolean c = false;
				while (!c) {
					System.out.println("Type the nickname of the user to view or type 'exit'");
					choice = read.readLine();
					if (!choice.equals("exit")) {
						for (User user : users) {
							if (user.getNickname().equals(choice)) {
								user.seeProfile(user.getNickname());
								c = true;
								break;
							}
						}
						System.out.println("User not found");
					}
					c = true;
				}
			} else if (choice.equals("exit")) {
				b = true;
			} else {
				System.out.println("Input not recognized");
			}
		}
	}

	/*
	 * This method is called from the runProgram method, for when a User enters '1'
	 * for login, the runProgram method calls this, asking the User to enter their
	 * nickname. The following checks are done: - If a profile exists with that
	 * nickname, log-in - Else, the User is prompted that they have no profile and
	 * logging in ends
	 */
	private void getUserName() throws IOException {
		// System.out.println(listOfProfiles.size());
		// for(UserProfile t : listOfProfiles) {
		// System.out.println(t.getUserName());
		// }
		String name = "";
		System.out.println("Please Enter your nickname");
		name = read.readLine();
		System.out.println("Please enter your password");
		String password = read.readLine();
		System.out.println(password);
		usernode.logIn(name, password);
		// boolean found = false;
		// do {
		// name = "";
		// System.out.println("Please Enter your nickname");
		// name = read.readLine();
		// System.out.println("name entered is: " + name);
		// UserProfile profile = getOwnUser(name);
		// // System.out.println("new Profile: " + profile);
		// if (profile != null) {
		// U = new User(profile);
		// found = true;
		// } else {
		// System.out.println("We could not find a profile with name of: " + name);
		// }
		// } while (!found);
	}

	/*
	 * This method permits a User to view their own Profile, so they can see their
	 * game stats. With the entrance of nickname that exists in the system, the User
	 * Profile associated with that nickname is presented to the User.
	 */
	private UserProfile getOwnUser(String nickname) throws IOException {
		for (UserProfile prof : listOfProfiles) {
			// System.out.println("UserName is: " + prof.getUserName());
			if (prof.getUserName().equals(nickname)) {
				// log in, by entering password
				// boolean success = enterCredentials(prof);
				// if (success) {
				// System.out.println("Welcome User " + prof.getUserName() + "!");
				// return prof;
				// }
			}
		}
		return null;
	}

	/*
	 * This method verifies the credentials of a User, to ensure they are registered
	 * in the system. In addition it implements a security measure, by giving the
	 * User no more than three attempts to enter a valid password. The verification
	 * process is cancelled if the User does not input a password associated with
	 * the given nickname.
	 */
	public void enterCredentials() throws IOException {
		// int count = 0;
		// while (count < 3) {
		System.out.println("Please enter your password");
		String password = read.readLine();
		System.out.println(password);
		usernode.sendPassword(password);
		// System.out.println(prof.getPassword());
		// if(!(password.equals(prof.getPassword()))) {
		// count++;
		// System.out.println("Password was incorrect, you have " + (3 - count) + "
		//// attempts remaining");
		// }
		// else {
		// return true;
		// }
		// }

	}

	/*
	 * This implements the functionality needed to register a new User. The User
	 * enters: - a unique nickname (not in the system) - an email - a valid
	 * password, entered twice for verification The system takes care: - date/time
	 * of the account creation - creating the new User Profile
	 */
	private void makeNewUser() throws IOException {
		boolean passMatch = false;
		String nickname = "";
		String email = "";
		String password = "";

		// Enter data using BufferReader
		System.out.println("Please Enter a Nickname:");
		nickname = read.readLine();

		System.out.println("Please Enter an Email Address:");
		email = read.readLine();

		while (!passMatch) {
			System.out.println("Please Enter a Password:");
			password = read.readLine();

			System.out.println("Please Re-enter Your Password:");
			String reEntered = read.readLine();

			if (password.equals(reEntered)) {
				passMatch = true;
			} else {
				System.out.println("Passwords Do Not Match!");
			}
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		UserProfile newUser = new UserProfile(nickname, email, password, dtf.format(now), 0, 0, 0, 0);
		listOfProfiles.add(newUser);
		users.add(new User(newUser));
		// System.out.println(listOfProfiles.size());
		writeToFile(newUser);

	}

	public void playGame(String opponent, List<String[]> pieceNames, List<String[]> pieceColors,
			List<boolean[]> pieceVis) {
		System.out.println("In Play Game method");
		User opponen = this.getUser(opponent);
		game = new BanqiGame(U, opponen);
		Board board = updateBoard(pieceNames, pieceColors, pieceVis);

	}

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

		return board;
	}

	/*
	 * This writes a new file to the storage system, in order to record the current
	 * details of a User Profile. The file will contain everything on the User
	 * Profile: - nickname, email, password, registration date - wins, losses,
	 * draws, forfeits This file will be stored with the records of the Banqi Game
	 * system.
	 */
	private void writeToFile(UserProfile u) {
		String s = u.getUserName() + " " + u.getEmail() + " " + u.getPassword() + " " + u.getJoinedDate() + " "
				+ u.getWins() + " " + u.getLosses() + " " + u.getDraws() + " " + u.getForfeits();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(profilesFile, true));
			out.write(s);
			out.close();
		} catch (IOException e) {

		}

	}

	/*
	 * This function returns a user object given the users nickname. Null is
	 * returned if not found.
	 */
	public static User getUser(String nickname) {
		for (User user : users) {
			if (user.getNickname().equals(nickname)) {
				return user;
			}
		}
		return null;
	}

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

	public void startGame(String opponent, boolean turn) throws IOException {
		game = new BanqiGame(U, BanqiController.getUser(opponent));
		game.updateBoard(board);
		game.printBoard();
		while (turn != true) {
			//wait
		}
		System.out.println("It is your turn, please make a move");
		game.makeMove(U);

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
				// System.out.println(pieceNames[j]);
				pieceColors[j] = game.getBoard().getTileInfo(j, i).getPiece().getColor();
				// System.out.println(pieceColors[j]);
				isVis[j] = game.getBoard().getTileInfo(j, i).getPiece().isVisible();
			}
			pieceNameArray.add(pieceNames);
			pieceColorArray.add(pieceColors);
			pieceVisArray.add(isVis);

			usernode.sendMove(pieceNameArray, pieceColorArray, pieceVisArray, opponent);
		}
	}

	// public static void main(String args[]) throws IOException {
	// BanqiController banqi = new BanqiController(args[0]);
	// banqi.runProgram();
	// }

}