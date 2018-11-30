package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendPassword implements Event{
	
	private byte[] password;
	private byte passwordLength;
	
	public void setPassword(byte length, byte[] password) {
		this.password = password;
		this.passwordLength = length;
	}
	
	public byte[] getPassword() {
		return password;
	}

	@Override
	public byte getType() {
		return 9;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(getType());
		dout.writeByte(passwordLength);
		dout.write(password);
		
		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();
		
		baOutputStream.close();
		dout.close();
		return marshalledBytes;
	}

	@Override
	public void unPackBytes(byte[] marshalledBytes) throws IOException {
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
		
		int type = din.readByte();
		byte lengthRec = din.readByte();
		byte[] name = new byte[lengthRec];
		din.readFully(name);
		password = name;

		baInputStream.close();
		din.close();
		
	}

}
