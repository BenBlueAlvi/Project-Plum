package com.ue.projectplum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Images {

	// Base textures
	public static final Texture na = getImg("missingTex");

	public static Texture getImg(String path) {
		try {
			Texture t = new Texture(Gdx.files.internal("assets/" + path + ".png"));
			return t;

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(na.getTextureData());
			return t;
		}
	}

	// Copy a texture and return it
	public static Texture copy(Texture t) {
		return new Texture(t.getTextureData());
	}
}