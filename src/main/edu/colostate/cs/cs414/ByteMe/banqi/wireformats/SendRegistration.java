package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.*;
import java.util.Arrays;

import main.edu.colostate.cs.cs414.ByteMe.banqi.server.Server;
import main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats.Event;

public class SendRegistration implements Event {
	
//	private byte length;
	private byte[] ipAddr;
	private int portNum;
	private byte type = Protocol.SendRegistration;
	
	public int getPortNumber() {
		return portNum;
	}
	
	public byte[] getIpAddr() {
		return ipAddr;
	}
	
	@Override
	public byte getType() {
		return 2;
	}
	
	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(getType());
		dout.writeByte(ipAddr.length);
		dout.write(ipAddr);
		dout.writeInt(portNum);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}
	
	
	//place for the MessagingNode to send the port number and INetAddress
	public void setPortInetAddress(int port, byte[] iNet) {
		portNum = port;
		ipAddr = iNet;
	}
	
	//unpack the marshalled bytes
	public void unPackBytes(byte[] marshalledBytes) throws IOException {
		
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		type = din.readByte();
		byte lengthRec = din.readByte();
		byte[] identifyerBytes = new byte[lengthRec];
		din.readFully(identifyerBytes);
		ipAddr = identifyerBytes;
		portNum = din.readInt();
		
		baInputStream.close();
		din.close();
		
	}
}