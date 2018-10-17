package com.ue.projectplum.world;

import com.badlogic.gdx.math.Vector2;

public class Room {
	private Vector2 dimensions;
	private int id;
	
	private Tile[][] tileMap;
	
	public Room(char[][] map, Vector2 dimensions) {
		//create tileMap with dimensions
		tileMap = new Tile[(int) dimensions.x][(int) dimensions.y];
		//loop through all the chars in the char map and translate them into the tileMap
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				tileMap[i][j] = Tile.getTile(map[i][j]);
			}
		}
		
	}
	
	
}
