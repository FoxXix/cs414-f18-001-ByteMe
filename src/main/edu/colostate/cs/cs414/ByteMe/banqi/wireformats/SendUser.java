package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import main.edu.colostate.cs.cs414.ByteMe.banqi.client.User;

public class SendUser implements Event {

	byte[] nickname;
	byte nameLength;
	byte[] email;
	byte emailLength;
	byte[] password;
	byte passLength;
	byte[] date;
	byte dateLength;
	int wins;
	int losses;
	int draws;
	int forfeits;
	
	Byte[] lengths;
	List<byte[]> names = new ArrayList<byte[]>();

	public void setInfo(byte[] name, byte nameLength, byte[] email,  byte emailLength, byte[] password, byte passLength,
			byte[] date, byte dateLength, int wins, int losses, int draws, int forfeits) {
		this.nickname = name;
		this.nameLength = nameLength;
		this.email = email;
		this.emailLength = emailLength;
		this.password = password;
		this.passLength = passLength;
		this.date = date;
		this.dateLength = dateLength;
		this.wins = wins;
		this.losses = losses;
		this.draws = draws;
		this.forfeits = forfeits;
	}
	
	public void setListUsers(Byte[] namesLengths, List<byte[]> names2) {
		this.lengths = namesLengths;
		this.names = names2;
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
	
	public byte[] getDate() {
		return date;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	public int getDraws() {
		return draws;
	}
	
	public int getForfeits() {
		return forfeits;
	}
	
	public List<byte[]> getNames() {
		return names;
	}

	@Override
	public byte getType() {
		return 11;
	}

	@Override
	public byte[] getBytes() throws IOException {
		
		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
	
		dout.writeByte(getType());
		dout.writeByte(nameLength);
		dout.write(nickname);
		dout.writeByte(emailLength);
		dout.write(email);
		dout.writeByte(passLength);
		dout.write(password);
		dout.writeByte(dateLength);
		dout.write(date);
		dout.writeInt(wins);
		dout.writeInt(losses);
		dout.writeInt(draws);
		dout.writeInt(forfeits);
		
		int s = names.size();
		dout.writeInt(s);
		
		for(int i = 0; i < names.size(); i++) {
			dout.writeByte(lengths[i]);
			dout.write(names.get(i));
		}
		
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
		byte nameRec = din.readByte();
		byte[] name = new byte[nameRec];
		din.readFully(name);
		nickname = name;
		byte emailRec = din.readByte();
		byte[] em = new byte[emailRec];
		din.readFully(em);
		email = em;
		byte lengthPass = din.readByte();
		byte[] pass = new byte[lengthPass];
		din.readFully(pass);
		password = pass;
		byte datePass = din.readByte();
		byte[] dat = new byte[datePass];
		din.readFully(dat);
		date = dat;
		
		wins = din.readInt();
		losses = din.readInt();
		draws = din.readInt();
		forfeits = din.readInt();
		
		int s = din.readInt();
		
		for(int i = 0; i < s; i++) {
			byte length = din.readByte();
			byte[] nam = new byte[length];
			din.readFully(nam);
			names.add(nam);
		}
		

		baInputStream.close();
		din.close();
		
	}
}
