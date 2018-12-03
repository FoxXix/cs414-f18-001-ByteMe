package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendInvite implements Event{
	
	private byte[] invitee;
	private byte inviteeLength;
	
	private byte[] inviteFrom;
	private byte inviteFromLength;
	
	public void setInvitee(byte length, byte[] invitee) {
		this.invitee = invitee;
		this.inviteeLength = length;
	}
	
	public void setInviteFrom(byte length, byte[] inviter) {
		this.inviteFrom = inviter;
		this.inviteFromLength = length;
	}
	
	public byte[] getInvitee() {
		return invitee;
	}
	
	public byte[] getInviteFrom() {
		return inviteFrom;
	}

	@Override
	public byte getType() {
		return 12;
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
		dout.writeByte(inviteeLength);
		dout.write(invitee);
		dout.writeByte(inviteFromLength);
		dout.write(inviteFrom);
		
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

		invitee = name;
		
		byte lengthFRec = din.readByte();
		byte[] nameF = new byte[lengthFRec];
		din.readFully(nameF);
		inviteFrom = nameF;

		baInputStream.close();
		din.close();
		
	}

}
