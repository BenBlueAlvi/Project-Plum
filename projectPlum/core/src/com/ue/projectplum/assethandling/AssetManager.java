package com.ue.projectplum.assethandling;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	
	private static final String textureFileExtentions = ".png .jpg";
	private static final String soundFileExtentions = ".ogg";
	
	private static class Asset<T>{
		public String name;
		public T data;
		public Asset(String name, T data) {
			this.name = name;
			this.data = data;
		}
		
	}
	
	private static ArrayList<Asset<Texture>> textureRegistry = new ArrayList<Asset<Texture>>();
	private static ArrayList<Asset<Sound>> soundRegistry = new ArrayList<Asset<Sound>>();;
	private static ArrayList<Asset<Music>> musicRegistry;
	
	/**
	 * Preloads all the assets in the various asset folders
	 */
	
	public static void preLoadAssets() {
		//open the assets folder
		final File assetFolder = new File("assets");
		//loop through the folders within
		for (File folder : assetFolder.listFiles()) {
			//check if file within is directory
			if (folder.isDirectory()) {
				//loop through files in that directory
				for (File asset : folder.listFiles()) {
					//split the asset name by the .
					
					
					//get the name and extension, extension is the last 4 chars, while name is everything but the last 4 chars
					String name = asset.getName().substring(0, asset.getName().length()-4);
					String extention = asset.getName().substring(asset.getName().length()-4);
					
					//check to make sure the asset is a file
					if (asset.isFile()) {
						//Check to make sure extension matches valid ones, if so, depending on the folder, load the texture and add it to the registry
						if (folder.getName().equals("textures") && textureFileExtentions.contains(extention)) {
							textureRegistry.add(new Asset<Texture>(name, new Texture(Gdx.files.internal(asset.getPath()))));
						} else if (folder.getName().equals("sounds") && soundFileExtentions.contains(extention)) {
							soundRegistry.add(new Asset<Sound>(name, Gdx.audio.newSound(Gdx.files.internal(asset.getPath()))));
						}
					} 
			
					
				}
				
			} else {
				System.out.println("Misplaced file: " + folder.getName());
			}
			
		}
	}
	
	

	// Base textures
	public static final Texture missingTexture = loadTexture("missingTexture");
	@Deprecated
	private static Texture loadTexture(String path) {
		try {
			Texture t = new Texture(Gdx.files.internal("assets/textures/" + path + ".png"));
			return t;

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");

			
		
			return new Texture(missingTexture.getTextureData());
		}
	}
	

	
}
