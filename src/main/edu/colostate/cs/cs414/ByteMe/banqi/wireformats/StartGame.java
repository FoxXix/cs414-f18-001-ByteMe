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
	
	List<String[]> strPieceNames = new ArrayList<String[]>(8);
	List<String[]> strPieceColors = new ArrayList<String[]>(8);
	List<boolean[]> pieceVis = new ArrayList<boolean[]>();
	List<ArrayList<byte[]>> pieceNames;
	List<ArrayList<byte[]>> pieceColors;
	
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
	ArrayList<byte[]> namN8 = new ArrayList<byte[]>();
	ArrayList<byte[]> colC8 = new ArrayList<byte[]>();
	
	List<ArrayList<byte[]>> pieceNames2 = new ArrayList<ArrayList<byte[]>>();
	List<ArrayList<byte[]>> pieceColors2 = new ArrayList<ArrayList<byte[]>>();
	
	List<byte[]> nameLen;
	List<byte[]> colorLen;
	
	private byte[] playerName;
	private byte length;
	
	public void setPlayerName(byte length, byte[] name) {
		this.length = length;
		this.playerName = name;
	}

	public void setNameLengths(List<byte[]> nameLeng) {
		this.nameLen = nameLeng;
		
	}
	
	public void setColorLengths(List<byte[]> colorLeng) {
		this.colorLen = colorLeng;
		
	}
	
	public void setNames(List<ArrayList<byte[]>> pieceNameBytes) {
		pieceNames = pieceNameBytes;
		
	}
	
	public void setColors(List<ArrayList<byte[]>> pieceColorBytes) {
		pieceColors = pieceColorBytes;
	}
	
	public void setVis(List<boolean[]> vis) {
		pieceVis = vis;
	}
	
	public byte[] getPlayerName() {
		return playerName;
	}
	
	public List<ArrayList<byte[]>> getPieceNames(){
		return pieceNames;
	}
	
	public List<ArrayList<byte[]>> getPieceColors(){
		return pieceColors;
	}
	
	public List<boolean[]> getPieceVis(){
		return pieceVis;
	}

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
		
		System.out.println(pieceNames.size());
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 4; j++) {
//				byte l = (byte)g.getBytes().length;
//				System.out.println(l);
//				byte[] gt = g.getBytes();
//				
				dout.writeByte(nameLen.get(i)[j]);
				dout.write(pieceNames.get(i).get(j));
				
//				String c = pieceColors.get(i)[j];
//				System.out.println(c);
//				byte lc = (byte)g.getBytes().length;
//				System.out.println(lc);
//				byte[] ct = c.getBytes();
				
//				System.out.println(colorLen.get(i)[j]);
				dout.writeByte(colorLen.get(i)[j]);
				dout.write(pieceColors.get(i).get(j));
				
				boolean v = pieceVis.get(i)[j];
				dout.writeBoolean(v);

			}
			System.out.println();
			dout.flush();
		}

		dout.flush();
		marshalledBytes = baOutputStream.toByteArray();

		baOutputStream.close();
		dout.close();
		
//		pieceNames.clear();
//		pieceColors.clear();
//		pieceVis.clear();
//		for(int k = 0; k < pieceNames.size(); k++) {
//			for(int h = 0; h < 4; h++) {
//				pieceNames.remove(k).remove(h);
//				pieceColors.remove(k).remove(h);
//			}
//			pieceVis.remove(k);
//		}
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
		
		for(int i = 0; i < 8; i++) {
//			String[] strN = new String[4];
//			String[] strC = new String[4];
			ArrayList<byte[]> namN = new ArrayList<byte[]>();
			ArrayList<byte[]> colC = new ArrayList<byte[]>();
			byte[] names = new byte[4];
			byte[] colors = new byte[4];
			boolean[] vis = new boolean[4];
			for(int j = 0; j < 4; j++) {

				byte l = din.readByte();
				byte[] gt = new byte[l];
				System.out.println("name length: " + gt.length);
				din.readFully(gt);
				System.out.println(gt);
				names = gt;
				namN0.add(names);
				String n = new String(gt);
//				strN[j] = n;
				System.out.println(n);
//				names[j] = n;
				
				byte lc = din.readByte();
				byte[] ct = new byte[lc];
				System.out.println("color byte length: " + ct.length);
				din.readFully(ct);
				colC0.add(ct);
				String c = new String(ct);
//				strC[j] = c;
//				System.out.println(c);
//				colors[j] = c;
				
				boolean v = din.readBoolean();
				System.out.println("boolean: " + v);
				System.out.println();
				vis[j] = v;
				

			}
			for(byte[] a : namN) {
				System.out.println(Arrays.toString(a));
			}
//			din.
//			strPieceNames.add(strN);
//			strPieceColors.add(strC);
//			System.out.println(pieceNames2.size());
//			pieceNames2.add(namN);
//			pieceColors2.add(colC);
//			pieceVis.add(vis);
		}

		baInputStream.close();
		din.close();

	}


}
