package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.IOException;

public interface Event {
	
	public byte getType();
	public byte[] getBytes() throws IOException;
	public void unPackBytes(byte[] marshalledBytes) throws IOException;
	
}