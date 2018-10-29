package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	//List<Tile> boardTiles;
	private List<List<Tile>> boardTiles; 
	
	public Board() {
		boardTiles = new ArrayList<List<Tile>>();
		
		Tile tile;
		List<Tile> row;
		
		for (int y = 0; y < 8; y++) {
			row = new ArrayList<Tile>();
			for (int x = 0; x < 4; x++) {
				tile = new Tile(x, y);
				row.add(tile);
			}
			boardTiles.add(row);
		}
	}

	public Tile getTileInfo(int x, int y) {
		int [] position = {x,y};
		return getTileInfo(position);		
	}
	
	public Tile getTileInfo(int [] position) {		
		return boardTiles.get(position[0]).get(position[1]);
	}

}
