package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

public class UserProfile {
	private String nickname = "";
	private String email = "";
	private String password = "";
	private String joinedDate = null;
	private int wins = 0;
	private int losses = 0;
	private int draws = 0;
	private int forfeits = 0;
	
	/**
	 * Construct a new UserProfile for a new User registering in the system.
	 * The nickname, email and password come via command line from the User.
	 * The joinedDate is set by the system with LocalTime.
	 * The rest of the data starts out at 0 when first registered, and is incremented based on game results.
	 * @param nickname, the String nickname of a User who's profile this is
	 * @param email, the String email of a User who's profile this is
	 * @param password, the String password of the User who's profile this is
	 * @param joinedDate, the String identifying the date/time of the User's registration
	 * @param wins, the number of Banqi game wins this User has
	 * @param losses, the number of Banqi game losses this User has
	 * @param draws, the number of Banqi game draws this User has
	 * @param forfeits, the number of Banqi game forfeits this User has
	 */
	public UserProfile(String nickname, String email, String password, String joinedDate, int wins, int losses, int draws, int forfeits) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.joinedDate = joinedDate;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.forfeits = forfeits;
	}
	

	/**
	 *
	 * @return nickname, the nickname on this UserProfile
	 */
	public String getUserName() {
		return nickname;
	}

	/**
	 *
	 * @param newName, the nickname to be set for this UserProfile
	 */
	public void setUserName(String newName) {
		this.nickname = newName;
	}

	/**
	 *
	 * @return password, the password related to this UserProfile
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @param newPassword, the password to be set to this UserProfile
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	/**
	 *
	 * @return email, the email associated with this UserProfile
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @param newEmail, the email to be set to this UserProfile
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	/**
	 *
	 * @return joinDate, the date this UserProfile was made
	 */
	public String getJoinedDate() {
		return joinedDate;
	}

	/**
	 *
	 * @return wins, the number of wins on this UserProfile
	 */
	public int getWins() {
		return wins;
	}
	
	/**
	 * Whenever a specific User wins a game, the count on their profile for the number of wins they have
	 * since they started playing Banqi gets incremented
	 */
	public void incrementWins() {
		wins += 1;
	}

	/**
	 *
	 * @return losses, the number of losses on this UserProfile
	 */
	public int getLosses() {
		return losses;
	}
	/* Whenever a specific User loses a game, the count on their profile for the number of losses they have
	since they started playing Banqi gets incremented*/

	/**
	 * Whenever a specific User loses a game, the count on their profile for the number of losses they have
	 * since they started playing Banqi gets incremented
	 */
	public void incrementLosses() {
		losses += 1;
	}

	/**
	 *
	 * @return draws, the number of draws on this UserProfile
	 */
	public int getDraws() {
		return draws;
	}
	
	/**
	 *  Whenever a specific User draws a game, the count on their profile for the number of draws they have
	 *  since they started playing Banqi gets incremented
	 */
	public void incrementDraws() {
		draws += 1;
	}

	/**
	 *
	 * @return forfeits, the number of forfeits on this UserProfile
	 */
	public int getForfeits() {
		return forfeits;
	}
	
	/**
	* Whenever a specific User forfeits a game, the count on their profile for the number of forfeits they have
	* since they started playing Banqi gets incremented
	 */
	public void incrementForfeits() {
		forfeits += 1;
	}
}
