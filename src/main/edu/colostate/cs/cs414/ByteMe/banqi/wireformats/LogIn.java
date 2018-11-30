package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LogIn implements Event{
	
	private byte[] nickname;
	private byte nameLength;
	
	public void setNickname(byte length, byte[] nickname) {
		this.nickname = nickname;
		this.nameLength = length;
	}
	
	public byte[] getNickname() {
		return nickname;
	}

	@Override
	public byte getType() {
		return 6;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(getType());
		dout.writeByte(nameLength);
		dout.write(nickname);
		
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
		nickname = name;

		baInputStream.close();
		din.close();
		
	}

}
