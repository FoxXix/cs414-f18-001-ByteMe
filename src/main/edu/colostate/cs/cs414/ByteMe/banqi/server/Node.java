package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

import java.io.IOException;

import main.edu.colostate.cs.cs414.ByteMe.banqi.transport.TCPConnection;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;

public abstract class Node {
	
	public abstract void OnEvent(Event e, TCPConnection connect) throws IOException;

}