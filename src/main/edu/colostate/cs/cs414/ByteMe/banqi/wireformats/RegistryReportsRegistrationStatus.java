package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode;

public class RegistryReportsRegistrationStatus implements Event {

	private int successStatus;
	private byte infoLength;
	private byte[] informationString;
	private byte typeRec;

	public int getStatus() {
		return successStatus;
	}
	
	public byte[] getInfoString() {
		return informationString;
	}
	
	@Override
	public byte getType() {
		return 3;
	}

	@Override
	public byte[] getBytes() throws IOException{
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(getType());
		dout.writeInt(successStatus);
		dout.writeByte(informationString.length);
		dout.write(informationString);
		
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}
	
	public void setStatus(int succStatus, byte length, byte[] infoString) {
		successStatus = succStatus;
		infoLength = length;
		informationString = infoString;
	}

	@Override
	public void unPackBytes(byte[] marshalledBytes) throws IOException {
//		System.out.println("unpack the bytes");
		
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		typeRec = din.readByte();
		successStatus = din.readInt();
		byte lengthRec = din.readByte();
		byte[] infoString = new byte[lengthRec];
		din.readFully(infoString);
		informationString = infoString;
		
		baInputStream.close();
		din.close();
	}

}