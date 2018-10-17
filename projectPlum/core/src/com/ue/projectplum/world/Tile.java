package com.ue.projectplum.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.ue.projectplum.BaseActor;
import com.ue.projectplum.assethandling.AssetManager;

public class Tile extends BaseActor{
	
	private static Tile missingTile;
	
	
	
	private char id;
	private static ArrayList<Tile> allTiles = new ArrayList<Tile>();
	
	private Tile(char id, Texture tex) {
		super(tex);
		this.id = id;
		allTiles.add(this);
		
	}
	/**
	 * Gets a tile based on it's id. For internal use only
	 * returns missingTile if the tile is not found
	 * @param id the id of the texture
	 * @return the tile with id id
	 */
	public static Tile getTile(char id){
		for (Tile t : allTiles) {
			if (t.id == id) {
				return t;
			}
		}
		System.out.println("Could not find tile with id: " + id);
		return missingTile;
	}
	
	//all tiles must be defined here
	static {
		missingTile = new Tile('m', AssetManager.missingTexture);
	}
}
