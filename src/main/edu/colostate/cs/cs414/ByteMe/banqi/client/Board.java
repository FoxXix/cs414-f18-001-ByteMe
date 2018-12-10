package main.edu.colostate.cs.cs414.ByteMe.banqi.client;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	//List<Tile> boardTiles;
	private List<List<Tile>> boardTiles; 
	
	/**
	* Initializes a Banqi Game board to have the appropriate number of tiles.
	* The Banqi Game board is 4x8. 
	*/
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
	
	/**
	 * Enter an x-coordinate and a y-coordinate to find the info pertaining to the tile there
	 * @param x, the x-coordinate of the (x,y) position of the tile on the board
	 * @param y, the y-coordinate of the (x,y) position of the tile on the board
	 * @return Tile tile, the tile at the given x,y position on the board
	 */
	public Tile getTileInfo(int x, int y) {
		int [] position = {x,y};
		return getTileInfo(position);		
	}
	
	/**
	 * Enter an (x,y) coordinate pair, such as [1,2] to find the info pertaining to the tile there
	 * @param int[] position, the position of the tile on the board to get info of
	 * @return Tile tile, the tile object at the given [x,y] position on the board
	 */
	public Tile getTileInfo(int [] position) {
		return boardTiles.get(position[1]).get(position[0]);
	}

}
