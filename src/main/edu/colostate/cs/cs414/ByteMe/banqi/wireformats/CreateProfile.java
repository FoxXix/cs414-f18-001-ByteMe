package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CreateProfile implements Event{
	
	private byte[] nickname;
	private byte nicknameLength;
	private byte[] email;
	private byte emailLength;
	private byte[] password;
	private byte passwordLength;
	
	public void setNickname(byte length, byte[] nickname) {
		this.nickname = password;
		this.nicknameLength = length;
	}
	
	public void setEmail(byte length, byte[] email) {
		this.email = password;
		this.emailLength = length;
	}
	
	public void setPassword(byte length, byte[] password) {
		this.password = password;
		this.passwordLength = length;
	}
	
	public byte[] getNickname() {
		return nickname;
	}
	
	public byte[] getEmail() {
		return email;
	}
	
	public byte[] getPassword() {
		return password;
	}

	@Override
	public byte getType() {
		return 10;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeByte(getType());
		dout.writeByte(nicknameLength);
		dout.write(nickname);
		dout.writeByte(emailLength);
		dout.write(email);
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
		
		byte nicknameLen = din.readByte();
		byte[] name = new byte[nicknameLen];
		din.readFully(name);
		nickname = name;
		
		byte emailLen = din.readByte();
		byte[] email = new byte[emailLen];
		din.readFully(email);
		this.email = email;
		
		byte lengthRec = din.readByte();
		byte[] pw = new byte[lengthRec];
		din.readFully(pw);
		password = pw;

		baInputStream.close();
		din.close();
		
	}
}