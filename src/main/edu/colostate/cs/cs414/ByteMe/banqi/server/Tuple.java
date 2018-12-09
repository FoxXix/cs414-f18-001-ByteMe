/**
 * This class Tuple.java, which a tuple in itself is immutable, is designed to to set the IP and the Port for the server.
 * After they are set they will not change as they are final values.  These two values are important for hosting a
 * server in which two devices/users can connect to the play Banqi.
 */
package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

public class Tuple<S1, S2> {
	public final S1 s1;
	public final S2 s2;
	
	/**
	 * Construct a new Tuple<S1,S2> object
	 * @param s1, the IP address of the server (such as 10.84.134.101)
	 * @param s2, the Port number of the Server (such as 3130)
	 */
	public Tuple(S1 s1, S2 s2) {
		this.s1 = s1;
		this.s2 = s2;
	}
	
	/**
	 *
	 * @return s1, the IP address of the server
	 */
	public S1 getIp() {
		return s1;
	}
	
	/**
	 *
	 * @return s2, the Port number of the server
	 */
	public S2 getPort() {
		return s2;
	}
}
