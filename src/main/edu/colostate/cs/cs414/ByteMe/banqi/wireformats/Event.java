/**
 * The Event.java interface outlines the methods that are to be used to categorize and handle events through this
 * application of distributed systems.  The Events are categorized by the type of byte that they are, as different
 * events are assigned different bytes.
 * 
 * The three methods identified here are:
 * 		getType()
 * 		getBytes()
 * 		unPackBytes(byte[] marshalledBytes)
 * 
 * The classes which implement the Event interface will need to define more methods to handle the specific type of
 * event, however, these are the methods which are applicable to all types of events in the Banqi Game system. 
 * For example CreateProfile.java, which implements Event, defines getters and setters to work with 
 * the Profile credentials, but also still needs these three methods.  Seemingly, the classes which implement this
 * interface share these same details, but may also add more.
 */
package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.IOException;

public interface Event {
	
	/**
	 * Events are encoded in bytes to send them as messages.  This method returns the type of byte.
	 * Each type of event is recognized based on what the byte is.
	 * @return byte, the type of byte
	 */
	public byte getType();
	
	/**
	 * Returns the actual bytes in a byte[] pertaining to the
	 * @return byte[] containing the bytes of the event
	 * @throws IOException if the byte[] cannot be read
	 */
	public byte[] getBytes() throws IOException;
	
	/**
	 * Unpacks the bytes given in the marshalledBytes byte[].
	 * Once unpacked, items such as User nickname, email and password are accessible in their actual form.
	 * @param marshalledBytes, a byte[] which contains the bytes to unpack
	 * @throws IOException
	 */
	public void unPackBytes(byte[] marshalledBytes) throws IOException;
	
}
