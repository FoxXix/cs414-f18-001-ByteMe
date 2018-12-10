/**
 * This abstract class, entitled Node, is a very basic idea for any type of Node to handle in a system.
 * In the case of this project, this abstract class will be extended as UserNode.java, Server.java or another class
 * which needs to handle events through the OnEvent method.
 */

package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.io.IOException;

import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;

public abstract class Node {
	/**
	 * An abstract framework for how to handle any event that happens in the Banqi Game system.
	 * @param Event e, the event in which to handle (i.e. sending an invite)
	 * @see Event.java interface in the wireformats package for more info on the types of events in this system
	 * @param TCPConnection connect, a connection between two devices
	 * @throws IOException, if something cannot be correctly read
	 */
	public abstract void OnEvent(Event e, TCPConnection connect) throws IOException;

}
