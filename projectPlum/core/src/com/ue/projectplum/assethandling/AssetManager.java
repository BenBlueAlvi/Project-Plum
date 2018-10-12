package com.ue.projectplum.assethandling;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
	
	private static final String textureFileExtentions = ".png .jpg";
	private static final String soundFileExtentions = ".ogg .wav";
	
	public static final Texture missingTexture = new Texture(Gdx.files.internal("assets/textures/missingTexture.png"));
	public static final Sound missingSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/missingSound.wav"));
	

	
	private static class Asset<T>{
		public String name;
		public T data;
		public Asset(String name, T data) {
			this.name = name;
			this.data = data;
		}
		
	}
	
	private static Asset<Texture>[] textureRegistry;
	private static Asset<Sound>[] soundRegistry;
	
	
	public static Asset[] sortRegistry(Asset[] ar) {
		for (int i = 1; i < ar.length; i++) {
			Asset index = ar[i];
			int j = i;
			//follows string, sort greatest to least alphabetically
			while (j > 0 && ar[j - 1].name.compareTo(index.name) == 1) {
				ar[j] = ar[j - 1];
				j--;
			}
			ar[j] = index;
		}
		
		return ar;

	}
	
	
	
	private static Asset searchRegistry(Asset[] registry, int l, int r, String name) 
    { 
        if (r>=l) 
        { 
            int mid = l + (r - l)/2; 
  
            // If the element is present at the  
            // middle itself 
            if (registry[mid].name.equals(name)) 
               return registry[mid]; 
  
            // If element is smaller than mid, then  
            // it can only be present in left subarray 
            if (registry[mid].name.compareTo(name) == 1) 
               return searchRegistry(registry, l, mid-1, name); 
  
            // Else the element can only be present 
            // in right subarray 
            return searchRegistry(registry, mid+1, r, name); 
        } 
  
        // We reach here when element is not present 
        //  in array 
        return null; 
    } 
	
	public static Asset searchRegistry(Asset[] registry, String name) {
		return searchRegistry(registry, 0, registry.length-1, name);
	}
	
	/**
	 * Preloads all the assets in the various asset folders
	 */
	
	public static void preLoadAssets() {
		ArrayList<Asset<Texture>> foundTextures = new ArrayList<Asset<Texture>>();
		ArrayList<Asset<Sound>> foundSounds = new ArrayList<Asset<Sound>>();
		
		
		//open the assets folder
		final File assetFolder = new File("assets");
		//loop through the folders within
		for (File folder : assetFolder.listFiles()) {
			//check if file within is directory
			if (folder.isDirectory()) {
				//loop through files in that directory
				for (File asset : folder.listFiles()) {
					//get the name and extension, extension is the last 4 chars, while name is everything but the last 4 chars
					String name = asset.getName().substring(0, asset.getName().length()-4);
					String extention = asset.getName().substring(asset.getName().length()-4);
					
					//check to make sure the asset is a file
					if (asset.isFile()) {
						//Check to make sure extension matches valid ones, if so, depending on the folder, load the asset and add it to the registry
						if (folder.getName().equals("textures") && textureFileExtentions.contains(extention)) {
							foundTextures.add(new Asset<Texture>(name, new Texture(Gdx.files.internal(asset.getPath()))));
						} else if (folder.getName().equals("sounds") && soundFileExtentions.contains(extention)) {
							foundSounds.add(new Asset<Sound>(name, Gdx.audio.newSound(Gdx.files.internal(asset.getPath()))));
						}
					} 
			
					
				}
				
			} else {
				System.out.println("Misplaced file: " + folder.getName());
			}
			
		}
		//sort registries and change them to arrays
		textureRegistry = sortRegistry(foundTextures.toArray(new Asset[foundTextures.size()]));
		soundRegistry = sortRegistry(foundSounds.toArray(new Asset[foundSounds.size()]));
		
	}
	/**
	 * Searches the texture registry for a texture with name name
	 * @param name the name of the texture
	 * @return the texture with the name name
	 */
	public static Texture getTexture(String name) {
		//search the registry
		Asset<Texture> asset = searchRegistry(textureRegistry, name);
		//make sure the asset is found
		if (asset != null) {
			//return
			return asset.data;
		} else {
			//substitute missing version
			System.out.println("Could not find " + name + " in texture registry. Substituting...");
			return missingTexture;
		}
	}
	/**
	 * Searches the sound registry for a sound with name name
	 * @param name the name of the sound
	 * @return the sound with name name
	 */
	public static Sound getSound(String name) {
		//search the registry
		Asset<Sound> asset = searchRegistry(soundRegistry, name);
		//make sure the asset is found
		if (asset != null) {
			//return
			return asset.data;
		} else {
			//substitute missing version
			System.out.println("Could not find " + name + " in sound registry. Substituting...");
			return missingSound;
		}
	}
	
	
	

	
	

	
}
