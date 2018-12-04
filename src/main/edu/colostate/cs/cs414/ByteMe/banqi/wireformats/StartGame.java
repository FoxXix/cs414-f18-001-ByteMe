package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartGame implements Event {

	ArrayList<byte[]> namN0 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC0 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN1 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC1 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN2 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC2 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN3 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC3 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN4 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC4 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN5 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC5 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN6 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC6 = new ArrayList<byte[]>();
	ArrayList<byte[]> namN7 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC7 = new ArrayList<byte[]>();
	byte[] nameLen0;
	byte[] nameLen1;
	byte[] nameLen2;
	byte[] nameLen3;
	byte[] nameLen4;
	byte[] nameLen5;
	byte[] nameLen6;
	byte[] nameLen7;

	byte[] colorLen0;
	byte[] colorLen1;
	byte[] colorLen2;
	byte[] colorLen3;
	byte[] colorLen4;
	byte[] colorLen5;
	byte[] colorLen6;
	byte[] colorLen7;

	boolean[] vis0 = new boolean[4];
	boolean[] vis1 = new boolean[4];
	boolean[] vis2 = new boolean[4];
	boolean[] vis3 = new boolean[4];
	boolean[] vis4 = new boolean[4];
	boolean[] vis5 = new boolean[4];
	boolean[] vis6 = new boolean[4];
	boolean[] vis7 = new boolean[4];

	private byte[] playerName;
	private byte length;

	public void setPlayerName(byte length, byte[] name) {
		this.length = length;
		this.playerName = name;
	}

	public void setNameLengths(byte[] nN) {
		this.nameLen0 = nN;
	}

	public void setNameLengths1(byte[] nN) {
		this.nameLen1 = nN;
	}

	public void setNameLengths2(byte[] nN) {
		this.nameLen2 = nN;
	}

	public void setNameLengths3(byte[] nN) {
		this.nameLen3 = nN;
	}

	public void setNameLengths4(byte[] nN) {
		this.nameLen4 = nN;
	}

	public void setNameLengths5(byte[] nN) {
		this.nameLen5 = nN;
	}

	public void setNameLengths6(byte[] nN) {
		this.nameLen6 = nN;
	}

	public void setNameLengths7(byte[] nN) {
		this.nameLen7 = nN;
	}

	public void setColorLengths0(byte[] cN) {
		this.colorLen0 = cN;
	}

	public void setColorLengths1(byte[] cN) {
		this.colorLen1 = cN;
	}

	public void setColorLengths2(byte[] cN) {
		this.colorLen2 = cN;
	}

	public void setColorLengths3(byte[] cN) {
		this.colorLen3 = cN;
	}

	public void setColorLengths4(byte[] cN) {
		this.colorLen4 = cN;
	}
	
	public void setColorLengths5(byte[] cN) {
		this.colorLen5 = cN;
	}

	public void setColorLengths6(byte[] cN) {
		this.colorLen6 = cN;
	}

	public void setColorLengths7(byte[] cN) {
		this.colorLen7 = cN;
	}

	public void setNames0(ArrayList<byte[]> pieceNameBytes) {
		namN0 = pieceNameBytes;
	}

	public void setNames1(ArrayList<byte[]> pieceNameBytes) {
		namN1 = pieceNameBytes;
	}

	public void setNames2(ArrayList<byte[]> pieceNameBytes) {
		namN2 = pieceNameBytes;
	}

	public void setNames3(ArrayList<byte[]> pieceNameBytes) {
		namN3 = pieceNameBytes;
	}

	public void setNames4(ArrayList<byte[]> pieceNameBytes) {
		namN4 = pieceNameBytes;
	}

	public void setNames5(ArrayList<byte[]> pieceNameBytes) {
		namN5 = pieceNameBytes;
	}

	public void setNames6(ArrayList<byte[]> pieceNameBytes) {
		namN6 = pieceNameBytes;
	}

	public void setNames7(ArrayList<byte[]> pieceNameBytes) {
		namN7 = pieceNameBytes;
	}

	public void setColor0(ArrayList<byte[]> pieceColorBytes) {
		colC0 = pieceColorBytes;
	}

	public void setColor1(ArrayList<byte[]> pieceColorBytes) {
		colC1 = pieceColorBytes;
	}

	public void setColor2(ArrayList<byte[]> pieceColorBytes) {
		colC2 = pieceColorBytes;
	}

	public void setColor3(ArrayList<byte[]> pieceColorBytes) {
		colC3 = pieceColorBytes;
	}

	public void setColor4(ArrayList<byte[]> pieceColorBytes) {
		colC4 = pieceColorBytes;
	}

	public void setColor5(ArrayList<byte[]> pieceColorBytes) {
		colC5 = pieceColorBytes;
	}

	public void setColor6(ArrayList<byte[]> pieceColorBytes) {
		colC6 = pieceColorBytes;
	}

	public void setColor7(ArrayList<byte[]> pieceColorBytes) {
		colC7 = pieceColorBytes;
	}

	public void setVis0(boolean[] v) {
		this.vis0 = v;
	}

	public void setVis1(boolean[] v) {
		this.vis1 = v;
	}

	public void setVis2(boolean[] v) {
		this.vis2 = v;
	}

	public void setVis3(boolean[] v) {
		this.vis3 = v;
	}

	public void setVis4(boolean[] v) {
		this.vis4 = v;
	}

	public void setVis5(boolean[] v) {
		this.vis5 = v;
	}

	public void setVis6(boolean[] v) {
		this.vis6 = v;
	}

	public void setVis7(boolean[] v) {
		this.vis7 = v;
	}


	public byte[] getPlayerName() {
		return playerName;
	}
	
	public ArrayList<byte[]> getNam0(){
		return namN0;
	}
	public ArrayList<byte[]> getNam1(){
		return namN1;
	}
	public ArrayList<byte[]> getNam2(){
		return namN2;
	}
	public ArrayList<byte[]> getNam3(){
		return namN3;
	}
	public ArrayList<byte[]> getNam4(){
		return namN4;
	}
	public ArrayList<byte[]> getNam5(){
		return namN5;
	}
	public ArrayList<byte[]> getNam6(){
		return namN6;
	}
	public ArrayList<byte[]> getNam7(){
		return namN7;
	}
	//-------------------------------------------------------
	public ArrayList<byte[]> getcolC0(){
		return colC0;
	}
	public ArrayList<byte[]> getcolC1(){
		return colC1;
	}
	public ArrayList<byte[]> getcolC2(){
		return colC2;
	}
	public ArrayList<byte[]> getcolC3(){
		return colC3;
	}
	public ArrayList<byte[]> getcolC4(){
		return colC4;
	}
	public ArrayList<byte[]> getcolC5(){
		return colC5;
	}
	public ArrayList<byte[]> getcolC6(){
		return colC6;
	}
	public ArrayList<byte[]> getcolC7(){
		return colC7;
	}
	//------------------------------------------------------
	public boolean[] getVis0() {
		return vis0;
	}
	public boolean[] getVis1() {
		return vis1;
	}
	public boolean[] getVis2() {
		return vis2;
	}
	public boolean[] getVis3() {
		return vis3;
	}
	public boolean[] getVis4() {
		return vis4;
	}
	public boolean[] getVis5() {
		return vis5;
	}
	public boolean[] getVis6() {
		return vis6;
	}
	public boolean[] getVis7() {
		return vis7;
	}

	// public List<ArrayList<byte[]>> getPieceNames() { = new boolean[4];
	// return pieceNames;
	// }
	//
	// public List<ArrayList<byte[]>> getPieceColors() {
	// return pieceColors;
	// }
	//
	// public List<boolean[]> getPieceVis() {
	// return pieceVis;
	// }

	@Override
	public byte getType() {
		return 15;
	}

	@Override
	public byte[] getBytes() throws IOException {

		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		dout.writeByte(getType());
		dout.writeByte(length);
		dout.write(playerName);

		for (int j = 0; j < 4; j++) {
			// byte l = (byte)g.getBytes().length;
			// System.out.println(l);
			// byte[] gt = g.getBytes();
			//
			dout.writeByte(nameLen0[j]);
			dout.write(namN0.get(j));

			dout.writeByte(colorLen0[j]);
			dout.write(colC0.get(j));

			boolean v = vis0[j];
			dout.writeBoolean(v);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen1[j]);
			dout.write(namN1.get(j));

			dout.writeByte(colorLen1[j]);
			dout.write(colC1.get(j));

			boolean v1 = vis1[j];
			dout.writeBoolean(v1);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen2[j]);
			dout.write(namN2.get(j));

			dout.writeByte(colorLen2[j]);
			dout.write(colC2.get(j));

			boolean v2 = vis2[j];
			dout.writeBoolean(v2);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen3[j]);
			dout.write(namN3.get(j));

			dout.writeByte(colorLen3[j]);
			dout.write(colC3.get(j));

			boolean v3 = vis3[j];
			dout.writeBoolean(v3);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen4[j]);
			dout.write(namN4.get(j));

			dout.writeByte(colorLen4[j]);
			dout.write(colC4.get(j));

			boolean v4 = vis4[j];
			dout.writeBoolean(v4);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen5[j]);
			dout.write(namN5.get(j));

			dout.writeByte(colorLen5[j]);
			dout.write(colC5.get(j));

			boolean v5 = vis5[j];
			dout.writeBoolean(v5);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen6[j]);
			dout.write(namN6.get(j));

			dout.writeByte(colorLen6[j]);
			dout.write(colC6.get(j));

			boolean v6 = vis6[j];
			dout.writeBoolean(v6);
			// ---------------------------------------------------------------
			dout.writeByte(nameLen7[j]);
			dout.write(namN7.get(j));

			dout.writeByte(colorLen7[j]);
			dout.write(colC7.get(j));

			boolean v7 = vis7[j];
			dout.writeBoolean(v7);

		}

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();

		baOutputStream.close();
		dout.close();

		// pieceNames.clear();
		// pieceColors.clear();
		// pieceVis.clear();
		// for(int k = 0; k < pieceNames.size(); k++) {
		// for(int h = 0; h < 4; h++) {
		// pieceNames.remove(k).remove(h);
		// pieceColors.remove(k).remove(h);
		// }
		// pieceVis.remove(k);
		// }
		return marshalledBytes;
	}

	@Override
	// unpack the marshalled bytes
	public void unPackBytes(byte[] marshalledBytes) throws IOException {

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
		DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

		int type = din.readByte();

		byte leng = din.readByte();
		byte[] playName = new byte[leng];
		din.readFully(playName);
		playerName = playName;

		byte[] names = new byte[4];
		byte[] colors = new byte[4];
		boolean[] vis = new boolean[4];
		for (int j = 0; j < 4; j++) {

			byte l = din.readByte();
			byte[] gt = new byte[l];
			System.out.println("name length: " + gt.length);
			din.readFully(gt);
			System.out.println(gt);
			names = gt;
			namN0.add(names);

			byte lc = din.readByte();
			byte[] ct = new byte[lc];
			System.out.println("color byte length: " + ct.length);
			din.readFully(ct);
			colC0.add(ct);
			
			boolean v = din.readBoolean();;
			vis0[j] = v;
			//----------------------------------------------------------------
			byte l1 = din.readByte();
			byte[] gt1 = new byte[l1];
			System.out.println("name length: " + gt1.length);
			din.readFully(gt1);
			System.out.println(gt1);
			names = gt1;
			namN1.add(names);

			byte lc1 = din.readByte();
			byte[] ct1 = new byte[lc1];
			System.out.println("color byte length: " + ct1.length);
			din.readFully(ct1);
			colC1.add(ct1);
			
			boolean v1 = din.readBoolean();;
			vis1[j] = v1;
			//----------------------------------------------------------------
			byte l2 = din.readByte();
			byte[] gt2 = new byte[l2];
			System.out.println("name length: " + gt2.length);
			din.readFully(gt2);
			System.out.println(gt2);
			names = gt2;
			namN2.add(names);

			byte lc2 = din.readByte();
			byte[] ct2 = new byte[lc2];
			System.out.println("color byte length: " + ct2.length);
			din.readFully(ct2);
			colC2.add(ct2);
			
			boolean v2 = din.readBoolean();;
			vis2[j] = v2;
			//----------------------------------------------------------------
			byte l3 = din.readByte();
			byte[] gt3 = new byte[l3];
			System.out.println("name length: " + gt3.length);
			din.readFully(gt3);
			System.out.println(gt3);
			names = gt3;
			namN3.add(names);

			byte lc3 = din.readByte();
			byte[] ct3 = new byte[lc3];
			System.out.println("color byte length: " + ct3.length);
			din.readFully(ct3);
			colC3.add(ct3);
			
			boolean v3 = din.readBoolean();;
			vis3[j] = v3;
			//----------------------------------------------------------------
			byte l4 = din.readByte();
			byte[] gt4 = new byte[l4];
			System.out.println("name length: " + gt4.length);
			din.readFully(gt4);
			System.out.println(gt1);
			names = gt4;
			namN4.add(names);

			byte lc4 = din.readByte();
			byte[] ct4 = new byte[lc4];
			System.out.println("color byte length: " + ct4.length);
			din.readFully(ct4);
			colC4.add(ct4);
			
			boolean v4 = din.readBoolean();;
			vis4[j] = v4;
			//----------------------------------------------------------------
			byte l5 = din.readByte();
			byte[] gt5 = new byte[l5];
			System.out.println("name length: " + gt5.length);
			din.readFully(gt5);
			System.out.println(gt5);
			names = gt1;
			namN1.add(names);

			byte lc5 = din.readByte();
			byte[] ct5 = new byte[lc5];
			System.out.println("color byte length: " + ct5.length);
			din.readFully(ct5);
			colC5.add(ct5);
			
			boolean v5 = din.readBoolean();;
			vis5[j] = v5;
			//----------------------------------------------------------------
			byte l6 = din.readByte();
			byte[] gt6 = new byte[l6];
			System.out.println("name length: " + gt6.length);
			din.readFully(gt6);
			System.out.println(gt6);
			names = gt6;
			namN6.add(names);

			byte lc6 = din.readByte();
			byte[] ct6 = new byte[lc6];
			System.out.println("color byte length: " + ct6.length);
			din.readFully(ct6);
			colC6.add(ct6);
			
			boolean v6 = din.readBoolean();;
			vis6[j] = v6;
			//----------------------------------------------------------------
			byte l7 = din.readByte();
			byte[] gt7 = new byte[l7];
			System.out.println("name length: " + gt7.length);
			din.readFully(gt7);
			System.out.println(gt7);
			names = gt7;
			namN7.add(names);

			byte lc7 = din.readByte();
			byte[] ct7 = new byte[lc7];
			System.out.println("color byte length: " + ct7.length);
			din.readFully(ct7);
			colC1.add(ct7);
			
			boolean v7 = din.readBoolean();;
			vis7[j] = v7;

		}
//		for (byte[] a : namN) {
//			System.out.println(Arrays.toString(a));
//		}
		// din.
		// strPieceNames.add(strN);
		// strPieceColors.add(strC);
		// System.out.println(pieceNames2.size());
		// pieceNames2.add(namN);
		// pieceColors2.add(colC);
		// pieceVis.add(vis);

		baInputStream.close();
		din.close();

	}

}
