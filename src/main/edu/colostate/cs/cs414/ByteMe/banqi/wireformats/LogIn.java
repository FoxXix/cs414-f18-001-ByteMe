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
	
	private byte[] password;
	private byte passwordLength;
	
	public void setNickname(byte length, byte[] nickname) {
		this.nickname = nickname;
		this.nameLength = length;
	}
	
	public void setPassword(byte length, byte[] password) {
		this.passwordLength = length;
		this.password = password;
	}
	
	public byte[] getNickname() {
		return nickname;
	}
	
	public byte[] getPassword() {
		return password;
	}

	@Override
	public byte getType() {
		return 6;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
//		ObjectOutput out = null;
//		out = new ObjectOutputStream(baOutputStream);
//		out.writeObject(obj);
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		
		dout.writeByte(getType());
		dout.writeByte(nameLength);
		dout.write(nickname);
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
		byte lengthPass = din.readByte();
		byte[] pass = new byte[lengthPass];
		din.readFully(pass);
		nickname = name;
		password = pass;

		baInputStream.close();
		din.close();
		
	}

}
